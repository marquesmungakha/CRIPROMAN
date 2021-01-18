import { Model } from "@vuex-orm/core";
import Inspector from "../inspector/inspector";
import Acusado from "../acusado/acusado"
import BemSubtraido from "../bemSubtraido/bemSubtraido";
import Custodiado from "../custodiado/custodiado";
import Declarante from "../declarante/declarante";
import Denunciante from "../denunciante/denunciante"
import Detido from "../detido/detido";
import MeiosUtilizado from "../meiosUtilizado/meiosUtilizado";
import ObjectoApreendido from "../objectoApreendido/objectoApreendido";
import Arguido from "../arguido/arguido";
import Ofendido from "../ofendido/ofendido"
import Possuidor from "../possuidor/possuidor";
import Testemunha from "../testemunha/testemunha";
import Vitima from "../vitima/vitima";
import Suspeito from "../suspeito/suspeito";

export default class PecaProcesso extends Model {

  static  entity = 'acarreacaos'

  static fields() {
    return {
      id: this.attr(null),
      numero: this.attr(''),
      dataAbertura: this.attr(''),
      sexo: this.attr(''),
      descricao: this.attr(''),
      anexo: this.attr(''),
      inspector_id: this.attr(''),

      // Relationshiops
      inspector: this.belongsTo(Inspector,'inspector_id'),
      acusados: this.hasMany(Acusado, "pecaProcesso_id"),
      custodiados: this.hasMany(Custodiado, "pecaProcesso_id"),
      bensSubtraido: this.hasMany(BemSubtraido, "pecaProcesso_id"),
      declarantes: this.hasMany(Declarante, "pecaProcesso_id"),
      denunciantes: this.hasMany(Denunciante, "pecaProcesso_id"),
      detidos: this.hasMany(Detido, "pecaProcesso_id"),
      arguidos: this.hasMany(Arguido, "pecaProcesso_id"),
      ofendidos: this.hasMany(Ofendido, "pecaProcesso_id"),
      meiosUtilizados: this.hasMany(MeiosUtilizado, "pecaProcesso_id"),
      objectosApreendido: this.hasMany(ObjectoApreendido, "pecaProcesso_id"),
      possuidores: this.hasMany(Possuidor, "pecaProcesso_id"),
      suspeitos: this.hasMany(Suspeito, "pecaProcesso_id"),
      testemunhas: this.hasMany(Testemunha, "pecaProcesso_id"),
      vitimas: this.hasMany(Vitima, "pecaProcesso_id")

    }
  }

}
