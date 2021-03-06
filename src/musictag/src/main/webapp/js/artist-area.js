$(document).ready(function(){
	
	list_image=[];
	
	
	var artistAreaChart = echarts.init(document.getElementById('artist-area'));
	
	
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
							node.value=0
						}
					})
					if(count_china>0){
						data.data.push({name:"China",value:count_china});
					}


					
					option = {
					    title : {
					        text: 'Release Areas',
					        //subtext: 'from United Nations, Total population, both sexes combined, as of 1 July (thousands)',
					        //sublink : 'http://esa.un.org/wpp/Excel-Data/population.htm',
					        left: 'center',
					        top: 'top'
					    },
					    tooltip : {
					    	
					    	enterable : true,
							showDelay: 300,
							hideDelay: 500,
					        trigger: 'item',
					        formatter : function (params, ticket, callback) {
					            var value = (params.value + '').split('.');
					            //value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
					            //       + '.' + value[1];
					            value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
					            //alert(value);
					            
					        
					            
					            if(params.name ==='United States of America'){
					            	 $.get('/musictag/artist/'+curArtistGid+'/'+'United States', function (content) {
					            		 
											callback(ticket, artistHtml(content,params.name,value));

											
									     });
					            }else{
					            
					            	$.get('/musictag/artist/'+curArtistGid+'/'+params.name, function (content) {
									
					            	callback(ticket, artistHtml(content,params.name,value));
					            	
							     });
					            }

					         return 'Loading';
					        }
					    },
					    //animationDuration : 500,
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
					        max: 20,
					        text:['High','Low'],
					        realtime: false,
					        calculable : true,
					        inRange: {
				                color: ['#EEEEEE','pink','red']
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
		
	
		function artistHtml(content,name,value){
						
			var times=0;
			var html = '<div>';	
			if(value==='NaN'){
				html+='<div>'+name+':'+'0'+'</div>';
	        }else{
	        	html+='<div>'+name+':'+value+'</div>';
	        } 
			html+='</div>';
			
			if(content.data.length>0){
				
			html += '<div style="width: 500px; word-wrap: break-word; white-space:normal;">';	
			html += '<table class="table"><thead><tr><th style="color: white;"> # </th><th style="color: white;"> Release Title </th><th style="color: white;">Country</th><th style="color: white;">Date</th></tr></thead><tbody>';

			content.data.forEach(function(node){

				//html += '<div>' + JSON.stringify(node) + '</div>';
				//html += '<tr>'+'<td>' + node.area+':'+'</td>'+'<td>'+'&lt;'+node.name + '&gt;'+'</td>'+'</tr>';
				times++;
				html += '<tr><td style="color: white;font-size: small;">' + times + '</td><td style="color: white;font-size: small;"><a style="color:white;text-decoration:underline;" href="/musictag/release/' + node.gid + '/">' + node.name + '</a></td>' +
				'<td style="color: white;font-size: small;">' + node.area + '</td>' +
				'<td style="color: white;font-size: small;">' + node.year + '</td>';
			});
			html += '</tbody></table>';
			html += '</div>';
			}
			return html;
		}	
		
		
		
		
		
});