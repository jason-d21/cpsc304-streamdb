CREATE TABLE Genre (
Name character (40),
PRIMARY KEY (name)
);

INSERT INTO Genre VALUES ("Comedy");
INSERT INTO Genre VALUES ("Romance");
INSERT INTO Genre VALUES ("Fictional");
INSERT INTO Genre VALUES ("Action");
INSERT INTO Genre VALUES ("Kids");


CREATE TABLE Actor (
Aid int,
FullName character(200),
DOB date,
PRIMARY KEY (AID)
);

INSERT INTO Actor VALUES (0, "John Green", '2001-04-25');
INSERT INTO Actor VALUES (1, "Liz Beth", '1998-03-28');
INSERT INTO Actor VALUES (2, "Carey Smith", '1986-06-27');
INSERT INTO Actor VALUES (3, "Jane Near", '1963-11-01');
INSERT INTO Actor VALUES (4, "Selena Gomez", '1997-09-24');

CREATE TABLE Director (
DID int,
FullName character(200),
DOB date,
PRIMARY KEY (DID)
);

INSERT INTO Director VALUES (11, "Peter Jackson", '1990-04-21');
INSERT INTO Director VALUES (22, "John Smith", '1978-03-26');
INSERT INTO Director VALUES (33, "Elizabeth May", '1997-06-17');
INSERT INTO Director VALUES (44, "Rachel Hottel", '1943-11-11');
INSERT INTO Director VALUES (55, "Mark Sheen", '1987-09-28');

CREATE TABLE CityAcode (
City character(40),
Acode int,
PRIMARY KEY (City)
);

INSERT INTO CityAcode VALUES ("Vancouver", 111);
INSERT INTO CityAcode VALUES ("New York City", 000);
INSERT INTO CityAcode VALUES ("Richmond", 222);
INSERT INTO CityAcode VALUES ("Toronto", 333);
INSERT INTO CityAcode VALUES ("Los Angeles", 444);

CREATE TABLE Studio (
Name character(40),
City character(40),
Country character(40),
YearEstablished int,
PRIMARY KEY (Name, City, Country),
FOREIGN KEY (City) REFERENCES CityAcode(City)
);

INSERT INTO Studio VALUES ("Lions", "Vancouver", "Canada", 1990);
INSERT INTO Studio VALUES ("Lions2", "Vancouver", "Canada", 1991);
INSERT INTO Studio VALUES ("Lions3", "Vancouver", "Canada", 1992);
INSERT INTO Studio VALUES ("Cloud", "New York City", "USA", 1991);
INSERT INTO Studio VALUES ("Quest", "Los Angeles", "USA", 1978);
INSERT INTO Studio VALUES ("Atrium", "Richmond", "Canada", 1960);
INSERT INTO Studio VALUES ("Rawel", "Toronto", "Canada", 1954);

CREATE TABLE Entertainment_Produce (
EID int,
Title character(200),
ReleaseDate date NOT NULL,
AvgRating real,
StudioName character(40) NOT NULL,
StudioCity character(40) NOT NULL,
StudioCountry character(40) NOT NULL,
PRIMARY KEY (EID),
UNIQUE (Title, ReleaseDate, StudioName, StudioCity, StudioCountry),
FOREIGN KEY (StudioName, StudioCity, StudioCountry) REFERENCES Studio(Name, City, Country)
);

/*
used same set of SN, SC & SCOUNTRY as found in studio tuples
*/

INSERT INTO Entertainment_Produce VALUES (1, "E1", '2011-01-10', 5.0, "Lions", "Vancouver", "Canada");
INSERT INTO Entertainment_Produce VALUES (8, "E5", '2011-01-10', 5.0, "Lions", "Vancouver", "Canada");
INSERT INTO Entertainment_Produce VALUES (6, "E1", '2011-01-11', 5.0, "Lions2", "Vancouver", "Canada");
INSERT INTO Entertainment_Produce VALUES (7, "E1", '2011-01-12', 5.0, "Lions3", "Vancouver", "Canada");
INSERT INTO Entertainment_Produce VALUES (2, "E2", '2015-03-05', 7.5, "Cloud", "New York City", "USA");
INSERT INTO Entertainment_Produce VALUES (3, "E2", '1995-10-28', 6.5, "Quest", "Los Angeles", "USA");
INSERT INTO Entertainment_Produce VALUES (4, "E3", '1984-11-01', 8.0, "Atrium", "Richmond", "Canada");
INSERT INTO Entertainment_Produce VALUES (5, "E4", '1999-09-10', 9.0, "Rawel", "Toronto", "Canada");



