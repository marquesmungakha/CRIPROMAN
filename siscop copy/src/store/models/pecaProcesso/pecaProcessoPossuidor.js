import {Model} from "@vuex-orm/core";
import Possuidor from "../possuidor/possuidor";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoPossuidor extends Model {

  static  entity = 'pecaProcesso_possuidor'

// static primaryKey = ['pecaProcesso_id','possuidor_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      possuidor_id: this.attr(''),
      // Relationshiops
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      possuidor: this.belongsTo(Possuidor, 'possuidor_id')
    }
  }

}
