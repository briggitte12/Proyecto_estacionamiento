-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.3.39-MariaDB - MariaDB Server
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para bd_parking
CREATE DATABASE IF NOT EXISTS `bd_parking` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `bd_parking`;

-- Volcando estructura para procedimiento bd_parking.ActualizarCobro
DELIMITER //
CREATE PROCEDURE `ActualizarCobro`(
	IN `p_id_cobro` INT,
	IN `p_nuevos_minutos` INT,
	IN `p_nuevo_monto` DOUBLE
)
    COMMENT 'Actualizar cobro registrado en la tabla cobro.'
BEGIN
	UPDATE cobro
   SET minutos_estacionado = p_nuevos_minutos,
	monto_total = p_nuevo_monto
   WHERE id_cobro = p_id_cobro;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ActualizarConductor
DELIMITER //
CREATE PROCEDURE `ActualizarConductor`(
	IN `p_dni_conductor` CHAR(8),
	IN `p_nombre_completo` VARCHAR(100),
	IN `p_celular` CHAR(9),
	IN `p_correo` VARCHAR(100),
	IN `p_fecha_nacimiento` DATE,
	IN `p_direccion` VARCHAR(255),
	IN `p_genero` ENUM('Masculino', 'Femenino')
)
BEGIN
    UPDATE conductor
    SET
        nombre_completo = p_nombre_completo,
        celular = p_celular,
        correo = p_correo,
        fecha_nacimiento = p_fecha_nacimiento,
        direccion = p_direccion,
        genero = p_genero
    WHERE 
        dni_conductor = p_dni_conductor;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ActualizarFechaSalida
DELIMITER //
CREATE PROCEDURE `ActualizarFechaSalida`(
    IN p_idRegistro INT,
    IN p_fechaSalida DATETIME
)
BEGIN
    UPDATE registro_vehiculos
    SET fecha_salida = p_fechaSalida, estado='Salida'
    WHERE id_registro = p_idRegistro;
    

END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ActualizarTarifa
DELIMITER //
CREATE PROCEDURE `ActualizarTarifa`(
	IN `idTarifa` INT,
	IN `nuevoPrecio` DOUBLE
)
BEGIN
    UPDATE tarifa
    SET precio_por_minuto = nuevoPrecio
    WHERE id_tarifa = idTarifa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ActualizarVehiculo
DELIMITER //
CREATE PROCEDURE `ActualizarVehiculo`(
	IN `p_numero_placa` VARCHAR(20),
	IN `p_tipo_vehiculo` ENUM('MotoLineal','MotoTaxi','Carro','Camioneta'),
	IN `p_nueva_marca` VARCHAR(50),
	IN `p_nuevo_color` VARCHAR(50),
	IN `p_dni` CHAR(10)
)
BEGIN
    UPDATE vehiculo
    SET 
    	  tipo_vehiculo = p_tipo_vehiculo,
    	  marca = p_nueva_marca,
        color = p_nuevo_color,
        dni_conductor = p_dni
        
    WHERE numero_placa = p_numero_placa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.AgregarUsuario
DELIMITER //
CREATE PROCEDURE `AgregarUsuario`(
    IN p_usuario VARCHAR(30),
    IN p_clave VARCHAR(100),
    IN p_rol VARCHAR(20),
    IN p_nombre_completo VARCHAR(50),
    IN p_correo VARCHAR(50),
    IN p_telefono CHAR(9),
    IN p_foto VARCHAR(80)
)
BEGIN
    -- Insertar nuevo usuario y el id_usuario será generado automáticamente por AUTO_INCREMENT
    INSERT INTO usuario (usuario, clave, rol, nombre_completo, correo, telefono, foto)
    VALUES (p_usuario, p_clave, p_rol, p_nombre_completo, p_correo, p_telefono, p_foto);
END//
DELIMITER ;

