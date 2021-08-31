import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:vaccom_mobile/commons/color.dart';
import 'package:vaccom_mobile/commons/utils.dart';

enum CustomTextFieldType { username, password }

class CustomTextField extends StatefulWidget {
  final TextEditingController controller;
  final String label;
  final TextStyle style;
  final bool obscureText;
  final Widget prefixIcon;
  final bool clearWhileEditing;
  final Stream<bool> invalid;
  final ValueChanged<String> onChanged;
  final TextInputType keyboardType;
  final CustomTextFieldType type;

  final TextInputAction textInputAction;
  final ValueChanged<String> onSubmitted;

  const CustomTextField(
      {@required this.controller,
      @required this.label,
      this.obscureText = false,
      this.prefixIcon,
      this.clearWhileEditing = true,
      this.invalid,
      this.style,
      this.onChanged,
      this.type,
      this.textInputAction = TextInputAction.next,
      this.onSubmitted,
      this.keyboardType = TextInputType.text});

  @override
  _CustomTextFieldState createState() => _CustomTextFieldState();
}

class _CustomTextFieldState extends State<CustomTextField> {
  final FocusNode _focusNode = FocusNode();

  Color cursorColor = AppColor.main;

  bool isFocus = false;
  bool showClearBtn = false;

  @override
  void initState() {
    super.initState();
    _focusNode.addListener(() {
      setState(() {
        isFocus = _focusNode.hasFocus;
      });
    });

    widget.controller.selection = TextSelection.fromPosition(TextPosition(
      offset: widget.controller.text.length,
    ));
  }

  @override
  void dispose() {
    super.dispose();
    _focusNode.removeListener(() => null);
  }

  void clearText() {
    setState(() {
      showClearBtn = false;
      widget.controller.text = '';
    });
  }

  void onChanged(String text) {
    bool noText = true;
    if (text.length > 0) {
      noText = false;
    }
    setState(() {
      showClearBtn = !noText;
    });
    if (widget.onChanged != null)
    widget.onChanged(text);
  }

  Widget prefixWidget() {
    if (widget.type != null) {
      var color = isFocus ? AppColor.main : AppColor.border;

      Widget prefixIcon = SizedBox(width: 10);
      if (widget.type == CustomTextFieldType.username) {
        prefixIcon = Icon(Icons.person_outline, size: 24, color: color);
      } else if (widget.type == CustomTextFieldType.password) {
        prefixIcon = Icon(Icons.lock_outline, size: 24, color: color);
      }

      return Padding(
        padding: EdgeInsets.symmetric(horizontal: 10),
        child: prefixIcon,
      );
    } else {
      return widget.prefixIcon != null
          ? Padding(
              padding: EdgeInsets.symmetric(horizontal: 10),
              child: widget.prefixIcon,
            )
          : SizedBox(width: 10);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        StreamBuilder<bool>(
          stream: widget.invalid,
          builder: (ctx, snapshot) {
            bool invalid = false;
            if (snapshot.hasData) {
              invalid = snapshot.data;
            }

            return Container(
              margin: EdgeInsets.only(top: 8),
              decoration: BoxDecoration(
                border: Border.all(
                  color: invalid
                      ? AppColor.error
                      : isFocus
                          ? AppColor.main
                          : AppColor.border,
                ),
                borderRadius: BorderRadius.all(Radius.circular(4)),
              ),
              child: Row(
                children: [
                  prefixWidget(),
                  Expanded(
                    child: TextFormField(
                      controller: widget.controller,
                      focusNode: _focusNode,
                      style: widget.style ??
                          GoogleFonts.roboto(
                            // color: AppColor.main,
                          ),
                      decoration: InputDecoration(
                        border: InputBorder.none,
                        contentPadding: EdgeInsets.zero,
                      ),
                      cursorColor: cursorColor,
                      keyboardType: widget.keyboardType,
                      obscureText: widget.obscureText,
                      onChanged: onChanged,
                      onFieldSubmitted: widget.onSubmitted,
                      textInputAction: widget.textInputAction,
                    ),
                  ),
                  (widget.clearWhileEditing && showClearBtn)
                      ? IconButton(
                          onPressed: clearText,
                          icon: Utils.getSvgItem(
                            'assets/svg/ic_clear.svg',
                            width: 13,
                            height: 13,
                          ),
                        )
                      : SizedBox(),
                ],
              ),
            );
          },
        ),
        Positioned(
          left: 15,
          top: 0,
          child: Container(
            color: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 5),
            child: Text(
              widget.label,
              style: GoogleFonts.roboto(
                color: isFocus ? AppColor.main : Color(0xFF818084),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
