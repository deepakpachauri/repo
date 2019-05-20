

--CREATING KEYSPACE FOR LMS DATABASE
CREATE KEYSPACE IF NOT EXISTS LMS with REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

--CREATING TABLES

--CREATE TABLE STUDENT
CREATE TABLE Student (
	StudentId UUID PRIMARY KEY, 
	FirstName Text, 
	LastName Text, 
	EmailId Text
	DateOfBirth Date Not Null
);

--CREATE INDEXES
CREATE INDEX studentfistnameindex ON Student (FirstName);
CREATE INDEX studentlastnameindex ON Student (LastName);
CREATE INDEX studentemailidindex ON Student (EmailId);
CREATE INDEX studentdateofbirthindex ON Student (DateOfBirth);
