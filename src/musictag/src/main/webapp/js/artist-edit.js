$(document).ready(function(){

	var artistEditChart=echarts.init(document.getElementById('artist-edit'));
	
	var curArtistGid = getUuid();
	
	var oldResize = window.onresize; 
	window.onresize=function(){
		if(oldResize){
			oldResize();
		}
		artistEditChart.resize();
	}

	$.get("/musictag/artist/"+curArtistGid+"/artist-edit",
			function(edit){
				
				var x_axis = [];
		
				var y_axis = [];
		
				edit.data.forEach(function(node){
					x_axis.push(node.year);
					y_axis.push(node.count);
				})
				//alert(x_axis);
				//alert(edit.data);
				//for(var i in edit.data){
					//alert("key"+i+"value"+edit.data[i]);
					//x_axis.push(i);
					//y_axis.push(edit.data[i]);
				//}
				//alert(x_axis);
				
				option = {
						title : {
					        text: 'Edit Distribution',
					        subtext: 'Source: MusicBrainz.org',
					        //sublink : 'http://esa.un.org/wpp/Excel-Data/population.htm',
					        left: 'center',
					        top: 'top'
					    },
						color: ['#3398DB'],
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : x_axis,
					            axisTick: {
					                alignWithLabel: true
					            },
					        	
					        }
					    ],
					    yAxis : [
					        {
					        	
					        	name:'Edit Count',
					        	nameLocation:'middle',
					            type : 'value',
					            data:y_axis,
					            nameGap:38,
					            axisLine:{
					        		show:false
					        	}
					        }
					    ],
					    series : [
					        {
					            name:'Edits Num',
					            type:'bar',
					            barWidth: '60%',
					            data:y_axis
					        }
					    ]
					};
					
				artistEditChart.setOption(option);
				if(data.data.nodes.length === 0 ){
					artistRelationChart.hideLoading();
					$('#artist-edit').html('<h2 style="text-align: center">Oops! No Edit found. </h2>');
				}
				
			},'json');
	
	
});