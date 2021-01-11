import { Model } from "@vuex-orm/core";
import Pais from "src/store/models/pais/pais";

export default class Provincia extends Model {

  static  entity = 'provincias'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      pais_id: this.attr(''),
      // Relationships
      pais: this.belongsTo(Pais,'pais_id')
    }
  }

}
