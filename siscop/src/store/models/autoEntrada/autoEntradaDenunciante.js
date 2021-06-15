import {Model} from "@vuex-orm/core";
import Denunciante from "../denunciante/denunciante";
import AutoEntrada from "./autoEntrada"

export default class AutoEntradaDenunciante extends Model {

  static  entity = 'autoEntrada_custodiado'

  static fields() {
    return {
      id: this.attr(null),
      autoEntrada_id: this.attr(''),
      denunciante_id: this.attr(''),
      descricaoOcorrencia: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      qualidadeDe: this.attr(''),
      // Relationshiops
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      denunciante: this.belongsTo(Denunciante, 'denunciante_id')
    }
  }

}
