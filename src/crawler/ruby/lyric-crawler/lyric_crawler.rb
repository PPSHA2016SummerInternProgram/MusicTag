require 'mongo'
require 'nokogiri'
require 'open-uri'
require 'yaml'
require 'pry'
require 'csv'

class AntiCrawlerError < StandardError
end

class LyricCrawler
  MONGO_CONN_STR = 'mongodb://10.24.53.72:27017/music-tag'
  OFFSET_FILE = 'logs/ruby_lyric_crawler.offset'

  def initialize
    @mongo_client = Mongo::Client.new MONGO_CONN_STR;
    @lyrics_url_coll = @mongo_client[:lyric]
    @selector = '.mxm-lyrics__content'
    load_offset
    @lyrics_url_set = @mongo_client[:lyric].find({'from' => 'musixmatch'}, 'no_cursor_timeout' => true, batch_size: 100, offset: @offset).to_enum
    @mutex = Mutex.new
    @header = {
      'User-Agent' => 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:48.0) Gecko/20100101 Firefox/48.0',
      'Accept' => 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
    }

  end

  def crawl_a_lyric_page
    lyric = next_lyric

    url = lyric['musixmatch_msg']['body']['track']['track_share_url']

    begin
      parsed_url = URI(url)
      html = Nokogiri::HTML(parsed_url.read(@header))
      lyric_html = html.css @selector
      if lyric_html
        doc = {
          'lyric_html' => lyric_html.to_html,
          'lyric_text' => lyric_html.inner_text
        }
        if doc['lyric_html'].empty? || doc['lyric_text'].empty?
          raise AntiCrawlerError.new('May encounter Anti-Cralwer')
        end
        @lyrics_url_coll.update_one({'_id' => lyric['_id']}, {'$set' => doc} )
      end
    rescue URI::InvalidURIError => e0
      puts "Invalid URI Error: #{e0.message}, offset: #{@offset}"
    rescue OpenURI::HTTPError => e1
      puts "HTTP Error: #{e1.message}, offset: #{@offset}"
    rescue RuntimeError => e2
      puts "Runtime Error: #{e2.message}, offset: #{@offset}"
    end
  end

  def next_lyric
    @mutex.synchronize {
      puts "offset is #{@offset}"
      save_offset if @offset % 10 == 0
      @offset += 1
      @lyrics_url_set.next
    }
  end

  def filter_proxies
    @proxies = []
    CSV.foreach('proxy_list.csv') do |row|
      puts 'begin test ' + row.to_s
      proxy = 'http://' + row.first
      read_timeout = row[3].to_i
      options = {proxy: proxy, read_timeout: read_timeout+5}
      test_url = URI('http://www.baidu.com')
      begin
        res = test_url.read(options)
        puts proxy + ' is OK'
        @proxies << proxy
      rescue Net::ReadTimeout => e1
        puts proxy + ': read Timeout'
      rescue Net::OpenTimeout => e2
        puts proxy + ': open Timeout'
      rescue Exception => e3
        puts e3.message
      end
    end
    binding.pry
  end

  def load_offset
    if File.exist? OFFSET_FILE
      @offset = IO.read(OFFSET_FILE).to_i
    else
      @offset = 0
    end
  end

  def save_offset
    IO.write OFFSET_FILE, @offset
  end

end

lc = LyricCrawler.new
lc.filter_proxies
# i = 0
# while i < 10000
  # lc.crawl_a_lyric_page
  # i += 1
# end
