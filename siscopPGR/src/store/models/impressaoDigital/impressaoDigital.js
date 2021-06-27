import {Model} from "@vuex-orm/core";
import TipoDedo from "../tipoDedo/tipoDedo"

export default class ImpressaoDigital extends Model {

  static  entity = 'impressoesDigital'

  static fields() {

    return {
      id: this.attr(null),
      anexo: this.attr(''),
      nomeFicheiro: this.attr(''),
      dedo_id: this.attr(''),
      individuo_id: this.attr(''),

      // Relationships
      dedo: this.belongsTo(TipoDedo, "dedo_id")
    }
  }

}
