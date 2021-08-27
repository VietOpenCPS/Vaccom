class CoSoYTe {
  CoSoYTe({
    this.id,
    this.maCoSo,
    this.tenCoSo,
    this.tinhThanhMa,
    this.tinhThanhTen,
    this.quanHuyenMa,
    this.quanHuyenTen,
    this.phuongXaMa,
    this.phuongXaTen,
    this.diaChiCoSo,
    this.nguoiDaiDien,
    this.soDienThoai,
    this.maQr,
  });

  int id;
  String maCoSo;
  String tenCoSo;
  String tinhThanhMa;
  String tinhThanhTen;
  String quanHuyenMa;
  String quanHuyenTen;
  String phuongXaMa;
  String phuongXaTen;
  String diaChiCoSo;
  String nguoiDaiDien;
  String soDienThoai;
  String maQr;

  factory CoSoYTe.fromJson(Map<String, dynamic> json) => CoSoYTe(
        id: json["id"],
        maCoSo: json["maCoSo"],
        tenCoSo: json["tenCoSo"],
        tinhThanhMa: json["tinhThanhMa"],
        tinhThanhTen: json["tinhThanhTen"],
        quanHuyenMa: json["quanHuyenMa"],
        quanHuyenTen: json["quanHuyenTen"],
        phuongXaMa: json["phuongXaMa"],
        phuongXaTen: json["phuongXaTen"],
        diaChiCoSo: json["diaChiCoSo"],
        nguoiDaiDien: json["nguoiDaiDien"],
        soDienThoai: json["soDienThoai"],
        maQr: json["maQR"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "maCoSo": maCoSo,
        "tenCoSo": tenCoSo,
        "tinhThanhMa": tinhThanhMa,
        "tinhThanhTen": tinhThanhTen,
        "quanHuyenMa": quanHuyenMa,
        "quanHuyenTen": quanHuyenTen,
        "phuongXaMa": phuongXaMa,
        "phuongXaTen": phuongXaTen,
        "diaChiCoSo": diaChiCoSo,
        "nguoiDaiDien": nguoiDaiDien,
        "soDienThoai": soDienThoai,
        "maQR": maQr,
      };

  static List<CoSoYTe> listFromJson(dynamic data) =>
      List<CoSoYTe>.from(data.map((x) => CoSoYTe.fromJson(x)));
}
