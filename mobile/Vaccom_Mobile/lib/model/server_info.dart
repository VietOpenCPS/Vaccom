class ServerInfo {
  String title, desc, baseUrl, logoBanner, protocol;

  ServerInfo({
    this.title,
    this.desc,
    this.baseUrl,
    this.logoBanner,
    this.protocol
  });

  static List<ServerInfo> all = [vaccom, thachban, mobile];

  static ServerInfo vaccom = ServerInfo(
      title: r'Máy chủ Vaccom',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'vaccom.vn:8080/rest/v1/',
      protocol: 'https',
      logoBanner: r'https://vaccom.vn/images/logo_banner.png');
  static ServerInfo thachban = ServerInfo(
      title: r'Máy chủ Phường Thạch Bàn',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'thachban.vaccom.vn:8080/rest/v1/',
      protocol: 'https',
      logoBanner: r'https://vaccom.vn/images/logo_banner.png');
  static ServerInfo mobile = ServerInfo(
      title: r'Máy chủ Mobile',
      desc: r'HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG',
      baseUrl: r'mobile.vaccom.vn:8080/rest/v1/',
      protocol: 'https',
      logoBanner: r'https://vaccom.vn/images/logo_banner.png');
}
