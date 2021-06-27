import {Model} from "@vuex-orm/core";
import Vitima from "../vitima/vitima";
import AutoEntrada from "./autoEntrada";

export default class AutoEntradaVitima extends Model {

  static  entity = 'autoEntrada_vitima'

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      autoEntrada_id: this.attr(''),
      vitima_id: this.attr(''),
       // Relationshiops
       autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
       vitima: this.belongsTo(Vitima, 'vitima_id')
    }
  }

}