CREATE TABLE isOF (
EID int,
GenreName character(40),
PRIMARY KEY (EID, GenreName),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE,
FOREIGN KEY (GenreName) references Genre(name)
);


/*
made changes here so the genres come from the ones in the genre tuples
*/
INSERT INTO isOF VALUES (1, "Comedy");
INSERT INTO isOF VALUES (1, "Romance");
INSERT INTO isOF VALUES (2, "Fictional");
INSERT INTO isOF VALUES (3, "Fictional");
INSERT INTO isOF VALUES (4, "Action");


CREATE TABLE Star (
EID int,
AID int,
Role character(200) NOT NULL,
PRIMARY KEY (EID, AID),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE,
FOREIGN KEY (AID) REFERENCES Actor(AID)
);

INSERT INTO Star VALUES (1, 0, "Andy Dufrense");
INSERT INTO Star VALUES (1, 1, "Warden Norton");
INSERT INTO Star VALUES (2, 2, "Hermione Granger");
INSERT INTO Star VALUES (4, 3, "Bilbo Baggins");
INSERT INTO Star VALUES (5, 4, "Princess Leia");

CREATE TABLE Direct (
DID int,
EID int,
PRIMARY KEY (DID, EID),
FOREIGN KEY (DID) REFERENCES Director(DID),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE
);

/*
Change the DID values to match those in the Director tuples
*/

INSERT INTO Direct VALUES (11, 1);
INSERT INTO Direct VALUES (11, 2);
INSERT INTO Direct VALUES (22, 3);
INSERT INTO Direct VALUES (22, 2);
INSERT INTO Direct VALUES (33, 5);


CREATE TABLE Movie (
EID int,
Length Int,
PRIMARY KEY (EID),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE
);

INSERT INTO Movie VALUES (1, 150);
INSERT INTO Movie VALUES (2, 250);
INSERT INTO Movie VALUES (3, 180);
INSERT INTO Movie VALUES (4, 190);
INSERT INTO Movie VALUES (5, 220);

CREATE TABLE SeriesLength (
EpCount int,
AvgEpLength int,
SeasonLength int,
PRIMARY KEY (EpCount, AvgEpLength)
);

INSERT INTO SeriesLength VALUES (10, 10, 100);
INSERT INTO SeriesLength VALUES (12, 25, 300);
INSERT INTO SeriesLength VALUES (14, 30, 420);
INSERT INTO SeriesLength VALUES (22, 60, 1320);
INSERT INTO SeriesLength VALUES (20, 45, 900);

CREATE TABLE Series (
EID int,
EpCount int,
SeasonNUM int,
AvgEpLength int,
PRIMARY KEY (EID),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE,
FOREIGN KEY (EpCount, AvgEpLength) REFERENCES SeriesLength(EpCount, AvgEpLength)
);

INSERT INTO Series VALUES (1, 10, 22, 10);
INSERT INTO Series VALUES (2, 12, 25, 25);
INSERT INTO Series VALUES (3, 14, 30, 30);
INSERT INTO Series VALUES (4, 22, 60, 60);
INSERT INTO Series VALUES (5, 20, 45, 45);

/*
changed the name of account here
*/

CREATE TABLE Account_ (
Status character(20),
SignUpDate date,
Email character(40),
Password character(40) NOT NULL,
SubscriptionType character(30),
PRIMARY KEY (Email)
);

