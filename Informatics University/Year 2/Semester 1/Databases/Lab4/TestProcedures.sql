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

		IF (@is_primary = 1 AND @is_foreign = 1) OR @is_primary = 0 OR @column_type <> 'int'
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
			ELSE IF @is_primary = 0 OR @column_type <> 'int'
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
					SET @insert_stmt_value = @insert_stmt_value + '''' + CONVERT(VARCHAR(20), FORMAT(GETDATE(), 'yyyy-MM-dd')) + ''',';
				END
			END

			FETCH NEXT FROM ColumnCursor INTO
				@column_name, @column_type;
		END

		SET @insert_stmt_value = LEFT(@insert_stmt_value, LEN(@insert_stmt_value) -1);
		SET @insert_stmt_value = @insert_stmt_value + ');';
		DECLARE @insert_stmt VARCHAR(200) = @insert_stmt_column + @insert_stmt_value;

		BEGIN TRY
			PRINT(@insert_stmt);
			EXEC(@insert_stmt);
			SET @counter = @counter + 1;
		END TRY
		BEGIN CATCH
		END CATCH
	END

	CLOSE ColumnCursor;
END
GO

DELETE FROM Meal
GO

EXEC GenerateInsert 4, 5000;
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
EXEC @xd = CheckIfPrimaryKey 'FitnessCalendar', 'DayOfWeekID';
PRINT(@xd);
GO


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
	IF @table_count > 0
	BEGIN
		DECLARE delete_cursor CURSOR LOCAL SCROLL FOR
			SELECT T.Name
			FROM Tables T INNER JOIN TestTables TT ON T.TableID = TT.TableID
			WHERE TT.TestID = @test_id
			ORDER BY TT.Position ASC;

		OPEN delete_cursor;
		DECLARE @table_name VARCHAR(30);
		FETCH FIRST FROM delete_cursor INTO
			@table_name;
		DECLARE @delete_stmt VARCHAR(50);

		WHILE @@FETCH_STATUS = 0
		BEGIN
			SET @delete_stmt = 'DELETE FROM ' + @table_name;
			EXEC(@delete_stmt);

			FETCH NEXT FROM delete_cursor INTO
				@table_name;

		END

		CLOSE delete_cursor;
	END

	--inserting and view evaluation
	DECLARE @Description VARCHAR(100) = 'Number of tables tested ' + CONVERT(varchar(10), @table_count) + ', number of views tested : ' + CONVERT(varchar(10), @view_count);
	DECLARE @end_time VARCHAR(20);
	DECLARE @start_time VARCHAR(20) = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

	INSERT INTO TestRuns(Description, StartAt) VALUES (@Description, @start_time);
	DECLARE @last_test_id INT = @@IDENTITY;

	IF @table_count > 0
	BEGIN
		DECLARE insert_cursor CURSOR LOCAL SCROLL FOR
			SELECT T.TableID, TT.NoOfRows
			FROM Tables T INNER JOIN TestTables TT ON T.TableID = TT.TableID
			WHERE TT.TestID = @test_id
			ORDER BY TT.Position DESC;

		OPEN insert_cursor;
		DECLARE @table_id INT;
		DECLARE @no_rows INT;

		FETCH FIRST FROM insert_cursor INTO
			@table_id, @no_rows;

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT(@table_id);
			DECLARE @start_table_test_time VARCHAR(20) = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

			EXEC GenerateInsert @table_id, @no_rows;

			DECLARE @end_table_test_time VARCHAR(20) = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');
			
			INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@last_test_id, @table_id, @start_table_test_time, @end_table_test_time);

			FETCH NEXT FROM insert_cursor INTO
				@table_id, @no_rows;
		END

		CLOSE insert_cursor;
	END

	IF @view_count > 0
	BEGIN
		DECLARE view_cursor CURSOR LOCAL SCROLL FOR
			SELECT V.ViewID, V.Name
			FROM Views V INNER JOIN TestViews TV ON V.ViewID = TV.ViewID
			WHERE TV.TestID = @test_id;

		OPEN view_cursor;
		DECLARE @view_name VARCHAR(30);
		DECLARE @view_id INT;
		DECLARE @view_stmt VARCHAR(50);

		FETCH NEXT FROM view_cursor INTO
			@view_id, @view_name

		WHILE @@FETCH_STATUS = 0
		BEGIN
			SET @view_stmt = 'SELECT* FROM ' + @view_name;

			DECLARE @start_view_test_time VARCHAR(20) = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

			EXEC(@view_stmt);

			DECLARE @end_view_test_time VARCHAR(20) = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

			INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt) VALUES (@last_test_id, @view_id, @start_view_test_time, @end_view_test_time);

			FETCH NEXT FROM view_cursor INTO
				@view_id, @view_name;
		END
	END
	
	SET @end_time = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

	UPDATE TestRuns
	SET EndAt = @end_time
	WHERE TestRunID = @last_test_id;
END
GO

EXEC RunTest 3
GO

SELECT* FROM TestRunViews;