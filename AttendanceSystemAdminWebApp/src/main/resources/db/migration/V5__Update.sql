ALTER TABLE `teacher_attendance` 
ADD COLUMN `person_accuracy` VARCHAR(255) NULL DEFAULT NULL AFTER `process_end_time`;