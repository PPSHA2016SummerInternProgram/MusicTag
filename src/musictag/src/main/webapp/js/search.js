$(document).ready(function() {

    var queryKey = decodeURIComponent( getUrlVars()['key'].replace(/\+/g, '%20'));

    $("#search-input").val(queryKey);
    $("#navbar-search-input").val(queryKey);

    var summaryUpdate = function (numFound) {
        $("#search-summary").html(TagBuilder('p', null,
            'Search for "'+ queryKey + '", ' + TagBuilder('span', {class: 'total-found'}, numFound) + ' Found'
        ));
    };

    var artistHandler = function(e) {
        var numFound = 0;
        Paginator($("#artist-frames"), '/musictag/search/artist?key=' + queryKey, function (json) {

            var frames = $("#artist-frames");
            var res = json.data.response;
            var highlight = json.data.highlighting;

            numFound = res.numFound;
            summaryUpdate(res.numFound);

            frames.empty();
            res.docs.forEach(function(doc){
                var artistUrl = UrlHelper.artistUrl(doc.mbid);
                var frame = $(TagBuilder('div', {class: 'artist-frame col-xs-6 col-sm-4 col-md-3 col-lg-2'},
                    TagBuilder('a',  {href: artistUrl, class: 'thumbnail'},
                        TagBuilder('div', {class: 'cover-wrapper'},
                            TagBuilder('img', {class: 'cover', src: UrlHelper.defaultArtistCoverUrl})
                        ) +
                        TagBuilder('span', {class: 'title'}, highlight[doc.mbid]['name'][0] || doc.name)
                    )));

                $.getJSON(artistUrl + 'image', function(json){
                    var downloadingImage = new Image();
                    downloadingImage.onload = function () {
                        frame.find('img').attr('src', this.src);
                    };

                    if(json['data']['commons-img']){
                    	downloadingImage.src = json.data['commons-img'];
                    	frame.find('img').attr('src', this.src);
                    }
                });
                frame.appendTo(frames);
            });

            return res.numFound;
        });
        $("#artists-tab").off('show.bs.tab', artistHandler).on('show.bs.tab', function(){
            summaryUpdate(numFound);
        });
    };

    var releaseHandler = function(e) {
        var numFound = 0;
        Paginator($("#release-frames"), '/musictag/search/release?key=' + queryKey, function (json) {

            var frames = $("#release-frames");
            var res = json.data.response;
            var highlight = json.data.highlighting;

            numFound = res.numFound;
            summaryUpdate(res.numFound);

            frames.empty();
            res.docs.forEach(function(doc){
                var releaseUrl = UrlHelper.releaseUrl(doc.mbid);
                var year_month = (doc.release_year ? doc.release_year : '?') + ' - ' + (doc.release_month ? doc.release_month : '?');
                var frame = $(TagBuilder('div', {class: 'release-frame col-xs-6 col-sm-4 col-md-3 col-lg-2'},
                    TagBuilder('a',  {href: releaseUrl, class: 'thumbnail'},
                        TagBuilder('div', {class: 'cover-wrapper'},
                            TagBuilder('img', {class: 'cover', src: UrlHelper.defaultArtistCoverUrl})
                        ) +
                        TagBuilder('span', {class: 'title'}, (highlight[doc.mbid]['name'] || doc.name) + ' ' + year_month) +
                        TagBuilder('span', {class: 'artists_name'}, highlight[doc.mbid]['artists_name'].join(',') || doc.name)
                    )));

                $.getJSON(releaseUrl + 'image', function(json){
                    var downloadingImage = new Image();
                    downloadingImage.onload = function () {
                        frame.find('img').attr('src', this.src);
                    };

                    if(json['data']['images']){
                    	downloadingImage.src = json.data.images[0].thumbnails.large;
                    	frame.find('img').attr('src', this.src);
                    }
                });
                frame.appendTo(frames);
            });

            return res.numFound;
        });
        $("#releases-tab").off('show.bs.tab', releaseHandler).on('show.bs.tab', function(){
            summaryUpdate(numFound);
        });
    };

    var recordingHandler = function(e) {
        var numFound = 0;
        var table = $("#recording-tbl");
        Paginator(table, '/musictag/search/recording?key=' + queryKey, function (json) {

            var tbody = table.find('tbody');
            var res = json.data.response;
            var highlight = json.data.highlighting;

            numFound = res.numFound;
            summaryUpdate(res.numFound);

            tbody.empty();
            res.docs.forEach(function(doc){
                var releaseUrl = UrlHelper.recordingUrl(doc.mbid);
                var hl = highlight[doc.mbid];
                var relatedRelease = '';
                var relatedReleasesMore = '';

                if( doc.hasOwnProperty("releases_mbid") ) {
                    // NOTE: assume doc.releases_mbid, hl.releases_name have the same length
                    relatedRelease = TagBuilder('a', {href: UrlHelper.releaseUrl(doc.releases_mbid[0])}, hl.releases_name[0]);

                    for (var i = 1; i < doc.releases_mbid.length; i++) {
                        relatedReleasesMore += TagBuilder('li', null,
                            TagBuilder('a', {href: UrlHelper.releaseUrl(doc.releases_mbid[i])}, hl.releases_name[i]));
                    }

                    if (doc.releases_mbid.length > 1) {
                        relatedReleasesMore = TagBuilder('span', {class: 'recording-icon'}, ' | ') + TagBuilder('span', {class: 'dropdown'},
                                TagBuilder('a', { class: 'releases-more', href: '#',data: { toggle: 'dropdown'}}, 'More' + TagBuilder('span', {class: 'caret'})) +
                                TagBuilder('ul', {class: 'dropdown-menu'}, relatedReleasesMore)
                            );
                    }
                }

                var row = $(TagBuilder('tr', {class: 'recording-row'},
                    TagBuilder('td', {class: 'name'}, TagBuilder('i', {class: 'glyphicon glyphicon-music'}, '&nbsp;') +
                        TagBuilder('a', {href: releaseUrl}, highlight[doc.mbid]['name'] || doc.name)) +
                    TagBuilder('td', {class: 'artists_name'}, hl.artists_name.join(',')) +
                    TagBuilder('td', {class: 'related_releases'}, relatedRelease + relatedReleasesMore) +
                    TagBuilder('td', {class: 'length'}, OtherHelper.recordingLength(doc.length)) ));

                row.appendTo(tbody);
            });

            return res.numFound;
        });
        $("#recordings-tab").off('show.bs.tab', recordingHandler).on('show.bs.tab', function() {
            summaryUpdate(numFound);
        });
    };

    $('#artists-tab').on('show.bs.tab', artistHandler).trigger('click');
    $('#releases-tab').on('show.bs.tab', releaseHandler);
    $('#recordings-tab').on('show.bs.tab', recordingHandler);
});