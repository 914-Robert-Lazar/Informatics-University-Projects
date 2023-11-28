USE Fitness_Guide
GO

--a.)
--The ids of exercises which type is push or uses a muscle with a role in leg movement
SELECT E.ExerciseID
FROM Exercise E
WHERE E.Type = 'Push'
UNION
SELECT U.ExerciseID
FROM Muscle M INNER JOIN Uses U ON M.MuscleID = U.MuscleId
WHERE M.Role = 'Leg movement'
ORDER BY E.ExerciseID DESC
GO

--The Exercises with type push or pull 
SELECT *
FROM Exercise E
WHERE E.Type = 'Push' OR E.Type = 'Pull'
GO

--b.)
--Exercises which uses both chest and triceps muscles
SELECT E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Chest'
INTERSECT
SELECT E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Triceps'
GO

--Training days in which there is the exercise pull-up and in that exercise plan there 5 training days a week
SELECT D.Name, D.ExercisePlanID
FROM DayOfTrainingWeek D INNER JOIN CarriedOut C ON D.DayOfWeekID = C.DayOfWeekID
INNER JOIN Exercise E ON C.ExerciseID = E.ExerciseID
WHERE E.Name = 'Pull-up' AND D.ExercisePlanID IN
	(SELECT EP.ExercisePlanID
	FROM ExercisePlan EP
	WHERE EP.NUmberOfDaysAWeek = 5)
GO

--c.)
--Exercises which uses chest muscles but don't use front delt muscles
SELECT TOP (3) E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Chest'
EXCEPT
SELECT E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Front delt'
GO

--Exercises which uses chest muscles but don't use front delt muscles
SELECT E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Chest' AND E.Name NOT IN 
	(SELECT E.Name
	FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
	INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
	WHERE M.Name = 'Front delt')
GO

--d.)
--Muscles which used on Monday in push/pull/leg split
SELECT DISTINCT M.Name, E.Name
FROM Muscle M FULL JOIN Uses U ON M.MuscleID = U.MuscleId
FULL JOIN Exercise E ON U.ExerciseID = E.ExerciseID
FULL JOIN CarriedOut C ON E.ExerciseID = C.ExerciseID
FULL JOIN DayOfTrainingWeek D ON C.DayOfWeekID = D.DayOfWeekID
WHERE D.ExercisePlanID = 1 AND D.Name = 'Monday'
GO

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

--e.)
--Training days in which there is the exercise pull-up and in that exercise plan there 5 training days a week
SELECT D.Name, D.ExercisePlanID
FROM DayOfTrainingWeek D INNER JOIN CarriedOut C ON D.DayOfWeekID = C.DayOfWeekID
INNER JOIN Exercise E ON C.ExerciseID = E.ExerciseID
WHERE E.Name = 'Pull-up' AND D.ExercisePlanID IN
	(SELECT EP.ExercisePlanID
	FROM ExercisePlan EP
	WHERE EP.NUmberOfDaysAWeek = 5)
GO

--Training days in which there is the exercise pull-up, in it's exercise plan there 5 training days a week and it is a weekday
SELECT DISTINCT D.Name, D.ExercisePlanID
FROM DayOfTrainingWeek D INNER JOIN CarriedOut C ON D.DayOfWeekID = C.DayOfWeekID
INNER JOIN Exercise E ON C.ExerciseID = E.ExerciseID
WHERE E.Name = 'Pull-up' AND D.ExercisePlanID IN
	(SELECT EP.ExercisePlanID
	FROM ExercisePlan EP
	WHERE EP.NUmberOfDaysAWeek = 5 AND EP.Name IN 
		(SELECT EP2.Name
		FROM ExercisePlan EP2
		WHERE EP2.Name != 'Saturday' AND Ep2.Name != 'Sunday')
	)
GO

