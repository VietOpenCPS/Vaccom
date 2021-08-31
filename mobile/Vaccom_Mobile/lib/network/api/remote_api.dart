import 'package:vaccom_mobile/network/api/api.dart';
import 'package:vaccom_mobile/network/response/mapping/phuongxa.dart';
import 'package:vaccom_mobile/network/response/response.dart';

class RemoteAPI implements API {
  RemoteAPI._();

  static final shared = RemoteAPI._();

  @override
  ApiType apiType = ApiType.remote;

  Future<VacToken> securityToken({String username, String password}) async {
    try {
      var res = await ApiMethod.getToken(
        username: username,
        password: password,
      );
      return VacToken.fromJson(res);
    } catch (e) {
      throw e;
    }
  }

  Future<User> getUser({int userId}) async {
    final apiPath = ApiPath.getUser(userId);
    final Map<String, String> param = {};
    try {
      var res = await ApiMethod.getData(apiPath, param);
      return User.fromJson(res);
    } catch (e) {
      throw e;
    }
  }

  Future<List<TinhThanh>> getCity() async {
    var apiPath = ApiPath.getCity;
    final Map<String, String> param = {};
    try {
      var res = await ApiMethod.getData(apiPath, param);
      return TinhThanh.listFromJson(res);
    } catch (e) {
      throw e;
    }
  }

  Future<InjectorPaging> getListNguoiTiemChung({
    Map<String, dynamic> params,
  }) async {
    try {
      var data = await ApiMethod.getData(ApiPath.getListNguoiTiemChung, params);
      return InjectorPaging.fromJson(data);
    } catch (e) {
      throw e;
    }
  }

  Future<List<QuanHuyen>> getDistrict({int cityId}) async {
    final apiPath = ApiPath.getDistrict(cityId);
    final Map<String, String> param = {};
    try {
      var res = await ApiMethod.getData(apiPath, param);
      return QuanHuyen.listFromJson(res);
    } catch (e) {
      throw e;
    }
  }

  Future<List<PhuongXa>> getPhuongXa({int districtId}) async {
    final apiPath = ApiPath.getPhuongXa(districtId);
    final Map<String, String> param = {};
    try {
      var res = await ApiMethod.getData(apiPath, param);
      return PhuongXa.listFromJson(res);
    } catch (e) {
      throw e;
    }
  }

  Future<List<CoSoYTe>> getCSYT() async {
    final apiPath = ApiPath.getCSTY;
    final Map<String, String> param = {};
    try {
      var data = await ApiMethod.getData(apiPath, param);
      return CoSoYTe.listFromJson(data);
    } catch (e) {
      throw e;
    }
  }

  Future<List<DiaBanCoSo>> getDiaBanCoSo(int cosoyteId) async {
    final apiPath = ApiPath.getDiaBanCoSo(cosoyteId);
    final Map<String, dynamic> param = {};
    try {
      var data = await ApiMethod.getData(apiPath, param);
      return DiaBanCoSo.listFromJson(data['data']);
    } catch (e) {
      throw e;
    }
  }

  Future<List<DanToc>> getDanToc() async {
    final apiPath = ApiPath.getDanToc;
    final Map<String, String> param = {};
    try {
      var data = await ApiMethod.getData(apiPath, param);
      return DanToc.listFromJson(data);
    } catch (e) {
      throw e;
    }
  }

  Future<List<DoiTuong>> getDoiTuong() async {
    final apiPath = ApiPath.getSubject;
    final Map<String, String> param = {};
    try {
      var data = await ApiMethod.getData(apiPath, param);
      return DoiTuong.listFromJson(data);
    } catch (e) {
      throw e;
    }
  }

  Future<List<QuocGia>> getQuocGia() async {
    final apiPath = ApiPath.getCountry;
    final Map<String, String> param = {};
    try {
      var data = await ApiMethod.getData(apiPath, param);
      return QuocGia.listFromJson(data);
    } catch (e) {
      throw e;
    }
  }
}
