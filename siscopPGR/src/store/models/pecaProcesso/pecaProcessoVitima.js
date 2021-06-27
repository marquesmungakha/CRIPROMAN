import {Model} from "@vuex-orm/core";
import Vitima from "../vitima/vitima";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class PecaProcessoVitima extends Model {

  static  entity = 'pecaProcesso_vitima'

// static primaryKey = ['pecaProcesso_id','vitima_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      vitima_id: this.attr(''),
       // Relationshiops
       pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
       vitima: this.belongsTo(Vitima, 'vitima_id')
    }
  }

}
