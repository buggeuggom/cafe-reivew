<script setup lang="ts">

import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const address = ref("");
const recommendations = ref(Array(10).fill({
  id: " ",
  distance: ' ',
  targetStoreName: " ",
  targetPhone: " ",
  targetRoadAddressName: " "
}));

const search = function () {

  if (address.value.trim() == "") {
    alert("주소를 입력하세요.")
    return
  }
  const queryParam = {address: address.value}
  axios.get(`/myapi/directions`, {params: queryParam})
      .then(response => {
            recommendations.value = []
            if (response.data.length == 0) {
              recommendations.value = Array(10).fill({
                id: " ",
                distance: ' ',
                targetStoreName: " ",
                targetPhone: " ",
                targetRoadAddressName: " "
              })
            }

            response.data.forEach((r: any) => {
              recommendations.value.push(r)
            })
          }
      )
}

</script>

<template>

  <el-container>
    <el-input style="width: 50%" v-model="address" placeholder="주소를 입력하세요">
      <template #prepend>
        주소
      </template>
    </el-input>
    <el-button type="primary" @click="search()">검색</el-button>
  </el-container>

  <table>
    <thead>
    <tr>
      <th>카페이름</th>
      <th>거리(km)</th>
      <th>전화번호</th>
      <th>도로명 주소</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="recommendation in recommendations" :key="recommendation.id">
      <td>
        <router-link :to="{ name: 'direction', params: {directionId: recommendation.id}}">
          {{ recommendation.targetStoreName || "-" }}
        </router-link>
      </td>
      <td>{{ recommendation.distance || "-" }}</td>
      <td>{{ recommendation.targetPhone || "-" }}</td>
      <td>{{ recommendation.targetRoadAddressName || "-" }}</td>

    </tr>
    </tbody>
  </table>
</template>
<style scoped>
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