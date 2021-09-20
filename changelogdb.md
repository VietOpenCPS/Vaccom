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

----------------------------------------------------------------------------------------------------------
#update 01/09/2021
ALTER TABLE `vaccom`. `t_diabancoso` ADD COLUMN `UyBanNhanDanID` bigint(20) DEFAULT 0;

CREATE TABLE IF NOT EXISTS `vaccom`.`t_uybannhandan` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenCoQuan` varchar(512) DEFAULT NULL,
  `MaDinhDanh` varchar(30) DEFAULT NULL,
  `UyBanNhanDanChaID` bigint(20) DEFAULT 0,
  `TinhThanhMa` varchar(30) DEFAULT NULL,
  `TinhThanhTen` varchar(128) DEFAULT NULL,
  `QuanHuyenMa` varchar(30) DEFAULT NULL,
  `QuanHuyenTen` varchar(128) DEFAULT NULL,
  `PhuongXaMa` varchar(30) DEFAULT NULL,
  `PhuongXaTen` varchar(128) DEFAULT NULL,
  `DiaChiHoatDong` text DEFAULT NULL,
  `NguoiDaiDien` varchar(256) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

ALTER TABLE `vaccom`.`t_cosoyte` ADD COLUMN  `CoSoYTeChaId` bigint(20) DEFAULT NULL;

ALTER TABLE `vaccom`.`t_cosoyte` ADD COLUMN  `UyBanNhanDanID` bigint(20) DEFAULT NULL;

ALTER TABLE `vaccom`.`t_phieuhentiem` ADD COLUMN  `GhiChuLyDo` text DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `t_giaydiduong` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MaQR` varchar(128) DEFAULT NULL,
  `HoVaTen` varchar(128) NOT NULL,
  `CMTCCCD` varchar(20) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `NoiCtTenCoQuan` varchar(512) DEFAULT NULL,
  `NoiCtDiaChi` varchar(512) DEFAULT NULL,
  `NoiCtTinhThanhMa` varchar(30) DEFAULT NULL,
  `NoiCtTinhThanhTen` varchar(128) DEFAULT NULL,
  `NoiCtQuanHuyenMa` varchar(30) DEFAULT NULL,
  `NoiCtQuanHuyenTen` varchar(128) DEFAULT NULL,
  `NoiCtPhuongXaMa` varchar(30) DEFAULT NULL,
  `NoiCtPhuongXaTen` varchar(128) DEFAULT NULL,
  `NoiODiaChi` text DEFAULT NULL,
  `NoiOTinhThanhMa` varchar(30) DEFAULT NULL,
  `NoiOTinhThanhTen` varchar(128) DEFAULT NULL,
  `NoiOQuanHuyenMa` varchar(30) DEFAULT NULL,
  `NoiOQuanHuyenTen` varchar(128) DEFAULT NULL,
  `NoiOPhuongXaMa` varchar(30) DEFAULT NULL,
  `NoiOPhuongXaTen` varchar(128) DEFAULT NULL,
  `LichLamViec` text DEFAULT NULL,
  `UyBanNhanDanID` bigint(20) DEFAULT 0,
  `NgayCap` varchar(8) DEFAULT NULL,
  `ThoiHan` varchar(8) DEFAULT NULL,
  `CheckSum` text DEFAULT NULL,
  `GhiChu` text DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;

create table IF NOT EXISTS t_hangchothongbao (

    `ID` bigint not null auto_increment,

    `LoaiThongBao` varchar(255),

    `isSent` bit,

    `isReady` bit,

    `payload` varchar(1024),

    `toEmail` varchar(255),

    `toTelNo` varchar(255),

    primary key (ID)

);


----------------------------------------------------------------------------------------------------------
#update 02/09/2021
ALTER TABLE `t_cosoyte` modify  `UyBanNhanDanID` bigint(20) DEFAULT 0;
ALTER TABLE `t_cosoyte` modify  `CoSoYTeChaId` bigint(20) DEFAULT 0;
update t_cosoyte set CoSoYTeChaId = 0;
update t_cosoyte set UyBanNhanDanID = 0;

