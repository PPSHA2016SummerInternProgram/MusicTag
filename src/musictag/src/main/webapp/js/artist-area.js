$(document).ready(function(){
	
	var artistAreaChart = echarts.init(document.getElementById('artist-area'));
	artistAreaChart.setOption({
	    series: [{
	        type: 'map',
	        map: 'world'
	    }]
	});
	var oldResize = window.onresize;
	window.onresize = function() {
		if(oldResize){
			oldResize();
		}
		artistAreaChart.resize();
	}
	var curArtistGid = getUuid();
		$.get("/musictag/artist/"+curArtistGid+"/artist-areas",
				function(data){
					data.data.forEach(function(node){
						node.value=1000000;
					})
					
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
					            value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
					                    + '.' + value[1];
					            //return params.seriesName + '<br/>' + params.name + ' : ' + value;
					            return params.name;
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
					        show:false,
					    	type: 'continuous',
					        min: 0,
					        max: 1000000,
					        text:['High','Low'],
					        realtime: false,
					        calculable : true,
					        color: ['orangered','yellow','lightskyblue']
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