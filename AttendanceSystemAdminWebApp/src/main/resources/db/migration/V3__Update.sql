ALTER TABLE `teacher` 
ADD COLUMN `device_id` VARCHAR(255) NULL AFTER `video_label`,
ADD COLUMN `latitude` VARCHAR(255) NULL AFTER `device_id`,
ADD COLUMN `longitude` VARCHAR(255) NULL AFTER `latitude`;
	
ALTER TABLE `teacher_attendance` 
ADD COLUMN `device_id` VARCHAR(255) NULL AFTER `video_label`,
ADD COLUMN `latitude` VARCHAR(255) NULL AFTER `device_id`,
ADD COLUMN `longitude` VARCHAR(255) NULL AFTER `latitude`;
