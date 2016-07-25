$(function() {
	clearBasicInfo();
	getRecordingFull()
	getReleaseCoverFromServer();
});

function clearBasicInfo() {
	$('[data-artist-name]').text('');
	$('[data-date]').text('');
	$('[data-recording-name]').text('');
	$('[data-rate]').html('');
	$('[data-song-length]').text('');
	$('[data-song-style').text('');
	$('[data-song-producer').text('');
	$('[data-song-composer]').text('');
	$('[data-song-lyricist').text('');
	$('[data-song-publisher').text('');
	$('[data-song-versions').text('');
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

function getRecordingFull() {
	var url = "full";
	sendAjax(url, null, receivedRecordingFull);
}

function receivedRecordingFull(data) {
	if (!data.success) {
		return;
	}

	// get basic info
	var artistCredit = getValue(data, "data", "artist-credit")[0];
	var artistName = getValue(artistCredit, "name");
	var recordingName = getValue(data, "data", "title");
	var length = getValue(data, "data", "length");
	if (!length)
		length = 0;
	var rating = getValue(data, "data", "rating", "value");
	if (!rating)
		rating = 0;
	var tags = getValue(data, "data", "tags");
	var tagStr = "";
	for (var i = 0; i < tags.length; i++) {
		tagStr += getValue(tags[i], "name") + ", ";
	}
	if (tagStr !== "")
		tagStr = tagStr.substring(0, tagStr.length - 2);

	var relations = getValue(data, "data", "relations");
	var work = null;
	var producerArtists = [];
	for (var j = 0; j < relations.length; j++) {
		if (getValue(relations[j], "type") === "performance") {
			work = getValue(relations[j], "work");
		} else if (getValue(relations[j], "type") === "producer") {
			producerArtists.push(getValue(relations[j], "artist"));
		}
	}

	var composerArtist = null;
	var lyricistArtist = null;
	var translationWorks = [];
	var publisherArtist = null; // can't find now;
	if (work !== null) {
		var workRelations = getValue(work, "relations");
		for (var k = 0; workRelations !== '' && k < workRelations.length; k++) {
			if (getValue(workRelations[k], "type") === "composer") {
				composerArtist = getValue(workRelations[k], "artist");
			} else if (getValue(workRelations[k], "type") === "lyricist") {
				lyricistArtist = getValue(workRelations[k], "artist");
			} else if (getValue(workRelations[k], "type") === "other version") {
				translationWorks.push(getValue(workRelations[k], "work"));
			}
		}
	}

	updateRecordingBasicInfo(artistName, recordingName, length, rating, tagStr,
			producerArtists, composerArtist, lyricistArtist, translationWorks,
			publisherArtist);

	// get releases
	var releases = getValue(data, "data", "releases");
	updateContainingReleases(releases);
}

function updateContainingReleases(releases){
	var html = '';
	if(!releases){
		return;
	};
	
	for(var i = 0; i < releases.length; i++){
		html += createContainingReleasesHtml(releases[i]);
	}
	$('[data-song-releases').html(html);
	$('[data-song-releases').fadeIn(300);
}

function createContainingReleasesHtml(release){
	var html = '<tr></tr>';
	if(release){
		html = '<tr>'+
			 		'<td>' + createIdTitleLinkElem(release, 'release') + '</td>'+
			 		'<td>' + createArtistLinkElem(release['artist-credit'][0]['artist']) +'</td>'+
			 		'<td>' + release.date+'</td>'+
			 		'<td>'+ release.country+'</td>'+
		 		'</tr>';
	}
	return html;
}

function createContainingReleaseLinkEle(release){
	if(release){
		return '<a href="' + ContextPath + '/release/' + artist.id + '/" >'
		+ artist.name + '</a>';
	}
	
	return '<a href=""> </a>';
}

function updateRecordingBasicInfo(artistName, recordingName, length, rating,
		tagStr, producerArtists, composerArtist, lyricistArtist,
		translationWorks, publisherArtist) {
	$('[data-artist-name]').text(artistName);
	$('[data-recording-name]').text(recordingName);
	$('[data-rate]').html(createVoteHtml(rating));
	$('[data-song-length]').text(millsecToMinutes(length));
	$('[data-song-style').text(tagStr);
	$('[data-song-producer').html(createProducersHtml(producerArtists));
	$('[data-song-composer]').html(createArtistLinkElem(composerArtist));
	$('[data-song-lyricist').html(createArtistLinkElem(lyricistArtist));
	$('[data-song-publisher').html(createArtistLinkElem(publisherArtist));
	$('[data-song-versions').html(createTranslationHtml(translationWorks));
}

function millsecToMinutes(length) {
	var min = Math.floor(length / 60000);
	var sec = Math.floor((length % 60000) / 1000);
	return min + ":" + sec;
}

function createVoteHtml(rating) {
	var html = '';
	for (var i = 0; i < 5; i++) {
		if (i < rating) {
			html += '<span class="glyphicon glyphicon-star"></span>';
		} else {
			html += '<span class="glyphicon glyphicon-star-empty"></span>';
		}
	}
	return html;
}

function createProducersHtml(producerArtists) {
	var html = '';
	for (var i = 0; i < producerArtists.length; i++) {
		html += '<div>';
		html += createArtistLinkElem(producerArtists[i]);
		html += '</div>';
	}
	return html;
}

function createArtistLinkElem(artist) {
	if (artist) {
		return '<a href="' + ContextPath + '/artist/' + artist.id + '/" >'
				+ artist.name + '</a>';
	}

	return '<a href=""> </a>';
}


function createTranslationHtml(translationWorks){
	var html = '';
	for (var i = 0; i < translationWorks.length; i++) {
		html += '<div>';
		html += createIdTitleLinkElem(translationWorks[i], 'recording');
		html += '</div>';
	}
	return html;
}

function createIdTitleLinkElem(ele, entity) {
	if (ele) {
		return '<a href="' + ContextPath + '/'+ entity + '/' + ele.id + '/" >'
		+ ele.title + '</a>';
	}
	return '<a href=""> </a>';
}
