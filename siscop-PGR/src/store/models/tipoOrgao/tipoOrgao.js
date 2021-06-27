import {Model} from "@vuex-orm/core";

export default class TipoOrgao extends Model {

  static  entity = 'tiposOrgao'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr('')
    }
  }

}
