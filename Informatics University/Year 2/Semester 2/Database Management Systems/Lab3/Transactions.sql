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
		print('"Success');
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		print('Fail');
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
		print('Success');
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		print('Fail');
		ROLLBACK TRAN
		RETURN
	END CATCH
GO

EXEC rollBackSuccessScenario;
GO

EXEC rollbackErrorScenario;
GO

--without rollback
CREATE OR ALTER PROCEDURE noRollbackSuccessScenario
AS
	DECLARE @addExerciseSuccess BIT = 0;
	DECLARE @addMuscleSuccess BIT = 0;
	DECLARE @addUsesSuccess BIT = 0;
	DECLARE @ErrorMessage NVARCHAR(4000);
	BEGIN TRAN
	BEGIN TRY
		EXEC addExercise 'Reverse Nordic curl', 'Leg', 'Horizontal';
		SET @addExerciseSuccess = 1
		SAVE TRANSACTION addedExercise

		EXEC addMuscle 'Hamstrings', 'Leg';
		SET @addMuscleSuccess = 1
		SAVE TRANSACTION addedMuscle

		EXEC addToUses 'Reverse Nordic curl', 'Hamstrings'
		SET @addUsesSuccess = 1
		SAVE TRANSACTION addedToUses
		PRINT('All succeeded')
		COMMIT TRAN;
	END TRY
	BEGIN CATCH
		IF @@Trancount > 0
		BEGIN
			IF @addExerciseSuccess = 0
			BEGIN
				PRINT('XD1');
				ROLLBACK TRAN
			END
			ELSE IF @addMuscleSuccess = 0
			BEGIN
				PRINT('XD2');
				ROLLBACK TRAN addedExercise
				COMMIT TRAN
			END
			ELSE IF @addUsesSuccess = 0
			BEGIN
				PRINT('XD3');
				ROLLBACK TRAN addedMuscle
				COMMIT TRAN
			END
		END
		RETURN
	END CATCH
GO

CREATE OR ALTER PROCEDURE noRollbackErrorScenario
AS
	DECLARE @addExerciseSuccess BIT = 0;
	DECLARE @addMuscleSuccess BIT = 0;
	DECLARE @addUsesSuccess BIT = 0;
	DECLARE @ErrorMessage NVARCHAR(4000);
	BEGIN TRAN
	BEGIN TRY
		EXEC addExercise 'Reverse Nordic curl', 'Leg', 'Horizontal';
		SET @addExerciseSuccess = 1
		SAVE TRANSACTION addedExercise

		EXEC addMuscle NULL, 'Leg';
		SET @addMuscleSuccess = 1
		SAVE TRANSACTION addedMuscle

		EXEC addToUses 'Reverse Nordic curl', 'Hamstrings'
		SET @addUsesSuccess = 1
		SAVE TRANSACTION addedToUses
		PRINT('All succeeded')
		COMMIT TRAN;
	END TRY
	BEGIN CATCH
		IF @@Trancount > 0
		BEGIN
			IF @addExerciseSuccess = 0
			BEGIN
				PRINT('XD1');
				ROLLBACK TRAN
			END
			ELSE IF @addMuscleSuccess = 0
			BEGIN
				PRINT('XD2');
				ROLLBACK TRAN addedExercise
				COMMIT TRAN
			END
			ELSE IF @addUsesSuccess = 0
			BEGIN
				PRINT('XD3');
				ROLLBACK TRAN addedMuscle
				COMMIT TRAN
			END
		END
		RETURN
	END CATCH
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
SELECT* FROM Exercise;
SELECT * FROM Muscle;
SELECT * FROM Uses;

EXEC noRollbackSuccessScenario;
GO



