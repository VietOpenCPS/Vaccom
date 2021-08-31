import 'package:vaccom_mobile/model/base_item.dart';

class QuanHuyen implements BaseItem {
  QuanHuyen({
    this.id,
    this.code,
    this.name,
    this.parentID,
  });

  int id;
  String code;
  String name;
  int parentID;

  factory QuanHuyen.fromJson(Map<String, dynamic> json) => QuanHuyen(
    id: json["id"],
    code: json["quanHuyenMa"],
    name: json["quanHuyenTen"],
    parentID: json["tinhThanhID"],
  );

  static List<QuanHuyen> listFromJson(dynamic data) =>
      List<QuanHuyen>.from(data.map((x) => QuanHuyen.fromJson(x)));
}
