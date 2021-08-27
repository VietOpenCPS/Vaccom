import 'package:flutter/cupertino.dart';

enum MenuIndex { menu1, menu2, menu3 }

class MenuItem {
  String title, desc;
  IconData icon;
  MenuIndex index;

  MenuItem({
    this.title,
    this.desc = '',
    this.icon,
    this.index,
  });
}