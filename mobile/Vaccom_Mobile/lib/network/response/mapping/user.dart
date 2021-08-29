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
  });

  int id;
  String tenDangNhap;
  String hoVaTen;
  String chucDanh;
  String soDienThoai;
  String email;
  String matKhau;
  int diaBanCoSoId;
  int coSoYTeId;
  int nguoiTiemChungId;
  bool quanTriHeThong;
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
  );
}
