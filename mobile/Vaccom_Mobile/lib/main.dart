// import 'package:firebase_core/firebase_core.dart';
// import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/commons/language.dart';
// import 'package:vaccom_mobile/commons/utils.dart';
// import 'package:vaccom_mobile/network/global.dart';
import 'package:vaccom_mobile/router/router.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp();

  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
  ]);

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // final messaging = FirebaseMessaging.instance;

  @override
  StatelessElement createElement() {
    // fireBaseCloudMessagingListeners();
    return super.createElement();
  }

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      initialRoute: GetRouter.root,
      defaultTransition: Transition.native,
      translations: MultiLanguage(),
      locale: Locale('vi', 'VN'),
      debugShowCheckedModeBanner: false,
      getPages: GetRouter.getPages(),
    );
  }

  ///
  // void fireBaseCloudMessagingListeners() async {
  //   /// Update the iOS foreground notification presentation options to allow
  //   /// heads up notifications.
  //   await messaging.setForegroundNotificationPresentationOptions(
  //     alert: true,
  //     badge: true,
  //     sound: true,
  //   );
  //
  //   NotificationSettings settings = await messaging.requestPermission(
  //     alert: true,
  //     announcement: false,
  //     badge: true,
  //     carPlay: false,
  //     criticalAlert: false,
  //     provisional: false,
  //     sound: true,
  //   );
  //
  //   logger.info('User granted permission: ${settings.authorizationStatus}');
  //
  //   String token = await messaging.getToken();
  //   logger.info('token: $token');
  //
  //   Global.shared.deviceToken = token;
  //
  //   await messaging.subscribeToTopic('global');
  // }
}
