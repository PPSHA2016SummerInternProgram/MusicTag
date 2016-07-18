$(function() {
	hideBasicInfo();
	getBasicInfoFromServer(getUrlVars()['gid']);
	addReadMoreProfileListener();
	addShowLinksListener();
});

function hideBasicInfo() {
	$('[data-artist-overview]').hide();
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

function getBasicInfoFromServer(gid) {
	var url = ContextPath + '/artist/basicInfo';
	var data = {
		gid : gid
	}
	sendAjax(url, data, receivedBasicInfo);
}

function receivedBasicInfo(data) {
	if (!data.success) {
		console(data.errorMsg);
		return;
	}

	info = data.data;
	var name = info['name'];
	var gender = info['gender'];
	var area = info['area']['name'];
	var image = info['commons-img'];
	var lifeSpanBegin = info['life-span']['begin'];
	var lifeSpanEnd = info['life-span']['end'];
	var profile = info['wikipedia-extract'];
	var lifeSpan = '';
	if (!isEmpty(lifeSpanBegin) || !isEmpty(lifeSpanEnd)) {
		lifeSpan = (isEmpty(lifeSpanBegin) ? '?' : lifeSpanBegin) + '~'
				+ (isEmpty(lifeSpanEnd) ? '?' : lifeSpanEnd);
	}

	$('[data-artist-overview-name]').text(name);
	$('[data-artist-overview-gender]').text(gender);
	$('[data-artist-overview-area]').text(area);
	$('[data-artist-overview-profile]').html(profile);
	$('[data-artist-overview-life-span]').text(lifeSpan);
	$('[data-artist-overview-image]').attr('src', image);

	var relations = info['relations'];
	for (var i = 0; i < relations.length; i++) {
		var relation = relations[i];
		var url = relation['url']['resource'];
		var type = relation['type'];
		$('[data-artist-overview-links-wrapper]').append(
				'<li class="list-group-item"><label class="artist-overview-link-label">'
						+ type + '</label><a href="' + url + '">' + url
						+ '</a></li>');
	}

	showBasicInfo();
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
