part of api;

class ApiPath {
  String name, path;

  ApiPath({this.name, this.path});

  static ApiPath login = ApiPath(name: 'Login', path: 'security/login');

  static ApiPath getUser(int id) => ApiPath(
    name: r'Chi tiết người dùng',
    path: r'app/get/nguoidung/' + '$id',
  );
}
