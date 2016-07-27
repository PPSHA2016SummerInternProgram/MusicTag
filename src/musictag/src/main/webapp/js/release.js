$(function() {

	clearBasicInfo();
	getTracklistFromServer();
	var uuid = getUuid();
	getReleaseCoverFromServer(uuid);
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
	var artistGid = getArtistGid(data);
	var artistName = getArtistName(data);
	var date = getValue(data, 'data', 'date');
	var releaseName = getValue(data, 'data', 'title');
	$('[data-artist-name]').html(createAristPageLink(artistGid, artistName));
	$('[data-date]').text(date.split('-')[0]);
	$('[data-release-name]').text(releaseName);
}

function createAristPageLink(artistGid, artistName){
	return '<a href="' + getArtistPageLink(artistGid) + '">' + artistName + '</a>';
}

function getArtistPageLink(artistGid){
	return ContextPath + '/artist/' + artistGid + '/';
}

function getRecordingPageLink(recordingGid) {
	return ContextPath + '/recording/' + recordingGid + '/';
}

function getArtistGid(data){
	var artistCredit = getValue(data, 'data', 'artist-credit');
	if (!artistCredit || artistCredit.length === 0) {
		return '';
	}
	return getValue(artistCredit[0], 'artist', 'id');
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

function getTracklistFromServer() {
	var url = 'tracklist';
	sendAjax(url, null, receivedTracklist);
}

function receivedTracklist(data) {
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
	var recordingGid = getValue(recording, 'id');
	html += '<tr data-recording-id="' + recordingGid + '">';
	html += '<td class="id">' + id + '</td>';
	html += '<td class="name" data-name><a class="title" href="' + getRecordingPageLink(recordingGid) + '">' + getValue(recording, 'title') + '</a>';
	html += '<div class="detail" style="display: none;"></div>';
	html += '</td>';
	html += '<td class="more" data-more="true"><a style="display: none;">more';
	html += '		<span class="glyphicon glyphicon-chevron-down"></span>';
	html += '</a></td>';

	var time = '';
	var length = getValue(recording, 'length');
	if (length) {
		var minutes = parseInt(length / 1000 / 60);
		var seconds = parseInt(length / 1000 % 60);
		seconds = seconds < 10 ? '0' + seconds : seconds;
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

	var playAmount = getValue(recording, 'play-amount');
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
	$('[data-more]')
			.off('click')
			.on(
					'click',
					function() {
						var tr = $(this).closest('tr');
						var name = tr.find('.name');
						var flag = 'data-name';
						if (name.attr(flag) === 'show') {
							name.attr(flag, 'hide');
							tr
									.find('[data-more] a')
									.html(
											'more <span class="glyphicon glyphicon-chevron-down"></span>');
							name.find('.detail').fadeOut(300);
						} else {
							name.attr(flag, 'show');
							tr
									.find('[data-more] a')
									.html(
											'less <span class="glyphicon glyphicon-chevron-up"></span>');
							name.find('.detail').fadeIn(300);
							showMoreData(tr);
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

function showMoreData(tr) {
	var recordingId = tr.attr('data-recording-id');
	if (!tr.find('.detail').html()) {
		getRecordingWorkArtistRels(tr, recordingId);
	}
}

function getRecordingWorkArtistRels(tr, recordingId) {
	var url = ContextPath + '/recording/' + recordingId + '/work-artist-rels';
	sendAjax(url, null, receivedRecordingWorkArtistRels, tr);
}

function receivedRecordingWorkArtistRels(data, tr) {
	var group = {};
	recordingWorkArtistGroupByType(data['data'], group);
	var html = createRelHtml(group);
	tr.find('.detail').append(html);
}

function recordingWorkArtistGroupByType(data, group){
	var relations = getValue(data, 'relations');
	for (var i = 0; !isEmpty(relations) && i < relations.length; i++) {
		var relation = relations[i];
		var type = getValue(relation, 'type');
		var artist = getValue(relation, 'artist', 'name');
		var artistGid = getValue(relation, 'artist', 'id');
		var targetType = getValue(relation, 'target-type');
		if (type && artist && targetType === 'artist') {
			var item = {
					'artist-gid' : artistGid,
					'type' : type,
					'artist-name' : artist
			};
			if(group[type]){
				group[type].push(item);
			}else{
				group[type] = [item];
			}
		} else if (getValue(relation, 'work')) {
			recordingWorkArtistGroupByType(getValue(relation, 'work'), group);
		}
	}
}

function createRelHtml(group) {
	var html = '';
	for(var type in group) {
		if (group.hasOwnProperty(type)) {
			var arr = group[type];
			html += '<div>' + type + ': ';
			for(var i=0; i<arr.length; i++) {
				var artistGid = arr[i]['artist-gid'];
				var artistName = arr[i]['artist-name'];
				html += '<a href="' + getArtistPageLink(artistGid) + '">' + artistName + '</a> ';
			}
			html += '</div>';
		}
	}
	return html;
}