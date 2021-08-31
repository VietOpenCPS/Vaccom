import 'package:vaccom_mobile/model/base_item.dart';

class QuocGia implements BaseItem {
  QuocGia({
    this.id,
    this.code,
    this.name,
    this.parentID = -1,
  });

  int id, parentID;
  String code;
  String name;

  factory QuocGia.fromJson(Map<String, dynamic> json) => QuocGia(
    id: json["id"],
    code: json["quocGiaMa"],
    name: json["quocGiaTen"],
  );

  static List<QuocGia> listFromJson(dynamic data) =>
      List<QuocGia>.from(data.map((x) => QuocGia.fromJson(x)));
}
