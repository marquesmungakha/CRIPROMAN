import {Model} from "@vuex-orm/core";
import Acarreacao from "../acarreacao/acarreacao"
import CorpoDelito from "../corpoDelito/corpoDelito";
import Declaracao from "../declaracao/declaracao";
import Denuncia from "../denuncia/denuncia";
import Detencao from "../detencao/detencao";
import Pergunta from "../pergunta/pergunta";

export default class Inspector extends Model {

  static  entity = 'inspectores'

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
      acarreacoes: this.hasMany(Acarreacao, "inspector_id"),
      corposDelito: this.hasMany(CorpoDelito, "inspector_id"),
      declaracoes: this.hasMany(Declaracao, "inspector_id"),
      denuncias: this.hasMany(Denuncia, "inspector_id"),
      detencoes: this.hasMany(Detencao, "inspector_id"),
      perguntas: this.hasMany(Pergunta, "inspector_id")

    }
  }

}
