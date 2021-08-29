import 'package:rxdart/rxdart.dart';
import 'package:vaccom_mobile/network/api/api.dart';
import 'package:vaccom_mobile/network/response/base.dart';

abstract class BaseViewModel {
  final BehaviorSubject<BaseResponse> _error = BehaviorSubject();

  Stream<dynamic> get errorStream => _error.stream;

  Function(dynamic) get errorChanged => _error.sink.add;

  final API api = MockAPI.shared;
  // final API api = RemoteAPI.shared;

  void dispose() {
    _error.close();
  }
}