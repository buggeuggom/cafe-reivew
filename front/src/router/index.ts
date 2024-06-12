import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CafesView from "@/views/CafesView.vue";
import ReviewView from "@/views/ReviewView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/cafes',
      name: 'cafes',
      component: CafesView
    },
    {
      path: '/cafes/:cafeId',
      name: 'review',
      component: ReviewView,
      props: true
    },
  ]
})

export default router
