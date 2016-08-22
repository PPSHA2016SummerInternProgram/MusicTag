;(function($, window, undefined){
    window.LyricHelper = {
        fillLyric: function (container) {
            $.getJSON(UrlHelper.lyricUrl(getUuid()), function (json) {
                data = json.data;
                if( data.lyric_html){
                    container.html(data.lyric_html)
                } else if( data.lyric_limited) {
                    container.html(data.lyric_limited)
                } else {
                    if(data.from === 'musixmatch' && data.musixmatch_msg.body.track.instrumental === 0) {
                        container.html('This recording is instrumental');
                    } else {
                        container.html('The lyric is not available for now');
                    }
                }
            })
            
        }

    }
})(jQuery, window);
