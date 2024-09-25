import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import BoardView from '@/views/BoardView.vue'
import BoardDetailView from "@/views/BoardDetailView.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/board',
    name: 'board',
    component: BoardView
  },
  {
    path: '/board/detail/:id',
    name: 'BoardDetailView',
    component: BoardDetailView,
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
