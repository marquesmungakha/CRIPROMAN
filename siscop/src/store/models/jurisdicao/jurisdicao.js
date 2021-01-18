import { Model } from "@vuex-orm/core";
import Crime from "src/store/models/crime/crime";

export default class ClasseJudicial extends Model {

  static  entity = 'classesJudicial'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),

      // Relationshiops
      crimes: this.hasMany(Crime,'classeJudicial_id')
    }
  }

}
