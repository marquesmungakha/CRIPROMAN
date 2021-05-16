import {Model} from "@vuex-orm/core";
import Detido from "../detido/detido";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoDetido extends Model {

  static  entity = 'pecaProcesso_detido'

// static primaryKey = ['pecaProcesso_id','detido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      detido_id: this.attr(''),
       // Relationshiops
       pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
       detido: this.belongsTo(Detido, 'detido_id')
    }
  }

}
