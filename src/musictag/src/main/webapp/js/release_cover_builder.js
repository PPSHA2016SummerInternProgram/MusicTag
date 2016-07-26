/**
 * get a release cover from server.
 * 
 * @param releaseId
 * @returns
 */
function getReleaseCoverFromServer(releaseId) {
	if (!releaseId) {
		return;
	}
	var url = ContextPath + '/cover-art-archive/release/' + releaseId;
	sendAjax(url, null, receivedReleaseCover);
}

function receivedReleaseCover(data) {
	if (!data.success) {
		return;
	}
	var src = findReleaseCoverSrc(data);
	if (!src) {
		return;
	}
	$('[data-release-cover]').attr('src', src);
}

function findReleaseCoverSrc(data) {
	var images = getValue(data, 'data', 'images');
	if (!images || images.length === 0) {
		return '';
	}
	var image = images[0];
	var src = getValue(image, 'thumbnails', 'small');
	if (!src) {
		src = getValue(image, 'thumbnails', 'large');
	}
	if (!src) {
		src = getValue(image, 'image');
	}
	return src;
}
