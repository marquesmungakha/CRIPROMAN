import Api from '../services/Api'
// import axios from "axios";
import Login from "src/store/models/login/login";
import {Notify} from 'quasar'

const config = {
  'Content-Type': 'application/json'
}

export default {
  logout() {
    return Api().get('logout')
      .catch((error) => {
        if (error.response) {
          console.log(JSON.stringify(error.response));
          alert(JSON.stringify(error.response.data))
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  },
  fetchUsers() {
    return Api().get('secUser')
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
          alert(error.response.data)
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  },
  signup(params) {
    return Api().post('user/signup', params)
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
          alert(error.response.data)
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  },
  login(params) {
    return Login.api().post('/login', params) // Api().post('login', params) //
      .catch((error) => {
        if (error.response) {
          Notify.create({
            icon: 'announcement',
            message: 'Username or Password is invalid',
            type: 'negative',
            progress: true,
            timeout: 3000,
            position: 'top',
            color: 'negative',
            textColor: 'white',
            classes: 'glossy'
          })
          console.log(error.response);
        } else if (error.request) {
          console.log(error.request);
        } else {
          Notify.create({
            icon: 'announcement',
            message: 'Problemas ao conectar-se com o Servidor. ',
            type: 'negative',
            progress: true,
            timeout: 3000,
            position: 'top',
            color: 'negative',
            textColor: 'white',
            classes: 'glossy'
          })
          console.log('Error', error.message);
        }
      });
  },
  updateUser(params) {
    return Api().put('secUser/' + params.id, params)
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  },
  getUser(params) {
    return Api().get('user/' + params.id)
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  },
  deleteUser(id) {
    return Api().delete('user/' + id)
      .catch((error) => {
        if (error.response) {
          console.log(error.response);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
      });
  }
}
