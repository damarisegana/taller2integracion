/*correr esta linea primero:*/

CREATE DATABASE `taller3`;

/*Luego esto:*/

CREATE TABLE taller3.categoria (
  `id` int(11) NOT NULL,
  `descripción` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Luego esto:*/

CREATE TABLE taller3.pelicula (
  `codigo` int(11) NOT NULL,
  `precio` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `formato4k` varchar(1) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `id_categoria_fk_idx` (`id_categoria`),
  CONSTRAINT `id_categoria_fk` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Luego esto:*/

insert into taller3.categoria VALUES (1, 'Accion');
insert into taller3.categoria VALUES (2, 'Aventura');
insert into taller3.categoria VALUES (3, 'Ciencia Ficcion');
insert into taller3.categoria VALUES (4, 'Comedia');
insert into taller3.categoria VALUES (5, 'Crimen');
insert into taller3.categoria VALUES (6, 'Drama');
insert into taller3.categoria VALUES (7, 'Historico');
insert into taller3.categoria VALUES (8, 'Horror');
insert into taller3.categoria VALUES (9, 'Musical');
insert into taller3.categoria VALUES (10, 'Romance');


select * from taller3.pelicula;