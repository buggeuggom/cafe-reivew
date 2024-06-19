<template>
  <div class="demo-collapse">
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item title="http method" name="1">
        <a href="https://docs.google.com/spreadsheets/d/1DFhouNXJJzhR5s1qPbkk59ifF7rd1EZz4iRqePg4CUE/edit?usp=sharing">
          google doc
        </a>
      </el-collapse-item>

      <el-collapse-item title="github" name="2">
        <a href="https://github.com/InKyuHwang001/cafe-reivew">
          github Link
        </a>
      </el-collapse-item>

    </el-collapse>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

const activeName = ref('1')
</script>
