import 'dart:convert';

import 'package:vaccom_mobile/network/api/api.dart';
import 'package:vaccom_mobile/network/mock/mock.dart';
import 'package:vaccom_mobile/network/response/response.dart';

class MockAPI implements API {
  MockAPI._();

  static final shared = MockAPI._();

  @override
  ApiType apiType = ApiType.mock;

  Future<VacToken> securityToken({String username, String password}) async {
    var data = json.decode(mockToken);
    return VacToken.fromJson(data);
  }

  Future<User> getUser({int userId}) async {
    var data = json.decode(mockUser);
    return User.fromJson(data);
  }

  Future<List<TinhThanh>> getCity() async {
    var data = json.decode(mockTinhThanh);
    return TinhThanh.listFromJson(data);
  }

  Future<List<QuanHuyen>> getDistrict({int cityId}) async {
    var data = json.decode(mockQuanHuyen);
    return QuanHuyen.listFromJson(data);
  }

  Future<List<PhuongXa>> getPhuongXa({int districtId}) async {
    var data = json.decode(mockXaPhuong);
    return PhuongXa.listFromJson(data);
  }

  Future<InjectorPaging> getListNguoiTiemChung({Map<String, dynamic> params}) async{
    await Future.delayed(Duration(seconds: 1));
    var data = json.decode(mockNguoiTiemChung);
    return InjectorPaging.fromJson(data);
  }

  Future<List<CoSoYTe>> getCSYT() async {
    var data = json.decode(mockCoSoYte);
    return CoSoYTe.listFromJson(data);
  }

  Future<List<DiaBanCoSo>> getDiaBanCoSo(int cosoyteId) async {
    var data = json.decode(mockCoSoYte);
    return DiaBanCoSo.listFromJson(data);
  }

  Future<List<DanToc>> getDanToc() async {
    var data = json.decode(mockCoSoYte);
    return DanToc.listFromJson(data);
  }

  Future<List<DoiTuong>> getDoiTuong() async {
    var data = json.decode(mockCoSoYte);
    return DoiTuong.listFromJson(data);
  }

  Future<List<QuocGia>> getQuocGia() async {
    var data = json.decode(mockCoSoYte);
    return QuocGia.listFromJson(data);
  }
}
  