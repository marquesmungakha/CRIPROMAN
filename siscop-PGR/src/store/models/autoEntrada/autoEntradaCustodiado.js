import {Model} from "@vuex-orm/core";
import Custodiado from "../custodiado/custodiado";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaCustodiado extends Model {

  static  entity = 'autoEntrada_custodiado'

  static fields() {
    return {
      id: this.attr(null),
      autoEntrada_id: this.attr(''),
      custodiado_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      custodiado: this.belongsTo(Custodiado, 'custodiado_id')
    }
  }

}
