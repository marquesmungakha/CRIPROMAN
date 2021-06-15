import {Model} from "@vuex-orm/core";
import TipoMeio from "src/store/models/tipoMeio/tipoMeio";
import Marca from "../marca/marca"
import Modelo from "../modelo/modelo"
import PecaProcesso from "src/store/models/pecaProcesso/pecaProcesso";
import AutoEntrada from "../autoEntrada/autoEntrada";

export default class MeiosUtilizado extends Model {

  static  entity = 'meiosUtilizados'

  static fields() {

    return {
      id: this.attr(null),
      matricula: this.attr(''),
      cor: this.attr(''),
      calibre: this.attr(''),
      designacao: this.attr(''),
      tipoMeio_id: this.attr(''),
      marca_id: this.attr(''),
      modelo_id: this.attr(''),
      chassi: this.attr(''),
      numeroMotor: this.attr(''),
      pecaProcesso_id: this.attr(''),
      autoEntrada_id: this.attr(''),


      // Relationshiops
      tipoMeio: this.belongsTo(TipoMeio, 'tipoMeio_id'),
      marca: this.belongsTo(Marca, 'marca_id'),
      modelo: this.belongsTo(Modelo, 'modelo_id'),
      pecaProcesso: this.belongsTo(PecaProcesso, 'pecaProcesso_id'),
      autoEntrada: this.belongsTo(AutoEntrada, 'autoEntrada_id')
    }
  }

}
