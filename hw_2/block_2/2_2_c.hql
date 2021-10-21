SELECT artist_lastfm
FROM (SELECT DISTINCT artist_lastfm, t.scrobbles_lastfm
      FROM (SELECT tag, artist_lastfm, scrobbles_lastfm
            FROM artists
                     LATERAL VIEW explode(split(tags_lastfm, "; ")) tag_table AS tag
            WHERE length(tag) > 0
            ORDER BY scrobbles_lastfm DESC) AS t
      WHERE t.tag IN (SELECT c.tag
                      FROM (SELECT tag, artist_lastfm, scrobbles_lastfm
                            FROM artists
                                     LATERAL VIEW explode(split(tags_lastfm, "; ")) tag_table AS tag
                            WHERE length(tag) > 0
                            ORDER BY scrobbles_lastfm DESC) as c)
      ORDER BY t.scrobbles_lastfm DESC
      LIMIT 10) as tt