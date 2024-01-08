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

--clustered index scan
SELECT* FROM DietPlan;
GO

SELECT* FROM DietPlan D
WHERE D.