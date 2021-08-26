
import 'package:vaccom/src/auth/auth_bloc.dart';
import 'package:flutter/material.dart';

class MyApp extends InheritedWidget {
  final AuthBloc authBloc;
  final Widget child;
  MyApp(this.authBloc, this.child) : super(child: child);
  static MyApp of(BuildContext context) {
    final MyApp? result = context.dependOnInheritedWidgetOfExactType<MyApp>();
    assert(result != null, 'No MyApp found in context');
    return result!;

  }
  @override
  bool updateShouldNotify(InheritedWidget oldWidget) {
    return false;
  }


}