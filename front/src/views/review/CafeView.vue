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
const cafe = ref({
  id: 0,
  storeName: "",
  address: "",
  phone: "",
  url: "",
  roadAddressName: ""
});
const reviews = ref([{
  id: "",
  writerId: "",
  title: "",
  comment: "",
  tasteRating: 0,
  ambienceRating: 0,
  serviceRating: 0,
  createdAt: "",
}]);

onMounted(() => {
  axios.get(`/api/v1/cafes/${props.cafeId}`).then(res => {
    cafe.value = res.data;
  });

  axios.get(`/api/v1/cafes/${props.cafeId}/reviews`).then(res => {
    reviews.value = []
    reviews.value = res.data;
  });
});


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
      {{ cafe.storeName }}
    </el-descriptions-item>

    <el-descriptions-item label="주소" label-align="right" align="center">
      {{ cafe.address }}
    </el-descriptions-item>
    <el-descriptions-item label="전화 번호" label-align="right" align="center">
      {{ cafe.phone || "전화 번호 미등록" }}
    </el-descriptions-item>

    <el-descriptions-item label="Url" label-align="right" align="center">
      <el-link type="success" :href="cafe.url">
        {{ cafe.url }}
      </el-link>
    </el-descriptions-item>


    <el-descriptions-item label="도로명 주소" label-align="right" align="center">
      {{ cafe.roadAddressName }}
    </el-descriptions-item>
  </el-descriptions>

  <ul>
    <li v-for="review in reviews" :key="review.id">

      <div class="title">
        리뷰 제목: {{ review.title }}
      </div>
      맛
      <el-rate disabled show-score text-color="#ff9900" v-model="review.tasteRating"/>
      분위기
      <el-rate disabled show-score text-color="#ff9900" v-model="review.ambienceRating"/>
      서비스
      <el-rate disabled show-score text-color="#ff9900" v-model="review.serviceRating"/>


      <div class="content">
        코멘트: {{ review.comment }}
      </div>

      <div class="sub d-flex">
        <div class="category">작성자: {{ review.writerId }}</div>
        <div class="regDate">작성 시간: {{ review.createdAt }}</div>
      </div>
    </li>
  </ul>

</template>

<style lang="scss">
:deep(.my-label) {
  background: var(--el-color-success-light-9) !important;
}

:deep(.my-content) {
  background: var(--el-color-danger-light-9);
}

ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>