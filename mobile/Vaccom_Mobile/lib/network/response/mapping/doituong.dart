import 'package:vaccom_mobile/model/base_item.dart';

class DoiTuong implements BaseItem {
  DoiTuong({
    this.id,
    this.code,
    this.name,
    this.parentID = -1,
  });

  int id, parentID;
  String code;
  String name;

  factory DoiTuong.fromJson(Map<String, dynamic> json) => DoiTuong(
    id: json["id"],
    code: json["doiTuongMa"],
    name: json["doiTuongMoTa"],
  );

  static List<DoiTuong> listFromJson(dynamic data) =>
      List<DoiTuong>.from(data.map((x) => DoiTuong.fromJson(x)));
}
