;(function($, window, undefined){
    'use strict';

    window.Paginator = function(enumerable, url, updatePage, customTemplate) {
        enumerable = $(enumerable);
        // prepare config
        var perPageParam = enumerable.data('per-page-param') || 'per-page';
        var pageParam = enumerable.data('page-param') || 'cur-page';
        var orderByParam = enumerable.data('order-by-param') || 'order-by';
        var directionParam = enumerable.data('direction-param') || 'direction';

        var curPageClass = enumerable.data('cur-page-class') || 'active';
        var autoTurn = enumerable.data('auto-turn');
        if(autoTurn === undefined) autoTurn = true;

        // pagination template
        var template = function(curPage, pageCnt, window) {
            var ellipsis = TagBuilder('li', null, TagBuilder('a', null, TagBuilder('span', null, '...')));
            var pageTemplate = function(index, isActive) {
                return TagBuilder('li', $.extend({data: {page: index+1}}, isActive ? {class: curPageClass} : {}),
                    TagBuilder('a', {href: '#'}, index+1)
                );
            };
            var left = '', right = '';
            var bg, end, last = pageCnt - 1;
            if( window < pageCnt ) {
                bg = curPage - (window % 2 === 0 ? Math.floor(window / 2) - 1 : Math.floor(window / 2));
                end = bg + window - 1;
                if( bg < 0 ) {
                    end += -bg;
                    bg = 0;
                } else if (bg > 0) {
                    left += pageTemplate(0, curPage === 0 ) + ellipsis;
                }

                if( end > last) {
                    bg -= end - last;
                    end = last;
                } else if( end < last ){
                    right += ellipsis + pageTemplate(last, curPage === last);
                }
            } else {
               bg = 0; end = last;
            }

            var pages = '';
            for(var i = bg; i <= end; ++i) {
                pages += pageTemplate(i, i === curPage);
            }

            return '<nav>' +
              '<ul class="pagination">' +
                '<li data-previous-page="" ' + (curPage === 0 ? 'class="disabled"' : '') + '>' +
                  '<a href="#" aria-label="Previous">' +
                    '<span aria-hidden="true">&laquo;</span>' +
                  '</a>' +
                '</li>' +
                    left + pages + right +
                '<li data-next-page="" ' + (curPage === last ? 'class="disabled"' : '') + '>' +
                  '<a href="#" aria-label="Next">' +
                    '<span aria-hidden="true">&raquo;</span>' +
                  '</a>' +
                '</li>' +
              '</ul>' +
            '</nav>';
        };

        if(customTemplate instanceof Function) template = customTemplate;

        // TODO: redundantly redefine
        var turnTo = function(enumerable, index) {
            // turn into jquery object
            enumerable = $(enumerable);
            var pagination = $(enumerable.data('pagination'));

            var window = enumerable.data('window') || 5;

            var params = {};
            params[pageParam] = index;
            params[perPageParam] = enumerable.data(perPageParam) || 10;
            params[orderByParam] = enumerable.data(orderByParam) || '';
            params[directionParam] = enumerable.data(directionParam) || '';

            $.getJSON(url, params, function(json){
                // update page
                enumerable.hide();
                var pageCnt = Math.ceil(updatePage(json) / params[perPageParam]);
                enumerable.fadeIn(1000);

                // generate pagination
                pagination.html(template(index, pageCnt, window));
            });
        };

        window.Paginator.turnTo = turnTo;

        if(autoTurn === true) turnTo(enumerable, 0);

        var pagination = $(enumerable.data('pagination'));
        pagination.data('enumerable', '#' + enumerable.attr('id'));
        // bind events
        pagination.on('click', '[data-page]', function(e) {
            if(!$(this).hasClass('active')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var index = $(this).data('page') - 1;

                turnTo(enumerable, index);
            }
            e.preventDefault();
        });

        pagination.on('click', '[data-previous-page]', function(e) {
            if(!$(this).hasClass('disabled')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var curPage = pagination.find('.active').data('page');

                turnTo(enumerable, curPage - 2);
            }
            e.preventDefault();
        });

        pagination.on('click', '[data-next-page]', function(e) {
            if(!$(this).hasClass('disabled')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var curPage = pagination.find('.active').data('page');

                turnTo(enumerable, curPage);
            }
            e.preventDefault();
        });
    };

})(jQuery, window);
