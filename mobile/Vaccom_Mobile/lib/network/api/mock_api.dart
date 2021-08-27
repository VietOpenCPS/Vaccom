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
}