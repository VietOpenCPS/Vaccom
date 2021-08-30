import 'package:vaccom_mobile/model/base_item.dart';

class DiaBanCoSo implements BaseItem {
  DiaBanCoSo({
    this.id,
    this.name,
    this.code,
    this.tinhThanhTen,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.phuongXaMa,
    this.phuongXaTen,
    this.coSoYTeId,
  });

  int id, parentID;
  String name;
  String code;
  String tinhThanhTen;
  String quanHuyenMa;
  String quanHuyenTen;
  String phuongXaMa;
  String phuongXaTen;
  int coSoYTeId;

  factory DiaBanCoSo.fromJson(Map<String, dynamic> json) => DiaBanCoSo(
        id: json["id"],
        name: json["tenDiaBan"],
        code: json["tinhThanhMa"],
        tinhThanhTen: json["tinhThanhTen"],
        quanHuyenMa: json["quanHuyenMa"],
        quanHuyenTen: json["quanHuyenTen"],
        phuongXaMa: json["phuongXaMa"],
        phuongXaTen: json["phuongXaTen"],
        coSoYTeId: json["coSoYTeId"],
      );

  static List<DiaBanCoSo> listFromJson(dynamic data) =>
      List<DiaBanCoSo>.from(data.map((x) => DiaBanCoSo.fromJson(x)));
}
