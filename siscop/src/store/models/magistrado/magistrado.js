import { Model } from "@vuex-orm/core";

export default class Magistrado extends Model {

  static  entity = 'magistrados'

  static fields() {
    return {
      id: this.attr(null),
      numero: this.attr(''),
      nome: this.attr(''),
      apelido: this.attr(''),
      sexo: this.attr(''),
      idade: this.attr(''),
      telemovel1:this.attr(''),
      telemovel2:this.attr(''),
      fotografia:this.attr(''),
      descricao:this.attr(''),
      cargoOcupado:this.attr(''),
    }
  }

}
