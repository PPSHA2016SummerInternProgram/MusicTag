$(function() {

	clearBasicInfo();
	getTracklistFromServer();
	getReleaseCoverFromServer();
	getReleaseArtistinfoFromServer();
	getReleasevoteFromServer();
});

function clearBasicInfo() {
	$('[data-artist-name]').text('');
	$('[data-date]').text('');
	$('[data-release-name]').text('');
	$('[data-rate]').html('');
}

function getReleaseArtistinfoFromServer() {
	var url = 'artistinfo';
	sendAjax(url, null, receivedArtistinfo);
}

function receivedArtistinfo(data) {
	if (!data.success) {
		return;
	}
	var artistName = getArtistName(data);
	var date = getValue(data, 'data', 'date');
	var releaseName = getValue(data, 'data', 'title');
	$('[data-artist-name]').text(artistName);
	$('[data-date]').text(date.split('-')[0]);
	$('[data-release-name]').text(releaseName);
}

function getArtistName(data) {
	var artistCredit = getValue(data, 'data', 'artist-credit');
	if (!artistCredit || artistCredit.length === 0) {
		return '';
	}
	var artistName = getValue(artistCredit[0], 'name');
	if (!artistName) {
		artistName = getValue(artistCredit[0], 'artist', 'name');
	}
	return artistName;
}

function getReleasevoteFromServer() {
	var url = 'releasevote';
	sendAjax(url, null, receivedReleasevote);
}

function receivedReleasevote(data) {
	if (!data.success) {
		return;
	}
	var rating = getValue(data, 'data', 'rating', 'value');
	if (!rating) {
		rating = 0;
	}
	$('[data-rate]').html('');
	for (var i = 0; i < 5; i++) {
		if (i < rating) {
			$('[data-rate]').append(
					'<span class="glyphicon glyphicon-star"></span>');
		} else {
			$('[data-rate]').append(
					'<span class="glyphicon glyphicon-star-empty"></span>');
		}
	}
}

function getReleaseCoverFromServer() {
	var uuid = getUuid();
	if (!uuid) {
		return;
	}
	var url = ContextPath + '/cover-art-archive/release/' + uuid;
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

function getTracklistFromServer() {
	var url = 'tracklist';
	sendAjax(url, null, receivedBasicInfo);
}

function receivedBasicInfo(data) {
	if (!data.success) {
		return;
	}

	$('[data-track-table]').html('');
	var recordings = getValue(data, 'data', 'recordings');
	var ratingMax = findMaxRating(recordings);
	for (var i = 0; !isEmpty(recordings) && i < recordings.length; i++) {
		var recording = recordings[i];
		$('[data-track-table]').append(
				createRecordingHtml(recording, i + 1, ratingMax));
	}
	$('[data-track-table]').fadeIn(300);

	addMoreControlListener();
	addMoreListener();
}

function findMaxRating(recordings) {
	var max = 0;
	for (var i = 0; !isEmpty(recordings) && i < recordings.length; i++) {
		var recording = recordings[i];
		var playAmount = getValue(recording, 'play-amount');
		if (!playAmount) {
			playAmount = randomInt(100, 5000); // fake data now
			recording['play-amount'] = playAmount;
		}
		max = Math.max(playAmount, max);
	}
	return max;
}

function createRecordingHtml(recording, id, ratingMax) {
	var html = '';
	html += '<tr>';
	html += '<td class="id">' + id + '</td>';
	html += '<td class="name" data-name>' + getValue(recording, 'title');
	html += '	<div class="detail" style="display: none;">';
	html += '		<div>';
	html += '			arranger: <a>林邁可</a>';
	html += '		</div>';
	html += '		<div>';
	html += '			background vocals and lead vocals: <a>林邁可</a>';
	html += '		</div>';
	html += '		<div>';
	html += '			background vocals arranger: <a>周杰倫</a>';
	html += '		</div>';
	html += '		<div>';
	html += '			mixer: <a>林邁可</a>';
	html += '		</div>';
	html += '		<div>';
	html += '			recorded by: <a>楊瑞代</a>';
	html += '		</div>';
	html += '		<div>';
	html += '			lyricist: <a>方文山</a>';
	html += '		</div>';
	html += '	</div>';
	html += '</td>';
	html += '<td class="more" data-more="true"><a style="display: none;">more';
	html += '		<span class="glyphicon glyphicon-chevron-down"></span>';
	html += '</a></td>';

	var time = '';
	var length = getValue(recording, 'length');
	if (length) {
		var minutes = parseInt(length / 1000 / 60);
		var seconds = parseInt(length / 1000 % 60);
		time = minutes + ':' + seconds;
	}
	html += '<td class="time">' + time + '</td>';

	var votes = getValue(recording, 'rating', 'value');
	if (!votes) {
		votes = 0;
	}
	html += '<td class="rate">';
	for (var i = 0; i < 5; i++) {
		if (i < votes) {
			html += '<span class="glyphicon glyphicon-star"></span>';
		} else {
			html += '<span class="glyphicon glyphicon-star-empty"></span>';
		}
	}
	html += '</td>';

	// fake data now
	var playAmount = getValue(recording, 'play-amount');
	var ratingMax = 5000;
	var percent = 30 + (playAmount / ratingMax * 70);

	html += '<td class="play-amount"><div style="padding:5px; min-width:50px;width:'
			+ percent + '%;  background:#ccc">' + playAmount + '</div></td>';
	html += '</tr>';
	return html;
}

function randomInt(min, max) {
	return parseInt(Math.random() * (max - min) + min);
}

function addMoreListener() {
	$('[data-more]').off('click').on('click', function() {
		var name = $(this).closest('tr').find('.name');
		var flag = 'data-name';
		if (name.attr(flag) === 'hide') {
			name.attr(flag, 'show');
			name.find('.detail').fadeOut(300);
		} else {
			name.attr(flag, 'hide');
			name.find('.detail').fadeIn(300);
		}
	});
}

function addMoreControlListener() {
	$('.track-table tr').off('mouseover').on('mouseover', function() {
		$(this).find('[data-more] a').show();
	});
	$('.track-table tr').off('mouseout').on('mouseout', function() {
		$(this).find('[data-more] a').hide();
	});
}