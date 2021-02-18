import {Model} from "@vuex-orm/core";
import Denunciante from "../denunciante/denunciante";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaDenunciante extends Model {

  static  entity = 'processoInstrucaoPreparatoria_denunciante'

// static primaryKey = ['processoInstrucaoPreparatoria_id','denunciante_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      denunciante_id: this.attr(''),
      descricaoOcorrencia: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      qualidadeDe: this.attr(''),
       // Relationshiops
       processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
       denunciante: this.belongsTo(Denunciante, 'denunciante_id')
    }
  }

}
