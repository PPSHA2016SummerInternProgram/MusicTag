var initAlbumEumerable = function(json) {
    var release_groups_key = 'release-groups';
    var release_group_key = 'release-group';

    var table = $('table[' + 'data-'+release_groups_key + ']');
    var thead = table.find('thead');
    var tbody = table.find('tbody');

    var framesContainer = $('#album-frames .panel-body');
    
    var data = json['data'][release_groups_key];

    var builder = window.TagBuilder;
    var imgURLs = [];

    window.Enumerable(tbody, data, function(rg){
        var cnt = rg.count;

        var html = $(builder('tr', {class: 'album-row'},
            builder('td', null,
                builder('img', {class: "album-cover"}) +
                builder('span', {class: 'album-title'}, rg['title']) +
                (cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                    '版本数' + builder('span', {class: 'badge'}, cnt)) : '')
            ) +
            builder('td') +
            builder('td', {class: 'first-release-date'}, rg['first-release-date'])) );

        var url = ContextPath + '/cover-art-archive/release-group/' + rg.id;

        $.getJSON(url, function(json){
            if(json.success == true) {
                var downloadingImage = new Image();
                downloadingImage.onload = function () {
                    var img = $(html).find('img');
                    img.attr('src', this.src);
                };
                downloadingImage.src = json.data.images[0].thumbnails.large;
            } else {
                $(html).find('img').attr('src', ContextPath + '/images/default_album_cover.jpg')
            }
        }).fail(function(){
            $(html).find('img').attr('src', ContextPath + '/images/default_album_cover.jpg')
        });

        return html;
    });
    window.Paginator(tbody);

    window.Enumerable(framesContainer, data, function(rg){
        //var cnt = rg['release-count'];
        var frame = $(builder('div', {class: 'album-frame col-xs-6 col-sm-4 col-md-3'},
            builder('a', {href: '#', class: 'thumbnail'},
                builder('div', {class: 'album-cover-wrapper'},
                    builder('img', {class: 'album-cover'})
                ) +
                builder('span', {class: 'album-title'}, rg['title'])
                //(cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                //    '版本数' + builder('span', {class: 'badge'}, cnt)) : '')
            ))
        );

        var url = ContextPath + '/cover-art-archive/release-group/' + rg.id;

        $.getJSON(url, function(json){
            if(json.success == true) {
                var downloadingImage = new Image();
                downloadingImage.onload = function () {
                    var img = $(frame).find('img');
                    img.attr('src', this.src);
                };
                downloadingImage.src = json.data.images[0].thumbnails.large;
            } else {
                $(frame).find('img').attr('src', ContextPath + '/images/default_album_cover.jpg')
            }
        }).fail(function () {
            $(frame).find('img').attr('src', ContextPath + '/images/default_album_cover.jpg')
        });
        return frame;
    });
    window.Paginator(framesContainer);

};

$(document).ready(function() {
    $.getJSON('release-groups', function (json) {
        initAlbumEumerable(json);
        $('[data-artist-albums]').show();
    });

});

