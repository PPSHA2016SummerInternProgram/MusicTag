var ContextPath = '/musictag';

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
function sendAjax(url, requestData, callback, args) {
	$.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		data : requestData,
		success : function(data) {
			if (callback) {
				if(args){
					callback(data, args);
				}else{
					callback(data);
				}
			}
		},
		error : function() {
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
	return obj ? false : true;
}

function getValue() {
	if (arguments.length === 0)
		return '';
	var json = arguments[0];
	for (var i = 1; i < arguments.length; i++) {
		if (json[arguments[i]] !== undefined) {
			json = json[arguments[i]];
		} else {
			return '';
		}
	}
	return json;
}

/**
 * Parse the URL, and return UUID. If not found, return ''
 */
function getUuid() {
	var path = location.pathname;
	var paths = path.split('/');
	for (var i = 0; i < paths.length; i++) {
		if (/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/
				.test(paths[i])) {
			return paths[i];
		}
	}
	return '';
}