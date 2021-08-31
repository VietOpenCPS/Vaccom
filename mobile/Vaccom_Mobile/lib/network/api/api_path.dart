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
    path: r'app/get/nguoitiemchung'
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

  static ApiPath postThemNguoiDung = ApiPath(
    name: r'Thêm mới tài khoản',
    path: r'app/add/nguoidung',
  );

  static ApiPath putUpdateNguoiDung(int id) => ApiPath(
    name: r'Cập nhật tài khoản người dùng',
    path: r'app/update/nguoidung/' + '$id',
  );

  static ApiPath putDoiMatKhau(int id) => ApiPath(
    name: r'Đổi mật khẩu',
    path: r'app/changepwd/' + '$id',
  );

  static ApiPath putKhoaTaiKhoan(int id) => ApiPath(
    name: r'Khóa tài khoản',
    path: 'app/lock/nguoidung/$id/true',
  );

  static ApiPath postThemNguoiTiemChung = ApiPath(
    name: r'Thêm người tiêm chủng',
    path: r'app/add/nguoitiemchung',
  );

  static ApiPath putCapNhatNguoiTiemChung(int id) => ApiPath(
    name: r'Cập nhật người tiêm chủng',
    path: r'app/update/nguoitiemchung/' + '$id',
  );

  static ApiPath deleteNguoiTiemChung(int id) => ApiPath(
    name: r'Cập nhật người tiêm chủng',
    path: r'app/delete/nguoitiemchung/' + '$id',
  );

  static ApiPath getListMuiTiemChung(int cosoyteId) => ApiPath(
    name: r'Danh sách mũi tiêm chủng theo cơ sở y tế',
    path: r'app/get/muitiemchung/cosoyte/' + '$cosoyteId',
  );

  static ApiPath getLichTiemChung = ApiPath(
    name: r'Danh sách lịch tiêm chủng',
    path: r'app/get/lichtiemchung',
  );

  /// CoSoYTe
  static ApiPath postThemCoSoYTe = ApiPath(
    name: r'Thêm mới cơ sở y tế',
    path: r'app/add/cosoyte',
  );

  static ApiPath putUpdateCoSoYTe(int id) => ApiPath(
    name: r'Câp nhật cơ sở y tế',
    path: r'app/update/cosoyte/' + '$id',
  );

  static ApiPath deleteCoSoYTe(int id) => ApiPath(
    name: r'Xóa cơ sở y tế',
    path: r'app/delete/cosoyte/' + '$id',
  );

  /// ThemMuiTiemChung
  static ApiPath postThemMuiTiemChung = ApiPath(
    name: r'Thêm mũi tiêm chủng',
    path: r'app/add/muitiemchung',
  );

  static ApiPath putUpdateMuiTiemChung(int id) => ApiPath(
    name: r'Câp nhật thông tin mũi tiêm chủng',
    path: r'app/update/muitiemchung/' + '$id',
  );

  static ApiPath deleteMuiTiemChung(int id) => ApiPath(
    name: r'Xóa mũi tiêm chủng',
    path: r'app/delete/muitiemchung/' + '$id',
  );

  /// PhieuHenTiem
  static ApiPath postThemPhieuHenTiem = ApiPath(
    name: r'Thêm mới phiếu hẹn tiêm',
    path: r'app/add/phieuhentiem',
  );

  static ApiPath putUpdatePhieuHenTiem(int id) => ApiPath(
    name: r'Câp nhật phiếu hẹn tiêm',
    path: r'app/update/phieuhentiem/' + '$id',
  );

  static ApiPath deletePhieuHenTiem(int id) => ApiPath(
    name: r'Xóa phiếu hẹn tiêm',
    path: r'app/delete/phieuhentiem/' + '$id',
  );

  /// LichTiemChung
  static ApiPath postThemLichTiemChung = ApiPath(
    name: r'Thêm mới lịch tiêm chủng',
    path: r'app/add/lichtiemchung',
  );

  static ApiPath putUpdateLichTiemChung(int id) => ApiPath(
    name: r'Câp nhật lịch tiêm chủng',
    path: r'app/update/lichtiemchung/' + '$id',
  );

  static ApiPath deleteLichTiemChung(int id) => ApiPath(
    name: r'Xóa lịch tiêm chủng',
    path: r'app/delete/lichtiemchung/' + '$id',
  );

  /// DiaBanCoSo
  static ApiPath postThemDiaBanCoSo = ApiPath(
    name: r'Thêm mới địa bàn cơ sở',
    path: r'app/add/diabancoso',
  );

  static ApiPath putUpdateDiaBanCoSo(int id) => ApiPath(
    name: r'Câp nhật địa bàn cơ sở',
    path: r'app/update/diabancoso/' + '$id',
  );

  static ApiPath deleteDiaBanCoSo(int id) => ApiPath(
    name: r'Xóa địa bàn cơ sở',
    path: r'app/delete/diabancoso/' + '$id',
  );
}
