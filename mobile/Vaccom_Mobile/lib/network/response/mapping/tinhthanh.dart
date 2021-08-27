class TinhThanh {
  TinhThanh({
    this.id,
    this.tinhThanhMa,
    this.tinhThanhTen,
  });

  int id;
  String tinhThanhMa;
  String tinhThanhTen;

  factory TinhThanh.fromJson(Map<String, dynamic> json) => TinhThanh(
    id: json["id"],
    tinhThanhMa: json["tinhThanhMa"],
    tinhThanhTen: json["tinhThanhTen"],
  );

  static List<TinhThanh> listFromJson(dynamic data) =>
      List<TinhThanh>.from(data.map((x) => TinhThanh.fromJson(x)));
}
