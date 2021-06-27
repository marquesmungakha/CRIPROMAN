import {Model} from "@vuex-orm/core";

export default class TipoDocumentoIdentificacao extends Model {

  static  entity = 'tiposDocumentoIdentificacao'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr('')
    }
  }

}
