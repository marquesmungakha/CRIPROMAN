import { Model } from "@vuex-orm/core";

export default class Individuo extends Model {

  static  entity = 'individuos'

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
      telemovel1:this.attr(''),
      telemovel2:this.attr(''),
      estadoCivil:this.attr(''),
      morada:this.attr(''),
      numDocumentoIndentificacao:this.attr(''),
      tipoDocumento_id:this.attr(''),
      documentoValidade:this.attr(''),
      pecaProcesso_id:this.attr(''),
      profissao_id:this.attr(''),

      // Relationshiops

    }
  }

}
