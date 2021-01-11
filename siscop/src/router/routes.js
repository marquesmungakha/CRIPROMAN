import Login from "src/store/models/login/login";
import Router from "vue-router";

const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue')},
      { path: '/pais', component: () => import('pages/pais/index') },
      { path: '/pais/:id', component: () => import('pages/pais/details') },
      { path: '/provincia', component: () => import('pages/provincia/index') },
      { path: '/provincia/:id', component: () => import('pages/provincia/details') },
    ]
  },
  {
    path: '/Login',
    component: () => import('pages/login/Login.vue'),
    meta: { requiresAuth:false}
  },
  {
    path: '/Logout',
    component: () => import('pages/logout/Logout'),
    meta: { requiresAuth:false}
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

//
export default routes
