function getStatisticsDataFromServer(entityType, hotType, id, gid) {
	var subUrl = entityType + '-' + hotType;
	var url = ContextPath + '/statistics/' + subUrl + '/' + gid + '.ajax';
	var args = {
		id : id,
		title : id,
		yText : entityType,
		name : subUrl,
	}
	sendAjax(url, null, drawCharts, args);
}

var seriesCache = [];

function operate(amount) {
	return parseInt(Math.log(amount) * 100);
}

function drawCharts(response, args) {
	var id = args['id'];
	var title = args['title'];
	var yText = args['yText'];
	var name = args['name'];
	var data = response['data'];
	var total = getValue(data, 'total');
	var amount = getValue(data, 'amount');
	var rank = getValue(data, 'rank');
	data = getValue(data, 'data');
	var mark = operate(amount);

	seriesCache = [];
	seriesCache.push({
		type : 'area',
		name : name,
		data : data,
	});
	var config = {
		chart : {
			zoomType : 'x'
		},
		title : {
			text : title
		},
		subtitle : {
			text : 'Click and drag in the plot area to zoom in'
		},
		xAxis : {
			type : 'int',
			plotLines : [ {
				color : 'red',
				value : mark,
				width : 1,
			} ],
		},
		yAxis : {
			title : {
				text : yText
			}
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			area : {
				fillColor : {
					linearGradient : {
						x1 : 0,
						y1 : 0,
						x2 : 0,
						y2 : 1
					},
					stops : [
							[ 0, '#000000' ],
							[
									1,
									Highcharts.Color('#FFFFFF').setOpacity(0)
											.get('rgba') ], ]
				},
				marker : {
					radius : 2
				},
				lineWidth : 1,
				states : {
					hover : {
						lineWidth : 1
					}
				},
				threshold : null
			}
		},

		colors : [ '#333333' ],

		series : seriesCache
	}
	$('#' + id).highcharts(config);
}
