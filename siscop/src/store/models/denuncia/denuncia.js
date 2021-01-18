import { Model } from "@vuex-orm/core";
import ProcessoInvestigacao from "src/store/models/processoInvestigacao/processoInvestigacao";
import Inspector from "src/store/models/inspector/inspector";

export default class Denuncia extends Model {

  static  entity = 'denuncias'

  static fields() {

    return {
      id: this.attr(null),
      numero: this.attr(''),
      dataAbertura: this.attr(''),
      descricao: this.attr(''),
      anexo: this.attr(''),
      inspector_id: this.attr(''),
      processo_id:this.attr(''),
      procedimentoCriminal: this.attr(''),

      // Relationshiops
      processo: this.belongsTo(ProcessoInvestigacao,'processo_id'),
      inspector: this.belongsTo(Inspector,'inspector_id'),
    }
  }

}
