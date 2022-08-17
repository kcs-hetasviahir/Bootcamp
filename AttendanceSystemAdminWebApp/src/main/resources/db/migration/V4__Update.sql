ALTER TABLE `teacher_attendance` 
ADD COLUMN `process_start_time` VARCHAR(255) NULL DEFAULT NULL AFTER `video_label`,
ADD COLUMN `process_end_time` VARCHAR(255) NULL DEFAULT NULL AFTER `process_start_time`;

ALTER TABLE `teacher` 
ADD COLUMN `process_start_time` VARCHAR(255) NULL DEFAULT NULL AFTER `video_label`,
ADD COLUMN `process_end_time` VARCHAR(255) NULL DEFAULT NULL AFTER `process_start_time`;
