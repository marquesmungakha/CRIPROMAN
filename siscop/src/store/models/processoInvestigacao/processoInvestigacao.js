import {Model} from "@vuex-orm/core";
import AutoEntrada from "src/store/models/autoEntrada/autoEntrada";
import FormaProcesso from "src/store/models/formaProcesso/formaProcesso";
import Inspector from "src/store/models/inspector/inspector";
import Magistrado from "src/store/models/magistrado/magistrado";
import Acarreacao from "../acarreacao/acarreacao"
import Apreensao from "../apreensao/apreensao";
import CorpoDelito from "../corpoDelito/corpoDelito";
import Declaracao from "../declaracao/declaracao";
import Denuncia from "../denuncia/denuncia";
import Despacho from "../despacho/despacho";
import Detencao from "../detencao/detencao";
import Pergunta from "../pergunta/pergunta";

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
      formaProcesso_id: this.attr(''),
      inspector_id: this.attr(''),
      magistrado_id: this.attr(''),
      autor: this.attr(''),

      // Relationshiops
      numeroAuto: this.belongsTo(AutoEntrada, 'numeroAuto_id'),
      formaProcesso: this.belongsTo(FormaProcesso, 'formaProcesso_id'),
      inspector: this.belongsTo(Inspector, 'inspector_id'),
      magistrado: this.belongsTo(Magistrado, 'magistrado_id'),

      acarreacao: this.hasMany(Acarreacao, 'processo_id'),
      apreensoes: this.hasMany(Apreensao, 'processo_id'),
      corposDelito: this.hasMany(CorpoDelito, 'processo_id'),
      declaracoes: this.hasMany(Declaracao, 'processo_id'),
      denuncias: this.hasMany(Denuncia, 'processo_id'),
      despachos: this.hasMany(Despacho, 'processo_id'),
      detencoes: this.hasMany(Detencao, 'processo_id'),
      perguntas: this.hasMany(Pergunta, 'processo_id')
    }
  }

}
