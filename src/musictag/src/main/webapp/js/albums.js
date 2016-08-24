$(document).ready(function() {
    // initialize albums display
    var albumsWrapper = $('#albums-wrapper');
    var albums = $('#albums');
    window.Paginator(albums, 'release-groups', function(groups){

        var builder = window.TagBuilder;
        var tbody = $('#album-table').find('tbody');
        var frames = $('#album-frames').find('.panel-body');
        tbody.empty();
        frames.empty();
        var albumCnt = groups.data['release-group-total-count'];
        var imgMap = [];
        var data = groups.data['release-groups'];

        data.forEach(function(rg){
            var cnt = rg['release-count'];
            var dataAttr;
            if(cnt === 0) dataAttr = {data: {toggle: 'modal', target: '#release-group-modal'}};
            else if(cnt === 1) dataAttr = { data: {link: rg.id}};
            else dataAttr = {data: {toggle: 'modal', target: '#releases-modal', 'release-group-id': rg.id}};

            var html = $(builder('tr', {class: 'album-row'},
                builder('td', null,
                    builder('img', $.extend({class: 'album-cover clickable'}, dataAttr) ) +
                    builder('span', {class: 'album-title'}, rg['title']) +
                    (cnt > 1 ? builder('button', $.extend({class: 'btn btn-sm btn-primary'}, dataAttr),
                        builder('span', {class: 'badge'}, cnt) + ' Versions') : '')
                ) +
                builder('td') +
                builder('td', {class: 'first-release-date'}, rg['first-release-date']))
            );

            imgMap[rg.id] = [html.find('img')];
            html.appendTo(tbody);

            var frame = $(builder('div', {class: 'album-frame col-xs-6 col-sm-4 col-md-3'},
                builder('a',  $.extend({class: 'thumbnail'}, dataAttr),
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
                if(json.success === true && json.data.images) {
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
            if(json.success === true) {
                var releases = json.data.releases;
                releases.forEach(function(r, index){
                    var region;
                    var re = r['release-events'][0];
                    if( re.area && re.area.name ) {
                        region = re.area.name;
                    } else {
                        region = r.country;
                    }
                    var builder = window.TagBuilder;
                    $(builder('tr', null,
                        builder('td', null, index + 1) +
                        builder('td', null, builder('a', {href: UrlHelper.releaseUrl(r.id)}, r.title)) +
                        builder('td', null, r.status) +
                        builder('td', null, region) +
                        builder('td', null, r.date) +
                        builder('td', null, r.disambiguation)
                    )).appendTo(container);
                });
            }
        });
    });

    albums.on('click', "[data-link]", function (e) {
        e.preventDefault();
        var id = $(this).data('link');
        $.getJSON(UrlHelper.releasesUrl(id), function (json) {
            if(json.success) {
                window.location.href = UrlHelper.releaseUrl(json.data.releases[0].id);
            }
            // TODO: when not success
        });
    });

    albumsWrapper.show();
});

