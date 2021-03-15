import {Model} from "@vuex-orm/core";
import Arguido from "../arguido/arguido";
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaArguido extends Model {

  static  entity = 'processoInstrucaoPreparatoria_arguido'

// static primaryKey = ['processoInstrucaoPreparatoria_id','arguido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processo_id: this.attr(''),
      arguido_id: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
      situacaoPrisional_id: this.attr(''),
      // Relationshiops
      processo: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processo_id'),
      arguido: this.belongsTo(Arguido, 'arguido_id'),
      situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id')
    }
  }

}
