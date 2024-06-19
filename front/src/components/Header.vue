<template>
  <el-header class="header">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/directions">근처 카페 검색</el-menu-item>
      <el-menu-item index="/cafes">카페 리뷰</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts"></script>

<style scoped>
.header {
  padding: 0;
  height: 60px;
}
</style>