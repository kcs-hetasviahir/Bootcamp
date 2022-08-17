ALTER TABLE `teacher`
ADD COLUMN `district` double NULL DEFAULT NULL AFTER `last_updated_dttm`,
ADD COLUMN `taluka` double NULL DEFAULT NULL AFTER `district`,
ADD COLUMN `village` double NULL DEFAULT NULL AFTER `taluka`,
ADD COLUMN `ssa_district_code` double NULL DEFAULT NULL AFTER `village`,
ADD COLUMN `ssa_block_code` double NULL DEFAULT NULL AFTER `ssa_district_code`,
ADD COLUMN `ssa_school_code` double NULL DEFAULT NULL AFTER `ssa_block_code`;