<script setup lang="ts">

import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  cafeId: {
    type: [Number, String],
    required: true,
  }
})
const distance = ref(
    {
      id: 0,
      inputAddress: "",
      targetStoreName: "",
      targetAddress: "",
      targetPhone: "",
      targetUrl: "",
      targetRoadAddressName: ""
    })
onMounted(() => {
  axios.get(`/myapi/cafes/${props.cafeId}`).then(res => {
    distance.value = res.data;
  });
});

const postReview = ref({
  writerId: "",
  password: "",
  title: "",
  comment: "",
  tasteRating: 0,
  ambienceRating: 0,
  serviceRating: 0
});

const post = () => {
  axios.patch(`/myapi/cafes/${distance.value.id}`, postReview.value).then(() => {
    router.replace({ name: "home" });
  });
};
</script>

<template>
  <el-descriptions title="카페 정보" :column="3" border>
    <el-descriptions-item
        label="카페 이름"
        label-align="right"
        align="center"
        label-class-name="my-label"
        class-name="my-content"
        width="150px"
    >
      {{ distance.targetStoreName }}
    </el-descriptions-item>
    <el-descriptions-item label="전화 번호" label-align="right" align="center">
      {{ distance.targetPhone || "전화 번호 미등록" }}
    </el-descriptions-item>
    <el-descriptions-item label="입력 주소" label-align="right" align="center">
      {{ distance.inputAddress }}
    </el-descriptions-item>
    <el-descriptions-item label="Url" label-align="right" align="center">
      <el-link type="success" :href="distance.targetUrl">
        {{ distance.targetUrl }}
      </el-link>
    </el-descriptions-item>
    <el-descriptions-item label="주소" label-align="right" align="center">
      {{ distance.targetRoadAddressName }}
    </el-descriptions-item>
  </el-descriptions>
  <br/>
  <el-descriptions class="mt-1" title="카페 리뷰" :column="3" border>
    <el-descriptions-item label="맛" label-align="right" align="center">
      <el-rate v-model="postReview.tasteRating"/>
    </el-descriptions-item>
    <el-descriptions-item label="분위기" label-align="right" align="center">
      <el-rate v-model="postReview.ambienceRating"/>
    </el-descriptions-item>
    <el-descriptions-item label="서비스" label-align="right" align="center">
      <el-rate v-model="postReview.serviceRating"/>
    </el-descriptions-item>
  </el-descriptions>
  <div class="m-2">
    <p>제목</p>
    <el-input v-model="postReview.title" placeholder="제목을 입력해주세요"/>

    <p>내용</p>
    <el-input v-model="postReview.comment" type="textarea" rows="5"/>
  </div>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="primary" @click="post()">수정완료</el-button>
  </div>
</template>

<style lang="scss">
:deep(.my-label) {
  background: var(--el-color-success-light-9) !important;
}

:deep(.my-content) {
  background: var(--el-color-danger-light-9);
}
</style>