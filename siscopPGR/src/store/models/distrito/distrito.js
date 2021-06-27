import {Model} from "@vuex-orm/core";
import Provincia from "src/store/models/provincia/provincia";
import Bairro from "../bairro/bairro";
import Cidade from "../cidade/cidade";
import Localidade from "../localidade/localidade";
import PostoAdministrativo from "../postoAdministrativo/postoAdministrativo";

export default class Distrito extends Model {

  static  entity = 'distritos'

  static fields() {

    return {
      id: this.attr(null),
      codigo: this.attr(''),
      designacao: this.attr(''),
      provincia_id: this.attr(''),

      // Relationshiops
      provincia: this.belongsTo(Provincia, 'provincia_id'),
      cidades: this.hasMany(Cidade, "distrito_id"),
      bairros: this.hasMany(Bairro, "distrito_id"),
      localidades: this.hasMany(Localidade, "distrito_id"),
      postosAdministrativo: this.hasMany(PostoAdministrativo, "distrito_id")
    }
  }

}
