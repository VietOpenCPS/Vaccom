<template>
  <v-container
    id="createEWarranty"
    fluid
    tag="section"
    class="mt-3"
    :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
  >
    <v-row justify="center">
      <v-col
        cols="12" class="px-0 py-0"
      >
        <base-material-card
          style="margin-top: 20px"
          icon="mdi-clipboard-text"
          :title="String(uid) === '0' ? 'ĐĂNG KÝ CẤP GIẤY ĐI ĐƯỜNG' : 'CẬP NHẬT THÔNG TIN NGƯỜI ĐĂNG KÝ'"
          class="px-5 py-3"
        >
          <v-btn id="xemdanhsach" class="mx-0" dark color="#0072bc" @click.stop="showDanhSach" style="position: absolute; right: 20px; top: 15px;">
            <v-icon dark class="mr-2">
              mdi-format-list-bulleted
            </v-icon>
            Danh sách đăng ký
          </v-btn>
          <v-form
            ref="formDangKy"
            v-model="validFormAdd"
            lazy-validation
            id="formDangKy"
          >
            <v-container class="py-0 mt-3">
              <!-- row 1 -->
              <v-row v-if="dataHistory && typeAction === 'add'">
                <v-btn class="mx-3 mt-3" dark small color="#0072bc" @click.stop="copyContent">
                  <v-icon dark class="mr-2">
                    mdi-content-copy
                  </v-icon>
                  Sử dụng thông tin trước
                </v-btn>
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  md="3"
                  class="pb-0"
                >
                  <div class="mb-2">Họ và tên <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['HoVaTen']"
                    :rules="required"
                    required
                    outlined
                    placeholder="Họ và tên"
                    dense
                    hide-details="auto"
                    @blur="formatHoTen"
                    id="hoten"
                    @keyup.enter="nextFocus('cccd')"
                    autofocus
                  ></v-text-field>
                </v-col>
                
                <v-col
                  cols="12"
                  md="3"
                  class="pb-0"
                >
                  <div class="mb-2">Số CMND/CCCD <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['CMTCCCD']"
                    :rules="!giayToLoaiKhac ? requiredCredit : required"
                    required
                    outlined
                    placeholder="Số CMND/CCCD"
                    dense
                    id="cccd"
                    hide-details="auto"
                    @keyup.enter="nextFocus('sdt')"
                  ></v-text-field>
                  <v-checkbox
                  class="mt-0 checkboxCmt"
                    v-model="giayToLoaiKhac"
                    label="Giấy tờ loại khác"
                  ></v-checkbox>
                </v-col>
                <v-col
                  cols="12"
                  md="3"
                  class="pb-0"
                >
                  <div class="mb-2">Số điện thoại <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['SoDienThoai']"
                    :rules="requiredTelNo"
                    required
                    outlined
                    placeholder="Số điện thoại"
                    dense
                    hide-details="auto"
                    id="sdt"
                    @keyup.enter="nextFocus('email')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  md="3"
                  class="pb-0"
                >
                  <div class="mb-2">Email</div>
                  <v-text-field
                    v-model="applicantInfo['Email']"
                    outlined
                    placeholder="Email"
                    dense
                    id="email"
                    hide-details="auto"
                    @keyup.enter="nextFocus('noio')"
                  ></v-text-field>
                </v-col>
                
              </v-row>
              <!-- row 3 -->
              <v-row>
                <v-col
                  cols="12"
                  class="py-0"
                >
                  <div class="mb-2 font-weight-bold">Địa chỉ nơi ở/ cư trú <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['NoiO_DiaChi']"
                    :rules="required"
                    required
                    outlined
                    placeholder="Số nhà, đường, tổ dân phố, khóm ấp, thôn bản…"
                    dense
                    id="noio"
                    hide-details="auto"
                    @keyup.enter="nextFocus('tinhthanh')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Tỉnh/ Thành phố <span style="color:red">(*)</span></div>
                  <v-autocomplete
                      hide-no-data
                      :items="listTinhThanh"
                      v-model="tinhThanh"
                      item-text="tinhThanhTen"
                      item-value="tinhThanhMa"
                      id="tinhthanh"
                      :rules="required"
                      required
                      outlined
                      placeholder="Tỉnh/ Thành phố"
                      dense
                      hide-details="auto"
                      @keyup.enter="nextFocus('quanhuyen')"
                  ></v-autocomplete>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Quận/ Huyện <span style="color:red">(*)</span></div>
                  <v-autocomplete
                      hide-no-data
                      :items="listQuanHuyen"
                      v-model="quanHuyen"
                      item-text="quanHuyenTen"
                      item-value="quanHuyenMa"
                      id="quanhuyen"
                      :rules="required"
                      required
                      outlined
                      placeholder="Quận/ Huyện"
                      dense
                      hide-details="auto"
                      @keyup.enter="nextFocus('xaphuong')"
                  ></v-autocomplete>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Phường/ Xã <span style="color:red">(*)</span></div>
                  <v-autocomplete
                      hide-no-data
                      :items="listXaPhuong"
                      v-model="xaPhuong"
                      item-text="phuongXaTen"
                      item-value="phuongXaMa"
                      id="xaphuong"
                      :rules="required"
                      required
                      outlined
                      placeholder="Phường/ Xã"
                      dense
                      hide-details="auto"
                      @keyup.enter="nextFocus('noilamviec')"
                  ></v-autocomplete>
                </v-col>
                                
              </v-row>
              <!-- row 4 -->
              <v-row class="pt-3">
                <v-col
                  cols="12"
                  class="pb-0"
                >
                  <div class="mb-2 font-weight-bold">Địa chỉ nơi làm việc <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['NoiCT_DiaChi']"
                    :rules="required"
                    required
                    outlined
                    placeholder=""
                    dense
                    id="noilamviec"
                    hide-details="auto"
                    @keyup.enter="nextFocus('tinhthanhlamviec')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Tỉnh/ Thành phố <span style="color:red">(*)</span></div>
                  <v-autocomplete
                      hide-no-data
                      :items="listTinhThanh"
                      v-model="tinhThanhLamViec"
                      item-text="tinhThanhTen"
                      item-value="tinhThanhMa"
                      id="tinhthanhlamviec"
                      :rules="required"
                      required
                      outlined
                      placeholder="Tỉnh/ Thành phố"
                      dense
                      hide-details="auto"
                      @keyup.enter="nextFocus('quanhuyenlamviec')"
                  ></v-autocomplete>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Quận/ Huyện <span style="color:red">(*)</span></div>
                  <v-autocomplete
                    hide-no-data
                    :items="listQuanHuyenLamViec"
                    v-model="quanHuyenLamViec"
                    item-text="quanHuyenTen"
                    item-value="quanHuyenMa"
                    id="quanhuyenlamviec"
                    :rules="required"
                    required
                    outlined
                    placeholder="Quận/ Huyện"
                    dense
                    hide-details="auto"
                    @keyup.enter="nextFocus('xaphuonglamviec')"
                ></v-autocomplete>
                </v-col>
                <v-col
                  cols="12"
                  md="4"
                  class="pb-0"
                >
                  <div class="mb-2">Phường/ Xã <span style="color:red">(*)</span></div>
                  <v-autocomplete
                      hide-no-data
                      :items="listXaPhuongLamViec"
                      v-model="xaPhuongLamViec"
                      item-text="phuongXaTen"
                      item-value="phuongXaMa"
                      id="xaphuonglamviec"
                      :rules="required"
                      required
                      outlined
                      placeholder="Phường/ Xã"
                      dense
                      hide-details="auto"
                      @keyup.enter="nextFocus('ngaycap')"
                  ></v-autocomplete>
                </v-col>
                                
              </v-row>
              <!-- row 7 -->
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0"
                >
                  <div class="mb-2">Ngày cấp <span style="color:red">(*)</span></div>
                  <v-text-field
                    :rules="required"
                    required
                    v-model="applicantDateFormatted"
                    placeholder="dd/mm/yyyy, ddmmyyyy"
                    @blur="formatBirthDate"
                    dense
                    hide-details="auto"
                    outlined
                    id="ngaycap"
                    @keyup.enter="nextFocus('thoihan')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0"
                >
                  <div class="mb-2">Ngày hết hạn <span style="color:red">(*)</span></div>
                  <v-text-field
                    :rules="required"
                    required
                    v-model="ngayDuKienFormatted"
                    placeholder="dd/mm/yyyy, ddmmyyyy"
                    @blur="formatNgayTiem"
                    dense
                    hide-details="auto"
                    outlined
                    id="thoihan"
                    @keyup.enter="nextFocus('ghichu')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  class="pb-0"
                >
                  <div class="mb-2">Ghi chú</div>
                  <v-textarea
                    v-model="applicantInfo['GhiChu']"
                    outlined
                    placeholder=""
                    dense
                    rows=3
                    hide-details="auto"
                    id="ghichu"
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
          <v-row class="mt-5">
            <v-col
              cols="12"
              class="text-center"
            >
              <v-btn class="mr-4" v-if="String(uid) === '0'" color="red" @click="huyDangKy">
                <v-icon left>
                  mdi-close
                </v-icon>
                <span>HỦY</span>
              </v-btn>
              <v-btn v-if="String(uid) === '0'" class="mr-3" color="#0072bc" @click="submitForm" :loading="processingAction" :disabled="processingAction">
                <v-icon left>
                  mdi-content-save-outline
                </v-icon>
                <span>ĐĂNG KÝ</span>
              </v-btn>
              <v-btn v-if="String(uid) !== '0'" class="mr-3" color="#0072bc" @click="submitForm">
                <v-icon left>
                  mdi-content-save-outline
                </v-icon>
                <span>CẬP NHẬT THÔNG TIN</span>
              </v-btn>
            </v-col>
          </v-row>
          
        </base-material-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import $ from 'jquery'
  export default {
    name: 'CreateEWarranty',
    props: ['uid'],
    data () {
      return {
        loading: false,
        validFormAdd: true,
        tab: null,
        typeAction: 'add',
        processingAction: false,
        giayToLoaiKhac: false,
        applicantInfo: {
          HoVaTen: '',
          CMTCCCD: '',
          SoDienThoai: '',
          Email: '',
          NoiCT_TenCoQuan: '',
          NoiCT_DiaChi: '',
          NoiCT_TinhThanh_Ma: '',
          NoiCT_TinhThanh_Ten: '',
          NoiCT_QuanHuyen_Ma: '',
          NoiCT_QuanHuyen_Ten: '',
          NoiCT_PhuongXa_Ma: '',
          NoiCT_PhuongXa_Ten: '',
          NoiO_DiaChi: '',
          NoiO_TinhThanh_Ma: '',
          NoiO_TinhThanh_Ten: '',
          NoiO_QuanHuyen_Ma: '',
          NoiO_QuanHuyen_Ten: '',
          NoiO_PhuongXa_Ma: '',
          NoiO_PhuongXa_Ten: '',
          LichLamViec: '',
          UyBanNhanDan_ID: '',
          NgayCap: '',
          ThoiHan: '',
          GhiChu: ''
        },
        listGioiTinh: [{name: 'Nam', value: 0},{name: 'Nữ', value: 1},{name: 'Không xác định', value: 2}],
        listDoiTuong: [],
        listTinhThanh: [],
        tinhThanh: '01',
        tinhThanhLamViec: '01',
        listQuanHuyen: [],
        listQuanHuyenLamViec: [],
        quanHuyen: '004',
        quanHuyenLamViec: '004',
        listXaPhuong: [],
        listXaPhuongLamViec: [],
        xaPhuong: '',
        xaPhuongLamViec: '',
        listDiaBan: [
        ],
        listCoSoYTe: [],
        coSoYTe: '',
        listQuocTich: [],
        listDanToc: [],
        menuApplicantIdDate: false,
        menuDate: false,
        applicantDate: null,
        applicantDateFormatted: null,
        birthDate: null,
        ngayDuKienFormatted: null,
        ngayDuKien: null,
        dataHistory: '',

        required: [
          v => !!v || 'Thông tin bắt buộc'
        ],
        requiredSex: [
          (value) => {
            if(value || value == 0){
              return true
            } else {
              return 'Thông tin bắt buộc'
            }  
          }
        ],
        requiredCredit: [
          (value) => {
            if (value && value.length === 9) {
              const pattern = /^(([0-9]{9,9}))$/
              return pattern.test(value) || 'Số CMND gồm 9 hoặc 12 ký tự 0-9'
            } else {
              const pattern = /^(([0-9]{12,12}))$/
              return pattern.test(value) || 'Số CMND gồm 9 hoặc 12 ký tự 0-9'
            }
          },
          (value) => {
            if(value || value == 0){
              return true
            } else {
              return 'Thông tin bắt buộc'
            }  
          }
        ],
        requiredTelNo: [
          (value) => {
            const pattern = /^0([1-9]{1}\d{8})$/
            if (value) {
              return pattern.test(value) || 'Số điện thoại gồm 10 số'
            } else {
              return true
            }
          },
          (value) => {
            if(String(value).trim()){
              return true
            } else {
              return 'Thông tin bắt buộc'
            } 
          }
        ],
        telNo: (value) => {
          const pattern = /^0([1-9]{1}\d{8})$/
          if (value) {
            return pattern.test(value) || 'Số điện thoại gồm 10 số'
          } else {
            return true
          }
        },
        require: (value) => {
          if(String(value).trim()){
              return true
            } else {
              return 'Thông tin bắt buộc'
            } 
        },
        requiredBirthDate: [
          v => !!v || 'Thông tin bắt buộc'
        ],
      }
    },
    watch: {
      '$route': function (newRoute, oldRoute) {
        let vm = this
        let currentQuery = newRoute.query
        vm.getTinhThanh()
        if (String(vm.uid) === '0') {
          vm.typeAction = 'add'
        } else {
          vm.typeAction = 'update'
          vm.bindDataUpdate()
        }
      },
      tinhThanh (val) {
        this.applicantInfo.NoiO_TinhThanh_Ma = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.applicantInfo.NoiO_QuanHuyen_Ma = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.applicantInfo.NoiO_PhuongXa_Ma = val 
      },
      tinhThanhLamViec (val) {
        this.applicantInfo.NoiCT_TinhThanh_Ma = val
        this.getQuanHuyen(val)
      },
      quanHuyenLamViec (val) {
        this.applicantInfo.NoiCT_QuanHuyen_Ma = val
        this.getXaPhuong(val)
      },
      xaPhuongLamViec (val) {
        this.applicantInfo.NoiCT_PhuongXa_Ma = val 
      },
      birthDate (val) {
        this.applicantDateFormatted = this.formatDate(this.birthDate)
      },
      ngayDuKien (val) {
        this.ngayDuKienFormatted = this.formatDate(this.ngayDuKien)
      },
      menuApplicantIdDate (val) {
        val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
      }
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
      giaydiduongUpdate () {
        return this.$store.getters.getGiayDiDuongUpdate
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 1)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login' })
        return
      }
      try {
        let data = localStorage.getItem('giayDiDuong')
        vm.dataHistory = data ? JSON.parse(data) : ''
      } catch (error) {
      }
      vm.getTinhThanh()
      if (String(vm.uid) === '0') {
        vm.typeAction = 'add'
      } else {
        vm.typeAction = 'update'
        vm.bindDataUpdate()
      }
    },
    methods: {
      autoBind() {
        let vm = this
        console.log('add', $('#nhomdoituong').val())
        if ($('#nhomdoituong').val() && Number.isInteger(Number($('#nhomdoituong').val())) && $('#nhomdoituong').val().length <= 2) {
          vm.applicantInfo['NhomDoiTuong'] = Number($('#nhomdoituong').val()) - 1
        }
        vm.$refs.chondoituong.isMenuActive = false
      },
      submitForm () {
        let vm = this
        if (vm.processingAction) {
          return
        }
        vm.processingAction = true
        let validateTuoi = vm.checkTuoi()
        // let validateTuoi = true
        if (vm.$refs.formDangKy.validate()) {
          vm.formatDataInput()
          let filter = {
            data: vm.applicantInfo
          }
          // thực hiện thêm mới
          if (vm.typeAction === 'add') {
            try {
              vm.dataHistory = vm.applicantInfo ? vm.applicantInfo : ''
              localStorage.setItem('giayDiDuong', JSON.stringify(vm.applicantInfo))
            } catch (error) {
            }
          }
          if (vm.typeAction === 'add') {
            vm.$store.dispatch('createRegistration', filter).then(function (result) {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Đăng ký thành công',
                color: 'success',
              })
              vm.processingAction = false
              
              vm.tinhThanh = '01'
              vm.quanHuyen = '004'
              // vm.xaPhuong = '00148'
              vm.applicantInfo['DanToc_Ma'] = '01'
              vm.applicantInfo['QuocTich_Ma'] = 'VN'
              vm.applicantInfo['HoVaTen'] = ''
              vm.applicantInfo['GioiTinh'] = ''
              vm.applicantDateFormatted = ''
              vm.applicantInfo['CMTCCCD'] = ''
              vm.applicantInfo['SoTheBHYT'] = ''
              vm.applicantInfo['SoDienThoai'] = ''
              vm.$refs.formDangKy.resetValidation()
              $('html, body').animate({
                  scrollTop: $('#xemdanhsach').offset().top,
                },
                500,
                'linear'
              )
              $("#hoten").focus()
            }).catch(function () {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Đăng ký không thành công',
                color: 'error',
              })
              vm.processingAction = false
            })
          } else {
            // thực hiện cập nhật
            filter['id'] = vm.uid
            vm.$store.dispatch('updateRegistration', filter).then(function (result) {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.processingAction = false
              vm.$router.push('/pages/danh-sach-dang-ky-tiem-moi')
            }).catch(function () {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật không thành công',
                color: 'error',
              })
              vm.processingAction = false
            })
          }
        } else {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Vui lòng nhập đầy đủ các thông tin bắt buộc',
            color: 'error',
          })
          $('html, body').animate({
              scrollTop: $('#xemdanhsach').offset().top,
            },
            500,
            'linear'
          )
        }
      },
      copyContent () {
        let vm = this
        try {
          let data = localStorage.getItem('giayDiDuong')
          vm.dataHistory = data ? JSON.parse(data) : ''
        } catch (error) {
        }
        vm.applicantInfo['Email'] = vm.dataHistory['Email']
        vm.applicantInfo['NhomDoiTuong'] = vm.dataHistory['NhomDoiTuong']
        vm.applicantInfo['DiaChiNoiO'] = vm.dataHistory['DiaChiNoiO']
        vm.tinhThanh = vm.dataHistory['TinhThanh_Ma']
        vm.quanHuyen = vm.dataHistory['QuanHuyen_Ma']
        vm.xaPhuong = vm.dataHistory['PhuongXa_Ma']
        vm.applicantInfo['DiaBanCoSo_ID'] = vm.dataHistory['DiaBanCoSo_ID']
        vm.coSoYTe = vm.dataHistory['CoSoYTe_Ma']
        vm.applicantInfo['DanToc_Ma'] = vm.dataHistory['DanToc_Ma']
        vm.applicantInfo['QuocTich_Ma'] = vm.dataHistory['QuocTich_Ma']
        vm.applicantInfo['DonViCongTac'] = vm.dataHistory['DonViCongTac']
        vm.applicantInfo['TienSuDiUng'] = vm.dataHistory['TienSuDiUng']
        vm.applicantInfo['CacBenhLyDangMac'] = vm.dataHistory['CacBenhLyDangMac']
        vm.applicantInfo['CacThuocDangDung'] = vm.dataHistory['CacThuocDangDung']
        vm.ngayDuKienFormatted = vm.dataHistory['NgayDangKi']
        vm.applicantInfo['GhiChu'] = vm.dataHistory['GhiChu']
      },
      huyDangKy () {
        let vm = this
        vm.$refs.formDangKy.reset()
        vm.$refs.formDangKy.resetValidation()
      },
      bindDataUpdate () {
        let vm = this
        vm.applicantInfo.CacBenhLyDangMac = vm.giaydiduongUpdate.cacBenhLyDangMac
        vm.applicantInfo.CacThuocDangDung = vm.giaydiduongUpdate.cacThuocDangDung
        vm.applicantInfo['CMTCCCD'] = vm.giaydiduongUpdate.cmtcccd
        vm.coSoYTe = vm.giaydiduongUpdate.coSoYTeMa
        vm.applicantInfo.DanToc_Ma = vm.giaydiduongUpdate.danTocMa
        vm.applicantInfo.DiaBanCoSo_ID = vm.giaydiduongUpdate.diaBanCoSoID
        vm.applicantInfo.DiaChiNoiO = vm.giaydiduongUpdate.diaChiNoiO
        vm.applicantInfo.DonViCongTac = vm.giaydiduongUpdate.donViCongTac
        vm.applicantInfo.Email = vm.giaydiduongUpdate.email
        vm.applicantInfo.GhiChu = vm.giaydiduongUpdate.ghiChu
        vm.applicantInfo.GioiTinh = vm.giaydiduongUpdate.gioiTinh
        vm.applicantInfo.HoVaTen = vm.giaydiduongUpdate.hoVaTen
        vm.applicantInfo.MaSoBHXH = vm.giaydiduongUpdate.maSoBHXH
        // vm.ngayDuKienFormatted = vm.convertDate(vm.giaydiduongUpdate.ngayDangKi)
        // vm.applicantDateFormatted = vm.convertDate(vm.giaydiduongUpdate.ngaySinh)
        vm.ngayDuKienFormatted = vm.giaydiduongUpdate.ngayDangKi
        vm.applicantDateFormatted = vm.giaydiduongUpdate.ngaySinh
        vm.applicantInfo.NgheNghiep = vm.giaydiduongUpdate.ngheNghiep
        vm.applicantInfo.NhomDoiTuong = vm.giaydiduongUpdate.nhomDoiTuong
        vm.tinhThanh = vm.giaydiduongUpdate.tinhThanhMa
        vm.quanHuyen = vm.giaydiduongUpdate.quanHuyenMa
        vm.xaPhuong = vm.giaydiduongUpdate.phuongXaMa
        vm.applicantInfo.QuocTich_Ma = vm.giaydiduongUpdate.quocTichMa
        vm.applicantInfo.SoDienThoai = vm.giaydiduongUpdate.soDienThoai
        vm.applicantInfo.SoTheBHYT = vm.giaydiduongUpdate.soTheBHYT
        vm.applicantInfo.TienSuDiUng = vm.giaydiduongUpdate.tienSuDiUng
        vm.applicantInfo.TinhTrangDangKi = vm.giaydiduongUpdate.tinhTrangDangKi
      },
      formatDataInput () {
        let vm = this
        try {
          if (vm.tinhThanh) {
            let obj = vm.listTinhThanh.find(function (item) {
              return item.tinhThanhMa == vm.tinhThanh
            })
            vm.applicantInfo.TinhThanh_Ma = vm.tinhThanh
            vm.applicantInfo.TinhThanh_Ten = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyen) {
            let obj = vm.listQuanHuyen.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyen
            })
            vm.applicantInfo.QuanHuyen_Ma = vm.quanHuyen
            vm.applicantInfo.QuanHuyen_Ten = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuong) {
            let obj = vm.listXaPhuong.find(function (item) {
              return item.phuongXaMa == vm.xaPhuong
            })
            vm.applicantInfo.PhuongXa_Ma = vm.xaPhuong
            vm.applicantInfo.PhuongXa_Ten = obj ? obj['phuongXaTen'] : ''
          }
          if (vm.coSoYTe) {
            let obj = vm.listCoSoYTe.find(function (item) {
              return item.maCoSo == vm.coSoYTe
            })
            vm.applicantInfo.CoSoYTe_Ma = vm.coSoYTe
            vm.applicantInfo.CoSoYTe_Ten = obj ? obj['tenCoSo'] : ''
          }
          vm.applicantInfo.NgaySinh = vm.applicantDateFormatted
          vm.applicantInfo.NgayDangKi = vm.ngayDuKienFormatted
          // let splitNgayDangKy = String(vm.ngayDuKienFormatted).split('/')
          // vm.applicantInfo.NgayDangKi = splitNgayDangKy[2] + splitNgayDangKy[1] + splitNgayDangKy[0]
          // let lengthDate = String(vm.applicantDateFormatted).trim().length
          // let splitDate = String(vm.applicantDateFormatted).split('/')
          // if (lengthDate && lengthDate == 4) {
          //   vm.applicantInfo.NgaySinh = vm.applicantDateFormatted + '0000'
          // } else if (lengthDate && lengthDate > 4 && splitDate.length === 3) {
          //   vm.applicantInfo.NgaySinh = splitDate[2] + splitDate[1] + splitDate[0]
          // }
          console.log('applicantInfo', vm.applicantInfo)
        } catch (error) {
          vm.processingAction = false
        }
      },
      checkTuoi () {
        let vm = this
        let tuoi = ''
        let year = ''
        let lengthDate = String(vm.applicantDateFormatted).trim().length
        let splitDate = String(vm.applicantDateFormatted).split('/')
        let namTiem = (new Date()).getFullYear()
        // if (vm.ngayDuKienFormatted) {
        //   namTiem = String(vm.ngayDuKienFormatted).split('/')[2]
        // }
        if (lengthDate && lengthDate == 4) {
          year = Number(vm.applicantDateFormatted)
          tuoi = Number(namTiem) - year
        } else if (lengthDate && lengthDate > 4 && splitDate.length === 3) {
          let isoDate = vm.parseDate(vm.applicantDateFormatted)
          tuoi = Math.floor((new Date() - new Date(isoDate).getTime()) / 3.15576e+10)
        }
        if (tuoi >= 18) {
          return true
        } else {
          return false
        }
      },
      getTinhThanh () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getDanhMucTinhThanh', filter).then(function (result) {
          vm.listTinhThanh = result ? result : []
          if (vm.tinhThanh && vm.listTinhThanh.length) {
            vm.getQuanHuyen(vm.tinhThanh)
          }
          if (vm.tinhThanhLamViec && vm.listTinhThanh.length) {
            vm.getQuanHuyen(vm.tinhThanhLamViec, 'noilamviec')
          }
        }).catch(function(error){
          if (error && error.response && error.response.status == 401) {
            vm.$router.push({ path: '/login' })
          }
        })
      },
      getQuanHuyen (code, type) {
        let vm = this
        if (!code) {
          return
        }
        let obj = vm.listTinhThanh.find(function (item) {
          return item.tinhThanhMa == code
        })
        let filter = {
          idParent: obj['id']
        }
        vm.$store.dispatch('getDanhMucQuanHuyen', filter).then(function (result) {
          if (type) {
            vm.listQuanHuyenLamViec = result ? result : []
            if (vm.quanHuyenLamViec) {
              vm.getXaPhuong(vm.quanHuyenLamViec, 'noilamviec')
            }
          } else {
            vm.listQuanHuyen = result ? result : []
            if (vm.quanHuyen) {
              vm.getXaPhuong(vm.quanHuyen)
            }
          }
        })
      },
      getXaPhuong (code, type) {
        let vm = this
        if (!code) {
          return
        }
        let obj = vm.listQuanHuyen.find(function (item) {
          return item.quanHuyenMa == code
        })
        if (!obj) {
          return
        }
        let filter = {
          idParent: obj['id']
        }
        vm.$store.dispatch('getDanhMucXaPhuong', filter).then(function (result) {
          if (type) {
            vm.listXaPhuongLamViec = result ? result : []
          } else {
            vm.listXaPhuong = result ? result : []
          }
        })
      },
      showDanhSach () {
        let vm = this
        vm.$router.push({ path: '/pages/danh-sach-dang-ky-tiem-moi' })
      },
      nextFocus(id) {
        $("#"+id).focus()
      },
      formatHoTen () {
        let vm = this
        if (vm.applicantInfo.HoVaTen) {
          let str = String(vm.applicantInfo.HoVaTen).toLocaleLowerCase()
          let arr = str.split(" ");
          for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1)
          }
          let str2 = arr.join(" ")
          vm.applicantInfo.HoVaTen = str2
        }
      },
      convertDate (dateInput) {
        if (dateInput) {
          let date = ''
          try {
            date = dateInput.slice(6,8) + '/' + dateInput.slice(4,6) + '/' + dateInput.slice(0,4)
          } catch (error) {
          }
          return date
        } else {
          return ''
        }
      },
      formatBirthDate () {
        let vm = this
        let lengthDate = String(vm.applicantDateFormatted).trim().length
        let splitDate = String(vm.applicantDateFormatted).split('/')
        let currentYear = (new Date()).getFullYear()
        let tuoi = 0
        if (lengthDate && lengthDate == 4) {
        } else if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.applicantDateFormatted = vm.translateDate(vm.applicantDateFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.applicantDateFormatted)
          vm.applicantDateFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.applicantDateFormatted = ''
        }
        if (vm.applicantDateFormatted) {
          if (lengthDate === 4) {
            tuoi = currentYear - Number(vm.applicantDateFormatted)
          } else {
            let isoDate = vm.parseDate(vm.applicantDateFormatted)
            tuoi = Math.floor((new Date() - new Date(isoDate).getTime()) / 3.15576e+10)
          }
          if (tuoi < 18) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Người đăng ký chưa đủ 18 tuổi',
              color: 'error',
            })
          }
        }
        
      },
      formatNgayTiem () {
        let vm = this
        let lengthDate = String(vm.ngayDuKienFormatted).trim().length
        let splitDate = String(vm.ngayDuKienFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayDuKienFormatted = vm.translateDate(vm.ngayDuKienFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayDuKienFormatted)
          vm.ngayDuKienFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayDuKienFormatted = ''
        }
      },
      translateDate (date) {
        if (!date) return null
        const [day, month, year] = date.split('/')
        return `${day.padStart(2, '0')}/${month.padStart(2, '0')}/${year}`
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
      getMaxdate () {
        let date = new Date()
        return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`
      },
      getMindate () {
        let date = new Date()
        return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`
      },
    },
  }
</script>

<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>
