$(function() {

	clearBasicInfo();
	getTracklistFromServer();
	var uuid = getUuid();
	getReleaseCoverFromServer(uuid);
	getReleaseArtistinfoFromServer();
	getReleasevoteFromServer();
//	showHotCharts();
});

var basicInfo;

function showHotCharts() {
	getStatisticsDataFromServer('release', 'listeners', 'release-listeners',
			getUuid(), showRank);
}

function showRank(rank, total) {
	if (rank && total) {
		var key = $('#release-hot-rank-hint');
		var artistName = '';
		if(basicInfo){
			artistName = getValue(basicInfo, 'name');
		}
		var rate = parseInt(rank / total * 10000) / 100;
		key.html(artistName + ' beats <span style="color:#dd4b39">' + rate + '%</span> releases: ');
		$('[data-artist-listeners-char]').show();
		$('#release-hot-div').show();
		addReleaseHotChartListener();
	}
}

function addReleaseHotChartListener() {
	var chart = $('#release-listeners');
	var more = $('#release-hot-chart-read-more');
	var less = $('#release-hot-chart-read-less');
	more.on('click', function() {
		more.hide();
		less.show();
		chart.css('margin-top', '50px');
		chart.css('height', '');
	});
	less.on('click', function() {
		more.show();
		less.hide();
		chart.css('margin-top', '0');
		chart.css('height', '0');
	});
}

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

function SeedRandom(state1,state2){
    var mod1=4294967087
    var mul1=65539
    var mod2=4294965887
    var mul2=65537
    if(typeof state1!="number"){
        state1=+new Date()
    }
    if(typeof state2!="number"){
        state2=state1
    }
    state1=state1%(mod1-1)+1
    state2=state2%(mod2-1)+1
    function random(limit){
        state1=(state1*mul1)%mod1
        state2=(state2*mul2)%mod2
        if(state1<limit && state2<limit && state1<mod1%limit && state2<mod2%limit){
            return random(limit)
        }
        return (state1+state2)%limit
    }
    return random
}

String.prototype.hashCode = function() {
	  var hash = 0, i, chr, len;
	  if (this.length === 0) return hash;
	  for (i = 0, len = this.length; i < len; i++) {
	    chr   = this.charCodeAt(i);
	    hash  = ((hash << 5) - hash) + chr;
	    hash |= 0; // Convert to 32bit integer
	  }
	  return hash;
	};
	
var randomSeed = null;

function findMaxRating(recordings) {
	
	if(!randomSeed){
		var seed = getUuid().hashCode();
		randomSeed = SeedRandom(seed < 0 ? -seed : seed+0);
	}
	
	var max = 0;
	for (var i = 0; !isEmpty(recordings) && i < recordings.length; i++) {
		var recording = recordings[i];
		var playAmount = getValue(recording, 'play-amount');
		if (!playAmount) {
			playAmount = randomSeed(5000) + 100; // fake data now
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
		time = OtherHelper.recordingLength(length);
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