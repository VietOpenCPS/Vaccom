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
              <span>Kính gửi ông/bà: </span>
              <span style="font-weight: 600;text-transform: uppercase;">{{dataInfo.HoVaTen}}</span>
            </p>
            <!-- <p class="mb-2">
              <span>Ngày tháng năm sinh: </span>
              <span style="font-weight: 600">{{dataInfo.Donvicap}}</span>
            </p> -->
            <p class="mb-2">
              <span>Số CCCD/CMTND: </span>
              <span style="font-weight: 600;">{{dataInfo.cmtcccd}}</span>
            </p>
            <p class="mb-2" style="text-align: justify;">
              <span>Ông/bà được </span>
              <span style="font-weight: 600;">{{dataInfo.CoSoYTe}}</span>
              <span> gọi tiêm chủng theo lịch như sau:</span>
            </p>
            <p class="mb-2">
              <span>Ngày hẹn tiêm: </span>
              <span style="font-weight: 600;">{{dataInfo.ngayHenTiem}}</span>
            </p>
            <p class="mb-2">
              <span>Giờ hẹn tiêm (dự kiến): </span>
              <span style="font-weight: 600;">{{dataInfo.gioHenTiem}}</span>
            </p>
            <p class="mb-2" style="text-align: justify;">
              <span>Địa điểm: </span>
              <span style="font-weight: 600;">{{dataInfo.DiaDiem}}</span>
            </p>
            <p class="mb-2">
              <span>Mũi tiêm: </span>
              <span style="font-weight: 600;">{{dataInfo.lanTiem}}</span>
            </p>
            <p class="mb-2">
              <span>Loại Vaccine: </span>
              <span style="font-weight: 500;">{{dataInfo.LoaiThuocTiem}}</span>
            </p>
            <p class="my-2">
              <span style="text-align: justify;">Xin vui lòng đến đúng giờ và địa điểm tiêm, mang theo: </span><br>
              <span class="mb-2 pl-3">-	CCCD/CMTND (bắt buộc);</span><br>
              <span class="mb-2 pl-3">-	Thẻ BHYT;</span><br>
              <!-- <span class="mb-2 pl-3">-	Giấy xác nhận tiêm mũi 1 (với người tiêm mũi 2);</span> -->
            </p>
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
    name: 'PhieuHenTiem',
    components: {
    },
    props: ['uid'],
    data () {
      return {
        urlQr: '',
        dataInfo: ''
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
        }).catch (function () {
        })
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

