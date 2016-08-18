$(document).ready(function(){
	
	var artistAreaChart = echarts.init(document.getElementById('artist-area'));
	artistAreaChart.setOption({
	    series: [{
	        type: 'map',
	        map: 'world'
	    }]
	});
	var curArtistGid = getUuid();
		$.get("/musictag/artist/"+curArtistGid+"/artist-areas",
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
					data.data.push({name:"China",value:count_china});
					
					if(data.data.length < 1){
						$('#artist-area').hide()
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
					        trigger: 'item',
					        formatter : function (params) {
					            var value = (params.value + '').split('.');
					            //value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
					            //       + '.' + value[1];
					            value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
					            //alert(value);
					            if(value=='NaN'){
					            	return params.name + ' : ' + '0';
					            }else{return params.name + ' : ' + value;}
					        
					         
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
					
					artistAreaChart.setOption(option);

					

	},'json');
			
});