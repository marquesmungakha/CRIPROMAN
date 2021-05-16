import {Model} from "@vuex-orm/core";
import Detido from "../detido/detido"

export default class ObjectoDetido extends Model {

  static  entity = 'objectosDetido'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      estado: this.attr(''),
      detido_id: this.attr(''),

      // Relationships
      detido: this.belongsTo(Detido, "detido_id")
    }
  }

}
