import {Model} from "@vuex-orm/core";
import Detido from "../detido/detido";
import Ofendido from "../ofendido/ofendido";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaOfendido extends Model {

  static  entity = 'autoEntrada_ofendido'

  static fields() {
    return {
      id: this.attr(null),
      autoEntrada_id: this.attr(''),
      ofendido_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      ofendido: this.belongsTo(Ofendido, 'ofendido_id')
    }
  }

}
