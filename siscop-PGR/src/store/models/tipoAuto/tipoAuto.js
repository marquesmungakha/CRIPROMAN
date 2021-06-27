import {Model} from "@vuex-orm/core";

export default class TipoAuto extends Model {

  static  entity = 'tiposAuto'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr('')
    }
  }

}
