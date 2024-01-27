Use Library 
Go

Create View ViewTable1 As
	Select * from Books
Go


Create View ViewTable2 As
	Select CB.CustomerId as CustomerId, Cb.BookId, C.DateOfBirth
	From Customers C Inner Join CustomersBooks CB on
	C.CustomerId = CB.CustomerId
Go

Create View ViewTable3 as 
	Select B.OwnerId, Count(*) as Books
	From Books B Inner Join Owners O on
	B.OwnerId = O.OwnerId
	Group By B.OwnerId;
GO

Insert Into Views ([Name]) Values ('ViewTable1');
Insert Into Views ([Name]) Values ('ViewTable2');
Insert Into Views ([Name]) Values ('ViewTable3');

Delete from Views Where ViewID > 3;

Insert Into TestViews (TestID, ViewID) Values(1,1);
Insert Into TestViews (TestID, ViewID) Values(1,2);
Insert Into TestViews (TestID, ViewID) Values(1,3);

Insert Into TestViews (TestID, ViewID) Values(2003,1);

Insert Into TestViews (TestId, ViewID) Values(2004, 1);
Insert Into TestViews (TestId, ViewID) Values(2004, 3);


Select * from TestViews;
Select * from Views;

Select * from INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS;
Select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;

select * from CustomersBooks;