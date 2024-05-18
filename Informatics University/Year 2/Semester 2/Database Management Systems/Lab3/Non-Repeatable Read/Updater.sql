BEGIN TRAN
	UPDATE Exercise SET Orientation = 'Horizontal' WHERE ExerciseID = 1;
COMMIT TRAN