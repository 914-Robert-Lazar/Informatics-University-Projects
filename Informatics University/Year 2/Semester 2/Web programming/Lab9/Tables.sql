USE BattleShips
GO

CREATE TABLE players (
    player_id INT PRIMARY KEY IDENTITY(1,1),
    player_name VARCHAR(MAX) NOT NULL,
    play_session_id VARCHAR(30) UNIQUE,
    grid VARCHAR(255),
    ready BIT DEFAULT 0,
    active BIT DEFAULT 1
);

CREATE TABLE game_state (
    game_id INT PRIMARY KEY IDENTITY(1,1),
    player1_id INT,
    player2_id INT,
    current_turn INT,
    game_status VARCHAR(255)
);