-- Volcando estructura para tabla bd_parking.cobro
CREATE TABLE IF NOT EXISTS `cobro` (
  `id_cobro` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador',
  `id_registro` int(11) NOT NULL COMMENT 'Se relaciona con la tabla registro',
  `id_tarifa` int(11) NOT NULL COMMENT 'Se relaciona con la tabla tarifa',
  `minutos_estacionado` int(11) NOT NULL COMMENT 'Tiempo que el vehiculo estuvo en el estacionamiento',
  `monto_total` decimal(10,2) NOT NULL COMMENT 'Representa el monto total a pagar por el servicio',
  PRIMARY KEY (`id_cobro`),
  KEY `id_registro` (`id_registro`),
  KEY `id_tarifa` (`id_tarifa`),
  CONSTRAINT `cobro_ibfk_1` FOREIGN KEY (`id_registro`) REFERENCES `registro_vehiculos` (`id_registro`),
  CONSTRAINT `cobro_ibfk_2` FOREIGN KEY (`id_tarifa`) REFERENCES `tarifa` (`id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.cobro: ~6 rows (aproximadamente)
INSERT INTO `cobro` (`id_cobro`, `id_registro`, `id_tarifa`, `minutos_estacionado`, `monto_total`) VALUES
	(12, 21, 5, 787, 1180.50),
	(13, 22, 7, 1, 2.50),
	(16, 21, 5, 69320, 103980.00),
	(22, 24, 6, 63523, 127046.00),
	(24, 24, 6, 72176, 144352.00);

-- Volcando estructura para tabla bd_parking.conductor
CREATE TABLE IF NOT EXISTS `conductor` (
  `dni_conductor` char(8) NOT NULL COMMENT 'Identficador',
  `nombre_completo` varchar(35) NOT NULL COMMENT 'Nombre conductor',
  `celular` char(9) NOT NULL COMMENT 'Celular conductor',
  `correo` varchar(40) NOT NULL COMMENT 'Correo conductor',
  `fecha_nacimiento` date NOT NULL COMMENT 'Fecha Nacimiento conductor',
  `direccion` varchar(50) NOT NULL COMMENT 'Lugar donde vive el conductor',
  `genero` enum('Masculino','Femenino') NOT NULL COMMENT 'Genero conductor',
  PRIMARY KEY (`dni_conductor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.conductor: ~6 rows (aproximadamente)
INSERT INTO `conductor` (`dni_conductor`, `nombre_completo`, `celular`, `correo`, `fecha_nacimiento`, `direccion`, `genero`) VALUES
	('71484607', 'Yesenia', '952534124', 'yesenia@gmail.com', '2000-10-10', 'Calle Tacna 651', 'Femenino'),
	('74840000', 'Briggitte Martinez', '940571000', 'briggite@gmail.com', '2000-10-18', 'no', 'Femenino'),
	('78110100', 'Jhonathan Hernandez', '987654132', 'yona@gmail.com', '2000-10-20', 'Av.Balta', 'Masculino'),
	('78111111', 'Eberth', '945147845', 'eberth@gmail.com', '2000-10-25', 'Av.Balta', 'Masculino'),
	('78475087', 'Francisco Martin ', '945143895', 'developerfran25@gmail.com', '2000-10-25', 'Calle Tacna 651', 'Masculino'),
	('78478904', 'Fabricio ', '987458012', 'fabri@gmail.com', '2000-10-20', 'La Victoria', 'Masculino');

-- Volcando estructura para procedimiento bd_parking.EliminarCobro
DELIMITER //
CREATE PROCEDURE `EliminarCobro`(
	IN `p_id_cobro` INT
)
    COMMENT 'Eliminar un cobro.'
BEGIN
	DELETE FROM cobro
   WHERE id_cobro = p_id_cobro;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.EliminarConductor
DELIMITER //
CREATE PROCEDURE `EliminarConductor`(
	IN `p_dni_conductor` CHAR(8)
)
    COMMENT 'Eliminar un conductor.'
BEGIN
	-- Variable para almacenar existencia
   DECLARE existe INT DEFAULT 0;
   -- Verificar si el conductor existe
   SELECT COUNT(1) INTO existe 
   FROM conductor 
   WHERE dni_conductor = p_dni_conductor;
   -- Si existe, eliminar el conductor
   IF existe > 0 THEN
      DELETE FROM conductor 
		WHERE dni_conductor = p_dni_conductor;
   END IF;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.EliminarIngreso
DELIMITER //
CREATE PROCEDURE `EliminarIngreso`(
	IN `p_id_registro` INT
)
    COMMENT 'Eliminar ingreso de vehículo con el id de registro.'
BEGIN
	-- Eliminar los registros dependientes en la tabla cobro
   DELETE FROM cobro WHERE id_registro = p_id_registro; 
   -- Ahora, eliminar el registro en registro_vehiculos
   DELETE FROM registro_vehiculos WHERE id_registro = p_id_registro;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.EliminarTarifaPorTipoVehiculo
DELIMITER //
CREATE PROCEDURE `EliminarTarifaPorTipoVehiculo`(
	IN `tipoVehiculo` ENUM('Carro','Camioneta','MotoLineal','MotoTaxi')
)
    COMMENT 'Eliminar la tarifa por tipo de vehículo.'
BEGIN
	-- Eliminar los registros en la tabla cobro relacionados con las tarifas del tipo de vehículo
    DELETE FROM cobro
    WHERE id_registro IN (
        SELECT id_registro
        FROM registro_vehiculos
        WHERE id_tarifa IN (
            SELECT id_tarifa
            FROM tarifa
            WHERE tipo_vehiculo = tipoVehiculo
        )
    );

    -- Eliminar los registros en la tabla registro_vehiculos relacionados con las tarifas del tipo de vehículo
    DELETE FROM registro_vehiculos
    WHERE id_tarifa IN (
        SELECT id_tarifa
        FROM tarifa
        WHERE tipo_vehiculo = tipoVehiculo
    );

    -- Finalmente, eliminar las tarifas asociadas al tipo de vehículo
    DELETE FROM tarifa
    WHERE tipo_vehiculo = tipoVehiculo;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.EliminarVehiculo
DELIMITER //
CREATE PROCEDURE `EliminarVehiculo`(
	IN `p_numero_placa` VARCHAR(15)
)
    COMMENT 'Eliminar un vehículo a partir de su número de placa.'
BEGIN
	-- Eliminar registros en la tabla cobro que hacen referencia a registro_vehiculos
   DELETE FROM cobro
   WHERE id_registro IN (SELECT id_registro FROM registro_vehiculos WHERE numero_placa = p_numero_placa);
   -- Eliminar registros relacionados en la tabla registro_vehiculos
   DELETE FROM registro_vehiculos WHERE numero_placa = p_numero_placa;
   -- Ahora, eliminar el vehículo
   DELETE FROM vehiculo WHERE numero_placa = p_numero_placa;
END//
DELIMITER ;

-- Volcando estructura para tabla bd_parking.estacionamiento
CREATE TABLE IF NOT EXISTS `estacionamiento` (
  `id_estacionamiento` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador',
  `nombre` varchar(100) NOT NULL DEFAULT 'Estacionamiento Principal' COMMENT 'Nombre Estacionamiento',
  `capacidad_total` int(11) NOT NULL COMMENT 'Aforo',
  `numero_pisos` int(11) NOT NULL COMMENT '10 pisos',
  `horario_apertura` time NOT NULL COMMENT 'horario apertura ',
  `horario_cierre` time NOT NULL COMMENT 'horario cierre',
  PRIMARY KEY (`id_estacionamiento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.estacionamiento: ~0 rows (aproximadamente)
INSERT INTO `estacionamiento` (`id_estacionamiento`, `nombre`, `capacidad_total`, `numero_pisos`, `horario_apertura`, `horario_cierre`) VALUES
	(1, 'Estacionamiento Principal', 150, 10, '09:00:00', '22:00:00');

-- Volcando estructura para procedimiento bd_parking.GetAllCobros
DELIMITER //
CREATE PROCEDURE `GetAllCobros`()
SELECT 
        c.id_cobro,
        r.id_registro,
        r.id_tarifa,
        c.minutos_estacionado,
        c.monto_total,
        co.nombre_completo,
        v.dni_conductor,
        r.numero_placa AS placa,
        v.tipo_vehiculo AS tipo_vehiculo
    FROM 
        cobro c
    INNER JOIN 
        registro_vehiculos r ON c.id_registro = r.id_registro
    INNER JOIN 
        vehiculo v ON r.numero_placa = v.numero_placa
    INNER JOIN 
        conductor co ON v.dni_conductor = co.dni_conductor//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetAllConductores
DELIMITER //
CREATE PROCEDURE `GetAllConductores`()
BEGIN
    SELECT dni_conductor, nombre_completo, celular, correo, fecha_nacimiento, direccion, genero
    FROM conductor;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetAllEstacionamiento
DELIMITER //
CREATE PROCEDURE `GetAllEstacionamiento`()
BEGIN
    SELECT * FROM estacionamiento LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetAllRegistrosIngreso
DELIMITER //
CREATE PROCEDURE `GetAllRegistrosIngreso`()
BEGIN
    SELECT id_registro, id_tarifa,numero_placa, id_usuario, piso, id_estacionamiento, fecha_ingreso, fecha_salida, observacion, estado
    FROM registro_vehiculos;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetAllTarifa
DELIMITER //
CREATE PROCEDURE `GetAllTarifa`()
BEGIN
    SELECT id_tarifa, tipo_vehiculo, precio_por_minuto
    FROM tarifa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetAllVehiculo
DELIMITER //
CREATE PROCEDURE `GetAllVehiculo`()
BEGIN
    SELECT *
    FROM vehiculo;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetByIdTarifa
DELIMITER //
CREATE PROCEDURE `GetByIdTarifa`(
	IN `p_tarifa` INT
)
BEGIN
    SELECT *
    FROM tarifa
    WHERE id_tarifa = p_tarifa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetCantidadConductores
DELIMITER //
CREATE PROCEDURE `GetCantidadConductores`()
BEGIN
    SELECT COUNT(*) AS cantidad FROM conductor;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetCantidadUsuarios
DELIMITER //
CREATE PROCEDURE `GetCantidadUsuarios`()
BEGIN
    SELECT COUNT(*) AS cantidad_usuario FROM usuario;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetCantidadVehiculos
DELIMITER //
CREATE PROCEDURE `GetCantidadVehiculos`()
BEGIN
    SELECT COUNT(*) AS cantidad_vehiculos FROM vehiculo;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetConductorByDNI
DELIMITER //
CREATE PROCEDURE `GetConductorByDNI`(
    IN p_dni_conductor CHAR(8)
)
BEGIN
    SELECT 
        dni_conductor,
        nombre_completo,
        celular,
        correo,
        fecha_nacimiento,
        direccion,
        genero
    FROM 
        conductor
    WHERE 
        dni_conductor = p_dni_conductor;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetTotalGanancia
DELIMITER //
CREATE PROCEDURE `GetTotalGanancia`()
BEGIN
    SELECT SUM(monto_total) AS total_ganancias FROM cobro;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.GetUsuarioByNombre
DELIMITER //
CREATE PROCEDURE `GetUsuarioByNombre`(
	IN `pUsuario` VARCHAR(50)
)
BEGIN
    SELECT usuario, id_usuario, clave, nombre_completo, correo, telefono, foto, rol
    FROM usuario
    WHERE usuario = pUsuario
    LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ObtenerPrecioPorMinuto
DELIMITER //
CREATE PROCEDURE `ObtenerPrecioPorMinuto`(IN p_id_tarifa INT)
BEGIN
    SELECT precio_por_minuto FROM tarifa WHERE id_tarifa = p_id_tarifa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.ObtenerVehiculo
DELIMITER //
CREATE PROCEDURE `ObtenerVehiculo`(
	IN `p_numero_placa` VARCHAR(20)
)
BEGIN
    SELECT *
    FROM vehiculo
    WHERE numero_placa = p_numero_placa;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.pr_checkUser
DELIMITER //
CREATE PROCEDURE `pr_checkUser`(
	IN `pUsuario` VARCHAR(30),
	IN `pClave` VARCHAR(100)
)
BEGIN
    SELECT id_usuario, usuario, nombre_completo
    FROM usuario 
    WHERE usuario = pUsuario 
      AND clave = pClave 
    LIMIT 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.RegistrarCobro
DELIMITER //
CREATE PROCEDURE `RegistrarCobro`(
	IN `p_id_registro` INT,
	IN `p_id_tarifa` INT,
	IN `p_minutos_estacionado` INT,
	IN `p_monto_total` DECIMAL(10,2)
)
BEGIN
    INSERT INTO cobro (id_registro, id_tarifa, minutos_estacionado, monto_total)
    VALUES (p_id_registro, p_id_tarifa, p_minutos_estacionado, p_monto_total);
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.RegistrarConductor
DELIMITER //
CREATE PROCEDURE `RegistrarConductor`(
    IN dni_conductor VARCHAR(8),
    IN nombre_completo VARCHAR(35),
    IN celular VARCHAR(9),
    IN correo VARCHAR(40),
    IN fecha_nacimiento DATE,
    IN direccion VARCHAR(50),
    IN genero VARCHAR(20)
)
BEGIN
    INSERT INTO conductor (dni_conductor, nombre_completo, celular, correo, fecha_nacimiento, direccion, genero)
    VALUES (dni_conductor, nombre_completo, celular, correo, fecha_nacimiento, direccion, genero);
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.RegistrarIngreso
DELIMITER //
CREATE PROCEDURE `RegistrarIngreso`(
	IN `p_numero_placa` VARCHAR(15),
	IN `p_id_estacionamiento` INT,
	IN `p_id_usuario` INT,
	IN `p_id_tarifa` INT,
	IN `p_piso` INT,
	IN `p_observacion` TEXT,
	IN `p_estado` VARCHAR(30)
)
BEGIN
    INSERT INTO registro_vehiculos (
        numero_placa, id_estacionamiento, id_usuario, id_tarifa ,piso, fecha_ingreso, fecha_salida, observacion, estado
    ) 
    VALUES (
        p_numero_placa, 
		  p_id_estacionamiento, 
		  p_id_usuario, p_id_tarifa, 
		  p_piso, 
		  NOW(), 
		  STR_TO_DATE(CURDATE(), '%Y-%m-%d %H:%i:%s'),
 		  p_observacion, 
		  p_estado
    );
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.RegistrarTarifa
DELIMITER //
CREATE PROCEDURE `RegistrarTarifa`(
	IN `tipo_vehiculo` ENUM('Carro','Camioneta','MotoLineal','MotoTaxi'),
	IN `precio_por_minuto` DECIMAL(10,2)
)
    COMMENT 'Registrar una tarifa.'
BEGIN
	INSERT INTO tarifa (tipo_vehiculo, precio_por_minuto)
   VALUES (tipo_vehiculo, precio_por_minuto);
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.RegistrarVehiculo
DELIMITER //
CREATE PROCEDURE `RegistrarVehiculo`(
	IN `numero_placa` VARCHAR(15),
	IN `tipo_vehiculo` VARCHAR(15),
	IN `marca` VARCHAR(45),
	IN `color` VARCHAR(30),
	IN `dni_conductor` CHAR(8)
)
BEGIN
    INSERT INTO vehiculo (numero_placa, tipo_vehiculo, marca, color, dni_conductor)
    VALUES (numero_placa, tipo_vehiculo, marca, color, dni_conductor);
END//
DELIMITER ;

-- Volcando estructura para tabla bd_parking.registro_vehiculos
CREATE TABLE IF NOT EXISTS `registro_vehiculos` (
  `id_registro` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador',
  `numero_placa` varchar(15) NOT NULL COMMENT 'Placa Vehiculo',
  `id_estacionamiento` int(11) NOT NULL COMMENT 'Se relaciona con la tabla estacionamiento',
  `id_usuario` int(11) NOT NULL COMMENT 'Se relaciona con la tabla usuario',
  `id_tarifa` int(11) DEFAULT NULL COMMENT 'Se relaciona con la tabla tarifa',
  `piso` int(11) NOT NULL COMMENT 'piso del estacionamiento , cuenta con 10 pisos',
  `fecha_ingreso` datetime NOT NULL COMMENT 'fecha ingreso del vehiculo',
  `fecha_salida` datetime NOT NULL COMMENT 'fecha salida del vehiculo',
  `observacion` text NOT NULL DEFAULT 'Sin observación' COMMENT 'observaciones',
  `estado` varchar(30) NOT NULL COMMENT 'En estacionamiento o Salida',
  PRIMARY KEY (`id_registro`),
  KEY `numero_placa` (`numero_placa`),
  KEY `id_estacionamiento` (`id_estacionamiento`),
  KEY `id_usuario` (`id_usuario`) USING BTREE,
  KEY `id_tarifa` (`id_tarifa`),
  CONSTRAINT `registro_vehiculos_ibfk_1` FOREIGN KEY (`numero_placa`) REFERENCES `vehiculo` (`numero_placa`),
  CONSTRAINT `registro_vehiculos_ibfk_2` FOREIGN KEY (`id_estacionamiento`) REFERENCES `estacionamiento` (`id_estacionamiento`),
  CONSTRAINT `registro_vehiculos_ibfk_3` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `registro_vehiculos_ibfk_4` FOREIGN KEY (`id_tarifa`) REFERENCES `tarifa` (`id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.registro_vehiculos: ~6 rows (aproximadamente)
INSERT INTO `registro_vehiculos` (`id_registro`, `numero_placa`, `id_estacionamiento`, `id_usuario`, `id_tarifa`, `piso`, `fecha_ingreso`, `fecha_salida`, `observacion`, `estado`) VALUES
	(21, 'AEI-234', 1, 2, 5, 4, '2024-10-14 12:11:15', '2024-12-02 19:22:45', 'Sin observaciones', 'Salida'),
	(22, 'IOU-123', 1, 1, 7, 10, '2024-10-16 09:01:42', '2024-12-02 19:22:52', 'Sin observaciones', 'Salida'),
	(24, 'XYZ-456', 1, 1, 6, 5, '2024-10-18 11:38:46', '2024-12-10 03:42:47', 'Sin observaciones', 'Salida'),
	(29, 'EEE-123', 1, 2, 6, 5, '2024-12-02 18:59:06', '2024-12-02 00:00:00', 'nada', 'En Estacionamiento'),
	(32, 'AEI-234', 1, 1, 5, 10, '2024-12-02 19:37:29', '2024-12-02 00:00:00', 'Sin observaciones', 'En Estacionamiento'),
	(36, 'AQW-123', 1, 1, 6, 5, '2024-12-06 11:08:53', '2024-12-06 11:09:20', 'Sin observaciones', 'Salida');

-- Volcando estructura para tabla bd_parking.tarifa
CREATE TABLE IF NOT EXISTS `tarifa` (
  `id_tarifa` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador',
  `tipo_vehiculo` enum('MotoLineal','MotoTaxi','Carro','Camioneta') NOT NULL COMMENT 'tipos de vehiculos que entraran al estacionamiento',
  `precio_por_minuto` decimal(10,2) NOT NULL COMMENT 'precio por cada minuto para un vehiculo estacionado',
  PRIMARY KEY (`id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.tarifa: ~4 rows (aproximadamente)
INSERT INTO `tarifa` (`id_tarifa`, `tipo_vehiculo`, `precio_por_minuto`) VALUES
	(5, 'MotoLineal', 1.50),
	(6, 'Carro', 2.00),
	(7, 'Camioneta', 2.50),
	(14, 'MotoTaxi', 1.00);

-- Volcando estructura para tabla bd_parking.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador',
  `usuario` varchar(30) NOT NULL,
  `clave` varchar(100) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `nombre_completo` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `telefono` char(9) NOT NULL,
  `foto` varchar(80) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.usuario: ~4 rows (aproximadamente)
INSERT INTO `usuario` (`id_usuario`, `usuario`, `clave`, `rol`, `nombre_completo`, `correo`, `telefono`, `foto`) VALUES
	(1, 'Francisco', '9407c826d8e3c07ad37cb2d13d1cb641', 'Administrador', 'Francisco Aquino', 'DevTeamMasters@utp.edu.pe', '945143895', 'user.png'),
	(2, 'Briggitte', '9407c826d8e3c07ad37cb2d13d1cb641', 'Administrador', 'Briggitte Martinez', 'briggitte@gmail.com', '945784014', 'user1.png'),
	(3, 'Eberth', '9407c826d8e3c07ad37cb2d13d1cb641', 'Asistente', 'Eberth ', 'Eberth@gmail.com', '945781245', 'user.png'),
	(4, 'Jhonatan', '9407c826d8e3c07ad37cb2d13d1cb641', 'Administrador', 'Jhonatan Yoel', 'Jhonatan@gmail.com', '912059555', 'user.png'),
	(5, 'Alice', '9407c826d8e3c07ad37cb2d13d1cb641', 'Asistente', 'Alice Martinez', 'alice@gmail.com', '982648528', 'user1.png'),
	(6, 'Alice', '9407c826d8e3c07ad37cb2d13d1cb641', 'Asistente', 'Alice Martinez', 'alice@gmail.com', '982648528', 'user1.png');

-- Volcando estructura para tabla bd_parking.vehiculo
CREATE TABLE IF NOT EXISTS `vehiculo` (
  `numero_placa` varchar(15) NOT NULL,
  `tipo_vehiculo` enum('MotoLineal','MotoTaxi','Carro','Camioneta') NOT NULL,
  `marca` varchar(45) NOT NULL,
  `color` varchar(30) NOT NULL,
  `dni_conductor` char(8) NOT NULL,
  PRIMARY KEY (`numero_placa`),
  KEY `dni_conductor` (`dni_conductor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla bd_parking.vehiculo: ~14 rows (aproximadamente)
INSERT INTO `vehiculo` (`numero_placa`, `tipo_vehiculo`, `marca`, `color`, `dni_conductor`) VALUES
	('ABC-123', 'Carro', 'Toyota', 'Azul', '98765432'),
	('ABD-123', 'MotoLineal', 'Taxi', 'AZUL', '71484607'),
	('AEI-234', 'MotoLineal', 'Hyundai', 'Blanco', '78475087'),
	('AQW-123', 'Carro', 'Hyundai', 'Blanco', '78478904'),
	('EAW-123', 'MotoTaxi', 'Toyota', 'Azul', '78475087'),
	('EEE-123', 'Carro', 'Toyota', 'Blanco', '78475087'),
	('III-232', 'MotoLineal', 'Taxi', 'Rojo', '11111111'),
	('IOU-123', 'Camioneta', 'HYUNDAI', 'Negro', '78475087'),
	('MAL-123', 'Carro', 'Taxi', 'AZUL', '00000000'),
	('OAQ-123', 'MotoLineal', 'TOYOTA', 'AZUL', '11111110'),
	('PIA-123', 'Carro', 'TOYOTA', 'AZUL', '11998855'),
	('XYZ-123', 'Carro', 'Toyota', 'Celeste', '78110100'),
	('XYZ-456', 'Carro', 'Toyota', 'Celeste', '78110100');

-- Volcando estructura para procedimiento bd_parking.verificarDNIExistente
DELIMITER //
CREATE PROCEDURE `verificarDNIExistente`(
	IN `dni` VARCHAR(8)
)
BEGIN
    IF EXISTS (SELECT 1 FROM conductor WHERE dni_conductor = dni) THEN
        SELECT 1;  -- Retorna 1 si el DNI existe
    ELSE
        SELECT 0;  -- Retorna 0 si el DNI no existe
    END IF;
END//
DELIMITER ;

-- Volcando estructura para procedimiento bd_parking.verificarPlacaExistente
DELIMITER //
CREATE PROCEDURE `verificarPlacaExistente`(
	IN `placa` VARCHAR(10)
)
BEGIN
    IF EXISTS (SELECT 1 FROM vehiculo WHERE numero_placa = placa) THEN
        SELECT 1;  -- Retorna 1 si la placa existe
    ELSE
        SELECT 0;  -- Retorna 0 si la placa no existe
    END IF;
END//
DELIMITER ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
