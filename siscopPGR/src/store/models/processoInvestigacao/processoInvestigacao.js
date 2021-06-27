import {Model} from "@vuex-orm/core";
import AutoEntrada from "src/store/models/autoEntrada/autoEntrada";
import ProcessoInstrucaoPreparatoria from 'src/store/models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoria'

export default class ProcessoInvestigacao extends Model {

  static  entity = 'processosInvestigacao'

  static fields() {

    return {
      id: this.attr(null),
      numeroProcesso: this.attr(''),
      numeroAuto_id: this.attr(''),
      proveniencia: this.attr(''),
      dataEntrada: this.attr(''),
      anexo: this.attr(''),
      formaProcesso: this.attr(''),
      inspector: this.attr(''),
      magistrado: this.attr(''),
      autor: this.attr(''),
      uuid: this.attr(''),

      // Relationshiops
      numeroAuto: this.belongsTo(AutoEntrada, 'numeroAuto_id'),
      processosInstrucaoPreparatoria: this.hasMany(ProcessoInstrucaoPreparatoria,'processoInvestigacao_id')
    }
  }

}
