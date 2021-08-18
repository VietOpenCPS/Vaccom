/*USE vaccom */;

CREATE TABLE IF NOT EXISTS `t_nguoidung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TenDangNhap` varchar(64) NOT NULL,
  `HoVaTen` varchar(128) NOT NULL,
  `ChucDanh` varchar(64) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `MatKhau` varchar(512) NOT NULL,
  `DiaBanCoSo_ID` bigint(20) DEFAULT NULL,
  `CoSoYTe_ID` bigint(20) DEFAULT NULL,
  `QuanTriHeThong` boolean DEFAULT FALSE,
  `KhoaTaiKhoan` boolean DEFAULT FALSE,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_diabancoso` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TinhThanh_Ma` varchar(5) NOT NULL,
  `TinhThanh_Ten` varchar(64) NOT NULL,
  `QuanHuyen_Ma` varchar(5) NOT NULL,
  `QuanHuyen_Ten` varchar(64) NOT NULL,
  `PhuongXa_Ma` varchar(5) NOT NULL,
  `PhuongXa_Ten` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_cosoyte` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MaCoSo` varchar(5) NOT NULL,
  `TenCoSo` varchar(256) NOT NULL,
  `TinhThanh_Ma` varchar(5) NOT NULL,
  `TinhThanh_Ten` varchar(64) NOT NULL,
  `QuanHuyen_Ma` varchar(5) NOT NULL,
  `QuanHuyen_Ten` tinyint(64) NOT NULL,
  `PhuongXa_Ma` varchar(5) NOT NULL,
  `PhuongXa_Ten` tinyint(64) NOT NULL,
  `DiaChiCoSo` varchar(512) NOT NULL,
  `NguoiDaiDien` varchar(128) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_phieuhentiem` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LichTiemChung_ID` bigint(20) NOT NULL,
  `NguoiTiemChung_ID` bigint(20) NOT NULL,
  `MaPhieuHen` varchar(10) NOT NULL,
  `NgayHenTiem` varchar(6) NOT NULL,
  `GioHenTiem` varchar(4) NOT NULL,
  `TinhTrangXacNhan` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_lichtiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CoSoYTe_ID` bigint(20) NOT NULL,
  `TenDot` varchar(256) NOT NULL,
  `NgayBatDau` varchar(6) NOT NULL,
  `NgayKetThuc` varchar(6) NOT NULL,
  `DiaDiemTiemChung` varchar(512) NOT NULL,
  `LoaiThuocTiem` varchar(64) NOT NULL,
  `NoiSanXuat` varchar(64) DEFAULT NULL,
  `SoLoThuoc` varchar(64) DEFAULT NULL,
  `HanSuDung` varchar(6) NOT NULL,
  `TongSoMuiTiem` tinyint(4) NOT NULL,
  `TinhTrangLich` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_muitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(6) NOT NULL,
  `CMTCCCD` varchar(20) NOT NULL,
  `CoSoYTe_Ma` varchar(64) NOT NULL,
  `CoSoYTe_Ten` varchar(256) NOT NULL,
  `LanTiem` tinyint(4) NOT NULL,
  `NgayTiemChung` varchar(6) NOT NULL,
  `GioTiemChung` varchar(4) NOT NULL,
  `DiaDiemTiemChung` varchar(512) NOT NULL,
  `LoaiThuocTiem` varchar(64) NOT NULL,
  `NoiSanXuat` varchar(64) NOT NULL,
  `SoLoThuoc` varchar(64) NOT NULL,
  `HanSuDung` varchar(6) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_nguoitiemchung` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HoVaTen` varchar(128) NOT NULL,
  `NgaySinh` varchar(6) NOT NULL,
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
  `TinhThanh_Ma` varchar(5) NOT NULL,
  `TinhThanh_Ten` varchar(64) NOT NULL,
  `QuanHuyen_Ma` varchar(5) NOT NULL,
  `QuanHuyen_Ten` tinyint(64) NOT NULL,
  `PhuongXa_Ma` varchar(5) NOT NULL,
  `PhuongXa_Ten` tinyint(64) NOT NULL,
  `DiaBanCoSo_ID` bigint(20) NOT NULL,
  `CoSoYTe_Ma` tinyint(64) DEFAULT NULL,
  `CoSoYTe_Ten` tinyint(64) DEFAULT NULL,
  `DanToc_Ma` varchar(2) NOT NULL,
  `QuocTich_Ma` varchar(3) NOT NULL,
  `TienSuDiUng` varchar(512) DEFAULT NULL,
  `CacBenhLyDangMac` varchar(512) DEFAULT NULL,
  `CacThuocDangDung` varchar(512) DEFAULT NULL,
  `GhiChu` varchar(512) DEFAULT NULL,
  `NgayDangKi` varchar(6) DEFAULT NULL,
  `TinhTrangDangKi` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_quocgia` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QuocGia_Ma` varchar(5) NOT NULL,
  `QuocGia_Ten` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_dantoc` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DanToc_Ma` varchar(5) NOT NULL,
  `DanToc_Ten` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_tinhthanh` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TinhThanh_Ma` varchar(5) NOT NULL,
  `TinhThanh_Ten` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_quanhuyen` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QuanHuyen_Ma` varchar(5) NOT NULL,
  `QuanHuyen_Ten` varchar(64) NOT NULL,
  `TinhThanh_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `t_phuongxa` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PhuongXa_Ma` varchar(5) NOT NULL,
  `PhuongXa_Ten` varchar(64) NOT NULL,
  `QuanHuyen_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ;

CREATE TABLE IF NOT EXISTS `vc_khoa` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NguoiDung_ID` bigint(20) NOT NULL,
  `KhoaCongKhai` varchar(128) NOT NULL,
  `KhoaBiMat` varchar(128) NOT NULL,
  `PhamVi` varchar(20) NOT NULL,
  `TrangThai` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;









