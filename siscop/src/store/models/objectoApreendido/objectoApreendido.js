import {Model} from "@vuex-orm/core";
import PecaProcesso from "src/store/models/pecaProcesso/pecaProcesso";
import AutoEntrada from "../autoEntrada/autoEntrada";

export default class ObjectoApreendido extends Model {

  static  entity = 'objectosApreendido'

  static fields() {

    return {
      id: this.attr(null),
      descricao: this.attr(''),
      tipo: this.attr(''),
      estado: this.attr(''),
      pecaProcesso_id: this.attr(''),
      autoEntrada_id: this.attr(''),
      // Relationship
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id')

    }
  }

}
