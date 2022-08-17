CREATE TABLE `district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `taluka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dist_id` int(11) NOT NULL,
  `taluka_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `village` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taluka_id` int(11) NOT NULL,
  `village_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `ssa_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_code` int(11) NOT NULL,
  `district_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `ssa_blocks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dist_id` int(11) NOT NULL,
  `block_code` int(11) NOT NULL,
  `block_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `ssa_schools` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `block_id` int(11) NOT NULL,
  `school_code` double NOT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;