import {Model} from "@vuex-orm/core";
import Acusado from "../acusado/acusado";
import AutoEntrada from "../autoEntrada/autoEntrada"

export default class AutoEntradaAcusado extends Model {

  static  entity = 'autoEntrada_acusado'

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      autoEntrada_id: this.attr(''),
      acusado_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      acusado: this.belongsTo(Acusado, 'acusado_id')
    }
  }

}
