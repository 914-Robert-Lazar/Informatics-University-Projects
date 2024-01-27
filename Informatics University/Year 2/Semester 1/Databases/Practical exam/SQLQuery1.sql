USE Practical_Exam
GO

CREATE TABLE FerryBoat
(BoatID INT PRIMARY KEY IDENTITY(1,1),
BoatName VARCHAR(30),
Capacity INT,
BoatType VARCHAR(30))
GO

CREATE TABLE BoatPort
(PortID INT PRIMARY KEY IDENTITY(1,1),
PortName VARCHAR(30),
PortType VARCHAR(30),
Capacity INT)
GO

CREATE TABLE Driver
(DriverID INT PRIMARY KEY IDENTITY(1,1),
DriverName VARCHAR(30))
GO

CREATE TABLE Trip
(TripID INT PRIMARY KEY IDENTITY(1,1),
DepartureTime DATETIME,
Distance INT,
TicketPrice INT,
DeparturePortID INT REFERENCES BoatPort(PortID),
DestiantionPortID INT REFERENCES BoatPort(PortID),
BoatID INT REFERENCES FerryBoat(BoatID))
GO

CREATE TABLE TripWithDriver
(TripWithDriverID INT PRIMARY KEY IDENTITY(1,1),
TripID INT REFERENCES Trip(TripID),
DriverID INT REFERENCES Driver(DriverID))
GO

CREATE TABLE Ticket
(TicketID INT PRIMARY KEY IDENTITY(1,1),
TripWithDriverID INT UNIQUE REFERENCES TripWithDriver(TripWithDriverID))
GO

INSERT INTO Driver (DriverName) VALUES ('sdsdsds'), ('frwgew'), ('grwfeq'), ('vewqq')
INSERT INTO FerryBoat (BoatName, Capacity, BoatType) VALUES ('asasa', 50, 'E'), ('sadsad', 54, 'ff'), ('adsdsa', 63, 'trwe')
INSERT INTO BoatPort (PortName, PortType, Capacity) VALUES ('dsadsadd', 'treww', 432), ('dsfdsf', 'ewrf', 423), ('ghytrfv', 'fdcfr', 754)
INSERT INTO Trip (DepartureTime, Distance, TicketPrice, DeparturePortID, DestiantionPortID, BoatID) VALUES (GETDATE(), 32, 45, 1, 2, 1), (GETDATE(), 55, 65, 3, 2, 1),
(GETDATE(), 22, 50, 2, 1, 2), (GETDATE(), 54, 39, 1, 3, 3)
INSERT INTO TripWithDriver (TripID, DriverID) VALUES (1, 1), (1, 2), (3, 1), (4, 2), (2, 2), (4, 3), (4, 4)
INSERT INTO Ticket (TripWithDriverID) VALUES (1), (2), (3), (5), (6)
GO

CREATE OR ALTER PROCEDURE uspDeleteDrivers(@DriverName VARCHAR(30)) AS
BEGIN
	DELETE FROM Ticket
	WHERE TicketID IN 
		(SELECT T.TicketID
		FROM Ticket T INNER JOIN
		TripWithDriver TW ON T.TripWithDriverID = TW.TripWithDriverID
		INNER JOIN Driver D ON D.DriverID = TW.DriverID
		WHERE D.DriverName = @DriverName)

	DELETE FROM Driver
	WHERE DriverName = @DriverName
END
GO

CREATE OR ALTER VIEW DriverWithLotOfTrips AS
	(SELECT D2.DriverName
	FROM Driver D2 INNER JOIN 
		(SELECT TW.DriverID
		FROM Driver D INNER JOIN TripWithDriver TW ON D.DriverID = TW.DriverID
		INNER JOIN Trip T ON T.TripID = TW.TripID
		GROUP BY TW.DriverID
		HAVING COUNT(T.TripID) >= 20 AND SUM(T.TicketPrice) >= 500) A ON D2.DriverID = A.DriverID
	)
GO

SELECT * FROM DriverWithLotOfTrips
GO

CREATE OR ALTER FUNCTION PortWithTAppearance (@T INT) 
RETURNS TABLE AS

	RETURN 
		(SELECT B.PortName
		FROM BoatPort B 
		WHERE PortID IN
			(SELECT PortID
			FROM
				(SELECT *
				FROM BoatPort P INNER JOIN Trip T ON P.PortID = T.DeparturePortID
				UNION
				SELECT *
				FROM BoatPort P2 INNER JOIN Trip T2 ON P2.PortID = T2.DestiantionPortID
				) A
			GROUP BY PortID
			HAVING COUNT(*) >= @T)
		)
GO

