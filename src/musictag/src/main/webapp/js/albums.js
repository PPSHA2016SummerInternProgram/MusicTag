$(document).ready(function() {
    var albumsWrapper = $('#albums-wrapper');
    window.Paginator($('#albums'), function(index, perPage, orderBy, direction){

        var builder = window.TagBuilder;
        var tbody = $('#album-table').find('tbody');
        tbody.empty();
        var framesContainer = $('#album-frames').find('.panel-body');
        framesContainer.empty();
        var albumCnt = 0;

        $.ajax({dataType: 'json',
            url: 'release-groups',
            data: {'cur-page': index, 'per-page': perPage, 'order-by': orderBy, 'direction': direction},
            async: false,
            success: function(groups){

                albumCnt = groups.data['release-group-total-count'];
                var imgMap = [];
                var data = groups.data['release-groups'];

                window.Enumerable(tbody, data, function(rg){
                    var cnt = rg['release-count'];
                    var html = $(builder('tr', {class: 'album-row'},
                        builder('td', null,
                            builder('img', {class: "album-cover"}) +
                            builder('span', {class: 'album-title'}, rg['title']) +
                            (cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                                'Versions' + builder('span', {class: 'badge'}, cnt)) : '')
                        ) +
                        builder('td') +
                        builder('td', {class: 'first-release-date'}, rg['first-release-date'])) );

                    imgMap[rg.id] = [html.find('img')];
                    return html;
                });

                window.Enumerable(framesContainer, data, function(rg){
                    //var cnt = rg['release-count'];
                    var frame = $(builder('div', {class: 'album-frame col-xs-6 col-sm-4 col-md-3'},
                        builder('a', {href: '#', class: 'thumbnail'},
                            builder('div', {class: 'album-cover-wrapper'},
                                builder('img', {class: 'album-cover'})
                            ) +
                            builder('span', {class: 'album-title'}, rg['title'])
                            //(cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                            //    'Version' + builder('span', {class: 'badge'}, cnt)) : '')
                        ))
                    );

                    imgMap[rg.id][1] = frame.find('img');

                    var url = ContextPath + '/cover-art-archive/release-group/' + rg.id;
                    $.getJSON(url, function(json){
                        if(json.success == true) {
                            var downloadingImage = new Image();
                            downloadingImage.onload = function () {
                                imgMap[rg.id][0].attr('src', this.src);
                                imgMap[rg.id][1].attr('src', this.src);
                            };
                            downloadingImage.src = json.data.images[0].thumbnails.large;
                        } else {
                            imgMap[rg.id][0].attr('src', ContextPath + '/images/default_album_cover.jpg');
                            imgMap[rg.id][1].attr('src', ContextPath + '/images/default_album_cover.jpg');
                        }
                    }).fail(function () {
                        imgMap[rg.id][0].attr('src', ContextPath + '/images/default_album_cover.jpg');
                        imgMap[rg.id][1].attr('src', ContextPath + '/images/default_album_cover.jpg');
                    });
                    return frame;
                });
            }
        });

        return albumCnt;
    });
    albumsWrapper.show();
});

