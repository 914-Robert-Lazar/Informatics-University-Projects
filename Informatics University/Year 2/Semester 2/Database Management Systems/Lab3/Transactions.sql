USE Fitness_Guide
GO

DROP TABLE IF EXISTS LogTable 
CREATE TABLE LogTable(
	Lid INT IDENTITY(1,1) PRIMARY KEY,
	TypeOperation VARCHAR(50),
	TableOperation VARCHAR(50),
	ExecutionDate DATETIME
);
GO

CREATE OR ALTER PROCEDURE addExercise(@name VARCHAR(MAX), @type VARCHAR(MAX), @orientation VARCHAR(MAX)) AS
	IF (@name IS NULL)
	BEGIN
		RAISERROR('Name of exercise must not be null', 16, 1);
	END
	IF (@type IS NULL)
	BEGIN
		RAISERROR('Type of exercise must not be null', 16, 1);
	END
	IF (@orientation IS NULL)
	BEGIN
		RAISERROR('Orientation of exercise must not be null', 16, 1);
	END

	INSERT INTO Exercise (Name, Type, Orientation) VALUES (@name, @type, @orientation)
	INSERT INTO LogTable VALUES('add','exercise',GETDATE())
GO

CREATE OR ALTER PROCEDURE addMuscle(@name VARCHAR(MAX), @role VARCHAR(MAX)) AS
	IF (@name IS NULL)
	BEGIN
		RAISERROR('Name of exercise must not be null', 16, 1);
	END
	IF (@role IS NULL)
	BEGIN
		RAISERROR('Type of exercise must not be null', 16, 1);
	END

	INSERT INTO Muscle(Name, Role) VALUES (@name,@role)
	INSERT INTO LogTable VALUES('add','muscle',GETDATE())
GO

CREATE OR ALTER PROCEDURE addToUses(@ExerciseName VARCHAR(MAX), @muscleName VARCHAR(MAX)) AS
	DECLARE @foundExerciseID INT
	SET @foundExerciseID = (SELECT E.ExerciseID FROM Exercise E WHERE E.Name = @ExerciseName)
	DECLARE @foundMuscleID INT
	SET @foundMuscleID = (SELECT M.MuscleID FROM Muscle M WHERE M.Name = @muscleName)

	IF (@foundExerciseID is null)
	BEGIN
		RAISERROR('Exercise does not exist', 16, 1);
	END
	IF (@foundMuscleID is null)
	BEGIN
		RAISERROR('Muscle does not exist', 16, 1);
	END

	INSERT INTO Uses(ExerciseID, MuscleId) VALUES (@foundExerciseID, @foundMuscleID);
	INSERT INTO LogTable VALUES('add','uses',GETDATE())
GO

--With rollback
CREATE OR ALTER PROCEDURE rollBackSuccessScenario
AS
	BEGIN TRAN
	BEGIN TRY
		EXEC addExercise 'Muscle-up', 'Push/Pull', 'Vertical';

		EXEC addMuscle 'Forearm', 'Grip';

		EXEC addToUses 'Muscle-up', 'Forearm'
		print('Siker');
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		print('Kudarc');
		ROLLBACK TRAN
		RETURN
	END CATCH
GO

CREATE OR ALTER PROCEDURE rollbackErrorScenario
AS
	BEGIN TRAN
	BEGIN TRY
		EXEC addExercise 'Muscle-up', 'Push/Pull', 'Vertical';

		EXEC addMuscle 'Forearm', 'Grip';

		EXEC addToUses 'Muscle-up', 'Forearms'
		print('Siker');
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		print('Kudarc');
		ROLLBACK TRAN
		RETURN
	END CATCH
	print('Siker');
	COMMIT TRAN
GO

EXEC rollBackSuccessScenario;
GO

EXEC rollbackErrorScenario;
GO

--without rollback
CREATE OR ALTER PROCEDURE noRollbackSuccessScenario
AS
	DECLARE @ERRORS INT
	SET @ERRORS = 0
	BEGIN TRY
		EXEC addExercise 'Reverse Nordic curl', 'Leg', 'Horizontal';
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	BEGIN TRY
		EXEC addMuscle 'Hamstrings', 'Leg';
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH
	PRINT(@ERRORS);
	IF (@ERRORS = 0) BEGIN
		BEGIN TRY
			EXEC addToUses 'Reverse Nordic curl', 'Hamstrings'
		END TRY
		BEGIN CATCH
		END CATCH
	END
GO

CREATE OR ALTER PROCEDURE noRollbackErrorScenario
AS
	DECLARE @ERRORS INT
	SET @ERRORS = 0
	BEGIN TRY
		EXEC addExercise 'Reverse Nordic curl', 'Leg', 'Horizontal';
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	BEGIN TRY
		EXEC addMuscle NULL, 'Leg';
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH
	
	IF (@ERRORS = 0) BEGIN
		BEGIN TRY
			EXEC addToUses 'Reverse Nordic curl', 'Hamstrings'
		END TRY
		BEGIN CATCH
		END CATCH
	END
GO

EXEC noRollbackErrorScenario;
GO

DELETE FROM Uses
WHERE ExerciseID > 7

DELETE FROM Muscle
WHERE MuscleID > 7

DELETE FROM Exercise
WHERE ExerciseID > 7


SELECT * FROM LogTable;

EXEC noRollbackSuccessScenario;
GO



