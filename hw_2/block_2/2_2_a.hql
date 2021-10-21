SELECT artist_lastfm
FROM (
         SELECT artist_lastfm, scrobbles_lastfm
         FROM artists
         ORDER BY scrobbles_lastfm DESC
         LIMIT 1
     ) as a