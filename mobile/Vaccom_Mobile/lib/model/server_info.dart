class ServerInfo {
  String title, desc, baseUrl, logoBanner;

  ServerInfo({
    this.title,
    this.desc,
    this.baseUrl,
    this.logoBanner,
  });

  static ServerInfo vaccom = ServerInfo(
      title: r'Vaccom',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'thachban.vaccom.vn:8080/rest/v1/',
      logoBanner: r'https://thachban.vaccom.vn/images/logo_banner.png');
}
