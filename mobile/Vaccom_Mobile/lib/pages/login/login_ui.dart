import 'dart:math';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:vaccom_mobile/commons/color.dart';
import 'package:vaccom_mobile/commons/toast.dart';
import 'package:vaccom_mobile/commons/utils.dart';
import 'package:vaccom_mobile/components/custom_text_field.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/components/password_input.dart';
import 'package:vaccom_mobile/model/server_info.dart';
import 'package:vaccom_mobile/network/global.dart';
import 'package:vaccom_mobile/pages/login/login_vm.dart';
import 'package:vaccom_mobile/router/router.dart';

class LoginPage extends StatefulWidget {
  const LoginPage();

  @override
  State<StatefulWidget> createState() => _LoginPage();
}

class _LoginPage extends State<LoginPage> {
  final viewModel = LoginViewModel();

  final FocusNode _focusNode = FocusNode();

  ServerInfo serverInfo = Global.shared.server;
  final vaccomBanner = Image.asset('assets/images/logo_banner.png');

  @override
  void dispose() {
    viewModel.dispose();
    super.dispose();
  }

  void onLogin() {
    Utils.hideKeyboard();

    if (!viewModel.isValid) {
      Toast.show(text: r'Vui lòng nhập đủ thông tin đăng nhập');
      return;
    }

    Toast.showLoading();

    viewModel.login().then((value) {
      Utils.saveToken(value);
      getUser(value.userId);
    }).catchError(
      (e) => Toast.show(text: r'Tên đăng nhập hoặc mật khẩu không đúng'),
    );
  }

  void getUser(int id) {
    viewModel.getUser(id).then((value) {
      Toast.dismiss();
      Global.shared.saveUser(value);
      Get.offAndToNamed(GetRouter.main);
    }).catchError((e) => Toast.show(text: e.toString()));
  }

  void onRegister() {
    Toast.show(text: 'register'.tr);
  }

  Future<List<ServerInfo>> getServerList() async {
    Toast.showLoading();
    await Future.delayed(Duration(milliseconds: 300));
    Toast.dismiss();
    return ServerInfo.all;
  }

  void selectServerInfo() async {
    final list = await getServerList();

    final server = await showDialog<ServerInfo>(
      context: context,
      builder: (ctx) {
        return SimpleDialog(
          title: Text(
            r'Chọn máy chủ',
            textAlign: TextAlign.center,
            style: GoogleFonts.merriweather(
              fontSize: 16,
              color: AppColor.main,
            ),
          ),
          children: ListTile.divideTiles(
            context: context,
            tiles: List.generate(
              list.length,
              (int index) {
                final item = list[index];
                return ListTile(
                  title: Text(
                    item.title,
                    style: GoogleFonts.roboto(),
                  ),
                  onTap: () => Navigator.pop(ctx, item),
                );
              },
            ),
          ).toList(),
        );
      },
    );

    if (server != null && server.title != serverInfo.title) {
      setState(() => serverInfo = server);
      Global.shared.setServer(server);
    }
  }

