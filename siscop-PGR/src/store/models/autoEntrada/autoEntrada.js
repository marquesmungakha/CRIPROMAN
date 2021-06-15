import {Model} from "@vuex-orm/core";
import TipoAuto from "src/store/models/tipoAuto/tipoAuto";
import Jurisdicao from "../jurisdicao/jurisdicao";
import Crime from "../crime/crime";
import Orgao from "../orgao/orgao";
import ProcessoInvestigacao from "../processoInvestigacao/processoInvestigacao"
import Inspector from "../inspector/inspector";
import ParecerAuto from "../parecerAuto/parecerAuto"
import AutoEntradaAcusado from "../autoEntrada/autoEntradaAcusado"
import AutoEntradaCustodiado from "../autoEntrada/autoEntradaCustodiado"
import BemSubtraido from "../bemSubtraido/bemSubtraido";
import MeiosUtilizado from "../meiosUtilizado/meiosUtilizado";
import ObjectoApreendido from "../objectoApreendido/objectoApreendido";
import AutoEntradaDeclarante from "../autoEntrada/autoEntradaDeclarante"
import AutoEntradaDenunciante from "../autoEntrada/autoEntradaDenunciante"
import AutoEntradaDetido from "../autoEntrada/autoEntradaDetido"
import AutoEntradaArguido from "../autoEntrada/autoEntradaArguido"
import AutoEntradaOfendido from "../autoEntrada/autoEntradaOfendido"
import AutoEntradaPossuidor from "../autoEntrada/autoEntradaPossuidor"
import AutoEntradaSuspeito from "../autoEntrada/autoEntradaSuspeito"
import AutoEntradaTestemunha from "../autoEntrada/autoEntradaTestemunha"
import AutoEntradaVitima from "../autoEntrada/autoEntradaVitima"


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
      pareceresAuto: this.hasMany(ParecerAuto, 'auto_id'),
      acusados: this.hasMany( AutoEntradaAcusado, "autoEntrada_id"),
      custodiados: this.hasMany( AutoEntradaCustodiado, "autoEntrada_id"),
      bensSubtraido: this.hasMany(BemSubtraido, "autoEntrada_id"),
      declarantes: this.hasMany( AutoEntradaDeclarante, 'autoEntrada_id'),
      denunciantes: this.hasMany( AutoEntradaDenunciante, 'autoEntrada_id'),
      detidos: this.hasMany( AutoEntradaDetido,"autoEntrada_id"),
      arguidos: this.hasMany(AutoEntradaArguido, "autoEntrada_id"),
      ofendidos: this.hasMany( AutoEntradaOfendido, "autoEntrada_id"),
      meiosUtilizados: this.hasMany(MeiosUtilizado, "autoEntrada_id"),
      objectosApreendido: this.hasMany(ObjectoApreendido, "autoEntrada_id"),
      possuidores: this.hasMany( AutoEntradaPossuidor, "autoEntrada_id"),
      suspeitos: this.hasMany( AutoEntradaSuspeito, "autoEntrada_id"),
      testemunhas: this.hasMany( AutoEntradaTestemunha, "autoEntrada_id"),
      vitimas: this.hasMany( AutoEntradaVitima, "autoEntrada_id"),

      tipoAuto: this.belongsTo(TipoAuto, "tipoAuto_id"),
      classeJudicial: this.belongsTo(Jurisdicao, "classeJudicial_id"),
      crime: this.belongsTo(Crime, "crime_id"),
      orgao: this.belongsTo(Orgao, "orgao_id"),
      inspector: this.belongsTo(Inspector, 'inspector_id')

    }
  }

}
