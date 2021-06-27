import {Model} from "@vuex-orm/core";
import Possuidor from "../possuidor/possuidor";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaPossuidor extends Model {

  static  entity = 'autoEntrada_possuidor'

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      autoEntrada_id: this.attr(''),
      possuidor_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      possuidor: this.belongsTo(Possuidor, 'possuidor_id')
    }
  }

}
