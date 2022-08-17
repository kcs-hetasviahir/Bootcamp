CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_role` varchar(45) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `audit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `entity_name` varchar(255) DEFAULT NULL,
  `ref_table_id` int(11) DEFAULT NULL,
  `content` longtext,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_STATE_CITY_INDEX_idx` (`state_id`),
  CONSTRAINT `FK_STATE_CITY_INDEX` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `institution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1024) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `device_unique_id` varchar(512) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CITY_INSTITUTION_INDEX_idx` (`city`),
  KEY `FK_USER_ID_USER_INDEX_idx` (`user_id`),
  CONSTRAINT `FK_CITY_INSTITUTION_INDEX` FOREIGN KEY (`city`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USR_ID_INSTI_INDEX` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `supervisor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supervisor_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `support_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ID_USER_INDEX_idx` (`user_id`),
  CONSTRAINT `FK_USER_ID_USER_INDEX` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(45) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `date_of_birth` timestamp NULL DEFAULT NULL,
  `date_of_joining` timestamp NULL DEFAULT NULL,
  `mobile_number` varchar(45) DEFAULT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `designation` varchar(155) DEFAULT NULL,
  `posting_location` int(11) DEFAULT NULL,
  `institution_id` int(11) DEFAULT NULL,
  `supervisor_id` int(11) DEFAULT NULL,
  `house_name` varchar(255) DEFAULT NULL,
  `address1` varchar(512) DEFAULT NULL,
  `address2` varchar(512) DEFAULT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `pincode` varchar(45) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SUPERVISOR_SUPER_INDEX_idx` (`supervisor_id`),
  KEY `FK_POSTING_LOC_CITY_INDEX_idx` (`posting_location`),
  KEY `FK_CITY_ID_CITY_INDEX_idx` (`city`),
  KEY `FK_STATE_ID_STATE_INDEX_idx` (`state`),
  KEY `FK_INSTI_ID_INSTITUTION_INDEX_idx` (`institution_id`),
  CONSTRAINT `FK_CITY_ID_CITY_INDEX` FOREIGN KEY (`city`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_INSTI_ID_INSTITUTION_INDEX` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_POSTING_LOC_CITY_INDEX` FOREIGN KEY (`posting_location`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_STATE_ID_STATE_INDEX` FOREIGN KEY (`state`) REFERENCES `state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SUPERVISOR_SUPER_INDEX` FOREIGN KEY (`supervisor_id`) REFERENCES `supervisor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `teacher_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `attendance_date` timestamp NULL DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `video_location` varchar(1024) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_dttm` timestamp NULL DEFAULT NULL,
  `last_updated_dttm` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TEACHER_TEACHER_ATTENDANCE_INDEX_idx` (`teacher_id`),
  CONSTRAINT `FK_TEACHER_TEACHER_ATTENDANCE_INDEX` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `holiday` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(512) NULL,
  `date` TIMESTAMP NULL,
  `created_dttm` TIMESTAMP NULL DEFAULT NULL,
  `last_updated_dttm` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
