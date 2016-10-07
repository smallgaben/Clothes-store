-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: shop
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` enum('accepted','confirmed','forming','sent','finished','canceled') NOT NULL DEFAULT 'forming',
  `description` varchar(200) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) DEFAULT NULL,
  `address` varchar(45) NOT NULL,
  `creditcard` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `product_count` int(11) NOT NULL,
  `product_price` decimal(10,0) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_fk_idx` (`product_id`),
  KEY `order_fk_idx` (`order_id`),
  CONSTRAINT `order_fk` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `category` varchar(20) NOT NULL,
  `brand` varchar(20) NOT NULL,
  `price` float NOT NULL,
  `type` varchar(20) NOT NULL,
  `image_path` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'The king lion of the library','Men','Tom Taylor',150,'T-shirt','197714.a7.b552cS7ayBAA-640x640-b-p.jpg'),(2,'Ink Dragon','Men','Tom Taylor',175,'T-shirt','24177.a7.3bc91S7YyAQA-640x640-b-p.jpg'),(3,'Cute Baby Panda','Women','Tom Taylor',120,'T-shirt','28569.b9.79c79S7YyAgA-640x640-b-p.jpg'),(4,'Biceps Are Importanter Dark Tees','Women','Tom Taylor',140,'T-shirt','211166.b9.63452S7YyNAAA-640x640-b-p.jpg'),(5,'California Love','Kids','Tom Taylor',70,'T-shirt','153400.d59.da526S7ayBAA-640x640-b-p.jpg'),(6,'My piano','Kids','Tom Taylor',90,'T-shirt','10715.d07.dd733S7ayBAA-640x640-b-p.jpg'),(7,'Dark blue wash Dylan slim fit jeans','Men','Levis',300,'Jeans','286749_main.jpg'),(8,'ASOS Skinny Shirt In Teardrop Print','Men','Tom Taylor',120,'Shirt','ervetrtnetmn.jpg'),(9,'Blue Casual Shirt','Kids','Adidas',120,'T-shirt','aeuhvbwrhvbwrvbt.jpg'),(10,'CLIMACHILL TEE','Men','Adidas',140,'T-shirt','AO2920_21_model.jpg'),(11,'Adidas Women Originals Silver Glitter Shiny','Women','Adidas',156,'Jeans','s-l1600.jpg'),(12,'NIKE PRO COOL LONGSLEEVE','Women','Nike',236,'shirt','25740010_fr_ven_sc7.jpg'),(13,'adidas Originals Graphic T-Shirt','Kids','Adidas',77,'T-shirt','AA0114_21_model.jpg'),(14,'Nike Gym Vintage Full Zip Hoodie - Women','Women','Nike',457,'Jacket','nike-gym-vintage-fus.jpg'),(15,'Ideology Girls Melange Active Zip-Up ','Kids','Levis',357,'Jacket','3337483_fpx.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `realname` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `phonenum` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(50) NOT NULL,
  `image_path` varchar(60) DEFAULT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phonenum_UNIQUE` (`phonenum`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (16,'smallgaben','Myroslav','Prutkov','123-123-1234','smallgaben@gmail.com','Pfdnhfr040','-8533971569688433418.jpg','USER'),(17,'admin','adminushka','admenskiy','123-123-7890','admin@ad.min','admin','18638118.jpg','ADMIN'),(18,'mirka040','Myroslav','Prutkov','456-432-5676','mirka040@ukr.net','Pfdnhfr040','-2182544174140605248.jpg','USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-08 13:11:10
