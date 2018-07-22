-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: movie_library
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `genre` varchar(45) NOT NULL,
  PRIMARY KEY (`genre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES ('Action'),('Adventure'),('Animation'),('Biography'),('Comedy'),('Crime'),('Documentary'),('Drama'),('Family'),('Fantasy'),('Horror'),('Mystery'),('Romance'),('Sci-Fi'),('Thriler');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `movie_id` varchar(45) NOT NULL,
  `title` varchar(60) NOT NULL,
  `description` varchar(800) NOT NULL,
  `runningTime` varchar(45) NOT NULL,
  `year` int(4) NOT NULL,
  `rating` double NOT NULL,
  `watchStatus` varchar(45) NOT NULL,
  `favourite` varchar(45) NOT NULL,
  `poster` varchar(100) NOT NULL,
  `offlineLink` varchar(250) NOT NULL,
  `onlineLink` varchar(300) NOT NULL,
  `trailerLink` varchar(250) NOT NULL,
  `addedDate` date NOT NULL,
  `allMovieGenres` varchar(100) NOT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES ('Mov001','Warm Bodies','With much of the world\'s population now an undead horde, R is a young and oddly introspective zombie. While fighting with and feeding on a human scavenger party, R meets Julie and feels an urge to protect her. What happens next is the beginning of a strangely warm relationship that allows R to begin regaining his humanity. As this change spreads through the local undead population like a virus, Julie and R eventually have to face a larger issue when the very nature of their friendship is challenged. Caught between the paranoid human forces and the ferocious \"Bonies\", zombies who are a mutual threat, R and Julie must find a way to bridge the differences of each side to fight for a better world no one thought possible.','98 min',2013,6.9,'Watched','Yes','posters/warm_bodies.jpg','n/a','http://123moviesfree.com/watch/warm-bodies-2013-online-free-123movies.html','https://www.youtube.com/embed/07s-cNFffDM','2017-04-01','Comedy/Horror/Romance'),('Mov002','Fantastic Beasts and Where to Find Them','The year is 1926 and Newt Scamander has just completed a global excursion to find and document an extraordinary array of magical creatures. Arriving in New York for a brief stopover, he might have come and gone without incident...were it not for a No-Maj (American for Muggle) named Jacob, a misplaced magical case, and the escape of some of Newt\'s fantastic beasts, which could spell trouble for both the wizarding and No-Maj worlds.','133 min',2016,8,'Watched','Yes','posters/fan_beast.jpg','D:\\Torrent Downloads\\Fantastic Beasts And Where To Find Them (2016) [YTS.AG]\\Fantastic.Beasts.And.Where.To.Find.Them.2016.720p.BluRay.x264-[YTS.AG].mp4','http://123moviesfree.com/watch/fantastic-beasts-and-where-to-find-them-2016-i.27-online-free-123movies.html','https://www.youtube.com/embed/VYZ3U1inHA4','2017-04-01','Adventure/Family/Fantasy'),('Mov003','Rogue One: A Star Wars Story','All looks lost for the Rebellion against the Empire as they learn of the existence of a new super weapon, the Death Star. Once a possible weakness in its construction is uncovered, the Rebel Alliance must set out on a desperate mission to steal the plans for the Death Star. The future of the entire galaxy now rests upon its success.','133 min',2016,8.1,'Not Watched','Not Yet','posters/sample.jpg','n/a','http://123moviesfree.com/watch/rogue-one-2016-i.5-online-free-123movies.html','https://www.youtube.com/embed/sC9abcLLQpI','2017-04-01','Action/Adventure/Sci-Fi'),('Mov004','Hachi: A Dog\'s Tale','In Bedridge, Professor Parker Wilson finds an abandoned dog at the train station and takes it home with the intention of returning the animal to its owner. He finds that the dog is an Akita and names it Hachiko. However, nobody claims the dog so his family decides to keep Hachi. ','93 min ',2009,8.1,'Watched','Yes','posters/hachi.jpg','n/a','https://123movies.io/movie/hachi-a-dogs-tale-22364','https://www.youtube.com/embed/TIl2o1hm1F4','2017-04-08','Drama/Family'),('Mov005','The Terminal','Victor Novarski reaches JFK airport from a politically unstable country. Due to collapse of his government, his papers are no longer valid in the airport, and hence he is forced to stay in the airport till the war cools down. He makes the airport his home and develops a friendship with the people who work there until he can leave.','128 min',2004,7.3,'Watched','Not Yet','posters/terminal.jpg','n/a','https://123movies.io/movie/the-terminal-21292','https://www.youtube.com/embed/GZjC9dAvWuU','2017-04-08','Comedy/Drama/Romance'),('Mov006','The Sixth Sense','Malcom Crowe (Bruce Willis)is a child psychologist who receives an award on the same night that he is visited by a very unhappy ex-patient. After this encounter, Crowe takes on the task of curing a young boy with the same ills as the ex-patient (Donnie Wahlberg) . This boy \"sees dead people\". Crowe spends a lot of time with the boy much to the dismay of his wife (Olivia Williams). Cole\'s mom (Toni Collette) is at her wit\'s end with what to do about her son\'s increasing problems. Crowe is the boy\'s only hope. ','107 min',1999,8.1,'Watched','Not Yet','posters/sixth_sense.jpg','n/a','https://123moviesfree.com/watch/the-sixth-sense-1999-online-free-123movies.html','https://www.youtube.com/embed/VG9AGf66tXM','2017-04-08','Drama/Mystery/Thriler'),('Mov007','World War Z','Life for former United Nations investigator Gerry Lane and his family seems content. Suddenly, the world is plagued by a mysterious infection turning whole human populations into rampaging mindless zombies. After barely escaping the chaos, Lane is persuaded to go on a mission to investigate this disease. What follows is a perilous trek around the world where Lane must brave horrific dangers and long odds to find answers before human civilization falls.','116 min',2013,7,'Watched','Not Yet','posters/world_war_z.jpg','n/a','https://123moviesfree.com/watch/world-war-z-2013-online-free-123movies.html','https://www.youtube.com/embed/HcwTxRuq-uk','2017-04-08','Action/Adventure/Horror'),('Mov008','Because of Winn-Dixie','A 10-year-old girl, abandoned by her mother when she was three, moves to a small town in Florida with her father, a preacher. While there, she adopts a stray dog whom she names after the local supermarket where he was found. With her goofy pooch by her side, she meets an eclectic group of townspeople and rekindles an almost lost relationship with her father.','106 min',2005,6.4,'Watched','Yes','posters/winn-dixie.jpg','n/a','https://123moviesfree.com/watch/because-of-winn-dixie-2005-online-free-123movies.html','https://www.youtube.com/embed/ZpsE_7oLdAU','2017-04-08','Comedy/Drama/Family'),('Mov009','Kick-Ass','Dave Lizewski is an unnoticed high school student and comic book fan with a few friends and who lives alone with his father. His life is not very difficult and his personal trials not that overwhelming. However, one day he makes the simple decision to become a super-hero even though he has no powers or training. Written by Daniel J. Leary','117 min',2010,7.7,'Watched','Yes','posters/kick-ass.jpg','n/a','https://123moviesfree.com/watch/kick-ass-2010-online-free-123movies.html','https://www.youtube.com/embed/rFpWpkxsVI8','2017-04-08','Action/Comedy'),('Mov010','The Prestige','In the end of the Nineteenth Century, in London, Robert Angier, his beloved wife Julia McCullough and Alfred Borden are friends and assistants of a magician. When Julia accidentally dies during a performance, Robert blames Alfred for her death and they become enemies. Both become famous and rival magicians, sabotaging the performance of the other on the stage. When Alfred performs a successful trick, Robert becomes obsessed trying to disclose the secret of his competitor with tragic consequences. Written by Claudio Carvalho, Rio de Janeiro, Brazil','130 min',2006,8.5,'Watched','Yes','posters/the_prestige.jpg','n/a','https://123moviesfree.com/watch/the-prestige-2006-online-free-123movies.html','https://www.youtube.com/embed/DHoXum3M9eE','2017-04-08','Drama/Mystery/Sci-Fi/Thriler'),('Mov011','Beauty and the Beast','Disney\'s animated classic takes on a new form, with a widened mythology and an all-star cast. A young prince, imprisoned in the form of a beast, can be freed only by true love. What may be his only opportunity arrives when he meets Belle, the only human girl to ever visit the castle since it was enchanted. ','129 min',2017,7.8,'Not Watched','Not Yet','posters/beauty_n_beast.jpg','n/a','https://123moviesfree.com/watch/beauty-and-the-beast-2017-online-free-123movies.html','https://www.youtube.com/embed/e3Nl_TCQXuw','2017-04-11','Family/Fantasy/Romance'),('Mov012','Shutter Island','It\'s 1954, and up-and-coming U.S. marshal Teddy Daniels is assigned to investigate the disappearance of a patient from Boston\'s Shutter Island Ashecliffe Hospital. He\'s been pushing for an assignment on the island for personal reasons, but before long he wonders whether he hasn\'t been brought there as part of a twisted plot by hospital doctors whose radical treatments range from unethical to illegal to downright sinister. Teddy\'s shrewd investigating skills soon provide a promising lead, but the hospital refuses him access to records he suspects would break the case wide open. Written by alfiehitchie',' 138 min',2010,8.1,'Watched','Yes','posters/shutter_island.jpg','n/a','https://123moviesfree.com/watch/shutter-island-2010-online-free-123movies.html','https://www.youtube.com/embed/YDGldPitxic','2017-04-11','Mystery/Thriler'),('Mov013','How to Train Your Dragon','A hapless young Viking who aspires to hunt dragons becomes the unlikely friend of a young dragon himself, and learns there may be more to the creatures than he assumed. ','98 min',2010,8.2,'Watched','Not Yet','posters/how_to_trian.jpg','n/a','https://123moviesfree.com/watch/how-to-train-your-dragon-2010-i.1-online-free-123movies.html','https://www.youtube.com/embed/TgmJPcs6BE8','2017-04-11','Action/Adventure/Animation'),('Mov014','The Help','An aspiring author during the civil rights movement of the 1960s decides to write a book detailing the African American maids\' point of view on the white families for which they work, and the hardships they go through on a daily basis. ','146 min',2011,8.1,'Watched','Not Yet','posters/the_help.jpg','n/a','https://123moviesfree.com/watch/the-help-2011-online-free-123movies.html','https://www.youtube.com/embed/aT9eWGjLv6s','2017-04-11','Drama'),('Mov015','The Age of Adaline','A young woman, born at the turn of the 20th century, is rendered ageless after an accident. After many solitary years, she meets a man who complicates the eternal life she has settled into. ','112 min',2015,7.2,'Watched','Yes','posters/age_of_adaline.jpg','n/a','n/a','https://www.youtube.com/embed/7UzSekc0LoQ','2017-04-11','Drama/Fantasy/Romance'),('Mov016','Me Before You','A girl in a small town forms an unlikely bond with a recently-paralyzed man she\'s taking care of. ','110 min',2016,7.4,'Watched','Yes','posters/me_before_u.jpg','n/a','https://123moviesfree.com/watch/me-before-you-2016-online-free-123movies.html','https://www.youtube.com/embed/Eh993__rOxA','2017-04-11','Drama/Romance'),('Mov017','Deadpool','This is the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life. Written by 20th Century Fox','108 min',2016,8,'Watched','Yes','posters/deadpool.jpg','n/a','https://123moviesfree.com/watch/deadpool-2016-i.13-online-free-123movies.html','https://www.youtube.com/embed/ZIM1HydF9UA','2017-04-11','Action/Adventure/Comedy'),('Mov018','The Conjuring 2','Lorraine and Ed Warren travel to north London to help a single mother raising four children alone in a house plagued by a malicious spirit. ','134 min',2016,7.4,'Watched','Not Yet','posters/conjuring2.jpg','n/a','https://123moviesfree.com/watch/the-conjuring-2-2016-i.1-online-free-123movies.html','https://www.youtube.com/embed/KyA9AtUOqRM','2017-04-11','Horror/Mystery/Thriler'),('Mov019','Doctor Strange','While on a journey of physical and spiritual healing, a brilliant neurosurgeon is drawn into the world of the mystic arts. ','115 min',2016,7.6,'Watched','Not Yet','posters/doctor_strange.jpg','n/a','https://123moviesfree.com/watch/doctor-strange-2016-i.15-online-free-123movies.html','https://www.youtube.com/embed/Lt-U_t2pUHI','2017-04-11','Action/Adventure/Fantasy'),('Mov020','Train to Busan','While a zombie virus breaks out in South Korea, passengers struggle to survive on the train from Seoul to Busan. ','118 min',2016,7.5,'Watched','Yes','posters/train_to_busan.jpg','n/a','https://123moviesfree.com/watch/train-to-busan-2016-i.2-online-free-123movies.html','https://www.youtube.com/embed/pyWuHv2-Abk','2017-04-11','Action/Drama/Horror'),('Mov021','Mama ','A young couple take in their two nieces only to suspect that a foreboding evil has latched itself to their family. ','100 min',2013,6.2,'Watched','Not Yet','posters/mama.jpg','n/a','https://123moviesfree.com/watch/mama-2013-i.1-online-free-123movies.html','https://www.youtube.com/embed/7Am7i7uM9r0','2017-04-11','Horror/Thriler'),('Mov022','The Ring','Rachel Keller is a journalist investigating a videotape that may have killed four teenagers (including her niece). There is an urban legend about this tape: the viewer will die seven days after watching it. If the legend is correct, Rachel will have to run against time to save her son\'s and her own life. Written by Claudio Carvalho, Rio de Janeiro, Brazil','115 min',2002,7.1,'Watched','Yes','posters/the_ring.jpg','n/a','https://123moviesfree.com/watch/the-ring-2002-online-free-123movies.html','https://www.youtube.com/embed/_PkgRhzq_BQ\" frameborder=\"0','2017-04-11','Horror/Mystery'),('Mov023','The Mist','A freak storm unleashes a species of bloodthirsty creatures on a small town, where a small band of citizens hole up in a supermarket and fight for their lives. ','126 min',2007,7.2,'Watched','Yes','posters/mist.jpg','n/a','https://123moviesfree.com/watch/the-mist-2007-online-free-123movies.html','https://www.youtube.com/embed/LhCKXJNGzN8','2017-04-11','Horror'),('Mov024','Diary of a Wimpy Kid','The adventures of a teenager who is fresh out of elementary and transitions to middle school, where he has to learn the consequences and responsibility to survive the year. ','94 min',2010,6.2,'Watched','Not Yet','posters/wimpy_kid.jpg','n/a','https://123moviesfree.com/watch/diary-of-a-wimpy-kid-2010-online-free-123movies.html','https://www.youtube.com/embed/nlI3Ykm3HV4','2017-04-11','Comedy/Family');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duplicate_movId` varchar(45) NOT NULL,
  `movie_id` varchar(45) NOT NULL,
  `genre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movie_id_fk_idx` (`movie_id`),
  KEY `genre_fk_idx` (`genre`),
  CONSTRAINT `genre_fk` FOREIGN KEY (`genre`) REFERENCES `genre` (`genre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `movie_id_fk` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (44,'Mov001','Mov001','Comedy'),(45,'Mov001','Mov001','Horror'),(46,'Mov001','Mov001','Romance'),(47,'Mov002','Mov002','Adventure'),(48,'Mov002','Mov002','Family'),(49,'Mov002','Mov002','Fantasy'),(50,'Mov003','Mov003','Action'),(51,'Mov003','Mov003','Adventure'),(52,'Mov003','Mov003','Sci-Fi'),(53,'Mov004','Mov004','Drama'),(54,'Mov004','Mov004','Family'),(55,'Mov005','Mov005','Comedy'),(56,'Mov005','Mov005','Drama'),(57,'Mov005','Mov005','Romance'),(58,'Mov006','Mov006','Drama'),(59,'Mov006','Mov006','Mystery'),(60,'Mov006','Mov006','Thriler'),(61,'Mov007','Mov007','Action'),(62,'Mov007','Mov007','Adventure'),(63,'Mov007','Mov007','Horror'),(73,'Mov008','Mov008','Comedy'),(74,'Mov008','Mov008','Drama'),(75,'Mov008','Mov008','Family'),(76,'Mov009','Mov009','Action'),(77,'Mov009','Mov009','Comedy'),(78,'Mov010','Mov010','Drama'),(79,'Mov010','Mov010','Mystery'),(80,'Mov010','Mov010','Sci-Fi'),(81,'Mov010','Mov010','Thriler'),(82,'Mov011','Mov011','Family'),(83,'Mov011','Mov011','Fantasy'),(84,'Mov011','Mov011','Romance'),(85,'Mov012','Mov012','Mystery'),(86,'Mov012','Mov012','Thriler'),(87,'Mov013','Mov013','Action'),(88,'Mov013','Mov013','Adventure'),(89,'Mov013','Mov013','Animation'),(90,'Mov014','Mov014','Drama'),(91,'Mov015','Mov015','Drama'),(92,'Mov015','Mov015','Fantasy'),(93,'Mov015','Mov015','Romance'),(94,'Mov016','Mov016','Drama'),(95,'Mov016','Mov016','Romance'),(96,'Mov017','Mov017','Action'),(97,'Mov017','Mov017','Adventure'),(98,'Mov017','Mov017','Comedy'),(99,'Mov018','Mov018','Horror'),(100,'Mov018','Mov018','Mystery'),(101,'Mov018','Mov018','Thriler'),(102,'Mov019','Mov019','Action'),(103,'Mov019','Mov019','Adventure'),(104,'Mov019','Mov019','Fantasy'),(105,'Mov020','Mov020','Action'),(106,'Mov020','Mov020','Drama'),(107,'Mov020','Mov020','Horror'),(108,'Mov021','Mov021','Horror'),(109,'Mov021','Mov021','Thriler'),(110,'Mov022','Mov022','Horror'),(111,'Mov022','Mov022','Mystery'),(112,'Mov023','Mov023','Horror'),(113,'Mov024','Mov024','Comedy'),(114,'Mov024','Mov024','Family');
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-11  4:58:27
