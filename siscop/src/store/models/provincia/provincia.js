import { Model } from "@vuex-orm/core";
import Pais from "src/store/models/pais/pais";
import Cidade from "../cidade/cidade";
import Distrito from "../distrito/distrito";

export default class Provincia extends Model {

  static  entity = 'provincias'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      pais_id: this.attr(''),
      // Relationships
      pais: this.belongsTo(Pais,'pais_id'),
      distritos: this.hasMany(Distrito,"provincia_id"),
      cidades: this.hasMany(Cidade,"provincia_id")

    }
  }

}
