USE Fitness_Guide
GO

ALTER TABLE DietPlan
ADD AverageCalory INT
GO

ALTER TABLE DietPlan
ADD CONSTRAINT UK_AverageCalory_DietPlan UNIQUE(AverageCalory);
GO

ALTER TABLE DayOfTrainingWeek
DROP CONSTRAINT FK_DayOfTrainingWeek_To_FitnessPlan;
GO

ALTER TABLE FitnessPlan
DROP CONSTRAINT PK__FitnessP__3719322FA5379F44;
GO

ALTER TABLE FitnessPlan
ADD FitnessPlanId INT PRIMARY KEY IDENTITY(1,1)
GO

--a.)
--clustered index scan
SELECT* FROM DietPlan;
GO

--clustered index seek
SELECT* FROM DietPlan
WHERE DietPlanID > 1;

--non-clustered index scan
SELECT DietPlanID
FROM DietPlan
GO

--non-clustered index seek
SELECT * FROM DietPlan
WHERE AverageCalory = 2100;
GO

SELECT Name, DietPlanID, AverageCalory, Level
FROM DietPlan
WHERE AverageCalory = 2000 AND DietPlanID = 2
GO

--b.)
CREATE NONCLUSTERED INDEX NODaysIndex
	ON dbo.ExercisePlan(NUmberOfDaysAWeek)
GO

SELECT* FROM ExercisePlan
WHERE NUmberOfDaysAWeek = 5;
GO

--c.)
CREATE VIEW ViewLab5 AS
	SELECT E.Name, E.ExercisePlanID, E.Level, E.NUmberOfDaysAWeek, F.FitnessPlanId
	FROM ExercisePlan E INNER JOIN FitnessPlan F ON E.ExercisePlanID = F.ExercisePlanID
	WHERE NUmberOfDaysAWeek = 5;
GO

SELECT* FROM ViewLab5;
