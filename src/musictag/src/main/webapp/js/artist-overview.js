var basicInfo;

function getBasicInfoFromServer(callback) {
	var url = 'basic-info';
	callback = callback ? callback : defaultReceivedBasicInfo;
	sendAjax(url, null, callback);
}

function showRadar() {
	getRadarDataFromServer('artist-radar-chart', null);
}

function defaultReceivedBasicInfo(response) {
	if (!response.success) {
		return;
	}

	var info = response.data;
	basicInfo = info;
	var name = getValue(info, 'name');
	var image = getValue(info, 'commons-img');
	$('[data-artist-overview-name]').text(name);
	$('[data-artist-overview-image]').attr('src', image);
	
	getImageFromServer();
	showRadar();
}

function getImageFromServer() {
	var url = 'image';
	sendAjax(url, null, receivedImageUrl);
}

function receivedImageUrl(data) {
	if (data.success && !isEmpty(getValue(data, 'data', 'commons-img'))) {
		var src = getValue(data, 'data', 'commons-img');
		$('[data-artist-overview-image]').show();
		$('[data-artist-overview-image]').attr('src', src);
	}
}