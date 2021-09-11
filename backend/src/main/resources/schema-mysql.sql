/*USE vaccom */;

CREATE TABLE IF NOT EXISTS `t_nguoidung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenDangNhap` varchar(64) NOT NULL,
  `HoVaTen` varchar(128) NOT NULL,
  `ChucDanh` varchar(64) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `MatKhau` varchar(512) NOT NULL,
  `DiaBanCoSoID` bigint(20) DEFAULT 0,
  `CoSoYTeID` bigint(20) DEFAULT 0,
  `VaiTro` tinyint(4) DEFAULT 5,
  `KhoaTaiKhoan` boolean DEFAULT FALSE,
  `NguoiTiemChungID` bigint(20) DEFAULT 0,
  `UyBanNhanDanID` bigint(20) DEFAULT 0,
  `QuanTriHeThong` tinyint(4) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_diabancoso` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenDiaBan` varchar(256) NOT NULL,
  `TinhThanhMa` varchar(5) DEFAULT NULL,
  `TinhThanhTen` varchar(64) DEFAULT NULL,
  `QuanHuyenMa` varchar(5) DEFAULT NULL,
  `QuanHuyenTen` varchar(64) DEFAULT NULL,
  `PhuongXaMa` varchar(5) DEFAULT NULL,
  `PhuongXaTen` varchar(64) DEFAULT NULL,
  `CoSoYTeId` bigint(20) NOT NULL,
  `UyBanNhanDanID` bigint(20) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;


