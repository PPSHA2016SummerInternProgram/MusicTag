;(function($, window, undefined){
    'use strict';

    window.Paginator = function(enumerable, updatePage, customTemplate) {

        // pagination template
        var template = function(amount) {
            var pages = '';
            for(var i = 0; i < amount; ++i) {
                pages += TagBuilder('li', {data: {page: i+1}},
                    TagBuilder('a', {href: '#'}, i+1)
                );
            }
            return '<nav>' +
              '<ul class="pagination">' +
                '<li data-previous-page="">' +
                  '<a href="#" aria-label="Previous">' +
                    '<span aria-hidden="true">&laquo;</span>' +
                  '</a>' +
                '</li>' +
                    pages +
                '<li data-next-page="">' +
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

            var perPage = enumerable.data('per-page') || 10;
            var orderBy = enumerable.data('order-by') || '';
            var direction = enumerable.data('direction') || '';
            var curPageClass = enumerable.data('cur-page-class') || 'active';

            // update page
            enumerable.hide();
            var pageCnt = Math.ceil(updatePage(index, perPage, orderBy, direction) / perPage);
            enumerable.fadeIn(1000);

            // generate pagination
            pagination.html(template(pageCnt));
            if(index == 0) pagination.find('[data-previous-page]').addClass('disabled');
            if(index == pageCnt - 1 ) pagination.find('[data-next-page]').addClass('disabled');
            pagination.find('[data-page="' + (index + 1) + '"]').addClass(curPageClass);
        };

        window.Paginator.turnTo = turnTo;

        turnTo(enumerable, 0);

        var pagination = $(enumerable.data('pagination'));
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
