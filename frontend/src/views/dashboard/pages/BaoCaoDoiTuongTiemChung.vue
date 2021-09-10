<template>
  <div>
    <v-container
      id="users"
      fluid
      tag="section"
      :style="
        breakpointName === 'xs' || breakpointName === 'sm'
          ? 'padding-top: 75px'
          : ''
      "
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="BÁO CÁO ĐỐI TƯỢNG TIÊM CHỦNG"
        class="px-5 py-3"
      >
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số:
              <span style="font-weight: bold; color: green">{{
                totalItem
              }}</span>
              địa bàn
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số:
              <span style="font-weight: bold; color: green">{{
                totalItem
              }}</span>
              địa bàn
            </span>
          </div>
          <v-data-table
            :headers="headers"
            :items="items"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
          >
            <template v-slot:item.index="{ item, index }">
              <span>{{
                (page + 1) * itemsPerPage - itemsPerPage + index + 1
              }}</span>
            </template>
            <template v-slot:item.diaBanCoSo="{ item, index }">
              <div>
                {{ item.tenDiaBan }}
              </div>
            </template>
            <template v-slot:item.dangKy="{ item, index }">
              <div>{{ getDangKy(item.id) }}</div>
            </template>
            <template v-slot:item.chuaTiem="{ item, index }">
              <div>{{ getChuaTiem(item.id) }}</div>
            </template>
            <template v-slot:item.muiTiem1="{ item, index }">
              <div>{{ getMuiTiem(item.id, 1) }}</div>
            </template>
            <template v-slot:item.muiTiem2="{ item, index }">
              <div>{{ getMuiTiem(item.id, 2) }}</div>
            </template>
          </v-data-table>
          <pagination
            v-if="pageCount"
            :pageInput="page"
            :pageCount="pageCount"
            @tiny:change-page="changePage"
          ></pagination>
        </v-card-text>
      </base-material-card>
    </v-container>
  </div>
</template>

<script>
import Pagination from "./Pagination";
export default {
  name: "BaoCaoDoiTuongTiemChung",
  components: {
    pagination: Pagination,
  },
  data() {
    return {
      loading: false,
      loadingData: false,
      totalItem: 0,
      page: 0,
      pageCount: 0,
      itemsPerPage: 50,
      items: [],
      dangKyChinhThuc: [],
      headers: [
        {
          sortable: false,
          text: "STT",
          align: "center",
          value: "index",
        },
        {
          sortable: false,
          text: "Địa bàn cơ sở",
          align: "left",
          value: "diaBanCoSo",
        },
        {
          sortable: false,
          text: "Số đăng ký",
          align: "left",
          value: "dangKy",
        },
        {
          sortable: false,
          text: "Số chưa tiêm",
          align: "left",
          value: "chuaTiem",
        },
        {
          sortable: false,
          text: "Số tiêm mũi 1",
          align: "left",
          value: "muiTiem1",
        },
        {
          sortable: false,
          text: "Số tiêm mũi 2",
          align: "left",
          value: "muiTiem2",
        },
      ],
    };
  },
  created() {
    let vm = this;
    vm.getDiaBanCoSo();
    vm.getDanhSachDangKyChinhThuc();
  },
  computed: {
    breakpointName() {
      return this.$store.getters.getBreakpointName;
    },
  },
  methods: {
    getDangKy(coSoId) {
      return this.dangKyChinhThuc.filter(
        (dangKy) => dangKy.diaBanCoSoId === coSoId
      ).length;
    },
    getChuaTiem(coSoId) {
      return this.dangKyChinhThuc
        .filter((dangKy) => dangKy.diaBanCoSoId === coSoId)
        .filter((dangKy) => dangKy.muiTiemChung.length === 0).length;
    },
    getMuiTiem(coSoId, soLanTiem) {
      return this.dangKyChinhThuc
        .filter((dangKy) => dangKy.diaBanCoSoId === coSoId)
        .filter((dangKy) => dangKy.muiTiemChung.length === soLanTiem).length;
    },
    getDanhSachDangKyChinhThuc() {
      let vm = this;
      vm.loadingData = true;
      let filter = {
        tinhtrangdangky: 1,
        typeSearch: "danhsachdangkychinhthuc",
        cmtcccd: "",
        nhomdoituong: "",
        ngaydangki: "",
        hovaten: "",
        diabancosoid: "",
        cosoytema: "",
        kiemtratrung: "",
        tinhthanhma: "",
        quanhuyenma: "",
        phuongxama: "",
        tinhthanhten: "",
        quanhuyenten: "",
        phuongxaten: "",
      };
      vm.$store
        .dispatch("getNguoiTiemChung", filter)
        .then(function (result) {
          vm.loadingData = false;
          if (result) {
            vm.dangKyChinhThuc = result.hasOwnProperty("data")
              ? result.data
              : [];
          } else {
            vm.dangKyChinhThuc = [];
          }
        })
        .catch(function () {
          vm.loadingData = false;
        });
    },
    getDiaBanCoSo() {
      let vm = this;
      let filter = {
        id: -1,
      };
      vm.$store
        .dispatch("getDiaBanCoSo", filter)
        .then(function (result) {
          vm.items = result.hasOwnProperty("data") ? result.data : [];
          vm.totalItem = result.hasOwnProperty("total") ? result.total : 0;
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage);
        })
        .catch(function () {
          vm.items = [];
          vm.totalItem = 0;
          vm.loadingData = false;
        });
    },
    changePage(config) {
      let vm = this;
      vm.page = config.page;
      vm.getDanhSachDangKyChinhThuc();
    },
  },
};
</script>
<style lang="css" scoped>
.v-data-table-header-mobile {
  display: none !important;
}
</style>

