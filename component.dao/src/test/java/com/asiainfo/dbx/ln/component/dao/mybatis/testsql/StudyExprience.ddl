CREATE TABLE `study_experience` (
  `id` varchar(100) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `person_id` varchar(100) DEFAULT NULL,
  KEY `study_experience_person_FK` (`person_id`),
  CONSTRAINT `study_experience_person_FK` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8