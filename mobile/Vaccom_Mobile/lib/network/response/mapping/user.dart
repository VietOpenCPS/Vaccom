import 'dart:math';

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
  bool quanTriHeThong;
  bool khoaTaiKhoan;

  int role = 0;

  VRoles get vRole {
    switch (role) {
      case 1: return VRoles.CAN_BO_DIA_BAN;
      case 2: return VRoles.CAN_BO_Y_TE;
      case 3: return VRoles.QUAN_TRI;
      default: return VRoles.NGUOI_DUNG;
    }
  }

  String get roleName {
    return vRole.toString().split('.').last;
  }

  factory User.fromJson(Map<String, dynamic> json) {
    String qtht = json["quanTriHeThong"].toString().toLowerCase();
    bool isQuantri = qtht == '1' || qtht == 'true';

    var user = User(
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
      quanTriHeThong: isQuantri,
      khoaTaiKhoan: json["khoaTaiKhoan"],
      uyBanNhanDanId: json['uyBanNhanDanId'],
    );

    user.role = Random().nextInt(VRoles.QUAN_TRI.index);

    return user;
  }
}

enum VRoles {
  NGUOI_DUNG,
  CAN_BO_DIA_BAN,
  CAN_BO_Y_TE,
  QUAN_TRI,
}
