<template>
<v-app v-resize="onResize">
  <router-view />
  <!-- global snackbar -->
  <v-snackbar v-model="snackbar.show" :timeout="3000" app top centered :color="snackbar.color">
    {{ snackbar.text }}
    <template #action="{ attrs }">
      <v-btn icon v-bind="attrs" @click="$store.commit('HIDE_SNACKBAR')">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </template>
  </v-snackbar>
</v-app>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'App',
    mounted() {
      if (typeof window !== undefined && window._VMA === undefined) {
        window._VMA = this
      }
    },
    computed: {
      ...mapGetters(['getSnackbar']),
      snackbar: {
        get() {
          return this.getSnackbar
        },
        set(val) {
          this.$store.commit('UPDATE_SNACKBAR', val)
        },
      },
    },
    methods: {
      onResize: function () {
        let breakpointName = this.$vuetify.breakpoint.name
        this.$store.commit('SET_BREAKPOINTNAME', breakpointName)
      }
    }
  }
</script>
