import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:simple_logger/simple_logger.dart';
import 'package:vaccom_mobile/commons/constants.dart';
import 'package:vaccom_mobile/network/response/mapping/vac_token.dart';
import 'package:vaccom_mobile/router/router.dart';

/// LOGGER
final SimpleLogger logger = SimpleLogger()
  ..mode = LoggerMode.print
  ..setLevel(Level.FINEST, includeCallerInfo: true);

extension EmailValidator on String {
  bool get isValidEmail => GetUtils.isEmail(this);
}

class Utils {
  static hideKeyboard() => FocusManager.instance.primaryFocus.unfocus();

  static Future<void> revokeToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.remove(AppConstant.accessToken);
    prefs.remove(AppConstant.userId);
  }

  static Future<String> getAppToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String accessToken = prefs.getString(AppConstant.accessToken) ?? '';
    return accessToken;
  }

  static Future<int> getUserID() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getInt(AppConstant.userId);
  }

  static Future<void> saveToken(VacToken token) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString(AppConstant.accessToken, token.accessToken);
    prefs.setInt(AppConstant.userId, token.userId);
  }

  static void logout() {
    revokeToken();
    Get.offAllNamed(GetRouter.login);
  }

  static Widget getSvgItem(String path,
      {double width, double height, Color color}) {
    return SizedBox(
      width: width,
      height: height,
      child: SvgPicture.asset(path, color: color),
    );
  }

  static void showAwesomeDialog(
      BuildContext context, {
        String title = r'Xác nhận',
        String message = '',
        Widget child,
        String okTitle = r'Đồng ý',
        String cancelTitle = 'Bỏ qua',
        bool showCancelButton = true,
        bool isDestructive = false,
        Function onPressedOK,
        Function onPressCancel,
        bool dismissOnTouchOutside = false,
      }) {
    AwesomeDialog(
      context: context,
      dismissOnTouchOutside: dismissOnTouchOutside,
      animType: AnimType.SCALE,
      dialogType: DialogType.NO_HEADER,
      title: title,
      desc: message,
      btnOkOnPress: onPressedOK,
      btnOkText: okTitle,
      btnOkColor: isDestructive ? Colors.deepOrange : Colors.grey[300],
      btnCancel: showCancelButton
          ? AnimatedButton(
        isFixedHeight: false,
        pressEvent: () {
          Navigator.pop(context);
          if (onPressCancel != null) {
            onPressCancel();
          }
        },
        text: cancelTitle,
        color: Colors.grey[300],
        buttonTextStyle: TextStyle(
          color: Colors.black87,
        ),
      )
          : null,
    )..show();
  }
}

