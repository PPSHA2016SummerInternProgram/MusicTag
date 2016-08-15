$(function() {
	hideBasicInfo();
	getBasicInfoFromServer();
	getProfileFromServer();
	addReadMoreProfileListener();
	addShowLinksListener();
	Statistics.drawArtistReleaseDist($('#artist-release-dist'), getUuid());
	showSimilarArtists();
});

function showSimilarArtists(){
	var url = 'similar';
	sendAjax(url, null, receivedSimilarArtists);
}

function receivedSimilarArtists(response){
	var artists = getValue(response,'data', 'similarartists', 'artist');
	if(!artists || !artists.length){
		return;
	}
	var max = 7;
	$('#similar-artist-list').children().remove();
	for(var i=0; i<artists.length && max > 0; i++){
		var artist = artists[i];
		var mbid = getValue(artist, 'mbid');
		var name = getValue(artist, 'name');
		var images = getValue(artist, 'image');
		if(!mbid || !name || !images || !images.length){
			continue;
		}
		var imageSrc;
		for(var j=0; j<images.length; j++){
			if(getValue(images[j], 'size') === 'extralarge'){
				imageSrc = getValue(images[j], '#text'); 
			}
		}
		if(imageSrc){
			$('#similar-artist-list').append(createSimilarArtistHtml(mbid, name, imageSrc));
			--max;
		}
	}
	$('#similar-artists').show();
}

function createSimilarArtistHtml(mbid, name, imageSrc){
	return '<li class="grid-items-item">'
	+'	<div class="grid-items-item-wrapper">'
	+'		<div class="similar-artist-image">'
	+'			<img'
	+'				src="' + imageSrc + '">'
	+'		</div>'
	+'		<div class="grid-items-item-details">'
	+'			<div>' + name + '</div>'
	+'		</div>'
	+'		<a class="grid-items-item-link" href="' + ContextPath + '/artist/' + mbid + '/"></a>'
	+'	</div>'
	+'</li>';
}

var basicInfo;

function showHotCharts() {
	getStatisticsDataFromServer('artist', 'listeners', 'artist-listeners',
			getUuid(), showRank);
}

function showRank(rank, total) {
	if (rank && total) {
		var key = $('[data-artist-listeners-chart-key]');
		var artistName = '';
		if(basicInfo){
			artistName = getValue(basicInfo, 'name');
		}
		var rate = parseInt(rank / total * 10000) / 100;
		key.html(artistName + ' beats <span style="color:#dd4b39">' + rate + '%</span> artists: ');
		$('[data-artist-listeners-char]').show();
		addArtistHotChartListener();
	}
}

function addArtistHotChartListener() {
	var chart = $('[data-artist-listeners-chart-value]');
	var more = $('#artist-hot-chart-read-more');
	var less = $('#artist-hot-chart-read-less');
	more.on('click', function() {
		more.hide();
		less.show();
		chart.css('height', '');
	});
	less.on('click', function() {
		more.show();
		less.hide();
		chart.css('height', '0');
	});
}

function hideBasicInfo() {
	$('[data-artist-overview]').hide();
	$('[data-artist-overview-image]').hide();
	clearBasicInfo();
}

function showBasicInfo() {
	$('[data-artist-overview]').fadeIn(1000);
}

function clearBasicInfo() {
	$('[data-artist-overview-name]').text('');
	$('[data-artist-overview-gender]').text('');
	$('[data-artist-overview-area]').text('');
	$('[data-artist-overview-profile]').html('');
	$('[data-artist-overview-life-span]').text('');
	$('[data-artist-overview-image]').attr('src', '');
	$('[data-artist-overview-links-wrapper]').html('')
	$('[data-artist-listeners-chart]').html('');
}

function getBasicInfoFromServer() {
	var url = 'basic-info';
	var data = {};
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
		$('[data-artist-overview-image]').show();
		$('[data-artist-overview-image]').attr('src', src);
	}
}

function receivedRelLinks(data) {
	if (!data.success) {
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

function receivedProfile(data) {
	if (!data.success) {
		return;
	}

	var profile = getValue(data, 'data', 'wikipedia-extract');
	$('[data-artist-overview-profile]').html(profile);
}

function receivedBasicInfo(data) {
	if (!data.success) {
		return;
	}
	
	basicInfo = data.data;

	var info = data.data;
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

	showHotCharts();
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
		if ($(this).attr(btn) === 'hide') {
			$(this).attr(btn, 'show');
			$(filterLinks).fadeIn(500);
		} else {
			$(this).attr(btn, 'hide');
			$(filterLinks).fadeOut(500);
		}
	});
}