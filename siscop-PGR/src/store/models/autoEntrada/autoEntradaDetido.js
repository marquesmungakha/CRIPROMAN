import {Model} from "@vuex-orm/core";
import Detido from "../detido/detido";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaDetido extends Model {

  static  entity = 'autoEntrada_detido'

  static fields() {
    return {
      id: this.attr(null),
      autoEntrada_id: this.attr(''),
      detido_id: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      detido: this.belongsTo(Detido, 'detido_id')
    }
  }

}
