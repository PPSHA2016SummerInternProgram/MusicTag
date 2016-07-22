;(function($, window, undefined){
    'use strict';

    // TODO: lack html escape
    window.TagBuilder = function(tag, attrs, content){
        if(content === undefined || content === null) content = '';
        if(!(attrs instanceof Object)) attrs = {};
        var dataAttrs = {};
        if( attrs.data instanceof Object ) {
            dataAttrs = attrs.data;
            delete attrs.data;
        }

        var delimiter = ' ';

        var buildPair = function(key, val) { return key + '="' + val + '"';};
        var buildAttr = function(key, map) {
            var attrs = map[key];
            if( attrs !== undefined) {
                var val = '';
                if (attrs instanceof Array) {
                    val += attrs.join(delimiter);
                } else {
                    val += attrs;
                }

                return buildPair(key, val);
            }
        };

        var attrStr = Object.keys(attrs).map(function(key){ return buildAttr(key, attrs);}).join(delimiter);
        attrStr += ' ' + Object.keys(dataAttrs).map(function(key){ return 'data-' + buildAttr(key, dataAttrs);}).join(delimiter);

        return '<' + tag + ' ' + attrStr + '>' +
            content +
            '</' + tag + '>';
    };

})(jQuery, window);