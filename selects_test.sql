SELECT Distinct(m.idmovie) FROM Movies m LEFT JOIN Repertoire r ON r.displayday='2022-09-17';

SELECT * FROM Movies m LEFT JOIN Repertoire r ON r.displayday='2022-09-17';

SELECT m.idmovie,m.title,m.duration FROM Movies m LEFT JOIN
Repertoire r ON r.idmovie=m.idmovie where r.displayday='2022-09-16' AND r.idmovie in (select distinct(r.idmovie) from Repertoire);


INSERT INTO REPERTOIRE(IdMovie, DisplayDay, DisplayTime, IdRoom, NoFreePlaces, TicketPrice)
	VALUES (2, '2022-09-17', '22:15:00', 1, 27, '45.50');


select * FROM Repertoire  WHERE displayday = '2022-09-17';