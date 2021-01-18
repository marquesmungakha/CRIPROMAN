import { Model } from "@vuex-orm/core";
import Jurisdicao from "src/store/models/jurisdicao/jurisdicao";
import ProcessoInstrucaoPreparatoria from "../processoInstrucaoPreparatoria/processoInstrucaoPreparatoria";

export default class Crime extends Model {

  static  entity = 'crimes'

  static fields() {

    return {
      id: this.attr(null),
      designacao: this.attr(''),
      classeJudicial_id: this.attr(''),

      // Relationshiops
      classeJudicial: this.belongsTo(Jurisdicao,'classeJudicial_id'),
      processosInstrucaoPreparatoria: this.hasMany(ProcessoInstrucaoPreparatoria,"accoesCrimes_id")
    }
  }

}
