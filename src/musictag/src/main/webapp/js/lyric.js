;(function($, window, undefined){
    window.LyricHelper = {
        fillLyric: function (container) {
            $.getJSON(UrlHelper.lyricUrl(getUuid()), function (json) {
                data = json.data;
                if( data === null) {
                    container.html(LyricHelper.lyric_unavailable);
                    return;
                }

                if( data.lyric_html){
                    container.html(data.lyric_html)
                } else if( data.lyric_limited) {
                    var url = data.musixmatch_msg.body.track.track_share_url;
                    container.html( TagBuilder('p', {class: 'lyric-limited'}, data.lyric_limited +
                    TagBuilder('p', null,
                        'See the full version at ' + TagBuilder('a', {class: 'lyric-url', href: url}, 'MusixMatch') + '.') ));
                } else {
                    if(data.from === 'musixmatch' && data.musixmatch_msg.body.track.instrumental === 1) {
                        container.html(LyricHelper.lyric_instrumental);
                    } else {
                        container.html(LyricHelper.lyric_unavailable);
                    }
                }
            })
            
        },
        lyric_unavailable: TagBuilder('p', {class: 'lyric-unavailable'},
            TagBuilder('i', {class: 'glyphicon glyphicon-exclamation-sign'}) + 'This lyric is not available for now.'),

        lyric_instrumental: TagBuilder('p', {class: 'lyric-instrumental'},
            TagBuilder('i', {class: 'glyphicon glyphicon-info-sign'}) + 'This recording is instrumental.')

    }
})(jQuery, window);
