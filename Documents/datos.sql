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

/*Data for the table `appointment` */

insert  into `appointment`(`id_appointment`,`startTime`,`endTime`,`date`,`id_client`,`id_space`) values 
(00001,'12:30:00','13:00:00','2023-05-31',00002,00001),
(00002,'13:30:00','14:00:00','2023-05-12',00001,00002),
(00003,'18:00:00','19:00:00','2023-05-23',00002,00002);

/*Data for the table `client` */

insert  into `client`(`id_client`,`name`,`surname`,`telephone`,`email`,`password`,`colorTestResult`) values 
(00001,'Isabel','Roldan Cordoba','722772576','isabelroldancordoba@hotmail.com','1234','autumn'),
(00002,'Francisco Javier','Ordoñez Salamanca','-636952418','fjsalamanca@hotmail.com','9876','WINTER'),
(00003,'Francisco Javier','Ordoñez Salamanca','-636952418','wcxjwjcioecjerj','9876','SUMMER'),
(00004,'Francisco Javier','Ordoñez Salamanca','-636952418','THNTHNR','9876','SUMMER'),
(00005,'Galleta','galleta','123456789','galleta@hotmail.com','2222','SUMMER'),
(00075,'ejemplo','ejemplo..','123456789','ejemploejemplo@example.com','contraseña','SUMMER');

/*Data for the table `professional` */

insert  into `professional`(`id_professional`,`name`,`surname`,`telephone`,`email`,`password`,`dni`,`nPersonnel`,`nSocialSecurity`,`id_space`) values 
(00006,'Diego','ugart','789512463','du@gmail.com','5632','31030561W',6,654123987,00002),
(00014,'Mari Carmen','Lopez Garcia','123789541','mclg@gmail.com','5555','12345678A',14,1234567891,00001),
(00016,'Jose Luis','Perales','123456789','jorugo@gmail.com','$31$16$X_Juz2a8qmu7CMmuGSWp-Sd69eXGLxb9RoXnr0yIdyA','32015489A',12,123456789,00002),
(00045,'Alejandro','Gutierrez','789456123','cruces@gmail.com','$31$16$7xNLbwVLNNeqrqoeA2L1HPnrKhRsLL2ch1wFgY0q43o','21236547F',123,147852369,00002),
(00050,'Marta','López','456852123','marta@gmail.com','$31$16$0iay1XiK6ryzH5r8RLR8I1bc06LdaESw88mLaGL5dEU','54213698G',89,965742324,00001);

/*Data for the table `space` */

insert  into `space`(`id_space`,`name`,`serviceType`) values 
(00001,'Sala 1','Asesoría de Imagen Personal'),
(00002,'prueba','prueba'),
(00003,'New Space','Service B');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