----------------------------------------------------------------------------------------------------------
#update 03/09/2021
ALTER TABLE t_giaydiduong modify `NgayCap` varchar(20) DEFAULT NULL;
ALTER TABLE t_giaydiduong modify `ThoiHan` varchar(20) DEFAULT NULL;
----------------------------------------------------------------------------------------------------------
#update 08/09/2021
CREATE TABLE IF NOT EXISTS t_thuoc (
ID bigint(20) NOT NULL AUTO_INCREMENT,
TenThuoc varchar(256) NOT NULL,
MaThuoc varchar(128) DEFAULT NULL,
GhiChu varchar(512) DEFAULT NULL,
NgayNhap varchar(10) DEFAULT NULL,
NgayHetHan varchar(10) DEFAULT NULL,
Status tinyint(1) DEFAULT 0,
PRIMARY KEY (ID)
) ;

ALTER TABLE t_hangchothongbao ADD COLUMN  `UyBanNhanDanID` bigint(20) DEFAULT NULL;
ALTER TABLE t_uybannhandan ADD COLUMN  `NotificationConfig` varchar(2048) DEFAULT NULL;
----------------------------------------------------------------------------------------------------------
#update 09/09/2021
ALTER TABLE t_hangchothongbao MODIFY `UyBanNhanDanID` bigint(20) DEFAULT 0;
update t_hangchothongbao set UyBanNhanDanID = 0;

#update 10/09/2021
ALTER TABLE t_lichtiemchung ADD COLUMN  `UyBanNhanDanID` bigint(20) DEFAULT 0;
update t_lichtiemchung set UyBanNhanDanID = 0;
ALTER TABLE vaccom.t_lichtiemchung ADD COLUMN  `UyBanNhanDanID` bigint(20) DEFAULT 0;
ALTER TABLE vaccom.t_lichtiemchung ADD COLUMN  `TenCoSo` varchar(256) DEFAULT NULL;
ALTER TABLE vaccom.t_lichtiemchung ADD COLUMN  `GioHenTiem` varchar(6) DEFAULT NULL;
delete from t_nguoidung where chucdanh='citizen';
ALTER TABLE vaccom.t_hangchothongbao ADD COLUMN  `status` bigint(10) DEFAULT 0;

#update 11/09/2021
ALTER TABLE vaccom.t_hangchothongbao ADD COLUMN  `mappingKey` bigint(10) DEFAULT 0;
#update 13/09/2021
ALTER TABLE vaccom.t_hangchothongbao ADD COLUMN  `errorCodeZalo` bigint(3) DEFAULT 1;
ALTER TABLE vaccom.t_hangchothongbao ADD COLUMN  `createDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

#update 17/09/2021
create table IF NOT EXISTS t_congdan (
`ID` bigint not null auto_increment,
`HoVaTen` varchar(128) NOT NULL,
`NgaySinh` varchar(10) DEFAULT NULL,
`GioiTinh` tinyint(4) DEFAULT 0,
`DiaChiThuongTru` varchar(512) DEFAULT NULL,
`TinhThanh_Ma` varchar(30) DEFAULT NULL,
`TinhThanh_Ten` varchar(128) DEFAULT NULL,
`QuanHuyen_Ma` varchar(30) DEFAULT NULL,
`QuanHuyen_Ten` varchar(128) DEFAULT NULL,
`PhuongXa_Ma` varchar(30) DEFAULT NULL,
`PhuongXa_Ten` varchar(128) DEFAULT NULL,
`CMTCCCD` varchar(20) DEFAULT NULL,
`NoiCap` varchar(128) DEFAULT NULL,
`NgayCap` varchar(20) DEFAULT NULL,
`SoMuiTiem` tinyint(4) DEFAULT 0,
`SoDienThoai` varchar(20) DEFAULT NULL,
primary key (ID)
)
---
ALTER TABLE vaccom.t_nguoitiemchung ADD COLUMN  `CongDan_ID` bigint(20) DEFAULT 0;
ALTER TABLE vaccom.t_nguoitiemchung ADD COLUMN  `SoMuiTiem` tinyint(4) DEFAULT 0;
ALTER TABLE vaccom.t_nguoitiemchung ADD COLUMN  `NgayTiemCuoi` varchar(20) DEFAULT NULL;
---
ALTER TABLE vaccom.t_muitiemchung ADD COLUMN  `CongDan_ID` bigint(20) DEFAULT 0;
ALTER TABLE vaccom.t_nguoidung ADD COLUMN  `CongDan_ID` bigint(20) DEFAULT 0;
ALTER TABLE vaccom.t_giaydiduong ADD COLUMN  `CongDan_ID` bigint(20) DEFAULT 0;

ALTER TABLE vaccom.t_muitiemchung DROP COLUMN `NguoiTiemChungID`;





