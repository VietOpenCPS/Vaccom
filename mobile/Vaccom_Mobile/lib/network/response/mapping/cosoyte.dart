import 'package:vaccom_mobile/model/base_item.dart';

class CoSoYTe implements BaseItem {
  CoSoYTe({
    this.id,
    this.code,
    this.name,
    this.tinhThanhMa,
    this.tinhThanhTen,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.phuongXaMa,
    this.phuongXaTen,
    this.diaChiCoSo,
    this.nguoiDaiDien,
    this.soDienThoai,
    this.maQr,
  });

  int id, parentID;
  String code;
  String name;
  String tinhThanhMa;
  String tinhThanhTen;
  String quanHuyenMa;
  String quanHuyenTen;
  String phuongXaMa;
  String phuongXaTen;
  String diaChiCoSo;
  String nguoiDaiDien;
  String soDienThoai;
  String maQr;

  factory CoSoYTe.fromJson(Map<String, dynamic> json) => CoSoYTe(
        id: json["id"],
        code: json["maCoSo"],
        name: json["tenCoSo"],
        tinhThanhMa: json["tinhThanhMa"],
        tinhThanhTen: json["tinhThanhTen"],
        quanHuyenMa: json["quanHuyenMa"],
        quanHuyenTen: json["quanHuyenTen"],
        phuongXaMa: json["phuongXaMa"],
        phuongXaTen: json["phuongXaTen"],
        diaChiCoSo: json["diaChiCoSo"],
        nguoiDaiDien: json["nguoiDaiDien"],
        soDienThoai: json["soDienThoai"],
        maQr: json["maQR"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "maCoSo": code,
        "tenCoSo": name,
        "tinhThanhMa": tinhThanhMa,
        "tinhThanhTen": tinhThanhTen,
        "quanHuyenMa": quanHuyenMa,
        "quanHuyenTen": quanHuyenTen,
        "phuongXaMa": phuongXaMa,
        "phuongXaTen": phuongXaTen,
        "diaChiCoSo": diaChiCoSo,
        "nguoiDaiDien": nguoiDaiDien,
        "soDienThoai": soDienThoai,
        "maQR": maQr,
      };

  static List<CoSoYTe> listFromJson(dynamic data) =>
      List<CoSoYTe>.from(data.map((x) => CoSoYTe.fromJson(x)));
}
