$(document).ready(
		function() {
			initArtistCredit();
			
			function initArtistCredit(){
				var artistRelationChart = echarts.init(document
						.getElementById('artist-relations'));
				artistRelationChart.showLoading();
				var curArtistGid = getUuid();
				$.get("/musictag/artist/" + curArtistGid + "/artist-credit-counts",
						function(data) {
							artistRelationChart.hideLoading();
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
											Math.seed = node.id;
											node.x = Math.seededRandom(300, 0);
											node.y = Math.seededRandom(150, -150);
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
											if(params.seriesName === "Credit Relation"){
												$.get('/musictag/artist/' + params.data.source + '/target-artist/' + params.data.target + '/type/credit/cooperations', function (content) {
													callback(ticket, cooperationTooltipHtml(content));
											     });
											}else if(params.seriesName === "Lyricist Relation"){
												$.get('/musictag/artist/' + params.data.source + '/target-artist/' + params.data.target + '/type/lyricist/cooperations', function (content) {
													callback(ticket, cooperationTooltipHtml(content));
											     });
											}else if(params.seriesName === "Composer Relation"){
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
								series : [ {
									name : 'Credit Relation',
									type : 'graph',
									layout : 'none', // circular
									data : data.data.nodes,
									links : data.data.links,
									roam : true,
									top: '0%',
									bottom: '0%',
									left: '50%',
									right: '0%',
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
											curveness : 0.3,
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
							
							$.get("/musictag/artist/" + curArtistGid + "/artist-lyricists", function(lyricistData){
								lyricistData.data.nodes
									.forEach(function(node) {
										if (node.gid === curArtistGid) {
											node.symbolSize = 20;
											node.x = 0;
											node.y = 0;
										} else {
											node.itemStyle = null;
											node.symbolSize = Math
													.log(node.symbolSize * 3) * 10;
											Math.seed = node.id;
											node.x = Math.seededRandom(0, -500);
											node.y = Math.seededRandom(250, 0);
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
									name : 'Lyricist Relation',
									type : 'graph',
									layout : 'none', // circular
									data : lyricistData.data.nodes,
									links : lyricistData.data.links,
									roam : true,
									focusNodeAdjacency: true,
									right: '50%',
									left: '0%',
									top: '50%',
									bottom: '0%',
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
											curveness : 0.3,
										}
									},
								};
								option.series.push(lyricistsSeries);
								artistRelationChart.setOption(option);

/*								
								lyricistData.data.links.forEach(function(link){
									link.lineStyle = {
											normal : {
												color : 'source',
												curveness : 1,
											}	
									}
								});
								var newdata = data.data.nodes.concat(lyricistData.data.nodes);
								var newlinks = data.data.links.concat(lyricistData.data.links);
								console.log(JSON.stringify(lyricistData.data.links));
								console.log(JSON.stringify(data.data.nodes));
								console.log(JSON.stringify(newdata));
								console.log(JSON.stringify(newlinks));
								artistRelationChart.setOption({
									series: [{
										name: 'Credit Relation',
										links: newlinks
									}]
								});
*/								
								$.get("/musictag/artist/" + curArtistGid + "/artist-composers", function(composerData){
									composerData.data.nodes
										.forEach(function(node) {
											if (node.gid === curArtistGid) {
												node.symbolSize = 20;
												node.x = 0;
												node.y = 0;
											} else {
												node.itemStyle = null;
												node.symbolSize = Math
														.log(node.symbolSize * 3) * 10;
												Math.seed = node.id;
												node.x = Math.seededRandom(-100, -500);
												node.y = Math.seededRandom(-50, -250);
												
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
											name : 'Composer Relation',
											type : 'graph',
											layout : 'none', // circular
											data : composerData.data.nodes,
											links : composerData.data.links,
											roam : true,
											left: "0%",
											top: '0%',
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
													curveness : 0.3,
												}
											},
										};
										option.series.push(composerSeries);
										artistRelationChart.setOption(option);
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
						'<img width="100px" src=' + img + 'style="display: inline;">'+
					'</div>' +
					'<div style="margin-left: 110px; min-width: 200px; max-width: 500px; max-height: 50px"> <h2 stylle"margin-right: 50px">'+content.data.name+'</h2> </div>';
				return html
			}
			
			function cooperationTooltipHtml(content){
				var html = '<div style="width: 500px; word-wrap: break-word; white-space:normal;">' + 
					buildRecordingTable(content.data.recordings) +
					buildReleaseTable(content.data.releases) +
					'</div>';
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
