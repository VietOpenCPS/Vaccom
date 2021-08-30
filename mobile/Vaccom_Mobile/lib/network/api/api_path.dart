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
    name: r'Danh sách quận huyện theo tinhthanhId',
    path: r'app/get/quanhuyen/'+ '$id',
  );

  static ApiPath getPhuongXa(int id) => ApiPath(
    name: r'Danh sách phường xã theo quanhuyenId',
    path: r'app/get/phuongxa/'+ '$id',
  );

  static ApiPath getCSTY = ApiPath(
    name: r'Danh sách Cơ sở y tế',
    path: r'app/get/cosoyte',
  );

  static ApiPath getDiaBanCoSo(int id) => ApiPath(
    name: r'Danh sách địa bàn cơ sở theo cosoyteId',
    path: r'app/get/diabancoso?cosoyteid=' + '$id',
  );

  static ApiPath getDanToc = ApiPath(
    name: r'Danh sách dân tộc',
    path: r'app/get/dantoc',
  );

  static ApiPath getSubject = ApiPath(
    name: r'Danh sách đối tượng',
    path: r'app/get/doituong',
  );

  static ApiPath getCountry = ApiPath(
    name: r'Danh sách quốc gia',
    path: r'app/get/quocgia',
  );
}
