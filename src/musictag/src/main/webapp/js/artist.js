$(function() {
	hideBasicInfo();
	getBasicInfoFromServer();
	getProfileFromServer();
	addReadMoreProfileListener();
	addShowLinksListener();
});

function hideBasicInfo() {
	$('[data-artist-overview]').hide();
	$('[data-artist-overview-image]').hide();
	clearBasicInfo();
}

function showBasicInfo() {
	$('[data-artist-overview]').show();
}

function clearBasicInfo() {
	$('[data-artist-overview-name]').text('');
	$('[data-artist-overview-gender]').text('');
	$('[data-artist-overview-area]').text('');
	$('[data-artist-overview-profile]').html('');
	$('[data-artist-overview-life-span]').text('');
	$('[data-artist-overview-image]').attr('src', '');
	$('[data-artist-overview-links-wrapper]').html('')
}

function getBasicInfoFromServer() {
	var url = 'basic-info';
	var data = {}
	sendAjax(url, data, receivedBasicInfo);
}

function getImageFromServer() {
	var url = 'image';
	sendAjax(url, null, receivedImageUrl);
}

function getRelLinksFromServer() {
	var url = 'rel-links';
	sendAjax(url, null, receivedRelLinks);
}

function getProfileFromServer() {
	var url = 'profile';
	sendAjax(url, null, receivedProfile);
}

function receivedImageUrl(data) {
	if (data.success && !isEmpty(getValue(data, 'data', 'commons-img'))) {
		var src = getValue(data, 'data', 'commons-img');
		console.log(src);
		$('[data-artist-overview-image]').show();
		$('[data-artist-overview-image]').attr('src', src);
	}
}

function receivedRelLinks(data){
	if(!data.success){
		console.log(data.errorMsg);
		return;
	}

	var relations = getValue(data, 'data', 'relations');
	for (var i = 0; !isEmpty(relations) && i < relations.length; i++) {
		var relation = relations[i];
		var url = relation['url']['resource'];
		var type = relation['type'];
		$('[data-artist-overview-links-wrapper]').append(
				'<li class="list-group-item"><label class="artist-overview-link-label">'
						+ type + '</label><a href="' + url + '">' + url
						+ '</a></li>');
	}
} 

function receivedProfile(data){
	if(!data.success){
		console.log(data.errorMsg);
		return;
	}

	var profile = getValue(data, 'data', 'wikipedia-extract');
	$('[data-artist-overview-profile]').html(profile);
}

function receivedBasicInfo(data) {
	if (!data.success) {
		console.log(data.errorMsg);
		return;
	}

	info = data.data;
	var name = getValue(info, 'name');
	var gender = getValue(info, 'gender');
	var area = getValue(info, 'area', 'name');
	var image = getValue(info, 'commons-img');
	var lifeSpanBegin = getValue(info, 'life-span', 'begin');
	var lifeSpanEnd = getValue(info, 'life-span', 'end');
	var lifeSpan = '';
	if (!isEmpty(lifeSpanBegin) || !isEmpty(lifeSpanEnd)) {
		lifeSpan = (isEmpty(lifeSpanBegin) ? '?' : lifeSpanBegin) + '~'
				+ (isEmpty(lifeSpanEnd) ? '?' : lifeSpanEnd);
	}

	$('[data-artist-overview-name]').text(name);
	$('[data-artist-overview-gender]').text(gender);
	$('[data-artist-overview-area]').text(area);
	$('[data-artist-overview-life-span]').text(lifeSpan);
	$('[data-artist-overview-image]').attr('src', image);

	showBasicInfo();

	getImageFromServer();
	getRelLinksFromServer();
}

/**
 * show more & show less profile for artist
 */
function addReadMoreProfileListener() {
	var profile = $('#artist-overview-profile');
	var more = $('#artist-overview-profile-read-more');
	var less = $('#artist-overview-profile-read-less');
	var hiddenClass = 'artist-overview-profile-less';
	more.on('click', function() {
		profile.removeClass(hiddenClass);
		more.hide();
		less.show();
	});
	less.on('click', function() {
		profile.addClass(hiddenClass);
		more.show();
		less.hide();
	});
}

/**
 * show & hide links for artist
 */
function addShowLinksListener() {
	var btn = 'data-artist-overview-link-btn';
	var filterBtn = '[' + btn + ']';
	var filterLinks = '[data-artist-overview-links]';
	$(filterBtn).on('click', function() {
		if ($(this).attr(btn) == 'hide') {
			$(this).attr(btn, 'show');
			$(filterLinks).show();
		} else {
			$(this).attr(btn, 'hide');
			$(filterLinks).hide();
		}
	});
}
