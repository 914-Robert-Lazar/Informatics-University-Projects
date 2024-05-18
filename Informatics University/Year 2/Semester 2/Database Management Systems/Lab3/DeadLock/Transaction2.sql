
BEGIN TRAN
	UPDATE Muscle SET Role = 'Pull' WHERE MuscleID = 1;
	WAITFOR DELAY '00:00:10';
	UPDATE Exercise SET Orientation = 'Vertical' WHERE ExerciseID = 1;
COMMIT TRAN