import { Model } from "@vuex-orm/core";
import Orgao from "src/store/models/orgao/orgao";

export default class Alocacao extends Model {

  static  entity = 'alocacoes'

  static fields() {

    return {
      id: this.attr(null),
      data: this.attr(''),
      funcao: this.attr(''),
      descricao:this.attr(''),
      quadro_id:this.attr(''),
      orgao_id:this.attr(''),

      // Relationshiops
      orgao: this.belongsTo(Orgao,'orgao_id')
    }
  }

}
