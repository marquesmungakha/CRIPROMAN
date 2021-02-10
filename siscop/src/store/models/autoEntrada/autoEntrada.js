import {Model} from "@vuex-orm/core";
import TipoAuto from "src/store/models/tipoAuto/tipoAuto";
import Jurisdicao from "../jurisdicao/jurisdicao";
import Crime from "../crime/crime";
import Orgao from "../orgao/orgao";
import ProcessoInvestigacao from "../processoInvestigacao/processoInvestigacao"
import Inspector from "../inspector/inspector";

export default class AutoEntrada extends Model {

  static  entity = 'autosEntrada'

  static fields() {

    return {
      id: this.attr(null),
      tipoAuto_id: this.attr(''),
      numero: this.attr(''),
      dataAbertura: this.attr(''),
      descricao: this.attr(''),
      anexo: this.attr(''),
      classeJudicial_id: this.attr(''),
      crime_id: this.attr(''),
      modusOperandi: this.attr(''),
      horaOcorrencia: this.attr(''),
      infraccao: this.attr(''),
      endereco: this.attr(''),
      responsavelLocal: this.attr(''),
      contacto: this.attr(''),
      orgao_id: this.attr(''),
      inspector_id: this.attr(''),


      // Relationshiops
      processoInvestigacao: this.hasMany(ProcessoInvestigacao, 'numeroAuto_id'),
      tipoAuto: this.belongsTo(TipoAuto, "tipoAuto_id"),
      classeJudicial: this.belongsTo(Jurisdicao, "classeJudicial_id"),
      crime: this.belongsTo(Crime, "crime_id"),
      orgao: this.belongsTo(Orgao, "orgao_id"),
      inspector: this.belongsTo(Inspector, 'inspector_id')

    }
  }

}
