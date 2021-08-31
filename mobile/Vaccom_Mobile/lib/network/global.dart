import 'package:rxdart/rxdart.dart';
import 'package:vaccom_mobile/model/server_info.dart';

import 'response/mapping/user.dart';

class Global {
  static String _schema = 'https';
  static String _baseUrl = r'thachban.vaccom.vn:8080/rest/v1/';

  Global() {
    setServer(_serverInfo);
  }

  static final shared = Global();

  String get authority => _baseUrl;

  ServerInfo _serverInfo = ServerInfo.vaccom;
  ServerInfo get server => _serverInfo;

  setServer(ServerInfo info) {
    _serverInfo = info;
    _baseUrl = info.baseUrl;
    _schema = info.protocol;
  }

  String endpoint(String path) {
    return _schema + '://' + _baseUrl + (path.startsWith('/') ? path : ('/$path'));
  }

  String deviceToken = '';

  final appUser = BehaviorSubject<User>();
  User get currentUser => appUser.value;
  void saveUser(User user) => appUser.sink.add(user);

  void dispose() {
    appUser.close();
  }
}