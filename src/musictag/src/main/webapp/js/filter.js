;(function ($, window, undefined) {
  window.Filter = {
    serializeFilter: function(field, items) {
      if(items.length === 0) return null;
      else {
        var html = 'fq=' + field + ':("' + items[0] + '"';
        for(var i = 1; i < items.length; i++) {
          html += ' OR "' + items[i] + '"';
        }
        return html + ')';
      }
    },

    serializeRangesWithGap: function (field, ranges, gap) {
      if(ranges.length === 0 ) return null;
      else {
        var html = 'fq=' + field + ':([' + ranges[0] + ' TO ' + (ranges[0] + gap) + '}' ;
        for (var i = 1; i < ranges.length; i++) {
          html += ' OR [' + ranges[i] + ' TO ' + (ranges[i] + gap) + '}';
        }
        return html + ')'
      }
    },
      
    serializeRange: function (field, begin, end) {
      var beginStr = begin.toString();
      var endStr = end.toString();
      if( !beginStr || !endStr) {
        return null;
      } else {
        return 'fq=' + field + ':([' + beginStr + ' TO ' + endStr + '})' ;
      }
    },

    serializeFilterGroup: function (filter) {
      var field = filter.data('filter-field');
      var gap = filter.data('filter-gap');
      var items_or_ranges = filter.find('.selected').map(function(index, item){
        return $(item).data('filter-item');
      }).get();
      if( gap ) {
        return window.Filter.serializeRangesWithGap(field, items_or_ranges, gap);
      } else {
        return window.Filter.serializeFilter(field, items_or_ranges);
      }
    },

    serializeSorter: function(sorter) {
      var selected = sorter.find('.up.selected, .down.selected');
      if(selected.length === 0) {
        return null;
      } else {
        return 'sort=' + selected.data('sorter') + ' ' + (selected.hasClass('up') ? 'asc' : 'desc');
      }
    },

    serializePartition: function (partition) {
      return partition.find('.partition-input').map( function(index, elem){
        var e = $(elem);
        var begin = parseInt(e.find('.begin').val());
        var end = parseInt(e.find('.end').val());
        if( !!begin && !!end && begin >=0 && begin <= end) {
          return window.Filter.serializeRange(e.data('partition-field'), 100 - end, 100 - begin);
        } else {
          return null;
        }
      }).get().join('&');
    },



    buildFilter: function (facet_fields, facet_ranges) {
      var transform = function (str) {
        var reg = /\b(\w)|(_)/g;
        return str.replace(reg, function(m){
          return m === '_' ? ' ' : m.toUpperCase();
        });
      };

      var buildFilterItems = function (item, count, gap) {
        var items_html = '';
        if (count !== 0) {
          items_html += TagBuilder('a', {
            title: item + ': ' + count, class: 'item',
            data: {toggle: 'popover', trigger: 'hover', content: count, selectable: '', 'filter-item': item}
          }, gap ? item + '~' + (parseInt(item) + gap) : item);
        }
        return items_html;
      };

      var buildFilterRow = function (field, items_html, gap) {
        return TagBuilder('div', {class: 'filter row', data: $.extend({'filter-field': field}, gap ? {'filter-gap': gap} : {})},
            TagBuilder('div', {class: 'col-md-1'}, TagBuilder('span', {class: 'field' }, transform(field))) +
            TagBuilder('div', {class: 'item-group col-md-11'}, items_html)
        );
      };

      var html = '';

      for(var index in arguments) {
        if(arguments.hasOwnProperty(index)) {
          var arr = arguments[index];
          for (var field in arr) {
            if(arr.hasOwnProperty(field)) {
              var items = arr[field].counts || arr[field];
              var items_html = '';
              for (var i = 0; i < items.length; i += 2) {
                items_html += buildFilterItems(items[i], items[i + 1], arr[field].gap)
              }
              html += buildFilterRow(field, items_html, arr[field].gap);
            }
          }
        }
      }

      return html;
    }


  };
})(jQuery, window);