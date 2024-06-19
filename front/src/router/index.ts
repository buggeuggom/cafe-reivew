import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DirectionsView from "@/views/direction/DirectionsView.vue";
import DirectionView from "@/views/direction/DirectionView.vue";
import CafesView from "@/views/review/CafesView.vue";
import CafeView from "@/views/review/CafeView.vue";

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
            path: '/cafes',
            name: 'cafes',
            component: CafesView
        },
        {
            path: '/cafes/:cafeId',
            name: 'cafe',
            component: CafeView,
            props: true
        }
    ]
})

export default router
