/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 5.5.37-log : Database - car_service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`car_service` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `car_service`;

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `color` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `sign` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `car` */

insert  into `car`(`id`,`model`,`color`,`year`,`sign`,`owner_id`) values 
(1,'Chevrolet Charger','yellow',1980,'e233po22',1),
(2,'Dodge Stratus','black',1972,'e442ca14',2),
(3,'Mersedes Smart',NULL,2018,'y222ko77',2),
(5,'Suzuki Grand Vitara','Green',2016,'a233po54',1),
(6,'Volvo s40','Черный',2005,'а232оо18',3);

/*Table structure for table `car_owner` */

DROP TABLE IF EXISTS `car_owner`;

CREATE TABLE `car_owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passport` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `car_owner` */

insert  into `car_owner`(`id`,`name`,`passport`,`address`,`age`) values 
(1,'Северов Николай Петрович','9204 233223','ул. Союзная 14-22',42),
(2,'Ковров Никита Петрович','9233 211122','ул. Южная 88-23',23),
(3,'Сергеев Олег Михайлович','2932 983322','ул. Советская 10-23',33);

/*Table structure for table `defect` */

DROP TABLE IF EXISTS `defect`;

CREATE TABLE `defect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `defect` */

insert  into `defect`(`id`,`name`) values 
(1,'Электрика'),
(2,'Двигатель'),
(3,'Кузов'),
(4,'Что-то стучит');

/*Table structure for table `mechanic` */

DROP TABLE IF EXISTS `mechanic`;

CREATE TABLE `mechanic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL,
  `specialty` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `mechanic` */

insert  into `mechanic`(`id`,`name`,`specialty`,`rank`) values 
(1,'Гринев Виталий Сергеевич','автоэлектрик',5),
(2,'Снегирев Павел Алексеевич','кузовщик',3),
(3,'Горбушин Виктор Степанович','мастер на все руки',1);

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `status` */

insert  into `status`(`id`,`name`) values 
(0,'Новая задача'),
(1,'В работе'),
(2,'Ожидание запчастей'),
(3,'Готова'),
(4,'Переназначена'),
(5,'Отменена'),
(6,'Архив');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_owner_id` int(11) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `mechanic_id` int(11) DEFAULT NULL,
  `defect_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `income_date` date DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `ticket` */

insert  into `ticket`(`id`,`car_owner_id`,`car_id`,`mechanic_id`,`defect_id`,`status_id`,`price`,`income_date`,`finish_date`) values 
(1,2,3,3,4,0,0,'2021-05-06',NULL),
(2,2,3,1,3,0,0,'2021-05-07',NULL),
(3,3,6,2,4,0,0,'2021-05-07',NULL),
(4,3,6,1,2,0,0,'2021-05-07',NULL),
(5,3,6,3,3,0,0,'2021-05-07',NULL),
(6,2,3,3,4,0,0,'2021-05-07',NULL),
(7,3,6,2,2,0,0,'2021-05-07',NULL),
(8,2,3,1,2,0,0,'2021-05-07',NULL),
(9,1,1,3,2,0,0,'2021-05-07',NULL),
(10,2,3,3,4,4,61651,'2021-05-07',NULL),
(11,2,3,3,2,2,55556,'2021-05-07',NULL),
(12,2,2,1,4,3,2123123,'2021-05-07',NULL),
(13,3,6,2,1,2,0,'2021-05-07',NULL),
(14,3,6,2,2,4,0,'2021-05-07',NULL),
(15,3,6,2,2,2,0,'2021-05-07',NULL),
(16,3,6,1,1,0,0,'2021-05-07',NULL),
(17,2,3,2,4,5,445412,'2021-05-07',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
