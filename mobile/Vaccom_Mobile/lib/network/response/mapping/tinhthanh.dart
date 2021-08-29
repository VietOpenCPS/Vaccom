import 'package:vaccom_mobile/model/base_item.dart';
import 'package:vaccom_mobile/components/custom_picker_input/custom_picker_input_controller.dart';
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

  static List<PickerItem> parsePicker(List<TinhThanh> data) =>
      List<PickerItem>.from(
        data.map(
          (x) => PickerItem(title: x.name, value: x.code),
        ),
      );
}
