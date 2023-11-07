USE Fitness_Guide
GO

INSERT INTO Muscle (Name, Role) VALUES
('Front delt', 'Pushing'), 
('Chest', 'Pushing'), 
('Triceps', 'Pushing'), 
('Lats', 'Pulling'), 
('Biceps', 'Pulling'), 
('Calf', 'Leg movement'), 
('Glutes', 'Leg movement') 

INSERT INTO Exercise (Name, Type, Orientation) VALUES
('Push-up', 'Push', 'Horizontal'), 
('Pull-up', 'Pull', 'Vertical'), 
('Dips', 'Push', 'Vertical'), 
('Pistol squat', 'Leg', 'Vertical'), 
('Hip raises', 'Leg', 'Horizontal'), 
('Bodyrows', 'Pull', 'Horizontal'), 
('Pike push-up', 'Push', 'Vertical') 

INSERT INTO Uses (ExerciseID, MuscleId) VALUES
(1, 2), (1, 3), (2, 4), (2, 5), (3, 2), (3, 3), (3, 1), (4, 7), (5, 7), (4, 6), (6, 4), (6, 5), (7, 1), (7, 3)

INSERT INTO CarriedOut(DayOfWeekID, ExerciseID) VALUES
(1, 1)

INSERT INTO ExercisePlan(Name, Level, NUmberOfDaysAWeek) VALUES
('Push/pull/leg split', 'Intermediate', 5),
('Upper body/Lower body split', NULL, 4),
('Full body split', 'Easy', 3)

INSERT INTO DietPlan(Name, Level) VALUES
('Low in fat diet', NULL),
('Frequent salad diet', 'Easy')

INSERT INTO FitnessPlan(ExercisePlanID, DietPlanID) VALUES
(1, 2), (1, 1), (2, 1)

INSERT INTO DayOfTrainingWeek(Name, ExercisePlanID, DietPlanID) VALUES
('Monday', 1, 2),
('Tuesday', 1, 2),
('Wednesday', 1, 2),
('Friday', 1, 2),
('Saturday', 1, 2),
('Monday', 2, 1),
('Wednesday', 2, 1)

INSERT INTO CarriedOut(DayOfWeekID, ExerciseID) VALUES
(2, 3), (2, 1), (3, 2), (3, 6), (4, 4), (4, 5), (5, 7), (5, 1), (6, 2), (6, 6), (7, 3), (7, 2), (7, 6)

UPDATE Muscle
SET Role = 'Push'
WHERE Role = 'Pushing'

UPDATE M
SET M.Role = 'Pull'
FROM Muscle M
INNER JOIN Uses U ON M.MuscleID = U.MuscleId
INNER JOIN Exercise E ON U.ExerciseID = E.ExerciseID
WHERE E.Type = 'Pull'

DELETE ExercisePlan
WHERE NUmberOfDaysAWeek < 4 AND Level != NULL