  @override
  Widget build(BuildContext context) {
    var loginWidth = min(320, MediaQuery.of(context).size.width - 24 * 2);
    double padding = (MediaQuery.of(context).size.width - loginWidth) / 2;

    var loginButton = Container(
      padding: EdgeInsets.only(top: 16),
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: EdgeInsets.fromLTRB(20.0, 12.0, 20.0, 12.0),
        disabledColor: Colors.blueGrey,
        hoverColor: Colors.lightBlue,
        color: AppColor.main,
        onPressed: onLogin,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.login, size: 20, color: Colors.white),
            SizedBox(width: 7),
            Text(
              'login'.tr,
              textAlign: TextAlign.center,
              style: GoogleFonts.merriweather(
                fontWeight: FontWeight.w600,
                fontSize: 14,
                color: Colors.white,
              ),
            ),
          ],
        ),
      ),
    );

    var registerButton = Container(
      padding: EdgeInsets.only(top: 5),
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: EdgeInsets.fromLTRB(20.0, 12.0, 20.0, 12.0),
        disabledColor: Colors.blueGrey,
        hoverColor: Colors.lightBlue,
        color: AppColor.main,
        onPressed: onRegister,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.app_registration, size: 20, color: Colors.white),
            SizedBox(width: 7),
            Text(
              'register'.tr,
              textAlign: TextAlign.center,
              style: GoogleFonts.merriweather(
                fontWeight: FontWeight.w600,
                fontSize: 14,
                color: Colors.white,
              ),
            ),
          ],
        ),
      ),
    );

    final header = Column(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        Container(
          margin: EdgeInsets.only(
            left: padding,
            right: padding,
            bottom: 30,
          ),
          padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(5),
              gradient: LinearGradient(
                colors: [Color(0xFF171cc2), Color(0xFFff5200)],
                begin: Alignment.centerLeft,
                end: Alignment.centerRight,
              )),
          child: Column(
            children: [
              Padding(
                padding: EdgeInsets.symmetric(
                  horizontal: 36,
                ),
                child: CachedNetworkImage(
                  imageUrl: serverInfo.logoBanner,
                  errorWidget: (_, e, o) => vaccomBanner,
                ),
              ),
              Container(
                alignment: Alignment.bottomCenter,
                child: Text(
                  serverInfo.desc,
                  textAlign: TextAlign.center,
                  style: GoogleFonts.merriweather(
                      color: AppColor.nearlyWhite,
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                      height: 1.65),
                ),
              )
            ],
          ),
        )
      ],
    );

    Widget serverInfoWidget() {
      return Container(
        margin: EdgeInsets.only(
          left: padding,
          right: padding,
        ),
        child: GestureDetector(
          onTap: selectServerInfo,
          child: Card(
            child: Padding(
              padding: EdgeInsets.fromLTRB(10, 8, 8, 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Expanded(
                    child: Text(
                      serverInfo.title ?? r'- Chọn máy chủ -',
                      style: GoogleFonts.roboto(
                        color: AppColor.main,
                        fontWeight: FontWeight.w600,
                      ),
                      overflow: TextOverflow.fade,
                    ),
                  ),
                  Icon(
                    Icons.arrow_drop_down,
                    color: AppColor.main,
                  ),
                ],
              ),
            ),
          ),
        ),
      );
    }

    return Scaffold(
      resizeToAvoidBottomInset: false,
      // resizeToAvoidBottomPadding: false,
      body: SingleChildScrollView(
        child: InkWell(
          onTap: () => Utils.hideKeyboard(),
          splashColor: Colors.transparent,
          highlightColor: Colors.transparent,
          child: Stack(
            children: [
              Container(
                height: Get.height,
                width: Get.width,
                child: Image.asset(
                  'assets/images/bg_active.png',
                  fit: BoxFit.cover,
                ),
              ),
              Container(
                height: Get.height,
                decoration: BoxDecoration(),
                child: Container(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Stack(
                        children: [
                          Container(
                            margin: EdgeInsets.only(
                                left: padding,
                                right: padding,
                                top: 80,
                                bottom: 80),
                            padding: EdgeInsets.all(16),
                            decoration: BoxDecoration(
                              color: Color(0xFFFEFEFE),
                              borderRadius: BorderRadius.circular(5),
                              border: Border.all(
                                color: Colors.white54.withOpacity(0.2),
                                width: 0.7,
                              ),
                              boxShadow: <BoxShadow>[
                                BoxShadow(
                                    color: Color(0xFF3A5160).withOpacity(0.3),
                                    blurRadius: 2.0),
                              ],
                            ),
                            child: Column(
                              children: <Widget>[
                                SizedBox(height: 56),
                                CustomTextField(
                                  controller: viewModel.usernameCtrl,
                                  label: 'username'.tr,
                                  type: CustomTextFieldType.username,
                                  onSubmitted: (_) => FocusScope.of(context)
                                      .requestFocus(_focusNode),
                                ),
                                SizedBox(height: 16),
                                PasswordInput(
                                  focusNode: _focusNode,
                                  controller: viewModel.passwordCtrl,
                                  label: 'password'.tr,
                                  onSubmitted: (_) => onLogin(),
                                ),
                                SizedBox(height: 7.0),
                                loginButton,
                                registerButton,
                              ],
                            ),
                          ),
                          Positioned(
                            child: Padding(
                              padding: const EdgeInsets.only(top: 10),
                              child: header,
                            ),
                            left: 16,
                            right: 16,
                            top: -10,
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                child: Padding(
                  padding: EdgeInsets.only(
                    bottom: 16 + MediaQuery.of(context).padding.bottom,
                  ),
                  child: serverInfoWidget(),
                ),
                left: 0,
                right: 0,
                bottom: 0,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
