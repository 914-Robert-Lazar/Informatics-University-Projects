USE MPP
CREATE LOGIN Robertusos2 WITH PASSWORD = 'szialajos';
CREATE USER Robertusos2 FOR LOGIN Robertusos2;
ALTER ROLE db_owner ADD MEMBER Robertusos2;