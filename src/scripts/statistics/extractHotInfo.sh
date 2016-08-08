
artistPlaycount="artist-playcount"
artistListeners="artist-listeners"
albumPlaycount="album-playcount"
albumListeners="album-listeners"
trackPlaycount="track-playcount"
trackListeners="track-listeners"

echo "Extracting $artistPlaycount"
mongo music-tag --eval 'var collection="artist", type="playcount"' extractHotInfo.js > $artistPlaycount
echo "OK"
echo "Extracting $artistListeners"
mongo music-tag --eval 'var collection="artist", type="listeners"' extractHotInfo.js > $artistListeners
echo "OK"
echo "Extracting $albumPlaycount"
mongo music-tag --eval 'var collection="album", type="playcount"' extractHotInfo.js > $albumPlaycount
echo "OK"
echo "Extracting $albumListeners"
mongo music-tag --eval 'var collection="album", type="listeners"' extractHotInfo.js > $albumListeners
echo "OK"
echo "Extracting $trackPlaycount"
mongo music-tag --eval 'var collection="track", type="playcount"' extractHotInfo.js > $trackPlaycount
echo "OK"
echo "Extracting $trackListeners"
mongo music-tag --eval 'var collection="track", type="listeners"' extractHotInfo.js > $trackListeners
echo "OK"
