import {Model} from "@vuex-orm/core";
import Provincia from "src/store/models/provincia/provincia";
import Pais from "../pais/pais";
import TipoDocumentoIdentificacao from "../tipoDocumentoIdentificacao/tipoDocumentoIdentificacao"
import Profissao from "../profissao/profissao";
import PecaProcesso from "../pecaProcesso/pecaProcesso"

export default class Arguido extends Model {

  static  entity = 'arguidos'

  static fields() {
    return {
      id: this.attr(null),
      nome: this.attr(''),
      apelido: this.attr(''),
      sexo: this.attr(''),
      dataNascimento: this.attr(''),
      naturalidade: this.attr(''),
      nacionalidade_id: this.attr(''),
      provincia_id: this.attr(''),
      localNascimento: this.attr(''),
      telemovel1: this.attr(''),
      telemovel2: this.attr(''),
      estadoCivil: this.attr(''),
      morada: this.attr(''),
      numDocumentoIndentificacao: this.attr(''),
      tipoDocumento_id: this.attr(''),
      documentoValidade: this.attr(''),
      // pecaProcesso_id: this.attr(''),
      profissao_id: this.attr(''),
      localTrabalho: this.attr(''),
      ocupacao: this.attr(''),

      // Relationshiops
      nacionalidade: this.belongsTo(Pais, 'nacionalidade_id'),
      provincia: this.belongsTo(Provincia, 'provincia_id'),
      tipoDocumento: this.belongsTo(TipoDocumentoIdentificacao, 'tipoDocumento_id'),
      profissao: this.belongsTo(Profissao, 'profissao_id'),
      // pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id')
    }
  }

}
