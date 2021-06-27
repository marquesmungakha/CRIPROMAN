import {Model} from "@vuex-orm/core";
import TipoParecerAuto from "../tipoParecerAuto/tipoParecerAuto";
import Inspector from "../inspector/inspector";
import AutoEntrada from "../autoEntrada/autoEntrada";

export default class ParecerAuto extends Model {

  static  entity = 'pareceresAuto'

  static fields() {
    return {
      id: this.attr(null),
      parecer: this.attr(''),
      seccao: this.attr(''),
      brigada: this.attr(''),
      tipoParecer_id: this.attr(''),
      dataRegisto: this.attr(''),
      inspector_id: this.attr(''),
      auto_id: this.attr(''),

      // Relationshiops
      tipoParecer: this.belongsTo(TipoParecerAuto, 'tipoParecer_id'),
      inspector: this.belongsTo(Inspector, 'inspector_id'),
      auto: this.belongsTo(AutoEntrada, 'auto_id')
    }

  }
}
