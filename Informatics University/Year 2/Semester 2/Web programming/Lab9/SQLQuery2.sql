USE BattleShips
GO

SELECT * FROM players;
SELECT * FROM game_state;

DELETE FROM players;
DELETE FROM game_state;

SELECT TOP 1 player_id FROM players WHERE active = 1;

ALTER TABLE players
ALTER COLUMN grid VARCHAR(MAX)

ALTER TABLE players
ADD hitGrid VARCHAR(MAX)