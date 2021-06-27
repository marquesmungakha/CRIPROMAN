import {Model} from "@vuex-orm/core";

export default class TipoParecerAuto extends Model {

  static  entity = 'tiposParecerAuto'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr('')
    }
  }

}
