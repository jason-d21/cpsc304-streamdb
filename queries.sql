/*
Select all actors born in or after 1990
*/
SELECT FullName
FROM Actor
WHERE DOB >= '1990-01-01';


/*
Project Name, City, and Country of all studios established after 1978
*/
SELECT Name, City, Country
FROM Studio
WHERE YearEstablished > 1978;


/*
Find all activated user profiles
Join UserProfile_Has with Account_ and select all the user profile names that are activated
*/
SELECT A.Email, Name
FROM Account_ A, UserProfile_Has UPH
WHERE A.Status = "Activated";


/*
Aggregation - find the total number of distinct entertainment titles
*/
SELECT COUNT(DISTINCT Title) As DistinctTitleCount
FROM Entertainment_Produce;


/*
Nested Aggregation with Group By
Find the studio that produced the most entertainments
*/

/* This is a more compact version using WITH that does not seem to work with MySQL
WITH Temp (StudioName, StudioCity, StudioCountry) AS
  (SELECT StudioName, StudioCity, StudioCountry, COUNT(*)
  FROM Entertainment_Produce
  GROUP BY StudioName, StudioCity, StudioCountry)
SELECT MAX(*)
FROM Temp;
*/

SELECT *
FROM
    (SELECT StudioName, StudioCity, StudioCountry, COUNT(*) AS EntNum
     FROM Entertainment_Produce
     GROUP BY StudioName, StudioCity, StudioCountry) AS Temp
WHERE Temp.EntNum =
(
  SELECT MAX(Temp.EntNum)
  FROM
      (SELECT StudioName, StudioCity, StudioCountry, COUNT(*) AS EntNum
       FROM Entertainment_Produce
       GROUP BY StudioName, StudioCity, StudioCountry) AS Temp
);


/*
Division - Find users who have watched all entertainments with an AvgRating > 7.0
*/
SELECT Email, Name
FROM UserProfile_Has UPH
WHERE NOT EXISTS (
        SELECT EP.EID
        FROM Entertainment_Produce EP
        WHERE EP.AvgRating > 7.0
          AND EP.EID NOT IN (
            SELECT W.EID
            FROM Watch W
            WHERE W.Email = UPH.Email AND W.UserProfileName = UPH.Name
        )
    )





















