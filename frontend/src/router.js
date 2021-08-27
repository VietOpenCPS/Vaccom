import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('@/views/dashboard/Index'),
      children: [
        // Dashboard
        {
          path: '',
          name: 'TrangChu',
          component: () => import('@/views/dashboard/pages/TrangChu'),
        },
        {
          path: '/pages/dang-ky-tiem-moi/:uid',
          name: 'DangKyTiemMoi',
          component: () => import('@/views/dashboard/pages/DangKyTiemMoi'),
          props: true,
        },
        {
          path: '/pages/danh-sach-dang-ky-tiem-moi',
          name: 'DanhSachDangKyTiemMoi',
          component: () => import('@/views/dashboard/pages/DanhSachDangKyTiemMoi'),
          props: true,
        },
        {
          path: '/pages/danh-sach-dang-ky-chinh-thuc',
          name: 'DanhSachDangKyChinhThuc',
          component: () => import('@/views/dashboard/pages/DanhSachDangKyChinhThuc'),
          props: true,
        },
        {
          name: 'Users',
          path: '/pages/nguoi-dung',
          component: () => import('@/views/dashboard/pages/NguoiDung'),
        },
        {
          name: 'DiaBanCoSo',
          path: '/pages/dia-ban-co-so',
          component: () => import('@/views/dashboard/pages/DiaBanCoSo'),
        },
        {
          name: 'CoSoYTe',
          path: '/pages/co-so-y-te',
          component: () => import('@/views/dashboard/pages/CoSoYTe'),
        },
        {
          name: 'LichTiemChung',
          path: '/pages/lich-tiem-chung',
          component: () => import('@/views/dashboard/pages/DanhSachLichTiem'),
        },
        {
          name: 'CaTiem',
          path: '/pages/lich-tiem-chung/:uid',
          component: () => import('@/views/dashboard/pages/CaTiem'),
          props: true,
        },
        {
          name: 'Dashboard',
          path: 'pages/dashboard',
          component: () => import('@/views/dashboard/Dashboard'),
        },
        {
          name: 'User Profile',
          path: 'pages/user',
          component: () => import('@/views/dashboard/pages/UserProfile'),
        },
        {
          name: 'HDSD',
          path: 'pages/huong-dan-su-dung',
          component: () => import('@/views/dashboard/pages/HuongDanSuDung'),
        },
        // Tables
        {
          name: 'Regular Tables',
          path: 'tables/regular-tables',
          component: () => import('@/views/dashboard/tables/RegularTables'),
        },
      ],
    },
    // Login
    {
      name: 'Login',
      path: '/login',
      component: () => import('@/views/dashboard/pages/Login'),
    },
  ],
})
