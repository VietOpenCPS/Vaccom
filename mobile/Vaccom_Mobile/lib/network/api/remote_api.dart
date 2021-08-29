import 'package:vaccom_mobile/network/api/api.dart';
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
}
