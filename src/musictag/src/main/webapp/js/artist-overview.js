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
//	var gender = getValue(info, 'gender');
//	var area = getValue(info, 'area', 'name');
	var image = getValue(info, 'commons-img');
//	var lifeSpanBegin = getValue(info, 'life-span', 'begin');
//	var lifeSpanEnd = getValue(info, 'life-span', 'end');
//	var lifeSpan = '';
//	if (!isEmpty(lifeSpanBegin) || !isEmpty(lifeSpanEnd)) {
//		lifeSpan = (isEmpty(lifeSpanBegin) ? '?' : lifeSpanBegin) + '~'
//				+ (isEmpty(lifeSpanEnd) ? '?' : lifeSpanEnd);
//	}

	$('[data-artist-overview-name]').text(name);
//	$('[data-artist-overview-gender]').text(gender);
//	$('[data-artist-overview-area]').text(area);
//	$('[data-artist-overview-life-span]').text(lifeSpan);
	$('[data-artist-overview-image]').attr('src', image);

//	showBasicInfo();

	getImageFromServer();
//	getRelLinksFromServer();

//	showHotCharts();
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