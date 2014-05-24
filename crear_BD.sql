
CREATE DATABASE `empresa` ;

USE `empresa` ;

CREATE TABLE `configuracion` (
  `preciohoraextra` decimal(5,2) NOT NULL,
  `preciohoratrabajada` decimal(5,2) NOT NULL,
  `totaltrabajadores` smallint(6) NOT NULL,
  `codigoultimotrabajador` smallint(6) NOT NULL,
  `totalfijos` smallint(6) NOT NULL,
  `totaleventuales` smallint(6) NOT NULL,  
  `cuotaobrera` decimal(5,2) NOT NULL,
  `sueldofijominimo` decimal(7,2) NOT NULL  
) ENGINE=InnoDB ;


CREATE TABLE `trabajadorfijo` (
  `nifonie` varchar(9) NOT NULL,
  `codigo` smallint(6) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `fechanacimiento` date NOT NULL,
  `fechacontrato` date NOT NULL,
  `ocupacion` varchar(20) NOT NULL,
  `altura` decimal(3,2) NOT NULL,
  `ccc` varchar(20) NOT NULL,
  `sueldofijo` decimal(7,2) NOT NULL,
  `numerohorasextras` smallint(6) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `numerodehijos` smallint(6) NOT NULL,
  PRIMARY KEY (`nifonie`)
) ENGINE=InnoDB ;


CREATE TABLE `trabajadoreventual` (
  `nifonie` varchar(9) NOT NULL,
  `codigo` smallint(6) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `fechanacimiento` date NOT NULL,
  `fechacontrato` date NOT NULL,
  `ocupacion` varchar(20) NOT NULL,
  `altura` decimal(3,2) NOT NULL,
  `ccc` varchar(20) NOT NULL,
  `numerocolaboraciones` smallint(3) NOT NULL,
  `numerohorastrabajadas` smallint(3) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `numerodehijos` smallint(6) NOT NULL,
  PRIMARY KEY (`nifonie`)
) ENGINE=InnoDB ;






