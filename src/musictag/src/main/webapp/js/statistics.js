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

		series : seriesCache,
		
	}
	$('#' + id).highcharts(config);
	
	var callback = args['callback'];
	if(callback){
		callback(rank, total);
	}
}

window.Statistics = {
    drawArtistReleaseDist: function(container, gid) {
        var url = ContextPath + '/statistics/artist/' + gid + '/release-dist';
        $.getJSON(url, function(json){
            if(json.success) {
                window.Statistics._drawArtistReleaseDist(json.data, container);
            }
        });
    },

    _drawArtistReleaseDist: function (data, container) {
        var categories = [], release_cnts = [], recording_cnts = [];
        for(var i = 0; i < data.length; ++i) {
            if(data[i].date_year === -1) continue;
            categories.push(data[i].date_year);
            release_cnts.push(data[i].release_count);
            recording_cnts.push(data[i].recording_count);
        }
        var config = {
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'Artist Release and Recording Yearly Distribution'
            },
            subtitle: {
                text: 'Source: MusicBrainz.org'
            },
            xAxis: [{
                categories: categories,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'Recording Count',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: 'Release Count',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                labels: {
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                x: 120,
                verticalAlign: 'top',
                y: 100,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            series: [{
                name: 'Release',
                type: 'column',
                yAxis: 1,
                data: release_cnts,

            }, {
                name: 'Recording',
                type: 'spline',
                data: recording_cnts,
            }]
        };

        container.highcharts(config);
    }
};
