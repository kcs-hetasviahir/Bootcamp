ALTER TABLE `city` 
ADD COLUMN `code` VARCHAR(45) NULL AFTER `name`;
	
ALTER TABLE `state` 
ADD COLUMN `code` VARCHAR(45) NULL AFTER `name`;
	
ALTER TABLE `teacher` 
ADD COLUMN `is_processed` BIT(1) NULL AFTER `video_name`,
ADD COLUMN `processed_status` VARCHAR(255) NULL AFTER `is_processed`,
ADD COLUMN `video_label` VARCHAR(255) NULL AFTER `processed_status`;
	
ALTER TABLE `teacher_attendance` 
ADD COLUMN `is_processed` BIT(1) NULL AFTER `status`,
ADD COLUMN `processed_status` VARCHAR(255) NULL AFTER `is_processed`,
ADD COLUMN `video_label` VARCHAR(255) NULL AFTER `processed_status`;
	