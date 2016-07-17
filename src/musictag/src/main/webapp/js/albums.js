var initAlbumEumerable = function(json) {
    var release_groups_key = 'release-groups';
    var release_group_key = 'release-group';

    var table = $('table[' + 'data-'+release_groups_key + ']');
    var thead = table.find('thead');
    var tbody = table.find('tbody');

    var framesContainer = $('#album-frames .panel-body');
    
    var data = json['data'][release_groups_key];

    var builder = window.TagBuilder;

    window.Enumerable(tbody, data, function(rg){
        var cnt = rg['release-count'];
        var html = builder('tr', {class: 'album-row'},
            builder('td', null,
                builder('img', {class: "album-cover", src: "images/default_album_cover.jpg"}) +
                builder('span', {class: 'album-title'}, rg['title']) +
                (cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                    '版本数' + builder('span', {class: 'badge'}, cnt)) : '')
            ) +
            builder('td') +
            builder('td', {class: 'first-release-date'}, rg['first-release-date']));
        return html;
    });
    window.Paginator(tbody);

    window.Enumerable(framesContainer, data, function(rg){
        //var cnt = rg['release-count'];
        var html = builder('div', {class: 'album-frame col-xs-6 col-sm-4 col-md-3'},
            builder('a', {href: '#', class: 'thumbnail'},
                builder('img', {class: "album-cover", src: "images/default_album_cover.jpg"}) +
                builder('span', null, rg['title'])
                //(cnt > 1 ? builder('button', {class: 'btn btn-sm btn-primary'},
                //    '版本数' + builder('span', {class: 'badge'}, cnt)) : '')
            )

        );
        return html;
    });
    window.Paginator(framesContainer);

};

