library api;

import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:vaccom_mobile/commons/utils.dart';
import 'package:vaccom_mobile/network/exception.dart';
import 'package:vaccom_mobile/network/global.dart';
import 'package:vaccom_mobile/network/response/mapping/phuongxa.dart';
import 'package:vaccom_mobile/network/response/mapping/user.dart';
import 'package:vaccom_mobile/network/response/response.dart';

export 'mock_api.dart';
export 'remote_api.dart';

part 'api_method.dart';

part 'api_path.dart';

enum ApiType { mock, remote }

abstract class API {
  ApiType apiType;

  Future<VacToken> securityToken({String username, String password});

  Future<User> getUser({int userId});

  Future<List<TinhThanh>> getCity();

  Future<List<QuanHuyen>> getDistrict({int cityId});

  Future<List<PhuongXa>> getPhuongXa({int districtId});

  Future<List<CoSoYTe>> getCSYT();

  Future<List<DiaBanCoSo>> getDiaBanCoSo(int cosoyteId);

  Future<List<DanToc>> getDanToc();

  Future<List<DoiTuong>> getDoiTuong();

  Future<List<QuocGia>> getQuocGia();

  Future<InjectorPaging> getListNguoiTiemChung({
    Map<String, dynamic> params,
  });
}
