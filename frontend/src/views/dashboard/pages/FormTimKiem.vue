<template>
  <div>
    <v-card-text>
        <v-row>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Họ tên"
              v-model="dataSearch['HoVaTen']"
              outlined
              placeholder="Họ và tên"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Số CMND/ CCCD"
              v-model="dataSearch['CMTCCCD']"
              outlined
              placeholder="Số CMND/ CCCD"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Ngày đăng ký tiêm"
              v-model="dataSearch['NgayDangKi']"
              outlined
              placeholder="Ngày đăng ký tiêm"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-autocomplete
                :items="listDoiTuong"
                placeholder="Nhóm đối tượng"
                v-model="dataSearch['NhomDoiTuong']"
                item-text="doiTuongMoTa"
                item-value="doiTuongMa"
                hide-no-data
                outlined
                dense
                hide-details="auto"
                clearable    
            ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-autocomplete
              hide-no-data
              :items="listCoSoYTe"
              v-model="coSoYTe"
              item-text="tenCoSo"
              item-value="maCoSo"
              outlined
              placeholder="Cơ sở y tế"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-autocomplete
              hide-no-data
              :items="listDiaBan"
              v-model="dataSearch['DiaBanCoSo_ID']"
              item-text="tenDiaBan"
              item-value="id"
              outlined
              placeholder="Tổ dân phố, khóm ấp, thôn bản…"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
          
        </v-row>
        <v-row class="justify-end">
          <v-btn color="#0072bc" class="mt-3 mx-3" @click="$emit('trigger-search', dataSearch)">
              <v-icon left size="22">
              mdi-magnify
              </v-icon>
              Tìm kiếm
          </v-btn>
        </v-row>
    </v-card-text>
    
  </div>
  
</template>

<script>
  export default {
    name: 'Search',
    props: [],
    data () {
      return {
        listDoiTuong: [],
        listDiaBan: [],
        listCoSoYTe: [],
        coSoYTe: '',
        dataSearch: {
          HoVaTen: '',
          CMTCCCD: '',
          NhomDoiTuong: '',
          DiaBanCoSo_ID: '',
          CoSoYTe_Ma: '',
          NgayDangKi: ''
        },
      }
    },
    created () {
      let vm = this
      vm.getCoSoYTe()
      vm.getNhomDoiTuong()
    },
    watch: {
      coSoYTe (val) {
        this.dataSearch.CoSoYTe_Ma = val
        this.getDiaBanCoSo(val)
      },
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
      userLogin () {
        return this.$store.getters.getPermistion
      }
    },
    methods: {
      getDiaBanCoSo (val) {
        let vm = this
        let obj = vm.listCoSoYTe.find(function (item) {
          return item.maCoSo == val
        })
        let filter = {
          id: obj['id']
        }
        vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
          vm.listDiaBan = result ? result : []
        })
      },
      getCoSoYTe () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getCoSoYTe', filter).then(function (result) {
          vm.listCoSoYTe = result ? result : []
        })
      },
      getNhomDoiTuong () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getNhomDoiTuong', filter).then(function (result) {
          vm.listDoiTuong = result ? result : []
        })
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

