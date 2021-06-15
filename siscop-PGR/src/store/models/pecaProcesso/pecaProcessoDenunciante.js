import {Model} from "@vuex-orm/core";

import PecaProcesso from "../pecaProcesso/pecaProcesso"
import Denunciante from "../denunciante/denunciante"

export default class PecaProcessoDenunciante extends Model {

  static  entity = 'pecaProcesso_denunciante'

// static primaryKey = ['pecaProcesso_id','denunciante_id']

  static fields() {
    return {
    id: this.attr(null),
    declaracao: this.attr(''),
    pecaProcesso_id: this.attr(''),
    denunciante_id: this.attr(''),
    descricaoOcorrencia: this.attr(''),
    localTrabalho: this.attr(''),
    ocupacao: this.attr(''),
    qualidadeDe: this.attr(''),
    // Relationshiops
    pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
    denunciante: this.belongsTo(Denunciante, 'denunciante_id')
    }
  }

}



