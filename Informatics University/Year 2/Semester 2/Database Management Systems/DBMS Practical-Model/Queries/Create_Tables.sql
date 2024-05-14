USE [DBMS Practical-Model]
GO

DROP TABLE Comments
DROP TABLE Posts
DROP TABLE Likes
DROP TABLE Pages
DROP TABLE Category
DROP TABLE Users
GO

CREATE TABLE Users 
(UserID INT PRIMARY KEY IDENTITY(1,1),
Name VARCHAR(30),
City VARCHAR(30),
DateOfBirth DATE
)
GO

CREATE TABLE Category
(CategoryID INT PRIMARY KEY IDENTITY(1,1),
Name VARCHAR(30),
Description VARCHAR(30))
GO

CREATE TABLE Pages
(PageID INT PRIMARY KEY IDENTITY(1,1),
Name VARCHAR(30),
CategoryID INT REFERENCES Category(CategoryID))
GO

CREATE TABLE Likes
(UserID INT REFERENCES Users(UserID),
PageID INT REFERENCES Pages(PageID),
LikeDate DATE,
CONSTRAINT PK_Likes PRIMARY KEY (UserID, PageID)
)
GO

CREATE TABLE Posts
(PostID INT PRIMARY KEY IDENTITY(1,1),
PostDate DATE,
PostText VARCHAR(50),
NumberOfShares INT,
UserID Int REFERENCES Users(UserID)
)
GO

CREATE TABLE Comments
(CommentID INT PRIMARY KEY IDENTITY(1,1),
CommentText VARCHAR(50),
CommentDate DATE,
TopComment BIT,
PostID INT REFERENCES Posts(PostID)
)
GO

INSERT INTO Users (Name, City, DateOfBirth) VALUES('U1', 'b', '2022-02-02');
INSERT INTO Users (Name, City, DateOfBirth) VALUES('U2', 'd', '2022-02-04');

INSERT INTO Posts (PostText, PostDate, NumberOfShares, UserID) VALUES('P1', '2022-02-04', 4, 1);
INSERT INTO Posts (PostText, PostDate, NumberOfShares, UserID) VALUES('P2', '2023-02-04', 3, 1);
INSERT INTO Posts (PostText, PostDate, NumberOfShares, UserID) VALUES('P3', '2024-02-04', 2, 2);

SELECT * FROM Users
GO
SELECT * FROM Posts
GO
