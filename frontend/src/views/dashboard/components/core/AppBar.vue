<template>
<div>
  <v-app-bar
    v-if="breakpointName === 'xs' || breakpointName === 'sm'"
    id="app-bar"
    absolute
    app
    color="transparent"
    flat
    height="75"
  >
    <v-btn
      class="mr-3"
      elevation="1"
      fab
      small
      @click="setDrawer(!drawer)"
    >
      <v-icon v-if="value">
        mdi-view-quilt
      </v-icon>

      <v-icon v-else>
        mdi-dots-vertical
      </v-icon>
    </v-btn>

    <v-toolbar-title
      class="hidden-sm-and-down font-weight-light"
      v-text=""
    />

    <v-spacer />

    <div class="mx-3" />
    

    <v-menu v-if="isSigned" offset-y origin="center center" transition="scale-transition">
      <template #activator="{ on }">
        <v-btn slot="activator" class="ml-2" min-width="0" text v-on="on">
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>
      <v-list class="pa-0">
        <v-list-item
          to="/pages/user"
          rel="noopener"
        >
          <v-list-item-action class="mr-3">
            <v-icon color="#3f51b5">mdi-account</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Thông tin tài khoản</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          @click="logout"
          rel="noopener"
        >
          <v-list-item-action class="mr-3">
            <v-icon color="red">mdi-power</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Đăng xuất</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
  <div v-else id="header-desktop">
    <header id="banner">
        <div class="container layout wrap" style="padding-top: 5px;padding-bottom: 5px;padding-left: 10px;"> 
          <a href="" class="mLogo col-sm-4 py-0" style="text-decoration: none;display: block"> 
            <img style="width: auto;height: 52px;border-radius: 10px;" src="/vac/images/logo_banner.png">
            <div>
              <p class="ml-2 mb-2" style="font-size: 16px; color: #fff;">HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG</p>
            </div>
            
          </a>
          <div class="col-sm-8 text-right py-0" style="color:#ff6a00;">
            <nav class="sort-pages modify-pages mt-3" id="navigation"> 
                <ul aria-label="Site Pages" role="menubar" class="container">
                    <li v-for="(item, i) in items" :key="i" :class="indexTab == i ? 'selected' : ''" :id="'layout_'+i" role="presentation">
                      <v-menu
                        v-if="item.hasOwnProperty('childItems')"
                        open-on-hover
                        bottom
                        offset-y
                        :class="item['class']" :id="item['id']" :aria-labelledby="'layout_'+i" 
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <a
                            v-bind="attrs"
                            v-on="on"
                          >
                            <span> {{item.title}}</span>
                            <v-icon class="ml-2" color="#fff" size="22">
                              mdi-chevron-down
                            </v-icon>
                          </a>
                          
                        </template>

                        <v-list>
                          <template v-for="(item2, index) in item.childItems" >
                            <v-list-item
                              :key="index"
                              @click="redirectTo(item2, i)"
                            >
                              <v-list-item-title>
                                <span class="" style="font-weight: 500;"> {{item2.title}}</span>
                              </v-list-item-title>
                            </v-list-item>
                            <v-divider
                              v-if="index !== item.childItems.length - 1"
                              :key="index"
                              :inset="item.inset"
                            ></v-divider>
                          </template>

                          
                        </v-list>
                      </v-menu>
                      <a v-else @click="redirectTo(item, i)" :class="item['class']" :id="item['id']" :aria-labelledby="'layout_'+i" aria-haspopup="true"  href="javascript:;" role="menuitem">
                        <span> {{item.title}}</span>
                      </a>
                    </li>
                </ul>
            </nav>
          </div>
        </div>

        <v-menu v-if="isSigned && breakpointName !== 'xs' && breakpointName !== 'sm'" offset-y origin="center center" transition="scale-transition">
          <template v-slot:activator="{ on, attrs }">
            <v-chip
              style="height: 36px;position: absolute;top: 15px;right:5px;z-index: 2"
              v-bind="attrs"
              v-on="on"
              class="ma-2"
              color="primary"
              @click="showMenu = !showMenu"
            >
              <v-icon left size="24" class="mx-2">
                mdi-account-circle-outline
              </v-icon>
              {{userLogin['role_name']}}
              <v-icon class="ml-2" v-if="!showMenu" size="20" color="white" >mdi-chevron-up</v-icon>
              <v-icon class="ml-2" v-else size="20" color="white">mdi-chevron-down</v-icon>
            </v-chip>
          </template>
          <v-list class="pa-0">
            <v-list-item
              to="/pages/user"
              rel="noopener"
            >
              <v-list-item-action class="mr-3">
                <v-icon color="#3f51b5">mdi-account</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>Thông tin tài khoản</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item
              @click="logout"
              rel="noopener"
            >
              <v-list-item-action class="mr-3">
                <v-icon color="red">mdi-power</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>Đăng xuất</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-menu>
        <div class="btns" v-if="!isSigned"> 
          <a @click="goToLogin" href="javascript:;" class="login">Đăng nhập</a> 
        </div>
    </header>
  </div>
  
