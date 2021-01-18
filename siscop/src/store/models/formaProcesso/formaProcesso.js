import { Model } from "@vuex-orm/core";
import ProcessoInvestigacao from "src/store/models/processoInvestigacao/processoInvestigacao";
import ProcessoInstrucaoPreparatoria from "src/store/models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoria"

export default class FormaProcesso extends Model {

  static  entity = 'formasProcesso'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      codigo: this.attr(''),
      //Relationships
      processosInvestigacao: this.hasMany(ProcessoInvestigacao, 'formaProcesso_id'),
      processosInstrucaoPreparatoria: this.hasMany(ProcessoInstrucaoPreparatoria, 'formaProcesso_id')

    }
  }



}
