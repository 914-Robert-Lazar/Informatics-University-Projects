USE Fitness_Guide
GO

--Muscles which used on Monday in push/pull/leg split
SELECT M.Name, E.Name
FROM Muscle M FULL JOIN Uses U ON M.MuscleID = U.MuscleId
FULL JOIN Exercise E ON U.ExerciseID = E.ExerciseID
FULL JOIN CarriedOut C ON E.ExerciseID = C.ExerciseID
FULL JOIN DayOfTrainingWeek D ON C.DayOfWeekID = D.DayOfWeekID
WHERE D.ExercisePlanID = 1 AND D.Name = 'Monday'
GO

--Exercise type in which there is an exercise which at least uses more than 2 muscles
SELECT E.Type
FROM Exercise E
GROUP BY E.Type
HAVING E.Type IN
	(SELECT E3.Type
	FROM Exercise E3
	WHERE E3.ExerciseID IN 
		(SELECT E2.ExerciseID
		FROM Exercise E2 INNER JOIN Uses U ON E2.ExerciseID = U.ExerciseID
		INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
		GROUP BY E2.ExerciseID
		HAVING COUNT(*) > 2))

--Exercise types with number of exercises which uses exactly 2 muscles
SELECT E.Type, COUNT(*) AS NoExercisesUsesGivenNOMuscles
FROM Exercise E
WHERE E.ExerciseID IN 
	(SELECT E2.ExerciseID
	FROM Exercise E2 INNER JOIN Uses U ON E2.ExerciseID = U.ExerciseID
	INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
	GROUP BY E2.ExerciseID
	HAVING COUNT(*) = 2)
GROUP BY E.Type

SELECT E2.ExerciseID, COUNT(*)
FROM Exercise E2 INNER JOIN Uses U ON E2.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
GROUP BY E2.ExerciseID
HAVING COUNT(*) = 2

--Training weekdays in an exercise plan with more than 4 days a week
SELECT D.Name, E.Name, E.Level, E.NUmberOfDaysAWeek
FROM DayOfTrainingWeek D LEFT JOIN FitnessPlan F ON D.ExercisePlanID = F.ExercisePlanID AND D.DietPlanID = F.DietPlanID
LEFT JOIN ExercisePlan E ON F.ExercisePlanID = E.ExercisePlanID
WHERE E.NUmberOfDaysAWeek > 4 AND D.Name <> 'Saturday' AND D.Name <> 'Sunday'

--Exercises at weekends
SELECT D.Name, E.Name, E.Type, E.Orientation
FROM DayOfTrainingWeek D RIGHT JOIN CarriedOut C ON D.DayOfWeekID = C.DayOfWeekID
RIGHT JOIN Exercise E ON C.ExerciseID = E.ExerciseID
WHERE D.Name = 'Saturday' OR D.Name = 'Sunday'