class QuanHuyen {
  QuanHuyen({
    this.id,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.tinhThanhId,
  });

  int id;
  String quanHuyenMa;
  String quanHuyenTen;
  int tinhThanhId;

  factory QuanHuyen.fromJson(Map<String, dynamic> json) => QuanHuyen(
    id: json["id"],
    quanHuyenMa: json["quanHuyenMa"],
    quanHuyenTen: json["quanHuyenTen"],
    tinhThanhId: json["tinhThanhID"],
  );

  static List<QuanHuyen> listFromJson(dynamic data) =>
      List<QuanHuyen>.from(data.map((x) => QuanHuyen.fromJson(x)));
}
