ALTER TABLE `teacher` 
ADD COLUMN `registration_id` VARCHAR(45) NULL DEFAULT NULL AFTER `teacher_id`;

ALTER TABLE `teacher_attendance`
ADD COLUMN `transaction_id` VARCHAR(45) NULL DEFAULT NULL AFTER `teacher_id`;