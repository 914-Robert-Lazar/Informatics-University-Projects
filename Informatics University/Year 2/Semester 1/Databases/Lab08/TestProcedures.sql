Use Library
Go

--sysdatetime() getdate()
GO
CREATE OR ALTER VIEW getRANDValue
AS
SELECT RAND() AS Value

GO
CREATE OR ALTER FUNCTION RandIntBetween(@lower INT, @upper INT)
RETURNS INT
AS
BEGIN
  DECLARE @result INT;
  DECLARE @range INT = @upper - @lower + 1;
  SET @result = FLOOR((SELECT Value FROM getRANDValue) * @range + @lower);
  RETURN @result;
END

GO
CREATE OR ALTER FUNCTION CheckIfColumnIsAForeignKey (@table_name VARCHAR(50), @column_name VARCHAR(50))
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
Create OR ALTER FUNCTION CheckIfColumnIsAPrimaryKey (@table_name VARCHAR(50), @column_name VARCHAR(50))
RETURNS BIT
AS
BEGIN
	IF( EXISTS(
			SELECT t.name as TableName, i.name, i.index_id, i.type, i.type_desc, c.name as KeyName
			FROM sys.tables t INNER JOIN sys.indexes i ON t.object_id = i.object_id
				INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id
					AND i.index_id = ic.index_id
				INNER JOIN sys.columns c ON ic.object_id = c.object_id
					AND ic.column_id = c.column_id
			WHERE i.is_primary_key = 1 AND t.name = @table_name and c.name = @column_name
			)
		)
		RETURN 1
	RETURN 0
END


Go
Create Or Alter Function GenerateRandomString ()
RETURNS VARCHAR(100)
AS
BEGIN
	DECLARE @RandomString NVARCHAR(MAX) = '''';

	DECLARE @Length INT = ROUND((SELECT Value FROM getRANDValue) * 30, 0) + 2; -- Random length up to 30

	WHILE LEN(@RandomString) < @Length
	BEGIN
		SET @RandomString = @RandomString + CHAR(ROUND((SELECT Value FROM getRANDValue) * 25 + 65, 0)); -- ASCII values for uppercase letters
	END
	Set @RandomString = @RandomString + '''';
	RETURN @RandomString;
END
Go
Create Or Alter Function GetPrimaryKeysTable (@key_name varchar(50))
RETURNS VARCHAR(100)
AS
BEGIN
	Declare @PrimaryTableName varchar(100);
	Select @PrimaryTableName = R.name FROM
		(SELECT t.name --here we get all the tables where key_name is a primary key / and referenced
					FROM sys.tables t INNER JOIN sys.indexes i ON t.object_id = i.object_id
						INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id
							AND i.index_id = ic.index_id
						INNER JOIN sys.columns c ON ic.object_id = c.object_id
							AND ic.column_id = c.column_id
					WHERE i.is_primary_key = 1 AND c.name = @key_name
		EXCEPT
			SELECT KCU.TABLE_NAME --and here we extract from the prev result all the tables whos columns were referenced 
				FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC
					JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU
					ON RC.CONSTRAINT_CATALOG = KCU.CONSTRAINT_CATALOG
						AND RC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME
					WHERE KCU.COLUMN_NAME = @key_name)R;
	Return @PrimaryTableName;
END


