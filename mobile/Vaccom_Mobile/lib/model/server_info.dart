class ServerInfo {
  String title, desc, baseUrl, logoBanner;

  ServerInfo({
    this.title,
    this.desc,
    this.baseUrl,
    this.logoBanner,
  });


  static ServerInfo vaccom = ServerInfo(
      title: r'thachban.vaccom.vn',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'thachban.vaccom.vn:8080/rest/v1/',
      logoBanner: r'https://thachban.vaccom.vn/images/logo_banner.png');
  static ServerInfo server_2 = ServerInfo(
      title: r'mobile.vaccom.vn',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'mobile.vaccom.vn:8080/rest/v1/',
      logoBanner: r'https://thachban.vaccom.vn/images/logo_banner.png');
}
