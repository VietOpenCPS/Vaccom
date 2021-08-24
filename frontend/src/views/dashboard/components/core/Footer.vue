<template>
  <v-footer
    id="dashboard-core-footer" class="py-0"
  >
    <v-container>
      <v-row
        align="center"
        no-gutters
      >
        <v-col
          class=" mb-sm-0"
          cols="auto"
        >
          <div>
            <p class="mb-2" style="text-transform: uppercase;">Phần mềm hỗ trợ quản lý và tổ chức Điểm tiêm chủng Vaccine</p>
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
              <!-- <p class="mb-2"><v-icon color="#fff" class="mr-1">mdi-phone-in-talk</v-icon> Điện thoại: </p>
              <p><v-icon color="#fff" class="mr-1">mdi-email</v-icon> Email: </p> -->
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
    background-color: #3122ad!important;
    color: #fff;
  }
  #dashboard-core-footer .container {
    padding-bottom: 0px !important;
  }
</style>
