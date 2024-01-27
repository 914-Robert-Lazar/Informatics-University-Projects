USE Shoes_Prac_Exam_Tutorial2
GO

CREATE TABLE PresentationShop
(ShopID INT PRIMARY KEY IDENTITY(1,1),
ShopName VARCHAR(30),
City VARCHAR(30))
GO

CREATE TABLE Woman
(WomanID INT PRIMARY KEY IDENTITY(1,1),
WomanName VARCHAR(30),
MaxToSpend INT)
GO

CREATE TABLE ShoeModel
(ModelID INT PRIMARY KEY IDENTITY(1,1),
ModelName VARCHAR(30),
Season VARCHAR(20))
GO

CREATE TABLE Shoe
(ShoeID INT PRIMARY KEY IDENTITY(1,1),
Price INT,
ModelID INT REFERENCES ShoeModel(ModelID))
GO

CREATE TABLE AvailableShoes
(AvailableID INT PRIMARY KEY IDENTITY(1,1),
ShoeID INT REFERENCES Shoe(ShoeID),
ShopID INT REFERENCES PresentationShop(ShopID),
NOShoes INT)
GO

CREATE TABLE Purchase
(PurchaseID INT PRIMARY KEY IDENTITY(1,1),
ShoeID INT REFERENCES Shoe(ShoeID),
WomanID INT REFERENCES Woman(WomanID),
NOShoesBought INT,
SpentAmount INT)
GO

INSERT INTO Woman (WomanName, MaxToSpend) VALUES ('Ilona', 3243), ('Ernesztina', 225), ('Emese', 721), ('Tulipan', 1632)
INSERT INTO ShoeModel (ModelName, Season) VALUES ('T800', 'Spring'), ('sadaa', 'Summer'), ('3223435', 'Autumn'), ('Frosty', 'Winter')
INSERT INTO Shoe (Price, ModelID) VALUES (123, 1), (443, 1), (543, 2), (55, 4), (1213, 4), (543, 4)
INSERT INTO Purchase (ShoeID, WomanID, NOShoesBought, SpentAmount) VALUES (1, 1, 1, 453), (2, 1, 1, 55), (5, 2, 3, 22), (3, 4, 1, 445), (4, 1, 1, 333)
INSERT INTO PresentationShop (ShopName, City) VALUES ('sadsadsa', 'sfsada'), ('geeee', 'ggewwsx'), ('dsadsa', 'berwasx')
INSERT INTO AvailableShoes (ShoeID, ShopID, NOShoes) VALUES (1, 1, 2), (1, 2, 3), (1, 3, 4), (2, 1, 1), (2, 2, 4), (4, 3, 5), (5, 2, 3), (5, 3, 6)
GO

CREATE OR ALTER PROCEDURE uspAddShoe (@ShoeID INT, @ShopId INT, @Amount INT) AS
	INSERT INTO AvailableShoes (ShoeID, ShopID, NOShoes) VALUES (@ShoeID, @ShopId, @Amount)
GO

CREATE OR ALTER VIEW WomanWithMin2Shoes AS
	SELECT DISTINCT W2.WomanID, W2.WomanName 
	FROM Woman W2 INNER JOIN 
		(SELECT W.WomanID, M.ModelID, SUM(P.NOShoesBought) AS NOBought
		FROM Woman W INNER JOIN Purchase P ON W.WomanID = P.WomanID
		INNER JOIN Shoe S ON S.ShoeID = P.ShoeID
		INNER JOIN ShoeModel M ON M.ModelID = S.ModelID
		GROUP BY W.WomanID, M.ModelID) T ON w2.WomanID = T.WomanID
	WHERE T.NOBought > 1
GO

CREATE OR ALTER FUNCTION ShoesInTShops(@T INT)
RETURNS TABLE
AS
	RETURN 
		(SELECT S2.ShoeID, S2.Price, S2.ModelID
		FROM Shoe S2 INNER JOIN 
			(SELECT A.ShoeID
			FROM Shoe S INNER JOIN AvailableShoes A ON S.ShoeID = A.ShoeID
			GROUP BY A.ShoeID
			HAVING COUNT(*) >= @T) T ON S2.ShoeID = T.ShoeID
		)
GO

SELECT* FROM ShoesInTShops(3)