--f.)
--Training days in which there is the exercise pull-up and in that exercise plan there 5 training days a week
SELECT D.Name, D.ExercisePlanID
FROM DayOfTrainingWeek D INNER JOIN CarriedOut C ON D.DayOfWeekID = C.DayOfWeekID
INNER JOIN Exercise E ON C.ExerciseID = E.ExerciseID
WHERE E.Name = 'Pull-up' AND EXISTS
	(SELECT EP.ExercisePlanID
	FROM ExercisePlan EP INNER JOIN DayOfTrainingWeek D2 ON EP.ExercisePlanID = D2.ExercisePlanID
	WHERE D.DayOfWeekID = D2.DayOfWeekID AND EP.NUmberOfDaysAWeek = 5)
GO

--Exercises which uses chest muscles but don't use front delt muscles
SELECT DISTINCT E.Name
FROM Exercise E INNER JOIN Uses U ON E.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
WHERE M.Name = 'Chest' AND NOT EXISTS
	(SELECT E.Name
	FROM Exercise E2 INNER JOIN Uses U2 ON E2.ExerciseID = U2.ExerciseID
	INNER JOIN Muscle M2 ON U2.MuscleId = M2.MuscleID
	WHERE E.ExerciseID = E2.ExerciseID AND M2.Name = 'Front delt')
GO

--g.)
--Vertical pushing exercises
SELECT *
FROM 
	(SELECT *
	FROM Exercise E
	WHERE E.Orientation = 'Vertical') E2
WHERE E2.Type = 'Push'
ORDER BY E2.Name DESC
GO

--Muscles with pushing role and with id greater than 2
SELECT *
FROM
	(SELECT *
	FROM Muscle M 
	WHERE M.Role = 'Push') S
WHERE S.MuscleID > 2
GO

--h.)
--Exercise type in which there is an exercise which at least uses more than 2 muscles
SELECT TOP (3) E.Type
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

--Exercise ids which uses 2 muscles
SELECT E2.ExerciseID
FROM Exercise E2 INNER JOIN Uses U ON E2.ExerciseID = U.ExerciseID
INNER JOIN Muscle M ON U.MuscleId = M.MuscleID
GROUP BY E2.ExerciseID
HAVING COUNT(*) = 2


--The ids of exercise plans in fitness plans in which the exercise plan has a determined level
SELECT F.ExercisePlanID
FROM FitnessPlan F INNER JOIN ExercisePlan EP ON F.ExercisePlanID = EP.ExercisePlanID
GROUP BY F.ExercisePlanID
HAVING F.ExercisePlanID IN
	(SELECT EP2.ExercisePlanID
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NOT NULL)
GO

--i.)
--Exercise plans with more days a week than a plan with undefined level
SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek > ANY 
	(SELECT EP2.NUmberOfDaysAWeek
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NULL)
GO

SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek >  
	(SELECT MIN(EP2.NUmberOfDaysAWeek)
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NULL)
GO

--Exercise plan with the most number of days a week with defined level
SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek = ANY 
	(SELECT Max(EP2.NUmberOfDaysAWeek)
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NOT NULL)
GO

SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek IN
	(SELECT MAX(EP2.NUmberOfDaysAWeek)
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NOT NULL)
GO

--Exercise plans with more number of days a week than all easy exercise plans
SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek > ALL
	(SELECT EP2.NUmberOfDaysAWeek
	FROM ExercisePlan EP2
	WHERE EP2.Level = 'Easy')
GO

SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek >
	(SELECT MAX(EP2.NUmberOfDaysAWeek)
	FROM ExercisePlan EP2
	WHERE EP2.Level = 'Easy')
GO

--Exercise plans with other number of days than all exercise plans with undefined level
SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek <> ALL
	(SELECT EP2.NUmberOfDaysAWeek
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NULL)
GO

SELECT *
FROM ExercisePlan EP
WHERE EP.NUmberOfDaysAWeek NOT IN
	(SELECT EP2.NUmberOfDaysAWeek
	FROM ExercisePlan EP2
	WHERE EP2.Level IS NULL)
GO


