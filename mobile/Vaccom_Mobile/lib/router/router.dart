import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/pages/injector/injector.dart';
import 'package:vaccom_mobile/pages/injector/register/new_injector.dart';
import 'package:vaccom_mobile/pages/login/login_ui.dart';
import 'package:vaccom_mobile/pages/main/main_ui.dart';
import 'package:vaccom_mobile/pages/register_injection/register_injection_ui.dart';
import 'package:vaccom_mobile/pages/splash/splash_ui.dart';

final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

class GetArgument {
  static const injectUser = 'inject_user';
}

class GetRouter {
  static const root = '/';
  static const login = '/login';
  static const main = '/main';
  static const register_injection = '/register_injection';
  static const injector = '/injector';
  static const newInjector = '/new_injector';

  static List<GetPage> getPages() {
    return [
      GetPage(name: root, page: () => SplashPage()),
      GetPage(
        name: login,
        page: () => LoginPage(),
        transition: Transition.fade,
      ),
      GetPage(name: main, page: () => MainPage()),
      GetPage(name: register_injection, page: () => RegisterInjection()),
      GetPage(name: injector, page: () => InjectorPage()),
      GetPage(name: newInjector, page: () => NewInjectorPage()),
    ];
  }
}