USE Fitness_Guide
GO

INSERT INTO Tests (Name) VALUES 
('AllTableTest'), ('AllViewTest'), ('MixedTest');
GO

CREATE OR ALTER VIEW getRandomValue AS
	SELECT RAND() AS RandomValue
GO

CREATE OR ALTER FUNCTION RandomInBetween(@lower INT, @upper INT)
RETURNS INT
AS
	BEGIN
		DECLARE @range INt = @upper - @lower + 1;
		DECLARE @result INT = FLOOR((SELECT RandomValue FROM getRandomValue) * @range + @lower);
		RETURN @result;
	END
GO

CREATE OR ALTER FUNCTION GenerateRandomString()
RETURNS VARCHAR(21)
AS 
	BEGIN 
		DECLARE @randomString VARCHAR(30) = '''';
		DECLARE @size INT = ROUND((SELECT randomValue FROM getRandomValue) * 18, 0) + 1; --max length is 19

		WHILE LEN(@randomString) < @size
		BEGIN
			SET @randomString = @randomString + CHAR(ROUND((SELECT randomValue FROM getRandomValue) * 25 + 65, 0));
		END
		SET @randomString = @randomString + '''';
		RETURN @randomString;
	END
GO

CREATE OR ALTER FUNCTION CheckIfPrimaryKey(@table_name VARCHAR(30), @column_name VARCHAR(30))
RETURNS BIT
AS
	BEGIN
		IF( EXISTS(
			SELECT T.name AS TableName, I.name, I.index_id, I.type, I.type_desc, C.name AS ColumnName
			FROM sys.tables T INNER JOIN sys.indexes I ON T.object_id = I.object_id
			INNER JOIN sys.index_columns IC ON I.object_id = IC.object_id AND I.index_id = IC.index_id
			INNER JOIN sys.columns C ON IC.object_id = C.object_id AND IC.column_id = C.column_id
			WHERE I.is_primary_key = 1 AND T.name = @table_name AND C.name = @column_name
			)
		)
			RETURN 1;
		RETURN 0;
	END
GO

CREATE OR ALTER FUNCTION CheckIfForeignKey (@table_name VARCHAR(50), @column_name VARCHAR(30))
RETURNS BIT
AS 
BEGIN
	IF( EXISTS(
		SELECT *
		FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC
			JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU
			ON RC.CONSTRAINT_CATALOG = KCU.CONSTRAINT_CATALOG
				AND RC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME
		WHERE KCU.TABLE_NAME = @table_name AND KCU.COLUMN_NAME = @column_name
			)
		)
		RETURN 1
	RETURN 0
END
GO

CREATE Or ALTER FUNCTION GetPrimaryKeysTable (@key_name VARCHAR(50))
RETURNS VARCHAR(100)
AS
BEGIN
	DECLARE @PrimaryTableName VARCHAR(100);
	SELECT @PrimaryTableName = R.name FROM
		(SELECT T.name --here we get all the tables where key_name is a primary key / and referenced
					FROM sys.tables T INNER JOIN sys.indexes I ON T.object_id = I.object_id
					INNER JOIN sys.index_columns IC ON I.object_id = IC.object_id AND I.index_id = IC.index_id
					INNER JOIN sys.columns C ON IC.object_id = C.object_id AND IC.column_id = C.column_id
					WHERE I.is_primary_key = 1 AND C.name = @key_name
		EXCEPT
			SELECT KCU.TABLE_NAME --and here we extract from the prev result all the tables whos columns were referenced 
			FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC
			JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU ON RC.CONSTRAINT_CATALOG = KCU.CONSTRAINT_CATALOG AND RC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME
			WHERE KCU.COLUMN_NAME = @key_name) R;
	RETURN @PrimaryTableName;
END
GO

