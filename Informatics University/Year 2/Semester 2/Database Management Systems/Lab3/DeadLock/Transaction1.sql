--The solution:
SET DEADLOCK_PRIORITY HIGH

BEGIN TRAN
	UPDATE Exercise SET Orientation = 'Horizontal' WHERE ExerciseID = 1;
	WAITFOR DELAY '00:00:10';
	UPDATE Muscle SET Role = 'Push' WHERE MuscleID = 1;
COMMIT TRAN
