<script setup lang="ts">

import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  directionId: {
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
  axios.get(`/myapi/directions/${props.directionId}`).then(res => {
    distance.value = res.data;
  });
});

const postReview = ref({
  directionId: props.directionId,
  writerId: "",
  password: "",
  title: "",
  comment: "",
  tasteRating: 0,
  ambienceRating: 0,
  serviceRating: 0
});

const post = () => {
  axios.post(`/myapi/reviews`, postReview.value).then(() => {
    router.replace({ name: "reviews" });
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

  <el-form ref="formRef" style="max-width: 80%" :model="postReview" label-width="auto" class="demo-dynamic">
    <el-form-item label="아이디">
      <el-input v-model="postReview.writerId" placeholder="아이디는 영문과 숫자로 5~15자로만 가능합니다."/>
    </el-form-item>

    <el-form-item label="비밀번호">
      <el-input v-model="postReview.password" placeholder="비밀번호는 영문과 숫자로 10~20자로만 가능합니다."/>
    </el-form-item>

    <el-form-item label="평가" class="m-auto">
      <el-descriptions>
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
    </el-form-item>

    <el-form-item label="제목">
      <el-input v-model="postReview.title" placeholder="제목을 입력해주세요"/>
    </el-form-item>

    <el-form-item label="코멘트">
      <el-input v-model="postReview.comment" type="textarea" rows="5"/>
    </el-form-item>

  </el-form>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="primary" @click="post()">작성완료</el-button>
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