import {Model} from "@vuex-orm/core";
import Declarante from "../declarante/declarante";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaDeclarante extends Model {

  static  entity = 'processoInstrucaoPreparatoria_declarante'

// static primaryKey = ['processoInstrucaoPreparatoria_id','declarante_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      declarante_id: this.attr(''),
      declaracao: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      declarante: this.belongsTo(Declarante, 'declarante_id')
    }
  }

}
