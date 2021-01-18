import { Model } from "@vuex-orm/core";
import Suspeito from "../suspeito/suspeito";

export default class SituacaoPrisional extends Model {

  static  entity = 'situacoesPrisional'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo:this.attr(''),
      // Relationship
      suspeitos: this.hasMany(Suspeito, "situacaoPrisional_id")

    }
  }

}
