USE Fitness_Guide
GO

INSERT INTO Tables (Name) VALUES 
('Meal'), ('Consumed'), ('FitnessCalendar');
GO

SELECT * FROM Tables;
GO

INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) VALUES
(1, 1, 1000, 1), (1, 2, 1000, 2), (1, 3, 1000, 3), --test1
(3, 3, 1000, 1);--test3
GO

SELECT* FROM TestTables;
GO
--Procedures Used For Test Runs:




