import 'package:flutter/material.dart';
import 'package:vaccom_mobile/network/response/response.dart';
import 'package:vaccom_mobile/view_model/base.dart';

class LoginViewModel extends BaseViewModel {
  final usernameCtrl = TextEditingController();
  final passwordCtrl = TextEditingController();

  ///
  String get username => usernameCtrl.text;
  String get password => passwordCtrl.text;

  bool get isValid => username.isNotEmpty && password.isNotEmpty;

  LoginViewModel() {
    usernameCtrl.text = r'';
    passwordCtrl.text = r'';
  }

  Future<VacToken> login() async {
    return api.securityToken(username: username, password: password);
  }

  Future<User> getUser(int userId) async {
    return api.getUser(userId: userId);
  }
}
