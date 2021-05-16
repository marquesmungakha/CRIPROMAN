import {Model} from "@vuex-orm/core";
import Vitima from "../vitima/vitima";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaVitima extends Model {

  static  entity = 'processoInstrucaoPreparatoria_vitima'

// static primaryKey = ['processoInstrucaoPreparatoria_id','vitima_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      vitima_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      vitima: this.belongsTo(Vitima, 'vitima_id')
    }
  }

}
