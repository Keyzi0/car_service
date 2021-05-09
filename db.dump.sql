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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `car` */

insert  into `car`(`id`,`model`,`color`,`year`,`sign`,`owner_id`) values 
(1,'Chevrolet Charger','yellow',1980,'e233po22',1),
(5,'Suzuki Grand Vitara','Green',2016,'a233po54',1),
(6,'Volvo s40','Черный',2005,'а232оо18',3),
(7,'Ауди Q5','Белый',2005,'а222аа19',4);

/*Table structure for table `car_owner` */

DROP TABLE IF EXISTS `car_owner`;

CREATE TABLE `car_owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passport` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `car_owner` */

insert  into `car_owner`(`id`,`name`,`passport`,`address`,`age`) values 
(1,'Северов Николай Петрович','9204 233223','ул. Союзная 14-22',42),
(3,'Сергеев Олег Михайлович','2932 983322','ул. Советская 10-23',33),
(4,'Иванов Сергей Николаевич','1542 321145','ул. Главная 6',25),
(5,'Матросов Святослав Александрович','1232 321332','ул. Котаева 221',23),
(6,'Котовский Андрей Максимович','3242 234432','ул. Светлая 77',43);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `ticket` */

insert  into `ticket`(`id`,`car_owner_id`,`car_id`,`mechanic_id`,`defect_id`,`status_id`,`price`,`income_date`,`finish_date`) values 
(3,3,6,2,4,0,1254,'2021-05-07',NULL),
(4,3,6,1,2,0,35434,'2021-05-07',NULL),
(5,3,6,3,3,0,25254,'2021-05-07',NULL),
(7,3,6,2,2,0,257,'2021-05-07',NULL),
(9,1,1,3,2,0,5785,'2021-05-07',NULL),
(13,3,6,2,1,2,2547,'2021-05-07',NULL),
(14,3,6,2,2,4,2546,'2021-05-07',NULL),
(15,3,6,2,2,2,1456,'2021-05-07',NULL),
(16,3,6,1,1,0,584,'2021-05-07',NULL),
(18,4,7,2,4,3,3000,'2021-05-08',NULL);

/*Table structure for table `ticketview` */

DROP TABLE IF EXISTS `ticketview`;

/*!50001 DROP VIEW IF EXISTS `ticketview` */;
/*!50001 DROP TABLE IF EXISTS `ticketview` */;

/*!50001 CREATE TABLE  `ticketview`(
 `id` int(11) ,
 `car_owner` varchar(120) ,
 `car_owner_id` int(11) ,
 `car` varchar(200) ,
 `car_id` int(11) ,
 `mechanic` varchar(120) ,
 `mechanic_id` int(11) ,
 `defect` varchar(100) ,
 `defect_id` int(11) ,
 `status` varchar(20) ,
 `status_id` int(11) ,
 `income_date` date ,
 `price` int(11) 
)*/;

/*View structure for view ticketview */

/*!50001 DROP TABLE IF EXISTS `ticketview` */;
/*!50001 DROP VIEW IF EXISTS `ticketview` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`user`@`%` SQL SECURITY DEFINER VIEW `ticketview` AS (select `ticket`.`id` AS `id`,`car_owner`.`name` AS `car_owner`,`car_owner`.`id` AS `car_owner_id`,`car`.`model` AS `car`,`car`.`id` AS `car_id`,`mechanic`.`name` AS `mechanic`,`mechanic`.`id` AS `mechanic_id`,`defect`.`name` AS `defect`,`defect`.`id` AS `defect_id`,`status`.`name` AS `status`,`status`.`id` AS `status_id`,`ticket`.`income_date` AS `income_date`,`ticket`.`price` AS `price` from (((((`ticket` left join `car_owner` on((`ticket`.`car_owner_id` = `car_owner`.`id`))) left join `car` on((`ticket`.`car_id` = `car`.`id`))) left join `defect` on((`ticket`.`defect_id` = `defect`.`id`))) left join `mechanic` on((`ticket`.`mechanic_id` = `mechanic`.`id`))) left join `status` on((`ticket`.`status_id` = `status`.`id`)))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
