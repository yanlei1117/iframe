CREATE TABLE `person` (
  `name` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `identity_number` int(11) NOT NULL,
  `sex` char(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `stature` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8