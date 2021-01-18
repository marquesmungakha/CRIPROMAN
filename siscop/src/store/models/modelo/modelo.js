import { Model } from "@vuex-orm/core";
import Marca from "src/store/models/marca/marca";

export default class Modelo extends Model {

  static  entity = 'modelos'

  static fields() {

    return {
      id: this.attr(null),
      codigo: this.attr(''),
      designacao: this.attr(''),
      marca_id: this.attr(''),

      // Relationshiops
      marca: this.belongsTo(Marca,'marca_id')
    }
  }

}
