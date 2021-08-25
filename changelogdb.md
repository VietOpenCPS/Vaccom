#update 26/08/2021
ALTER TABLE `vaccom`.`t_catiemchung` CHANGE COLUMN `GioHenTien` `GioHenTiem` VARCHAR(6) NULL DEFAULT NULL ;

ALTER TABLE `vaccom`.`t_cosoyte` ADD COLUMN `MaQR` VARCHAR(128) DEFAULT NULL;

----------------------------------------------------------------------------------------------------------
