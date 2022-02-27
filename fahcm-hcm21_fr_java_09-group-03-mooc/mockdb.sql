-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: mockdb
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct` bit(1) NOT NULL,
  `text` varchar(150) NOT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,_binary '\0','a 1',1),(2,_binary '','a 2 ',1),(3,_binary '\0','a 3 ',1),(4,_binary '\0','a 4',1),(5,_binary '\0','4454',2),(6,_binary '\0','4888',2),(7,_binary '\0','4464',2),(8,_binary '\0','4546',2),(9,_binary '','1',3),(10,_binary '\0','2',3),(11,_binary '\0','5',4),(12,_binary '','9',4),(13,_binary '\0','8',4),(14,_binary '\0','Cat',5),(15,_binary '','Dog',5),(16,_binary '\0','Mouse',5),(17,_binary '\0','Pig',5),(18,_binary '','Cat',6),(19,_binary '\0','Dog',6),(20,_binary '\0','Mouse',6),(21,_binary '\0','Pig',6),(22,_binary '\0','Cat',7),(23,_binary '\0','Dog',7),(24,_binary '','Mouse',7),(25,_binary '\0','Pig',7),(26,_binary '\0','Cat',8),(27,_binary '\0','Dog',8),(28,_binary '\0','Mouse',8),(29,_binary '','Pig',8);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `multiple` bit(1) NOT NULL,
  `text` varchar(150) NOT NULL,
  `quiz_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yh0c1qaxfwlcnwo9dms2txf` (`quiz_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,NULL,_binary '\0','test question',1),(2,NULL,_binary '\0','test question 2',1),(3,NULL,_binary '\0','test question',2),(4,NULL,_binary '\0','test question 2',2),(5,'',_binary '\0','Choose the correct word',NULL),(6,'',_binary '\0','Choose the correct word',NULL),(7,'',_binary '\0','Choose the correct word',NULL),(8,'',_binary '\0','Choose the correct word',NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` datetime DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `education_level` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `max_attempts` int DEFAULT NULL,
  `plays` int DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `time_limit` int DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1tofsm1qynhakggx7ttqh8ihu` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (1,NULL,'this is quiz','HIGHSCHOOL','https://res.cloudinary.com/quizsystem/image/upload/v1642564565/user/images/992404.jpg.jpg',5,6,_binary '',60,'Happy new Year','2022-01-19 11:00:03',2),(2,'2022-01-19 11:02:02','this is test test','KINDERGARTEN','https://res.cloudinary.com/quizsystem/image/upload/v1642564920/user/images/swiss-alps-mountains-night-milky-way-starry-sky-hdr-night-2560x1440-6085.jpg.jpg',10,1,_binary '',5,'test post 1234','2022-01-19 11:02:02',2);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_comment`
--

DROP TABLE IF EXISTS `quiz_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_flagged` bit(1) NOT NULL,
  `text` varchar(200) NOT NULL,
  `quiz_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmrgd65fy1kwq3nsefanj2biw` (`quiz_id`),
  KEY `FK94ly7mswi70ewyg6m21lvwxm4` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_comment`
--

LOCK TABLES `quiz_comment` WRITE;
/*!40000 ALTER TABLE `quiz_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `score` double DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  `quiz_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeqh1jtif4bn46v1112vo5e45v` (`quiz_id`),
  KEY `FKeny3549xar8rnrcmdw3hl0la1` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,5,'2022-01-19 10:59:00','2022-01-19 10:59:21',1,3),(2,0,'2022-01-19 10:59:00','2022-01-19 10:59:34',1,3),(3,5,'2022-01-19 10:59:00','2022-01-19 10:59:42',1,3),(4,5,'2022-01-19 11:00:00','2022-01-19 11:00:36',1,3),(5,5,'2022-01-19 11:00:00','2022-01-19 11:00:55',1,3),(6,10,'2022-01-19 11:02:00','2022-01-19 11:02:35',2,3),(7,0,'2022-02-20 18:31:00','2022-02-20 18:31:55',1,4);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'MATH'),(2,'ENGLISH'),(3,'SCIENCE');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_quizz`
--

DROP TABLE IF EXISTS `subject_quizz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_quizz` (
  `quizz_id` bigint NOT NULL,
  `subject_id` bigint NOT NULL,
  KEY `FKt47fveu77luxn4twoqox7c1ob` (`subject_id`),
  KEY `FKj3otyr1gvgfffvusktu7yl98q` (`quizz_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_quizz`
--

LOCK TABLES `subject_quizz` WRITE;
/*!40000 ALTER TABLE `subject_quizz` DISABLE KEYS */;
INSERT INTO `subject_quizz` VALUES (1,2),(1,1),(2,3);
/*!40000 ALTER TABLE `subject_quizz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `education_level` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `provider` int DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'/QuizSystem_FE/static/img/user-default-avatar.png',NULL,'2022-01-19 10:24:47',NULL,'ngotoan@gmail.com','toan','$2a$10$n91UaTUncvLChTK61jbvUesLQB1tBxldG892H5JKG1g62eK1x9w4q',NULL,NULL,'ADMIN',_binary '','2022-01-19 10:24:47','bTqgrF1gNe7fsLRbdaM44rkV1GQc0BnHfFnJbB5vJeNfeA2jh1IiBxHhCxGQoeGY'),(2,'https://res.cloudinary.com/quizsystem/image/upload/v1645281309/user/images/banktranfer.png.png','2022-02-16 07:00:00','2022-01-19 10:29:28',NULL,'ngotoanlibra@gmail.com','toanngo','$2a$10$UCrLyUuTskUCzKIeu846k..pTyjvoyZK4oEJubu6drcq2Q9FUo/mi',NULL,NULL,'TEACHER',_binary '','2022-02-19 21:35:21',NULL),(3,'/QuizSystem_FE/static/img/user-default-avatar.png',NULL,'2022-01-19 10:58:15','Kindergarten','customer@gmail.com','customer','$2a$10$R806njcjggfw8oaZCpiIlOHc1ir/q1lX2mbnVjj7SueqUH7yigNcu',NULL,NULL,'STUDENT',_binary '','2022-01-19 10:59:04','WubNLVz8mUJlok5TIl6jg5f28MVzf7eRbnx5kZchTm0lUwy2J5m5vfDJujdK0LlE'),(4,'/QuizSystem_FE/static/img/user-default-avatar.png',NULL,'2022-02-19 21:37:08','Secondary School','toanngongo97@gmail.com','toan','$2a$10$tbTLSUoAcVDkynPGf9gmYewxYZwbTVlkdSIh6BFosy5Oo4PoWahMu',NULL,NULL,'STUDENT',_binary '','2022-02-20 18:31:37',NULL),(5,'/QuizSystem_FE/static/img/user-default-avatar.png',NULL,'2022-02-21 08:08:37',NULL,'admin@gmail.com','toanngo','$2a$10$euQu7.nCuyUfww.WceC6g.tiRyzBBPTo14317vmpwp6DKW13u1udG',NULL,NULL,'ADMIN',_binary '','2022-02-21 08:08:37','sUXIrN3Fis3WsyAbw2yAO8VN4fPiF0NTcg4Nt0quJvoilc996Id7Mum6va9kvBxl'),(6,'/QuizSystem_FE/static/img/user-default-avatar.png',NULL,'2022-02-21 09:13:40','University','huan@gmail.com','huan','$2a$10$RwQI5NuzO8Im5.OQyOGVlujehrvPQaNJFNH6qPxY.6EdM5jmw1uBK',NULL,NULL,'STUDENT',_binary '','2022-02-21 09:14:59','WYCBf7uKXRbDAHwkjJ8JCb5EfL5aKT2Aqg9MxxCW5mJf0kYdNIaK8g86BRAL3w7S');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_interests`
--

DROP TABLE IF EXISTS `user_interests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_interests` (
  `user_id` bigint NOT NULL,
  `subject_id` bigint NOT NULL,
  KEY `FKoex8v75fvoq4rlwpov23gqxx` (`subject_id`),
  KEY `FKfk6yib4h6a7ca0k3xwtr09eom` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_interests`
--

LOCK TABLES `user_interests` WRITE;
/*!40000 ALTER TABLE `user_interests` DISABLE KEYS */;
INSERT INTO `user_interests` VALUES (3,1),(3,2),(3,3),(4,1),(4,2),(6,3);
/*!40000 ALTER TABLE `user_interests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-21  9:29:01
