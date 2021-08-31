import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class CustomCheckbox extends StatefulWidget {
  final bool value;
  final ValueChanged<bool> onChanged;
  final Color activeColor;
  final Color inactiveColor;
  final String activeText;
  final String inactiveText;
  final Color activeTextColor;
  final Color inactiveTextColor;
  final IconData activeIcon;
  final IconData inactiveIcon;

  const CustomCheckbox({
    Key key,
    this.value,
    this.onChanged,
    this.activeColor,
    this.inactiveColor = Colors.grey,
    this.activeText = r'Ghi nhớ tài khoản',
    this.inactiveText = r'Ghi nhớ tài khoản',
    this.activeTextColor = Colors.white70,
    this.inactiveTextColor = Colors.white70,
    this.activeIcon = Icons.check_box,
    this.inactiveIcon = Icons.check_box_outline_blank,
  }) : super(key: key);

  bool get isEmptyTitle => activeText.isEmpty;

  @override
  _CustomCheckboxState createState() => _CustomCheckboxState();
}

class _CustomCheckboxState extends State<CustomCheckbox> {
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => widget.onChanged(!widget.value),
      child: Container(
        // height: 30,
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Icon(
              widget.value ? widget.activeIcon : widget.inactiveIcon,
              color: widget.value ? widget.activeColor : widget.inactiveColor,
            ),
            SizedBox(
                width: widget.isEmptyTitle ? 0 : 5
            ),
            Expanded(
              child: Text(
                widget.value ? widget.activeText : widget.inactiveText,
                overflow: TextOverflow.visible,
                maxLines: 2,
                style: GoogleFonts.roboto(
                  fontWeight: widget.value ? FontWeight.w500 : FontWeight.normal,
                  fontSize: 15,
                  color: widget.value ? widget.activeColor : widget.inactiveColor,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
