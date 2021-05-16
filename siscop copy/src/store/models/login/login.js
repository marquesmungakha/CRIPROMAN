import {Model} from "@vuex-orm/core";

export default class Login extends Model {

  static  entity = 'logins'

  static state() {
    return {
      fetching: false
    }
  }

  static fields() {
    return {
      id: this.attr(null),
      access_token: this.attr(''),
      refresh_token: this.attr(''),
      username: this.attr('')
    }

  }

}