$(document).ready(function() {
    var json = {"data":{"release-group-count":29,"release-groups":[
            {"release-count": 3,"disambiguation":"","title":"12 新作","first-release-date":"2012-12-28","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":[],"id":"01385daa-0a2d-462a-bdbb-fe9a320d0f13","secondary-types":[],"primary-type":"Album"},{"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2003-11-12","secondary-type-ids":["22a628ad-c082-3c4f-b1b6-d41665107b88"],"id":"07e46f14-39c0-39d4-93a0-7806c9c25745","primary-type":"Album","secondary-types":["Soundtrack"],"title":"尋找周杰倫","disambiguation":""},{"disambiguation":"","title":"魔杰座","secondary-type-ids":[],"first-release-date":"2008-10-15","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-types":[],"primary-type":"Album","id":"277e7c44-0b3b-3349-b280-1ca1b29491d2"},{"id":"29e848d0-8635-311b-89fc-2e59bb3d1437","primary-type":"Album","secondary-types":["Soundtrack"],"first-release-date":"2006-12-08","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":["22a628ad-c082-3c4f-b1b6-d41665107b88"],"disambiguation":"","title":"黃金甲"},{"disambiguation":"","title":"J III","primary-type":"Other","secondary-types":[],"id":"3a3a5a0b-cd08-3840-9a7c-4beae7883167","secondary-type-ids":[],"primary-type-id":"4fc3be2b-de1e-396b-a933-beb8f1607a22","first-release-date":"2005-06-23"},{"disambiguation":"","title":"11月的蕭邦","primary-type":"Album","secondary-types":[],"id":"463ffb62-7116-399e-9721-9b46ae412b88","secondary-type-ids":[],"first-release-date":"2005-10-31","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc"},{"disambiguation":"","title":"我很忙","secondary-types":[],"primary-type":"Album","id":"48ec5f95-6378-3dc1-bab0-d7275124b935","secondary-type-ids":[],"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2007-11-02"},{"id":"603a6f67-8eb2-3eba-b428-ce93feb92d16","primary-type":"Album","secondary-types":["Compilation"],"first-release-date":"2007-11-16","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":["dd2a21e1-0c00-3729-a7a0-de60b84eb5d1"],"title":"我們都愛這個倫","disambiguation":""},{"disambiguation":"","title":"范特西","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2001-09-20","secondary-type-ids":[],"id":"732b78cb-9f7d-383d-86cc-5cf7e43c9658","primary-type":"Album","secondary-types":[]},{"title":"八度空間","disambiguation":"","first-release-date":"2002-07-19","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":[],"id":"796a921e-9370-3441-a755-f6d9adb2e8dc","secondary-types":[],"primary-type":"Album"},{"first-release-date":"2005-08-31","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":["dd2a21e1-0c00-3729-a7a0-de60b84eb5d1"],"id":"87235d7d-7398-3fa5-aaf2-d1baca826257","primary-type":"Album","secondary-types":["Compilation"],"disambiguation":"","title":"Initial J"},{"id":"877e9870-1b55-3153-8875-139cfe468679","primary-type":"Album","secondary-types":[],"first-release-date":"2000-11-07","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":[],"disambiguation":"","title":"Jay"},{"primary-type":"Album","secondary-types":[],"id":"87cf88bb-468c-468e-84d2-ce677354894c","secondary-type-ids":[],"first-release-date":"2011-11-11","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","title":"驚嘆號","disambiguation":""},{"title":"天地一鬥","disambiguation":"","primary-type-id":"d6038452-8ee0-3f68-affc-2de9a1ede0b9","first-release-date":"2011-03-08","secondary-type-ids":[],"id":"8836dff9-78b5-49c2-bad2-2c8a936c296a","secondary-types":[],"primary-type":"Single"},{"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2003-07-31","secondary-type-ids":[],"id":"ac2b0f62-9139-351c-8639-c5bac2a31a30","primary-type":"Album","secondary-types":[],"disambiguation":"","title":"葉惠美"},{"secondary-type-ids":[],"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2010-05-18","secondary-types":[],"primary-type":"Album","id":"affe3ada-0a7c-4b47-aaad-d8052aac5dd2","title":"跨時代","disambiguation":""},{"disambiguation":"","title":"不能說的秘密","first-release-date":"2007-08-13","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":["22a628ad-c082-3c4f-b1b6-d41665107b88"],"id":"b8b7446d-3a4a-341e-86d6-313ca45cbb86","primary-type":"Album","secondary-types":["Soundtrack"]},{"secondary-types":[],"primary-type":"Album","id":"bb41418c-24b7-37c2-bfe9-aaf5e571e97b","secondary-type-ids":[],"first-release-date":"2006-09-05","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","disambiguation":"","title":"依然范特西"},{"secondary-types":["Compilation"],"primary-type":"Album","id":"bd40ab7a-a2c7-323f-a4ca-f850321dd9af","secondary-type-ids":["dd2a21e1-0c00-3729-a7a0-de60b84eb5d1"],"first-release-date":"2002-04-01","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","title":"Partners: 周杰倫 方文山聯手創作精選","disambiguation":""},{"secondary-type-ids":["22a628ad-c082-3c4f-b1b6-d41665107b88"],"first-release-date":"2006-01-20","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-types":["Soundtrack"],"primary-type":"Album","id":"c7100151-8359-3ef3-b033-4bf0d0ff85e4","title":"霍元甲","disambiguation":""},{"first-release-date":"2001-12","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"id":"c91314f2-5b6c-39b6-8fd2-74fbc4ad2477","secondary-types":["Live"],"primary-type":"Album","disambiguation":"","title":"Fantasy Plus"},{"disambiguation":"","title":"哎呦, 不錯哦","id":"cb2c53b8-946d-47a5-b7d4-fc01bb25b9e0","primary-type":"Album","secondary-types":[],"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2015-01-13","secondary-type-ids":[]},{"primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"","secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"id":"d42bdd73-b09f-3c30-9952-0a480acfb2bb","secondary-types":["Live"],"primary-type":"Album","title":"Fantasy Show","disambiguation":""},{"secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"first-release-date":"2011-01-25","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-types":["Live"],"primary-type":"Album","id":"d7762703-d3f9-48b7-97fc-58dcf5a7724a","disambiguation":"","title":"2010超時代演唱會"},{"secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"first-release-date":"2008-01-30","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","primary-type":"Album","secondary-types":["Live"],"id":"d87401cf-ad76-3f2a-95dd-631e03b0e04b","title":"2007世界巡迴演唱會","disambiguation":""},{"disambiguation":"","title":"2004無與倫比演唱會","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2004-12-03","secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"id":"dc977ef5-1678-3ed4-bfeb-baf3b2dee17b","secondary-types":["Live"],"primary-type":"Album"},{"title":"雙截棍","disambiguation":"","secondary-type-ids":[],"primary-type-id":"d6038452-8ee0-3f68-affc-2de9a1ede0b9","first-release-date":"2002-04","primary-type":"Single","secondary-types":[],"id":"e9d1657a-ccf9-35c3-b981-acfdc872eca7"},{"title":"The One","disambiguation":"","secondary-type-ids":["6fd474e2-6b58-3102-9d17-d6f7eb7da0a0"],"first-release-date":"2002-10-25","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","secondary-types":["Live"],"primary-type":"Album","id":"eae7f8b6-b73b-3c39-98d2-4938168181cd"},{"id":"f13d05fa-0e8d-3731-be94-2ca5cfaf5dc0","secondary-types":[],"primary-type":"Album","primary-type-id":"f529b476-6e62-324f-b0aa-1f3e33d313fc","first-release-date":"2004-08-03","secondary-type-ids":[],"title":"七里香","disambiguation":""}],"release-group-offset":0},"success":true,"errorMessage":null};
        initAlbumEumerable(json);
    }
);


