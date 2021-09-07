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
                    v-model="applicantInfo['hoVaTen']"
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
                    v-model="applicantInfo['cmtcccd']"
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
                    v-model="applicantInfo['soDienThoai']"
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
                    v-model="applicantInfo['email']"
                    outlined
                    placeholder="email"
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
                  <div class="mb-2">Địa chỉ nơi ở/ cư trú <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['noiODiaChi']"
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
                      @keyup.enter="nextFocus('congtylamviec')"
                  ></v-autocomplete>
                </v-col>
                                
              </v-row>
              <!-- row 4 -->
              <v-row class="pt-3">
                <v-col
                  cols="12"
                  class="pb-0"
                >
                  <div class="mb-2">Đơn vị/ công ty làm việc <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['noiCtTenCoQuan']"
                    :rules="required"
                    required
                    outlined
                    placeholder=""
                    dense
                    id="congtylamviec"
                    hide-details="auto"
                    @keyup.enter="nextFocus('noilamviec')"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  class="pb-0"
                >
                  <div class="mb-2">Địa chỉ nơi làm việc <span style="color:red">(*)</span></div>
                  <v-text-field
                    v-model="applicantInfo['noiCtDiaChi']"
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
                      @keyup.enter="nextFocus('starttime')"
                  ></v-autocomplete>
                </v-col>
                                
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  class="pb-0"
                >
                  <div>
                    <span class="font-weight-bold">LỊCH LÀM VIỆC</span>
                    <i> (Chọn các ngày trong tuần hoặc ngày cụ thể) </i>
                  </div>
                </v-col>
              </v-row>
              <!--  -->
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0"
                >
                  <div class="mb-3">Các ngày trong tuần <span style="color:red">(*)</span></div>
                  <div class="pt-2">
                    <v-chip
                      v-for="(item, index) in weekDay" v-bind:key="index"
                      class="mr-2"
                      :color="item.selected ? 'primary' : 'dark'"
                      label
                      @click="changeWeekDay(index)"
                    >
                      <span style="font-size: 12px">{{item.name}}</span>
                    </v-chip>
                  </div>
                </v-col>
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0"
                >
                  <div class="mb-3">Các ngày cụ thể <span style="color:red">(*)</span></div>
                  <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    :return-value.sync="dates"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-combobox
                        v-model="dates"
                        multiple
                        chips
                        placeholder="Các ngày cụ thể"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        dense
                        hide-details="auto"
                        outlined
                        clearable
                      >
                        <template v-slot:selection="data">
                          <v-chip
                            class="ma-2"
                            color="green"
                            text-color="white"
                          >
                            {{formatDate(data.item)}}
                          </v-chip>
                        </template>
                      </v-combobox>
                    </template>
                    <v-date-picker
                      v-model="dates"
                      multiple
                      no-title
                      scrollable
                      locale="vi"
                    >
                      <v-spacer></v-spacer>
                      <v-btn
                        text
                        color="primary"
                        @click="menu = false"
                      >
                        Thoát
                      </v-btn>
                      <v-btn
                        text
                        color="primary"
                        @click="$refs.menu.save(dates)"
                      >
                        Đồng ý
                      </v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-col>
                
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0"
                >
                  <div class="mb-2">Thời gian làm việc<span style="color:red">(*)</span></div>
                  <v-layout wrap>
                    
                    <v-text-field
                      style="max-width: 120px;"
                      label="Từ:"
                      class="flex mr-2"
                      v-model="timeStart"
                      placeholder="hh:mm"
                      v-mask="'##:##'"
                      dense
                      outlined
                      hide-details="auto"
                      id="starttime"
                      @keyup.enter="nextFocus('endtime')"
                      :rules="required"
                      required
                    ></v-text-field>
                  
                    <v-text-field
                      style="max-width: 120px;"
                      label="Đến:"
                      class="flex ml-2"
                      v-model="timeEnd"
                      placeholder="hh:mm"
                      v-mask="'##:##'"
                      dense
                      outlined
                      hide-details="auto"
                      id="endtime"
                      @keyup.enter="nextFocus('ngaycap')"
                      :rules="required"
                      required
                    ></v-text-field>
                  </v-layout>
                </v-col>
              </v-row>
              <!-- -->
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                  class="pb-0 pt-3"
                >
                  <div class="mb-2">Ngày cấp giấy đi đường</div>
                  <v-text-field
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
                  <div class="mb-2">Ngày hết hạn <span style="color:red"></span></div>
                  <v-text-field
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
                    v-model="applicantInfo['ghiChu']"
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
        dates: [],
        menu: false,
        weekDay: [
          {name: 'Thứ 2', value: 2, selected: false},
          {name: 'Thứ 3', value: 3, selected: false},
          {name: 'Thứ 4', value: 4, selected: false},
          {name: 'Thứ 5', value: 5, selected: false},
          {name: 'Thứ 6', value: 6, selected: false},
          {name: 'Thứ 7', value: 7, selected: false},
          {name: 'Chủ nhật', value: 0, selected: false}
        ],
        weekDayDefault: [
          {name: 'Thứ 2', value: 2, selected: false},
          {name: 'Thứ 3', value: 3, selected: false},
          {name: 'Thứ 4', value: 4, selected: false},
          {name: 'Thứ 5', value: 5, selected: false},
          {name: 'Thứ 6', value: 6, selected: false},
          {name: 'Thứ 7', value: 7, selected: false},
          {name: 'Chủ nhật', value: 0, selected: false}
        ],
        timeStart: '',
        timeEnd: '',
        workTime: {
          ngayTuan: [],
          ngayThang: [],
          tuGio: '',
          denGio: ''
        },
        applicantInfo: {
          hoVaTen: '',
          cmtcccd: '',
          soDienThoai: '',
          email: '',
          noiCtTenCoQuan: '',
          noiCtDiaChi: '',
          noiCtTinhThanhMa: '',
          noiCtTinhThanhTen: '',
          noiCtQuanHuyenMa: '',
          noiCtQuanHuyenTen: '',
          noiCtPhuongXaMa: '',
          noiCtPhuongXaTen: '',
          noiODiaChi: '',
          noiOTinhThanhMa: '',
          noiOTinhThanhTen: '',
          noiOQuanHuyenMa: '',
          noiOQuanHuyenTen: '',
          noiOPhuongXaMa: '',
          noiOPhuongXaTen: '',
          lichLamViec: '',
          uyBanNhanDanID: '',
          ngayCap: '',
          thoiHan: '',
          ghiChu: '',
          status: 0
        },
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
      dates (val) {
        if (val && val.length) {
          // this.weekDay = this.weekDayDefault
        }
      },
      tinhThanh (val) {
        this.applicantInfo.noiOTinhThanhMa = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.applicantInfo.noiOQuanHuyenMa = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.applicantInfo.noiOPhuongXaMa = val 
      },
      tinhThanhLamViec (val) {
        this.applicantInfo.noiCtTinhThanhMa = val
        this.getQuanHuyen(val, 'noilamviec')
      },
      quanHuyenLamViec (val) {
        this.applicantInfo.noiCtQuanHuyenMa = val
        this.getXaPhuong(val, 'noilamviec')
      },
      xaPhuongLamViec (val) {
        this.applicantInfo.noiCtPhuongXaMa = val 
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
      vm.$store.commit('SET_INDEXTAB', 3)
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
      changeWeekDay(index) {
        let vm = this
        vm.weekDay[index]['selected'] = !vm.weekDay[index]['selected']
        let exits = vm.weekDay.filter(function (item) {
          return item.selected
        })
        if (exits && exits.length) {
          // vm.dates = []
        }
      },
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
        // vm.formatDataInput()
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
            vm.$store.dispatch('createGiayDiDuong', filter).then(function (result) {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Đăng ký thành công',
                color: 'success',
              })
              vm.processingAction = false
              // vm.tinhThanh = '01'
              // vm.quanHuyen = '004'
              // vm.tinhThanhLamViec = '01'
              // vm.quanHuyenLamViec = '004'
              vm.applicantInfo.hoVaTen = ''
              vm.applicantInfo['cmtcccd'] = ''
              vm.applicantInfo.email = ''
              vm.applicantInfo.soDienThoai = ''
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
            vm.$store.dispatch('updateGiayDiDuong', filter).then(function (result) {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.processingAction = false
              vm.$router.push('/pages/danh-sach-di-duong-yeu-cau')
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
        vm.applicantInfo.hoVaTen = vm.dataHistory.hoVaTen
        vm.applicantInfo['cmtcccd'] = vm.dataHistory['cmtcccd']
        vm.applicantInfo.email = vm.dataHistory.email
        vm.applicantInfo.soDienThoai = vm.dataHistory.soDienThoai
        vm.applicantInfo.noiODiaChi = vm.dataHistory.noiODiaChi
        vm.tinhThanh = vm.dataHistory.noiOTinhThanhMa
        vm.quanHuyen = vm.dataHistory.noiOQuanHuyenMa
        vm.xaPhuong = vm.dataHistory.noiOPhuongXaMa
        vm.applicantInfo.noiCtTenCoQuan = vm.dataHistory.noiCtTenCoQuan
        vm.applicantInfo.noiCtDiaChi = vm.dataHistory.noiCtDiaChi
        vm.tinhThanhLamViec = vm.dataHistory.noiCtTinhThanhMa
        vm.quanHuyenLamViec = vm.dataHistory.noiCtQuanHuyenMa
        vm.xaPhuongLamViec = vm.dataHistory.noiCtPhuongXaMa

        vm.ngayDuKienFormatted = vm.dataHistory.thoiHan
        vm.applicantDateFormatted = vm.dataHistory.ngayCap
        vm.applicantInfo.ghiChu = vm.dataHistory.ghiChu
        vm.converLichLamViec(vm.dataHistory.lichLamViec)
      },
      huyDangKy () {
        let vm = this
        vm.$refs.formDangKy.reset()
        vm.$refs.formDangKy.resetValidation()
      },
      bindDataUpdate () {
        let vm = this
        vm.applicantInfo.hoVaTen = vm.giaydiduongUpdate.hoVaTen
        vm.applicantInfo['cmtcccd'] = vm.giaydiduongUpdate.cmtcccd
        vm.applicantInfo.email = vm.giaydiduongUpdate.email
        vm.applicantInfo.soDienThoai = vm.giaydiduongUpdate.soDienThoai
        vm.applicantInfo.noiODiaChi = vm.giaydiduongUpdate.noiODiaChi
        vm.tinhThanh = vm.giaydiduongUpdate.noiOTinhThanhMa
        vm.quanHuyen = vm.giaydiduongUpdate.noiOQuanHuyenMa
        vm.xaPhuong = vm.giaydiduongUpdate.noiOPhuongXaMa
        vm.applicantInfo.noiCtDiaChi = vm.giaydiduongUpdate.noiCtDiaChi
        vm.applicantInfo.noiCtTenCoQuan = vm.giaydiduongUpdate.noiCtTenCoQuan
        vm.tinhThanhLamViec = vm.giaydiduongUpdate.noiCtTinhThanhMa
        vm.quanHuyenLamViec = vm.giaydiduongUpdate.noiCtQuanHuyenMa
        vm.xaPhuongLamViec = vm.giaydiduongUpdate.noiCtPhuongXaMa

        vm.ngayDuKienFormatted = vm.giaydiduongUpdate.thoiHan
        vm.applicantDateFormatted = vm.giaydiduongUpdate.ngayCap
        vm.applicantInfo.ghiChu = vm.giaydiduongUpdate.ghiChu     
        vm.applicantInfo.status = vm.giaydiduongUpdate.status
        vm.converLichLamViec(vm.giaydiduongUpdate.lichLamViec,'string')
      },
      formatDataInput () {
        let vm = this
        vm.applicantInfo.uyBanNhanDanID = vm.userLogin['uyBanNhanDanId']
        try {
          if (vm.tinhThanh) {
            let obj = vm.listTinhThanh.find(function (item) {
              return item.tinhThanhMa == vm.tinhThanh
            })
            vm.applicantInfo.noiOTinhThanhMa = vm.tinhThanh
            vm.applicantInfo.noiOTinhThanhTen = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyen) {
            let obj = vm.listQuanHuyen.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyen
            })
            vm.applicantInfo.noiOQuanHuyenMa = vm.quanHuyen
            vm.applicantInfo.noiOQuanHuyenTen = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuong) {
            let obj = vm.listXaPhuong.find(function (item) {
              return item.phuongXaMa == vm.xaPhuong
            })
            vm.applicantInfo.noiOPhuongXaMa = vm.xaPhuong
            vm.applicantInfo.noiOPhuongXaTen = obj ? obj['phuongXaTen'] : ''
          }
          if (vm.tinhThanhLamViec) {
            let obj = vm.listTinhThanh.find(function (item) {
              return item.tinhThanhMa == vm.tinhThanhLamViec
            })
            vm.applicantInfo.noiCtTinhThanhMa = vm.tinhThanhLamViec
            vm.applicantInfo.noiCtTinhThanhTen = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyenLamViec) {
            let obj = vm.listQuanHuyenLamViec.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyenLamViec
            })
            vm.applicantInfo.noiCtQuanHuyenMa = vm.quanHuyenLamViec
            vm.applicantInfo.noiCtQuanHuyenTen = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuongLamViec) {
            let obj = vm.listXaPhuongLamViec.find(function (item) {
              return item.phuongXaMa == vm.xaPhuongLamViec
            })
            vm.applicantInfo.noiCtPhuongXaMa = vm.xaPhuongLamViec
            vm.applicantInfo.noiCtPhuongXaTen = obj ? obj['phuongXaTen'] : ''
          }
          vm.applicantInfo.ngayCap = vm.applicantDateFormatted
          vm.applicantInfo.thoiHan = vm.ngayDuKienFormatted
          let weekDayArr = vm.weekDay.filter(function(item) {
            return item.selected
          })
          vm.workTime['ngayTuan'] = weekDayArr.map(function(item) {
            return item['value']
          })
          // vm.workTime['tuGio'] = vm.timeStart.replace(':','')
          // vm.workTime['denGio'] = vm.timeEnd.replace(':','')
          vm.workTime['tuGio'] = vm.timeStart
          vm.workTime['denGio'] = vm.timeEnd
          vm.applicantInfo['lichLamViec'] = vm.workTime
          if (vm.dates && vm.dates.length) {
            let dateInput = []
            vm.dates.forEach((element, index) => {
              let day = vm.formatDate(element)
              dateInput.push(day)
            })
            vm.workTime['ngayThang'] = dateInput
          }
          console.log('work', vm.workTime)
          console.log('applicantInfo', vm.applicantInfo)
        } catch (error) {
          vm.processingAction = false
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
        let obj = ''
        if (type) {
          obj = vm.listQuanHuyenLamViec.find(function (item) {
            return item.quanHuyenMa == code
          })
        } else {
          obj = vm.listQuanHuyen.find(function (item) {
            return item.quanHuyenMa == code
          })
        }
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
      converLichLamViec (data, type) {
        let vm = this
        let dataConvert = ''
        if (type) {
          dataConvert = JSON.parse(data)
        } else {
          dataConvert = data
        }
        let ngayTuan = dataConvert.ngayTuan
        let ngayThang = dataConvert.ngayThang
        let timeStart = dataConvert.tuGio
        let timeEnd = dataConvert.denGio
        if (ngayTuan && ngayTuan.length) {
          vm.weekDay.forEach((element, index) => {
            let find = ngayTuan.filter(function (item) {
              return item == element['value']
            })
            if (find && find.length) {
              vm.weekDay[index]['selected'] = true
            }
          })
        }
        if (ngayThang && ngayThang.length) {
          let dateInput = []
          ngayThang.forEach((element, index) => {
            let day = vm.parseDate(element)
            dateInput.push(day)
          })
          vm.dates = dateInput
        }
        vm.timeStart = timeStart
        vm.timeEnd = timeEnd
        // if (timeStart) {
        //   vm.timeStart = String(timeStart).slice(0,2) + ':' + String(timeStart).slice(2,4)
        // }
        // if (timeEnd) {
        //   vm.timeEnd = String(timeEnd).slice(0,2) + ':' + String(timeEnd).slice(2,4)
        // }
      },
      showDanhSach () {
        let vm = this
        vm.$router.push({ path: '/pages/danh-sach-di-duong-yeu-cau' })
      },
      nextFocus(id) {
        $("#"+id).focus()
      },
      formatHoTen () {
        let vm = this
        if (vm.applicantInfo.hoVaTen) {
          let str = String(vm.applicantInfo.hoVaTen).toLocaleLowerCase()
          let arr = str.split(" ");
          for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1)
          }
          let str2 = arr.join(" ")
          vm.applicantInfo.hoVaTen = str2
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
