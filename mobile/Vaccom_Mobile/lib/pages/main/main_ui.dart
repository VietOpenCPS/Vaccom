import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:vaccom_mobile/commons/color.dart';
import 'package:vaccom_mobile/commons/constants.dart';
import 'package:vaccom_mobile/commons/styles.dart';
import 'package:vaccom_mobile/commons/toast.dart';
import 'package:vaccom_mobile/commons/utils.dart';
import 'package:vaccom_mobile/components/gradient_view.dart';
import 'package:vaccom_mobile/model/menu_item.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/network/global.dart';
import 'package:vaccom_mobile/router/main_key.dart';
import 'package:vaccom_mobile/router/router.dart';
import 'main_item_view.dart';

class MainPage extends StatefulWidget {
  const MainPage({Key key}) : super(key: key);

  _MainPage createState() => _MainPage();
}

class _MainPage extends State<MainPage> with TickerProviderStateMixin {
  AnimationController animationController;
  bool isLoading = true;
  int _selectedIndex = 0;
  List<MenuItem> menuList = [];

  List<DrawerItemData> drawerData = [];

  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    animationController = AnimationController(
      duration: Duration(milliseconds: 1000),
      vsync: this,
    );

    // TODO: Hiển thị menu item theo tài khoản đăng nhập
    drawerData = DrawerItemData.items;
    menuList = buildMenuList();

    super.initState();
  }

  void _onItemTapped(int index) {
    setState(() => _selectedIndex = index);
  }

  buildMenuList() {
    return [
      MenuItem(
          title: r'Đối tượng đăng ký tiêm',
          icon:
              r'https://tiemchungcovid19.gov.vn/assets/portal/img/ic_register_people.svg',
          unit: r'lượt',
          value: r'5,795,421'),
      MenuItem(
          title: r'Số mũi tiêm hôm qua',
          icon:
              r'https://tiemchungcovid19.gov.vn/assets/portal/img/ic_injection.svg',
          unit: r'mũi',
          value: r'280,333'),
      MenuItem(
          title: r'Số mũi đã tiêm toàn quốc',
          icon:
              r'https://tiemchungcovid19.gov.vn/assets/portal/img/ic_injected_people.svg',
          unit: r'mũi',
          value: r'19,223,460'),
    ];
  }

  tapOnDrawerItem(DrawerItem item) {
    mainKey.currentState.openEndDrawer();
    switch (item) {
      case DrawerItem.duyetDanhSachDoiTuongTiemMoi:
        Get.toNamed(GetRouter.injector);
        break;
      default:
        Toast.show(text: item.toString());
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    final user = Global.shared.currentUser;

    final drawerWidget = Drawer(
      child: Column(
        children: [
          SizedBox(
            height: AppBar().preferredSize.height * 2.2,
            child: GradientView(
              child: DrawerHeader(
                margin: EdgeInsets.zero,
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    CircleAvatar(
                      child: Icon(
                        Icons.person_pin,
                        color: Colors.white,
                        size: 44,
                      ),
                      backgroundColor: Colors.transparent,
                    ),
                    SizedBox(width: 8),
                    Expanded(
                      child: Container(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(
                              '${user.hoVaTen}',
                              style: GoogleFonts.merriweather(
                                color: Colors.white,
                                fontSize: 16,
                              ),
                              maxLines: 1,
                            ),
                            RichText(
                              text: TextSpan(
                                text: '${user.tenDangNhap}',
                                style: GoogleFonts.openSans(
                                  color: AppColor.nearlyWhite,
                                  fontSize: 14,
                                  fontWeight: FontWeight.w300,
                                ),
                                children: <TextSpan>[],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
          Expanded(
            child: ListView(
              padding: EdgeInsets.symmetric(vertical: 8),
              children: ListTile.divideTiles(
                  context: context,
                  tiles: List.generate(
                    drawerData.length,
                    (int index) {
                      return ListTile(
                        title: Text(
                          drawerData[index].title,
                          style: GoogleFonts.roboto(),
                        ),
                        onTap: () => tapOnDrawerItem(drawerData[index].item),
                      );
                    },
                  )).toList(),
            ),
          ),
        ],
      ),
    );

    return WillPopScope(
      onWillPop: () async {
        return false;
      },
      child: Scaffold(
        key: mainKey,
        drawer: drawerWidget,
        appBar: Utils.gradientAppBar(
          child: AppBar(
            brightness: Brightness.dark,
            backgroundColor: Colors.transparent,
            title: Text(
              r'Dashboard',
              style: AppStyle.appBarTitle,
            ),
            actions: [
              IconButton(
                icon: Icon(
                  Icons.logout,
                  color: Colors.white,
                  size: 22,
                ),
                onPressed: () => Utils.showAwesomeDialog(
                  context,
                  title: 'confirm'.tr,
                  message: 'confirm_logout'.tr,
                  isDestructive: true,
                  dismissOnTouchOutside: true,
                  onPressedOK: () => Utils.logout(),
                ),
              )
            ],
          ),
        ),
        body: GridView(
          padding: EdgeInsets.all(24),
          physics: BouncingScrollPhysics(),
          scrollDirection: Axis.vertical,
          children: List.generate(
            menuList.length,
            (index) {
              var count = menuList.length;
              var animation = Tween(begin: 0.0, end: 1.0).animate(
                CurvedAnimation(
                  parent: animationController,
                  curve: Interval((1 / count) * index, 1.0,
                      curve: Curves.fastOutSlowIn),
                ),
              );
              animationController.forward();
              return MainItemView(
                item: menuList[index],
                index: index,
                animation: animation,
                animationController: animationController,
                callBack: () {},
              );
            },
          ),
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 1,
            mainAxisSpacing: 16.0,
            crossAxisSpacing: 24.0,
            childAspectRatio: 5,
          ),
        ),
        bottomNavigationBar: BottomNavigationBar(
          items: const <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: r'Checkin',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.history),
              label: r'Lịch sử tiêm',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.qr_code),
              label: 'QR của bạn',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.more),
              label: r'Thêm',
            ),
          ],
          currentIndex: _selectedIndex,
          unselectedItemColor: Colors.grey,
          selectedItemColor: AppColor.main,
          onTap: _onItemTapped,
        ),
        floatingActionButton: FloatingActionButton(
          child: Container(
            width: 60,
            height: 60,
            child: Icon(
              Icons.add,
              size: 30,
            ),
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              gradient: LinearGradient(colors: AppConstant.gradientColor),
            ),
          ),
          onPressed: () => Get.toNamed(GetRouter.newInjector),
        ),
      ),
    );
  }
}

