import {Model} from "@vuex-orm/core";
import TipoParecer from "src/store/models/tipoParecer/tipoParecer";
import Orgao from "../orgao/orgao";
import Magistrado from "../magistrado/magistrado"
import ProcessoInstrucaoPreparatoria from "../processoInstrucaoPreparatoria/processoInstrucaoPreparatoria"

export default class ParecerProcesso extends Model {

  static  entity = 'pareceresProcesso'

  static fields() {
    return {
      id: this.attr(null),
      parecer: this.attr(''),
      tipoParecer_id: this.attr(''),
      dataRegisto: this.attr(''),
      destino_id: this.attr(''),
      magistrado_id: this.attr(''),
      processo_id: this.attr(''),

      // Relationshiops
      tipoParecer: this.belongsTo(TipoParecer, 'tipoParecer_id'),
      destino: this.belongsTo(Orgao, 'destino_id'),
      magistrado: this.belongsTo(Magistrado, 'magistrado_id'),
      processo: this.belongsTo(ProcessoInstrucaoPreparatoria, 'processo_id')
    }

  }
}
