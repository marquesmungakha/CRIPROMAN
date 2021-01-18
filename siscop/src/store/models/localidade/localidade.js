import { Model } from "@vuex-orm/core";
import PostoAdministrativo from "src/store/models/postoAdministrativo/postoAdministrativo";
import Distrito from "../distrito/distrito"
import Bairro from "../bairro/bairro"

export default class Localidade extends Model {

  static  entity = 'localidades'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      postoAdministrativo_id:this.attr(''),
      distrito_id:this.attr(''),

      // Relationshiops
      bairros: this.hasMany(Bairro, "localidade_id"),
      postoAdministrativo: this.belongsTo(PostoAdministrativo,'postoAdministrativo_id'),
      distrito: this.belongsTo(Distrito,'distrito_id')
    }
  }

}


