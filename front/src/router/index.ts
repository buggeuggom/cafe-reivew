import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DirectionsView from "@/views/direction/DirectionsView.vue";
import DirectionView from "@/views/direction/DirectionView.vue";
import ReviewsView from "@/views/review/ReviewsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/directions',
      name: 'directions',
      component: DirectionsView
    },
    {
      path: '/directions/:directionId',
      name: 'direction',
      component: DirectionView,
      props: true
    },
    {
      path: '/reviews',
      name: 'reviews',
      component: ReviewsView
    },
  ]
})

export default router
