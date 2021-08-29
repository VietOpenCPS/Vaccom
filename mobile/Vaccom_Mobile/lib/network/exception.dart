class AppException implements Exception {
  final _message;
  final _prefix;

  AppException([this._message, this._prefix]);

  String toString() {
    return "$_prefix$_message";
  }
}

class FetchDataException extends AppException {
  FetchDataException([String message])
      : super(message, "");
}

class BadRequestException extends AppException {
  BadRequestException([String message]) : super(message, "Invalid request: ");
}

class UnauthorisedException extends AppException {
  UnauthorisedException([String message]) : super(message, "Unauthorised: ");
}

class InvalidInputException extends AppException {
  InvalidInputException([String message]) : super(message, r"Lỗi dữ liệu: ");
}

class ServerException extends AppException {
  ServerException([String message]) : super(message, '');
}

class NetworkException extends AppException {
  NetworkException([String message]) : super(r'Kết nối mạng không ổn định', '');
}

class ParsingError extends AppException {
  ParsingError() : super(r'Lỗi đọc dữ liệu', '');
}

class EmptyDataException extends AppException {
  EmptyDataException() : super(r'Không có dữ liệu', '');
}