import {Model} from "@vuex-orm/core";
import PecaProcesso from "src/store/models/pecaProcesso/pecaProcesso";
import AutoEntrada from "../autoEntrada/autoEntrada";

export default class BemSubtraido extends Model {

  static  entity = 'bensSubtraido'

  static fields() {

    return {
      id: this.attr(null),
      descricao: this.attr(''),
      valorPrejuizo: this.attr(''),
      pecaProcesso_id: this.attr(''),
      autoEntrada_id: this.attr(''),

      // Relationshiops
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id')
    }
  }

}
