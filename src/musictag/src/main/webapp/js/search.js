$(document).ready(function() {
    $('#artist-filter').on('click', '[data-selectable]', function (e) {
        var t = $(this);
        if(t.hasClass('selected')) {
            t.removeClass('selected');
        } else {
            t.addClass('selected');
        }
    });

    $('#artist-sorter').on('click', '.up, .down', function (e) {
        $(e.delegateTarget).find('.up, .down').not(this).removeClass('selected');
        $(this).toggleClass('selected');
    });

    var queryKey = decodeURIComponent( UrlHelper.getUrlVars()['key'].replace(/\+/g, '%20'));

    $("#search-input").val(queryKey);
    $("#navbar-search-input").val(queryKey);

    var summaryUpdate = function (numFound) {
        $("#search-summary").html(TagBuilder('p', null,
            'Search for "'+ queryKey + '", ' + TagBuilder('span', {class: 'total-found'}, numFound) + ' Found'
        ));
    };

    var artistHandler = function(e) {
        var numFound = 0;
        var url = '/musictag/search/artist?key=' + queryKey;

        var generateUrl = function() {
            var filter = $('#artist-filter').find('.filter').map(function(){
                return Filter.serializeFilterGroup($(this));
            }).get().join('&');

            var partition = Filter.serializePartition($('#artist-partition'));
            var sorter = Filter.serializeSorter($('#artist-sorter'));
            if(filter) url += '&' + filter;
            return url + (partition ? '&' + partition : '' ) + (sorter ? '&' + sorter : '');
        };

        var artistUpdate = function (json) {
            var filter_html = Filter.buildFilter(json.data.facet_counts.facet_fields,
                json.data.facet_counts.facet_ranges);
            $('#artist-filter').html(filter_html);

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
        };
        var enumerable = $("#artist-frames");
        Paginator(enumerable, generateUrl, artistUpdate);

        $('#apply-filter').on('click', function () {
            Paginator.turnTo(enumerable, 0, generateUrl, artistUpdate);
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
                        TagBuilder('span', {class: 'artists-name'}, highlight[doc.mbid]['artists_name'].join(',') || doc.name)
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
                    TagBuilder('td', {class: 'artists-name'}, hl.artists_name.join(',')) +
                    TagBuilder('td', {class: 'related-releases'}, relatedRelease + relatedReleasesMore) +
                    TagBuilder('td', {class: 'length'}, OtherHelper.recordingLength(doc.length)) ));

                row.appendTo(tbody);
            });

            return res.numFound;
        });
        $("#recordings-tab").off('show.bs.tab', recordingHandler).on('show.bs.tab', function() {
            summaryUpdate(numFound);
        });
    };

    var lyricHandler = function(e) {
        var numFound = 0;
        var table = $("#lyric-tbl");
        Paginator(table, '/musictag/search/lyric?key=' + queryKey, function (json) {

            var tbody = table.find('tbody');
            var res = json.data.response;
            var highlight = json.data.highlighting;

            numFound = res.numFound;
            summaryUpdate(res.numFound);

            tbody.empty();
            res.docs.forEach(function(doc){
                var hl = highlight[doc.work_mbid];
                var relatedRecording = '';
                var relatedRecordingsMore = '';

                if( doc.hasOwnProperty("recordings_mbid") ) {
                    // NOTE: assume doc.releases_mbid, hl.releases_name have the same length
                    relatedRecording = TagBuilder('a', {href: UrlHelper.recordingUrl(doc.recordings_mbid[0])},
                        TagBuilder('i', {class: 'glyphicon glyphicon-music'}, '&nbsp;') + doc.recordings_name[0]);

                    for (var i = 1; i < doc.recordings_mbid.length; i++) {
                        relatedRecordingsMore += TagBuilder('li', null,
                            TagBuilder('a', {href: UrlHelper.recordingUrl(doc.recordings_mbid[i])}, doc.recordings_name[i]));
                    }

                    if (doc.recordings_mbid.length > 1) {
                        relatedRecordingsMore = TagBuilder('span', {class: 'recording-icon'}, ' | ') + TagBuilder('span', {class: 'dropdown'},
                                TagBuilder('a', { class: 'recordings-more', href: '#', data: { toggle: 'dropdown'}}, 'More' + TagBuilder('span', {class: 'caret'})) +
                                TagBuilder('ul', {class: 'dropdown-menu'}, relatedRecordingsMore)
                            );
                    }
                }

                var row = $(TagBuilder('tr', {class: 'recording-row'},
                    TagBuilder('td', {class: 'related-recording'}, relatedRecording + relatedRecordingsMore) +
                    TagBuilder('td', {class: ''}, hl.lyric_limited[0] + TagBuilder('span', null, '...') )
                ));
                row.appendTo(tbody);
            });

            return res.numFound;
        });
        $("#lyrics-tab").off('show.bs.tab', lyricHandler).on('show.bs.tab', function() {
            summaryUpdate(numFound);
        });
    };

    $('#artists-tab').on('show.bs.tab', artistHandler).trigger('click');
    $('#releases-tab').on('show.bs.tab', releaseHandler);
    $('#recordings-tab').on('show.bs.tab', recordingHandler);
    $('#lyrics-tab').on('show.bs.tab', lyricHandler);
});