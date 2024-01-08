USE Fitness_Guide
GO

CREATE OR ALTER VIEW ConsumedMealsOnDays AS
	SELECT D.Name AS DayOfWeekName, COUNT(*) AS NOMeals
	FROM Meal M INNER JOIN Consumed C ON M.MealID = C.MealID
	INNER JOIN DayOfTrainingWeek D ON C.DayOfWeekID = D.DayOfWeekID
	GROUP BY D.Name
GO

CREATE OR ALTER VIEW MealWithMoreThan1000Calory AS
	SELECT M.Name, M.TimeToPrepare, M.CaloryIntake
	FROM Meal M
	WHERE M.CaloryIntake > 1000
GO

CREATE OR ALTER VIEW ExercisesUsingLegMuscles AS
	SELECT E.Name, E2.NOMuscles
	FROM Exercise E INNER JOIN
		(SELECT E.ExerciseID, COUNT(*) AS NOMuscles
		FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
		INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
		WHERE M.Role = 'Leg movement'
		GROUP BY E.ExerciseID) E2 ON E.ExerciseID = E2.ExerciseID
GO

INSERT INTO Views (Name) VALUES
('ConsumedMealsOnDays'), ('MealWithMoreThan1000Calory'), ('ExercisesUsingLegMuscles');
GO


SELECT * FROM Views;

INSERT INTO TestViews (TestID, ViewID) VALUES
(2, 1), (2, 2), (2, 3), (3, 2), (3, 3);
