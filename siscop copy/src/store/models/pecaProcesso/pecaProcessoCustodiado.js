import {Model} from "@vuex-orm/core";
import Custodiado from "../custodiado/custodiado";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoCustodiado extends Model {

  static  entity = 'pecaProcesso_custodiado'

// static primaryKey = ['pecaProcesso_id','custodiado_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      custodiado_id: this.attr(''),
        // Relationshiops
    pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
    custodiado: this.belongsTo(Custodiado, 'custodiado_id')
    }
  }

}
