import {Model} from "@vuex-orm/core";
import ImpressaoDigital from "../impressaoDigital/impressaoDigital";

export default class TipoDedo extends Model {

  static  entity = 'tiposDedo'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      // Relationship
      impressoesDigital: this.hasMany(ImpressaoDigital, "dedo_id")
    }
  }

}

