<template>
  <v-footer
    id="dashboard-core-footer" class="py-0 pl-0" :style="isTempDiDuong ? 'height: 85px;' : 'height: 120px'"
  >
    <div class="bg-line"></div>
    <v-container class="pt-1">
      <v-row
        align="center"
        no-gutters
      >
        <v-col
          class=" mb-sm-0"
          cols="auto"
        >
          <div class="mt-2">
            <img src="/images/logo_footer2.png" :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'max-width: 150px' : ''">
            <p class="mb-2" style="text-transform: uppercase;color:black">QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG VACCINE</p>
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
              <img class="mr-3" src="/images/logo_fds2.png">
              <img class="mr-3 pt-2" src="/images/logo_netnam2.png">
              <img class="mr-3" src="/images/logo_iway3.png">
              <img class="mr-3" src="/images/logo_pacific2.png">
              <img class="" src="/images/logo_vinades.png">
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-footer> -->
  <v-footer
    id="dashboard-core-footer" class="py-0 pl-0"
  >
    <div class="bg-line"></div>
    <v-container class="pt-1">
      <v-row
        align="center"
        no-gutters
      >
        <v-col
          class=" mb-sm-0"
          cols="auto"
        >
          <div class="mt-2">
            <img src="/images/logo_footer2.png" :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'max-width: 150px' : ''">
            <p class="mb-2" style="text-transform: uppercase;color:black">QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG VACCINE</p>
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
              <img class="mr-3" src="/images/logo_fds2.png">
              <img class="mr-3 pt-2" src="/images/logo_netnam2.png">
              <img class="mr-3" src="/images/logo_iway3.png">
              <img class="mr-3" src="/images/logo_pacific2.png">
              <img class="" src="/images/logo_vinades.png">
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
  /* #dashboard-core-footer{
    background: linear-gradient(65deg,#171cc2,#ff5200);
    color: #fff;
    height: 120px;
  } */
  #dashboard-core-footer{
    background-image: url('/images/bg-footer2.png');
    background-repeat: repeat;
    background-size: cover;
    color: #fff;
    height: 120px;
  }
  .bg-line{
    position: absolute;
    top:0px;
    height: 11px;
    background: linear-gradient(65deg,#3ec7d3,#2243ad);
    width: 100%;
  }
  #dashboard-core-footer .container {
    padding-bottom: 0px !important;
  }
  #dashboard-core-footer .container {
    padding-bottom: 0px !important;
  }
</style>
