<template>
  <q-page class="flex flex-center ">

    <q-img src="~assets/PGR-Simbolo.png" style="width:490px" />

  </q-page>
<!--  <q-page class="flex flex-center ">-->
<!--  <ResponsiveImage-->
<!--    :image-url="''"-->
<!--    src="~assets/PGR-Simbolo.png"-->
<!--    :screens="{-->
<!--    xs: '360px',-->
<!--    sm: '640px',-->
<!--    md: '768px',-->
<!--    lg: '1024px',-->
<!--    xl: '1280px'-->
<!--  }"-->
<!--    :max-width="3000"-->
<!--    sizes="w-90vw md:w-60vw xl:w-50vw"-->
<!--  />-->
<!--  </q-page>-->
</template>

<script>
import ResponsiveImage from 'vue-responsive-image'
import Vue from 'vue'
import {QSpinnerBall} from "quasar";

Vue.component('ResponsiveImage', ResponsiveImage)
export default {
  name: 'PageIndex',

  created() {
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout( () => {
      this.$q.loading.hide()
    },600)
  }
}
</script>
