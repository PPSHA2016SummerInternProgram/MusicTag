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
							    // 映射的最小值为 80
							    min: 0,
							    // 映射的最大值为 600
							    max: 40,
							    inRange: {
							        // 明暗度的范围是 0 到 1
							        colorLightness: [0, 1]
							    }
							},
							tooltip : {},
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
		});
