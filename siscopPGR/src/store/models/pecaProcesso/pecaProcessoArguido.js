import {Model} from "@vuex-orm/core";
import Arguido from "../arguido/arguido";
import PecaProcesso from "../pecaProcesso/pecaProcesso"
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";

export default class PecaProcessoArguido extends Model {

  static  entity = 'pecaProcesso_arguido'

// static primaryKey = ['pecaProcesso_id','arguido_id']

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      pecaProcesso_id: this.attr(''),
      arguido_id: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
      situacaoPrisional_id: this.attr(''),
       // Relationships
      situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id'),
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      arguido: this.belongsTo(Arguido, 'arguido_id')
    }
  }

}
