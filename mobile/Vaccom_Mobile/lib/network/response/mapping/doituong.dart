class DoiTuong {
  DoiTuong({
    this.id,
    this.doiTuongMa,
    this.doiTuongMoTa,
  });

  int id;
  String doiTuongMa;
  String doiTuongMoTa;

  factory DoiTuong.fromJson(Map<String, dynamic> json) => DoiTuong(
    id: json["id"],
    doiTuongMa: json["doiTuongMa"],
    doiTuongMoTa: json["doiTuongMoTa"],
  );

  static List<DoiTuong> listFromJson(dynamic data) =>
      List<DoiTuong>.from(data.map((x) => DoiTuong.fromJson(x)));
}
