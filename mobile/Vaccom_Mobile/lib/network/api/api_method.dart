part of api;

extension ApiMethod on API {
  static Future<bool> hasNetwork() async {
    try {
      final result = await InternetAddress.lookup('google.com');
      return result.isNotEmpty && result[0].rawAddress.isNotEmpty;
    } on SocketException catch (_) {
      return false;
    }
  }

  static String convertMapToQueryParameter(Map<String, dynamic> params) {
    List<String> result = [];
    params.forEach((key, value) {
      result.add("$key=$value");
    });
    return result.join('&');
  }

  /// GET
  static Future<dynamic> getData(
      ApiPath api, Map<String, dynamic> params) async {
    final isOnline = await hasNetwork();
    if (isOnline == false) {
      throw NetworkException();
    }

    logger.info('--[${api.name}] GET ${api.path} - params: $params');

    var accessToken = await Utils.getAppToken();

    Map<String, String> headers = {};
    if (accessToken.isNotEmpty) {
      headers[HttpHeaders.authorizationHeader] = 'Bearer $accessToken';
    }

    String endpoint = Global.shared.endpoint(api.path);
    if (params.isNotEmpty) {
      String queryString = ApiMethod.convertMapToQueryParameter(params);
      endpoint += '?$queryString';
    }

    try {
      var response = await http.get(Uri.parse(endpoint), headers: headers);
      var jsonData = processResponse(response);
      return jsonData;
    } catch (e) {
      throw e;
    }
  }

  /// POST
  static Future<dynamic> postData(
      ApiPath api, Map<String, dynamic> params) async {
    final isOnline = await hasNetwork();
    if (isOnline == false) {
      throw NetworkException();
    }

    logger.info('--[${api.name}] POST ${api.path} - params: $params');

    var accessToken = await Utils.getAppToken();

    Map<String, String> headers = {};
    if (accessToken.isNotEmpty) {
      headers[HttpHeaders.authorizationHeader] = 'Bearer $accessToken';
    }

    final uri = Uri.https(Global.shared.authority, api.path);

    try {
      var response = await http.post(
        uri,
        body: params,
        headers: headers,
      );
      var jsonData = processResponse(response);
      return jsonData;
    } catch (e) {
      throw e;
    }
  }

  /// TOKEN
  static Future<dynamic> getToken({String username, String password}) async {
    final isOnline = await hasNetwork();
    if (isOnline == false) {
      throw NetworkException();
    }

    String basicAuth =
        'Basic ' + base64Encode(utf8.encode('$username:$password'));
    logger.info(basicAuth);

    try {
      logger.info(Uri.parse(Global.shared.endpoint(ApiPath.login.path)));
      var response = await http.post(
        Uri.parse(Global.shared.endpoint(ApiPath.login.path)),
        headers: {'authorization': basicAuth},
      );

      var responseJson = processResponse(response);
      logger.info(responseJson);
      return responseJson;
    } catch (e) {
      throw e;
    }
  }

  /// Renew TOKEN
  static Future<void> renewToken() async {
    final user = Global.shared.currentUser;
    final u = user.tenDangNhap;
    final p = user.matKhau;

    String basicAuth = 'Basic ' + base64Encode(utf8.encode('$u:$p'));
    logger.info(basicAuth);

    try {
      logger.info(Uri.parse(Global.shared.endpoint(ApiPath.login.path)));
      var response = await http.post(
        Uri.parse(Global.shared.endpoint(ApiPath.login.path)),
        headers: {'authorization': basicAuth},
      );

      var utf8Decode = utf8.decode(response.bodyBytes);
      var responseJson = json.decode(utf8Decode);
      final token = VacToken.fromJson(responseJson);
      Utils.saveToken(token);
    } catch (e) {
      throw e;
    }
  }

  ///
  static dynamic processResponse(http.Response response) {
    switch (response.statusCode) {
      case 200:
        var utf8Decode = utf8.decode(response.bodyBytes);
        var responseJson = json.decode(utf8Decode);
        logger.info(responseJson);
        return responseJson;
        break;

      case 401:
        renewToken();
        break;

      default:
        throw FetchDataException('Lỗi kết nối: ${response.statusCode}');
    }
  }
}

extension VacNetwork on API {
  /// GET
  static Future<dynamic> getData({String uri}) async {
    final isOnline = await ApiMethod.hasNetwork();
    if (isOnline == false) {
      throw NetworkException();
    }

    logger.info('--uri: $uri');
    HttpClient client = HttpClient()
      ..badCertificateCallback =
          ((X509Certificate cert, String host, int port) => true);

    try {
      final request = await client.getUrl(Uri.parse(uri));
      final response = await request.close();

      String reply = await response.transform(utf8.decoder).join();

      if (response.statusCode != 200) {
        throw FetchDataException('Lỗi kết nối: ${response.statusCode}');
      }

      return json.decode(reply);
    } catch (e) {
      logger.info(e.toString());
      throw FetchDataException('Lỗi kết nối');
    }
  }
}
