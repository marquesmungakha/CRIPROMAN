import { Model } from "@vuex-orm/core";
import Marca from "../marca/marca";

export default class TipoMeio extends Model {

  static  entity = 'tiposMeio'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      // Relationships
      marcas: this.hasMany(Marca,"tipoMeio_id")
    }
  }

}

