import {Model} from "@vuex-orm/core";
import FormaProcesso from "src/store/models/formaProcesso/formaProcesso";
import Jurisdicao from "src/store/models/jurisdicao/jurisdicao";
import Crime from "src/store/models/crime/crime";
import Magistrado from "src/store/models/magistrado/magistrado";
import ProcessoInvestigacao from "../processoInvestigacao/processoInvestigacao";
import Orgao from "../orgao/orgao";
import ParecerProcesso from "../parecerProcesso/parecerProcesso";
import ProcessoInstrucaoPreparatoriaDeclarante from "./processoInstrucaoPreparatoriaDeclarante";
import ProcessoInstrucaoPreparatoriaDenunciante from "./processoInstrucaoPreparatoriaDenunciante";
import ProcessoInstrucaoPreparatoriaAcusado from "./processoInstrucaoPreparatoriaAcusado";
import ProcessoInstrucaoPreparatoriaCustodiado from "./processoInstrucaoPreparatoriaCustodiado";
import ProcessoInstrucaoPreparatoriaDetido from "./processoInstrucaoPreparatoriaDetido";
import ProcessoInstrucaoPreparatoriaArguido from "./processoInstrucaoPreparatoriaArguido";
import ProcessoInstrucaoPreparatoriaOfendido from "./processoInstrucaoPreparatoriaOfendido";
import ProcessoInstrucaoPreparatoriaPossuidor from "./processoInstrucaoPreparatoriaPossuidor";
import ProcessoInstrucaoPreparatoriaSuspeito from "./processoInstrucaoPreparatoriaSuspeito";
import ProcessoInstrucaoPreparatoriaTestemunha from "./processoInstrucaoPreparatoriaTestemunha";
import ProcessoInstrucaoPreparatoriaVitima from "./processoInstrucaoPreparatoriaVitima";

export default class ProcessoInstrucaoPreparatoria extends Model {

  static  entity = 'processosInstrucaoPreparatoria'

  static fields() {

    return {
      id: this.attr(null),
      numeroProcesso: this.attr(''),
      historial: this.attr(''),
      proveniencia: this.attr(''),
      dataEntrada: this.attr(''),
      anexo: this.attr(''),
      autor: this.attr(''),
      formaProcesso_id: this.attr(''),
      classeJudicial_id: this.attr(''),
      accoesCrimes_id: this.attr(''),
      processoInvestigacao_id: this.attr(''),
      orgao_id: this.attr(''),
      magistrado_id: this.attr(''),

      // Relationshiops
      accoesCrimes: this.belongsTo(Crime, 'accoesCrimes_id'),
      formaProcesso: this.belongsTo(FormaProcesso, 'formaProcesso_id'),
      classeJudicial: this.belongsTo(Jurisdicao, 'classeJudicial_id'),
      magistrado: this.belongsTo(Magistrado, 'magistrado_id'),
      processoInvestigacao: this.belongsTo(ProcessoInvestigacao, 'processoInvestigacao_id'),
      orgao: this.belongsTo(Orgao, 'orgao_id'),
      pareceresProcesso: this.hasMany(ParecerProcesso, "processo_id"),

      acusados: this.hasMany( ProcessoInstrucaoPreparatoriaAcusado, "processoInstrucaoPreparatoria_id"),
      custodiados: this.hasMany( ProcessoInstrucaoPreparatoriaCustodiado, "processoInstrucaoPreparatoria_id"),
      declarantes: this.hasMany( ProcessoInstrucaoPreparatoriaDeclarante, 'processoInstrucaoPreparatoria_id'),
      denunciantes: this.hasMany( ProcessoInstrucaoPreparatoriaDenunciante, 'processoInstrucaoPreparatoria_id'),
      detidos: this.hasMany( ProcessoInstrucaoPreparatoriaDetido,"processoInstrucaoPreparatoria_id"),
      arguidos: this.hasMany(ProcessoInstrucaoPreparatoriaArguido, "processoInstrucaoPreparatoria_id"),
      ofendidos: this.hasMany( ProcessoInstrucaoPreparatoriaOfendido, "processoInstrucaoPreparatoria_id"),
      possuidores: this.hasMany( ProcessoInstrucaoPreparatoriaPossuidor, "processoInstrucaoPreparatoria_id"),
      suspeitos: this.hasMany( ProcessoInstrucaoPreparatoriaSuspeito, "processoInstrucaoPreparatoria_id"),
      testemunhas: this.hasMany( ProcessoInstrucaoPreparatoriaTestemunha, "processoInstrucaoPreparatoria_id"),
      vitimas: this.hasMany( ProcessoInstrucaoPreparatoriaVitima, "processoInstrucaoPreparatoria_id")

    }
  }

}
