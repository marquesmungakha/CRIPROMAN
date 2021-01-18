import { Model } from "@vuex-orm/core";
import PecaProcesso from "src/store/models/pecaProcesso/pecaProcesso";

export default class ObjectoApreendido extends Model {

  static  entity = 'objectosApreendido'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      tipo: this.attr(''),
      estado: this.attr(''),
      pecaProcesso_id: this.attr(''),
      // Relationship
      pecaProcesso: this.belongsTo(PecaProcesso,'pecaProcesso_id')

    }
  }

}
