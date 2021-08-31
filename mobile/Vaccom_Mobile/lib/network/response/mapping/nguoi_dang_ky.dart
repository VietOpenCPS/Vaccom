class NguoiDangKy {
  NguoiDangKy({
    this.id,
    this.hoVaTen,
    this.ngaySinh,
    this.gioiTinh,
    this.cmtcccd,
    this.nhomDoiTuong,
    this.donViCongTac,
    this.soDienThoai,
    this.email,
    this.soTheBhyt,
    this.diaChiNoiO,
    this.tinhThanhMa,
    this.tinhThanhTen,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.phuongXaMa,
    this.phuongXaTen,
    this.diaBanCoSoId,
    this.coSoYTeMa,
    this.coSoYTeTen,
    this.danTocMa,
    this.quocTichMa,
    this.tienSuDiUng,
    this.cacBenhLyDangMac,
    this.cacThuocDangDung,
    this.ghiChu,
    this.ngayDangKi,
    this.tinhTrangDangKi,
    this.kiemTraTrung,
    this.ketQuaKiemTra,
    this.maQr,
    this.muiTiemChung,
    this.phieuHenTiem,
  });

  int id;
  String hoVaTen;
  String ngaySinh;
  int gioiTinh;
  String cmtcccd;
  int nhomDoiTuong;
  String donViCongTac;
  String soDienThoai;
  String email;
  String soTheBhyt;
  String diaChiNoiO;
  String tinhThanhMa;
  String tinhThanhTen;
  String quanHuyenMa;
  String quanHuyenTen;
  String phuongXaMa;
  String phuongXaTen;
  int diaBanCoSoId;
  String coSoYTeMa;
  String coSoYTeTen;
  String danTocMa;
  String quocTichMa;
  String tienSuDiUng;
  String cacBenhLyDangMac;
  String cacThuocDangDung;
  String ghiChu;
  String ngayDangKi;
  int tinhTrangDangKi;
  int kiemTraTrung;
  String ketQuaKiemTra;
  String maQr;
  List<dynamic> muiTiemChung;
  List<dynamic> phieuHenTiem;

  factory NguoiDangKy.fromJson(Map<String, dynamic> json) => NguoiDangKy(
    id: json["id"],
    hoVaTen: json["hoVaTen"],
    ngaySinh: json["ngaySinh"],
    gioiTinh: json["gioiTinh"],
    cmtcccd: json["cmtcccd"],
    nhomDoiTuong: json["nhomDoiTuong"],
    donViCongTac: json["donViCongTac"],
    soDienThoai: json["soDienThoai"],
    email: json["email"],
    soTheBhyt: json["soTheBHYT"],
    diaChiNoiO: json["diaChiNoiO"],
    tinhThanhMa: json["tinhThanhMa"],
    tinhThanhTen: json["tinhThanhTen"],
    quanHuyenMa: json["quanHuyenMa"],
    quanHuyenTen: json["quanHuyenTen"],
    phuongXaMa: json["phuongXaMa"],
    phuongXaTen: json["phuongXaTen"],
    diaBanCoSoId: json["diaBanCoSoId"],
    coSoYTeMa: json["coSoYTeMa"],
    coSoYTeTen: json["coSoYTeTen"],
    danTocMa: json["danTocMa"],
    quocTichMa: json["quocTichMa"],
    tienSuDiUng: json["tienSuDiUng"],
    cacBenhLyDangMac: json["cacBenhLyDangMac"],
    cacThuocDangDung: json["cacThuocDangDung"],
    ghiChu: json["ghiChu"],
    ngayDangKi: json["ngayDangKi"],
    tinhTrangDangKi: json["tinhTrangDangKi"],
    kiemTraTrung: json["kiemTraTrung"],
    ketQuaKiemTra: json["ketQuaKiemTra"],
    maQr: json["maQR"],
    muiTiemChung: List<dynamic>.from(json["muiTiemChung"].map((x) => x)),
    phieuHenTiem: List<dynamic>.from(json["phieuHenTiem"].map((x) => x)),
  );
}
