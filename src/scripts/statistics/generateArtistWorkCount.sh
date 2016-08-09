
# Result,txt contains gid, artist_count and artist_creadit columns.

cat Result.txt | awk -F" " 'BEGIN{printf "db.ArtistWorkCount.drop()\ndb.ArtistWorkCount.insertMany(["} {printf "{gid:\""$1"\",artist_count:"$2",artist_credit:"$3"},"} END{print "{gid:\"1234\"}])\ndb.ArtistWorkCount.remove({gid:\"1234\"})"}' > tmp.js
mongo music-tag tmp.js
