class QuocGia {
  QuocGia({
    this.id,
    this.quocGiaMa,
    this.quocGiaTen,
  });

  int id;
  String quocGiaMa;
  String quocGiaTen;

  factory QuocGia.fromJson(Map<String, dynamic> json) => QuocGia(
    id: json["id"],
    quocGiaMa: json["quocGiaMa"],
    quocGiaTen: json["quocGiaTen"],
  );

  static List<QuocGia> listFromJson(dynamic data) =>
      List<QuocGia>.from(data.map((x) => QuocGia.fromJson(x)));
}
