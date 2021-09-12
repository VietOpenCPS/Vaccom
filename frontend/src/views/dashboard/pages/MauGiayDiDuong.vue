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
          <div v-if="!conHieuLuc">
            <p v-if="dataInfo.status == 2 && dataInfo.ghiChu" style="text-align: center;font-weight: bold;">{{dataInfo.ghiChu}}</p>
            <p v-else style="text-align: center;font-weight: bold;">NGOÀI THỜI GIAN ĐƯỢC RA ĐƯỜNG</p>
          </div>
        </div>
        
        <v-card-text class="px-0 py-0" style="max-width: 750px; margin: 0px auto;background: #fff;">
          <div class="pt-0" style="border: 1px solid #dedede;border-radius: 10px;">
            <div style="text-align: center;">
              <qrcode v-if="urlQr" :value="urlQr" :options="{ width: 150 }" style="border-radius: 10px;"></qrcode>
            </div>
            <h4 class="my-3" style="text-align: center;text-transform: uppercase;color: #0054a6;">{{dataInfo.Donvicap}}</h4>
            <p style="text-align: center;" class="mb-2">Xác nhận: Ông /bà:</p>
            <p style="text-align: center;font-weight: 500;text-transform: uppercase;" class="mb-2">{{dataInfo.hoVaTen}}</p>
            <div class=" px-3 pb-3" style="font-size: 14px;">
              <p style="margin-top: 15px;margin-bottom:10px;color:#0054a6;">
                Địa chỉ nơi ở/ cư trú: 
              </p>
              <p style="font-weight: 500;" class="mb-2">
                {{ dataInfo.noiODiaChi}}
                <span v-if="dataInfo.noiOPhuongXaTen">, {{dataInfo.noiOPhuongXaTen}}</span>
                <span v-if="dataInfo.noiOQuanHuyenTen"> - {{dataInfo.noiOQuanHuyenTen}}</span>
                <span v-if="dataInfo.noiOTinhThanhTen"> - {{dataInfo.noiOTinhThanhTen}}</span>
              </p>
              <p class="mb-2" style="color:#0054a6;">Địa điểm làm việc: </p>
              <p style="font-weight: 500;" class="mb-2">
                {{ dataInfo.noiCtDiaChi }}
                <span v-if="dataInfo.noiCtPhuongXaTen">, {{dataInfo.noiCtPhuongXaTen}}</span>
                <span v-if="dataInfo.noiCtQuanHuyenTen"> - {{dataInfo.noiCtQuanHuyenTen}}</span>
                <span v-if="dataInfo.noiCtTinhThanhTen"> - {{dataInfo.noiCtTinhThanhTen}}</span>
              </p>
              <p class="mb-2"><span style="color:#0054a6;">Số CCCD/CMTND:</span> <span style="font-weight: 500">{{dataInfo.cmtcccd}}</span></p>
              <p class="mb-2"><span style="color:#0054a6;">Đơn vị/cty:</span> <span class="mb-2" style="font-weight: 500;">
                {{dataInfo.noiCtTenCoQuan}}
              </span></p>
              
              <p class="mb-2" style="color:#0054a6;">Thời gian làm việc: </p>
              <p class="mb-2" style="font-weight: 500;">
                {{translateLichNgay(dataInfo.lichLamViec)}}
              </p>
              <p class="mb-2" style="font-weight: 500;">
                Từ: <span class="mr-2" style="font-weight: 500">{{translateLichGio(dataInfo.lichLamViec, 'start')}}</span>
                đến <span class="ml-2" style="font-weight: 500">{{translateLichGio(dataInfo.lichLamViec, 'end')}}</span>
              </p>
              <p class="mb-2" style="color:#0054a6;">Thời hạn giấy đi đường: </p>
              <p class="mb-2" style="font-weight: 500;">Từ ngày: <span >{{ dataInfo.ngayCap }}</span></p>
              <p class="mb-2" style="font-weight: 500;">Đến ngày: 
                <span v-if="dataInfo.thoiHan">{{ dataInfo.thoiHan }}</span>
                <span v-else>Đến khi có thông báo mới</span>
              </p>
              <div>
                (Giấy này chỉ có hiệu lực trong phạm vi {{scopePhamVi}})
              </div>
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
        scopePhamVi: '',
        dataInfo: {
          // cmtcccd: "989878123",
          // email: "congtrinh0209@gmail.com",
          // ghiChu: "12312 123",
          // hoVaTen: "Trịnh Công Trình",
          // id: 3,
          // lichLamViec: "{\"ngayTuan\":[2,3,4,6],\"ngayThang\":[\"07/09/2021\",\"09/09/2021\"],\"tuGio\":\"08:00\",\"denGio\":\"10:00\"}",
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
    },
    watch: {
      uid (val) {
        if (val) {
          this.getThongTinDiDuong()
        }
      }
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
          vm.urlQr = dataInfo['LinkQrCode']
          vm.checkHieuLuc(vm.dataInfo)
          try {
            vm.scopePhamVi = vm.dataInfo['Donvicap'].split('-')[1] + ' - ' + vm.dataInfo['Donvicap'].split('-')[2]
          } catch (error) {
          }
        }).catch (function () {
          vm.conHieuLuc = false
        })
      },
      translateLichNgay (data) {
        try {
          let input = JSON.parse(data)
          let dataOut = ''
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
            dataOut += ngayTuanString
          }
          if (ngayThang && ngayThang.length) {
            dataOut = dataOut + ' - Các ngày: ' +  ngayThang.toString().replace(/,/g, "; ")
          }
          return dataOut
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
        vm.conHieuLuc = true
        if (!dataInfo) {
          vm.conHieuLuc = false
          return
        }
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

