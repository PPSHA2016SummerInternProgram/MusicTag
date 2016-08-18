$(document).ready(
		function() {
			initArtistCredit();
			
			function initArtistCredit(){
				var artistRelationChart = echarts.init(document
						.getElementById('artist-relations'));
				artistRelationChart.showLoading();
				var oldResize = window.onresize;
				window.onresize = function() {
					if(oldResize){
						oldResize();
					}
					artistRelationChart.resize();
				}
				var curArtistGid = getUuid();
				$.get("/musictag/artist/" + curArtistGid + "/artist-credit-counts",
						function(data) {
							if(data.data.nodes && data.data.nodes.length > 0){
								artistRelationChart.hideLoading();
								var isFirst = true;
								data.data.nodes
										.forEach(function(node) {
											if (node.gid === curArtistGid) {
												node.symbolSize = 20;
												node.x = 0;
												node.y = 0;
											} else {
												node.itemStyle = null;
												node.symbolSize = Math
														.log(node.symbolSize * 3) * 3;
												if(isFirst){
													node.x = 30;
													node.y = 15;
													isFirst = false;
												}else{
													Math.seed = node.id;
													node.x = Math.seededRandom(30, 5);
													node.y = Math.seededRandom(15, -15);
												}
											}
											node.label = {
													normal : {
														show : node.symbolSize > 10,
													}
											};
											node.value = node.symbolSize;
											
										});
								data.data.links.forEach(function(node) {
									node.source = node.source + "";
									node.target = node.target + "";

								});
								option = {
									title : {
										text : 'Artist Relation',
										subtext: 'Source: musicbrainz.org',
										top : 'top',
										left : 'center',
										right:'center'
									},
									visualMap: {
									    // 不显示 visualMap 组件，只用于明暗度的映射
									    show: false,
									    min: 0,
									    max: 40,
									    inRange: {
									        // 明暗度的范围是 0 到 1
									        colorLightness: [0, 1]
									    }
									},
									tooltip : {
										enterable : true,
										showDelay: 300,
										hideDelay: 500,
										backgroundColor: 'rgba(162,180,186,0.7)',
										formatter: function (params, ticket, callback) {
											if(params.dataType === "node"){
											     $.get('/musictag/artist/' + params.data.gid + '/tooltip-info', function (content) {
											         callback(ticket, artistTooltipHtml(content));
											     });
												
											}else if(params.dataType === "edge"){
												if(params.seriesName === "Credit"){
													$.get('/musictag/artist/' + params.data.source + '/target-artist/' + params.data.target + '/type/credit/cooperations', function (content) {
														callback(ticket, cooperationTooltipHtml(content));
												     });
												}else if(params.seriesName === "Lyrics"){
													$.get('/musictag/artist/' + params.data.source + '/target-artist/' + params.data.target + '/type/lyricist/cooperations', function (content) {
														callback(ticket, cooperationTooltipHtml(content));
												     });
												}else if(params.seriesName === "Composing"){
													$.get('/musictag/artist/' + params.data.source + '/target-artist/' + params.data.target + '/type/composer/cooperations', function (content) {
														callback(ticket, cooperationTooltipHtml(content));
												     });
												}
											}
										    return 'Loading';
										 }
									},
									animationDuration : 1500,
									animationEasingUpdate : 'quinticInOut',
									legend: {
										left: 'right',
										data:['Credit', 'Lyrics', 'Composing']
									},
									series : [ {
										name : 'Credit',
										type : 'graph',
										layout : 'none', // circular
										data : data.data.nodes,
										links : data.data.links,
										roam : true,
										top: 80,
										bottom: '10%',
										left: '50%',
										right: '10%',
										focusNodeAdjacency: true,
										label : {
											normal : {
												position : 'right',
												formatter : '{b}',
												textStyle : {
													color : '#a2b4ba'
												}
											}
										},
										lineStyle : {
											normal : {
												color : 'source',
												curveness : 0.2,
											}
										},
										itemStyle : {
											normal : {
												color : '#337ab7'
											}
										}
									}]
								};

								artistRelationChart.setOption(option);
							}
							
							$.get("/musictag/artist/" + curArtistGid + "/artist-lyricists", function(lyricistData){
								if(lyricistData.data.nodes &&  lyricistData.data.nodes.length > 0){
									artistRelationChart.hideLoading();
									var isFirst = true;
									lyricistData.data.nodes
										.forEach(function(node) {
											if (node.gid === curArtistGid) {
												node.symbolSize = 20;
												node.x = 0;
												node.y = 0;
											} else {
												node.itemStyle = null;
												node.symbolSize = Math
														.log(node.symbolSize * 3) * 3;
												if(isFirst){
													node.x = -50;
													node.y = 25;
													isFirst = false;
												}else{
													Math.seed = node.id;
													node.x = Math.seededRandom(-10, -50);
													node.y = Math.seededRandom(25, 5);
												}
											}
											node.label = {
													normal : {
														show : node.symbolSize > 10,
													}
											};
											node.value = node.symbolSize;
										});
	
									lyricistData.data.links.forEach(function(node) {
										node.source = node.source + "";
										node.target = node.target + "";
			
									});
									
									var lyricistsSeries = {
										name : 'Lyrics',
										type : 'graph',
										layout : 'none', // circular
										data : lyricistData.data.nodes,
										links : lyricistData.data.links,
										roam : true,
										focusNodeAdjacency: true,
										right: '50%',
										left: '10%',
										top: '50%',
										bottom: '10%',
										label : {
											normal : {
												position : 'right',
												formatter : '{b}',
												textStyle : {
													color : '#a2b4ba'
												}
											}
										},
										lineStyle : {
											normal : {
												color : 'source',
												curveness : 0.2,
											}
										}
									};
									option.series.push(lyricistsSeries);
									artistRelationChart.setOption(option);
								}
								$.get("/musictag/artist/" + curArtistGid + "/artist-composers", function(composerData){
									if(composerData.data.nodes &&  composerData.data.nodes.length > 0){
										artistRelationChart.hideLoading();
										var isFirst = true;
										composerData.data.nodes
											.forEach(function(node) {
												if (node.gid === curArtistGid) {
													node.symbolSize = 20;
													node.x = 0;
													node.y = 0;
												} else {
													node.itemStyle = null;
													node.symbolSize = Math
															.log(node.symbolSize * 3) * 3;
													if(isFirst){
														node.x = -50;
														node.y = -25;
														isFirst = false;
													}else{
														Math.seed = node.id;
														node.x = Math.seededRandom(-10, -50);
														node.y = Math.seededRandom(-5, -25);
													}
												}
												node.label = {
														normal : {
															show : node.symbolSize > 10,
														}
												};
												node.value = node.symbolSize;
											});
		
										composerData.data.links.forEach(function(node) {
											node.source = node.source + "";
											node.target = node.target + "";
				
										});
										
										var composerSeries = {
												name : 'Composing',
												type : 'graph',
												layout : 'none', // circular
												data : composerData.data.nodes,
												links : composerData.data.links,
												roam : true,
												left: "10%",
												top: 80,
												right: '50%',
												bottom: '50%',
												focusNodeAdjacency: true,
												label : {
													normal : {
														position : 'right',
														formatter : '{b}',
														textStyle : {
															color : '#a2b4ba'
														}
													}
												},
												lineStyle : {
													normal : {
														color : 'source',
														curveness : 0.2,
													}
												},
											};
											option.series.push(composerSeries);
											artistRelationChart.setOption(option);
									}
									
									/**
									 * if no relation found, display a message.
									 */
									if(data.data.nodes.length === 0 && lyricistData.data.nodes.length === 0 && composerData.data.nodes.length === 0){
										artistRelationChart.hideLoading();
										$('#artist-relations').html('<h2 style="text-align: center">Oops! No relation found. </h2>');
									}
								}, 'json');
								
							}, 'json');
						}, 'json');

				artistRelationChart.on('click',
						function(params) {
							if (params.data.gid) {
								location.href = '/musictag/artist/'
										+ params.data.gid + '/';
							}
						});
			}
			
			function artistTooltipHtml(content){
				var img = content.data['commons-img'] ? content.data['commons-img'] : window.UrlHelper.defaultArtistCoverUrl;
//				var wiki = content.data['wikipedia-extract'] ? content.data['wikipedia-extract'] : '';
				//TODO process wiki
				var html = '<div style="float: left; width: 100px; height: 100px;">' + 
						'<img width="100px" src="' + img + '" style="display: inline;">'+
					'</div>' +
					'<div style="margin-left: 110px; min-width: 200px; max-width: 500px; max-height: 50px"> <h2 stylle"margin-right: 50px">'+content.data.name+'</h2> </div>';
				return html
			}
			
			function cooperationTooltipHtml(content){
				var html = '<div style="width: 500px; word-wrap: break-word; white-space:normal;">';
				if(content.data.recordings && content.data.recordings.length > 0){
					html += buildRecordingTable(content.data.recordings);
				}
				if(content.data.releases && content.data.releases.length > 0){
					html += buildReleaseTable(content.data.releases);
				}
					
				html += '</div>';
				return html;
			}
			
			function buildRecordingTable(recordings){
				var html = '<table class="table"><thead><tr><th> # </th><th> Recording Title </th><th> Length </th></tr></thead><tbody>';
				if(recordings && recordings.length !== 0){
					for(var i = 0; i < recordings.length; i++){
						html += buildRecordingRow(i+1, recordings[i]);
					}
				}
				html += '</tbody></table>';
				return html;
			}
			
			function buildReleaseTable(releases){
				var html = '<table class="table"><thead><tr><th> # </th><th> Release Title </th><th>Country</th><th>Date</th></tr></thead><tbody>';
				if(releases && releases.length !== 0){
					for(var i = 0; i < releases.length; i++){
						html += buildReleaseRow(i, releases[i]);
					}
				}
				html += '</tbody></table>';
				return html;
			}
			
			function buildRecordingRow(index, recording){
				if(!recording.length) {
					recording.length = 0;
				}
				var html = '<tr><td>' + index + '</td><td><a href="/musictag/recording/' + recording.gid + '/">' + recording.name + '</a></td>' +
					'<td>' + OtherHelper.recordingLength(recording.length) + '</td>';
				return html;
			}
			
			function buildReleaseRow(index, release){
				release.country = release.country ? release.country : '';
				var date = '';
				
				if(release.date_year){
					date += release.date_year;
				}else{
					date += '????';
				}
				if(release.date_month){
					date += '-' + release.date_month
				}else{
					date += '-??';
				}
				if(release.date_day){
					date += '-' + release.date_day;
				}else{
					date += '-??';
				}
				
				var html = '<tr><td>' + index + '</td><td><a href="/musictag/release/' + release.gid + '/">' + release.name + '</a></td>' +
					'<td>' + release.country + '</td>' +
					'<td>' + date + '</td>';
				return html;
			}
		});
