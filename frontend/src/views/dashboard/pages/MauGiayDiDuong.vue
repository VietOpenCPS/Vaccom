<template>
  <div>
    <v-container
      id="giaydiduong"
      class="px-0 pt-0"
      fluid
      tag="section"
    >
      <v-card class="mt-0">
        <div class="pt-3" style="max-width: 320px;margin: 0 auto;">
          <v-alert
            dense
            type="success"
            v-if="conHieuLuc"
            class="px-2 py-2"
          >
          <span style="font-size: 16px;">GIẤY ĐI ĐƯỜNG HỢP LỆ</span>
          </v-alert>
          <v-alert
            dense
            type="error"
            v-else
            class="px-2 py-2"
          >
            <span style="font-size: 16px;">GIẤY ĐI ĐƯỜNG KHÔNG HỢP LỆ</span>
          </v-alert>
        </div>
        
        <v-card-text class="px-0 pb-0" style="max-width: 750px; margin: 0px auto;background: #fff;">
          <div class="pt-0" style="border: 1px solid #dedede;border-radius: 10px;">
            <div style="text-align: center;">
              <qrcode :value="urlQr" :options="{ width: 150 }" style="border-radius: 10px;"></qrcode>
            </div>
            <h4 class="my-3" style="text-align: center;text-transform: uppercase;color: #0054a6;">{{dataInfo.Donvicap}}</h4>
            <p style="text-align: center;">Xác nhận: Ông /bà:</p>
            <p style="text-align: center;font-weight: 500">{{dataInfo.hoVaTen}}</p>
            <div class=" px-3 pb-3" style="font-size: 14px;">
              <p style="margin-top: 25px;margin-bottom:10px;color:#0054a6;">
                Địa chỉ nơi ở/ cư trú: 
              </p>
              <p style="font-weight: 500;">
                {{ dataInfo.noiODiaChi}}, {{dataInfo.noiOPhuongXaTen}} - {{dataInfo.noiOQuanHuyenTen}} - {{dataInfo.noiOTinhThanhTen}}
              </p>
              <p class="mb-2" style="color:#0054a6;">Địa điểm làm việc: </p>
              <p style="font-weight: 500;">
                {{ dataInfo.noiCtDiaChi }}, {{dataInfo.noiCtPhuongXaTen}} - {{dataInfo.noiCtQuanHuyenTen}} - {{dataInfo.noiCtTinhThanhTen}}
              </p>
              <p class=""><span style="color:#0054a6;">Số CCCD/CMTND:</span> <span style="font-weight: 500">{{dataInfo.cmtcccd}}</span></p>
              <p class=""><span style="color:#0054a6;">Đơn vị/cty:</span> <span class="mb-2" style="font-weight: 500;">
                {{dataInfo.noiCtTenCoQuan}}
              </span></p>
              
              <p class="mb-2" style="color:#0054a6;">Thời gian làm việc: </p>
              <p class="mb-2" style="font-weight: 500;">
                Các ngày: {{translateLichNgay(dataInfo.lichLamViec)}}
              </p>
              <p class="" style="font-weight: 500;">
                Từ: <span class="mr-2" style="font-weight: 500">{{translateLichGio(dataInfo.lichLamViec, 'start')}}</span>
                đến <span class="ml-2" style="font-weight: 500">{{translateLichGio(dataInfo.lichLamViec, 'end')}}</span>
              </p>
              <p class="mb-2" style="color:#0054a6;">Thời hạn giấy đi đường: </p>
              <p class="mb-2" style="font-weight: 500;">Từ ngày: <span >{{ dataInfo.ngayCap }}</span></p>
              <p class="mb-2" style="font-weight: 500;">Đến ngày: 
                <span v-if="dataInfo.thoiHan">{{ dataInfo.thoiHan }}</span>
                <span v-else>Đến khi có thông báo mới</span>
              </p>
            </div>
            
          </div>
        </v-card-text>
      </v-card>
      
    </v-container>
    
  </div>
  
</template>

