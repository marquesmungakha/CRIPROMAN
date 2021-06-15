import {Model} from "@vuex-orm/core";
import Arguido from "../arguido/arguido";
import AutoEntrada from "../autoEntrada/autoEntrada"
import SituacaoPrisional from "../situacaoPrisional/situacaoPrisional";


export default class AutoEntradaArguido extends Model {

  static  entity = 'autoEntrada_arguido'

  static fields() {
    return {
      id: this.attr(null),
      declaracao: this.attr(''),
      autoEntrada_id: this.attr(''),
      arguido_id: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),
      dataSituacaoPrisional: this.attr(''),
      situacaoPrisional_id: this.attr(''),
       // Relationships
      situacaoPrisional: this.belongsTo(SituacaoPrisional, 'situacaoPrisional_id'),
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id'),
      arguido: this.belongsTo(Arguido, 'arguido_id')
    }
  }

}
