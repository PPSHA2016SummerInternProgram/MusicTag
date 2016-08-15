$(function() {
	getDistributionFromServer();
});

function getDistributionFromServer() {
	var url = ContextPath + '/statistics/distribution';
	sendAjax(url, null, receivedDistribution);
}

function receivedDistribution(response) {
	var distributions = getValue(response, 'data', 'distribution');
	for (var i = 0; i < distributions.length; i++) {
		var distribution = distributions[i];
		var type = getValue(distribution, 'type');
		var description = getValue(distribution, 'description');
		var data = getValue(distribution, 'data');
		var id = type + ' (' + description + ')';
		var config = chartConfig(id, 0, 'Artist Amount');

		var series = [];
		series.push({
			type : 'area',
			name : id,
			data : data,
		});
		
		config['series'] = series;
		if(data.length < 10000){
			$('#container').append('<div id="char-' + i + '"></div>');
			$('#char-' + i).highcharts(config);
		}
	}
}