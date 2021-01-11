<script src="../../services/ProfileService.js"></script>
<template>
  <q-layout class="bg-image" v-cloak>
    <q-page-container>
      <q-page class="flex flex-center">
        <transition
          appear
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
        >
        <q-card v-bind:style="this.$q.screen.lt.sm?{'width': '80%'}:{'width':'30%','border-radius': '15px'}">
          <q-card-section>
            <q-avatar size="90px" class="absolute-center shadow-10">
              <img src="~assets/User-Profile.png">
            </q-avatar>
          </q-card-section>
          <q-card-section>
            <div class="text-center q-pt-lg">
              <div class="col text-h6  ellipsis"> Entrada ao Sistema </div>
            </div>
          </q-card-section>
          <q-card-section>
            <q-form @submit.prevent="processForm" class="q-gutter-md" >
              <q-input v-model="username" label="Utilizador" lazy-rules>
                <template v-slot:prepend>
                  <q-icon name="person" />
                </template>
              </q-input>
              <q-input type="password" v-model="password" label="Senha" lazy-rules>
                <template v-slot:prepend>
                  <q-icon name="lock" />
                </template>
              </q-input>
              <div>
                <q-btn type="submit" :loading="submitting" label="Entrar" color="teal"/>
              </div>
            </q-form>
          </q-card-section>
        </q-card>
        </transition>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script>
import UsersService from '../../services/UsersService'
import ProfileService from '../../services/ProfileService'
import {QSpinnerBall} from "quasar";
import Login from "src/store/models/login/login";

export default {
  data () {
    return {
      context: 'login context',
      username:'',
      password:'',
      submitting: false,
      data: {
        rememberMe: false,
        fetchUser: true
      },
      error: null
    }
  },
  created() {
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)

  },
  computed: {
    isAuthenticated: function () {
      return this.$store.getters.isAuthenticated
    }
  },
  methods: {
    processForm: function () {
      console.log({username: this.username, password: this.password});
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 500)
      UsersService.login({
        'username':  this.username,
        'password':  this.password
      })
        .then((response) => {
          if (response.response.data) {
            console.log('Login >>>>>>>>', response.response.data)//.access_token);
            localStorage.setItem('id_token',  response.response.data.access_token)
            localStorage.setItem('refresh_token',  response.response.data.refresh_token)
            // Login.insert(response.response.data)
            // this.$store.dispatch('auth/login',  response.response.data);
            // this.$store.dispatch('user/setToken', response.data.access_token);
            // this.$store.dispatch('user/userLogged',new Boolean(true));

       //     this.getProfile(response.data.id);

            if(response.response.data.roles[0] === 'ROLE_ADMIN') {
              this.$router.push({path: '/'})
              // this.$router.push({path: 'Admin'})
            } else if(response.response.data.roles[0] === 'ROLE_DRIVER') {
              this.$router.push({name: 'Garage'})
            } else {
              this.$router.push({name: '/'})
            }
          }
        })
    },
    getProfile (userId) {
      console.log(userId)
      ProfileService.fetchProfile(userId)
        .then((res) => {
          console.log("PROFILE" +res.data)
          this.$store.dispatch('user/setProfile', res.data)
          //this.$router.push({name: 'Home'})
        })
    },
    authenticate: function (provider) {
      this.$auth.authenticate(provider).then(function () {
        // Execute application logic after successful social authentication
      })
    }
  }
}
</script>

<style>
.bg-image {
  background-image: url("~assets/pgr_picture.png");
  background-repeat: no-repeat;
  background-size: cover;
}
[v-cloak] {
  display: none !important;
}
</style>