<script>
  import Vue from 'vue'
  import VueQrcode from '@chenfengyuan/vue-qrcode'
  Vue.component(VueQrcode.name, VueQrcode)
  export default {
    name: 'GiayDiDuong',
    components: {
    },
    props: ['uid'],
    data () {
      return {
        urlQr: '',
        conHieuLuc: true,
        dataInfo: {
          // cmtcccd: "989878123",
          // email: "congtrinh0209@gmail.com",
          // ghiChu: "12312 123",
          // hoVaTen: "Trịnh Công Trình",
          // id: 3,
          // lichLamViec: "{\"ngayTuan\":[2,4,5,6],\"ngayThang\":[],\"tuGio\":\"03:00\",\"denGio\":\"22:22\"}",
          // maQR: null,
          // ngayCap: "01/09/2021",
          // noiCtDiaChi: "123123",
          // noiCtPhuongXaMa: "00133",
          // noiCtPhuongXaTen: "Phường Ngọc Lâm",
          // noiCtQuanHuyenMa: "004",
          // noiCtQuanHuyenTen: "Quận Long Biên",
          // noiCtTenCoQuan: "123 4123",
          // noiCtTinhThanhMa: "01",
          // noiCtTinhThanhTen: "Thành phố Hà Nội",
          // noiODiaChi: "31 4123",
          // noiOPhuongXaMa: "00133",
          // noiOPhuongXaTen: "Phường Ngọc Lâm",
          // noiOQuanHuyenMa: "004",
          // noiOQuanHuyenTen: "Quận Long Biên",
          // noiOTinhThanhMa: "01",
          // noiOTinhThanhTen: "Thành phố Hà Nội",
          // soDienThoai: "0898913413",
          // status: 1,
          // thoiHan: "",
          // uyBanNhanDanID: 10
        }
      }
    },
    created () {
      let vm = this
      vm.getThongTinDiDuong()
      vm.urlQr = window.location.host + '/#/pages/giay-di-duong/' + vm.uid
    },
    watch: {
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      getThongTinDiDuong () {
        let vm = this
        let filter = {
          maQr: vm.uid
        }
        vm.$store.dispatch('getThongTinDiduong', filter).then(function(dataInfo) {
          // console.log('dataInfo', dataInfo)
          vm.dataInfo = dataInfo
          vm.checkHieuLuc(vm.dataInfo)
        }).catch (function () {
        })
      },
      translateLichNgay (data) {
        try {
          let input = JSON.parse(data)
          let ngayTuan = input['ngayTuan']
          let ngayThang = input['ngayThang']
          if (ngayTuan && ngayTuan.length) {
            let ngayTuanString = ''
            ngayTuan.forEach(element => {
              let day = ''
              if (element == 0) {
                day = 'Chủ nhật, '
              } else {
                day = 'Thứ ' + element + ', '
              }
              ngayTuanString += day
            })
            ngayTuanString = ngayTuanString.trim().substring(0, ngayTuanString.trim().length - 1)
            return ngayTuanString
          }
          if (ngayThang && ngayThang.length) {
            return ngayThang.toString().replace(/,/g, "; ")
          }
          
        } catch (error) {
          return ''
        }
      },
      translateLichGio (data, type) {
        try {
          let input = JSON.parse(data)
          let timeStart = input['tuGio'] ? input['tuGio'].split(':')[0] + 'h' + input['tuGio'].split(':')[1] : ''
          let endStart = input['denGio'] ? input['denGio'].split(':')[0] + 'h' + input['denGio'].split(':')[1] : ''
          if (type === 'start') {
            return timeStart
          } else {
            return endStart
          }
        } catch (error) {
          return ''
        }
      },
      checkHieuLuc (dataInfo) {
        let vm = this
        let dataTime = JSON.parse(dataInfo.lichLamViec)
        if (dataInfo.status == 0 || dataInfo.status == 2) {
          vm.conHieuLuc = false
          return
        }
        let newDate = new Date()
        let ngayCap = ''
        if (dataInfo.ngayCap) {
          ngayCap = vm.parseDate(dataInfo.ngayCap)
        }
        let thoiHanDate = ''
        if (dataInfo.thoiHan && String(dataInfo.thoiHan).split('/').length === 3) {
          thoiHanDate = vm.parseDate(dataInfo.thoiHan)
        }
        let currentDate = newDate.getFullYear() + '-' + String((newDate.getMonth() + 1)).padStart(2, '0') + '-' + String(newDate.getDate()).padStart(2, '0')
        console.log('currentDate', currentDate)
        if (ngayCap && ((new Date(ngayCap)).getTime() > (new Date(currentDate)).getTime())) {
          vm.conHieuLuc = false
          return
        }
        if (thoiHanDate && (new Date(thoiHanDate)).getTime() < (new Date(currentDate)).getTime()) {
          vm.conHieuLuc = false
          return
        }
        let ngayTuan = dataTime['ngayTuan']
        let ngayThang = dataTime['ngayThang']
        let currentDay = (new Date()).getDay() == 0 ? 0 : (new Date()).getDay() + 1
        console.log('currentDay', currentDay)
        if (ngayTuan && ngayTuan.length) {
          let find = ngayTuan.filter(function (item) {
            return item == currentDay
          })
          if (!find || find.length == 0) {
            vm.conHieuLuc = false
            return
          }
        }
        if (ngayThang && ngayThang.length) {
          let dateCrt = String(newDate.getDate()).padStart(2, '0') + '/' + String((newDate.getMonth() + 1)).padStart(2, '0') + '/' + newDate.getFullYear()
          console.log('dateCrt', dateCrt)
          let find = ngayThang.find(function (item) {
            return item == dateCrt
          })
          if (!find) {
            vm.conHieuLuc = false
            return
          }
        }
        let timeStart = dataTime['tuGio']
        let timeEnd = dataTime['denGio']
        let date = (new Date()).getDate()
        let month = (new Date()).getMonth() + 1
        let year = (new Date()).getFullYear()
        let hours = (new Date()).getHours()
        let minutes = (new Date()).getMinutes()
        let currentTime = (new Date(`${year}-${month.toString().padStart(2, '0')}-${date.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`)).getTime()
        let startTime = (new Date(`${year}-${month.toString().padStart(2, '0')}-${date.toString().padStart(2, '0')}T${timeStart}`)).getTime()
        let endTime = (new Date(`${year}-${month.toString().padStart(2, '0')}-${date.toString().padStart(2, '0')}T${timeEnd}`)).getTime()
        // console.log(currentTime,startTime, endTime)
        if (currentTime < startTime || currentTime > endTime) {
          vm.conHieuLuc = false
          return
        }
        console.log('hieuLuc', vm.conHieuLuc)
      },
      formatDate (date) {
        if (!date) return null
        const [year, month, day] = date.split('-')
        return `${day}/${month}/${year}`
      },
      parseDate (date) {
        if (!date) return null
        console.log('date', date)
        const [day, month, year] = date.split('/')
        console.log('date', `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`)
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
  #navigation {
    display: none !important;
  }
  #header-desktop {
    display: none !important;
  }
</style>

