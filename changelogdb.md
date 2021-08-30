#update 26/08/2021
ALTER TABLE `vaccom`.`t_catiemchung` CHANGE COLUMN `GioHenTien` `GioHenTiem` VARCHAR(6) NULL DEFAULT NULL ;

ALTER TABLE `vaccom`.`t_cosoyte` ADD COLUMN `MaQR` VARCHAR(128) DEFAULT NULL;

----------------------------------------------------------------------------------------------------------
#update 28/08/2021

ALTER TABLE `vaccom`.`t_nguoidung` CHANGE COLUMN `VaiTro` `QuanTriHeThong` TINYINT(1) NULL DEFAULT '0' ;

ALTER TABLE `vaccom`.`t_lichtiemchung` CHANGE COLUMN `TongSoMuiTiem` `TongSoMuiTiem` INT(8) NOT NULL ,CHANGE COLUMN `SoCaTiem` `SoCaTiem` INT(8) NOT NULL ,CHANGE COLUMN `SoMuiMotCa` `SoMuiMotCa` INT(8) NOT NULL ;

ALTER TABLE `vaccom`.`t_catiemchung` CHANGE COLUMN `SoMuiTiem` `SoMuiTiem` INT(8) NULL DEFAULT NULL ,CHANGE COLUMN `SoPhieuHen` `SoPhieuHen` INT(8) NOT NULL ;

ALTER TABLE `vaccom`.`t_nguoidung` ADD COLUMN `UyBanNhanDanID` BIGINT(20) NULL DEFAULT 0;

ALTER TABLE `vaccom`.`t_nguoitiemchung` ADD COLUMN `CoSoYTeID` BIGINT(20) NULL DEFAULT 0;

update t_nguoidung set QuanTriHeThong = 0 where QuanTriHeThong != 1;
