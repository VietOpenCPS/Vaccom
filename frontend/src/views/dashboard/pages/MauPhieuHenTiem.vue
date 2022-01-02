<template>
  <div>
    <v-container
      id="phieuhentiem"
      class="px-0 pt-0"
      fluid
      tag="section"
    >
      <v-card class="mt-0">
        <v-card-text class="px-0 py-0" style="max-width: 750px; margin: 0px auto;background: #fff;">
          <div style="text-align: center;">
            <qrcode v-if="urlQr" :value="urlQr" :options="{ width: 150 }" style="border-radius: 10px;"></qrcode>
          </div>
          <div class="pt-0 px-2 pb-3" style="">
            <h4 class="mb-3" style="text-align: center;text-transform: uppercase;color: #0054a6;">
              {{dataInfo.Donvicap}}
            </h4>
            <p style="font-weight: 600;text-align: center;text-transform: uppercase;" class="mb-3">Thông báo lịch hẹn gọi tiêm chủng</p>
            <p class="mb-2">
              <span>Họ và tên: </span>
              <span style="font-weight: 600;text-transform: uppercase;">{{dataInfo.HoVaTen}}</span>
            </p>
            <p class="mb-2">
              <span>Ngày sinh: </span>
              <span style="font-weight: 600">{{dataInfo.hasOwnProperty('ngaySinh') ? dataInfo.ngaySinh : ''}}</span>
            </p>
            <!-- <p class="mb-2">
              <span>Số CCCD/CMTND: </span>
              <span style="font-weight: 600;">{{dataInfo.cmtcccd}}</span>
            </p> -->
            <!-- <p class="mb-2" style="text-align: justify;">
              <span>Ông/bà được </span>
              <span style="font-weight: 600;">{{dataInfo.CoSoYTe}}</span>
              <span> gọi tiêm chủng theo lịch như sau:</span>
            </p> -->
            <p class="mb-2">
              <span>Ngày tiêm: </span>
              <span style="font-weight: 600;">{{dataInfo.ngayHenTiem}}</span>
            </p>
            <p class="mb-2">
              <span>Giờ dự kiến: </span>
              <span style="font-weight: 600;">{{dataInfo.gioHenTiem}}</span>
            </p>
            <p class="mb-2" style="text-align: justify;">
              <span>Đơn vị tiêm: </span>
              <span style="font-weight: 600;">{{dataInfo.CoSoYTe}}</span>
            </p>
            <p class="mb-2" style="text-align: justify;">
              <span>Địa điểm: </span>
              <span style="font-weight: 600;">{{dataInfo.DiaDiem}}</span>
            </p>
            <p class="mb-2">
              <span>Loại Vaccine: </span>
              <span style="font-weight: 500;">{{dataInfo.LoaiThuocTiem}}</span>
            </p>
            <p class="mb-2">
              <span>Mũi tiêm: </span>
              <!-- <span style="font-weight: 600;">{{dataInfo.lanTiem}}</span> -->
              <span style="font-weight: 600;">3</span>
            </p>
            <i style="color: #2196f3">"Thông báo này thay thế cho các thông báo trước đây"</i>
            <p style="font-weight: 600;" class="mb-2">Lưu ý: </p>
            <div class="mb-2" style="text-align: justify;">
              <span class="mb-2">-	Đến đúng giờ. Có thể chụp lại màn hình điện thoại để xuất trình mã QR code tại bàn đón tiếp tại điểm tiêm;</span><br>
              <span class="mb-2">-	Mang theo: CMTND/CCCD, Phiếu sàng lọc; Thẻ bảo hiểm y tế (nếu có);</span><br>
              <span class="mb-2">-	Khai báo đầy đủ các biểu mẫu tại điểm tiêm; (Để giảm ùn tắc tại điểm tiêm: Có thể tải và in ra giấy, điền đầy đủ thông tin trước khi đến điểm tiêm theo đường dẫn sau đây);</span><br>
              <p class="my-2">
                <v-icon size="20" color="blue" class="mr-2">mdi-file-word-outline</v-icon> 
                <a style="color: #2196F3" href="https://drive.google.com/drive/folders/18Elo7z9-h58OVYFkWc1I1ProeawKBSup" target="_blank">Tải biểu mẫu khai báo</a>
              </p>
              
              <span class="mb-2">-	Tải/ cài đặt “ Sổ sức khỏe điện tử” cập nhật đầy đủ, chính xác thông tin cá nhân để giảm thiểu các thủ tục kiểm soát tại điểm tiêm;</span><br>
            </div>
            <p class="mb-2">
              Trường hợp đã tiêm mũi 3 hoặc không đến được điểm tiêm: Vui lòng thông tin lại ngay lý do cho tổ trưởng tổ dân phố.
            </p>
          </div>
          <div v-if="isSigned" class="pl-2">
            <span>Tình trạng đăng ký: </span>
            <span style="font-weight: 500;color: red">{{getTextTrangThai(dataInfo.tinhTrangXacNhan)}}</span>
          </div>
          <v-flex v-if="isSigned && dataInfo.tinhTrangXacNhan == 2 || dataInfo.tinhTrangXacNhan == 3" class="text-center py-3">
            <v-btn :color="dataInfo.tinhTrangXacNhan == 2 ? 'green' : 'orange'" class="mx-0" @click.stop="submitTranslate" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-account-check-outline
              </v-icon>
              {{getTextAction(dataInfo.tinhTrangXacNhan)}}
            </v-btn> 
          </v-flex>
        </v-card-text>
      </v-card>
      
    </v-container>
    
  </div>
  
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios'
  import VueQrcode from '@chenfengyuan/vue-qrcode'
  Vue.component(VueQrcode.name, VueQrcode)
  export default {
    name: 'PhieuHenTiem',
    components: {
    },
    props: ['uid'],
    data () {
      return {
        urlQr: '',
        dataInfo: '',
        processingAction: false
      }
    },
    created () {
      let vm = this
      vm.getThongTinPhieuHen()
    },
    watch: {
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      getThongTinPhieuHen () {
        let vm = this
        let filter = {
          maQr: vm.uid
        }
        vm.$store.dispatch('getThongTinPhieuHen', filter).then(function(dataInfo) {
          console.log('dataInfo', dataInfo)
          vm.dataInfo = dataInfo
          vm.urlQr = dataInfo['LinkQrCode']
          // vm.urlQr = "http://119.17.200.69:8030/vac/index.html#" + dataInfo['LinkQrCode'].split('#')[1]
        }).catch (function () {
        })
      },
      submitTranslate () {
        let vm = this
        try {
          let data = localStorage.getItem('user')
          let userInfo = JSON.parse(data)
          vm.$store.dispatch('getUserInfo', userInfo).then(function(dataInfo) {
            if (dataInfo && dataInfo['id']) {
              vm.translateStatus()
            } else {
              vm.$router.push({ path: '/login?redirect=' + vm.dataInfo.LinkQrCode.split('#')[1] })
            }
          }).catch(function (error) {
            vm.$router.push({ path: '/login?redirect=' + vm.dataInfo.LinkQrCode.split('#')[1] })
          })
        } catch (error) {
          vm.$router.push({ path: '/login?redirect=' + vm.dataInfo.LinkQrCode.split('#')[1] })
        }
      },
      translateStatus () {
        let vm = this
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = {
          TinhTrangXacNhan: vm.dataInfo.tinhTrangXacNhan == 2 ? 3 : 4,
          ids: String(vm.dataInfo.id)
        }
        vm.processingAction = true
        axios.put('/rest/v1/app/update/phieuhentiem/tinhtrangxacnhan', dataPost, param).then(function (response) {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Xác nhận thành công',
            color: 'success',
          })
          setTimeout(function () {
            vm.getThongTinPhieuHen()
          }, 200)
        }).catch(function (error) {
            vm.processingAction = false
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xác nhận thất bại',
              color: 'error',
            })
        });
      },
      getTextTrangThai(trangThai) {
        switch (trangThai) {
            case 0:
              return 'Chờ gửi thông báo';
            case 1:
                return 'Chờ xác nhận';
            case 2:
                return 'Đã gửi thông báo'
            case 3:
                return 'Đã check-in'
            case 4:
                return 'Đã tiêm xong'
            case 5:
                return 'Chưa được tiêm'
            case 6:
                return 'Xác nhận không đến'              
        }
        return ''
      },
      getTextAction(trangThai) {
        switch (trangThai) {
            case 2:
                return 'Xác nhận đến tiêm'
            case 3:
                return 'Xác nhận đã tiêm'             
        }
        return ''
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

