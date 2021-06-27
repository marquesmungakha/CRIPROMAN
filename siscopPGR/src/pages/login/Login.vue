
<template>
  <q-layout v-cloak >
    <q-page-container>
      <q-page class="flex flex-center bg-image">
       <!--div id="particles-js" :class="$q.dark.isActive ? 'dark_gradient' : 'normal_gradient'"></div-->
        <!--q-btn
          color="white"
          class="absolute-top-right"
          flat
          round
          @click="$q.dark.toggle()"
          :icon="$q.dark.isActive ? 'nights_stay' : 'wb_sunny'"
        /-->
        <transition
          appear
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
        >
          <q-card v-bind:style="this.$q.screen.lt.sm?{'width': '80%'}:{'width':'30%','border-radius': '15px'}">
            <q-card-section>
              <q-avatar class="absolute-center shadow-10" size="90px">
                <img src="~assets/User-Profile.png">
              </q-avatar>
            </q-card-section>
            <q-card-section>
              <div class="text-center q-pt-lg">
                <div class="col text-h6  ellipsis"> Entrada ao Sistema</div>
              </div>
            </q-card-section>
            <q-card-section>
              <q-form class="q-gutter-md" @submit.prevent="processForm">
                <q-input v-model="username" label="Utilizador" lazy-rules>
                  <template v-slot:prepend>
                    <q-icon name="person"/>
                  </template>
                </q-input>
                <q-input v-model="password" label="Senha" lazy-rules type="password">
                  <template v-slot:prepend>
                    <q-icon name="lock"/>
                  </template>
                </q-input>
                <div>
                  <q-btn :loading="submitting" color="teal" label="Entrar" type="submit"/>
                </div>
              </q-form>
            </q-card-section>
          </q-card>
        </transition>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script type="text/javascript"></script>
<script>
import UsersService from '../../services/UsersService'
import ProfileService from '../../services/ProfileService'
import {QSpinnerBall} from "quasar";

export default {
  data() {
    return {
      context: 'login context',
      username: '',
      password: '',
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
  mounted() {
  window.addEventListener('DOMContentLoaded', (event) => {
  /* ---- particles.js config ---- */
    particlesJS("particles-js", {
        "particles": {
            "number": {
                "value": 80,
                "density": {
                    "enable": true,
                    "value_area": 800
                }
            },
            "color": {
                "value": "#ffffff"
            },
            "shape": {
                "type": "circle",
                "stroke": {
                    "width": 0,
                    "color": "#000000"
                },
                "polygon": {
                    "nb_sides": 5
                },
                "image": {
                    "src": "img/github.svg",
                    "width": 100,
                    "height": 100
                }
            },
            "opacity": {
                "value": 0.5,
                "random": false,
                "anim": {
                    "enable": false,
                    "speed": 1,
                    "opacity_min": 0.1,
                    "sync": false
                }
            },
            "size": {
                "value": 3,
                "random": true,
                "anim": {
                    "enable": false,
                    "speed": 40,
                    "size_min": 0.1,
                    "sync": false
                }
            },
            "line_linked": {
                "enable": true,
                "distance": 150,
                "color": "#ffffff",
                "opacity": 0.4,
                "width": 1
            },
            "move": {
                "enable": true,
                "speed": 6,
                "direction": "none",
                "random": false,
                "straight": false,
                "out_mode": "out",
                "bounce": false,
                "attract": {
                    "enable": false,
                    "rotateX": 600,
                    "rotateY": 1200
                }
            }
        },
        "interactivity": {
            "detect_on": "canvas",
            "events": {
                "onhover": {
                    "enable": true,
                    "mode": "grab"
                },
                "onclick": {
                    "enable": true,
                    "mode": "push"
                },
                "resize": true
            },
            "modes": {
                "grab": {
                    "distance": 140,
                    "line_linked": {
                        "opacity": 1
                    }
                },
                "bubble": {
                    "distance": 400,
                    "size": 40,
                    "duration": 2,
                    "opacity": 8,
                    "speed": 3
                },
                "repulse": {
                    "distance": 200,
                    "duration": 0.4
                },
                "push": {
                    "particles_nb": 4
                },
                "remove": {
                    "particles_nb": 2
                }
            }
        },
        "retina_detect": true
    });
  })
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
        'username': this.username,
        'password': this.password
      }).then((response) => {
          if (response) {
            console.log('Login >>>>>>>>', response)//.access_token);
            localStorage.setItem('id_token', response.response.data.access_token)
            localStorage.setItem('orgaoId',response.response.data.orgaoId)
            localStorage.setItem('refresh_token', response.response.data.refresh_token)
            localStorage.setItem('username', response.response.data.username)
            // this.$store.dispatch('auth/login',  response.response.data);
            // this.$store.dispatch('user/setToken', response.data.access_token);
            // this.$store.dispatch('user/userLogged',new Boolean(true));

            //     this.getProfile(response.data.id);

            if (response.response.data.roles[0] === 'ROLE_ADMIN') {
              this.$router.push({path: '/'})
              // this.$router.push({path: 'Admin'})
            } else {
              this.$router.push({path: '/login'})
            }
        }
        })
    },
    getProfile(userId) {
      console.log(userId)
      ProfileService.fetchProfile(userId)
        .then((res) => {
          console.log("PROFILE" + res.data)
          this.$store.dispatch('user/setProfile', res.data)
          //this.$router.push({name: 'Home'})
        })
    },
    authenticate: function (provider) {
      this.$auth.authenticate(provider).then(function () {
        // Execute application logic after successful social authentication
      })
    },
    
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
#particles-js {
  position: absolute;
  width: 100%;
  height: 100%;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50% 50%;
}
.normal_gradient {
  background: linear-gradient(145deg, rgb(74, 94, 137) 15%, #b61924 70%);
}
.dark_gradient {
  background: linear-gradient(145deg, rgb(11, 26, 61) 15%, #4c1014 70%);
}
.login-form {
  position: absolute;
}
</style>
