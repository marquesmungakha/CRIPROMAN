import {Model} from "@vuex-orm/core";
import Provincia from "src/store/models/provincia/provincia";
import Alocacao from "../alocacao/alocacao";
import AutoEntrada from "../autoEntrada/autoEntrada";
import Distrito from "../distrito/distrito"
import Orgao from "../orgao/orgao";
import ProcessoInstrucaoPreparatoria from "../processoInstrucaoPreparatoria/processoInstrucaoPreparatoria";
import ResponsavelOrgao from "../responsavelOrgao/responsavelOrgao";
import TipoOrgao from "../tipoOrgao/tipoOrgao";

export default class DependenciaOrgao extends Model {

  static  entity = 'dependenciasOrgao'

  static fields() {

    return {
      id: this.attr(null),
      sigla: this.attr(''),
      designacao: this.attr(''),
      nivel: this.attr(''),
      endereco: this.attr(''),
      provincia_id: this.attr(''),
      distrito_id: this.attr(''),
      tipoOrgao_id: this.attr(''),
      orgao_id: this.attr(''),

      // Relationshiops
      provincia: this.belongsTo(Provincia, 'provincia_id'),
      distrito: this.belongsTo(Distrito, 'distrito_id'),
      tipoOrgao: this.belongsTo(TipoOrgao, 'tipoOrgao_id'),
      orgao: this.belongsTo(Orgao, 'orgao_id'),

      alocacoes: this.hasMany(Alocacao, "orgao_id"),
      autosEntrada: this.hasMany(AutoEntrada, "orgao_id"),
      processosInstrucaoPreparatoria: this.hasMany(ProcessoInstrucaoPreparatoria, "orgao_id"),
      responsaveisOrgao: this.hasMany(ResponsavelOrgao, "orgao_id"),
    }
  }

}
