function drawDistribution(elementId, type, title) {
	var url = 'distribution/' + type;
	var args = {
			id : elementId,
			title : title ? title : type,
			yText: 'Artist Amount',
			name : 'name',
			callback : null,
	}
	sendAjax(url, null, receivedDistribution, args);
}

function receivedDistribution(response, args) {
	var distribution = getValue(response, 'data', 'distribution');
	var rank = getValue(response, 'data', 'rank');
	var mark = rank['xAxis'];
	var arr = getValue(distribution, 'data');
	var max = arr[arr.length-1][0];
	mark = max < mark ? max : mark;
	mark = mark < 0 ? 0 : mark;
	var config = chartConfig(args['title'], mark, args['yText']);
	config['series'] = [{
		data : getValue(distribution, 'data'),
	}]
	$('#' + args['id']).highcharts(config);
}

function getRadarDataFromServer(elementId, callback) {
	var url = 'distribution/scores/';
	var args = {
		callback : callback,
		elementId : elementId,
	}
	sendAjax(url, null, drawRadar, args);
}

function radarConfig() {
	return {
		chart : {
			polar : true,
			type : 'line',
			backgroundColor : 'rgba(0,0,0,0)'
		},
		title : {
			text : null,
		},
		pane : {
			size : '100%'
		},
		xAxis : {
			categories : [],
			tickmarkPlacement : 'on',
			lineWidth : 0,
			labels : {
				enabled : false
			}
		},

		yAxis : {
			gridLineInterpolation : 'polygon',
			lineWidth : 0,
			min : 0,
			max : 100
		},

		tooltip : {
			shared : true,
			pointFormat : '<span style="color:{series.color}">{series.name}: <b>{point.y:,.0f}</b><br/>'
		},

		legend : {
			align : 'right',
			verticalAlign : 'top',
			y : 70,
			layout : 'vertical'
		},

		series : [],

		credits : {
			enabled : false
		}

	};
}

function drawRadar(response, args) {
	var elementId = args['elementId'];
	var config = radarConfig();
	var data = [];
	var categories = [];
	var items = getValue(response, 'data');

	var scores = {
		'Relationship' : getScore(items, 'contacts_amount'),
		'Popularity' : (getScore(items, 'edit_amount')
				+ getScore(items, 'listener_amount') + getScore(items,
				'play_amount')) / 3,
		'Influence' : getScore(items, 'country_amount'),
		'Productivity' : (getScore(items, 'recording_amount') + getScore(items,
				'release_amount')) / 2,
		'Active Span' : getScore(items, 'active_years'),
	};

	for ( var type in scores) {
		if (scores.hasOwnProperty(type)) {
			categories.push(type);
			data.push(getValue(scores, type))
		}
	}
	config['xAxis']['categories'] = categories;
	config['series'] = [ {
		color : '#B90000',
		'data' : data,
		pointPlacement : 'on',
		showInLegend : false,
		name : basicInfo ? getValue(basicInfo, 'name') : ''
	} ];
	config['pane']['size'] = '60%';

	$('#' + elementId).css('width', 150);
	$('#' + elementId).css('height', 150);
	$('#' + elementId).css('margin-top', -60);

	$('#' + elementId).highcharts(config);
	if (args['callback']) {
		args['callback']();
	}
}

function getScore(items, type) {
	var score = getValue(items, type, 'rank', 'score');
	return score ? score : 0;
}

function getStatisticsDataFromServer(entityType, hotType, id, gid, callback) {
	var subUrl = entityType + '-' + hotType;
	var url = ContextPath + '/statistics/' + subUrl + '/' + gid + '.ajax';
	var args = {
		id : id,
		title : id,
		yText : 'Amount of ' + entityType,
		name : subUrl,
		callback : callback,
	}
	sendAjax(url, null, drawCharts, args);
}

var seriesCache = [];

function operate(amount) {
	return parseInt(Math.log(amount));
}

function chartConfig(title, mark, yText) {
	var config = {
		chart : {
			zoomType : 'x'
		},
		title : {
			text : title
		},
		subtitle : {
//			text : 'Click and drag in the plot area to zoom in',
			text : null,
		},
		xAxis : {
			type : 'int',
			plotLines : [ {
				color : '#dd4b39',
				value : mark,
				width : 1,
			} ],
			title : {
				text : 'Score'
			}
		},
		yAxis : {
			title : {
				text : yText
			},
			min : 0
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
							[ 0, Highcharts.getOptions().colors[0] ],
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

		credits : {
			enabled : false
		}

	}
	return config;
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

	var config = chartConfig(title, mark, yText);
	config['series'] = seriesCache;

	$('#' + id).highcharts(config);

	var callback = args['callback'];
	if (callback) {
		callback(rank, total);
	}
}
