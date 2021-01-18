import { Model } from "@vuex-orm/core";

export default class Cargo extends Model {

  static  entity = 'cargos'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr('')
    }
  }

}
