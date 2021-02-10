import {Model} from "@vuex-orm/core";
import Arguido from "../arguido/arguido";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaArguido extends Model {

  static  entity = 'processoInstrucaoPreparatoria_arguido'

static primaryKey = ['processoInstrucaoPreparatoria_id','arguido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      arguido_id: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      arguido: this.belongsTo(Arguido, 'arguido_id')
    }
  }

}
