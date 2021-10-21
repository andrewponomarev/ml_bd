---Выбрать артиста из России с наибольшим числом слушателей

SELECT artist_lastfm, listeners_lastfm
FROM artists
WHERE country_mb = 'Russia'
ORDER BY listeners_lastfm DESC
LIMIT 1