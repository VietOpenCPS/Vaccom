<template>
  <div>
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="DANH SÁCH ĐĂNG KÝ TIÊM CHÍNH THỨC"
        class="px-5 py-3"
      >
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" v-on:trigger-search="searchDangKyTiem" v-on:trigger-cancel="cancelSearchDangKyTiem"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </span>
            <v-btn color="#0072bc" small class="mx-0 mr-4" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn>
            <v-btn color="orange" small class="mx-0" @click.stop="translateStatus('multiple')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-backup-restore
              </v-icon>
              Rút đăng ký
            </v-btn>  
          </div>
          <v-flex xs12 style="text-align: right;">
            <v-checkbox
              color="#0072bc"
              class="mt-0 checkboxCmt d-inline-block"
              v-model="dangkythieuthongtin"
            >
              <template v-slot:label>
                <span style="font-weight: 500;color: #0072bc">LỌC ĐĂNG KÝ THIẾU THÔNG TIN</span>
              </template>
            </v-checkbox>
          </v-flex>
          <v-data-table
            v-model="selected"
            show-select
            :headers="headers"
            :items="items"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
          >
            <template v-slot:header.muiTiemChung="{ header }">
              <div>
                <div class="py-2" style="border-bottom: 1px solid #dedede;">Thông tin mũi tiêm</div>
                <v-layout wrap>
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;">
                    <p class="py-2 mb-0">Mũi 1</p>
                  </v-flex>
                  <v-flex class="xs12 md6">
                    <p class="py-2 mb-0">Mũi 2</p>
                  </v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.index="{ item, index }">
              <span>{{ (page+1) * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.hoVaTen="{ item, index }">
              <div :style="item.kiemTraTrung == 2 ? 'background: yellow' : ''" :title="item.kiemTraTrung == 2 ? 'Đăng ký trùng lặp' : ''">
                <p class="mb-0" style="font-weight: 500;">{{ item.hoVaTen}}</p>
                <p class="mb-2" style="color: blue">Ngày sinh: {{ item.ngaySinh }}</p>
              </div>
            </template>
            <template v-slot:item.nhomDoiTuong="{ item, index }">
                <div class="mb-2">{{ item.nhomDoiTuong}}</div>
            </template>
            <template v-slot:item.diaChiNoiO="{ item, index }">
                <p class="mb-2">{{ item.diaChiNoiO}} - {{item.phuongXaTen}} - {{item.quanHuyenTen}} - {{item.tinhThanhTen}}</p>
            </template>
            <template v-slot:item.muiTiemChung="{ item, index }">
              <div style="width: 250px;height: 100%;">
                <v-layout wrap style="height: 100%;" v-if="item.muiTiemChung && item.muiTiemChung.length">
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;" v-for="(item2, index2) in item.muiTiemChung" :key="index2">
                    <p class="py-2 mb-0" v-if="item.muiTiemChung && item.muiTiemChung[index2]['lanTiem'] == 1" style="text-align: left;">
                      <span>Ngày tiêm: {{item.muiTiemChung[index2]['ngayTiemChung']}}</span><br>
                      <span>Loại thuốc: {{item.muiTiemChung[index2]['loaiThuocTiem']}}</span><br>
                      <span>Địa điểm: {{item.muiTiemChung[index2]['diaDiemTiemChung']}}</span>
                    </p>
                  </v-flex>
                  <v-flex class="xs12 md6" v-for="(item2, index2) in item.muiTiemChung" :key="index2">
                    <p class="py-2 mb-0 pl-2" v-if="item.muiTiemChung && item.muiTiemChung[index2]['lanTiem'] == 2" style="text-align: left;">
                      <span>Ngày tiêm: {{item.muiTiemChung[index2]['ngayTiemChung']}}</span><br>
                      <span>Loại thuốc: {{item.muiTiemChung[index2]['loaiThuocTiem']}}</span><br>
                      <span>Địa điểm: {{item.muiTiemChung[index2]['diaDiemTiemChung']}}</span>
                    </p>
                  </v-flex>
                </v-layout>
                <v-layout wrap style="height: 100%;" v-else>
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;"></v-flex>
                  <v-flex class="xs12 md6"></v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.action="{ item }">
              <div style="width: 150px">
                <!-- <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="editRegistration(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa thông tin</span>
                </v-tooltip> -->
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="translateStatus(item)" color="orange" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-backup-restore</v-icon>
                    </v-btn>
                  </template>
                  <span>Rút đăng ký</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="viewDetail(item)" color="blue" text icon class="ml-2" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-account-details-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>Thông tin chi tiết</span>
                </v-tooltip>
                <v-tooltip top v-if="userLogin['role_name'] == 'QuanTriHeThong' || userLogin['role_name'] == 'QuanTriCoSo' || userLogin['role_name'] == 'CanBoYTe'">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="addMuiTiem(item)" color="blue" text icon class="ml-2" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-plus</v-icon>
                    </v-btn>
                  </template>
                  <span>Thêm thông tin mũi tiêm</span>
                </v-tooltip>
              </div>
              
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>

      <v-dialog
        max-width="1000"
        v-model="dialogDetail"
        fullscreen
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title>Thông tin người đăng ký tiêm</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogDetail = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="pt-0">
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-account-check-outline
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">THÔNG TIN NGƯỜI TIÊM</span>
            </div>
            <v-layout wrap>
              <v-text-field
                  class="flex xs12 md3"
                  v-model="detaiInfo.hoVaTen"
                  outlined
                  label="Họ tên"
                  placeholder="Họ tên"
                  dense
                  readonly
                  hide-details="auto"
              ></v-text-field>
              <v-text-field
                  label="Giới tính"
                  placeholder="Giới tính"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="formatSex(detaiInfo.gioiTinh)"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Ngày sinh"
                  placeholder="Ngày sinh"
                  class="flex xs12 md3 pl-2 mb-2"
                  v-model="detaiInfo.ngaySinh"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số CMND/ CCCD"
                  placeholder="Số CMND/ CCCD"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.cmtcccd ? detaiInfo.cmtcccd : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số thẻ BHYT"
                  placeholder="Số thẻ BHYT"
                  class="flex xs12 md3 mb-2"
                  :value="detaiInfo.soTheBHYT ? detaiInfo.soTheBHYT : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số điện thoại"
                  placeholder="Số điện thoại"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.soDienThoai ? detaiInfo.soDienThoai : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Email"
                  placeholder="Email"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.email ? detaiInfo.email : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Địa chỉ"
                  placeholder="Địa chỉ"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.diaChiNoiO"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Nhóm đối tượng"
                  placeholder="Nhóm đối tượng"
                  class="flex xs12 md3 mb-2"
                  :value="detaiInfo.nhomDoiTuong"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Đơn vị công tác"
                  placeholder="Đơn vị công tác"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.donViCongTac ? detaiInfo.donViCongTac : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Ngày đăng ký tiêm"
                  placeholder="Ngày đăng ký tiêm"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.ngayDangKi ? detaiInfo.ngayDangKi : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
            </v-layout>
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-history
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">LỊCH SỬ TIÊM</span>
            </div>
            <v-layout wrap v-if="detaiInfo['muiTiemChung'] && detaiInfo['muiTiemChung'].length">
              <v-card outlined class="pa-2 mr-2" max-width="450" min-width="350" v-for="(item, index) in detaiInfo['muiTiemChung']" v-bind:key="index">
                <div class="mb-2">
                  <v-icon size=22 color="green" class="mr-3" >
                      mdi-checkbox-marked-circle-outline
                  </v-icon>
                  <span style="font-weight: bold">MŨI TIÊM {{item['lanTiem']}}</span>
                </div>
                <p class="mb-2">Tên vắc xin: {{item['loaiThuocTiem']}}</p>
                <p class="mb-2">Nơi sản xuất: {{item['noiSanXuat']}}</p>
                <p class="mb-2">Lô vắc xin: {{item['soLoThuoc']}}</p>
                <p class="mb-2">Ngày tiêm: {{item['ngayTiemChung']}}</p>
                <p class="mb-2">Địa điểm tiêm: {{item['diaDiemTiemChung']}}</p>
              </v-card>
            </v-layout>
            <p v-else>Chưa có lịch sử tiêm chủng</p>
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-history
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">THÔNG TIN LỊCH HẸN</span>
            </div>
            <v-layout wrap v-if="detaiInfo['phieuHenTiem'] && detaiInfo['phieuHenTiem'].length">
              <v-card  outlined class="pa-2 mr-2" max-width="450" min-width="350" v-for="(item, index) in detaiInfo['phieuHenTiem']" v-bind:key="index">
                <div class="mb-2">
                  <v-icon size=22 color="green" class="mr-3" >
                      mdi-checkbox-marked-circle-outline
                  </v-icon>
                  <span style="font-weight: bold">Lần tiêm {{item['lanTiem']}}</span>
                </div>
                <p class="mb-2">Ngày hẹn tiêm: {{item['ngayHenTiem']}}</p>
                <p class="mb-2">Giờ hẹn tiêm: {{item['gioHenTiem']}}</p>
                <p class="mb-2">Tình trạng: {{formatTinhTrangTiem(item['tinhTrangXacNhan'])}}</p>
              </v-card>
            </v-layout>
            <p v-else>Chưa có thông tin hẹn tiêm</p>
          </v-card-text>
        </v-card>
      </v-dialog>
      <v-dialog
        max-width="1000"
        v-model="dialogAddMuiTiem"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thêm thông tin tiêm chủng</v-toolbar-title>
            <v-toolbar-title v-else>Cập nhật thông tin</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogAddMuiTiem = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formAddMuiTiem"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                  <v-text-field
                      class="flex xs12 md4 pr-2"
                      v-model="thongTinMuiTiem['HoVaTen']"
                      outlined
                      :rules="required"
                      required
                      label="Họ tên"
                      placeholder="Họ tên người tiêm"
                      dense
                      clearable
                  ></v-text-field>
                  <v-text-field
                      class="flex xs12 md4 pr-2"
                      v-model="ngaySinh"
                      outlined
                      :rules="required"
                      required
                      label="Ngày sinh"
                      placeholder="Ngày sinh"
                      @blur="formatNgaySinh"
                      dense
                      clearable
                  ></v-text-field>
                  <v-text-field
                      class="flex xs12 md4"
                      v-model="thongTinMuiTiem['CMTCCCD']"
                      outlined
                      :rules="required"
                      required
                      label="Số CMND/CCCD"
                      placeholder="Số CMND/CCCD"
                      dense
                      clearable
                  ></v-text-field>
                  <v-text-field
                      class="flex xs12 md4 pr-2"
                      v-model="thongTinMuiTiem['LanTiem']"
                      outlined
                      :rules="required"
                      required
                      label="Lần tiêm "
                      placeholder="Lần tiêm"
                      dense
                      type='number'
                      clearable
                  ></v-text-field>
                  <v-text-field
                      class="flex xs12 md8"
                      v-model="thongTinMuiTiem['DiaDiemTiemChung']"
                      outlined
                      :rules="required"
                      required
                      label="Địa điểm tiêm chủng "
                      placeholder="Địa điểm tiêm chủng"
                      dense
                      clearable
                  ></v-text-field>
                  <v-text-field
                      label="Loại thuốc tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinMuiTiem.LoaiThuocTiem"
                      dense
                      outlined
                      :rules="required"
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Nơi sản xuất"
                      class="flex xs12 md6 pl-2"
                      v-model="thongTinMuiTiem.NoiSanXuat"
                      dense
                      outlined
                      :rules="required"
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Số lô thuốc"
                      class="flex xs12 md6"
                      v-model="thongTinMuiTiem.SoLoThuoc"
                      dense
                      outlined
                      :rules="required"
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Hạn sử dụng"
                      class="flex xs12 md6 pl-2"
                      v-model="expDateFormatted"
                      @blur="formatExpDate"
                      dense
                      outlined
                      :rules="required"
                      required
                    ></v-text-field>
                  <v-text-field
                      label="Thời gian tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinMuiTiem['GioTiemChung']"
                      placeholder="mm:ss"
                      v-mask="'##:##'"
                      dense
                      outlined
                      :rules="required"
                      required
                    ></v-text-field>
                    <v-text-field
                      class="flex xs12 md6 pl-2"
                      v-model="ngayTiem"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatNgayTiem"
                      dense
                      hide-details="auto"
                      outlined
                      label="Ngày tiêm"
                      :rules="required"
                      required
                    ></v-text-field>
                  <v-autocomplete
                      class="flex xs12"
                      hide-no-data
                      :items="listCoSoYTe"
                      v-model="coSoYTe"
                      :rules="required"
                      required
                      item-text="tenCoSo"
                      item-value="maCoSo"
                      outlined
                      label="Cơ sở y tế"
                      placeholder="Cơ sở y tế"
                      dense
                      hide-details="auto"
                      readonly
                  ></v-autocomplete>
                        
                </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialogAddMuiTiem = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitForm">
              <v-icon left>
                mdi-content-save
              </v-icon>
              <span v-if="typeAction === 'add'">Thêm mới</span>
              <span v-else>Cập nhật</span>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  import Search from './FormTimKiem.vue'
  import Pagination from './Pagination'
  export default {
    name: 'Customers',
    components: {
    'tim-kiem': Search,
    'pagination': Pagination
    },
    data () {
      return {
        validFormAdd: true,
        loading: false,
        loadingData: false,
        processingAction: false,
        listDaiLy: [],
        dailySelected: '',
        dialog: false,
        ngaySinh: '',
        ngayTiem: '',
        expDateFormatted: '',
        typeAction: 'add',
        dialogAddMuiTiem: false,
        listCoSoYTe: [],
        coSoYTe: '',
        lastVisible: '',
        firstVisible: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 50,
        items: [],
        dangkythieuthongtin: false,
        searchAll: true,
        detaiInfo: '',
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        selected: [],
        dataInputSearch: '',
        dialogDetail: '',
        thongTinMuiTiem: {
          "HoVaTen":"",
          "NgaySinh":"",
          "CMTCCCD":"",
          "CoSoYTe_Ma":"",
          "CoSoYTe_Ten":"",
          "LanTiem": '',
          "NgayTiemChung":"",
          "GioTiemChung":"",
          "DiaDiemTiemChung":"",
          "LoaiThuocTiem":"",
          "NoiSanXuat":"",
          "SoLoThuoc":"",
          "HanSuDung":"",
          "NguoiTiemChung_ID": '', 
          "CoSoYTe_Id": ''
        },
        required: [
          v => !!v || 'Thông tin bắt buộc'
        ],
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Họ tên',
            align: 'left',
            value: 'hoVaTen'
          },
          {
            sortable: false,
            text: 'Số CMND/ CCCD',
            align: 'left',
            value: 'cmtcccd'
          },
          {
            sortable: false,
            text: 'Mã nhóm đối tượng',
            align: 'left',
            value: 'nhomDoiTuong'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'left',
            value: 'soDienThoai'
          },
          {
            sortable: false,
            text: 'Địa chỉ',
            align: 'left',
            value: 'diaChiNoiO'
          },
          {
            sortable: false,
            text: 'Thông tin mũi tiêm',
            align: 'center',
            value: 'muiTiemChung',
            class: 'px-0'
          },
          {
            sortable: false,
            text: 'Thao tác',
            align: 'center',
            value: 'action'
          },
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 1)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login?redirect=/pages/danh-sach-dang-ky-tiem-moi' })
        return
      }
      vm.getDanhSachDangKyChinhThuc(0)
      vm.getCoSoYTe()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
    },
    watch: {
      dangkythieuthongtin (val) {
        if (val) {
          this.searchAll = false
          this.locDanhSachNguoiTiemThieuThongTin(0)
        } else {
          if (!this.searchAll) {
            this.getDanhSachDangKyChinhThuc(0)
          }
        }
      }
    },
    methods: {
      searchDangKyTiem (data) {
        let vm = this
        vm.searchAll = true
        vm.dangkythieuthongtin = false
        console.log('dataSearch', data)
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDangKyChinhThuc(0, data)
      },
      cancelSearchDangKyTiem (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDangKyChinhThuc(0, data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      getCoSoYTe () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getCoSoYTe', filter).then(function (result) {
          vm.listCoSoYTe = result ? result : []
          // bind co so y te
          try {
            let data = localStorage.getItem('user')
            if (data && JSON.parse(data) && JSON.parse(data)['coSoYTeId']) {
              let obj = vm.listCoSoYTe.find(function (item) {
                return item.id == JSON.parse(data)['coSoYTeId']
              })
              vm.coSoYTe = obj['maCoSo']
            }
          } catch (error) {
          }
        })
      },
      getDanhSachDangKyChinhThuc (pageIn, dataSearch) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          tinhtrangdangky: 1,
          cmtcccd: dataSearch && dataSearch['CMTCCCD'] ? dataSearch['CMTCCCD'] : '',
          nhomdoituong: dataSearch && dataSearch['NhomDoiTuong'] ? dataSearch['NhomDoiTuong'] : '',
          ngaydangki: dataSearch && dataSearch['NgayDangKi'] ? dataSearch['NgayDangKi'] : '',
          hovaten: dataSearch && dataSearch['HoVaTen'] ? dataSearch['HoVaTen'] : '',
          diabancosoid: dataSearch && dataSearch.hasOwnProperty('DiaBanCoSo_ID') ? dataSearch['DiaBanCoSo_ID'] : '',
          cosoytema: dataSearch && dataSearch['CoSoYTe_Ma'] ? dataSearch['CoSoYTe_Ma'] : '',
          kiemtratrung: dataSearch && dataSearch['KiemTraTrung'] ? dataSearch['KiemTraTrung'] : '',
          tinhthanhma: dataSearch && dataSearch['TinhThanh_Ma'] ? dataSearch['TinhThanh_Ma'] : '',
          quanhuyenma: dataSearch && dataSearch['QuanHuyen_Ma'] ? dataSearch['QuanHuyen_Ma'] : '',
          phuongxama: dataSearch && dataSearch['PhuongXa_Ma'] ? dataSearch['PhuongXa_Ma'] : '',
          tinhthanhten: '',
          quanhuyenten: '',
          phuongxaten: '',
          typeSearch: 'danhsachdangkychinhthuc'
        }
        vm.$store.dispatch('getNguoiTiemChung', filter).then(function(result) {
          vm.loadingData = false
          if (result) {
            vm.items = result.hasOwnProperty('data') ? result.data : []
            vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
            vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
          } else {
            vm.items = []
            vm.totalItem = 0
          }
        }).catch(function () {
          vm.items = []
          vm.totalItem = 0
          vm.loadingData = false
        })
      },
      locDanhSachNguoiTiemThieuThongTin (pageIn) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          data: {
            tinhtrangdangki: 1,
            isSearchOr: true
          }
          
        }
        vm.$store.dispatch('locDanhSachNguoiTiemThieuThongTin', filter).then(function(result) {
          vm.loadingData = false
          if (result) {
            vm.items = result.hasOwnProperty('data') ? result.data : []
            vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
            vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
          } else {
            vm.items = []
            vm.totalItem = 0
          }
        }).catch(function () {
          vm.items = []
          vm.totalItem = 0
          vm.loadingData = false
        })
      },
      exportDanhSach () {
        let vm = this
        vm.processingAction = true
        let filter = {
          typeList: 'DanhSachTiemChinhThuc',
          data: {
            page: -1,
            size: -1,
            tinhtrangdangky: 1,
            cmtcccd: vm.dataInputSearch && vm.dataInputSearch['CMTCCCD'] ? vm.dataInputSearch['CMTCCCD'] : '',
            nhomdoituong: vm.dataInputSearch && vm.dataInputSearch['NhomDoiTuong'] ? vm.dataInputSearch['NhomDoiTuong'] : '',
            ngaydangki: vm.dataInputSearch && vm.dataInputSearch['NgayDangKi'] ? vm.dataInputSearch['NgayDangKi'] : '',
            hovaten: vm.dataInputSearch && vm.dataInputSearch['HoVaTen'] ? vm.dataInputSearch['HoVaTen'] : '',
            diabancosoid: vm.dataInputSearch && vm.dataInputSearch.hasOwnProperty('DiaBanCoSo_ID') ? vm.dataInputSearch['DiaBanCoSo_ID'] : '',
            cosoytema: vm.dataInputSearch && vm.dataInputSearch['CoSoYTe_Ma'] ? vm.dataInputSearch['CoSoYTe_Ma'] : '',
            kiemtratrung: vm.dataInputSearch && vm.dataInputSearch['KiemTraTrung'] ? vm.dataInputSearch['KiemTraTrung'] : -1,
            tinhthanhma: vm.dataInputSearch && vm.dataInputSearch['TinhThanh_Ma'] ? vm.dataInputSearch['TinhThanh_Ma'] : '',
            quanhuyenma: vm.dataInputSearch && vm.dataInputSearch['QuanHuyen_Ma'] ? vm.dataInputSearch['QuanHuyen_Ma'] : '',
            phuongxama: vm.dataInputSearch && vm.dataInputSearch['PhuongXa_Ma'] ? vm.dataInputSearch['PhuongXa_Ma'] : '',
            tinhthanhten: '',
            quanhuyenten: '',
            phuongxaten: ''
          }
        }
        vm.$store.dispatch('exportDanhSach', filter).then(function(result) {
          vm.processingAction = false
        }).catch(function () {
          vm.processingAction = false
        })
      },
      translateStatus (item) {
        let vm = this
        let arrIds = ''
        if (item === 'multiple') {
          if (vm.selected.length === 0) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Vui lòng chọn người muốn rút đăng ký',
              color: 'success',
            })
            return
          }
          arrIds = vm.selected.map(function(item) {
            return item['id']
          }).toString()
          console.log('selected', arrIds)
        }
        let filter = {
          data: {
            ids: item === 'multiple' ? arrIds : String(item.id),
            TinhTrangDangKi: 2
          }
        }
        if (!filter['data']['ids']) {
          return
        }
        let x = confirm('Bạn có chắc chắn thực hiện hành động này?')
        if (x) {
          vm.$store.dispatch('updateRegistrationStatus', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút đăng ký thành công',
              color: 'success',
            })
            vm.getDanhSachDangKyChinhThuc(0)
            vm.selected = []
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút đăng ký thất bại',
              color: 'error',
            })
          })
        }
      },
      formatNgaySinh () {
        let vm = this
        let lengthDate = String(vm.ngaySinh).trim().length
        let splitDate = String(vm.ngaySinh).split('/')
        if (lengthDate && lengthDate == 4) {
        } else if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngaySinh = vm.translateDate(vm.ngaySinh)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngaySinh)
          vm.ngaySinh = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngaySinh = ''
        }
      },
      formatNgayTiem () {
        let vm = this
        let lengthDate = String(vm.ngayTiem).trim().length
        let splitDate = String(vm.ngayTiem).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayTiem = vm.translateDate(vm.ngayTiem)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayTiem)
          vm.ngayTiem = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayTiem = ''
        }
      },
      formatExpDate () {
        let vm = this
        let lengthDate = String(vm.expDateFormatted).trim().length
        let splitDate = String(vm.expDateFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.expDateFormatted = vm.translateDate(vm.expDateFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.expDateFormatted)
          vm.expDateFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.expDateFormatted = ''
        }
      },
      viewDetail (item) {
        let vm = this
        vm.detaiInfo = item
        vm.dialogDetail = true
      },
      addMuiTiem (item) {
        let vm = this
        vm.thongTinMuiTiem.NguoiTiemChung_ID = item.id
        vm.thongTinMuiTiem.HoVaTen = item.hoVaTen
        vm.thongTinMuiTiem['CMTCCCD'] = item.cmtcccd
        vm.ngaySinh = item.ngaySinh
        vm.dialogAddMuiTiem = true
      },
      formatDataInput () {
        let vm = this
        let obj = vm.listCoSoYTe.find(function (item) {
          return item.maCoSo == vm.coSoYTe
        })
        vm.thongTinMuiTiem.CoSoYTe_Ma = vm.coSoYTe
        vm.thongTinMuiTiem.CoSoYTe_Ten = obj ? obj['tenCoSo'] : ''
        vm.thongTinMuiTiem.CoSoYTe_Id = obj ? obj['id'] : ''
        vm.thongTinMuiTiem.NgaySinh = vm.ngaySinh
        vm.thongTinMuiTiem.NgayTiemChung = vm.ngayTiem
        vm.thongTinMuiTiem.HanSuDung = vm.expDateFormatted
        vm.thongTinMuiTiem['LanTiem'] = Number(vm.thongTinMuiTiem['LanTiem'])
      },
      submitForm () {
        let vm = this
        if (vm.$refs.formAddMuiTiem.validate()) {
          vm.formatDataInput()
          console.log('thongTinMuiTiem', vm.thongTinMuiTiem)
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.thongTinMuiTiem
            }
            vm.loading = true
            vm.$store.dispatch('addMuiTiem', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMuiTiem = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm mũi tiêm thành công',
                color: 'success',
              })
              vm.getDanhSachDangKyChinhThuc(0)
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm mũi tiêm không thành công',
                color: 'error',
              })
            });
          } else {
            let filter = {
              id: vm.lichTiemUpdate['id'],
              data: vm.thongTinMuiTiem
            }
            vm.loading = true
            vm.$store.dispatch('updateMuiTiem', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMuiTiem = false
              vm.getDanhSachDangKyChinhThuc(0)
            }).catch(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thất bại',
                color: 'error',
              })
            })
          }
          
        }
      },
      formatTinhTrangTiem (val) {
        if (val == 0) {
          return 'Dự kiến tiêm'
        } else if (val == 1) {
          return 'Hẹn gọi chờ xác nhận'
        } else if (val == 2) {
          return 'Hẹn gọi đã xác nhận'
        } else if (val == 3) {
          return 'Đã tiêm xong'
        } else if (val == 4) {
          return 'Chưa được tiêm'
        } else if (val == 5) {
          return 'Xác nhận không đến'
        }
      },
      formatSex (val) {
        if (val == 1) {
          return 'Nữ'
        } else if (val == 0) {
          return 'Nam'
        } else {
          return 'Không xác định'
        }
      },
      changePage (config) {
        let vm = this
        vm.page = config.page
        if (vm.dangkythieuthongtin) {
          vm.locDanhSachNguoiTiemThieuThongTin(config.page)
        } else {
          vm.getDanhSachDangKyChinhThuc(config.page, vm.dataInputSearch)
        }
      },
      editRegistration (item) {
        let vm = this
        vm.$store.commit('SET_RegistrationUpdate', item)
        vm.$router.push('/pages/dang-ky-tiem-moi/' + item.id)
      },
      parseDate (date) {
        if (!date) {
          return ''
        } else {
          let lengthDate = String(date).length
          if (lengthDate === 4) {
            return date
          } else {
            return String(date).slice(6,8) + '/' + String(date).slice(4,6) + '/' + String(date).slice(0,4)
          }
        }
      }
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

