import {Model} from "@vuex-orm/core";

export default class ModoAtuacao extends Model {

  static  entity = 'modosAtuacao'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr('')
    }
  }

}
