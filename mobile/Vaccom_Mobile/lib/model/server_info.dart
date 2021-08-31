class ServerInfo {
  String title, desc, baseUrl, logoBanner, protocol;

  ServerInfo({
    this.title,
    this.desc,
    this.baseUrl,
    this.logoBanner,
    this.protocol
  });

  static List<ServerInfo> all = [vaccom, mobile];

  static ServerInfo vaccom = ServerInfo(
      title: r'Vaccom',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'thachban.vaccom.vn:8080/rest/v1/',
      protocol: 'https',
      logoBanner: r'https://thachban.vaccom.vn/images/logo_banner.png');

  static ServerInfo mobile = ServerInfo(
      title: r'Mobile',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'mobile.vaccom.vn:8080/rest/v1/',
      protocol: 'https',
      logoBanner: r'https://thachban.vaccom.vn/images/logo_banner.png');
}
