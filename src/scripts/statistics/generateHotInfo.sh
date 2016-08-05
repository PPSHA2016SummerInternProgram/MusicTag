artistPlaycount="artist-playcount"
artistListeners="artist-listeners"
albumPlaycount="album-playcount"
albumListeners="album-listeners"
trackPlaycount="track-playcount"
trackListeners="track-listeners"

javac GenerateHotInfo.java

java GenerateHotInfo $artistPlaycount > tmp.js
mongo music-tag tmp.js
java GenerateHotInfo $artistListeners > tmp.js
mongo music-tag tmp.js
java GenerateHotInfo $albumPlaycount > tmp.js
mongo music-tag tmp.js
java GenerateHotInfo $albumListeners > tmp.js
mongo music-tag tmp.js
java GenerateHotInfo $trackPlaycount > tmp.js
mongo music-tag tmp.js
java GenerateHotInfo $trackListeners > tmp.js
mongo music-tag tmp.js
