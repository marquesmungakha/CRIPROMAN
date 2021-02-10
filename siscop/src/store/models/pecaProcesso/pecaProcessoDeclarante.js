import {Model} from "@vuex-orm/core";
import Declarante from "../declarante/declarante";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoDeclarante extends Model {

  static  entity = 'pecaProcesso_declarante'

// static primaryKey = ['pecaProcesso_id','declarante_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      declarante_id: this.attr(''),
      // Relationshiops
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      declarante: this.belongsTo(Declarante, 'declarante_id')
    }
  }

}
