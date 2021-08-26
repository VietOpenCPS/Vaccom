
import 'package:vaccom/src/app.dart';
import 'package:vaccom/src/Screen/home_page.dart';
import 'package:vaccom/src/Screen/login_page.dart';
import 'package:flutter/material.dart';
import 'package:vaccom/src/auth/auth_bloc.dart';
void main() {
  runApp(MyApp(
      new AuthBloc(),
      MaterialApp(
        home: HomePage(),
      )
    )
  );
}