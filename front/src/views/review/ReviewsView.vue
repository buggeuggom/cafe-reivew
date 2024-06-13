<script setup lang="ts">

import {onMounted, ref} from "vue";

const reviews = ref([]);

onMounted(()=>{

})
</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id">
      <div class="review">
        <router-link :to="{ name: 'review', params: { reviewId: review.id } }">
          {{review.title }}
        </router-link>
      </div>

      <div class="review">
        {{ review.content }}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-06-01</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
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

      &:hover {
        text-decoration: underline;
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
