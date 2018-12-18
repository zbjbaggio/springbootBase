CREATE DATABASE  IF NOT EXISTS `junJie` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `junJie`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 192.168.101.2    Database: junJie
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_manager_info`
--

DROP TABLE IF EXISTS `t_manager_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_manager_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `name` varchar(30) NOT NULL COMMENT '真实姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `salt` varchar(50) NOT NULL COMMENT '加密密码的盐',
  `status` tinyint(4) NOT NULL COMMENT '用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `dr` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 正常 1 删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j3bb02ycn6rf98pp2kx08fnu1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_manager_info`
--

LOCK TABLES `t_manager_info` WRITE;
/*!40000 ALTER TABLE `t_manager_info` DISABLE KEYS */;
INSERT INTO `t_manager_info` VALUES (32,'2017-11-10 09:06:09','2018-01-24 07:23:14','jinbao@163.com','金宝1','63278f4c2373d5658c491e2098c2a37b','18210596067','02ee5301-7db4-486f-a1ff-bd550691c74e',1,'jinbao',20,0),(35,'2017-11-24 21:51:16','2017-11-24 13:51:16','press@junjieofficial.com','杨峻杰','962249c48d7f48bc1eaefaf2ab709729','13800000000','a05980fb-d1c1-441c-88a0-3373dffdb118',1,'junjie',32,0),(36,'2018-01-25 10:56:28','2018-02-01 07:07:46','taizi@163.com','张宝军的大太子','36db7c0902aa889d112ac7e500afa639','15010111250','4b436351-c495-4bdc-978b-db80c185bed2',1,'lilong',35,0),(37,'2018-01-25 11:23:33','2018-01-25 03:53:17','1111@111111.com','1111','24f1eed0db7f75d9584bb3b7273e91cb','11111111111','efb1048d-ce8c-412c-86a0-c4e794fde867',1,'111111',35,1),(38,'2018-01-25 11:32:03','2018-01-25 03:53:17','11111111@141413132.cn','111111','7472091346b35a4d5edae8ce813b81f9','11111111111','3d8ab0e5-6dc7-43d8-9c4e-df1ebb256220',1,'1111111',35,1),(39,'2018-01-25 11:33:23','2018-01-25 03:53:12','11@1.com','11313','813e8447d335b0139203ce3f17d352db','31313333333','d27d7e38-a801-467a-8bff-fed642d854a7',1,'3333332222',35,1);
/*!40000 ALTER TABLE `t_manager_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_manager_role`
--

DROP TABLE IF EXISTS `t_manager_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_manager_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `manager_id` bigint(20) NOT NULL COMMENT '管理员id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`manager_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_manager_role`
--

LOCK TABLES `t_manager_role` WRITE;
/*!40000 ALTER TABLE `t_manager_role` DISABLE KEYS */;
INSERT INTO `t_manager_role` VALUES (1,'2017-12-13 10:19:48','2017-12-13 02:20:05',32,1),(21,'2018-01-29 17:09:51','2018-01-29 09:09:51',35,51),(23,'2018-01-29 17:09:51','2018-01-29 09:09:51',32,51),(24,'2018-02-01 14:56:10','2018-02-01 06:56:10',36,51);
/*!40000 ALTER TABLE `t_manager_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_detail`
--

DROP TABLE IF EXISTS `t_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_id` bigint(20) NOT NULL COMMENT '订单主表id',
  `product_id` bigint(20) NOT NULL COMMENT '产品id',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `price` decimal(18,2) NOT NULL COMMENT '产品单价',
  `number` int(11) NOT NULL COMMENT '数量',
  `size` varchar(10) NOT NULL COMMENT '产品大小',
  `amount` decimal(18,2) NOT NULL COMMENT '该产品总额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COMMENT='订单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_detail`
--

LOCK TABLES `t_order_detail` WRITE;
/*!40000 ALTER TABLE `t_order_detail` DISABLE KEYS */;
INSERT INTO `t_order_detail` VALUES (126,'2017-11-27 18:58:22','2017-11-27 10:58:22',115,1,'ORANGE PUFFER HOODIE CAPE',1.00,1,'WOMAN 40=S',1.00),(129,'2017-11-29 04:30:54','2017-11-28 20:30:54',118,1,'ORANGE PUFFER HOODIE CAPE',1.00,1,'WOMAN 40=S',1.00),(132,'2017-12-01 00:54:26','2017-11-30 16:54:26',121,1,'ORANGE PUFFER HOODIE CAPE',500.00,1,'MAN 48=M',500.00),(133,'2017-12-07 10:47:52','2017-12-07 02:47:52',122,1,'ORANGE PUFFER HOODIE CAPE',500.00,1,'WOMAN 40=S',500.00);
/*!40000 ALTER TABLE `t_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_info`
--

DROP TABLE IF EXISTS `t_order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `email` varchar(50) NOT NULL COMMENT 'email地址',
  `receiver_name` varchar(50) NOT NULL COMMENT '接受人姓名',
  `receiver_address1` varchar(200) NOT NULL COMMENT '接收人地址',
  `amount` decimal(18,2) NOT NULL COMMENT '总额',
  `status` int(2) NOT NULL COMMENT '订单状态 1 正常订单 2 支付中订单 3 订单支付完成 4 订单处理完成',
  `description` varchar(200) DEFAULT NULL COMMENT '描述用于订单用户备注',
  `receiver_city` varchar(100) NOT NULL COMMENT '收货城市',
  `receiver_country` varchar(100) NOT NULL COMMENT '收货国家',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注，用于后台人员使用',
  `postage` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '邮费',
  `order_no` varchar(50) NOT NULL,
  `dr` tinyint(1) NOT NULL DEFAULT '0',
  `payment_id` varchar(255) DEFAULT NULL,
  `receiver_address2` varchar(200) DEFAULT NULL COMMENT '接收人地址',
  `postcode` varchar(100) NOT NULL COMMENT '邮编',
  `receiver_area` varchar(100) NOT NULL COMMENT '收货地区',
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_UNIQUE` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_info`
--

LOCK TABLES `t_order_info` WRITE;
/*!40000 ALTER TABLE `t_order_info` DISABLE KEYS */;
INSERT INTO `t_order_info` VALUES (115,'2017-11-27 18:58:22','2017-11-27 10:58:42','happy1happy3%40gmail.com','y l','balabalabala',2.00,3,NULL,'london','Rest of the world',NULL,1.00,'10171127185880306208',0,'PAY-5PH264399U4194126LIN67TQ','','sw1p 4as','',''),(118,'2017-11-29 04:30:54','2017-11-30 09:20:31','happy1happy3@gmail.com','kelly Liang','Balllllllbala',2.00,4,NULL,'London ','Uk',NULL,1.00,'10171129043001053970',0,'PAY-9YV82739KR424770CLIO4O7Y','','Sw1p5se','Rest of the world','07746158823'),(121,'2017-12-01 00:54:26','2017-11-30 16:55:25','thejontouch@gmail.com','Jonathan  Hamilton ','132 Walker st sw apt 21',530.00,3,NULL,'Atl','Usa',NULL,30.00,'10171201005460868941',0,'PAY-4JA01583T9357770FLIQDPQY','','30313','USA/Canada','5042067285'),(122,'2017-12-07 10:47:52','2017-12-07 02:52:52','jinbaow@yeah.net','wang jinbao','beijing',525.00,2,NULL,'beijing','China',NULL,25.00,'10171207104714880644',1,'PAY-40454489NJ506653MLIUKXWI','','10000','Asia','13636363636');
/*!40000 ALTER TABLE `t_order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_permission_info`
--

DROP TABLE IF EXISTS `t_permission_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_permission_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL COMMENT '是否可用',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(30) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(30) DEFAULT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view',
  `resource_type` enum('menu','button') NOT NULL COMMENT '资源类型，[menu|button]',
  `be_url` varchar(100) DEFAULT NULL COMMENT '后台资源路径',
  `icon` varchar(40) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `fe_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission_info`
--

LOCK TABLES `t_permission_info` WRITE;
/*!40000 ALTER TABLE `t_permission_info` DISABLE KEYS */;
INSERT INTO `t_permission_info` VALUES (20,'2017-10-27 13:47:16','2018-05-11 07:40:40','','系统管理',0,'0','system:*','menu',NULL,'el-icon-setting','0002','/0002'),(21,'2017-10-27 13:47:37','2018-05-11 07:40:40','','管理员信息',20,'20','managerManage:*','menu','/manage/user/managerInfo/list',NULL,'00020002','/managerManage'),(24,'2017-12-13 14:18:48','2018-05-11 07:40:40','','订单管理',0,'0','order:*','menu',NULL,'el-icon-document','0001','/0001'),(25,'2017-12-13 14:18:48','2018-05-11 07:40:40','','订单信息',24,'24','orderInfo:','menu','/manage/user/orderInfo/list',NULL,'00010002','/orderInfo'),(26,'2017-12-13 14:18:48','2018-05-11 07:40:40','','邮费管理',24,'24','','menu','/manage/user/postageInfo/list',NULL,'00010001','/postageInfo'),(27,'2017-12-13 14:18:48','2018-05-11 07:40:40','','邮费查询',26,'20.26','','button','/manage/user/postageInfo/list',NULL,'000100010001',NULL),(28,'2017-12-13 14:18:48','2018-05-11 07:40:40','','菜单信息',20,'20','managerManage:*','menu','/manage/user/menu/list',NULL,'00020003','/menuManage'),(36,'2018-01-11 10:27:08','2018-05-11 07:40:40','','角色信息',20,NULL,NULL,'menu','/manage/user/role/list',NULL,'00020001','/roleManage'),(111,'2018-02-01 10:43:14','2018-05-11 07:40:40','','管理员编辑',21,NULL,NULL,'button','/manage/user/managerInfo/getDetail',NULL,'000200020001',NULL),(112,'2018-02-01 10:44:34','2018-05-11 07:40:40','','管理员锁定',21,NULL,NULL,'button','/manage/user/managerInfo/updateFreeze',NULL,'000200020002',NULL),(113,'2018-02-01 10:49:21','2018-05-11 07:40:40','','管理员保存',21,NULL,NULL,'button','/manage/user/managerInfo/save',NULL,'000200020003',NULL),(114,'2018-02-01 10:50:08','2018-05-11 07:40:40','','管理员删除',21,NULL,NULL,'button','/manage/user/managerInfo/remove',NULL,'000200020004',NULL),(115,'2018-02-01 10:50:55','2018-05-11 07:40:40','','管理员角色',21,NULL,NULL,'button','/manage/user/managerInfo/listRole',NULL,'000200020005',NULL),(116,'2018-02-01 10:51:25','2018-05-11 07:40:40','','管理员角色保存',21,NULL,NULL,'button','/manage/user/managerInfo/saveRoles',NULL,'000200020006',NULL),(117,'2018-02-01 10:54:15','2018-05-11 07:40:40','','管理员查询',21,NULL,NULL,'button','/manage/user/managerInfo/list',NULL,'000200020007',NULL),(118,'2018-02-01 11:05:12','2018-05-11 07:40:40','','菜单查询',28,NULL,NULL,'button','/manage/user/menu/list',NULL,'000200030001',NULL),(119,'2018-02-01 11:05:38','2018-05-11 07:40:40','','菜单保存',28,NULL,NULL,'button','/manage/user/menu/save',NULL,'000200030002',NULL),(120,'2018-02-01 11:05:58','2018-05-11 07:40:40','','菜单删除',28,NULL,NULL,'button','/manage/user/menu/remove',NULL,'000200030003',NULL),(121,'2018-02-01 11:06:19','2018-05-11 07:40:40','','菜单详情',28,NULL,NULL,'button','/manage/user/menu/getDetail',NULL,'000200030004',NULL),(122,'2018-02-01 11:07:14','2018-05-11 07:40:40','','菜单按钮详情',28,NULL,NULL,'button','/manage/user/menu/buttonDetail',NULL,'000200030005',NULL),(123,'2018-02-01 11:07:59','2018-05-11 07:40:40','','菜单按钮保存',28,NULL,NULL,'button','/manage/user/menu/saveButton',NULL,'000200030006',NULL),(124,'2018-02-01 11:08:34','2018-05-11 07:40:40','','菜单按钮删除',28,NULL,NULL,'button','/manage/user/menu/removeButton',NULL,'000200030007',NULL),(125,'2018-02-01 11:09:39','2018-05-11 07:40:40','','角色查询',36,NULL,NULL,'button','/manage/user/role/list',NULL,'000200010001',NULL),(126,'2018-02-01 11:10:04','2018-05-11 07:40:40','','角色保存',36,NULL,NULL,'button','/manage/user/role/save',NULL,'000200010002',NULL),(127,'2018-02-01 11:10:37','2018-05-11 07:40:40','','角色编辑',36,NULL,NULL,'button','/manage/user/role/getDetail',NULL,'000200010003',NULL),(128,'2018-02-01 11:19:25','2018-05-11 07:40:40','','角色删除',36,NULL,NULL,'button','/manage/user/role/remove',NULL,'000200010004',NULL),(129,'2018-02-01 11:20:21','2018-05-11 07:40:40','','角色权限',36,NULL,NULL,'button','/manage/user/role/getPermissionDetail',NULL,'000200010005',NULL),(130,'2018-02-01 11:21:11','2018-05-11 07:40:40','','角色权限保存',36,NULL,NULL,'button','/manage/user/role/savePermission',NULL,'000200010006',NULL),(131,'2018-02-01 11:22:14','2018-05-11 07:40:40','','角色用户',36,NULL,NULL,'button','/manage/user/role/listUser',NULL,'000200010007',NULL),(132,'2018-02-01 11:22:41','2018-05-11 07:40:40','','角色用户保存',36,NULL,NULL,'button','/manage/user/role/saveUsers',NULL,'000200010008',NULL);
/*!40000 ALTER TABLE `t_permission_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_postage_info`
--

DROP TABLE IF EXISTS `t_postage_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_postage_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(50) NOT NULL,
  `price` decimal(18,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_postage_info`
--

LOCK TABLES `t_postage_info` WRITE;
/*!40000 ALTER TABLE `t_postage_info` DISABLE KEYS */;
INSERT INTO `t_postage_info` VALUES (1,'2017-11-24 09:55:03','2017-11-30 02:38:41','USA/Canada',30.00),(2,'2017-11-24 09:57:23','2017-11-30 02:38:41','Asia',25.00),(3,'2017-11-24 11:10:14','2017-11-30 02:38:41','Rest of the world',30.00);
/*!40000 ALTER TABLE `t_postage_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_product_info`
--

DROP TABLE IF EXISTS `t_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_product_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `price` decimal(18,2) NOT NULL COMMENT '价格',
  `dr` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 正常 1 删除',
  `status` int(2) NOT NULL COMMENT '产品状态 1:上架 2:下架',
  `operator_id` bigint(20) NOT NULL,
  `product_no` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_no_UNIQUE` (`product_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_product_info`
--

LOCK TABLES `t_product_info` WRITE;
/*!40000 ALTER TABLE `t_product_info` DISABLE KEYS */;
INSERT INTO `t_product_info` VALUES (1,'2017-11-11 15:57:14','2017-11-30 02:37:36','ORANGE PUFFER HOODIE CAPE','1','11',500.00,0,1,20,'NO1'),(2,'2017-11-11 17:05:21','2017-11-24 02:48:30','LONG FLAME RACCON FUR COAT','444','666',18050.00,0,1,20,'NO2'),(3,'2017-11-11 18:08:44','2017-11-23 09:31:36','RETRO SPORTS WEAR INSPIRED WHITE MINK JACKET','11','11',14400.00,0,1,20,'NO3'),(4,'2017-11-11 18:08:58','2017-11-23 09:32:18','RETRO SPORTS WEAR INSPIRED BROWN MINK JACKET','22','2',14400.00,0,1,20,'NO4');
/*!40000 ALTER TABLE `t_product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_info`
--

DROP TABLE IF EXISTS `t_role_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL COMMENT '是否可用',
  `description` text COMMENT '角色描述,UI界面显示使用',
  `name` varchar(30) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_info`
--

LOCK TABLES `t_role_info` WRITE;
/*!40000 ALTER TABLE `t_role_info` DISABLE KEYS */;
INSERT INTO `t_role_info` VALUES (1,'2017-10-12 15:53:08','2017-10-12 07:53:14','','拥有所有权限','超级管理员'),(51,'2018-01-12 14:00:47','2018-01-12 06:15:43','','只能看到订单管理','业务员');
/*!40000 ALTER TABLE `t_role_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_permission`
--

DROP TABLE IF EXISTS `t_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_permission`
--

LOCK TABLES `t_role_permission` WRITE;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` VALUES (122,'2018-02-01 15:25:59','2018-02-01 07:25:59',128,51),(123,'2018-02-01 15:25:59','2018-02-01 07:25:59',129,51),(124,'2018-02-01 15:25:59','2018-02-01 07:25:59',130,51),(125,'2018-02-01 15:25:59','2018-02-01 07:25:59',131,51),(126,'2018-02-01 15:25:59','2018-02-01 07:25:59',36,51),(127,'2018-02-01 15:25:59','2018-02-01 07:25:59',132,51),(128,'2018-02-01 15:25:59','2018-02-01 07:25:59',111,51),(129,'2018-02-01 15:25:59','2018-02-01 07:25:59',112,51),(130,'2018-02-01 15:25:59','2018-02-01 07:25:59',114,51),(131,'2018-02-01 15:25:59','2018-02-01 07:25:59',20,51),(132,'2018-02-01 15:25:59','2018-02-01 07:25:59',117,51),(133,'2018-02-01 15:25:59','2018-02-01 07:25:59',21,51),(134,'2018-02-01 15:25:59','2018-02-01 07:25:59',118,51),(135,'2018-02-01 15:25:59','2018-02-01 07:25:59',24,51),(136,'2018-02-01 15:25:59','2018-02-01 07:25:59',25,51),(137,'2018-02-01 15:25:59','2018-02-01 07:25:59',28,51),(138,'2018-02-01 15:25:59','2018-02-01 07:25:59',125,51),(139,'2018-02-01 15:25:59','2018-02-01 07:25:59',126,51),(140,'2018-02-01 15:25:59','2018-02-01 07:25:59',127,51),(200,'2018-11-06 14:30:44','2018-11-06 06:30:44',128,1),(201,'2018-11-06 14:30:44','2018-11-06 06:30:44',129,1),(202,'2018-11-06 14:30:44','2018-11-06 06:30:44',130,1),(203,'2018-11-06 14:30:44','2018-11-06 06:30:44',131,1),(204,'2018-11-06 14:30:44','2018-11-06 06:30:44',132,1),(205,'2018-11-06 14:30:44','2018-11-06 06:30:44',20,1),(206,'2018-11-06 14:30:44','2018-11-06 06:30:44',21,1),(207,'2018-11-06 14:30:44','2018-11-06 06:30:44',24,1),(208,'2018-11-06 14:30:44','2018-11-06 06:30:44',25,1),(209,'2018-11-06 14:30:44','2018-11-06 06:30:44',26,1),(210,'2018-11-06 14:30:44','2018-11-06 06:30:44',27,1),(211,'2018-11-06 14:30:44','2018-11-06 06:30:44',28,1),(212,'2018-11-06 14:30:44','2018-11-06 06:30:44',36,1),(213,'2018-11-06 14:30:44','2018-11-06 06:30:44',111,1),(214,'2018-11-06 14:30:44','2018-11-06 06:30:44',112,1),(215,'2018-11-06 14:30:44','2018-11-06 06:30:44',113,1),(216,'2018-11-06 14:30:44','2018-11-06 06:30:44',114,1),(217,'2018-11-06 14:30:44','2018-11-06 06:30:44',115,1),(218,'2018-11-06 14:30:44','2018-11-06 06:30:44',116,1),(219,'2018-11-06 14:30:44','2018-11-06 06:30:44',117,1),(220,'2018-11-06 14:30:44','2018-11-06 06:30:44',118,1),(221,'2018-11-06 14:30:44','2018-11-06 06:30:44',119,1),(222,'2018-11-06 14:30:44','2018-11-06 06:30:44',120,1),(223,'2018-11-06 14:30:44','2018-11-06 06:30:44',121,1),(224,'2018-11-06 14:30:44','2018-11-06 06:30:44',122,1),(225,'2018-11-06 14:30:44','2018-11-06 06:30:44',125,1),(226,'2018-11-06 14:30:44','2018-11-06 06:30:44',126,1),(227,'2018-11-06 14:30:44','2018-11-06 06:30:44',127,1);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-29 13:28:15