</div>
</template>

<script>
  // Components
  import { VHover, VListItem } from 'vuetify/lib'

  // Utilities
  import { mapState, mapMutations } from 'vuex'

  export default {
    name: 'DashboardCoreAppBar',

    components: {
      AppBarItem: {
        render (h) {
          return h(VHover, {
            scopedSlots: {
              default: ({ hover }) => {
                return h(VListItem, {
                  attrs: this.$attrs,
                  class: {
                    'black--text': !hover,
                    'white--text secondary elevation-12': hover,
                  },
                  props: {
                    activeClass: '',
                    dark: hover,
                    link: true,
                    ...this.$attrs,
                  },
                }, this.$slots.default)
              },
            },
          })
        },
      },
    },

    props: {
      value: {
        type: Boolean,
        default: false,
      },
    },

    data: () => ({
      showMenu: false,
      items: [],
      notifications: [
      ],
    }),
    created () {
      let vm = this
      if (vm.userLogin && vm.userLogin['role_name'] && vm.userLogin['role_name'] === 'admin') {
        vm.items = [
          {
            icon: 'mdi-home',
            title: 'Trang chủ',
            to: '/',
            class: 'home-tab',
            id: 'home-vacc'
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'Đăng ký tiêm',
            id: 'search',
            childItems: [
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Đăng ký mới',
                to: '/pages/dang-ky-tiem-moi/0',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Đăng ký chính thức',
                to: '/pages/danh-sach-dang-ky-chinh-thuc',
                id: 'search',
              }
            ]
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'Kế hoạch tiêm',
            id: 'search',
            childItems: [
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch tiêm chủng',
                to: '/pages/lich-tiem-chung',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch hẹn chờ xác nhận',
                to: '/pages/lich-hen-cho-xac-nhan',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch hẹn đã xác nhận',
                to: '/pages/lich-hen-da-xac-nhan',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Danh sách tiêm chủng',
                to: '/pages/danh-sach-tiem-chung',
                id: 'search',
              },
            ]
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'Quản trị',
            id: 'search',
            childItems: [
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Cơ sở y tế',
                to: '/pages/co-so-y-te',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Địa bàn cơ sở',
                to: '/pages/dia-ban-co-so',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Người dùng',
                to: '/pages/nguoi-dung',
                id: 'search',
              }
            ]
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'HDSD',
            to: '/pages/huong-dan-su-dung',
            id: 'hdsd',
          },
        ]
      } else {
        vm.items = [
          {
            icon: 'mdi-home',
            title: 'Trang chủ',
            to: '/',
            class: 'home-tab',
            id: 'home-vacc'
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'Đăng ký tiêm',
            id: 'search',
            childItems: [
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Đăng ký mới',
                to: '/pages/dang-ky-tiem-moi/0',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Đăng ký chính thức',
                to: '/pages/danh-sach-dang-ky-chinh-thuc',
                id: 'search',
              }
            ]
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'Kế hoạch tiêm',
            id: 'search',
            childItems: [
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch tiêm chủng',
                to: '/pages/lich-tiem-chung',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch hẹn chờ xác nhận',
                to: '/pages/lich-hen-cho-xac-nhan',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Lịch hẹn đã xác nhận',
                to: '/pages/lich-hen-da-xac-nhan',
                id: 'search',
              },
              {
                icon: 'mdi-shield-plus-outline',
                title: 'Danh sách tiêm chủng',
                to: '/pages/danh-sach-tiem-chung',
                id: 'search',
              },
            ]
          },
          {
            icon: 'mdi-shield-plus-outline',
            title: 'HDSD',
            to: '/pages/huong-dan-su-dung',
            id: 'hdsd',
          },
        ]
      }
      // let index = vm.items.findIndex(function (item) {
      //   return item.id === 'search'
      // })
      // vm.indexTab = index
    },
    mounted () {
      let vm = this
    },
    computed: {
      ...mapState(['drawer']),
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
      indexTab () {
        return this.$store.getters.getIndexTab
      }
    },
    watch: {
      userLogin (val) {
        let vm = this
      }
    },
    methods: {
      ...mapMutations({
        setDrawer: 'SET_DRAWER',
      }),
      logout () {
        let vm = this
        // vm.$store.dispatch('logout').then(() => {
        //   console.log('logoutSuccess')
          vm.$store.commit('SET_ISSIGNED', false)
          if (localStorage.getItem('user')) {
            localStorage.setItem('user', null)
          }
          vm.$cookies.set('Token','')
          vm.$router.push({ path: '/login' })
        // }).catch(() => {
        //   vm.$store.commit('SHOW_SNACKBAR', {
        //     show: true,
        //     text: 'Lỗi đăng xuất hệ thống',
        //     color: 'error',
        //   })
        // })
      },
      redirectTo (item, index) {
        this.$store.commit('SET_INDEXTAB', index)
        this.$router.push({ path: item.to })
      },
      goToLogin () {
        this.$router.push({ path: '/login' })
      }
    },
  }
