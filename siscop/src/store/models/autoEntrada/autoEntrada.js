import { Model } from "@vuex-orm/core";
import TipoAuto from "src/store/models/tipoAuto/tipoAuto";
import Jurisdicao from "../jurisdicao/jurisdicao";
import Crime from "../crime/crime";
import Orgao from "../orgao/orgao";
import ProcessoInvestigacao from "../processoInvestigacao/processoInvestigacao"

export default class AutoEntrada extends Model {

  static  entity = 'autosEntrada'

  static fields() {

    return {
      id: this.attr(null),
      tipoAuto_id: this.attr(''),
      classeJudicial_id: this.attr(''),
      crime_id:this.attr(''),
      modusOperandi: this.attr(''),
      horaOcorrencia: this.attr(''),
      infraccao:this.attr(''),
      endereco: this.attr(''),
      responsavelLocal: this.attr(''),
      contacto:this.attr(''),
      orgao_id:this.attr(''),

      // Relationshiops
      processoInvestigacao: this.hasOne(ProcessoInvestigacao,'numeroAuto_id'),
      tipoAuto: this.belongsTo(TipoAuto,"tipoAuto_id"),
      classeJudicial: this.belongsTo(Jurisdicao, "classeJudicial_id"),
      crime: this.belongsTo(Crime,"crime_id"),
      orgao: this.belongsTo(Orgao, "orgao_id")

    }
  }

}
