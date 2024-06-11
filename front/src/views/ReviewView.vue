<script setup lang="ts">

import {ref} from "vue";

const props = defineProps({
  cafeId:{
    type:[Number, String],
    required: true,
  }
})

const cafe = ref({
  id:0,
  storeName:"",
})
</script>

<template>
  <main>

  </main>
</template>
