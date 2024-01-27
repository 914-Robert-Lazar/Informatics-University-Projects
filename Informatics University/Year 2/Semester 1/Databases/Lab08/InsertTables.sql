Use Library 
Go

Insert Into Tables ([Name]) Values ('Books');
Insert Into Tables ([Name]) Values ('CustomersBooks');
Insert Into Tables ([Name]) Values ('Customers');
select * from Tables;

Insert Into Tests ([Name]) Values ('Test all 3 tables');
Insert Into Tests ([Name]) Values ('Test table Books and View1');
Insert Into Tests ([Name]) Values ('Test table Books and Customers and View1, View3');

select * from Tests;
--test1
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (1, 4, 1000, 1); 
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (1, 6, 1000, 2); 
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (1, 5, 1000, 3);
--test2
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (2003, 4, 1000, 1);
--test3
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (2004, 4, 1000, 1);
Insert Into TestTables (TestID, TableID, NoOfRows, Position) Values (2004, 6, 1000, 2); 

select * from TestTables;

Delete from Tests Where TestID = 1002;
---------------------------------------------------------------------------------------------
--sysdatetime() getdate() 
--here we have all the procedures that i use for the testruns
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

GO
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




--testing
GO
SELECT t.name as TableName, i.name, i.index_id, i.type, i.type_desc, c.name as KeyName
FROM sys.tables t INNER JOIN sys.indexes i ON t.object_id = i.object_id
	INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id
		AND i.index_id = ic.index_id
	INNER JOIN sys.columns c ON ic.object_id = c.object_id
		AND ic.column_id = c.column_id
WHERE i.is_primary_key = 1 And t.name = 'Owners' and c.name = 'OwnerId';
--

SELECT c.name AS ColumnName, t.name AS DataType
FROM sys.columns c
JOIN sys.types t ON c.system_type_id = t.system_type_id
WHERE object_id = OBJECT_ID('Books')

--
declare @testtable Varchar(50);
declare @testcolumn Varchar(50);

set @testtable = 'Books';
set @testcolumn = 'Title';
declare @res bit;
EXEC @res = CheckIfColumnIsAPrimaryKey @testtable, @testcolumn
print @res;
--
--randoom
declare @tblnm varchar(20);
declare @clmnm varchar(20);
set @tblnm = 'Books';
set @clmnm = 'BookId';

DECLARE @foreignquery NVARCHAR(200);
DECLARE @gotbookid INT;

SET @foreignquery = 'SELECT TOP 1 @gotbookid = CategoryId FROM Category ORDER BY NEWID()';

EXEC sp_executesql @foreignquery, N'@gotbookid INT OUTPUT', @gotbookid OUTPUT;
print @gotbookid;
Select top 1 CategoryId From Category Order By NEWID();

select * from sys.tables syst
	Inner Join Tables t on syst.name = t.Name
	Where t.Name = @tblnm)
select top 1 @clmnm
From @tblnm as t
Order By NEWID()

Case 
When @DataType = 'int' Then --
When @DataType = 'date' Then --
When @DataType = 'varchar' Then --
End

--
declare @testchar varchar(5);
set @testchar = CHAR(ROUND(RAND() * 25 + 65, 0));
print @testchar;

select * from INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS;
select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;
select * from INFORMATION_SCHEMA.TABLES;
select * from 
---
declare @testtable Varchar(50);
Select @testtable = R.name From
	(SELECT t.name
				FROM sys.tables t INNER JOIN sys.indexes i ON t.object_id = i.object_id
					INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id
						AND i.index_id = ic.index_id
					INNER JOIN sys.columns c ON ic.object_id = c.object_id
						AND ic.column_id = c.column_id
				WHERE i.is_primary_key = 1 AND c.name = 'BookId'
	EXCEPT
		SELECT KCU.TABLE_NAME
			FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC
				JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU
				ON RC.CONSTRAINT_CATALOG = KCU.CONSTRAINT_CATALOG
					AND RC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME
				WHERE KCU.COLUMN_NAME = 'BookId')R;
print @testtable

declare @tablename varchar(100);

Exec @tablename = GetPrimaryKeysTable 'CustomerId';
print @tablename;
--valassz ki egy random id-t ebbol a tablebol!!! next task!!


select * from Tests;

Exec RunTest 1;

select * from Books;