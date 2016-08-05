/**
 * This script is used to extract listeners and playcount from mongo for artist and album and track.
 * This is a example cmd:
 * 	mongo music-tag --eval 'var collection="artist", type="playcount"' extractHotInfo.js
 */

var collection, type;

if(!collection || collection !== 'artist' && collection !== 'album' && collection !== 'track'){
	print('Collection is wrong, please pass a collection value as [artist | album | track].');
	exit();
}

if(!type || type !== 'listeners' && type !== 'playcount'){
	print('Type is wrong, please pass a type value as [listeners | playcount].');
	exit();
}

print('Collection: ' + collection + ', Type: ' + type);

var filter = {};
if(collection === 'artist'){
	filter['stats'] = 1;
}else{
	filter[type] = 1;
}

db.lastfm[collection].find({}, filter).forEach(result);

function result(item){

	if(collection === 'artist'){
		item = item['stats'];
	}
	if(item){
		print(item[type]);
	}
}
