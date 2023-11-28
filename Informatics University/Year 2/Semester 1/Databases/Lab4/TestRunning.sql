Use Fitness_Guide
GO

SELECT O.name, C.*
FROM sys.objects O INNER JOIN sys.columns C ON O.object_id = C.object_id 
WHERE type = 'U'
GO

CREATE PROCEDURE InsertTestData(@TableName VARCHAR(30), @NORows INT) AS
BEGIN
	DECLARE @InsertCommand VARCHAR(50)
	SET @InsertCommand = 'INSERT ' + @TableName + ' VALUES('
	WHILE @NORows > 0
	BEGIN
		
	END
END