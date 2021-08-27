class DanToc {
  DanToc({
    this.id,
    this.danTocMa,
    this.danTocTen,
  });

  int id;
  String danTocMa;
  String danTocTen;

  factory DanToc.fromJson(Map<String, dynamic> json) => DanToc(
        id: json["id"],
        danTocMa: json["danTocMa"],
        danTocTen: json["danTocTen"],
      );

  static List<DanToc> listFromJson(dynamic data) =>
      List<DanToc>.from(data.map((x) => DanToc.fromJson(x)));
}
