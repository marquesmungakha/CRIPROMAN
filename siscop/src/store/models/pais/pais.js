import {Model} from "@vuex-orm/core";
import Provincia from "src/store/models/provincia/provincia";

export default class Pais extends Model {

  static  entity = 'paises'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      nacionalidade: this.attr(''),
      codigo: this.attr(''),

      // Relationshiops
      provincias: this.hasMany(Provincia, 'pais_id')
    }
  }

}
