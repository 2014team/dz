/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - artcweb_admin
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`artcweb_admin` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `artcweb_admin`;

/*Table structure for table `admin_user` */

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `vaild` int(11) NOT NULL DEFAULT '0' COMMENT '0:无效 1:有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user` */

insert  into `admin_user`(`id`,`user_name`,`password`,`create_date`,`update_date`,`email`,`vaild`) values (1,'admin','admin','2019-11-14 20:41:16','2019-11-14 20:41:18','',0),(2,'lisi','lisi','2019-11-14 23:38:51','2019-11-14 23:38:53','',0);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `package_id` varchar(20) NOT NULL COMMENT '套餐ID',
  `current_step` varchar(50) DEFAULT NULL COMMENT '当前步骤',
  `sort` int(11) NOT NULL COMMENT '排序',
  `template` tinyint(4) DEFAULT '0' COMMENT '0:新建模板 1：选择模板',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`mobile`,`package_id`,`current_step`,`sort`,`template`,`create_date`,`update_date`) values (43,'18973919850','20','54',1,0,'2019-11-24 16:17:44','2019-11-24 16:17:44'),(45,'13537612226','20','',1,1,'2019-11-24 14:17:34','2019-11-24 14:17:34'),(46,'18973919800','20','',1,1,'2019-11-24 14:59:07','2019-11-24 14:59:07'),(47,'18973919850','21','',1,1,'2019-11-24 15:00:34','2019-11-24 15:00:34');

/*Table structure for table `pic_package` */

DROP TABLE IF EXISTS `pic_package`;

CREATE TABLE `pic_package` (
  `package_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '套餐ID',
  `package_name` varchar(100) NOT NULL COMMENT '套餐名称',
  `image_url` varchar(500) NOT NULL COMMENT '图片地址',
  `step` varchar(6000) NOT NULL COMMENT '执行步骤',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改日期',
  KEY `package_id` (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `pic_package` */

insert  into `pic_package`(`package_id`,`package_name`,`image_url`,`step`,`create_date`,`update_date`) values (20,'aaaa','/upload/packge/2019/11/23A72A5FBC68E4778515745760057113.jpg','aaaaa','2019-11-24 14:35:31','2019-11-24 14:35:31'),(21,'bb','/upload/packge/2019/11/23A72A5FBC68E4778515745760263654.jpg','bb','2019-11-24 14:13:46','2019-11-24 14:13:46');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`mobile`,`sort`,`create_date`,`update_date`) values (15,'13537612226',1,'2019-11-24 11:02:49','2019-11-24 11:02:49'),(17,'18973919859',1,'2019-11-24 14:57:28','2019-11-24 14:57:28'),(18,'18973919845',1,'2019-11-24 14:57:37','2019-11-24 14:57:37'),(19,'18973919851',1,'2019-11-24 14:57:55','2019-11-24 14:57:55'),(20,'18973919852',1,'2019-11-24 14:58:04','2019-11-24 14:58:04'),(21,'18973919855',1,'2019-11-24 14:58:12','2019-11-24 14:58:12'),(22,'18973919856',1,'2019-11-24 14:58:23','2019-11-24 14:58:23'),(23,'18973919811',1,'2019-11-24 14:58:28','2019-11-24 14:58:28'),(24,'18973919822',1,'2019-11-24 14:58:34','2019-11-24 14:58:34'),(25,'18973919800',1,'2019-11-24 14:58:41','2019-11-24 14:58:41'),(27,'18973919850',1,'2019-11-24 15:18:40','2019-11-24 15:18:40');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
