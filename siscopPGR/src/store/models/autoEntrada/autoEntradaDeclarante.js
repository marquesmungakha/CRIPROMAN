import {Model} from "@vuex-orm/core";
import Declarante from "../declarante/declarante";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaDeclarante extends Model {

  static  entity = 'autoEntrada_declarante'

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      autoEntrada_id: this.attr(''),
      declarante_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      declarante: this.belongsTo(Declarante, 'declarante_id')
    }
  }

}