GO
CREATE OR ALTER PROC RunTest(@TestId INT)
AS
BEGIN
	Declare @count_table_test int;
	Declare @count_view_test int;

	Select @count_table_test = COUNT(*)
	From TestTables
	Where TestID = @TestId;
	Select @count_view_test = COUNT(*)
	From TestViews
	Where TestID = @TestId;

	--i declare this here so it is accessible from both ifs
	Declare @startTestTime varchar(20);
	Declare @endTestTime varchar(20);
	Declare @Description varchar(100);
	Declare @LastTestId Int;
	Declare @startTableTestTime varchar(20);
	Declare @endTableTestTime varchar(20);
	Declare @startViewTestTime varchar(20);
	Declare @endViewTestTime varchar(20);

	If @count_table_test > 0 --there is table insert in the test
	Begin
	
		--first we declare a cursor for tables to iterate through them
		Declare TableCursor Cursor Local Scroll For
			Select t.Name, t.TableID, TestT.NoOfRows
			From Tables t Inner Join TestTables TestT On t.TableID = TestT.TableID
			Where TestT.TestID = @TestId
			Order by TestT.Position Asc;

		Open TableCursor;
		Declare @TableName Varchar(50);
		Declare @TableId Int;
		Declare @NrOfRows Int;

		Fetch Last From TableCursor
			Into @TableName, @TableId, @NrOfRows;
		
		--deleting rows!
		Declare @delStmt varchar(20);
		Set @delStmt = 'DELETE FROM ';
		Declare @delTableStmt varchar(100);

		While @@FETCH_STATUS = 0
		Begin
			Set @delTableStmt = @delStmt + @TableName;
			Exec (@delTableStmt)
			Fetch Prior From TableCursor
				Into @TableName, @TableId, @NrOfRows
		End

		--inserting rows!!!


		Fetch First From TableCursor
			Into @TableName, @TableId, @NrOfRows


		Set @startTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');
		Set @Description = 'Number of tables tested ' + CONVERT(varchar(10), @count_table_test) + ', number of views tested : ' + CONVERT(varchar(10), @count_view_test);
		
		--insert into testruns the startAt - later we update endAt for TestRuns Id we have identity(1,1)
		Insert Into TestRuns([Description], StartAt) Values(@Description, @startTestTime);
		Set @LastTestId = @@IDENTITY; --we save the last test id to work with it laster

		print @startTestTime

		

		While(@@FETCH_STATUS = 0)
		Begin
			Set @startTableTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');
			
			Exec GenerateInserts @TableId, @NrOfRows;

			Set @endTableTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

			--insert the recorded time it took to test that table into TestRunTables

			Insert Into TestRunTables(TestRunID, TableID, StartAt, EndAt) Values (@LastTestId, @TableId, @startTableTestTime, @endTableTestTime);

			Fetch Next From TableCursor
				Into @TableName, @TableId, @NrOfRows
		End
		Close TableCursor;

		If(@count_view_test > 0)--this in case there is both view and table in the test
		Begin
			
			Declare ViewCursor Cursor Local Scroll For
			Select v.Name, v.ViewID
			From Views v Inner Join TestViews testV On v.ViewID = TestV.ViewID
			Where TestV.TestID = @TestId;

			Open ViewCursor;
			Declare @ViewName Varchar(50);
			Declare @ViewStmt Varchar(70);
			Declare @ViewId Int;
			Set @ViewStmt = 'SELECT * FROM';

			Fetch Next From ViewCursor
				Into @ViewName, @ViewId

			While @@FETCH_STATUS = 0
			Begin
				
				Set @ViewStmt = 'SELECT * FROM ' + @ViewName;
				
				Set @startViewTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

				Exec (@ViewStmt);

				Set @endViewTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

				--insert the recorded time it took to test that view into TestRunViews;
				Insert Into TestRunViews(TestRunID, ViewID, StartAt, EndAt) Values(@LastTestId, @ViewId, @startViewTestTime, @endViewTestTime);


				Fetch Next From ViewCursor
					Into @ViewName, @ViewId;
			End

		End

		Set @endTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

		Update TestRuns
		Set EndAt = @endTestTime
		Where TestRunID = @LastTestId;

		print @endTestTime;
	End

	Else If @count_view_test > 0 --there is only view evaluation in the test
	Begin
		
		Set @startTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');
		Set @Description = 'Number of tables tested ' + CONVERT(varchar(10), @count_table_test) + ', number of views tested : ' + CONVERT(varchar(10), @count_view_test);

		
		--insert into testruns the startAt - later we update endAt for TestRuns Id we have identity(1,1)
		Insert Into TestRuns([Description], StartAt) Values(@Description, @startTestTime);
		Set @LastTestId = @@IDENTITY; --we save the last test id to work with it laster

		print @startTestTime

		Declare ViewCursor Cursor Local Scroll For
			Select v.Name
			From Views v Inner Join TestViews testV On v.ViewID = TestV.ViewID
			Where TestV.TestID = @TestId;

			Open ViewCursor;
			Declare @ViewName2 Varchar(50);
			Declare @ViewStmt2 Varchar(70);
			Set @ViewStmt2 = 'SELECT * FROM';

			Fetch Next From ViewCursor
				Into @ViewName2

			While @@FETCH_STATUS = 0
			Begin
				Set @ViewStmt = 'SELECT * FROM ' + @ViewName;
				
				Set @startViewTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

				Exec (@ViewStmt);

				Set @endViewTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

				--insert the recorded time it took to test that view into TestRunViews;
				Insert Into TestRunViews(TestRunID, ViewID, StartAt, EndAt) Values(@LastTestId, @ViewId, @startViewTestTime, @endViewTestTime);


				Fetch Next From ViewCursor
					Into @ViewName, @ViewId;
			End

		Set @endTestTime = FORMAT(GETDATE(), 'yyyy-MM-dd HH:mm:ss');

		Update TestRuns
		Set EndAt = @endTestTime
		Where TestRunID = @LastTestId;--update the TestRuns table with the endTIme

	End

