-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table beehave.ask
CREATE TABLE IF NOT EXISTS `ask` (
  `int` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `question` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`int`),
  FULLTEXT KEY `question` (`question`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.ask: ~0 rows (approximately)
DELETE FROM `ask`;
/*!40000 ALTER TABLE `ask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ask` ENABLE KEYS */;

-- Dumping structure for table beehave.askfeed
CREATE TABLE IF NOT EXISTS `askfeed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ask_id` int(11) DEFAULT NULL,
  `retention` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_ask_id` (`user_id`,`ask_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.askfeed: ~0 rows (approximately)
DELETE FROM `askfeed`;
/*!40000 ALTER TABLE `askfeed` DISABLE KEYS */;
/*!40000 ALTER TABLE `askfeed` ENABLE KEYS */;

-- Dumping structure for table beehave.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.user: ~0 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `email`, `password`, `name`) VALUES
	(1, 'budi', 'budi', 'budi');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table beehave.userstat
CREATE TABLE IF NOT EXISTS `userstat` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `word` int(11) DEFAULT NULL,
  `freq` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_word` (`user_id`,`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.userstat: ~0 rows (approximately)
DELETE FROM `userstat`;
/*!40000 ALTER TABLE `userstat` DISABLE KEYS */;
/*!40000 ALTER TABLE `userstat` ENABLE KEYS */;

-- Dumping structure for table beehave.wall
CREATE TABLE IF NOT EXISTS `wall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `keyword` text DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category` (`keyword`(768)) USING BTREE,
  FULLTEXT KEY `title` (`title`),
  FULLTEXT KEY `content` (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.wall: ~0 rows (approximately)
DELETE FROM `wall`;
/*!40000 ALTER TABLE `wall` DISABLE KEYS */;
/*!40000 ALTER TABLE `wall` ENABLE KEYS */;

-- Dumping structure for table beehave.wallfeed
CREATE TABLE IF NOT EXISTS `wallfeed` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `wall_id` int(11) DEFAULT NULL,
  `retention` int(11) DEFAULT NULL,
  `timestamp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_wall_id` (`user_id`,`wall_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table beehave.wallfeed: ~0 rows (approximately)
DELETE FROM `wallfeed`;
/*!40000 ALTER TABLE `wallfeed` DISABLE KEYS */;
/*!40000 ALTER TABLE `wallfeed` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
