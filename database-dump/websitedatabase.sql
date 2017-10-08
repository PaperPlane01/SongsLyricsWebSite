-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: websitedatabase
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `artists`
--

DROP TABLE IF EXISTS `artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artists` (
  `artist_id` int(11) NOT NULL,
  `artist_name` varchar(90) NOT NULL,
  `artist_letter` varchar(45) NOT NULL,
  PRIMARY KEY (`artist_id`),
  UNIQUE KEY `artist_id_UNIQUE` (`artist_id`),
  UNIQUE KEY `artist_name_UNIQUE` (`artist_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artists`
--

LOCK TABLES `artists` WRITE;
/*!40000 ALTER TABLE `artists` DISABLE KEYS */;
INSERT INTO `artists` VALUES (1,'The Chainsmokers','T'),(2,'Astrid S','A'),(3,'Troyboi','T'),(4,'YAS','Y'),(5,'Linkin Park','L'),(6,'Kill Paris','K'),(7,'Royal','R'),(8,'Illenium','I'),(9,'Annika Wells','A'),(10,'Three Days Grace','T'),(11,'Lana Del Rey','L'),(12,'Rickey F','R'),(13,'Lizer','L'),(14,'Changed','C'),(15,'RUNN','R');
/*!40000 ALTER TABLE `artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `song_id` int(11) NOT NULL,
  `comment_content` text NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_id_UNIQUE` (`comment_id`),
  KEY `id_of_song_key_comments_idx` (`song_id`),
  KEY `id_of_user_key_comments_idx` (`user_id`),
  CONSTRAINT `id_of_song_key_comments` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_of_user_key_comments` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,11,'Test comment!',0,'2017-09-24 11:03:54',6),(2,11,'Another comment!',0,'2017-09-24 11:03:54',7),(3,8,'Test comment!',0,'2017-09-24 11:03:54',8),(4,8,'Another comment!',0,'2017-09-24 11:03:54',6),(5,8,'And another one!',0,'2017-09-24 11:03:54',7),(6,8,'Комментарий на русском.',0,'2017-09-24 11:03:54',8),(7,12,'What a wonderful song!',0,'2017-09-24 16:59:23',6),(8,12,'Beautiful song with amazing video, really enjoyed it!',1,'2017-09-24 11:03:54',6),(9,5,'Commentary',0,'2017-09-24 11:03:54',8),(10,7,'11111111111111111',0,'2017-09-24 11:03:54',8),(11,12,'11111111',0,'2017-09-24 17:42:40',6);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `featurings`
--

DROP TABLE IF EXISTS `featurings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `featurings` (
  `featuring_id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`featuring_id`),
  UNIQUE KEY `featuring_id_UNIQUE` (`featuring_id`),
  KEY `featured_artist_key_idx` (`artist_id`),
  KEY `id_of_song_key_featurings_idx` (`song_id`),
  CONSTRAINT `featured_artist_key` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_of_song_key_featurings` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `featurings`
--

LOCK TABLES `featurings` WRITE;
/*!40000 ALTER TABLE `featurings` DISABLE KEYS */;
INSERT INTO `featurings` VALUES (1,4,3,0),(2,7,5,0),(3,9,6,0),(6,13,13,0),(10,15,21,1);
/*!40000 ALTER TABLE `featurings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(45) NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `genre_id_UNIQUE` (`genre_id`),
  UNIQUE KEY `genre_name_UNIQUE` (`genre_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (10,'Dubstep'),(2,'EDM'),(9,'Future Bass'),(8,'Hip-Hop'),(4,'House'),(5,'Indie'),(6,'Pop'),(7,'Rap'),(3,'Rock'),(1,'Trap');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres_of_songs`
--

DROP TABLE IF EXISTS `genres_of_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres_of_songs` (
  `match_id` int(11) NOT NULL AUTO_INCREMENT,
  `song_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  `match_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`match_id`),
  UNIQUE KEY `match_id_UNIQUE` (`match_id`),
  KEY `id_of_genre_key_idx` (`genre_id`),
  KEY `id_of_song_key_genres_idx` (`song_id`),
  CONSTRAINT `id_of_genre_key` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_of_song_key_genres` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres_of_songs`
--

LOCK TABLES `genres_of_songs` WRITE;
/*!40000 ALTER TABLE `genres_of_songs` DISABLE KEYS */;
INSERT INTO `genres_of_songs` VALUES (1,1,1,0),(2,1,2,0),(3,2,1,0),(4,3,1,0),(5,3,2,0),(6,4,3,0),(7,5,2,0),(8,5,4,0),(9,6,1,0),(10,6,2,0),(14,7,3,0),(15,8,2,0),(18,12,6,0),(19,12,5,0),(24,13,7,0),(25,13,8,0),(26,8,1,0),(27,8,2,0),(31,13,2,0),(34,21,2,0),(35,21,9,0),(36,21,1,0),(37,21,10,0);
/*!40000 ALTER TABLE `genres_of_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lines`
--

DROP TABLE IF EXISTS `lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lines` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `song_id` int(11) NOT NULL,
  `content` varchar(150) NOT NULL,
  `song_part` enum('verse','chorus','hook','bridge','intro','outro','other') NOT NULL,
  `line_position` int(11) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`line_id`),
  UNIQUE KEY `line_id_UNIQUE` (`line_id`),
  KEY `id_of_song_key_lines_idx` (`song_id`),
  CONSTRAINT `id_of_song_key_lines` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=817 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lines`
--

LOCK TABLES `lines` WRITE;
/*!40000 ALTER TABLE `lines` DISABLE KEYS */;
INSERT INTO `lines` VALUES (1,1,'Crashing, hit a wall','verse',0,0),(2,1,'Right now I need a miracle','verse',1,0),(3,1,'Hurry up now, I need a miracle','verse',2,0),(4,1,'Stranded, reaching out','verse',3,0),(5,1,'I call your name, but you\'re not around','verse',4,0),(6,1,'I say your name, but you\'re not around','verse',5,0),(7,1,'I need you, I need you, I need you right now','chorus',6,0),(8,1,'Yeah, I need you right now','chorus',7,0),(9,1,'So don\'t let me, don\'t let me, don\'t let me down','chorus',8,0),(10,1,'I think I\'m losing my mind now','chorus',9,0),(11,1,'It\'s in my head, darling I hope','chorus',10,0),(12,1,'That you\'ll be here when I need you the most','chorus',11,0),(13,1,'So don\'t let me, don\'t let me, don\'t let me down','chorus',12,0),(14,1,'Don\'t let me down!','chorus',13,0),(15,1,'We\'re running out of time','verse',14,0),(16,1,'I really thought you were on my side','verse',15,0),(17,1,'But now there\'s nobody by my side','verse',16,0),(18,1,'I need you, I need you, I need you right now','chorus',17,0),(19,1,'Yeah, I need you right now','chorus',18,0),(20,1,'So don\'t let me, don\'t let me, don\'t let me down','chorus',19,0),(21,1,'I think I\'m losing my mind now','chorus',20,0),(22,1,'It\'s in my head, darling I hope','chorus',21,0),(23,1,'That you\'ll be here when I need you the most','chorus',22,0),(24,1,'So don\'t let me, don\'t let me, don\'t let me down!','chorus',23,0),(25,1,'Don\'t let me down!','chorus',24,0),(26,2,'You\'re fighting me off like a firefighter','verse',0,0),(27,2,'So tell me why you still get burned','verse',1,0),(28,2,'You say you\'re not, but you\'re still a liar','verse',2,0),(29,2,'\'Cause I\'m the one that you run to first','verse',3,0),(30,2,'Every time, yeah, why do you try to deny it','bridge',4,0),(31,2,'When you show up every night','bridge',5,0),(32,2,'And tell me that you want me but it\'s complicated, so complicated','bridge',6,0),(33,2,'When it hurts, but it hurts so good','chorus',7,0),(34,2,'Do you take it? Do you break it off?','chorus',8,0),(35,2,'When it hurts, but it hurts so good','chorus',9,0),(36,2,'Can you say it? Can you say it?','chorus',10,0),(37,2,'Your love is like, na, na, na, na, na, na, na','hook',11,0),(38,2,'Your love is like, na, na, na, na, na, na, na','hook',12,0),(39,2,'It hurts so good, na, na, na, na, na, na, na','hook',13,0),(40,2,'Your love is like, na, na, na, na, na, na, na','hook',14,0),(41,2,'It hurts so good','hook',15,0),(42,2,'Every time that I swear it\'s over','verse',16,0),(43,2,'It makes you want me even more','verse',17,0),(44,2,'You pull away and I come in closer','verse',18,0),(45,2,'And all we ever stay is torn','verse',19,0),(46,2,'Baby, I don\'t know why I try to deny it','bridge',20,0),(47,2,'When you show up every night','bridge',21,0),(48,2,'I tell you that I want you but it\'s complicated, so complicated','bridge',22,0),(49,2,'When it hurts, but it hurts so good','chorus',23,0),(50,2,'Do you take it? Do you break it off?','chorus',24,0),(51,2,'When it hurts, but it hurts so good','chorus',25,0),(52,2,'Can you say it? Can you say it?','chorus',26,0),(53,2,'Your love is like, na, na, na, na, na, na, na','hook',27,0),(54,2,'Your love is like, na, na, na, na, na, na, na','hook',28,0),(55,2,'It hurts so good, na, na, na, na, na, na, na','hook',29,0),(56,2,'Your love is like, na, na, na, na, na, na, na','hook',30,0),(57,2,'It hurts so good','hook',31,0),(58,2,'Wide awake through the daylight','bridge',32,0),(59,2,'Will you hold me like we\'re running a yellow light?','bridge',33,0),(60,2,'Reach for you with my hands tied','bridge',34,0),(61,2,'Are we dancing like we\'re burning in paradise?','bridge',35,0),(62,2,'When it hurts, but it hurts so good','chorus',36,0),(63,2,'Do you take it? Do you break it off?','chorus',37,0),(64,2,'When it hurts, but it hurts so good','chorus',38,0),(65,2,'Can you say it? Can you say it?','chorus',39,0),(66,2,'Your love is like, na, na, na, na, na, na, na','hook',40,0),(67,2,'Your love is like, na, na, na, na, na, na, na','hook',41,0),(68,2,'It hurts so good, na, na, na, na, na, na, na','hook',42,0),(69,2,'Your love is like, na, na, na, na, na, na, na','hook',43,0),(70,2,'It hurts so good','hook',44,0),(71,3,'I hate you, I hate you','verse',0,0),(72,3,'But when all is said and done','verse',1,0),(73,3,'I need you, I need you','verse',2,0),(74,3,'Just tell me where to run','verse',3,0),(75,3,'A life without you may as well hand me the gun','verse',4,0),(76,3,'Oh, no no no','verse',5,0),(77,3,'And I tried to, I tried to','verse',6,0),(78,3,'Date them other dudes','verse',7,0),(79,3,'They\'re perfect, they\'re perfect','verse',8,0),(80,3,'\'Til I look at you','verse',9,0),(81,3,'They give me everything and still it ain\'t enough','verse',10,0),(82,3,'No matter who I choose','chorus',11,0),(83,3,'They don\'t compare to you','chorus',12,0),(84,3,'They treat me good and that\'s cool','chorus',13,0),(85,3,'But I, I\'ll take them back, no substitute for you','chorus',14,0),(86,3,'No matter who I choose','chorus',15,0),(87,3,'They don\'t compare to you','chorus',16,0),(88,3,'They treat me good and that\'s cool','chorus',17,0),(89,3,'But I\'ll take them back, no substitute for you','chorus',18,0),(90,3,'It\'s deeper, it\'s deeper','verse',19,0),(91,3,'That anyone can see','verse',20,0),(92,3,'I need to believe you','verse',21,0),(93,3,'You\'re my philoshophy','verse',22,0),(94,3,'You give me more than what a thousand lives could teach','verse',23,0),(95,3,'Oh, baby!','verse',24,0),(96,3,'Love and more','verse',25,0),(97,3,'Is like on either side','verse',26,0),(98,3,'The ones, the fight, they go most the life','verse',27,0),(99,3,'You hold me down and yet I feel like I can fly','verse',28,0),(100,3,'No matter who I choose','chorus',29,0),(101,3,'They don\'t compare to you','chorus',30,0),(102,3,'They treat me good and that\'s cool','chorus',31,0),(103,3,'But I, I\'ll take them back, no substitute for you','chorus',32,0),(104,3,'No matter who I choose','chorus',33,0),(105,3,'They don\'t compare to you','chorus',34,0),(106,3,'They treat me good and that\'s cool','chorus',35,0),(107,3,'But I, I\'ll take them back, no substitute for you','chorus',36,0),(108,3,'Oh, na na na na eh eh','bridge',37,0),(109,3,'Ain\'t no substitute for you','bridge',38,0),(110,3,'Oh, na na na na eh eh','bridge',39,0),(111,3,'Ain\'t no substitute for you','bridge',40,0),(112,3,'Oh na na na-na-na-na eh','bridge',41,0),(113,3,'Na na na eh','bridge',42,0),(114,3,'I\'ll take them back, no subsitute for you','bridge',43,0),(115,3,'No matter who I choose','chorus',44,0),(116,3,'They don\'t compare to you','chorus',45,0),(117,3,'They treat me good and that\'s cool','chorus',46,0),(118,3,'I\'ll take them back, no substitute for you','chorus',47,0),(119,3,'No matter who I choose','chorus',48,0),(120,3,'They don\'t compare to you','chorus',49,0),(121,3,'They treat me good and that\'s cool','chorus',50,0),(122,3,'But I, I\'ll take them back, no subsitute for you','chorus',51,0),(123,4,'I\'m tired of being what you what me to be','verse',0,0),(124,4,'Feeling so faithless, lost under the surface','verse',1,0),(125,4,'Don\'t know what you\'re expecting of me','verse',2,0),(126,4,'Put under the pressure of walking in your shoes','verse',3,0),(127,4,'(Caught in the undertow, just caught in the undertow)','verse',4,0),(128,4,'Every step that I take is another mistake to you','verse',5,0),(129,4,'I\'ve become so numb, I can\'t feel you there','chorus',6,0),(130,4,'Become so tired, so much more aware','chorus',7,0),(131,4,'I\'m becoming this, all I want to do','chorus',8,0),(132,4,'Is be more like me and be less like you','chorus',9,0),(133,4,'Can\'t you see that you\'re smothering me,','verse',10,0),(134,4,'Holding too tightly, afraid to lose control?','verse',11,0),(135,4,'\'Cause everything that you thought I would be','verse',12,0),(136,4,'Has fallen apart right in front of you.','verse',13,0),(137,4,'(Caught in the undertow, just caught in the undertow)','verse',14,0),(138,4,'Every step that I take is another mistake to you.','verse',15,0),(139,4,'(Caught in the undertow, just caught in the undertow)','verse',16,0),(140,4,'And every second I waste is more than I can take.','verse',17,0),(141,4,'I\'ve become so numb, I can\'t feel you there,','chorus',18,0),(142,4,'Become so tired, so much more aware','chorus',19,0),(143,4,'I\'m becoming this, all I want to do','chorus',20,0),(144,4,'Is be more like me and be less like you.','chorus',21,0),(145,4,'And I know','bridge',22,0),(146,4,'I may end up failing too.','bridge',23,0),(147,4,'But I know','bridge',24,0),(148,4,'You were just like me with someone disappointed in you.','bridge',25,0),(149,4,'I\'ve become so numb, I can\'t feel you there,','chorus',26,0),(150,4,'Become so tired, so much more aware','chorus',27,0),(151,4,'I\'m becoming this, all I want to do','chorus',28,0),(152,4,'Is be more like me and be less like you.','chorus',29,0),(153,4,'I\'ve become so numb, I can\'t feel you there.','outro',30,0),(154,4,'(I\'m tired of being what you want me to be)','outro',31,0),(155,4,'I\'ve become so numb, I can\'t feel you there.','outro',32,0),(156,4,'(I\'m tired of being what you want me to be)','outro',33,0),(157,5,'I got it in the worst way','intro',0,0),(158,5,'Got me trapped in your love cage','intro',1,0),(159,5,'I got it ithe worst way','intro',2,0),(160,5,'Got me trapped in your love cage','intro',3,0),(161,5,'I got it in the worst way','verse',4,0),(162,5,'You and me, we always skipped the foreplay','verse',5,0),(163,5,'Well listen, listen','verse',6,0),(164,5,'This love ain\'t written, written in stone, no','verse',7,0),(165,5,'I can\'t be doing this to myself anymore, no','verse',8,0),(166,5,'I know the way you operate','chorus',9,0),(167,5,'It\'s much easier than falling again','chorus',10,0),(168,5,'I know you won\'t cooperate','chorus',11,0),(169,5,'But I know far too much to buckle now','chorus',12,0),(170,5,'But I know far too much to buckle now','chorus',13,0),(171,5,'But I know far too much to buckle now','chorus',14,0),(172,5,'I can\'t stop it, I\'m running out of options','verse',15,0),(173,5,'Feels like my confidence is far behind','verse',16,0),(174,5,'You do it how you want it when nobody is watching','verse',17,0),(175,5,'I can see all the signs','verse',18,0),(176,5,'I\'d rather go blind','verse',19,0),(177,5,'I\'d rather go blind, yeah','verse',20,0),(178,5,'Yeah, I would','verse',21,0),(179,5,'I know the way you operate','chorus',22,0),(180,5,'It\'s much easier than falling again','chorus',23,0),(181,5,'I know you won\'t cooperate','chorus',24,0),(182,5,'But I know far too much to buckle now','chorus',25,0),(183,5,'But I know far too much to buckle now','chorus',26,0),(184,5,'But I know far too much to buckle now','chorus',27,0),(185,5,'But I know far too much to buckle now','chorus',28,0),(186,5,'But I know far too much to buckle now','chorus',29,0),(187,5,'I know the way you operate','outro',30,0),(188,5,'I know the way you operate','outro',31,0),(189,6,'We were so bright','verse',0,0),(190,6,'Standing in the sunlight','verse',1,0),(191,6,'Never got burnt, we were alright','verse',2,0),(192,6,'Had that easy kind of love and I let it in','verse',3,0),(193,6,'For a moment','verse',4,0),(194,6,'Felt like we were floating','verse',5,0),(195,6,'Baby, I swear, we were golden','verse',6,0),(196,6,'It was easy, natural as breathing air','verse',7,0),(197,6,'And when the sky went dark','bridge',8,0),(198,6,'You think I would have known','bridge',9,0),(199,6,'Before we let it get this far','bridge',10,0),(200,6,'I should have let you go','bridge',11,0),(201,6,'Now I gotta crawl outta love','chorus',12,0),(202,6,'Now I gotta crawl outta love','chorus',13,0),(203,6,'It\'s so f*cking easy to fall in','chorus',14,0),(204,6,'But now that this ain\'t what you promised','chorus',15,0),(205,6,'And tell me how to crawl outta love','chorus',16,0),(206,6,'Tell me how to–','bridge',17,0),(207,6,'Now I gotta crawl outta, crawl outta love','bridge',18,0),(208,6,'Tell me how to–','bridge',19,0),(209,6,'Now I gotta crawl outta, crawl outta love','bridge',20,0),(210,6,'Got your words right','verse',21,0),(211,6,'Always knew what to say in our fights','verse',22,0),(212,6,'Look at us now, hard as I try','verse',23,0),(213,6,'I get halfway up the wall and you pull me down','verse',24,0),(214,6,'And when the sky went dark','bridge',25,0),(215,6,'You think I would have known','bridge',26,0),(216,6,'Before we let it get this far','bridge',27,0),(217,6,'I should have let you go','bridge',28,0),(218,6,'Now I gotta crawl outta love','chorus',29,0),(219,6,'Now I gotta crawl outta love','chorus',30,0),(220,6,'It\'s so f*cking easy to fall in','chorus',31,0),(221,6,'But now that this ain\'t what you promised','chorus',32,0),(222,6,'Tell me how to crawl outta love','chorus',33,0),(223,6,'Now I gotta crawl outta, crawl outta love','bridge',34,0),(224,6,'Tell me how to–','bridge',35,0),(225,6,'Now I gotta crawl outta, crawl outta love','bridge',36,0),(226,6,'Crawl outta, crawl outta','bridge',37,0),(227,6,'Crawl outta, crawl outta love','bridge',38,0),(228,6,'Crawl outta, crawl outta','bridge',39,0),(229,6,'And when the sky went dark','bridge',40,0),(230,6,'You think I would have known','bridge',41,0),(231,6,'Before we let it get this far','bridge',42,0),(232,6,'I should have let you go','bridge',43,0),(233,6,'Now I gotta crawl outta love','chorus',44,0),(234,6,'Now I gotta crawl outta love','chorus',45,0),(235,6,'It\'s so f*cking easy to fall in','chorus',46,0),(236,6,'But now that this ain\'t what you promised','chorus',47,0),(342,7,'This world will never be\r','verse',0,0),(343,7,'What I expected\r','verse',1,0),(344,7,'And if I don\'t belong\r','verse',2,0),(345,7,'Who would have guessed it\r','verse',3,0),(346,7,'I will not leave alone\r','verse',4,0),(347,7,'Everything that I own\r','verse',5,0),(348,7,'To make you feel like it\'s not too late\r','verse',6,0),(349,7,'It\'s never too late\r','verse',7,0),(350,7,'Even if I say\r','chorus',8,0),(351,7,'It\'ll be alright\r','chorus',9,0),(352,7,'Still I hear you say\r','chorus',10,0),(353,7,'You want to end your life\r','chorus',11,0),(354,7,'Now and again we try\r','chorus',12,0),(355,7,'To just stay alive\r','chorus',13,0),(356,7,'Maybe we\'ll turn it all around\r','chorus',14,0),(357,7,'\'Cause it\'s not too late\r','chorus',15,0),(358,7,'It\'s never too late\r','chorus',16,0),(359,7,'No one will ever see\r','verse',17,0),(360,7,'This side reflected\r','verse',18,0),(361,7,'And if there\'s something wrong\r','verse',19,0),(362,7,'Who would have guessed it\r','verse',20,0),(363,7,'And I have left alone\r','verse',21,0),(364,7,'Everything that I own\r','verse',22,0),(365,7,'To make you feel like\r','verse',23,0),(366,7,'It\'s not too late\r','verse',24,0),(367,7,'It\'s never too late\r','verse',25,0),(368,7,'Even if I say\r','chorus',26,0),(369,7,'It\'ll be alright\r','chorus',27,0),(370,7,'Still I hear you say\r','chorus',28,0),(371,7,'You want to end your life\r','chorus',29,0),(372,7,'Now and again we try\r','chorus',30,0),(373,7,'To just stay alive\r','chorus',31,0),(374,7,'Maybe we\'ll turn it all around\r','chorus',32,0),(375,7,'\'Cause it\'s not too late\r','chorus',33,0),(376,7,'It\'s never too late\r','chorus',34,0),(377,7,'The world we knew\r','bridge',35,0),(378,7,'Won\'t come back\r','bridge',36,0),(379,7,'The time we\'ve lost\r','bridge',37,0),(380,7,'Can\'t get back\r','bridge',38,0),(381,7,'The life we had\r','bridge',39,0),(382,7,'Won\'t be ours again\r','bridge',40,0),(383,7,'This world will never be\r','bridge',41,0),(384,7,'What I expected\r','bridge',42,0),(385,7,'And if I don\'t belong\r','bridge',43,0),(386,7,'Even if I say\r','chorus',44,0),(387,7,'It\'ll be alright\r','chorus',45,0),(388,7,'Still I hear you say\r','chorus',46,0),(389,7,'You want to end your life\r','chorus',47,0),(390,7,'Now and again we try\r','chorus',48,0),(391,7,'To just stay alive\r','chorus',49,0),(392,7,'Maybe we\'ll turn it all around\r','chorus',50,0),(393,7,'\'Cause it\'s not too late\r','chorus',51,0),(394,7,'It\'s never too late\r','chorus',52,0),(395,7,'Maybe we\'ll turn it all around\r','chorus',53,0),(396,7,'\'Cause it\'s not too late\r','chorus',54,0),(397,7,'It\'s never too late (It\'s never too late)\r','chorus',55,0),(398,7,'It\'s not too late\r','chorus',56,0),(399,7,'It\'s never too late\r','chorus',57,0),(400,8,'I forget to\r','intro',0,0),(401,8,'It\'s been a week since you hit my bed\r','verse',1,0),(402,8,'And since then, you\'ve started living in my head, eh\r','verse',3,0),(403,8,'Seven days and I\'m nearly dead\r','verse',3,0),(404,8,'Never thought that I\'d reboot\r','verse',4,0),(405,8,'But you\'re a different kind of new\r','verse',5,0),(406,8,'It\'s like I feel everything more (I forget to)\r','bridge',6,0),(407,8,'All my body knows it when you reach my floor, eh\r','bridge',7,0),(408,8,'My heart is beating down the door\r','bridge',8,0),(409,8,'And now I\'m stocking up on oxygen\r','bridge',9,0),(410,8,'\'Cause when I see you\r','chorus',10,0),(411,8,'I forget to bre-e-eathe\r','chorus',11,0),(412,8,'I forget to bre-e-eathe\r','chorus',12,0),(413,8,'I forget to bre-e-eathe\r','chorus',13,0),(414,8,'I forget to, when I\'m with you\r','chorus',14,0),(415,8,'(I forget to)\r','chorus',15,0),(416,8,'If I could just inhale some chill\r','verse',16,0),(417,8,'It\'s like I\'m living out in space\r','verse',17,0),(418,8,'How am I still here?\r','verse',18,0),(419,8,'Did you slip me a magic pill?\r','verse',19,0),(420,8,'Got me lifted like an astronaut\r','verse',20,0),(421,8,'No helmet on and my lungs just stop\r','verse',21,0),(422,8,'So please, when you look at me like that\r','bridge',22,0),(423,8,'There\'s no way to fight back, no\r','bridge',23,0),(424,8,'Please, can\'t you see I\'m stocking up on oxygen\r','bridge',24,0),(425,8,'\'Cause when I see you\r','chorus',25,0),(426,8,'I forget to bre-e-eathe\r','chorus',26,0),(427,8,'I forget to bre-e-eathe\r','chorus',27,0),(428,8,'I forget to bre-e-eathe\r','chorus',28,0),(429,8,'I forget to, when I\'m with you\r','chorus',29,0),(430,8,'When I see you\r','chorus',30,0),(431,8,'I forget to bre-e-eathe (I forget to)\r','chorus',31,0),(432,8,'I forget to bre-e-eathe (I forget to)\r','chorus',32,0),(433,8,'I forget to bre-e-eathe (I forget to)\r','chorus',33,0),(434,8,'I forget to, when I\'m with you\r','chorus',34,0),(435,8,'I don\'t know how to live without the breath you finish\r','bridge',35,0),(436,8,'I don\'t know how to live without the breath you finish\r','bridge',36,0),(437,8,'I don\'t know how to live without the breath you finish\r','bridge',37,0),(438,8,'Give me it, give me it\r','bridge',38,0),(439,8,'I forget to bre-e-eathe\r','chorus',39,0),(440,8,'I forget to bre-e-eathe\r','chorus',40,0),(441,8,'I forget to bre-e-eathe\r','chorus',41,0),(442,8,'I forget to, when I\'m with you\r','chorus',42,0),(443,8,'When I see you\r','chorus',43,0),(444,8,'I forget to bre-e-eathe (I forget to)\r','chorus',44,0),(445,8,'I forget to bre-e-eathe (I forget to)\r','chorus',45,0),(446,8,'I forget to bre-e-eathe (I forget to)\r','chorus',46,0),(447,8,'I forget to, when I\'m with you\r','chorus',47,0),(448,8,'(I forget to)\r','chorus',48,0),(449,9,'Every street in this city','verse',0,0),(450,9,'Is the same to me','verse',1,0),(451,9,'Everyone\'s got a place to be','verse',2,0),(452,9,'But there\'s no room for me','verse',3,0),(453,9,'Am I to blame?','verse',4,0),(454,9,'When the guilt and the shame','verse',5,0),(455,9,'Hang over me','verse',6,0),(456,9,'Like a dark cloud that','verse',7,0),(457,9,'Chases you down','verse',8,0),(458,9,'In the pouring rain','verse',9,0),(459,9,'It\'s so hard','bridge',10,0),(460,9,'To find someone','bridge',11,0),(461,9,'Who cares about you','bridge',12,0),(462,9,'But it\'s easy enough to find','bridge',13,0),(463,9,'Someone who looks down on you','bridge',14,0),(464,9,'Why is it so hard','chorus',15,0),(465,9,'To find someone','chorus',16,0),(466,9,'Who cares about you','chorus',17,0),(467,9,'When it\'s easy enough to find','chorus',18,0),(468,9,'Someone who looks down on you?','chorus',19,0),(469,9,'It\'s not what it seems','verse',20,0),(470,9,'When you\'re not on the scene','verse',21,0),(471,9,'There\'s a chill in the air','verse',22,0),(472,9,'But there\'s people like me','verse',23,0),(473,9,'That nobody sees','verse',24,0),(474,9,'So nobody cares','verse',25,0),(475,9,'Why is it so hard','chorus',26,0),(476,9,'To find someone','chorus',27,0),(477,9,'Who cares about you','chorus',28,0),(478,9,'When it\'s easy enough to find','chorus',29,0),(479,9,'Someone who looks down on you?','chorus',30,0),(480,9,'Why is it so hard to find','chorus',31,0),(481,9,'Someone who can keep it','chorus',32,0),(482,9,'Together when you\'ve come undone?','chorus',33,0),(483,9,'Why is it so hard','chorus',34,0),(484,9,'To find someone','chorus',35,0),(485,9,'Who cares about you?','chorus',36,0),(486,9,'I swear this time','bridge',37,0),(487,9,'It won\'t turn out the same','bridge',38,0),(488,9,'Cause now I\'ve','bridge',39,0),(489,9,'Got myself to blame','bridge',40,0),(490,9,'And you\'ll know when we','bridge',41,0),(491,9,'End up on the streets','bridge',42,0),(492,9,'That it\'s easy enough to find','bridge',43,0),(493,9,'Someone who looks down on you','bridge',44,0),(494,9,'Why is it so hard','chorus',45,0),(495,9,'To find someone','chorus',46,0),(496,9,'Who cares about you','chorus',47,0),(497,9,'When it\'s easy enough to find','chorus',48,0),(498,9,'Someone who looks down on you?','chorus',49,0),(499,9,'Why is it so hard to find','chorus',50,0),(500,9,'Someone who can keep it','chorus',51,0),(501,9,'Together when you\'ve come undone','chorus',52,0),(502,9,'Why is it so hard','chorus',53,0),(503,9,'To find someone','chorus',54,0),(504,9,'Who cares about you?','chorus',55,0),(505,10,'I said I hate you, I was angry','verse',0,0),(506,10,'I got so mad, I slammed your door','verse',1,0),(507,10,'You know I really love you, baby','verse',2,0),(508,10,'I didn\'t mean to start a war','verse',3,0),(509,10,'And I know there\'s a line, but I crossed it','verse',4,0),(510,10,'And I pray that it won\'t leave a scar','verse',5,0),(511,10,'I said I hate you, but I\'m sorry','verse',6,0),(512,10,'Sometimes I wish you\'d cut me off','verse',7,0),(513,10,'Maybe I should think before I talk','chorus',8,0),(514,10,'I get emotional and words come out all wrong','chorus',9,0),(515,10,'Sometimes I\'m more honest than I want','chorus',10,0),(516,10,'So maybe I should think before','chorus',11,0),(517,10,'Maybe next time, I\'ll think before I talk','chorus',12,0),(518,10,'I try my best to make it better (better)','verse',13,0),(519,10,'I\'m all out of apologies (apologies)','verse',14,0),(520,10,'You know I\'m not good under pressure (pressure)','verse',15,0),(521,10,'Guess hurting you is hurting me','verse',16,0),(522,10,'I took all that we built and I broke it','verse',17,0),(523,10,'And I pray it won\'t tear us apart','verse',18,0),(524,10,'So let me piece it back together','verse',19,0),(525,10,'I know I cut you pretty deep (know I cut you pretty deep)','verse',20,0),(526,10,'Maybe I should think before I talk','chorus',21,0),(527,10,'I get emotional and words come out all wrong','chorus',22,0),(528,10,'Sometimes I\'m more honest than I want','chorus',23,0),(529,10,'So maybe I should think before','chorus',24,0),(530,10,'Maybe next time, I\'ll think before','chorus',25,0),(531,10,'I know that I should think before I speak','chorus',26,0),(532,10,'\'Cause I\'m saying things that I don\'t even mean','chorus',27,0),(533,10,'Maybe I\'m more honest than I wanna be','chorus',28,0),(534,10,'So maybe I should think before','chorus',29,0),(535,10,'Maybe next time, I\'ll think before I','chorus',30,0),(536,10,'Say something I might regret','bridge',31,0),(537,10,'And I might get too far under your skin','bridge',32,0),(538,10,'I can\'t lie, I wish we could try it again','bridge',33,0),(539,10,'Oh I, I wish we could try it again','bridge',34,0),(540,10,'Maybe I should think before I talk','chorus',35,0),(541,10,'I get emotional and words come out all wrong','chorus',36,0),(542,10,'Sometimes I\'m more honest than I want','chorus',37,0),(543,10,'So maybe I should think before','chorus',38,0),(544,10,'Maybe next time, I\'ll think before','chorus',39,0),(545,10,'I know that I should think before I speak','chorus',40,0),(546,10,'\'Cause I\'m saying things that I don\'t even mean','chorus',41,0),(547,10,'Maybe I\'m more honest than I wanna be','chorus',42,0),(548,10,'So maybe I should think before','chorus',43,0),(549,10,'Maybe next time, I\'ll think before I talk','chorus',44,0),(550,10,'Oh, oh, oh, oh','outro',45,0),(551,10,'So maybe I should think before','outro',46,0),(552,10,'Maybe next time, I\'ll think before I talk﻿','outro',47,0),(553,11,'On my way to your house, sneakers in the snow','verse',0,0),(554,11,'I only wanna see her','verse',1,0),(555,11,'And my thoughts are so loud, \'cause I just wanna know','verse',2,0),(556,11,'Are you gonna keep her?','verse',3,0),(557,11,'Only call you faded','bridge',4,0),(558,11,'Sorry, I know you hate it','bridge',5,0),(559,11,'Probably shouldn\'t say this','bridge',6,0),(560,11,'But it hurts to bite my tongue','bridge',7,0),(561,11,'Does she know that you held me in the dark?','chorus',8,0),(562,11,'Does she know that I had you from the start?','chorus',9,0),(563,11,'Does she know that the bruises never change?','chorus',10,0),(564,11,'My marks are on your heart, I had you from the start','chorus',11,0),(565,11,'Remember?','chorus',12,0),(571,11,'Holding water in my hands, but it never lasts','verse',13,0),(572,11,'Slipping through my fingers','verse',14,0),(573,11,'Think I held on too tight, cutting off the blood','verse',15,0),(574,11,'You don\'t feel a thing \'till it hurts','verse',16,0),(575,11,'Only call you faded','bridge',17,0),(576,11,'Sorry, I know you hate it, oh','bridge',18,0),(577,11,'Probably shouldn\'t say this','bridge',19,0),(578,11,'But I\'m done biting my tongue','bridge',20,0),(579,11,'Now, does she know that you held me in the dark?','chorus',21,0),(580,11,'Does she know that I had you from the start?','chorus',22,0),(581,11,'Does she know that the bruises never change?','chorus',23,0),(582,11,'My marks are on your heart, I had you from the start','chorus',24,0),(584,11,'Ah, ah, ah, ah','bridge',25,0),(585,11,'Ah, ah, ahhh','bridge',26,0),(586,11,'Does she know?','bridge',27,0),(587,11,'Ah, ah, ah, ah','bridge',28,0),(588,11,'Ah, ah, ahhh','bridge',29,0),(589,11,'Does she know?','outro',30,0),(590,11,'Does she know? (Does she know?)','outro',31,0),(591,11,'Does she know that you held me in the dark?','outro',32,0),(592,11,'Does she know that I had you from the start?','outro',33,0),(593,11,'Does she know that the bruises never change?','outro',34,0),(594,11,'My marks are on your heart, I had you from the start','outro',35,0),(643,12,'Look at you kids with your vintage music','verse',0,0),(644,12,'Comin\' through satellites while cruisin\'','verse',1,0),(645,12,'You\'re part of the past, but now you\'re the future','verse',2,0),(646,12,'Signals crossing can get confusin\'','verse',3,0),(647,12,'It\'s enough just to make you feel crazy, crazy, crazy','bridge',4,0),(648,12,'Sometimes, it\'s enough just to make you feel crazy','bridge',5,0),(649,12,'You get ready, you get all dressed up','chorus',6,0),(650,12,'To go nowhere in particular','chorus',7,0),(651,12,'Back to work or the coffee shop','chorus',8,0),(652,12,'Doesn\'t matter \'cause it\'s enough','chorus',9,0),(653,12,'To be young and in love (ah, ah)','chorus',10,0),(654,12,'To be young and in love (ah, ah)','chorus',11,0),(655,12,'Look at you kids, you know you\'re the coolest','verse',12,0),(656,12,'The world is yours and you can\'t refuse it','verse',13,0),(657,12,'Seen so much, you could get the blues, but','verse',14,0),(658,12,'That don\'t mean that you should abuse it','verse',15,0),(659,12,'Though it\'s enough just to make you go crazy, crazy, crazy','bridge',16,0),(660,12,'I know, it\'s enough just to make you go crazy, crazy, crazy','bridge',17,0),(661,12,'But you get ready, you get all dressed up','chorus',18,0),(662,12,'To go nowhere in particular','chorus',19,0),(663,12,'Back to work or go the coffee shop','chorus',20,0),(664,12,'It don\'t matter because it\'s enough','chorus',21,0),(665,12,'To be young and in love (ah, ah)','chorus',22,0),(666,12,'To be young and in love (ah, ah)','chorus',23,0),(667,12,'Hmm (ah, ah)','bridge',24,0),(668,12,'Hmm (ah, ah, ah, ah)','bridge',25,0),(669,12,'Hmm','bridge',26,0),(670,12,'Don\'t worry, baby','bridge',27,0),(671,12,'Hmm (ah, ah)','bridge',28,0),(672,12,'Hmm (ah, ah, ah, ah)','bridge',29,0),(673,12,'Hmm','bridge',30,0),(674,12,'Don\'t worry, baby','bridge',31,0),(675,12,'It\'s enough just to make me go crazy, crazy, crazy','bridge',32,0),(676,12,'It\'s enough just to make me go crazy','bridge',33,0),(677,12,'I get ready, I get all dressed up','chorus',34,0),(678,12,'To go nowhere in particular','chorus',35,0),(679,12,'It doesn\'t matter if I\'m not enough','chorus',36,0),(680,12,'For the future or the things to come','chorus',37,0),(681,12,'\'Cause I\'m young and in love (ah, ah)','chorus',38,0),(682,12,'I\'m young and in love (ah, ah, ah, ah)','chorus',39,0),(683,12,'Hmm (ah, ah)','outro',40,0),(684,12,'Hmm (ah, ah, ah, ah)','outro',41,0),(685,12,'Hmm','outro',42,0),(686,12,'Don\'t worry, baby','outro',43,0),(687,12,'Hmm (ah, ah)','outro',44,0),(688,12,'Hmm (ah, ah, ah, ah)','outro',45,0),(689,12,'Hmm','outro',46,0),(690,12,'Don\'t worry, baby','outro',47,0),(693,13,'(Lizer)','verse',0,0),(694,13,'И я снова подключаюсь','verse',1,0),(695,13,'Мои сети — мои знания','verse',2,0),(696,13,'В интернете много знаний, и я каждый день искал их,','verse',3,0),(697,13,'И теперь я загружаю свой ПК, ныряя в сеть','verse',4,0),(698,13,'Твои люди меня знают,','verse',5,0),(699,13,'И я открываю файлы','verse',6,0),(700,13,'Я давно готовил это, обещаю, будет сладко','verse',7,0),(701,13,'Мои мысли уже где-то, где проходят провода','verse',8,0),(702,13,'Я включаю монитор и загружается экран,','verse',9,0),(703,13,'На рабочем столе TOR, а я давно так не играл','verse',10,0),(704,13,'Я сдуваю пыль с Macbook\'а, на полу есть подзаряд','verse',11,0),(705,13,'Где моя клавиатура, ведь мне так нужны слова?','verse',12,0),(706,13,'Я расслаблен, мысли в сеть','verse',13,0),(707,13,'Самый быстрый интернет','verse',14,0),(708,13,'Меня проносит сквозь планету','verse',15,0),(709,13,'Я всегда живу в интернете','verse',16,0),(710,13,'Погружаюсь в эту сеть, меня уносят провода','verse',17,0),(711,13,'Здесь так много сообщений — я запутался в словах','verse',18,0),(712,13,'Федералы со всех щелей снова бьют по номерам','verse',19,0),(713,13,'Если есть вопросы, напиши мне в Telegram','verse',20,0),(714,13,'Я ищу сеть-сеть-сеть, и не получив ответа,','verse',21,0),(715,13,'Забиваю это в Google — мне поможет интернет','verse',22,0),(716,13,'Нет-нет, мне не нужно ваше гетто','verse',23,0),(717,13,'Я теряюсь среди файлов, моя розовая сеть','verse',24,0),(718,13,'Моя жизнь была пробелом, но я нажимаю Enter','verse',25,0),(719,13,'Моя жизнь была проблемой, но теперь я в интернете','verse',26,0),(720,13,'И тут много обновлений, и что скажут эти дети?','verse',27,0),(721,13,'Ведь их жизнь — это проблема, но они нажали Enter','verse',28,0),(722,13,'Я пропитан этой плазмой, мои синие глаза','verse',29,0),(723,13,'И мой монитор покажет, как я снова вырывал','verse',30,0),(724,13,'Из жизни любовь, теперь я в сети','verse',31,0),(725,13,'Где мой дисковод? Я так искал диск','verse',32,0),(726,13,'(Lizer)','bridge',33,0),(727,13,'Я умираю в интернете, посмотри на смерть онлайн','bridge',34,0),(728,13,'Я влюбился в интернете, интернет так развивал','bridge',35,0),(729,13,'Мысли в сеть, мой флоу — синтетик','bridge',36,0),(730,13,'Умер здесь, жил в интернете','bridge',37,0),(731,13,'Навсегда оставлю след','bridge',38,0),(732,13,'Будешь помнить обо мне','bridge',39,0),(733,13,'(Rickey F)','chorus',40,0),(734,13,'Веб, веб, веб, веб, веб, веб','chorus',41,0),(735,13,'Мой дом только где-где, есть-есть, сеть-сеть','chorus',42,0),(736,13,'Утопаю в L-L, T-T, E-E','chorus',43,0),(737,13,'Пойманный сетью, что тянет ко дну','chorus',44,0),(738,13,'Я настолько веб, веб, веб, веб, веб, веб','chorus',45,0),(739,13,'Мой дом только где-где, есть-есть, сеть-сеть','chorus',46,0),(740,13,'Утопаю в L-L, T-T, E-E','chorus',47,0),(741,13,'Кнопка \"Refresh\" как спасательный круг','chorus',48,0),(742,13,'(Rickey F)','verse',49,0),(743,13,'Я больше не чувствую голод, CTRL+Enter — закончился пост','verse',50,0),(744,13,'Кто защитит и заглушит мой страх — извечный секретный вопрос','verse',51,0),(745,13,'Запираем эту дверь на все засовые замки,','verse',52,0),(746,13,'Но реальность расщепляет их движением руки','verse',53,0),(747,13,'Данный логин уже занят, ублюдок','verse',54,0),(748,13,'Этот пароль слишком прост!','verse',55,0),(749,13,'Активировать вновь оборонный протокол','verse',56,0),(750,13,'Моё имя — Rickey F, где я будто Firewall','verse',57,0),(751,13,'Заклинание защиты как команда на консоль','verse',58,0),(752,13,'Моя магическая книга с яблоком на лицевой','verse',59,0),(753,13,'Я чувстую мир через свой интерфейс','verse',60,0),(754,13,'Цифровой следопыт не найдёт меня здесь','verse',61,0),(755,13,'Очищаю историю, обнуляю кэш','verse',62,0),(756,13,'Цифровой пастор, простите, я грешен!','verse',63,0),(757,13,'(Lizer)','bridge',64,0),(758,13,'Я умираю в интернете, посмотри на смерть онлайн','bridge',65,0),(759,13,'Я влюбился в интернете, интернет так развивал','bridge',66,0),(760,13,'Мысли в сеть, мой флоу — синтетик','bridge',67,0),(761,13,'Умер здесь, жил в интернете','bridge',68,0),(762,13,'Навсегда оставлю след','bridge',69,0),(763,13,'Будешь помнить обо мне','bridge',70,0),(764,13,'(Rickey F)','chorus',71,0),(765,13,'Веб, веб, веб, веб, веб, веб','chorus',72,0),(766,13,'Мой дом только где-где, есть-есть, сеть-сеть','chorus',73,0),(767,13,'Утопаю в L-L, T-T, E-E','chorus',74,0),(768,13,'Пойманный сетью, что тянет ко дну','chorus',75,0),(769,13,'Я настолько веб, веб, веб, веб, веб, веб','chorus',76,0),(770,13,'Мой дом только где-где, есть-есть, сеть-сеть','chorus',77,0),(771,13,'Утопаю в L-L, T-T, E-E','chorus',78,0),(772,13,'Кнопка \"Refresh\" как спасательный круг','chorus',79,0),(773,13,'(Диктор)','outro',80,0),(774,13,'Следующий уровень витальных, то есть жизненно важных, обеспечивающих выживание, потребностей —','outro',81,0),(775,13,'Это потребности безопасности и защиты, потребности в стабильности, организации, порядке, предсказуемости событий, в свободе от угрозы.','outro',82,0),(776,13,'Можно сказать, что если физиологические потребности связаны с выживанием организма в каждый конкретный момент,','outro',83,0),(777,6,'Tell me how to crawl outta love','chorus',48,0),(778,6,'Now I gotta–','outro',49,0),(779,6,'Tell me how to crawl outta, crawl outta love','outro',50,0),(780,6,'Tell me how to–','outro',51,0),(781,6,'Now I gotta crawl outta, crawl outta love','outro',52,0),(782,13,'То потребности безопасности обеспечивают долговременное выживание индивида.','outro',84,0),(783,13,'То потребности безопасности обеспечивают долговременное выживание индивида.','outro',85,1),(784,21,'I couldn\'t tell him, I couldn\'t breathe','verse',1,0),(785,21,'I never knew that I was ripping at the seams','verse',2,0),(786,21,'I couldn\'t hold back, I didn\'t try','verse',3,0),(787,21,'One look and you\'re right between my lines','verse',4,0),(788,21,'It\'s like you\'re diving in to my heart','bridge',5,0),(789,21,'I\'m floating off the ground','bridge',6,0),(790,21,'When you pull me in to your arms','bridge',7,0),(791,21,'I go into a free fall','chorus',8,0),(792,21,'I\'m spinning and I can\'t stop','chorus',9,0),(793,21,'I lose my breath when you say my name','chorus',10,0),(794,21,'I go into a free fall','chorus',11,0),(795,21,'I\'m spinning and I can\'t stop','chorus',12,0),(796,21,'I lose myself and I\'m not the same','chorus',13,0),(797,21,'When we\'re in a free fall','chorus',14,0),(798,21,'When we\'re in a free fall','chorus',15,0),(799,21,'Never felt like, this before','verse',16,0),(800,21,'Got the thrill and now I only want more','verse',17,0),(801,21,'Scared to look down, but gravity','verse',18,0),(802,21,'Can\'t compete with all the light you give in me','verse',19,0),(803,21,'It\'s like you\'re diving in to my heart','bridge',20,0),(804,21,'I\'m floating off the ground','bridge',21,0),(805,21,'When you pull me in to your arms','bridge',22,0),(806,21,'I go into a free fall','chorus',23,0),(807,21,'I\'m spinning and I can\'t stop','chorus',24,0),(808,21,'I lose myself and I\'m not the same','chorus',25,0),(809,21,'I go into a free fall','chorus',26,0),(810,21,'I\'m spinning and I can\'t stop','chorus',27,0),(811,21,'I lose myself and I\'m not the same','chorus',28,0),(812,21,'When we\'re in a free fall','chorus',29,0),(813,21,'I go into a free fall','outro',30,0),(814,21,'I\'m spinning and I can\'t stop','outro',31,0),(815,21,'I lose myself and I\'m not the same','outro',32,0),(816,21,'When we\'re in a free fall','outro',33,0);
/*!40000 ALTER TABLE `lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_id` int(11) NOT NULL,
  `song_name` varchar(45) NOT NULL,
  `is_approved` tinyint(4) DEFAULT '0',
  `has_featuring` tinyint(4) DEFAULT '0',
  `youtube_link` varchar(45) DEFAULT NULL,
  `contributed_user_id` int(11) DEFAULT '0',
  PRIMARY KEY (`song_id`),
  UNIQUE KEY `song_id_UNIQUE` (`song_id`),
  KEY `artist_key_idx` (`artist_id`),
  KEY `user_id_key_idx` (`contributed_user_id`),
  CONSTRAINT `artist_key` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id_key` FOREIGN KEY (`contributed_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,1,'Don\'t Let Me Down',1,0,'Io0fBr1XBUA',7),(2,2,'Hurts So Good',1,0,'4fqwVBuunxY',7),(3,3,'No Substitute',1,1,'O6xrigNEPJE',6),(4,5,'Numb',1,0,'kXYiU_JCYtU',8),(5,6,'Operate (Illenium remix)',1,1,'2DnOnw18u5E',7),(6,8,'Crawl Outta Love',1,1,'xxv_OyetkcE',7),(7,10,'Never Too Late',1,0,'lL2ZwXj1tXM',8),(8,2,'Breathe',1,0,'mUeaY6IN-RU',6),(9,10,'Someone Who Cares',1,0,'zRwLbuKvNpE',7),(10,2,'Think Before I Talk',1,0,'OcnbhekmsEM',7),(11,2,'Does She Know',1,0,'DeTubvq7MiU',6),(12,11,'Love',1,0,'3-NTv0CdFCk',8),(13,12,'So Web',1,1,'jIMqTj4RGlY',7),(21,8,'Free Fall',1,1,'gyaqCeuxhTE',0);
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs_ratings`
--

DROP TABLE IF EXISTS `songs_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs_ratings` (
  `vote_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  PRIMARY KEY (`vote_id`),
  UNIQUE KEY `vote_id_UNIQUE` (`vote_id`),
  KEY `id_of_song_key_ratings_idx` (`song_id`),
  CONSTRAINT `id_of_song_key_ratings` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs_ratings`
--

LOCK TABLES `songs_ratings` WRITE;
/*!40000 ALTER TABLE `songs_ratings` DISABLE KEYS */;
INSERT INTO `songs_ratings` VALUES (7,8,6,5),(8,7,8,5),(9,8,12,5),(10,6,12,4),(11,7,12,5),(12,7,10,5),(13,7,10,5),(14,7,5,4),(15,7,1,5);
/*!40000 ALTER TABLE `songs_ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(15) NOT NULL,
  `hashed_password` varchar(100) NOT NULL,
  `user_role` enum('MODERATOR','COMMON') NOT NULL DEFAULT 'COMMON',
  `is_blocked` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'Anonymous','1','COMMON',0),(6,'User','5f4dcc3b5aa765d61d8327deb882cf99','COMMON',1),(7,'admin','21232f297a57a5a743894a0e4a801fc3','MODERATOR',0),(8,'PaperPlane','aaeccbfb5432fcc2f8d7c2235a3865c8','COMMON',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'websitedatabase'
--

--
-- Dumping routines for database 'websitedatabase'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-08 18:05:34
