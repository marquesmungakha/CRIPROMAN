import {Model} from "@vuex-orm/core";
import Testemunha from "../testemunha/testemunha";
import PecaProcesso from "../pecaProcesso/pecaProcesso"
import Profissao from "../profissao/profissao";

export default class PecaProcessoTestemunha extends Model {

  static  entity = 'pecaProcesso_testemunha'

// static primaryKey = ['pecaProcesso_id','testemunha_id']

  static fields() {
    return {
      id: this.attr(null),
      ocupacao: this.attr(''),
      profissao_id: this.attr(''),
      pecaProcesso_id: this.attr(''),
      testemunha_id: this.attr(''),
      depoimento: this.attr(''),
      // Relationshiops
      profissao: this.belongsTo(Profissao, 'profissao_id'),
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      testemunha: this.belongsTo(Testemunha, 'testemunha_id')
    }
  }

}
