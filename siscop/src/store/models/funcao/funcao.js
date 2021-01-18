import { Model } from "@vuex-orm/core";

export default class Funcao extends Model {

  static  entity = 'funcoes'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      missao: this.attr('')
    }
  }

}
