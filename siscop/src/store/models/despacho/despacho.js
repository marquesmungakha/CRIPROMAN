import { Model } from "@vuex-orm/core";
import TipoParecer from "src/store/models/tipoParecer/tipoParecer";
import Orgao from "../orgao/orgao";
import Inspector from "../inspector/inspector"
import ProcessoInvestigacao from "../processoInvestigacao/processoInvestigacao"

export default class Despacho extends Model {

  static  entity = 'despachos'

  static fields() {
    return {
      id: this.attr(null),
      parecer: this.attr(''),
      tipoParecer_id: this.attr(''),
      dataRegisto: this.attr(''),
      destino_id: this.attr(''),
      inspector_id: this.attr(''),
      processo_id: this.attr(''),

      // Relationshiops
      tipoParecer: this.belongsTo(TipoParecer,'tipoParecer_id'),
      destino: this.belongsTo(Orgao,'destino_id'),
      inspector: this.belongsTo(Inspector,'inspector_id'),
      processo: this.belongsTo(ProcessoInvestigacao, "processo_id")
    }

  }
}
