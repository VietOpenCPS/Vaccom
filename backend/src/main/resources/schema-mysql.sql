/*USE vaccom */;

CREATE TABLE IF NOT EXISTS `t_nguoidung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenDangNhap` varchar(64) NOT NULL,
  `HoVaTen` varchar(128) NOT NULL,
  `ChucDanh` varchar(64) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `MatKhau` varchar(512) NOT NULL,
  `DiaBanCoSoID` bigint(20) DEFAULT NULL,
  `CoSoYTeID` bigint(20) DEFAULT NULL,
  `QuanTriHeThong` boolean DEFAULT FALSE,
  `KhoaTaiKhoan` boolean DEFAULT FALSE,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_diabancoso` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenDiaBan` varchar(256) NOT NULL,
  `TinhThanhMa` varchar(5) NOT NULL,
  `TinhThanhTen` varchar(64) NOT NULL,
  `QuanHuyenMa` varchar(5) NOT NULL,
  `QuanHuyenTen` varchar(64) NOT NULL,
  `PhuongXaMa` varchar(5) NOT NULL,
  `PhuongXaTen` varchar(64) NOT NULL,
  `CoSoYTeId` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_cosoyte` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MaCoSo` varchar(5) NOT NULL,
  `TenCoSo` varchar(256) NOT NULL,
  `TinhThanhMa` varchar(5) NOT NULL,
  `TinhThanhTen` varchar(64) NOT NULL,
  `QuanHuyenMa` varchar(5) NOT NULL,
  `QuanHuyenTen` varchar(64) NOT NULL,
  `PhuongXaMa` varchar(5) NOT NULL,
  `PhuongXaTen` varchar(64) NOT NULL,
  `DiaChiCoSo` varchar(512) NOT NULL,
  `NguoiDaiDien` varchar(128) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_phieuhentiem` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LichTiemChungID` bigint(20) NOT NULL,
  `NguoiTiemChungID` bigint(20) NOT NULL,
  `MaPhieuHen` varchar(10) NOT NULL,
  `NgayHenTiem` varchar(10) NOT NULL,
  `GioHenTiem` varchar(6) NOT NULL,
  `TinhTrangXacNhan` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_lichtiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CoSoYTeID` bigint(20) NOT NULL,
  `TenDot` varchar(256) NOT NULL,
  `NgayBatDau` varchar(10) NOT NULL,
  `NgayKetThuc` varchar(10) NOT NULL,
  `DiaDiemTiemChung` varchar(512) NOT NULL,
  `LoaiThuocTiem` varchar(64) NOT NULL,
  `NoiSanXuat` varchar(64) DEFAULT NULL,
  `SoLoThuoc` varchar(64) DEFAULT NULL,
  `HanSuDung` varchar(10) NOT NULL,
  `TongSoMuiTiem` tinyint(4) NOT NULL,
  `TinhTrangLich` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_muitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(10) NOT NULL,
  `CMTCCCD` varchar(20) NOT NULL,
  `CoSoYTeMa` varchar(64) NOT NULL,
  `CoSoYTeTen` varchar(256) NOT NULL,
  `LanTiem` tinyint(4) NOT NULL,
  `NgayTiemChung` varchar(10) NOT NULL,
  `GioTiemChung` varchar(6) NOT NULL,
  `DiaDiemTiemChung` varchar(512) NOT NULL,
  `LoaiThuocTiem` varchar(64) NOT NULL,
  `NoiSanXuat` varchar(64) NOT NULL,
  `SoLoThuoc` varchar(64) NOT NULL,
  `HanSuDung` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_nguoitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(10) NOT NULL,
  `GioiTinh` tinyint(4) NOT NULL,
  `CMTCCCD` varchar(20) NOT NULL,
  `NgheNghiep` varchar(256) DEFAULT NULL,
  `NhomDoiTuong` tinyint(4) DEFAULT NULL,
  `DonViCongTac` varchar(256) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `MaSoBHXH` varchar(64) DEFAULT NULL,
  `SoTheBHYT` varchar(64) DEFAULT NULL,
  `DiaChiNoiO` varchar(512) NOT NULL,
  `TinhThanhMa` varchar(5) NOT NULL,
  `TinhThanhTen` varchar(64) NOT NULL,
  `QuanHuyenMa` varchar(5) NOT NULL,
  `QuanHuyenTen` varchar(64) NOT NULL,
  `PhuongXaMa` varchar(5) NOT NULL,
  `PhuongXaTen` varchar(64) NOT NULL,
  `DiaBanCoSoID` bigint(20) NOT NULL,
  `CoSoYTeMa` varchar(64) DEFAULT NULL,
  `CoSoYTeTen` varchar(256) DEFAULT NULL,
  `DanTocMa` varchar(2) NOT NULL,
  `QuocTichMa` varchar(3) NOT NULL,
  `TienSuDiUng` varchar(512) DEFAULT NULL,
  `CacBenhLyDangMac` varchar(512) DEFAULT NULL,
  `CacThuocDangDung` varchar(512) DEFAULT NULL,
  `GhiChu` varchar(512) DEFAULT NULL,
  `NgayDangKi` varchar(10) DEFAULT NULL,
  `TinhTrangDangKi` tinyint(4) DEFAULT NULL,
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
