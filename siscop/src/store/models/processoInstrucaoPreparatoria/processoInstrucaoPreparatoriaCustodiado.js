import {Model} from "@vuex-orm/core";
import Custodiado from "../custodiado/custodiado";
import ProcessoInstrucaoPreparatoria from "./processoInstrucaoPreparatoria";

export default class ProcessoInstrucaoPreparatoriaCustodiado extends Model {

  static  entity = 'processoInstrucaoPreparatoria_custodiado'

static primaryKey = ['processoInstrucaoPreparatoria_id','custodiado_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      processoInstrucaoPreparatoria_id: this.attr(''),
      custodiado_id: this.attr(''),
      // Relationshiops
      processoInstrucaoPreparatoria: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processoInstrucaoPreparatoria_id'),
      custodiado: this.belongsTo(Custodiado, 'custodiado_id')
    }
  }

}
