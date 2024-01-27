USE Library
GO

Create View ViewTable5 As
	Select CB.CustomerId as CustomerId, C.[Name]
	From Customers C Inner Join CustomersBooks CB on
	C.CustomerId = CB.CustomerId
	Where C.[Name] = 'George'
Go

--a)

CREATE NONCLUSTERED			
INDEX NonClusteredBookIndex
	ON Books(SerialNumber)

--Drop Index NonClusteredBookIndex ON Books;

select * from Books Order By BookId; --clustered scan

select * from  Books Where BookId = 1; --clustered seek

Select serialnumber, bookid from Books Order by SerialNumber --nonclustered scan

Select SerialNumber from Books
Where SerialNumber = 44; --nonclustered seek 

--b)
CREATE NONCLUSTERED 
INDEX NonClusteredCustomersIndex
	ON Customers([Name])

Select Customers.[Surname] from Customers
Where Name = 'George';

--c)
Select * from ViewTable5;

sp_helpindex Books;

