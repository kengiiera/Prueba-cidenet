-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-10-2021 a las 08:23:31
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `mydb`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_persona`
--

CREATE TABLE `tbl_persona` (
  `identificacion` varchar(20) NOT NULL,
  `primernombre` varchar(20) NOT NULL,
  `segundonombre` varchar(20) DEFAULT NULL,
  `primerapellido` varchar(20) NOT NULL,
  `segundoapellido` varchar(20) NOT NULL,
  `tipo_identificacion` varchar(20) NOT NULL,
  `paisempleo` varchar(25) NOT NULL,
  `correo` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_usuario`
--

CREATE TABLE `tbl_usuario` (
  `id_usuario` varchar(20) NOT NULL,
  `identificacion` int(11) NOT NULL,
  `nombre_usuario` varchar(45) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `apellido_usuario` varchar(45) NOT NULL,
  `correo` varchar(300) NOT NULL,
  `estado` varchar(1) NOT NULL DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_usuario`
--

INSERT INTO `tbl_usuario` (`id_usuario`, `identificacion`, `nombre_usuario`, `clave`, `apellido_usuario`, `correo`, `estado`) VALUES
('CIDENET', 1000, 'CIDENET', '123456', 'SA', 'cidente@gmail.co', 'A');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbl_persona`
--
ALTER TABLE `tbl_persona`
  ADD PRIMARY KEY (`identificacion`);

--
-- Indices de la tabla `tbl_usuario`
--
ALTER TABLE `tbl_usuario`
  ADD PRIMARY KEY (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
