import {Model} from "@vuex-orm/core";

export default class Quadro extends Model {

  static  entity = 'quadros'

  static fields() {
    return {
      id: this.attr(null),
      numero: this.attr(''),
      nome: this.attr(''),
      apelido: this.attr(''),
      sexo: this.attr(''),
      idade: this.attr(''),
      telemovel1: this.attr(''),
      telemovel2: this.attr(''),
      fotografia: this.attr(''),
      descricao: this.attr(''),
      cargoOcupado: this.attr(''),

      // RelationShip
      // agentes: this.hasMany(Agente, "inspector_id")


    }
  }

}
