CREATE TABLE `otp_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver_id` varchar(255) DEFAULT NULL,
  `otp_key` blob NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8;