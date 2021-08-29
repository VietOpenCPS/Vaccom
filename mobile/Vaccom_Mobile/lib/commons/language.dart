import 'package:get/get.dart';

// Change locale to English
// Get.updateLocale(Locale('en', 'UK'));

class MultiLanguage extends Translations {
  @override
  Map<String, Map<String, String>> get keys => {
    'vi': {
      'login': r'Đăng nhập',
      'register': r'Đăng ký',
      'username': r'Tên đăng nhập',
      'password': r'Mật khẩu',
      'forgot_password': r'Quên mật khẩu?',
      'cancel': r'Bỏ qua',
      'confirm': r'Xác nhận',
      'confirm_logout': r'Bạn có muốn đăng xuất không?',
    },
    'en': {
      'login': r'Login',
      'register': r'Register',
      'username': r'Username',
      'password': r'Password',
      'forgot_password': r'Forgot password?',
      'cancel': r'Cancel',
      'confirm': r'Confirm',
      'confirm_logout': r'Do you want to logout?',
    },
  };
}