class User {
  User({
    this.id,
    this.tenDangNhap,
    this.hoVaTen,
    this.chucDanh,
    this.soDienThoai,
    this.email,
    this.matKhau,
    this.diaBanCoSoId,
    this.coSoYTeId,
    this.nguoiTiemChungId,
    this.quanTriHeThong,
    this.khoaTaiKhoan,
    this.uyBanNhanDanId,
  });

  int id;
  String tenDangNhap, hoVaTen;
  String chucDanh, soDienThoai;
  String email, matKhau;
  int diaBanCoSoId, uyBanNhanDanId;
  int coSoYTeId, nguoiTiemChungId;
  int quanTriHeThong;
  bool khoaTaiKhoan;

  factory User.fromJson(Map<String, dynamic> json) => User(
        id: json["id"],
        tenDangNhap: json["tenDangNhap"],
        hoVaTen: json["hoVaTen"],
        chucDanh: json["chucDanh"],
        soDienThoai: json["soDienThoai"],
        email: json["email"],
        matKhau: json["matKhau"],
        diaBanCoSoId: json["diaBanCoSoId"],
        coSoYTeId: json["coSoYTeId"],
        nguoiTiemChungId: json["nguoiTiemChungId"],
        quanTriHeThong: json["quanTriHeThong"],
        khoaTaiKhoan: json["khoaTaiKhoan"],
        uyBanNhanDanId: json['uyBanNhanDanId'],
      );
}
