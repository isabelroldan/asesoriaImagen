/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 8.0.33 : Database - imageconsulting
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`imageconsulting` /*!40100 DEFAULT CHARACTER SET utf8mb4 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `imageconsulting`;

/*Table structure for table `appointment` */

DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `id_appointment` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `date` date NOT NULL,
  `id_client` int(5) unsigned zerofill NOT NULL,
  `id_space` int(5) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id_appointment`),
  KEY `id_space` (`id_space`),
  KEY `id_client` (`id_client`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON UPDATE CASCADE,
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`id_space`) REFERENCES `space` (`id_space`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `client` */

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id_client` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `surname` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `telephone` varchar(12) CHARACTER SET utf8mb4 NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `colorTestResult` varchar(10) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `professional` */

DROP TABLE IF EXISTS `professional`;

CREATE TABLE `professional` (
  `id_professional` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `surname` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `telephone` varchar(12) CHARACTER SET utf8mb4 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(300) CHARACTER SET utf8mb4 NOT NULL,
  `dni` varchar(9) CHARACTER SET utf8mb4 NOT NULL,
  `nPersonnel` int NOT NULL,
  `nSocialSecurity` int NOT NULL,
  `id_space` int(5) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id_professional`),
  UNIQUE KEY `nPersonnel` (`nPersonnel`),
  UNIQUE KEY `nSocialSecurity` (`nSocialSecurity`),
  KEY `id_space` (`id_space`),
  KEY `id_space_2` (`id_space`),
  CONSTRAINT `professional_ibfk_1` FOREIGN KEY (`id_space`) REFERENCES `space` (`id_space`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `space` */

DROP TABLE IF EXISTS `space`;

CREATE TABLE `space` (
  `id_space` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `serviceType` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`id_space`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
