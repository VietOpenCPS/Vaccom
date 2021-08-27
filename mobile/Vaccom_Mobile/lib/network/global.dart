import 'package:rxdart/rxdart.dart';

import 'response/mapping/user.dart';

class Global {
  static String _schema = 'https://';
  static String _host = r'thachban.vaccom.vn:8080';
  static const _version = '/rest/v1/';

  Global._();

  static final shared = Global._();

  String get authority => _host + _version;

  setUrl(String url) {
    _host = url;
  }

  String endpoint(String path) {
    return _schema + authority + (path.startsWith('/') ? path : ('/$path'));
  }

  String deviceToken = '';

  final appUser = BehaviorSubject<User>();
  User get currentUser => appUser.value;
  void saveUser(User user) => appUser.sink.add(user);

  void dispose() {
    appUser.close();
  }
}