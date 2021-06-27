import {Model} from "@vuex-orm/core";


export default class AutoEntrada extends Model {

  static  entity = 'autosEntrada'

  static fields() {

    return {
      id: this.attr(null),
      tipoAuto: this.attr(''),
      numero: this.attr(''),
      dataAbertura: this.attr(''),
      descricao: this.attr(''),
      anexo: this.attr(''),
      classeJudicial: this.attr(''),
      crime: this.attr(''),
      modusOperandi: this.attr(''),
      horaOcorrencia: this.attr(''),
      infraccao: this.attr(''),
      endereco: this.attr(''),
      responsavelLocal: this.attr(''),
      contacto: this.attr(''),
      orgao: this.attr(''),
      inspector: this.attr(''),
      uuid: this.attr('')
    }
  }

}
