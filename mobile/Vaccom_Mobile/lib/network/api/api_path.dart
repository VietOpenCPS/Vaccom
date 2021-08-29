part of api;

class ApiPath {
  String name, path;

  ApiPath({this.name, this.path});

  static ApiPath login = ApiPath(
    name: 'Login',
    path: 'security/login',
  );

  static ApiPath getUser(int id) => ApiPath(
        name: r'Chi tiết người dùng',
        path: r'app/get/nguoidung/' + '$id',
      );

  static ApiPath getListNguoiTiemChung = ApiPath(
    name: r'Danh sách người tiêm chủng',
    path: r'app/get/search/nguoitiemchung'
  );

  static ApiPath getCity = ApiPath(
    name: r'Danh sách tỉnh thành',
    path: r'app/get/tinhthanh',
  );

  static ApiPath getDistrict(int id) => ApiPath(
    name: r'Danh sách quận huyện',
    path: r'app/get/quanhuyen/'+ '$id',
  );

  static ApiPath getPhuongXa(int id) => ApiPath(
    name: r'Danh sách phường xã',
    path: r'app/get/phuongxa/'+ '$id',
  );
}
