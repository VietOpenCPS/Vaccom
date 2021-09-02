<template>
  <div>
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <v-card-text style="max-width: 750px; margin: 15px auto;background: #fff;">
          <img style="width: 100px;border-radius: 10px;position: absolute;" src="/images/qrcode.png" alt="">
          <div class="pt-5">
            <h3 class="mb-3" style="text-align: center;">UBND PHƯỜNG THẠCH BÀN</h3>
            <p style="text-align: center;">Xác nhận: Ông /bà:</p>
            <p style="text-align: center;font-weight: bold">Nguyễn Văn A</p>
            <div class="px-3">
              <p style="margin-top:50px">Địa chỉ nơi ở/ cư trú: </p>
              <p style="font-weight: bold;padding-left: 50px;">1xx Nguyễn Văn Cừ - Phường Ngọc Lâm - Long Biên - Hà Nội</p>
              <p>Địa điểm làm việc: </p>
              <p style="font-weight: bold;padding-left: 50px;">Khu CN Sài đồng B - Thạch Bàn - Long Biên – HN</p>
              <p>Số CCCD/CMTND: <span style="font-weight: bold">123456789</span></p>
              <p>Đơn vị/cty:  <span style="font-weight: bold">Cty TNHH và TMDV ABCDXEZ </span></p>
              <p>Thời gian làm việc: </p>
              <p style="font-weight: bold;padding-left: 50px;">Từ thứ 2 đến thứ 7</p>
              <p style="font-weight: bold;padding-left: 50px;">Từ 08h đến 17h00</p>
              <p>Thời hạn sử dụng: </p>
              <p style="font-weight: bold;padding-left: 50px;">Đến 15/9/2021</p>
              <p style="font-weight: bold;padding-left: 50px;">(hoặc: Cho đến khi có thông báo mới)</p>
            </div>
            
          </div>
          
      </v-card-text>
    </v-container>
    
  </div>
  
</template>

<script>
  export default {
    name: 'GiayDiDuong',
    components: {
    },
    props: ['qruid'],
    data () {
      return {
      }
    },
    created () {
      let vm = this
      
    },
    watch: {
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
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
  #header-desktop #banner .btns a.login {
    display: none !important;
  }
</style>

