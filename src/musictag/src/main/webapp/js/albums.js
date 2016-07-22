$(document).ready(function() {
    // initialize albums display
    var albumsWrapper = $('#albums-wrapper');
    var albums = $('#albums')
    window.Paginator(albums, function(index, perPage, orderBy, direction){

        var builder = window.TagBuilder;
        var tbody = $('#album-table').find('tbody');
        var frames = $('#album-frames').find('.panel-body');
        tbody.empty();
        frames.empty();
        var albumCnt = 0;

        $.ajax({dataType: 'json',
            url: 'release-groups',
            data: {'cur-page': index, 'per-page': perPage, 'order-by': orderBy, 'direction': direction},
            async: false,
            success: function(groups){

                albumCnt = groups.data['release-group-total-count'];
                var imgMap = [];
                var data = groups.data['release-groups'];

                data.forEach(function(rg){
                    var cnt = rg['release-count'];
                    var html = $(builder('tr', {class: 'album-row'},
                        builder('td', null,
                            builder('img', {class: "album-cover"}) +
                            builder('span', {class: 'album-title'}, rg['title']) +
                            (cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary', data: {toggle: 'modal', target: '#releases-modal', 'release-group-id': rg.id}},
                                builder('span', {class: 'badge'}, cnt) + ' Versions') : '')
                        ) +
                        builder('td') +
                        builder('td', {class: 'first-release-date'}, rg['first-release-date']))
                        // (cnt > 1 ? builder('tr', {id: "releases-" + rg.id, class: 'collapse', data:{'release-group-id': rg.id}}) : '')
                    );

                    imgMap[rg.id] = [html.find('img')];
                    html.appendTo(tbody);

                    var frame = $(builder('div', {class: 'album-frame col-xs-6 col-sm-4 col-md-3'},
                        builder('a', cnt > 1 ? {href: '#releases-modal', class: 'thumbnail', data: {toggle: 'modal', 'release-group-id': rg.id}} : {href: '#', class: 'thumbnail'},
                            builder('div', {class: 'album-cover-wrapper'},
                                builder('img', {class: 'album-cover'})
                            ) +
                            builder('span', {class: 'album-title'}, rg['title'])
                        ))
                    );

                    imgMap[rg.id][1] = frame.find('img');
                    frame.appendTo(frames);

                    var url = ContextPath + '/cover-art-archive/release-group/' + rg.id;
                    var defaultAlbumCover = '/images/default_album_cover.jpg';
                    $.getJSON(url, function(json){
                        if(json.success === true) {
                            var downloadingImage = new Image();
                            downloadingImage.onload = function () {
                                imgMap[rg.id][0].attr('src', this.src);
                                imgMap[rg.id][1].attr('src', this.src);
                            };
                            downloadingImage.src = json.data.images[0].thumbnails.large;
                        } else {
                            imgMap[rg.id][0].attr('src', ContextPath + defaultAlbumCover);
                            imgMap[rg.id][1].attr('src', ContextPath + defaultAlbumCover);
                        }
                    }).fail(function () {
                        imgMap[rg.id][0].attr('src', ContextPath + defaultAlbumCover);
                        imgMap[rg.id][1].attr('src', ContextPath + defaultAlbumCover);
                    });
                });
            }
        });

        return albumCnt;
    });

    // bind album order select event
    $('[data-albums-order]').find('select').change(function(){
        var op = $(this).find('option:selected');
        var albums = $('#albums');
        albums.data('order-by', op.data('order-by')).data('direction', op.data('direction'));
        window.Paginator.turnTo(albums, 0);
    });

    // bind event to fetch multiple releases of one release group
    albums.on('click', "[data-release-group-id]", function (e) {
        e.preventDefault();
        var releaseGroupId = $(this).data('release-group-id');
        var container = $('#releases-modal tbody');
        container.empty();

        $.getJSON(ContextPath + "/release-group/" + releaseGroupId + "/releases", function(json){
            if(json.success == true) {
                var releases = json.data.releases;
                releases.forEach(function(r, index){
                    var builder = window.TagBuilder;
                    $(builder('tr', null,
                        builder('td', null, index + 1) +
                        builder('td', null, r.title) +
                        builder('td', null, r.status) +
                        builder('td', null, r.country) +
                        builder('td', null, r.date)
                    )).appendTo(container);
                });
            }
        });

    });

    albumsWrapper.show();
});

