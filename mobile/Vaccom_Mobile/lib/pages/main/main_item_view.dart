import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:vaccom_mobile/commons/color.dart';
import 'package:vaccom_mobile/commons/utils.dart';
import 'package:vaccom_mobile/model/dashboard_item.dart';

class MainItemView extends StatelessWidget {
  const MainItemView({
    Key key,
    this.item,
    this.index,
    this.animationController,
    this.animation,
    this.callBack,
  }) : super(key: key);

  final int index;
  final DashboardItem item;
  final VoidCallback callBack;
  final AnimationController animationController;
  final Animation<dynamic> animation;

  @override
  Widget build(BuildContext context) {

    return AnimatedBuilder(
      animation: animationController,
      builder: (BuildContext context, Widget child) {
        return FadeTransition(
          opacity: animation,
          child: Transform(
            transform: Matrix4.translationValues(
                0.0, 50 * (1.0 - animation.value), 0.0),
            child: Container(
              decoration: BoxDecoration(
                color: AppColor.nearlyWhite,
                borderRadius: BorderRadius.all(Radius.circular(8.0)),
                boxShadow: <BoxShadow>[
                  BoxShadow(
                    color: AppColor.grey.withOpacity(0.3),
                    blurRadius: 3.0,
                  ),
                ],
              ),
              child: AspectRatio(
                aspectRatio: 1.5,
                child: ClipRRect(
                  borderRadius: BorderRadius.all(Radius.circular(4.0)),
                  child: Stack(
                    alignment: AlignmentDirectional.center,
                    children: <Widget>[
                      Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: <Widget>[
                          SizedBox(width: 10),
                          SizedBox(
                            height: 30,
                            child: Utils.getSvgItem(item.icon),
                          ),
                          SizedBox(width: 8),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Text(
                                  item.title,
                                  style: GoogleFonts.openSans(
                                    fontWeight: FontWeight.w600,
                                    fontSize: 15,
                                    color: AppColor.nearlyBlack,
                                  ),
                                ),
                                RichText(
                                  text: TextSpan(
                                    text: item.valueString,
                                    style: GoogleFonts.roboto(
                                      color: AppColor.nearlyBlack,
                                      fontSize: 20,
                                      fontWeight: FontWeight.bold,
                                    ),
                                    children: <TextSpan>[
                                      TextSpan(
                                        text: ' (${item.unit})',
                                        style: TextStyle(
                                          fontStyle: FontStyle.italic,
                                          fontWeight: FontWeight.w200,
                                          color: AppColor.nearlyBlack,
                                          fontSize: 12,
                                        ),
                                      ),
                                    ],
                                  ),
                                )
                              ],
                            ),
                          ),
                        ],
                      ),
                      Material(
                        color: Colors.transparent,
                        child: InkWell(
                          splashColor: Color(0xFF81c7f5).withAlpha(120),
                          highlightColor: Colors.transparent,
                          borderRadius: BorderRadius.all(Radius.circular(4.0)),
                          onTap: () {
                            callBack();
                          },
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}
