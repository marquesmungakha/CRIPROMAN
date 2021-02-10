import {Model} from "@vuex-orm/core";
import Provincia from "src/store/models/provincia/provincia"
import Distrito from "../distrito/distrito"

export default class Cidade extends Model {

  static  entity = 'cidades'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      provincia_id: this.attr(''),
      distrito_id: this.attr(''),

      // Relationshiops
      provincia: this.belongsTo(Provincia, 'provincia_id'),
      distrito: this.belongsTo(Distrito, 'distrito_id')
    }
  }

}
