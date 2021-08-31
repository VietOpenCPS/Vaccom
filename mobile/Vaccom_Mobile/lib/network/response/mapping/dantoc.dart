import 'package:vaccom_mobile/model/base_item.dart';

class DanToc implements BaseItem {
  DanToc({
    this.id,
    this.code,
    this.name,
    this.parentID = -1,
  });

  int id, parentID;
  String code;
  String name;

  factory DanToc.fromJson(Map<String, dynamic> json) => DanToc(
        id: json["id"],
        code: json["danTocMa"],
        name: json["danTocTen"],
      );

  static List<DanToc> listFromJson(dynamic data) =>
      List<DanToc>.from(data.map((x) => DanToc.fromJson(x)));
}
