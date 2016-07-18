ContextPath = '/musictag';

/**
 * Get the URL params. For example:
 * localhost:8080/musictag/artist?gid=123&limit=100, will return {'gid': '123',
 * 'limit': '100'}
 * 
 * @returns {Array}
 */
function getUrlVars() {
	var vars = [], hash;
	var hashes = window.location.href.slice(
			window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}

/**
 * Send a GET request to server. If callback method exists, call it after
 * receiving response.
 * 
 * @param url
 * @param requestData
 * @param callback
 */
function sendAjax(url, requestData, callback) {
	$.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		data : requestData,
		success : function(data) {
			if (callback) {
				callback(data);
			}
		},
		error : function(jqueryXHR) {
			console.log('error: ' + jqueryXHR.status);
		}
	})
}

/**
 * obj is empty?
 * 
 * @param obj
 * @returns {Boolean}
 */
function isEmpty(obj) {
	if (obj) {
		return false;
	} else {
		return true;
	}
}

function getValue() {
	if (arguments.length == 0)
		return '';
	var json = arguments[0];
	for (var i = 1; i < arguments.length; i++) {
		if (json[arguments[i]] != undefined) {
			json = json[arguments[i]];
		} else {
			return '';
		}
	}
	return json;
}