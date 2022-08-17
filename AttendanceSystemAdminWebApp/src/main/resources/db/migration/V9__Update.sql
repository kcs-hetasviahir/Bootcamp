CREATE TABLE `designation_mst` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `designation_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;