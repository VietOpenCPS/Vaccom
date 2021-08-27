class BaseResponse<T> {
  int code;
  String message;
  T data;

  BaseResponse({this.code, this.message, this.data});

  BaseResponse.fromJson(Map<String, dynamic> json) {
    message = json['message'].toString();

    if (json.containsKey('code')) {
      code = int.tryParse(json['code'].toString());
    } else if (json.containsKey('status')) {
      code = int.tryParse(json['status'].toString());
    }

    if (json.containsKey('data')) {
      data = json['data'];
    }
  }

  bool get isSuccess => [200, 1].contains(code);

  String get debug => '\ncode: $code - message: $message - data: ${data.runtimeType}';
}