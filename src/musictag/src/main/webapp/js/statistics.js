$(function() {

	showStatistics('artist-listeners');
	showStatistics('artist-playcount');
	showStatistics('release-listeners');
	showStatistics('release-playcount');
	showStatistics('recording-listeners');
	showStatistics('recording-playcount');

});
function showStatistics(type) {
	$
			.getJSON(
					ContextPath + '/statistics/' + type + '.ajax',
					function(response) {
						data = response.data.data;

						$('#' + type)
								.highcharts(
										{
											chart : {
												zoomType : 'x'
											},
											title : {
												text : type
											},
											subtitle : {
												text : document.ontouchstart === undefined ? 'Click and drag in the plot area to zoom in'
														: 'Pinch the chart to zoom in'
											},
											xAxis : {
												type : 'int'
											},
											yAxis : {
												title : {
													text : 'Exchange rate'
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
																[
																		0,
																		Highcharts
																				.getOptions().colors[0] ],
																[
																		1,
																		Highcharts
																				.Color(
																						Highcharts
																								.getOptions().colors[0])
																				.setOpacity(
																						0)
																				.get(
																						'rgba') ] ]
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

											series : [ {
												type : 'area',
												name : type,
												data : data
											} ]
										});
					});
}