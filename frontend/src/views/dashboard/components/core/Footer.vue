<template>
  <v-footer
    id="dashboard-core-footer" class="py-0"
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
          <div>
            <img src="/images/logoVaccomFooter.png">
            <!-- <p class="mb-2 ml-2" style="text-transform: uppercase;">QUẢN LÝ VÀ TỔ CHỨC TIÊM CHỦNG VACCINE</p> -->
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
        >
          <div class="text-body-1 pt-md-0">
            <div>
              <img class="mr-8" src="/images/logoFDS.png">
              <img class="mr-8" src="/images/logoNetNam.png">
              <img class="mr-8" src="/images/logoIway.png">
              <img class="mr-8" src="/images/logoEpacific.png">
              <img class="" src="/images/logoVinades.png">
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
    }),
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
    /* background: linear-gradient(65deg,#171cc2,#ff5200); */
    background-image: url('/images/bgFooter.png');
    background-repeat: repeat;
    background-size: cover;
    color: #fff;
    height: 159px;
  }
  #dashboard-core-footer .container {
    padding-bottom: 0px !important;
  }
  .bg-line{
    position: absolute;
    top:0px;
    height: 11px;
    background: linear-gradient(65deg,#171cc2,#ff5200);
    width: 100%;
  }
  .v-footer{
    padding: 0px!important;
  }
</style>
