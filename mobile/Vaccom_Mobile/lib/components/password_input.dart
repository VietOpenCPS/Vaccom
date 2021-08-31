import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:vaccom_mobile/commons/color.dart';

class PasswordInput extends StatefulWidget {
  final TextEditingController controller;
  final String label;
  final TextStyle style;
  final Widget prefixIcon;
  final bool clearWhileEditing;
  final bool showPrefixIcon;
  final Stream<bool> invalid;
  final ValueChanged<String> onChanged;
  final TextInputType keyboardType;

  final TextInputAction textInputAction;
  final ValueChanged<String> onSubmitted;
  final FocusNode focusNode;

  const PasswordInput({
    @required this.controller,
    @required this.label,
    this.prefixIcon,
    this.clearWhileEditing = true,
    this.showPrefixIcon = true,
    this.invalid,
    this.style,
    this.onChanged,
    this.textInputAction = TextInputAction.next,
    this.focusNode,
    this.onSubmitted,
    this.keyboardType = TextInputType.text,
  });

  @override
  _PasswordInputState createState() => _PasswordInputState();
}

class _PasswordInputState extends State<PasswordInput> {
  FocusNode _focusNode;

  Color cursorColor = AppColor.main;

  bool isFocus = false;
  bool showHideSecure = false;

  bool obscureText = true;

  @override
  void initState() {
    super.initState();

    _focusNode = widget.focusNode ?? FocusNode();
    _focusNode.addListener(() {
      setState(() {
        isFocus = _focusNode.hasFocus;
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _focusNode.removeListener(() => null);
  }

  void changeObscureText() {
    setState(() {
      obscureText = !obscureText;
    });
  }

  void onChanged(String text) {
    bool noText = true;
    if (text.length > 0) {
      noText = false;
    }
    setState(() {
      showHideSecure = !noText;
    });
    if (widget.onChanged != null)
    widget.onChanged(text);
  }

  Widget prefixWidget() {
    if (widget.showPrefixIcon) {
      var color = isFocus ? AppColor.main : AppColor.border;

      Widget prefixIcon = Icon(Icons.lock_outline, size: 24, color: color);

      return Padding(
        padding: EdgeInsets.symmetric(horizontal: 10),
        child: prefixIcon,
      );
    } else {
      return SizedBox(width: 10);
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
                borderRadius: BorderRadius.circular(4),
              ),
              child: Row(
                children: [
                  prefixWidget(),
                  Expanded(
                    child: TextFormField(
                      controller: widget.controller,
                      focusNode: _focusNode,
                      style: widget.style ??
                          GoogleFonts.lato(
                            // color: AppColor.main,
                          ),
                      decoration: InputDecoration(
                        border: InputBorder.none,
                        contentPadding: EdgeInsets.zero,
                      ),
                      cursorColor: cursorColor,
                      keyboardType: widget.keyboardType,
                      obscureText: obscureText,
                      onChanged: onChanged,
                      onFieldSubmitted: widget.onSubmitted,
                      textInputAction: widget.textInputAction,
                    ),
                  ),
                  (showHideSecure)
                      ? IconButton(
                          onPressed: changeObscureText,
                          icon: Icon(
                            obscureText
                                ? Icons.visibility_off
                                : Icons.visibility,
                            color: Colors.grey[600],
                            size: 20,
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
            padding: EdgeInsets.symmetric(horizontal: 7),
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
