$(document).ready(function(){
	
	list_image=[];
	
	
	var artistAreaChart = echarts.init(document.getElementById('artist-area'));
	//alert("*******");
	

	
	
	var oldResize = window.onresize;
	window.onresize = function() {
		if(oldResize){
			oldResize();
		}
		artistAreaChart.resize();
	}
	

	var curArtistGid = getUuid();
		$.get("/musictag/artist/"+curArtistGid+"/artist-areas-count",
				function(data){
					var count_all=0;
					var count_china=0;
					data.data.forEach(function(node){
						//node.value=node.count;
						count_all=count_all+node.count;
						node.value=node.count;
						if(node.name === "United States"){
							//alert("OOOOOO");
							node.name='United States of America';
						}else if(node.name==="Hong Kong"||node.name==="Taiwan"||node.name==="China"){
							count_china=count_china+node.count;
						}
					})
					if(count_china>0){
						data.data.push({name:"China",value:count_china});
					}

					
					
					
					///$.get('/musictag/artist/China/release/032eee52-4ce5-49f6-a60e-44b00c778403/image',function(data1){
						
					//	list_image.push(data1.data.images[0].image);
						
            		//},'json');
					
					option = {
					    title : {
					        text: 'Release Areas',
					        //subtext: 'from United Nations, Total population, both sexes combined, as of 1 July (thousands)',
					        //sublink : 'http://esa.un.org/wpp/Excel-Data/population.htm',
					        left: 'center',
					        top: 'top'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter : function (params, ticket, callback) {
					            var value = (params.value + '').split('.');
					            //value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
					            //       + '.' + value[1];
					            value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
					            //alert(value);
					            
					        
					            
					            if(params.name ==='United States of America'){
					            	 $.get('/musictag/artist/'+curArtistGid+'/'+'United States', function (content) {
					            		 
											callback(ticket, artistHtml(content));

											//data.data.forEach(function(node){
							            	//	$.get('/musictag/artist/'+'United States'+'/release/'+node.gid+'/image',function(data){
							            			//url:data.data.images[0].image
							            	//		list_image.push(data.data.images[0].image);
							            			
							            	//	},'json');
							            		
							            	//});

											
									     });
					            }else{
					            
					            	$.get('/musictag/artist/'+curArtistGid+'/'+params.name, function (content) {
									
					            	callback(ticket, artistHtml(content));

					            	
							     });
					            }
					            
					            
					            if(value==='NaN'){
					            	return params.name + ' : ' + '0';
					            }else{return params.name + ' : ' + value;}
					        
					         //return 'Loading';
					        }
					    },
					    toolbox: {
					        show : false,
					        orient : 'vertical',
					        left: 'right',
					        top: 'center',
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    visualMap: {
					        show:true,
					    	type: 'continuous',
					        min: 0,
					        max: 50,
					        text:['High','Low'],
					        realtime: false,
					        calculable : true,
					        inRange: {
				                color: ['lightskyblue','yellow', 'orangered','red']
				            }
					    },
					    series : [
					        {
					            name: 'World Population (2010)',
					            type: 'map',
					            mapType: 'world',
					            roam: true,
					            itemStyle:{
					                emphasis:{label:{show:true}}
					            },
					            data:data.data
					        }
					    ]
					};
					
					if(data.data.length === 0 ){
						artistAreaChart.hideLoading();
						$('#artist-area').html('<h2 style="text-align: center">Oops! No Areas found. </h2>');
					}
					
					artistAreaChart.setOption(option);

					if(data.data.length>0){
						artistAreaChart.setOption({
						    series: [{
						        type: 'map',
						        map: 'world'
						    }]
						});
					}

	},'json');
		
	
		function artistHtml(content){
						
			var html = '<div>';						
			content.data.forEach(function(node){
				//html += '<div>' + JSON.stringify(node) + '</div>';
				html += '<div>' + node.area+','+node.name + '</div>';
			});
			
			html += '</div>';
			return html;
		}	
		
		
		
});