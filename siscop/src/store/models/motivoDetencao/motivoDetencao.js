import {Model} from "@vuex-orm/core";

export default class MotivoDetencao extends Model {

  static  entity = 'motivosDetencao'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr('')
    }
  }

}
