import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:vaccom_mobile/commons/color.dart';
import 'package:vaccom_mobile/commons/styles.dart';
import 'package:vaccom_mobile/commons/utils.dart';
import 'package:vaccom_mobile/components/gradient_view.dart';
import 'package:vaccom_mobile/model/dashboard_item.dart';
import 'package:get/get.dart';
import 'package:vaccom_mobile/network/global.dart';
import 'package:vaccom_mobile/network/response/mapping/user.dart';
import 'package:vaccom_mobile/pages/main/main_vm.dart';
import 'package:vaccom_mobile/pages/sample/sample.dart';
import 'package:vaccom_mobile/router/main_key.dart';
import 'package:vaccom_mobile/router/router.dart';
import 'main_item_view.dart';

class MainPage extends StatefulWidget {
  const MainPage({Key key}) : super(key: key);

  _MainPage createState() => _MainPage();
}

class _MainPage extends State<MainPage> with TickerProviderStateMixin {
  AnimationController animationController;

  int _selectedIndex = 0;
  List<DashboardItem> dashboardData = [];
  List<DrawerItemData> drawerData = [];

  final _refreshCtrl = RefreshController(initialRefresh: false);
  final viewModel = MainViewModel();

  @override
  void initState() {
    animationController = AnimationController(
      duration: Duration(milliseconds: 1000),
      vsync: this,
    );

    // TODO: Hiển thị menu item theo tài khoản đăng nhập
    drawerData = DrawerItemData.items();

    super.initState();

    getDashboardPublicData();
  }

  void _onItemTapped(int index) {
    setState(() => _selectedIndex = index);
  }

  void tapOnDrawerItem(DrawerItem item) {
    mainKey.currentState.openEndDrawer();
    switch (item) {
      case DrawerItem.duyetDoiTuongTiemMoi:
        Get.toNamed(GetRouter.injector);
        break;
      case DrawerItem.nhapDoiTuongTiemMoi:
        Get.toNamed(GetRouter.register_injection);
        break;
      default:
        final title = item.toString().split('.').last;
        Get.to(() => SamplePage(title: title));
        break;
    }
  }

  void getDashboardPublicData() async {
    dashboardData.clear();
    var summary = await viewModel.getSummary();

    if (summary != null) {
      dashboardData.add(
        DashboardItem(
          title: r'Đối tượng đăng ký tiêm',
          icon: 'assets/svg/ic_register_people.svg',
          unit: r'lượt',
          value: summary.totalVaccinationRegistration,
        ),
      );

      ///
      var dashboard = await viewModel.getDashboard();

      if (dashboard != null) {
        dashboardData.addAll([
          DashboardItem(
            title: r'Số mũi tiêm hôm qua',
            icon: 'assets/svg/ic_injection.svg',
            unit: r'mũi',
            value: dashboard.totalPopulation,
          ),
          DashboardItem(
            title: r'Số mũi đã tiêm toàn quốc',
            icon: 'assets/svg/ic_injected_people.svg',
            unit: r'mũi',
            value: dashboard.objectInjection,
          ),
        ]);
        setState(() {
          _refreshCtrl.refreshCompleted();
        });
      }
    }
  }

  Widget dashboardWidget() {
    if (dashboardData.isEmpty) {
      return SizedBox.shrink();
    }

    final grid = GridView(
      padding: EdgeInsets.all(24),
      physics: BouncingScrollPhysics(),
      scrollDirection: Axis.vertical,
      children: List.generate(
        dashboardData.length,
        (index) {
          var count = dashboardData.length;
          var animation = Tween(begin: 0.0, end: 1.0).animate(
            CurvedAnimation(
              parent: animationController,
              curve: Interval((1 / count) * index, 1.0,
                  curve: Curves.fastOutSlowIn),
            ),
          );
          animationController.forward();
          return MainItemView(
            item: dashboardData[index],
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
    );

    return SmartRefresher(
      enablePullDown: true,
      enablePullUp: false,
      header: WaterDropHeader(
        complete: Utils.completeRefreshWidget,
      ),
      footer: CustomFooter(
        builder: (BuildContext context, LoadStatus mode) {
          return SizedBox();
        },
      ),
      controller: _refreshCtrl,
      onRefresh: getDashboardPublicData,
      child: grid,
    );
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
                                text: '${user.tenDangNhap} - ',
                                style: GoogleFonts.openSans(
                                  color: AppColor.nearlyWhite,
                                  fontSize: 14,
                                  fontWeight: FontWeight.w300,
                                ),
                                children: <TextSpan>[
                                  TextSpan(
                                    text: '${user.roleName}',
                                    style: GoogleFonts.openSans(
                                      color: Colors.limeAccent,
                                      fontSize: 12,
                                      fontWeight: FontWeight.w500,
                                    ),
                                  )
                                ],
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
                ),
              ).toList(),
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
        body: dashboardWidget(),
        bottomNavigationBar: BottomNavigationBar(
          items: VBottomBar.items(),
          currentIndex: _selectedIndex,
          unselectedItemColor: Colors.grey,
          selectedItemColor: AppColor.main,
          onTap: _onItemTapped,
          type: BottomNavigationBarType.fixed,
        ),
        // floatingActionButton: FloatingActionButton(
        //   child: Container(
        //     width: 60,
        //     height: 60,
        //     child: Icon(
        //       Icons.add,
        //       size: 30,
        //     ),
        //     decoration: BoxDecoration(
        //       shape: BoxShape.circle,
        //       gradient: LinearGradient(colors: AppConstant.gradientColor),
        //     ),
        //   ),
        //   onPressed: () => Get.toNamed(GetRouter.register_injection),
        // ),
      ),
    );
  }
}

