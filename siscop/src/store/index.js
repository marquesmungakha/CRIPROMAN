import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import VuexORM from '@vuex-orm/core'
import VuexORMAxios from '@vuex-orm/plugin-axios'
import Pais from './models/pais/pais'
import Provincia from "src/store/models/provincia/provincia";
import Login from "src/store/models/login/login";

Vue.use(Vuex)

VuexORM.use(VuexORMAxios, {
  axios,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
  },
  baseURL: 'http://localhost:8080/api'
})


// Request interceptor for API calls
axios.interceptors.request.use(
  async config => {
    // const value = await redisClient.get(rediskey)
    // const keys = JSON.parse(value)
    config.headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded'
    }
    if (Login.all().length != 0) {
      // console.log(">>VFF "+JSON.parse(localStorage.getItem('vuex')).user.token+"------------"+ JSON.stringify(localStorage));
      config.headers['X-Auth-Token'] = [
        '',Login.query().first().access_token
      ].join(' ')

    } else {
      //console.log(">>VFF2 "+JSON.parse(localStorage.getItem('vuex'))+"------------"+ JSON.stringify(localStorage));
      delete config.headers['Authorization']
    }
    console.log("entra 0")
    return config;
  },
  error => {
    Promise.reject(error)
  });

// Response interceptor for API calls
axios.interceptors.response.use((response) => { console.log("entra 1")
  return response
}, async function (error) {
  console.log("entra 2")
  console.log(localStorage.getItem('refresh_token'))
  console.log(Login.query().all())

  const originalRequest = error.config;
  if ((error.response.status === 403 || error.response.status === 401) && !originalRequest._retry) {
    originalRequest._retry = true;

 //   const authUser = Login.query().first();
    const rToken = Login.query().first().refresh_token;

  //  console.log("LOGIN first "+Login.query().first())
    console.log('attempt to refresh token here -' + 'http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token=' + rToken);
    // console.log('r token '+authUser.auth.isAuthenticated.refresh_token+'_____________-'+JSON.stringify(authUser.auth.isAuthenticated))
    //, {grant_type:'refresh_token',refresh_token:tok}
    return axios.post('http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token=' + rToken)
      .then(({data}) => {
        console.log('==got the following token back: ' + data.access_token + '___________________________________________')
        //console.log("------------"+JSON.stringify(data)+' ')

        //window.localStorage.setItem('vuex.auth.isAuthenticated.refresh_token', data.refresh_token);

        //This updates underyling user token with returned data - by updating it here saves recalling this method
        //over and over again
        // const login = Login.all()[0].fields().access_token // = data.access_token;
       // Login.insert(login);
       //  console.log(login);
         //store.dispatch('user/setToken', data.access_token);
        // const authUser1 = JSON.parse(window.localStorage.getItem('vuex'));
        // //This is simply checking set data matches what came back in http request
        // if (data.access_token === authUser1.user.token) {
        //   console.log('Great news the underlying user token has now been updated meaning the user has now been re-authenticated')
        // }

        axios.defaults.headers.common['X-Auth-Token'] = data.access_token;
    return axios(originalRequest);
      });
  }
  return Promise.reject(error);
});

const database = new VuexORM.Database()

database.register(Login)
database.register(Pais)
database.register(Provincia)

export default new Vuex.Store({
  plugins: [VuexORM.install(database)]
})

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */
//
// export default function (/* { ssrContext } */) {
//   const Store = new Vuex.Store({
//     modules: {
//       pais,
//      login
//     },
//
//     // enable strict mode (adds overhead!)
//     // for dev mode only
//     strict: process.env.DEBUGGING
//   })
//
//   return Store
// }
