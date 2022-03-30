SELECT FullName
FROM Actor
WHERE AID > 2;

SELECT Name, City, Country
FROM Studio
WHERE YearEstablished > 1978;

SELECT A.Email, Name
FROM Account_ A, UserProfile_Has UPH
WHERE A.Status = "Activated";

SELECT COUNT( DISTINCT Name)
FROM Genre ;

WITH Temp ( StudioName, StudioCity, StudioCountry) AS
   ( SELECT StudioName, StudioCity, StudioCountry, COUNT(*)
    FROM Entertainment_Produce
    GROUP BY StudioName, StudioCity, StudioCountry )
SELECT MAX(*)
FROM Temp ;

Select EID, Title
FROM Entertainment_Produce EP
WHERE NOT EXISTS (
    SELECT distinct Name
    FROM Genre g
    WHERE name="Romance" OR name="Comedy"
    AND name
    NOT IN
   (SELECT GenreName
    FROM IsOF io
    WHERE io.EID=EP.EID))





















