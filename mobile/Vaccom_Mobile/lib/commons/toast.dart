import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:loading_indicator/loading_indicator.dart';

enum ToastGravity { bottom, center, top }

extension ToastPositionValue on ToastGravity {
  int get value {
    switch (this) {
      case ToastGravity.bottom:
        return 0;
      case ToastGravity.top:
        return 2;
      default:
        return 1;
    }
  }
}

class Toast {
  static const int LENGTH_SHORT = 1;
  static const int LENGTH_LONG = 2;

  static const int BOTTOM = 0;
  static const int CENTER = 1;
  static const int TOP = 2;

  static const int ERROR = -1;
  static const int NORMAL = 0;
  static const int SUCCESS = 1;

  static void showAndDismiss(
      {String text,
      int type = NORMAL,
      int duration = 2,
      int gravity = CENTER,
      Color backgroundColor = const Color(0xAA000000),
      Color textColor = Colors.white,
      double backgroundRadius = 5.0}) {
    ToastView.dismiss();
    ToastView.createView(
      text,
      type,
      duration,
      gravity,
      backgroundColor,
      textColor,
      backgroundRadius,
    );
  }

  static void show(
      {String text,
      int type = NORMAL,
      int duration = 2,
      int gravity = CENTER,
      Color backgroundColor = Colors.black87,
      Color textColor = Colors.white,
      double backgroundRadius = 5.0}) {
    ToastView.dismiss();
    ToastView.showOnView(
      text,
      type,
      duration,
      gravity,
      backgroundColor,
      textColor,
      backgroundRadius,
    );
  }

  static void showLoading(
      {String message = '',
      int gravity = CENTER,
      double size = 120,
      Color backgroundColor = const Color(0xAA000000),
      Color color = Colors.white,
      Indicator type = Indicator.ballClipRotateMultiple,
      double backgroundRadius = 5.0}) {
    ToastView.dismiss();
    ToastView.showLoading(
      message: message,
      gravity: gravity,
      backgroundRadius: backgroundRadius,
      background: backgroundColor,
      color: color,
      type: type,
      size: size,
    );
  }

  static void showLoadingWithLogo() {
    ToastView.dismiss();
    ToastView.showLoadingWithLogo();
  }

  static void dismiss() => ToastView.dismiss();
}

///----------------- TOAST VIEW ------------------------------------

class ToastView {
  static final ToastView _singleton = new ToastView._internal();

  factory ToastView() {
    return _singleton;
  }

  ToastView._internal();

  static OverlayState overlayState;
  static OverlayEntry overlayEntry;
  static bool _isVisible = false;

  static void createView(
    String msg,
    int type,
    int duration,
    int gravity,
    Color background,
    Color textColor,
    double backgroundRadius,
  ) async {
    overlayState = Overlay.of(Get.overlayContext); //Error
    overlayEntry = OverlayEntry(
      builder: (BuildContext context) => ToastWidget(
        widget: Container(
          width: MediaQuery.of(context).size.width,
          child: Container(
            alignment: Alignment.center,
            child: Container(
              margin: EdgeInsets.symmetric(horizontal: 32),
              padding: EdgeInsets.all(15.0),
              decoration: BoxDecoration(
                color: background,
                borderRadius: BorderRadius.circular(backgroundRadius),
              ),
              constraints: BoxConstraints(minHeight: 52, minWidth: 210),
              child: _buildContent(type, msg, textColor),
            ),
          ),
        ),
        gravity: gravity,
      ),
    );
    _isVisible = true;
    overlayState.insert(overlayEntry);
    await Future.delayed(
      Duration(seconds: duration == null ? Toast.LENGTH_SHORT : duration),
    );
    dismiss();
  }

  static void showOnView(
    String msg,
    int type,
    int duration,
    int gravity,
    Color background,
    Color textColor,
    double backgroundRadius,
  ) async {
    overlayState = Overlay.of(Get.overlayContext); //Error
    overlayEntry = OverlayEntry(
      builder: (BuildContext context) => ToastWidget(
        widget: GestureDetector(
          onTap: () => dismiss(),
          child: Container(
            width: MediaQuery.of(context).size.width,
            child: Container(
              alignment: Alignment.center,
              child: Container(
                margin: EdgeInsets.symmetric(horizontal: 32),
                padding: EdgeInsets.fromLTRB(12, 13, 12, 12),
                decoration: BoxDecoration(
                  color: background,
                  borderRadius: BorderRadius.circular(backgroundRadius),
                ),
                constraints: BoxConstraints(minHeight: 44, minWidth: 210),
                child: _buildContent(type, msg, textColor),
              ),
            ),
          ),
        ),
        gravity: gravity,
        onTapAround: () => dismiss(),
      ),
    );
    _isVisible = true;
    overlayState.insert(overlayEntry);
  }