CREATE TABLE IF NOT EXISTS `t_uybannhandan` (
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
  `NotificationConfig` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;


CREATE TABLE IF NOT EXISTS `t_cosoyte` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MaQR` varchar(128) NOT NULL,
  `MaCoSo` varchar(10) NOT NULL,
  `TenCoSo` varchar(256) NOT NULL,
  `TinhThanhMa` varchar(5) DEFAULT NULL,
  `TinhThanhTen` varchar(64) DEFAULT NULL,
  `QuanHuyenMa` varchar(5) DEFAULT NULL,
  `QuanHuyenTen` varchar(64) DEFAULT NULL,
  `PhuongXaMa` varchar(5) DEFAULT NULL,
  `PhuongXaTen` varchar(64) DEFAULT NULL,
  `DiaChiCoSo` varchar(512) DEFAULT NULL,
  `NguoiDaiDien` varchar(128) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `CoSoYTeChaId` bigint(20) DEFAULT 0,
  `UyBanNhanDanID` bigint(20) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_phieuhentiem` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MaQR` varchar(128) NOT NULL,
  `LichTiemChungID` bigint(20) NOT NULL,
  `CaTiemChungID` bigint(20) NOT NULL,
  `NguoiTiemChungID` bigint(20) NOT NULL,
  `LanTiem` tinyint(4) NOT NULL,
  `NgayHenTiem` varchar(10) DEFAULT NULL,
  `GioHenTiem` varchar(6) DEFAULT NULL,
  `TinhTrangXacNhan` tinyint(4) DEFAULT 0,
  `NgayCheckin` varchar(10) DEFAULT NULL,
  `ThongTinCheckin` longtext DEFAULT NULL,
  `GhiChuLyDo` text DEFAULT NULL,
  `GioDuocTiem` varchar(6) DEFAULT NULL,
  `TrieuChungSauTiem` varchar(2000) DEFAULT NULL,
  `DieuTriTrieuChung` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_lichtiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CoSoYTeID` bigint(20) NOT NULL,
  `MaDot` varchar(64) NOT NULL,
  `MaQR` varchar(128) NOT NULL,
  `NgayBatDau` varchar(10) DEFAULT NULL,
  `NgayKetThuc` varchar(10) DEFAULT NULL,
  `DiaDiemTiemChung` varchar(512) DEFAULT NULL,
  `LoaiThuocTiem` varchar(64) DEFAULT NULL,
  `NoiSanXuat` varchar(64) DEFAULT NULL,
  `SoLoThuoc` varchar(64) DEFAULT NULL,
  `HanSuDung` varchar(10) DEFAULT NULL,
  `TongSoMuiTiem` int(8) NOT NULL,
  `TinhTrangLich` tinyint(4) DEFAULT 0,
  `SoCaTiem` int(8) NOT NULL,
  `SoMuiMotCa` int(8) NOT NULL,
  `BacSiKham` varchar(256) DEFAULT NULL,
  `SoDienThoai` varchar(256) DEFAULT NULL,
  `UyBanNhanDanID` bigint(20) DEFAULT 0,
  `TenCoSo` varchar(256) DEFAULT NULL,
  `GioHenTiem` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_muitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(10) DEFAULT NULL,
  `CMTCCCD` varchar(20) DEFAULT NULL,
  `CoSoYTeMa` varchar(64) DEFAULT NULL,
  `CoSoYTeTen` varchar(256) DEFAULT NULL,
  `LanTiem` tinyint(4) NOT NULL,
  `NgayTiemChung` varchar(10) DEFAULT NULL,
  `GioTiemChung` varchar(6) DEFAULT NULL,
  `DiaDiemTiemChung` varchar(512) DEFAULT NULL,
  `LoaiThuocTiem` varchar(64) DEFAULT NULL,
  `NoiSanXuat` varchar(64) DEFAULT NULL,
  `SoLoThuoc` varchar(64) DEFAULT NULL,
  `HanSuDung` varchar(10) DEFAULT NULL,
  `CoSoYTeID` bigint(20) DEFAULT 0,
  `NguoiTiemChungID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_nguoitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(10) DEFAULT NULL,
  `GioiTinh` tinyint(4) DEFAULT 0,
  `CMTCCCD` varchar(20) DEFAULT NULL,
  `NhomDoiTuong` tinyint(4) DEFAULT 0,
  `DonViCongTac` varchar(256) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `SoTheBHYT` varchar(64) DEFAULT NULL,
  `DiaChiNoiO` varchar(512) DEFAULT NULL,
  `TinhThanhMa` varchar(5) DEFAULT NULL,
  `TinhThanhTen` varchar(64) DEFAULT NULL,
  `QuanHuyenMa` varchar(5) DEFAULT NULL,
  `QuanHuyenTen` varchar(64) DEFAULT NULL,
  `PhuongXaMa` varchar(5) DEFAULT NULL,
  `PhuongXaTen` varchar(64) DEFAULT NULL,
  `DiaBanCoSoID` bigint(20) DEFAULT 0,
  `CoSoYTeID` bigint(20) DEFAULT 0,
  `CoSoYTeMa` varchar(64) DEFAULT NULL,
  `CoSoYTeTen` varchar(256) DEFAULT NULL,
  `DanTocMa` varchar(2) DEFAULT NULL,
  `QuocTichMa` varchar(3) DEFAULT NULL,
  `TienSuDiUng` varchar(512) DEFAULT NULL,
  `CacBenhLyDangMac` varchar(512) DEFAULT NULL,
  `CacThuocDangDung` varchar(512) DEFAULT NULL,
  `GhiChu` varchar(512) DEFAULT NULL,
  `NgayDangKi` varchar(10) DEFAULT NULL,
  `TinhTrangDangKi` tinyint(4) DEFAULT 0,
  `MaQR` varchar(128) NOT NULL,
  `KiemTraTrung` tinyint(4) DEFAULT 0,
  `KetQuaKiemTra` longtext DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

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
  `NgayCap` varchar(20) DEFAULT NULL,
  `ThoiHan` varchar(20) DEFAULT NULL,
  `CheckSum` text DEFAULT NULL,
  `GhiChu` text DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_thuoc` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenThuoc` varchar(256) NOT NULL,
  `MaThuoc` varchar(128) DEFAULT NULL,
  `GhiChu` varchar(512) DEFAULT NULL,
  `NgayNhap` varchar(10) DEFAULT NULL,
  `NgayHetHan` varchar(10) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_catiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LichTiemChungID` bigint(20) NOT NULL,
  `STT` tinyint(4) NOT NULL,
  `NgayHenTiem` varchar(10) DEFAULT NULL,
  `GioHenTiem` varchar(6) DEFAULT NULL,
  `SoMuiTiem` int(8) DEFAULT NULL,
  `DiaBanCoSoID` bigint(20) NOT NULL,
  `SoPhieuHen` int(8) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_quocgia` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QuocGiaMa` varchar(5) NOT NULL,
  `QuocGiaTen` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_dantoc` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DanTocMa` varchar(5) NOT NULL,
  `DanTocTen` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_tinhthanh` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TinhThanhMa` varchar(5) NOT NULL,
  `TinhThanhTen` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_quanhuyen` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QuanHuyenMa` varchar(5) NOT NULL,
  `QuanHuyenTen` varchar(64) NOT NULL,
  `TinhThanhID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_phuongxa` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PhuongXaMa` varchar(5) NOT NULL,
  `PhuongXaTen` varchar(64) NOT NULL,
  `QuanHuyenID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_doituong` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DoiTuongMa` varchar(5) NOT NULL,
  `DoiTuongMoTa` varchar(1024) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `vc_khoadangky` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NguoiDungID` bigint(20) NOT NULL,
  `KhoaCongKhai` varchar(128) NOT NULL,
  `KhoaBiMat` varchar(128) NOT NULL,
  `PhamVi` varchar(20) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `vc_khoatruycap` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `KhoaTruyCap` varchar(1024) NOT NULL,
  `DoiTuong` varchar(128) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL,
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
    `UyBanNhanDanID` bigint(20) DEFAULT 0,
    `status` bigint(10) DEFAULT 0,
    `mappingKey` bigint(10) DEFAULT 0,
    primary key (ID)
);