</script>
<style lang="css">
  #header-desktop #banner {
      background: linear-gradient(65deg,#171cc2,#ff5200);
      padding: 0;
  }
  #header-desktop #navigation {
      /* background-color: #fd6c1d; */
      position: relative;
  }
  #header-desktop #banner .container, #header-desktop #navigation .container {
      padding-left: 0;
      padding-right: 0;
      /* max-width: 1300px; */
      margin: 0 auto;
  }
  #header-desktop #navigation ul {
      display: flex;
      list-style: none;
      margin-bottom: 0;
      overflow: hidden;
      margin: auto;
      padding: 0;
      justify-content: center;
  }
  #header-desktop #navigation ul {
      display: flex;
      list-style: none;
      margin-bottom: 0;
      overflow: hidden;
      margin: auto;
      padding: 0;
  }
  #header-desktop #navigation li.selected a {
      /* font-weight: bold; */
  }
  #header-desktop #navigation li:hover a, #header-desktop #navigation li.selected a {
      color: #fff;
  }
  #header-desktop #navigation li a {
      padding: 15px 15px;
      display: block;
      position: relative;
      text-transform: uppercase;
      text-decoration: none;
      color: #fff;
      font-size: 16px;
  }
  #header-desktop #navigation li:hover a:before, #header-desktop #navigation li.selected a:before {
      content: "";
      position: absolute;
      bottom: 4px;
      left: 0;
      width: 100%;
      height: 3px;
      background-color: #fff;
  }
  #header-desktop #banner .btns {
    position: absolute;
    right: 15px;
    top: 20px;
    z-index: 2;
  }
  #header-desktop #banner .btns a.login {
    background-image: url(/vac/images/icon-login-white.png);
  }
  #header-desktop #banner .btns a {
    display: inline-block;
    /* background-color: white; */
    background-color: transparent;
    padding: 6px 50px 6px 20px;
    /* color: #0072bc; */
    /* border: 2px solid #0072bc; */
    text-decoration: none;
    color: white;
    border: 2px solid white;
    border-radius: 50px;
    background-repeat: no-repeat;
    background-position: right -2px top -2px;
    background-size: auto 35px;
  }
  #register-btn {
    padding-left: 30px;
    padding-right: 30px;
    background: #fd6c1d;
    color: #FFF;
    padding: 15px 30px;
    text-transform: uppercase;
    border: none;
    line-height: 1.42857143;
    text-decoration: none;
    margin-left: 30px;
  }
</style>