CREATE OR ALTER PROCEDURE GenerateInsert(@table_id INT, @no_rows INT) AS
BEGIN
	DECLARE @table_name VARCHAR(30);
	SELECT @table_name = Name
	FROM Tables
	WHERE TableID = @table_id

	DECLARE ColumnCursor CURSOR LOCAL SCROLL FOR
		SELECT C.name AS ColumnName, T.name AS DataType
		FROM sys.columns C INNER JOIN sys.types T ON C.system_type_id = T.system_type_id
		WHERE object_id = OBJECT_ID(@table_name)

	OPEN ColumnCursor;
	DECLARE @column_name VARCHAR(30);
	DECLARE @column_type VARCHAR(30);

	DECLARE @insert_stmt_column VARCHAR(MAX) = 'INSERT INTO ' + @table_name + ' (';

	FETCH NEXT FROM ColumnCursor INTO
		@column_name, @column_type

	DECLARE @is_primary BIT;
	DECLARE @is_foreign BIT;

	WHILE @@FETCH_STATUS = 0
	BEGIN
		EXEC @is_primary = CheckIfPrimaryKey @table_name, @column_name;

		EXEC @is_foreign = CheckIfForeignKey @table_name, @column_name;

		IF (@is_primary = 1 AND @is_foreign = 1) OR @is_primary = 0
		BEGIN
			SET @insert_stmt_column = @insert_stmt_column + @column_name + ',';
		END

		FETCH NEXT FROM ColumnCursor INTO
			@column_name, @column_type;
	END

	--Get rid of last comma
	SET @insert_stmt_column = LEFT(@insert_stmt_column, LEN(@insert_stmt_column) - 1);
	SET @insert_stmt_column = @insert_stmt_column + ') VALUES ';

	DECLARE @counter INT = 0;
	WHILE @counter < @no_rows
	BEGIN
		FETCH FIRST FROM ColumnCursor INTO
			@column_name, @column_type

		DECLARE @insert_stmt_value VARCHAR(200) = '(';
		WHILE @@FETCH_STATUS = 0
		BEGIN
			EXEC @is_primary = CheckIfPrimaryKey @table_name, @column_name;

			EXEC @is_foreign = CheckIfForeignKey @table_name, @column_name;

			--if it's only a primary key, then the identity(1, 1) automatically assign a value

			IF @is_foreign = 1
			BEGIN
				DECLARE @foreign_table_name VARCHAR(30); 
				EXEC @foreign_table_name = GetPrimaryKeysTable @column_name;

				
				DECLARE @foreignkey_query NVARCHAR(100);
				IF @column_type = 'int'
				BEGIN
					DECLARE @new_foreignkey_value_int INT;
					SET @foreignkey_query = 'SELECT TOP 1 @new_foreignkey_value_int = ' + @column_name + ' FROM ' + @foreign_table_name + ' ORDER BY NEWID()';

					EXEC sp_executesql @foreignkey_query, N'@new_foreignkey_value_int INT OUTPUT', @new_foreignkey_value_int OUTPUT;
					SET @insert_stmt_value = @insert_stmt_value + CONVERT(VARCHAR(20),@new_foreignkey_value_int) + ', ';
				END
				ELSE IF @column_type = 'varchar'
				BEGIN 
					DECLARE @new_foreignkey_value_char VARCHAR(100);
					SET @foreignkey_query = 'SELECT TOP 1 @new_foreignkey_value_char = ' + @column_name + ' FROM ' + @foreign_table_name + ' ORDER BY NEWID()';

					EXEC sp_executesql @foreignkey_query, N'@new_foreignkey_value_char VARCHAR(100) OUTPUT', @new_foreignkey_value_char OUTPUT;
					SET @insert_stmt_value = @insert_stmt_value + @new_foreignkey_value_char + ', ';
				END
				ELSE IF @column_type = 'date'
				BEGIN 
					DECLARE @new_foreignkey_value_date DATE;
					SET @foreignkey_query = 'SELECT TOP 1 @new_foreignkey_value_date = ' + @column_name + ' FROM ' + @foreign_table_name + ' ORDER BY NEWID()';

					EXEC sp_executesql @foreignkey_query, N'@new_foreignkey_value_date DATE OUTPUT', @new_foreignkey_value_date OUTPUT;
					SET @insert_stmt_value = @insert_stmt_value + CONVERT(VARCHAR(20), @new_foreignkey_value_date) + ', ';
				END
			END
			ELSE IF @is_primary = 0
			BEGIN
				IF @column_type = 'int'
				BEGIN
					DECLARE @int_value INT;
					EXEC @int_value = RandomInBetween 0, 10000;

					SET @insert_stmt_value = @insert_stmt_value + CONVERT(VARCHAR(20), @int_value) + ', ';
				END
				ELSE IF @column_type = 'varchar'
				BEGIN
					DECLARE @string VARCHAR(101);
					EXEC @string = GenerateRandomString;
					SET @insert_stmt_value = @insert_stmt_value + @string + ', ';
				END
				ELSE IF @column_type = 'date'
				BEGIN
					SET @insert_stmt_value = @insert_stmt_value + '''' + CONVERT(VARCHAR(20), FORMAT(GETDATE(), 'yyyy-mm-dd')) + ''',';
				END
			END

			FETCH NEXT FROM ColumnCursor INTO
				@column_name, @column_type;
		END

		SET @insert_stmt_value = LEFT(@insert_stmt_value, LEN(@insert_stmt_value) -1);
		SET @insert_stmt_value = @insert_stmt_value + ');';
		DECLARE @insert_stmt VARCHAR(200) = @insert_stmt_column + @insert_stmt_value;

		EXEC(@insert_stmt);

		SET @counter = @counter + 1;
	END

	CLOSE ColumnCursor;
END
GO

EXEC GenerateInsert 2, 1000;
GO

SELECT C.name AS ColumnName, T.name AS DataType
FROM sys.columns C INNER JOIN sys.types T ON C.system_type_id = T.system_type_id
WHERE object_id = OBJECT_ID('dbo.Meal');

DELETE FROM DayOfTrainingWeek
WHERE DayOfWeekID > 7
GO

UPDATE TestTables
SET Position = 1
WHERE TableID = 2
GO

DECLARE @xd BIT;
EXEC @xd = 

CREATE OR ALTER PROCEDURE RunTest(@test_id INT) AS
BEGIN
	DECLARE @table_count INT;
	DECLARE @view_count INT;

	SELECT @table_count = COUNT(*)
	FROM TestTables
	WHERE TestID = @test_id

	SELECT @view_count = COUNT(*)
	FROM TestViews
	WHERE TestID = @test_id

	--delete rows from tables
