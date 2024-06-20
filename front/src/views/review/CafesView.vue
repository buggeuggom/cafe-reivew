<script setup lang="ts">

import {onMounted, ref} from "vue";
import axios from "axios";

const cafeSearchRequest = ref({
      roadAddress: "",
      storeName: "",
      page: 1,
      size: 10
    }
)

const cafes = ref(
    Array(10).fill({
      id: 0,
      storeName: "",
      address: "",
      averageScore: 0.0,
      reviewCount: 0
    })
);
const search = () => {
  axios.get(`/api/v1/cafes`, {params: cafeSearchRequest.value}).then(response => {
    cafes.value = []
    response.data.forEach((r: any) => {cafes.value.push(r)})
  })
}
onMounted(() => {
  search()
})
</script>

<template>
  <el-container>
    <el-input style="width: 50%" v-model="cafeSearchRequest.roadAddress" placeholder="주소를 입력하세요">
      <template #prepend>
        주소
      </template>
    </el-input>
    <el-input style="width: 50%" v-model="cafeSearchRequest.storeName" placeholder="주소를 입력하세요">
      <template #prepend>
        상호명
      </template>
    </el-input>
    <el-button type="primary" @click="search()">검색</el-button>
  </el-container>

  <table>
    <thead>
    <tr>
      <th>카페이름</th>
      <th>주소</th>
      <th>평균 리뷰 점수</th>
      <th>리뷰 수</th>
    </tr>
    </thead>

    <tbody>
    <tr v-for="cafe in cafes" :key="cafe.id">
      <td>
        <router-link :to="{ name: 'cafe', params: {cafeId: cafe.id}}">
          {{ cafe.storeName || "-" }}
        </router-link>
      </td>
      <td>{{ cafe.address || "-" }}</td>
      <td>
        <el-rate v-model="cafe.averageScore" disabled show-score text-color="#ff9900"/>
      </td>
      <td>{{ cafe.reviewCount || "-" }}</td>

    </tr>
    </tbody>
  </table>
</template>

<style scoped lang="scss">
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  font-family: Arial, sans-serif;
}

th, td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left; /* 텍스트를 왼쪽 정렬 */
}

th {
  background-color: #f4f4f4;
  color: #333;
  font-weight: bold;
  text-align: center; /* 헤더 텍스트를 중앙 정렬 */
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f1f1f1;
}

input {
  padding: 8px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 8px 12px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
