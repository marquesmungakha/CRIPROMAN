<template>
  <div>Logout</div>
</template>
<script>

export default {
  data() {
    return {}
  },
  created: function () {
    //http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_logout
    //Logout via jwt can simply be done by resetting the client data -
    // this.$store.dispatch('user/userLogged',new Boolean(false));
    localStorage.setItem('id_token', null);
    localStorage.setItem('orgaoId', null);
    localStorage.setItem('username', null);
    // this.$store.dispatch('auth/login',  null);
    // this.$store.dispatch('user/setToken', null);

    this.$router.push({path: '/Login'})
  }
}
</script>