INSERT INTO Account_ VALUES ("Activated", '2010-01-23', "jacqueline@gmail.com", "abcde", "Monthly");
INSERT INTO Account_ VALUES ("Not Activated", '2005-04-29', "denise@gmail.com", "happycow", "Yearly");
INSERT INTO Account_ VALUES ("Activated", '2020-09-11', "blake@gmail.com", "vancouverBC", "Monthly");
INSERT INTO Account_ VALUES ("Activated", '2021-04-25', "aaron@gmail.com", "mountains", "Yearly");
INSERT INTO Account_ VALUES ("Activated", '2019-07-23', "timothy@gmail.com", "ocean", "Monthly");


CREATE TABLE UserProfile_Has (
Name character(40),
Email character(40),
DateCreated date,
PRIMARY KEY (Email, Name),
FOREIGN KEY (Email) REFERENCES Account_(Email) ON DELETE CASCADE
);

INSERT INTO UserProfile_Has VALUES ("Jacqueline", "jacqueline@gmail.com", '2020-01-29');
INSERT INTO UserProfile_Has VALUES ("Denise", "denise@gmail.com", '2019-04-07');
INSERT INTO UserProfile_Has VALUES ("Blake", "blake@gmail.com", '2021-07-09');
INSERT INTO UserProfile_Has VALUES ("Aaron", "aaron@gmail.com", '2020-11-01');
INSERT INTO UserProfile_Has VALUES ("Timothy", "timothy@gmail.com", '2022-02-01');


CREATE TABLE Watch (
EID int,
UserProfileName character(40),
Email character(40),
PRIMARY KEY (EID, UserProfileName, Email),
FOREIGN KEY (EID) REFERENCES Entertainment_Produce(EID) ON DELETE CASCADE,
FOREIGN KEY (Email, UserProfileName) REFERENCES UserProfile_Has(Email, Name) ON DELETE CASCADE
);


INSERT INTO Watch VALUES (1, "Jacqueline", "jacqueline@gmail.com");
INSERT INTO Watch VALUES (2, "Denise", "denise@gmail.com");
INSERT INTO Watch VALUES (4, "Denise", "denise@gmail.com");
INSERT INTO Watch VALUES (5, "Denise", "denise@gmail.com");
INSERT INTO Watch VALUES (3, "Blake", "blake@gmail.com");
INSERT INTO Watch VALUES (4, "Aaron", "aaron@gmail.com");
INSERT INTO Watch VALUES (2, "Timothy", "timothy@gmail.com");
INSERT INTO Watch VALUES (4, "Timothy", "timothy@gmail.com");
INSERT INTO Watch VALUES (5, "Timothy", "timothy@gmail.com");

CREATE TABLE Rating (
RID int,
NumericalRating int NOT NULL,
PRIMARY KEY (RID)
);

INSERT INTO Rating VALUES (1, 5);
INSERT INTO Rating VALUES (2, 6);
INSERT INTO Rating VALUES (3, 7);
INSERT INTO Rating VALUES (4, 9);
INSERT INTO Rating VALUES (5, 1);


CREATE TABLE Rate_HasR (
EID integer,
UserProfileName character(40),
Email character(40),
RID Int NOT NULL,
unique (RID),
PRIMARY KEY (EID, UserProfileName, Email),
FOREIGN KEY (EID) References Entertainment_Produce(EID) ON DELETE CASCADE,
FOREIGN KEY (Email, UserProfileName) REFERENCES UserProfile_Has(Email, Name) ON DELETE CASCADE,
FOREIGN KEY(RID) REFERENCES Rating(RID) ON DELETE CASCADE
);

INSERT INTO Rate_HasR VALUES (1, "Jacqueline", "jacqueline@gmail.com", 1);
INSERT INTO Rate_HasR VALUES (2, "Denise", "denise@gmail.com", 2);
INSERT INTO Rate_HasR VALUES (3, "Blake", "blake@gmail.com", 3);
INSERT INTO Rate_HasR VALUES (4, "Aaron", "aaron@gmail.com", 4);
INSERT INTO Rate_HasR VALUES (5, "Timothy", "timothy@gmail.com", 5);





