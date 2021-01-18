import { Model } from "@vuex-orm/core";
import Distrito from "../distrito/distrito"
import Bairro from "../bairro/bairro"
import Localidade from "../localidade/localidade"

export default class PostoAdministrativo extends Model {

  static  entity = 'postosAdministrativo'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      distrito_id:this.attr(''),

      // Relationshiops
      localidades: this.hasMany(Localidade, "postoAdministrativo_id"),
      bairros: this.hasMany(Bairro, "postoAdministrativo_id"),
      distrito: this.belongsTo(Distrito,'distrito_id')
    }
  }

}