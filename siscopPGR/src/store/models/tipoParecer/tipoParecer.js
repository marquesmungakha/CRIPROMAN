import {Model} from "@vuex-orm/core";

export default class TipoParecer extends Model {

  static  entity = 'tiposParecer'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr('')
    }
  }

}
