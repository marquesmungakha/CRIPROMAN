import {Model} from "@vuex-orm/core";
import Testemunha from "../testemunha/testemunha";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaTestemunha extends Model {

  static  entity = 'processoInstrucaoPreparatoria_testemunha'

static primaryKey = ['processoInstrucaoPreparatoria_id','testemunha_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      testemunha_id: this.attr(''),
      depoimento: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      testemunha: this.belongsTo(Testemunha, 'testemunha_id')
    }
  }

}
