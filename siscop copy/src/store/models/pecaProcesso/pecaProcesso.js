import {Model} from "@vuex-orm/core";
import Inspector from "../inspector/inspector";
import BemSubtraido from "../bemSubtraido/bemSubtraido";
import MeiosUtilizado from "../meiosUtilizado/meiosUtilizado";
import ObjectoApreendido from "../objectoApreendido/objectoApreendido";
import PecaProcessoDeclarante from "./pecaProcessoDeclarante";
import PecaProcessoDenunciante from "./pecaProcessoDenunciante";
import PecaProcessoAcusado from "./pecaProcessoAcusado";
import PecaProcessoCustodiado from "./pecaProcessoCustodiado";
import PecaProcessoDetido from "./pecaProcessoDetido";
import PecaProcessoArguido from "./pecaProcessoArguido";
import PecaProcessoOfendido from "./pecaProcessoOfendido";
import PecaProcessoPossuidor from "./pecaProcessoPossuidor";
import PecaProcessoSuspeito from "./pecaProcessoSuspeito";
import PecaProcessoTestemunha from "./pecaProcessoTestemunha";
import PecaProcessoVitima from "./pecaProcessoVitima";

export default class PecaProcesso extends Model {

  static  entity = 'pecasProcesso'

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
      inspector: this.belongsTo(Inspector, 'inspector_id'),
      acusados: this.hasMany( PecaProcessoAcusado, "pecaProcesso_id"),
      custodiados: this.hasMany( PecaProcessoCustodiado, "pecaProcesso_id"),
      bensSubtraido: this.hasMany(BemSubtraido, "pecaProcesso_id"),
      declarantes: this.hasMany( PecaProcessoDeclarante, 'pecaProcesso_id'),
      denunciantes: this.hasMany( PecaProcessoDenunciante, 'pecaProcesso_id'),
      detidos: this.hasMany( PecaProcessoDetido,"pecaProcesso_id"),
      arguidos: this.hasMany(PecaProcessoArguido, "pecaProcesso_id"),
      ofendidos: this.hasMany( PecaProcessoOfendido, "pecaProcesso_id"),
      meiosUtilizados: this.hasMany(MeiosUtilizado, "pecaProcesso_id"),
      objectosApreendido: this.hasMany(ObjectoApreendido, "pecaProcesso_id"),
      possuidores: this.hasMany( PecaProcessoPossuidor, "pecaProcesso_id"),
      suspeitos: this.hasMany( PecaProcessoSuspeito, "pecaProcesso_id"),
      testemunhas: this.hasMany( PecaProcessoTestemunha, "pecaProcesso_id"),
      vitimas: this.hasMany( PecaProcessoVitima, "pecaProcesso_id")


      // acusados: this.belongsToMany(Acusado, PecaProcessoAcusado,'acusado_id', "pecaProcesso_id"),
      // custodiados: this.belongsToMany(Custodiado, PecaProcessoCustodiado, 'custodiado_id', "pecaProcesso_id"),
      // bensSubtraido: this.hasMany(BemSubtraido, "pecaProcesso_id"),
      // declarantes: this.belongsToMany(Declarante, PecaProcessoDeclarante, 'declarante_id', 'pecaProcesso_id'),
      // denunciantes: this.belongsToMany(Denunciante, PecaProcessoDenunciante, 'pecaProcesso_id', 'denunciante_id')
      // detidos: this.belongsToMany(Detido, PecaProcessoDetido, 'detido_id', "pecaProcesso_id"),
      // arguidos: this.belongsToMany(Arguido, PecaProcessoArguido, 'arguido_id', "pecaProcesso_id"),
      // ofendidos: this.belongsToMany(Ofendido, PecaProcessoOfendido, 'ofendido_id', "pecaProcesso_id"),
      // meiosUtilizados: this.hasMany(MeiosUtilizado, "pecaProcesso_id"),
      // objectosApreendido: this.hasMany(ObjectoApreendido, "pecaProcesso_id"),
      // possuidores: this.belongsToMany(Possuidor, PecaProcessoPossuidor, 'possuidor_id', "pecaProcesso_id"),
      // suspeitos: this.belongsToMany(Suspeito, PecaProcessoSuspeito, 'suspeito_id', "pecaProcesso_id"),
      // testemunhas: this.belongsToMany(Testemunha, PecaProcessoTestemunha, 'testemunha_id', "pecaProcesso_id"),
      // vitimas: this.belongsToMany(Vitima, PecaProcessoVitima, 'vitima_id', "pecaProcesso_id")

    }
  }

}
