<template>
  <div>
    <v-card-text class="px-0">
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
              v-model="ngayDangKyFormatted"
              outlined
              placeholder="dd/mm/yyyy, ddmmyyyy"
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
              label="Nhóm đối tượng"
              v-model="dataSearch['NhomDoiTuong']"
              item-text="doiTuongMoTa"
              item-value="id"
              hide-no-data
              outlined
              dense
              hide-details="auto"
              clearable
            >
            <template v-slot:selection="data">
              <span>{{data.item.id + 1}}. {{ data.item.doiTuongMoTa }}</span>
            </template>
            <template v-slot:item="data">
              <span>{{data.item.id + 1}}. {{ data.item.doiTuongMoTa }}</span>
            </template>
            </v-autocomplete>
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
              label="Cơ sở y tế"
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
              label="Tổ dân phố, khóm ấp, thôn bản…"
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
              :items="listTrangThaiTrung"
              v-model="dataSearch['KiemTraTrung']"
              item-text="name"
              item-value="value"
              outlined
              label="Kiểm tra trùng lặp"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
        </v-row>
        <v-row class="justify-end">
          <v-btn color="red" small class="mt-3 mx-3" @click="cancelSearch">
              <v-icon left size="20">
              mdi-close
              </v-icon>
              Thoát
          </v-btn>
          <v-btn color="#0072bc" small class="mt-3 mx-3" @click="$emit('trigger-search', dataSearch)">
              <v-icon left size="20">
              mdi-content-save
              </v-icon>
              Lọc danh sách
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
          NgayDangKi: '',
          KiemTraTrung: -1
        },
        listTrangThaiTrung: [
          {name: 'Chưa kiểm tra', value: 0},
          {name: 'Đã kiểm tra, không trùng lặp', value: 1},
          {name: 'Đã kiểm tra, bị trùng lặp', value: 2}
        ],
        ngayDangKyFormatted: ''
      }
    },
    created () {
      let vm = this
      vm.getCoSoYTe()
      vm.getDiaBanCoSo()
      vm.getNhomDoiTuong()
    },
    watch: {
      coSoYTe (val) {
        this.dataSearch.CoSoYTe_Ma = val
        this.getDiaBanCoSo(val)
      },
      // ngayDangKyFormatted (val) {
      //   let splitNgayDangKy = String(val).split('/')
      //   this.dataSearch.NgayDangKi = splitNgayDangKy[2] + splitNgayDangKy[1] + splitNgayDangKy[0]
      // }
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
      cancelSearch () {
        let vm = this
        vm.dataSearch = {
          HoVaTen: '',
          CMTCCCD: '',
          NhomDoiTuong: '',
          DiaBanCoSo_ID: '',
          CoSoYTe_Ma: '',
          NgayDangKi: '',
          KiemTraTrung: -1
        },
        vm.$emit('trigger-cancel', vm.dataSearch)
      },
      getDiaBanCoSo (val) {
        let vm = this
        let filter = {
          id: -1
        }
        if (val) {
          let obj = vm.listCoSoYTe.find(function (item) {
            return item.maCoSo == val
          })
          filter = {
            id: obj['id']
          }
        }
        
        vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
          if (result.hasOwnProperty('data') && result.data.length) {
            vm.listDiaBan = [{tenDiaBan: "Chưa gán địa bàn", id: 0}].concat(result.data)
          }
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
      formatNgayDangKy () {
        let vm = this
        let lengthDate = String(vm.ngayDangKyFormatted).trim().length
        let splitDate = String(vm.ngayDangKyFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayDangKyFormatted = vm.translateDate(vm.ngayDangKyFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayDangKyFormatted)
          vm.ngayDangKyFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayDangKyFormatted = ''
        }
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

