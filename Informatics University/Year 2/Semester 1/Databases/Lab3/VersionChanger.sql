USE Fitness_Guide
GO

CREATE PROCEDURE ChangeColumn AS
	ALTER TABLE Meal
	ALTER COLUMN CaloryIntake VARCHAR(20) NOT NULL
GO

CREATE PROCEDURE UndoChangeColumn AS
	ALTER TABLE Meal
	ALTER COLUMN CaloryIntake INT NULL
GO

CREATE PROCEDURE AddColumn AS
	ALTER TABLE FitnessCalendar
	ADD Notes VARCHAR(50) NULL
GO

CREATE PROCEDURE UndoAddColumn AS
	ALTER TABLE FitnessCalendar
	DROP COLUMN Notes
GO

CREATE PROCEDURE AddDefault AS
	ALTER TABLE DietPlan
	ADD CONSTRAINT defaultForLevel DEFAULT 'Intermediate' FOR Level
GO

CREATE PROCEDURE UndoAddDefault AS
	ALTER TABLE DietPlan
	DROP CONSTRAINT defaultForLevel
GO

CREATE PROCEDURE RemovePrimaryKey AS
	ALTER TABLE FitnessCalendar
	DROP CONSTRAINT PK_FitnessC
GO

CREATE PROCEDURE UndoRemovePrimaryKey AS
	ALTER TABLE FitnessCalendar
	ADD CONSTRAINT PK_FitnessC PRIMARY KEY(DayOfWeekID, DateOfDay)
GO

CREATE PROCEDURE AddCandidateKey AS
	ALTER TABLE Exercise
	ADD CONSTRAINT CandidateKey_Name_For_Exercise UNIQUE (Name)
GO

CREATE PROCEDURE UndoAddCandidateKey AS
	ALTER TABLE Exercise
	DROP CONSTRAINT CandidateKey_Name_For_Exercise
GO

CREATE PROCEDURE RemoveForeignKey AS
	ALTER TABLE DayOfTrainingWeek
	DROP CONSTRAINT FK_DayOfTrainingWeek_To_FitnessPlan
GO

CREATE PROCEDURE UndoRemoveForeignKey AS
	ALTER TABLE DayOfTrainingWeek
	ADD CONSTRAINT FK_DayOfTrainingWeek_To_FitnessPlan FOREIGN KEY (ExercisePlanID, DietPLanID) REFERENCES FitnessPlan(ExercisePlanID, DietPLanID)
GO

CREATE PROCEDURE CreateTableCoach AS
	CREATE TABLE Coach
	(CoachID INT PRIMARY KEY IDENTITY(1,1),
	Name VARCHAR(40) NOT NULL,
	YearsOfExperience INT,
	HighestEducation VARCHAR(30))
GO

CREATE PROCEDURE UndoCreateTableCoach AS
	DROP TABLE Coach
GO

CREATE TABLE VersionChangerProcedures
(uspUp VARCHAR(30),
uspDown VARCHAR(30),
targetVersion INT)
GO

INSERT INTO VersionChangerProcedures VALUES
('ChangeColumn', 'UndoChangeColumn', 1),
('AddColumn', 'UndoAddColumn', 2),
('AddDefault', 'UndoAddDefault', 3),
('RemovePrimaryKey', 'UndoRemovePrimaryKey', 4),
('AddCandidateKey', 'UndoAddCandidateKey', 5),
('RemoveForeignKey', 'UndoRemoveForeignKey', 6)
GO

INSERT INTO VersionChangerProcedures VALUES
('CreateTableCoach', 'UndoCreateTableCoach', 7)
GO

CREATE TABLE CurrentVersion
(crtVersion INT)
GO

INSERT INTO CurrentVersion VALUES
(0)
GO

CREATE PROCEDURE ChangeVersion(@toVersion INT) 
AS
BEGIN
	DECLARE @CurrVersion INT
	SET @CurrVersion = (SELECT * FROM CurrentVersion)
	WHILE @toVersion != @CurrVersion
	BEGIN
		DECLARE @CurrProc VARCHAR(30)
		IF @CurrVersion < @toVersion
			BEGIN
				SET @CurrProc = (SELECT uspUp FROM VersionChangerProcedures WHERE targetVersion = @CurrVersion + 1)
				EXEC @CurrProc;
				PRINT(@CurrProc)
				SET @CurrVersion = @CurrVersion + 1
				UPDATE CurrentVersion
				SET crtVersion = @toVersion
			END
		ELSE
			BEGIN
				SET @CurrProc = (SELECT uspDown FROM VersionChangerProcedures WHERE targetVersion = @CurrVersion)
				EXEC @CurrProc
				PRINT(@CurrProc)
				SET @CurrVersion = @CurrVersion - 1
				UPDATE CurrentVersion
				SET crtVersion = @toVersion
			END
	END
END
GO

EXEC ChangeVersion @toVersion = 0
GO
