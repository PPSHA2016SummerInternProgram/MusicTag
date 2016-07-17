;(function($, window, undefined){
    'use strict';

    window.Enumerable = function(container, data, template) {
        container = $(container);
        for( var i = 0; i < data.length; i++) {
            $(template(data[i])).appendTo(container);
        }
    }

})(jQuery, window);
