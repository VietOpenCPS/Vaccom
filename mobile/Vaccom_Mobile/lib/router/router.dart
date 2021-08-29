import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/pages/injector/injector.dart';
import 'package:vaccom_mobile/pages/injector/register/new_injector.dart';
import 'package:vaccom_mobile/pages/login/login_ui.dart';
import 'package:vaccom_mobile/pages/main/main_ui.dart';
import 'package:vaccom_mobile/pages/splash/splash_ui.dart';

final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

class GetRouter {
  static const root = '/';
  static const login = '/login';
  static const main = '/main';
  static const injector = '/injector';
  static const newInjector = '/new_injector';

  static List<GetPage> getPages() {
    return [
      GetPage(name: root, page: () => SplashPage()),
      GetPage(name: login, page: () => LoginPage()),
      GetPage(name: main, page: () => MainPage()),
      GetPage(name: injector, page: () => InjectorPage()),
      GetPage(name: newInjector, page: () => NewInjectorPage()),
    ];
  }
}