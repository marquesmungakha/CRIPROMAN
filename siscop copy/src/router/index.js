import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'

import routes from './routes'
import Router from "vue-router";
import Login from "src/store/models/login/login";

Vue.use(VueRouter)

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default function (/* { store, ssrContext } */) {
  const Router = new VueRouter({
    scrollBehavior: () => ({x: 0, y: 0}),
    routes,

    // Leave these as they are and change in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    mode: process.env.VUE_ROUTER_MODE,
    base: process.env.VUE_ROUTER_BASE
  })

  Router.beforeEach((to, from, next) => {
       const authUser = Login.query().first()
    //  if(!to.meta.requiresAuth && !authUser) {
    //   return next()
    //  }

    // if(!authUser) {
    //   return next({path:'/Login'})
    // }

    // if (findOne(to.meta.roles,authUser.auth.isAuthenticated.roles)) {
    //   console.log(' yes >'+to.meta.roles+ " <> "+authUser.auth.isAuthenticated.roles);
    //   return next()
    // } else {
    //   console.log('NO'+to.meta.roles.includes(authUser.auth.isAuthenticated.roles)+' '+to.meta.roles+' >> '+authUser.auth.isAuthenticated.roles)
    //   return next({name:'Home'})
    // }

    console.log('beforeEach', to.path + ' - Auth: ' + authUser)
    if ((to.path !== '/login' && to.path !== 'login') && authUser === null) {
      next({ path: '/login' })
    } else if ((to.path === '/login' || to.path === 'login') && authUser !== null) {
      next({ path: '/' })
    } else {
      next()
    }
  })

// axios.interceptors.response.use((response) => {
//   if (response.status === 401) {
//     let msg = response.body.returnMessage
//     localStorage.setItem('logoutReason', msg)
//     authUser = null
//   }else
//     next()
//   })

  return Router
}
