import 'package:vaccom_mobile/model/base_item.dart';

class TinhThanh implements BaseItem {
  TinhThanh({
    this.id,
    this.code,
    this.name,
    this.parentID = -1,
  });

  int id, parentID;
  String code;
  String name;

  factory TinhThanh.fromJson(Map<String, dynamic> json) => TinhThanh(
    id: json["id"],
    code: json["tinhThanhMa"],
    name: json["tinhThanhTen"],
  );

  static List<TinhThanh> listFromJson(dynamic data) =>
      List<TinhThanh>.from(data.map((x) => TinhThanh.fromJson(x)));
}
