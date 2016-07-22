$(function() {

	getTracklistFromServer();
});

function getTracklistFromServer() {
	var url = 'tracklist';
	sendAjax(url, null, receivedBasicInfo);
}

function receivedBasicInfo(data) {
	console.log(data);
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