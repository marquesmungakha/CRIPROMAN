import {Model} from "@vuex-orm/core";
import Orgao from "src/store/models/orgao/orgao";
import Cargo from "src/store/models/cargo/cargo";

export default class ResponsavelOrgao extends Model {

  static  entity = 'responsaveisOrgao'

  static fields() {

    return {
      id: this.attr(null),
      dataAtribuicao: this.attr(''),
      orgao_id: this.attr(''),
      activo: this.attr(''),
      dataTermino: this.attr(''),
      anexo: this.attr(''),
      membro_id: this.attr(''),
      cargo_id: this.attr(''),

      // Relationshiops
      orgao: this.belongsTo(Orgao, 'orgao_id'),
      cargo: this.belongsTo(Cargo, 'cargo_id')

    }
  }

}
