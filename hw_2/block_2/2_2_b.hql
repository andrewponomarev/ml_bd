SELECT counts.tag
FROM (SELECT tag, count(1) AS cnt
      FROM artists LATERAL VIEW explode(split(tags_lastfm, "; ")) tag_table AS tag
      WHERE length(tag) > 0
      GROUP BY tag
      ORDER BY cnt DESC
      LIMIT 1) AS counts;