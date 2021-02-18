import {Model} from "@vuex-orm/core";
import Possuidor from "../possuidor/possuidor";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaPossuidor extends Model {

  static  entity = 'processoInstrucaoPreparatoria_possuidor'

// static primaryKey = ['processoInstrucaoPreparatoria_id','possuidor_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      possuidor_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      possuidor: this.belongsTo(Possuidor, 'possuidor_id')
    }
  }

}