  static Widget _buildContent(int type, String msg, Color textColor) {
    if (type == Toast.NORMAL) {
      return Text(
        msg,
        maxLines: 20,
        overflow: TextOverflow.ellipsis,
        textAlign: TextAlign.center,
        style: TextStyle(
          fontSize: 16,
          color: textColor,
          decoration: TextDecoration.none,
          fontWeight: FontWeight.normal,
        ),
      );
    } else {
      return Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Icon(
            type.icon,
            color: Colors.white,
          ),
          Padding(
            padding: EdgeInsets.only(top: 16.0),
          ),
          Text(
            msg,
            maxLines: 20,
            overflow: TextOverflow.ellipsis,
            style: TextStyle(
              fontSize: 16,
              color: textColor,
              decoration: TextDecoration.none,
              fontWeight: FontWeight.normal,
            ),
          )
        ],
      );
    }
  }

  static void showLoading(
      {String message = '',
      int gravity,
      Color background,
      Color color,
      double size,
      Indicator type,
      double backgroundRadius}) async {
    overlayState = Overlay.of(Get.overlayContext); //Error
    overlayEntry = OverlayEntry(
      builder: (BuildContext context) => ToastWidget(
        gravity: gravity,
        widget: Stack(
          children: [
            Container(
              color: Colors.black.withOpacity(0.3),
              width: double.infinity,
              height: double.infinity,
            ),
            Container(
              alignment: Alignment.center,
              child: Container(
                constraints: BoxConstraints(minHeight: 52, minWidth: 210),
                child: Center(
                  child: Container(
                    width: size,
                    height: size,
                    child: Stack(
                      children: <Widget>[
                        Align(
                          alignment: Alignment.center,
                          child: LoadingIndicator(
                            indicatorType: type,
                            colors: [color],
                          ),
                        ),
                        Align(
                          alignment: Alignment.center,
                          child: Text(
                            message ?? '',
                            textAlign: TextAlign.center,
                            maxLines: 1,
                            style: GoogleFonts.roboto(
                              color: Colors.white,
                              fontSize: 16,
                              decoration: TextDecoration.none,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            )
          ],
        ),
      ),
    );
    _isVisible = true;
    overlayState.insert(overlayEntry);
  }

  static void showLoadingWithLogo() async {
    overlayState = Overlay.of(Get.overlayContext); //Error
    overlayEntry = OverlayEntry(
      builder: (BuildContext context) => ToastWidget(
        gravity: Toast.CENTER,
        widget: Stack(
          children: [
            Container(
              color: Colors.black.withOpacity(0.3),
              width: double.infinity,
              height: double.infinity,
            ),
            Container(
              alignment: Alignment.center,
              child: SizedBox(
                width: 64,
                height: 64,
                child: Container(
                  padding: EdgeInsets.all(4),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(34),
                  ),
                  child: Stack(
                    children: [
                      // logo here
                      LoadingIndicator(
                        indicatorType: Indicator.circleStrokeSpin,
                      ),
                    ],
                  ),
                ),
              ),
            )
          ],
        ),
      ),
    );
    _isVisible = true;
    overlayState.insert(overlayEntry);
  }

  static dismiss() async {
    if (!_isVisible) {
      return;
    }
    _isVisible = false;
    overlayEntry?.remove();
  }
}

///
class ToastWidget extends StatelessWidget {
  ToastWidget({
    Key key,
    @required this.widget,
    @required this.gravity,
    this.onTapAround,
    this.background = Colors.transparent,
  }) : super(key: key);
  final Widget widget;
  final int gravity;
  final Function onTapAround;
  final Color background;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        GestureDetector(
          onTap: onTapAround,
          child: Container(
            color: background,
            width: double.infinity,
            height: double.infinity,
          ),
        ),
        Positioned(
          top: gravity == 2
              ? (50 + MediaQuery.of(context).viewInsets.top)
              : null,
          bottom: gravity == 0
              ? (50 + MediaQuery.of(context).viewInsets.bottom)
              : null,
          child: widget,
        ),
      ],
    );
  }
}

extension ToastType on int {
  IconData get icon {
    switch (this) {
      case Toast.SUCCESS:
        return Icons.check_circle;
      case Toast.ERROR:
        return Icons.error;
      default:
        return null;
    }
  }
}
