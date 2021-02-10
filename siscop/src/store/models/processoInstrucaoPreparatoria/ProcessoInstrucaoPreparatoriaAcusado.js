import {Model} from "@vuex-orm/core";
import Acusado from "../acusado/acusado";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaAcusado extends Model {

  static  entity = 'processoInstrucaoPreparatoria_acusado'

static primaryKey = ['processoInstrucaoPreparatoria_id','acusado_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      acusado_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      acusado: this.belongsTo(Acusado, 'acusado_id')
    }
  }

}
