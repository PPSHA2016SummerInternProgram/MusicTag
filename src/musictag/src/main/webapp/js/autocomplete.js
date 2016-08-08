$(document).ready(function() {
	$("#navbar-search-input").autocomplete({
		serviceUrl: '/musictag/suggest/all',
		transformResult: function(response) {
			var responseJson = $.parseJSON(response);
			var artistSuggest = responseJson.data.artist.suggest.suggester;
			var query = Object.keys(artistSuggest)[0];
			
			var transformedArtists = transform(query, artistSuggest[query].suggestions, 'artist');
			
			var releaseSuggest = responseJson.data.release.suggest.suggester;
			var transformedReleases = transform(query, releaseSuggest[query].suggestions, 'release');
			
			var recordingSuggest = responseJson.data.recording.suggest.suggester;
			var transformedRecordings = transform(query, recordingSuggest[query].suggestions, 'recording');
			var transformed = transformedArtists.concat(transformedReleases).concat(transformedRecordings);
			
			return {suggestions: transformed};
		},
		groupBy: 'entity_type',
		onSelect: function (suggestion) {
			window.location.href = "/musictag/"+ suggestion.data.entity_type + "/" + suggestion.data.mbid + "/";
		}
	});
	
	function transform(query, solrSuggestions, group){
		let set = new Set();
		var num = 0;
		return $.map(solrSuggestions, function(dataItem){
			return {value: dataItem.term, data: {entity_type: group, mbid: dataItem.payload}};
		}).filter(function(elem){
			if(num > 2) return false;
			if(set.has(elem.data.mbid)){
				return false;
			}else{
				num++;
				set.add(elem.data.mbid);
				return true;
			}
		});
	}	
});