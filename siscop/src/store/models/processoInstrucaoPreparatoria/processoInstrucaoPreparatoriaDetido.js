import {Model} from "@vuex-orm/core";
import Detido from "../detido/detido";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaDetido extends Model {

  static  entity = 'processoInstrucaoPreparatoria_detido'

static primaryKey = ['processoInstrucaoPreparatoria_id','detido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      detido_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      detido: this.belongsTo(Detido, 'detido_id')
    }
  }

}
