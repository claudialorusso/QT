CREATE DATABASE MapDB;

commit;

CREATE USER 'MapUser'@'localhost' IDENTIFIED BY 'map';

GRANT CREATE, SELECT, INSERT, DELETE ON MapDB. * TO MapUser@localhost;

CREATE TABLE MapDB. playtennis(
	outlook varchar(10),
    temperature float(5,2),
    umidity varchar(10),
    wind varchar(10),
    play varchar(10)
);

insert into MapDB.playtennis values('sunny',30.3,'high','weak','no');
insert into MapDB.playtennis values('sunny',30.3,'high','strong','no');
insert into MapDB.playtennis values('overcast',30.0,'high','weak','yes');
insert into MapDB.playtennis values('rain',13.0,'high','weak','yes');
insert into MapDB.playtennis values('rain',0.0,'normal','weak','yes');
insert into MapDB.playtennis values('rain',0.0,'normal','strong','no');
insert into MapDB.playtennis values('overcast',0.1,'normal','strong','yes');
insert into MapDB.playtennis values('sunny',13.0,'high','weak','no');
insert into MapDB.playtennis values('sunny',0.1,'normal','weak','yes');
insert into MapDB.playtennis values('rain',12.0,'normal','weak','yes');
insert into MapDB.playtennis values('sunny',12.5,'normal','strong','yes');
insert into MapDB.playtennis values('overcast',12.5,'high','strong','yes');
insert into MapDB.playtennis values('overcast',29.21,'normal','weak','yes');
insert into MapDB.playtennis values('rain',12.5,'high','strong','no');

CREATE TABLE MapDB. example1(
	id INT PRIMARY KEY,
	x DOUBLE,
	y DOUBLE
);

insert into MapDB.example1 values
	(1,1,1),
	(2,0,1),
	(3,1,0),
	(4,11,12),
	(5,11,13),
	(6,13,13),
	(7,12,8.5),
	(8,13,8),
	(9,13,9),
	(10,13,7),
	(11,11,7),
	(12,8,2), 
	(13,9,2),
	(14,10,1),
	(15,7,13),
	(16,5,9),
	(17,16,16),
	(18,11.5,8),
	(20,13,10),
	(21,12,13),
	(31,14,12.5),
	(22,14.5,11.5),
	(23,15,10.5),
	(24,15,9.5),
	(25,12,9.5),
	(26,10.5,11),
	(27,10,10.5),
	(28,9,3),
	(29,9,4),
	(30,9,5);
CREATE TABLE MapDB. emptyt(
	id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(30)
);
commit;
