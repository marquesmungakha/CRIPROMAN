import {Model} from "@vuex-orm/core";
import Ofendido from "../ofendido/ofendido";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaOfendido extends Model {

  static  entity = 'processoInstrucaoPreparatoriaofendido'

// static primaryKey = ['processoInstrucaoPreparatoria_id','ofendido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      ofendido_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      ofendido: this.belongsTo(Ofendido, 'ofendido_id')
    }
  }

}
