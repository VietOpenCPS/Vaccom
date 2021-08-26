import 'package:vaccom/src/Screen/widgets/home_menu.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {

  @override
  Widget build(BuildContext context) {
    print("build UI");
    return Scaffold(
      body: Container(
        constraints: BoxConstraints.expand(),
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Positioned(
              left: 0,
              top: 0,
              right: 0,
              child: Column(
                mainAxisSize: MainAxisSize.max,
                children: <Widget>[
                  AppBar(
                    backgroundColor: Colors.transparent,
                    elevation: 0.0,
                    title: Text(
                      "Taxi App",
                      style: TextStyle(color: Colors.black),
                    ),
                    leading: FlatButton(
                        onPressed: () {
                          print("click menu");
                        },
                        child: Image.asset("ic_menu.png")),
                    actions: <Widget>[Image.asset("ic_notify.png")],
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: 20, left: 20, right: 20),
                    child: Text(
                        " "
                    ),
                  ),
                ],
              ),
            ),
            Positioned(left: 20,
              right: 20,
              bottom: 40,
              height: 248,
              child: Text(
                  " "
              ),
            )
          ],
        ),
      ),
      drawer: Drawer(
        child: HomeMenu(),
      ),
    );
  }
}

