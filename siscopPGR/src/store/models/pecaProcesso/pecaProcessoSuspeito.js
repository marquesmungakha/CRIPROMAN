import {Model} from "@vuex-orm/core";
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";
import Suspeito from "../suspeito/suspeito";
import PecaProcesso from "../pecaProcesso/pecaProcesso"
import Provincia from "../provincia/provincia";
import Profissao from "../profissao/profissao";

export default class PecaProcessoSuspeito extends Model {

  static  entity = 'pecaProcesso_suspeito'

// static primaryKey = ['pecaProcesso_id','suspeito_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      suspeito_id: this.attr(''),
      situacaoPrisional_id: this.attr(''),
      profissao_id: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
       // Relationships
       profissao: this.belongsTo(Profissao, 'profissao_id'),
       situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id'),
       pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
       suspeito: this.belongsTo(Suspeito, 'suspeito_id')
    }
  }

}
