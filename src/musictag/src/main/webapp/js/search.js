$(document).ready(function() {

    var queryKey = decodeURIComponent( getUrlVars()['key'].replace(/\+/g, '%20'));

    $("#search-input").val(queryKey);
    $("#navbar-search-input").val(queryKey);

    var artistHandler = function(e) {
        Paginator($("#artist-frames"), '/musictag/search/artist?key=' + queryKey, function (json) {

            var frames = $("#artist-frames");
            var res = json.data.response;
            var summary = $("#search-summary");

            summary.html(TagBuilder('p', null,
                'Search for "'+ queryKey + '", ' + TagBuilder('span', null, res.numFound) + ' Found'
            ));

            frames.empty();
            res.docs.forEach(function(doc){
                var artistUrl = UrlHelper.artistUrl(doc.mbid);
                var frame = $(TagBuilder('div', {class: 'artist-frame col-xs-6 col-sm-4 col-md-3 col-lg-2'},
                    TagBuilder('a',  {href: artistUrl, class: 'thumbnail'},
                        TagBuilder('div', {class: 'cover-wrapper'},
                            TagBuilder('img', {class: 'cover', src: UrlHelper.defaultArtistCoverUrl})
                        ) +
                        TagBuilder('span', {class: 'title'}, doc.name)
                    )));

                $.getJSON(artistUrl + 'image', function(json){
                    var downloadingImage = new Image();
                    downloadingImage.onload = function () {
                        frame.find('img').attr('src', this.src);
                    };
                    downloadingImage.src = json.data['commons-img'];
                    frame.find('img').attr('src', this.src);
                });
                frame.appendTo(frames);
            });

            return res.numFound;
        });
        $("#artists-tab").off('show.bs.tab', artistHandler);
    };
    
    var releaseHandler = function (e) {
        Paginator($("#release-frames"), '/musictag/search/release?key=' + queryKey, function (json) {

            var frames = $("#release-frames");
            var res = json.data.response;
            var summary = $("#search-summary");

            summary.html(TagBuilder('p', null,
                'Search for "'+ queryKey + '", ' + TagBuilder('span', null, res.numFound) + ' Found'
            ));

            frames.empty();
            res.docs.forEach(function(doc){
                var releaseUrl = UrlHelper.releaseUrl(doc.mbid);
                var frame = $(TagBuilder('div', {class: 'release-frame col-xs-6 col-sm-4 col-md-3 col-lg-2'},
                    TagBuilder('a',  {href: releaseUrl, class: 'thumbnail'},
                        TagBuilder('div', {class: 'cover-wrapper'},
                            TagBuilder('img', {class: 'cover', src: UrlHelper.defaultArtistCoverUrl})
                        ) +
                        TagBuilder('span', {class: 'title'}, doc.name)
                    )));

                $.getJSON(releaseUrl + 'image', function(json){
                    var downloadingImage = new Image();
                    downloadingImage.onload = function () {
                        frame.find('img').attr('src', this.src);
                    };

                    downloadingImage.src = json.data.images[0].thumbnails.large;
                    frame.find('img').attr('src', this.src);
                });
                frame.appendTo(frames);
            });

            return res.numFound;
        });
        $("#releases-tab").off('show.bs.tab', releaseHandler);
    };

    var recordingHandler = function(e) {
        var table = $("#recording-tbl");
        Paginator(table, '/musictag/search/recording?key=' + queryKey, function (json) {

            var tbody = table.find('tbody');
            var res = json.data.response;
            var summary = $("#search-summary");

            summary.html(TagBuilder('p', null,
                'Search for "'+ queryKey + '", ' + TagBuilder('span', null, res.numFound) + ' Found'
            ));

            tbody.empty();
            res.docs.forEach(function(doc){
                var releaseUrl = UrlHelper.recordingUrl(doc.mbid);
                var relatedReleases = '';
                for(var i = 0; i < doc.releases_name.length; i++) {
                    relatedReleases += TagBuilder('li', null,
                    TagBuilder('a', {href: UrlHelper.releaseUrl(doc.releases_mbid[i])}, doc.releases_name[i]))
                }
                relatedReleases = TagBuilder('ul', {class: 'related-releases'}, relatedReleases);
                var row = $(TagBuilder('tr', {class: 'recording-row'},
                    TagBuilder('td', {class: 'name'}, TagBuilder('i', {class: 'glyphicon glyphicon-music'}, '&nbsp;') + TagBuilder('a', {href: releaseUrl}, doc.name)) +
                    TagBuilder('td', {class: 'artists_name'}, doc.artists_name.join(',')) +
                    TagBuilder('td', {class: 'related_releases'}, relatedReleases) +
                    TagBuilder('td', {class: 'length'}, OtherHelper.recordingLength(doc.length)) ));

                row.appendTo(tbody);
            });

            return res.numFound;
        });
        $("#releases-tab").off('show.bs.tab', releaseHandler);
    };

    $('#artists-tab').on('show.bs.tab', artistHandler).trigger('click');
    $('#releases-tab').on('show.bs.tab', releaseHandler);
    $('#recordings-tab').on('show.bs.tab', recordingHandler);
});