ALTER TABLE `support_ticket`
DROP COLUMN `user_id`;

ALTER TABLE `support_ticket`
DROP COLUMN `username`;

ALTER TABLE `support_ticket`
ADD COLUMN `teacher_id` INT(11) NULL DEFAULT NULL AFTER `id`;

ALTER TABLE `support_ticket`
ADD COLUMN `email_address` VARCHAR(45) NULL DEFAULT NULL AFTER `teacher_id`;
