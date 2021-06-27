import {Model} from "@vuex-orm/core";
import TipoMeio from "src/store/models/tipoMeio/tipoMeio";
import Modelo from "src/store/models/modelo/modelo";

export default class Marca extends Model {

  static  entity = 'marcas'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      tipoMeio_id: this.attr(''),

      // Relationshiops
      tipoMeio: this.belongsTo(TipoMeio, 'tipoMeio_id'),
      modelos: this.hasMany(Modelo, 'marca_id')
    }
  }

}
