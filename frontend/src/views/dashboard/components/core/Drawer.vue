<template>
  <v-navigation-drawer
    id="core-navigation-drawer"
    v-model="drawer"
    :dark="barColor !== 'rgba(228, 226, 226, 1), rgba(255, 255, 255, 0.7)'"
    :expand-on-hover="expandOnHover"
    :right="$vuetify.rtl"
    src="/vac/images/bg-draw-2.jpg"
    mobile-breakpoint="960"
    app
    :width="breakpointName === 'xs' || breakpointName === 'sm' ? 260 : 0"
    v-bind="$attrs"
  >
    <template v-slot:img="props">
      <v-img
        :gradient="`to bottom, ${barColor}`"
        v-bind="props"
      />
    </template>

    <v-divider class="mb-1" />

    <v-list
      dense
      nav
    >
      <v-list-item class="px-0">
        <v-img
          src="/vac/images/logo.png"
        />
      </v-list-item>
    </v-list>

    <v-divider class="mb-2" />

    <v-list
      expand
      nav
    >

      <div />

      <template v-for="(item, i) in computedItems">
        <base-item-group
          v-if="item.children"
          :key="`group-${i}`"
          :item="item"
        >
          <!--  -->
        </base-item-group>

        <base-item
          v-else
          :key="`item-${i}`"
          :item="item"
          :class="item['class']"
          :id="item['id']"
        />
      </template>
      <div />
    </v-list>
  </v-navigation-drawer>
</template>

<script>
  // Utilities
  import {
    mapState,
  } from 'vuex'

  export default {
    name: 'DashboardCoreDrawer',

    props: {
      expandOnHover: {
        type: Boolean,
        default: false,
      },
    },

    data: () => ({
      items: []
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
            title: 'Đăng ký tiêm chủng',
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
            title: 'Kế hoạch tiêm chủng',
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
            title: 'Đăng ký tiêm chủng',
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
            title: 'Kế hoạch tiêm chủng',
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
        ]
      }
    },
    mounted () {
      let vm = this

    },
    computed: {
      ...mapState(['barColor', 'barImage']),
      drawer: {
        get () {
          return this.$store.state.drawer
        },
        set (val) {
          this.$store.commit('SET_DRAWER', val)
        },
      },
      computedItems () {
        return this.items.map(this.mapItem)
      },
      profile () {
        return {
          avatar: true,
          title: this.$t('avatar'),
        }
      },
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    watch: {
      userLogin (val) {
        let vm = this
        if (val && val['role_name'] && val['role_name'] === 'admin') {
          
        } else {
          
        } 
      }
    },
    methods: {
      mapItem (item) {
        return {
          ...item,
          children: item.children ? item.children.map(this.mapItem) : undefined,
          title: this.$t(item.title),
        }
      },
    },
  }
</script>

<style lang="sass">
  @import '~vuetify/src/styles/tools/_rtl.sass'

  #core-navigation-drawer
    .v-list-group__header.v-list-item--active:before
      opacity: .24

    .v-list-item
      &__icon--text,
      &__icon:first-child
        justify-content: center
        text-align: center
        width: 20px

        +ltr()
          margin-right: 24px
          margin-left: 12px !important

        +rtl()
          margin-left: 24px
          margin-right: 12px !important

    .v-list--dense
      .v-list-item
        &__icon--text,
        &__icon:first-child
          margin-top: 10px

    .v-list-group--sub-group
      .v-list-item
        +ltr()
          padding-left: 8px

        +rtl()
          padding-right: 8px

      .v-list-group__header
        +ltr()
          padding-right: 0

        +rtl()
          padding-right: 0

        .v-list-item__icon--text
          margin-top: 19px
          order: 0

        .v-list-group__header__prepend-icon
          order: 2

          +ltr()
            margin-right: 8px

          +rtl()
            margin-left: 8px
</style>
