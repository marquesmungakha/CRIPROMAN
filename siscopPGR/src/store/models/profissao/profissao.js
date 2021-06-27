import {Model} from "@vuex-orm/core";

export default class Profissao extends Model {

  static  entity = 'profissoes'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr('')
    }
  }

}
