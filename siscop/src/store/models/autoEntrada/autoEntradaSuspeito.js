import {Model} from "@vuex-orm/core";
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";
import Suspeito from "../suspeito/suspeito";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaSuspeito extends Model {

  static  entity = 'autoEntrada_suspeito'

  static fields() {
    return {
      id: this.attr(null),
      suspeito_id: this.attr(''),
      situacaoPrisional_id: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
      autoEntrada_id: this.attr(''),
      suspeito_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id'),
      suspeito: this.belongsTo(Suspeito, 'suspeito_id')
    }
  }

}
