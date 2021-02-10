import {Model} from "@vuex-orm/core";
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";
import Suspeito from "../suspeito/suspeito";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaSuspeito extends Model {

  static  entity = 'processoInstrucaoPreparatoria_suspeito'

static primaryKey = ['processoInstrucaoPreparatoria_id','suspeito_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      suspeito_id: this.attr(''),
      situacaoPrisional_id: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
       // Relationshiops
      situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id'),
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      suspeito: this.belongsTo(Suspeito, 'suspeito_id')
    }
  }

}
