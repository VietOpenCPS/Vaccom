import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/foundation.dart';
import 'dart:developer';

import 'package:vaccom/src/config/config.dart';



class UserLogin {
  final String email;
  final String phone;
  final String numbercard;

  UserLogin({this.email,  this.phone, this.numbercard});

  factory UserLogin.fromJson(Map<String, dynamic> json) {
    return UserLogin(
      email: json['email'],
      phone: json['phone'],
      numbercard: json['numbercard'],
    );
  }
}


Future<UserLogin> fetchInfo() async {
  Map<String, String> headers = {"Content-type": "application/json"};
  String json = '{"action": "InfoFirstRow", "module": "about", "language": "vi"}';
  final response = await http.post(API,headers: headers,body: json);

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.

    return UserLogin.fromJson(jsonDecode(response.body));
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to load userinfo');
  }
}
