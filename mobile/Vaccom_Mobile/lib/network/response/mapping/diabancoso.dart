class DiaBanCoSo {
  DiaBanCoSo({
    this.id,
    this.tenDiaBan,
    this.tinhThanhMa,
    this.tinhThanhTen,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.phuongXaMa,
    this.phuongXaTen,
    this.coSoYTeId,
  });

  int id;
  String tenDiaBan;
  String tinhThanhMa;
  String tinhThanhTen;
  String quanHuyenMa;
  String quanHuyenTen;
  String phuongXaMa;
  String phuongXaTen;
  int coSoYTeId;

  factory DiaBanCoSo.fromJson(Map<String, dynamic> json) => DiaBanCoSo(
        id: json["id"],
        tenDiaBan: json["tenDiaBan"],
        tinhThanhMa: json["tinhThanhMa"],
        tinhThanhTen: json["tinhThanhTen"],
        quanHuyenMa: json["quanHuyenMa"],
        quanHuyenTen: json["quanHuyenTen"],
        phuongXaMa: json["phuongXaMa"],
        phuongXaTen: json["phuongXaTen"],
        coSoYTeId: json["coSoYTeId"],
      );

  static List<DiaBanCoSo> listFromJson(dynamic data) =>
      List<DiaBanCoSo>.from(data.map((x) => DiaBanCoSo.fromJson(x)));
}