class VBottomBar {
  static List<BottomNavigationBarItem> items() {
    final userLoggedIn = Global.shared.currentUser;
    List<BottomNavigationBarItem> items = [
      BottomNavigationBarItem(
        icon: Icon(Icons.library_add_check_outlined),
        label: r'Checkin',
      ),
    ];
    
    if (userLoggedIn.vRole == VRoles.NGUOI_DUNG) {
      items.addAll([
        BottomNavigationBarItem(
          icon: Icon(Icons.history),
          label: r'Lịch sử tiêm',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.qr_code),
          label: 'QR của bạn',
        ),
      ]);
    } else {
      items.addAll([
        BottomNavigationBarItem(
          icon: Icon(Icons.domain_verification),
          label: r'Chính thức',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.icecream),
          label: r'Tiêm chủng',
        ),
      ]);
    }

    items.add(BottomNavigationBarItem(
      icon: Icon(Icons.more),
      label: r'Thêm',
    ));

    return items;
  }
}

///
class DrawerItemData {
  String title = '';
  Function onTap = () => {};
  DrawerItem item;

  DrawerItemData({this.title, this.onTap, this.item});

  static DrawerItemData checkIn = DrawerItemData(
    title: r'Checkin y tế tại điểm tiêm chủng',
    item: DrawerItem.checkIn,
  );

  static DrawerItemData xemThongTinLichSu = DrawerItemData(
    title: r'Xem thông tin lịch sử tiêm chủng',
    item: DrawerItem.xemThongTinLichSu,
  );

  static DrawerItemData xacNhanLichHen = DrawerItemData(
    title: r'Xác nhận lịch hẹn tiêm',
    item: DrawerItem.xacNhanLichHen,
  );

  static DrawerItemData capNhatDienBienSauTiem = DrawerItemData(
    title: r'Cập nhật diễn biến sau tiêm',
    item: DrawerItem.capNhatDienBienSauTiem,
  );

  static DrawerItemData nhapDoiTuongTiemMoi = DrawerItemData(
    title: r'Nhập đăng kí đối tượng tiêm mới',
    item: DrawerItem.nhapDoiTuongTiemMoi,
  );

  static DrawerItemData duyetDoiTuongTiemMoi = DrawerItemData(
    title: r'Duyệt danh sách đối tượng tiêm mới',
    item: DrawerItem.duyetDoiTuongTiemMoi,
  );

  static DrawerItemData traCuuDoiTuongChinhThuc = DrawerItemData(
    title: r'Tra cứu danh sách đối tượng chính thức',
    item: DrawerItem.traCuuDoiTuongChinhThuc,
  );

  static DrawerItemData xacNhanDanhSachGoiTiem = DrawerItemData(
    title: r'Xác nhận danh sách gọi tiêm',
    item: DrawerItem.xacNhanDanhSachGoiTiem,
  );

  static DrawerItemData quanLyDanhSachCheckIn = DrawerItemData(
    title: r'Quản lý danh sách checkin',
    item: DrawerItem.quanLyDanhSachCheckIn,
  );

  static DrawerItemData nhapKetQuaSauTiem = DrawerItemData(
    title: r'Nhập kết quả sau tiêm',
    item: DrawerItem.nhapKetQuaSauTiem,
  );

  static List<DrawerItemData> items() {
    List<DrawerItemData> users = [
      checkIn,
      xemThongTinLichSu,
      xacNhanLichHen,
      capNhatDienBienSauTiem,
    ];

    List<DrawerItemData> canBoDiaBan = [
      nhapDoiTuongTiemMoi,
      duyetDoiTuongTiemMoi,
      traCuuDoiTuongChinhThuc,
      xacNhanDanhSachGoiTiem,
    ];

    List<DrawerItemData> canBoYTe = [
      quanLyDanhSachCheckIn,
      nhapKetQuaSauTiem,
    ];

    List<DrawerItemData> items = users;

    final userLoggedIn = Global.shared.currentUser;
    switch (userLoggedIn.vRole) {
      case VRoles.NGUOI_DUNG:
        break;
      case VRoles.CAN_BO_DIA_BAN:
        items = users + canBoDiaBan;
        break;
      case VRoles.CAN_BO_Y_TE:
      case VRoles.QUAN_TRI:
        items = users + canBoDiaBan + canBoYTe;
    }

    return items;
  }
}

enum DrawerItem {
  checkIn,
  xemThongTinLichSu,
  xacNhanLichHen,
  capNhatDienBienSauTiem,
  nhapDoiTuongTiemMoi,
  duyetDoiTuongTiemMoi,
  traCuuDoiTuongChinhThuc,
  xacNhanDanhSachGoiTiem,
  quanLyDanhSachCheckIn,
  nhapKetQuaSauTiem
}
