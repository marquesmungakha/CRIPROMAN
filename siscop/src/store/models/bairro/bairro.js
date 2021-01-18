import { Model } from "@vuex-orm/core";
import Localidade from "src/store/models/localidade/localidade";
import PostoAdministrativo from "../postoAdministrativo/postoAdministrativo"
import Distrito from "../distrito/distrito"

export default class Bairro extends Model {

  static  entity = 'bairros'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      localidade_id: this.attr(''),
      postoAdministrativo_id:this.attr(''),
      distrito_id:this.attr(''),

      // Relationshiops
      localidade: this.belongsTo(Localidade,'localidade_id'),
      postoAdministrativo: this.belongsTo(PostoAdministrativo,'postoAdministrativo_id'),
      distrito: this.belongsTo(Distrito, "distrito_id")
    }
  }

}
