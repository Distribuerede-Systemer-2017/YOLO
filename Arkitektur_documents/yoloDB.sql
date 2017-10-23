# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: distribueredesystemer.cqsg17giwvxa.eu-central-1.rds.amazonaws.com (MySQL 5.6.35-log)
# Database: yolo
# Generation Time: 2017-10-23 13:02:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Items
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Items`;

CREATE TABLE `Items` (
  `item_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `itemName` varchar(45) NOT NULL DEFAULT ' ',
  `itemDescription` varchar(45) NOT NULL DEFAULT ' ',
  `itemPrice` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Items` WRITE;
/*!40000 ALTER TABLE `Items` DISABLE KEYS */;

INSERT INTO `Items` (`item_id`, `itemName`, `itemDescription`, `itemPrice`)
VALUES
	(1,'Kage','Kage',20);

/*!40000 ALTER TABLE `Items` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Order_has_Items
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Order_has_Items`;

CREATE TABLE `Order_has_Items` (
  `Items_itemId` int(11) unsigned NOT NULL,
  `Orders_orderId` int(11) unsigned NOT NULL,
  KEY `OrderId_user_relation` (`Orders_orderId`),
  KEY `ItemsId_order_relation` (`Items_itemId`),
  CONSTRAINT `ItemsId_order_relation` FOREIGN KEY (`Items_itemId`) REFERENCES `Items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OrderId_user_relation` FOREIGN KEY (`Orders_orderId`) REFERENCES `Orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Orders
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Orders`;

CREATE TABLE `Orders` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orderTime` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `isReady` tinyint(1) NOT NULL DEFAULT '0',
  `user_userid` int(11) unsigned NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `Userid_OrderRelation` (`user_userid`),
  CONSTRAINT `Userid_OrderRelation` FOREIGN KEY (`user_userid`) REFERENCES `Users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Token`;

CREATE TABLE `Token` (
  `tokenId` int(11) NOT NULL AUTO_INCREMENT,
  `tokenString` longtext NOT NULL,
  `Users_user_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`tokenId`),
  UNIQUE KEY `tokenId_UNIQUE` (`tokenId`),
  KEY `fk_Token_Users1_idx` (`Users_user_id`),
  CONSTRAINT `fk_Token_Users1` FOREIGN KEY (`Users_user_id`) REFERENCES `Users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Token` WRITE;
/*!40000 ALTER TABLE `Token` DISABLE KEYS */;

INSERT INTO `Token` (`tokenId`, `tokenString`, `Users_user_id`)
VALUES
	(30,'eyJraWQiOiIzNCIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJpc3MiOiJZT0xPIiwiZXhwIjoxNTA4NzgzMTA0OTg3LCJ1c2VybmFtZSI6ImR1bW15In0.ovkW56oWZmI6PmCo6yRRErBtDymyYocfGQl9Jd30ssA',34),
	(32,'eyJraWQiOiIzMSIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJpc3MiOiJZT0xPIiwiZXhwIjoxNTA4NzgzNDE2MTk5LCJ1c2VybmFtZSI6Ikx1a2FzIn0.aud2PpTrFcYgna5RtJZaHOfiogag9vXkA3wI9E3AHnY',31);

/*!40000 ALTER TABLE `Token` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `isPersonel` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;

INSERT INTO `Users` (`user_id`, `username`, `password`, `isPersonel`)
VALUES
	(31,'Lukas','2c18952a86189779eac02b55b96d73c308cd5e81dd08af9a7e94062cb57ca3d5',0),
	(32,'Frederik','31182e57a88dd335a5b86767dd60951a80bb63ce8f1b3c99eb32eb08ae203449',0),
	(33,'felix','fdfe07ac911a7168b7fe0c4394db122ce1afa78de66d00117df6f39aea142c6c',0),
	(34,'dummy','26bf81e61b60acb212dbce66b1048aa001e0a120a02bdf962afeb43a0ea1d9a5',0);

/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
