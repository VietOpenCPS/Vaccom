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
        title="BÁO CÁO KẾ HOẠCH TIÊM CHỦNG"
        class="px-5 py-3"
      >
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số:
              <span style="font-weight: bold; color: green">{{
                totalItem
              }}</span>
              đợt tiêm
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số:
              <span style="font-weight: bold; color: green">{{
                totalItem
              }}</span>
              đợt tiêm
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
            <template v-slot:item.dotTiemChung="{ item, index }">
              <div>{{ item.maDot }}</div>
            </template>
            <template v-slot:item.ngayBatDau="{ item, index }">
              <div>{{ item.ngayBatDau }}</div>
            </template>
            <template v-slot:item.goiTiem="{ item, index }">
              <div>{{ item.goiTiem.length }}</div>
            </template>
            <template v-slot:item.muiTiem1="{ item, index }">
              <div>{{ item.tiemMui1.length }}</div>
            </template>
            <template v-slot:item.muiTiem2="{ item, index }">
              <div>{{ item.tiemMui2.length }}</div>
            </template>
            <template v-slot:item.hoanTiem="{ item, index }">
              <div>{{ item.hoanTiem.length }}</div>
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
  name: "BaoCaoKeHoachTiemChung",
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
      itemsPerPage: 10,
      items: [],
      nguoiGoiTiem: [],
      headers: [
        {
          sortable: false,
          text: "STT",
          align: "center",
          value: "index",
        },
        {
          sortable: false,
          text: "Mã đợt tiêm chủng",
          align: "left",
          value: "dotTiemChung",
        },
        {
          sortable: false,
          text: "Ngày bắt đầu",
          align: "left",
          value: "ngayBatDau",
        },
        {
          sortable: false,
          text: "Số gọi tiêm",
          align: "left",
          value: "goiTiem",
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
        {
          sortable: false,
          text: "Số  hoãn tiêm",
          align: "left",
          value: "hoanTiem",
        },
      ],
    };
  },
  async created() {
    let vm = this;
    await vm.getInfo();
  },
  computed: {
    breakpointName() {
      return this.$store.getters.getBreakpointName;
    },
    filter() {
      return {
        page: this.page,
        size: this.itemsPerPage,
      };
    },
  },
  methods: {
    async getInfo() {
      let vm = this;
      vm.loading = true;
      await vm.getLichTiem();
      await vm.getNguoiTiemChung();
      vm.getPhieuHenTiem();
      vm.loading = false;
    },
    async changePage(config) {
      let vm = this;
      vm.page = config.page;
      await vm.getInfo();
    },
    async getLichTiem() {
      let vm = this;
      await vm.$store
        .dispatch("getLichTiem", vm.filter)
        .then(function (result) {
          vm.items = result.hasOwnProperty("data")
            ? result.data.map((value) => ({
                lichTiemChungId: value.id,
                maDot: value.maDot,
                ngayBatDau: value.ngayBatDau,
                ngayKetThuc: value.ngayKetThuc,
              }))
            : [];
          vm.totalItem = result.hasOwnProperty("total") ? result.total : 0;
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage);
        });
    },
    async getNguoiTiemChung() {
      let vm = this;
      try {
        if (Vue.$cookies.get("Token")) {
          param.headers["Authorization"] =
            "Bearer " + Vue.$cookies.get("Token");
        }
      } catch (error) {}
      const filter = {
        ...vm.filter,
        data: {
          typeGet: 1,
          lichTiemChungId: "",
          caTiemChungId: "",
        },
      };
      await vm.$store
        .dispatch("searchNguoiTiemChung", filter)
        .then(function (response) {
          if (response.data) {
            vm.nguoiGoiTiem = response.data;
          }
        });
    },
    getPhieuHenTiem() {
      let vm = this;
      // console.log(JSON.parse(JSON.stringify(vm.nguoiGoiTiem)));
      const listPhieuHen = [].concat(
        ...vm.nguoiGoiTiem.map((value) => value.phieuHenTiem)
      );
      vm.items = vm.items.map((item) => {
        const phieuHen = listPhieuHen.filter(
          (value) => value.lichTiemChungId === item.lichTiemChungId
        );
        // console.log(JSON.parse(JSON.stringify(phieuHen)));
        item = {
          ...item,
          goiTiem: phieuHen,
          hoanTiem: listPhieuHen.filter(
            (value) => value.tinhTrangXacNhan === 5
          ),
          tiemMui1: phieuHen.filter((value) => value.lanTiem === 0),
          tiemMui2: phieuHen.filter((value) => value.lanTiem === 1),
        };
        return item;
      });
    },
  },
};
</script>
<style lang="css" scoped>
.v-data-table-header-mobile {
  display: none !important;
}
</style>

