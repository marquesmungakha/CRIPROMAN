import {Model} from "@vuex-orm/core";

export default class SecUser extends Model {

  static  entity = 'tiposAuto'

  static fields() {

    return {
      id: this.attr(null),
      username: this.attr(''),
      orgao_id: this.attr('')
    }
  }

}
