import {Model} from "@vuex-orm/core";
import Arguido from "../arguido/arguido";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

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
      // Relationshiops
    pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
    arguido: this.belongsTo(Arguido, 'arguido_id')
    }
  }

}
