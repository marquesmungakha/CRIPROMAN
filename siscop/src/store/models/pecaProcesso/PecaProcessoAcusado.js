import {Model} from "@vuex-orm/core";
import Acusado from "../acusado/acusado";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoAcusado extends Model {

  static  entity = 'pecaProcesso_acusado'

// static primaryKey = ['pecaProcesso_id','acusado_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      acusado_id: this.attr(''),
      // Relationshiops
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      acusado: this.belongsTo(Acusado, 'acusado_id')
    }
  }

}