END

GO
CREATE OR ALTER PROC GenerateInserts(@TableId INT, @NrOfRows INT)
AS
BEGIN
	Declare @TableName Varchar(50);

	Select @TableName = [Name]
	From Tables
	Where TableID = @TableId;

	--get the column name and datatype!!
	Declare ColumnCursor Cursor Local Scroll For
		SELECT c.name AS ColumnName, t.name AS DataType
		FROM sys.columns c
		JOIN sys.types t ON c.system_type_id = t.system_type_id
		WHERE object_id = OBJECT_ID(@TableName)

	Open ColumnCursor;
	Declare @ColumnName Varchar(50);
	Declare @DataType Varchar(50);

	Declare @insertStmtColumns varchar(200);
	Set @insertStmtColumns = 'INSERT INTO ' + @TableName + ' ( '
	
	Fetch Next From ColumnCursor
		Into @ColumnName, @Datatype

	Declare @isPrimary BIT;--for checking if its primary column
	Declare @isForeign BIT;-- or foreign key column

	--first we generate the insert into(columname1, columname2, ...) part -----------------------------------
	While @@FETCH_STATUS = 0
	Begin

		--check if its a primary key
		Exec @isPrimary = CheckIfColumnIsAPrimaryKey @TableName, @ColumnName
		--check if its a foreign key
		Exec @isForeign = CheckIfColumnIsAForeignKey @TableName, @ColumnName
		--if its only primary the identity(1,1) takes care of the job insert
		If (@isPrimary = 1 AND @isForeign = 1) or @isPrimary = 0
		Begin
			Set @insertStmtColumns = @insertStmtColumns + @ColumnName + ',';
		End

		Fetch Next From ColumnCursor
			Into @ColumnName, @Datatype;
	End

	--deleting the last comma
	Set @insertStmtColumns = Left(@insertStmtColumns, LEN(@insertStmtColumns)-1);
	Set @insertStmtColumns = @insertStmtColumns + ') '

	--generating the values(value1, value2, ...) part ---------------------------------
	Declare @insertStmtValues Varchar(200);

	Declare @iterator Int;
	Set @iterator = 0;
	While @iterator < @NrOfRows
	Begin

		Set @insertStmtValues = 'Values (';

		Fetch First From ColumnCursor
			Into @ColumnName, @DataType

		--get each datatype!!!
		While(@@FETCH_STATUS = 0)
		Begin
			Exec @isPrimary = CheckIfColumnIsAPrimaryKey @TableName, @ColumnName
			--check if its a foreign key
			Exec @isForeign = CheckIfColumnIsAForeignKey @TableName, @ColumnName

			Declare @ForeignTableName NVARCHAR(50);
			Declare @ForeignKeyQuery NVarchar(100); --we get a random value from the column from the foreign table	
			Declare @NewForeignKeyValueInt INT;
			Declare @NewForeignKeyValueChar NVARCHAR(101);
						
			--if its only primary the identity(1,1) takes care of the job insert
			
			If(@isPrimary = 1 AND @isForeign = 1) --in this case we need to check if the generated value is unique!
			Begin

				Exec @ForeignTableName = GetPrimaryKeysTable @ColumnName; --we get the foreign keys original table it reference

				If @DataType = 'int'
				Begin 
					Set @ForeignKeyQuery = 'SELECT TOP 1 @NewForeignKeyValueInt = ' + @ColumnName + ' FROM ' + @ForeignTableName + ' ORDER BY NEWID()'; --random value from the foreginkeytable
					
					EXEC sp_executesql @ForeignKeyQuery, N'@NewForeignKeyValueInt INT OUTPUT', @NewForeignKeyValueInt OUTPUT;
					Set @insertStmtValues = @insertStmtValues +  CONVERT(VARCHAR(20), @NewForeignKeyValueInt) + ', ';
				End
				--foreign key cannot be date in my case so i dont bother with that lol
				Else If @DataType = 'varchar'
				Begin
					Set @ForeignKeyQuery = 'SELECT TOP 1 @NewForeignKeyValueChar = ' + @ColumnName + ' FROM ' + @ForeignTableName + ' ORDER BY NEWID()';
					
					EXEC sp_executesql @ForeignKeyQuery, N'@NewForeignKeyValueChar VARCHAR(101) OUTPUT', @NewForeignKeyValueChar OUTPUT;
					Set @insertStmtValues = @insertStmtValues +  CONVERT(VARCHAR(101), @NewForeignKeyValueChar) + ', ';
				End

			End
			Else if(@isPrimary = 0 AND @isForeign = 1 )
			Begin
				Exec @ForeignTableName = GetPrimaryKeysTable @ColumnName; --we get the foreign keys original table it references
				
				If @DataType = 'int'
				Begin 
					Set @ForeignKeyQuery = 'SELECT TOP 1 @NewForeignKeyValueInt = ' + @ColumnName + ' FROM ' + @ForeignTableName + ' ORDER BY NEWID()';
					
					EXEC sp_executesql @ForeignKeyQuery, N'@NewForeignKeyValueInt INT OUTPUT', @NewForeignKeyValueInt OUTPUT;
					Set @insertStmtValues = @insertStmtValues +  CONVERT(VARCHAR(20), @NewForeignKeyValueInt) + ', ';
				End
				--foreign key cannot be date in my case so i dont bother with that lol
				Else If @DataType = 'varchar'
				Begin
					Set @ForeignKeyQuery = 'SELECT TOP 1 @NewForeignKeyValueChar = ' + @ColumnName + ' FROM ' + @ForeignTableName + ' ORDER BY NEWID()';
					
					EXEC sp_executesql @ForeignKeyQuery, N'@NewForeignKeyValueChar VARCHAR(101) OUTPUT', @NewForeignKeyValueChar OUTPUT;
					Set @insertStmtValues = @insertStmtValues +  CONVERT(VARCHAR(101), @NewForeignKeyValueChar) + ', ';
				End

			End
			Else if @isPrimary = 0 AND @isForeign = 0 --it is not conneted to anything just generate random values corresponding the datatype
			Begin
				If @DataType = 'int'
				Begin 
					Declare @IntValue INT;
					
					Exec @IntValue = RandIntBetween 0, 100000;
					Print @IntValue;
					
					Set @insertStmtValues = @insertStmtValues +  CONVERT(VARCHAR(20), @IntValue) + ',';
				End
				Else If @DataType = 'date'
				Begin
					Set @insertStmtValues = @insertStmtValues +'''' + CONVERT(VARCHAR(20),  FORMAT(getdate(), 'yyyy-MM-dd')) + ''',';
				End
				Else If @DataType = 'varchar'
				Begin
					Declare @randomString VARCHAR(101); --max length 100
					Exec @randomString = GenerateRandomString;
					Set @insertStmtValues = @insertStmtValues + @randomString + ',';
				End
			End

			Fetch Next From ColumnCursor
			Into @ColumnName, @DataType
		End

		

		Declare @insertStmt Varchar(500);

		Set @insertStmtValues = Left(@insertStmtValues, LEN(@insertStmtValues)-1); --getting rid of the last comma
		Set @insertStmtValues = @insertStmtValues + ');';
		Set @insertStmt = @insertStmtColumns + ' ' + @insertStmtValues;
		
		--print (@insertStmt);
		Exec (@insertStmt);
		--BEGIN TRY
		--Exec (@insertStmt);

		--END TRY
		--BEGIN CATCH
			--print 'An error occured with the following statement: ' + @insertStmt
		--END CATCH

		Set @iterator = @iterator + 1;
	End
	Close ColumnCursor;
End

GO
--testing

Exec RunTest 1;

Select * from TestRuns;
Select * from TestRunTables;
Select * from TestRunVIews;

delete from Books;
select * from Books;
insert into Books(Title, Author, CategoryId, OwnerId) Values('lmao', 'lmao', 1, 1);
select @@IDENTITY;

Select * from Tests;
Select * from TestTables;
Select * from TestViews;