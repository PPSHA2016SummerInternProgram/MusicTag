;(function($, window, undefined){
    'use strict';

    window.Paginator = function(enumerable, customTemplate) {
        // turn into jquery object
        enumerable = $(enumerable);
        var pagination = $(enumerable.data('pagination'));

        var totalAmount = enumerable.children().length;
        var perPage = enumerable.data('per-page') || 10;
        var pageCnt = Math.ceil(totalAmount/perPage);
        //var curPageClass = enumerable.data('cur-page-class') || 'active';

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

        var turnTo = function(enumerable, index) {
            enumerable = $(enumerable);
            var pagination = $(enumerable.data('pagination'));
            var perPage = enumerable.data('per-page');
            var pageCnt = Math.ceil(totalAmount/perPage);
            var curPageClass = enumerable.data('cur-page-class') || 'active';

            enumerable.children().hide();

            enumerable.children().slice(index * perPage, (index + 1) * perPage).fadeIn(1000);

            pagination.find('[data-previous-page], [data-next-page]').removeClass('disabled');
            if(index == 0) pagination.find('[data-previous-page]').addClass('disabled');
            if(index == pageCnt - 1 ) pagination.find('[data-next-page]').addClass('disabled');

            pagination.find('.' + curPageClass).removeClass(curPageClass);
            pagination.find('[data-page="' + (index + 1) + '"]').addClass(curPageClass);
        };

        // generate pagination
        pagination.html(template(pageCnt));

        turnTo(enumerable, 0);

        // bind events
        pagination.on('click', '[data-page]', function(e) {
            e.preventDefault();
            if(!$(this).hasClass('active')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var index = $(this).data('page') - 1;

                turnTo(enumerable, index);
            }
        });

        pagination.on('click', '[data-previous-page]', function(e) {
            e.preventDefault();
            if(!$(this).hasClass('disabled')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var curPage = pagination.find('.active').data('page');

                turnTo(enumerable, curPage - 2);
            }
        });

        pagination.on('click', '[data-next-page]', function(e) {
            e.preventDefault();
            if(!$(this).hasClass('disabled')) {
                pagination = $(e.delegateTarget);
                enumerable = $(pagination.data('enumerable'));
                var curPage = pagination.find('.active').data('page');

                turnTo(enumerable, curPage);
            }

        });

    };

})(jQuery, window);
