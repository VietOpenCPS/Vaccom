<template>
  <v-footer
    id="dashboard-core-footer" class="py-0"
  >
    <v-container class="pt-1">
      <v-row
        align="center"
        no-gutters
      >
        <v-col
          class=" mb-sm-0"
          cols="auto"
        >
          <div>
            <img style="width: auto;height: 52px;border-radius: 10px;" src="/images/logo_banner.png">
            <p v-if="isTempDiDuong" class="mb-2 ml-2" style="text-transform: uppercase;">QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG</p>
            <p v-else class="mb-2 ml-2" style="text-transform: uppercase;">HỆ THỐNG QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG</p>
            <!-- <p class="mb-2"><v-icon color="#fff" class="mr-1">mdi-home-city-outline</v-icon> </p> -->
            <!-- <p class="mb-2"><v-icon color="#fff" class="mr-1">mdi-map-marker-star</v-icon> </p>
            <p class="mb-2"><v-icon color="#fff" class="mr-1">mdi-map-marker-star</v-icon> </p>
            <p><v-icon color="#fff" class="mr-1">mdi-map-marker-star</v-icon> </p> -->
          </div>
        </v-col>

        <v-spacer class="hidden-sm-and-down" />

        <v-col
          cols="12"
          md="auto"
          v-if="!isTempDiDuong"
        >
          <div class="text-body-1 pt-md-0">
            <div>
              <img class="mr-4" style="width: 80px;height: 52px;" src="/images/logo_fds.png">
              <img class="mr-4" style="width: 90px;height: 42px;" src="/images/logo_netnam.png">
              <img class="mr-4" style="width: 90px;height: 42px;" src="/images/logo_iway.png">
              <img class="pt-1" style="width: 90px;height: 42px;" src="/images/logo_epacific.png">
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script>
  export default {
    name: 'DashboardCoreFooter',

    data: () => ({
      links: [],
      isTempDiDuong: false
    }),
    created () {
      let vm = this
      try {
        let currentQuery = vm.$router.history.current
        if (currentQuery.name === 'MauGiayDiDuong' && (vm.breakpointName === 'xs' || vm.breakpointName === 'sm')) {
          vm.isTempDiDuong = true
        } else {
          vm.isTempDiDuong = false
        }
      } catch (error) {
      }
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    }
  }
</script>

<style lang="sass">
  #dashboard-core-footer
    a
      font-size: .825rem
      font-weight: 500
      text-decoration: none
      text-transform: uppercase
</style>
<style lang="css" scoped>
  #dashboard-core-footer{
    background: linear-gradient(65deg,#171cc2,#ff5200);
    color: #fff;
    height: 120px;
  }
  #dashboard-core-footer .container {
    padding-bottom: 0px !important;
  }
</style>