class DrawerItemData {
  String title = '';
  Function onTap = () => {};
  DrawerItem item;

  DrawerItemData({this.title, this.onTap, this.item});

  static List<DrawerItemData> items = [
    DrawerItemData(
      title: r'Checkin y tế tại điểm tiêm chủng',
      item: DrawerItem.checkIn,
    ),
    DrawerItemData(
      title: r'Xem thông tin lịch sử tiêm chủng',
      item: DrawerItem.xemThongTinLichSu,
    ),
    DrawerItemData(
      title: r'Xác nhận lịch hẹn tiêm',
      item: DrawerItem.xacNhanLichHen,
    ),
    DrawerItemData(
      title: r'Cập nhật diễn biến sau tiêm',
      item: DrawerItem.capNhatDienBienSauTiem,
    ),
    DrawerItemData(
      title: r'Nhập đăng kí đối tượng tiêm mới',
      item: DrawerItem.nhapDangKyDoiTuongTiemMoi,
    ),
    DrawerItemData(
      title: r'Duyệt danh sách đối tượng tiêm mới',
      item: DrawerItem.duyetDanhSachDoiTuongTiemMoi,
    ),
    DrawerItemData(
      title: r'Tra cứu danh sách đối tượng chính thức',
      item: DrawerItem.traCuuDanhSachDoiTuongChinhThuc,
    ),
    DrawerItemData(
      title: r'Xác nhận danh sách gọi tiêm',
      item: DrawerItem.xacNhanDanhSachGoiTiem,
    ),
    DrawerItemData(
      title: r'Quản lý danh sách checkin',
      item: DrawerItem.quanLyDanhSachCheckIn,
    ),
    DrawerItemData(
      title: r'Nhập kết quả sau tiêm',
      item: DrawerItem.nhapKetQuaSauTiem,
    ),
  ];
}

enum DrawerItem {
  checkIn,
  xemThongTinLichSu,
  xacNhanLichHen,
  capNhatDienBienSauTiem,
  nhapDangKyDoiTuongTiemMoi,
  duyetDanhSachDoiTuongTiemMoi,
  traCuuDanhSachDoiTuongChinhThuc,
  xacNhanDanhSachGoiTiem,
  quanLyDanhSachCheckIn,
  nhapKetQuaSauTiem
}
