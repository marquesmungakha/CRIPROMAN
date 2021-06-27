import {Model} from "@vuex-orm/core";
import Testemunha from "../testemunha/testemunha";
import Profissao from "../profissao/profissao";
import AutoEntrada from "./autoEntrada";

export default class AutoEntradaTestemunha extends Model {

  static  entity = 'autoEntrada_testemunha'

  static fields() {
    return {
      id: this.attr(null),
      ocupacao: this.attr(''),
      profissao_id: this.attr(''),
      autoEntrada_id: this.attr(''),
      testemunha_id: this.attr(''),
      depoimento: this.attr(''),
      // Relationshiops
      profissao: this.belongsTo(Profissao, 'profissao_id'),
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      testemunha: this.belongsTo(Testemunha, 'testemunha_id')
    }
  }

}
