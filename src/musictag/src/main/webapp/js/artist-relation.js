$(document).ready(
		function() {
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
										node.itemStyle = {
											normal : {
												color : '#990033'
											}
										};
										node.symbolSize = 20;
										node.label = {
												normal : {
													show : node.symbolSize > 10,
													textStyle: {
														color: '#990033'
													}
												}
										};
									} else {
										node.itemStyle = null;
										node.symbolSize = Math
												.log(node.symbolSize * 3) * 3;
										node.label = {
												normal : {
													show : node.symbolSize > 10,
												}
										};
									}
									node.value = node.symbolSize;
									node.x = Math.random() * 1000;
									node.y = Math.random() * 500;
								});

						data.data.links.forEach(function(node) {
							node.source = node.source + "";
							node.target = node.target + "";

						});
						option = {
							title : {
								text : 'Artist Relation',
								top : 'top',
								left : 'right'
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
								formatter: function (params, ticket, callback) {
									if(params.dataType === "node"){
									     $.get('/musictag/artist/' + params.data.gid + '/tooltip-info', function (content) {
									         callback(ticket, artistTooltipHtml(content));
									     });
										
									}else if(params.dataType === "edge"){
										if(params.seriesName === "Credit Relation"){
											$.get('/musictag/artist/' + params.data.source + '/', function (content) {
										    	 console.log(params);
										         callback(ticket, cooperationTooltipHtml(content));
										     });
										}
									}
//									console.log(JSON.stringify(params));
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
							} ]
						};

						artistRelationChart.setOption(option);

					}, 'json');

			artistRelationChart.on('click',
					function(params) {
						if (params.data.gid) {
							location.href = '/musictag/artist/'
									+ params.data.gid + '/';
						}
					});
			
			function artistTooltipHtml(content){
				var img = content.data['commons-img'] ? content.data['commons-img'] : window.UrlHelper.defaultArtistCoverUrl;
				var wiki = content.data['wikipedia-extract'] ? content.data['wikipedia-extract'] : '';
				//TODO process wiki
				var html = '<div style="float: left; width: 100px; height: 100px;">' + 
						'<img width="100px" src=' + img + 'style="display: inline;">'+
					'</div>' +
					'<div style="margin-left: 110px; min-width: 200px; max-width: 500px; max-height: 50px"> <h2 stylle"margin-right: 50px">'+content.data.name+'</h2> </div>';
				return html
			}
			
			function cooperationTooltipHtml(content){
				
			}
		});
