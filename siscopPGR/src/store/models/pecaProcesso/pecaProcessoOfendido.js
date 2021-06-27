import {Model} from "@vuex-orm/core";
import Ofendido from "../ofendido/ofendido";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoOfendido extends Model {

  static  entity = 'pecaProcesso_ofendido'

// static primaryKey = ['pecaProcesso_id','ofendido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      ofendido_id: this.attr(''),
       // Relationshiops
       pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
       ofendido: this.belongsTo(Ofendido, 'ofendido_id')
    }
  }

}
