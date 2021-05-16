import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import VuexORM from '@vuex-orm/core'
import VuexORMAxios from '@vuex-orm/plugin-axios'
import Pais from './models/pais/pais'
import Provincia from "./models/provincia/provincia"
import Login from "./models/login/login"
import Acarreacao from "./models/acarreacao/acarreacao"
import Acusado from "./models/acusado/acusado" 
import Agente from "./models/agente/agente"
import Alocacao from "./models/alocacao/alocacao"
import Apreensao from "./models/apreensao/apreensao"
import Arguido from "./models/arguido/arguido"
import AutoEntrada from "./models/autoEntrada/autoEntrada"
import BemSubtraido from "./models/bemSubtraido/bemSubtraido"
import Cargo from "./models/cargo/cargo"
import ClasseJudicial from "./models/jurisdicao/jurisdicao"
import CorpoDelito from "./models/corpoDelito/corpoDelito"
import Crime from "./models/crime/crime"
import Custodiado from "./models/custodiado/custodiado"
import Declaracao from "./models/declaracao/declaracao"
import Declarante from "./models/declarante/declarante"
import Denuncia from "./models/denuncia/denuncia"
import Denunciante from "./models/denunciante/denunciante"
import Despacho from "./models/despacho/despacho"
import Detencao from "./models/detencao/detencao"
import Detido from "./models/detido/detido"
import Bairro from "./models/bairro/bairro"
import Cidade from "./models/cidade/cidade"
import Distrito from "./models/distrito/distrito"
import Localidade from "./models/localidade/localidade"
import PostoAdministrativo from "./models/postoAdministrativo/postoAdministrativo"
import FormaProcesso from "./models/formaProcesso/formaProcesso"
import Funcao from "./models/funcao/funcao"
import ImpressaoDigital from "./models/impressaoDigital/impressaoDigital"
import Individuo from "./models/individuo/Individuo"
import Inspector from "./models/inspector/inspector"
import Magistrado from "./models/magistrado/magistrado"
import Marca from "./models/marca/marca"
import MeioUtilizado from "./models/meiosUtilizado/meiosUtilizado"
import Modelo from "./models/modelo/modelo"
import ModoAtuacao from "./models/modoAtuacao/modoAtuacao"
import MotivoDetencao from "./models/motivoDetencao/motivoDetencao"
import ObjectoApreendido from "./models/objectoApreendido/objectoApreendido"
import ObjectoDetido from "./models/objectoDetido/objectoDetido"
import Ofendido from "./models/ofendido/ofendido"
import Orgao from "./models/orgao/orgao"
import DependenciaOrgao from "./models/dependenciaOrgao/dependenciaOrgao"
import PecaProcesso from "./models/pecaProcesso/pecaProcesso"
import PecaProcessoAcusado from "./models/pecaProcesso/pecaProcessoAcusado"
import PecaProcessoArguido from "./models/pecaProcesso/pecaProcessoArguido"
import PecaProcessoCustodiado from "./models/pecaProcesso/pecaProcessoCustodiado"
import PecaProcessoDeclarante from "./models/pecaProcesso/pecaProcessoDeclarante"
import PecaProcessoDenunciante from "./models/pecaProcesso/pecaProcessoDenunciante"
import PecaProcessoDetido from "./models/pecaProcesso/pecaProcessoDetido"
import PecaProcessoOfendido from "./models/pecaProcesso/pecaProcessoOfendido"
import PecaProcessoPossuidor from "./models/pecaProcesso/pecaProcessoPossuidor"
import PecaProcessoSuspeito from "./models/pecaProcesso/pecaProcessoSuspeito"
import PecaProcessoTestemunha from "./models/pecaProcesso/pecaProcessoTestemunha"
import PecaProcessoVitima from "./models/pecaProcesso/pecaProcessoVitima"
import ParecerProcesso from "./models/parecerProcesso/parecerProcesso"
import Pergunta from "./models/pergunta/pergunta"
import Possuidor from "./models/possuidor/possuidor"
import ProcessoInstrucaoPreparatoria from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoria"
import ProcessoInstrucaoPreparatoriaAcusado from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaAcusado"
import ProcessoInstrucaoPreparatoriaArguido from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaArguido"
import ProcessoInstrucaoPreparatoriaCustodiado from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaCustodiado"
import ProcessoInstrucaoPreparatoriaDeclarante from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaDeclarante"
import ProcessoInstrucaoPreparatoriaDenunciante from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaDenunciante"
import ProcessoInstrucaoPreparatoriaDetido from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaDetido"
import ProcessoInstrucaoPreparatoriaOfendido from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaOfendido"
import ProcessoInstrucaoPreparatoriaPossuidor from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaPossuidor"
import ProcessoInstrucaoPreparatoriaSuspeito from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaSuspeito"
import ProcessoInstrucaoPreparatoriaTestemunha from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaTestemunha"
import ProcessoInstrucaoPreparatoriaVitima from "./models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoriaVitima"
import ProcessoInvestigacao from "./models/processoInvestigacao/processoInvestigacao"
import Profissao from "./models/profissao/profissao"
import Quadro from "./models/quadro/quadro"
import ResponsavelOrgao from "./models/responsavelOrgao/responsavelOrgao"
import SituacaoPrisional from "./models/situacaoPrisional/situacaoPrisional"
import Suspeito from "./models/suspeito/suspeito"
import Testemunha from "./models/testemunha/testemunha"
import TipoAuto from "./models/tipoAuto/tipoAuto"
import TipoDedo from "./models/tipoDedo/tipoDedo"
import TipoDocumentoIdentificacao from "./models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao"
import TipoMeio from "./models/tipoMeio/tipoMeio"
import TipoOrgao from "./models/tipoOrgao/tipoOrgao"
import TipoParecer from "./models/tipoParecer/tipoParecer"
import Vitima from "./models/vitima/vitima"


Vue.use(Vuex)

VuexORM.use(VuexORMAxios, {
  axios,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
  },
  baseURL: 'http://localhost:8080/api'
})


// // Request interceptor for API calls
axios.interceptors.request.use(
  async config => {
    // const value = await redisClient.get(rediskey)
    // const keys = JSON.parse(value)
    config.headers = {
      'Accept': 'application/json',
      // 'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded'
    }
    if (Login.all().length != 0) {
      // console.log(">>VFF "+JSON.parse(localStorage.getItem('vuex')).user.token+"------------"+ JSON.stringify(localStorage));
      config.headers['X-Auth-Token'] = [
        '',Login.query().first().access_token
      ].join(' ')

    } else {
      //console.log(">>VFF2 "+JSON.parse(localStorage.getItem('vuex'))+"------------"+ JSON.stringify(localStorage));
      delete config.headers['Authorization']
    }
    return config;
  },
  error => {
    Promise.reject(error)
  });

// Response interceptor for API calls
axios.interceptors.response.use((response) => {
  return response
}, async function (error) {
//  console.log(localStorage.getItem('refresh_token'))
 // console.log(Login.query().all())

  const originalRequest = error.config;
  if ((error.response.status === 403 || error.response.status === 401) && !originalRequest._retry) {
    originalRequest._retry = true;

 //   const authUser = Login.query().first();
    const rToken = Login.query().first().refresh_token;

  //  console.log("LOGIN first "+Login.query().first())
    console.log('attempt to refresh token here -' + 'http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token=' + rToken);
    // console.log('r token '+authUser.auth.isAuthenticated.refresh_token+'_____________-'+JSON.stringify(authUser.auth.isAuthenticated))
    //, {grant_type:'refresh_token',refresh_token:tok}
    return axios.post('http://localhost:8080/oauth/access_token?grant_type=refresh_token&refresh_token=' + rToken)
      .then(({data}) => {
        console.log('==got the following token back: ' + data.access_token + '___________________________________________')
        //console.log("------------"+JSON.stringify(data)+' ')

        //window.localStorage.setItem('vuex.auth.isAuthenticated.refresh_token', data.refresh_token);

        //This updates underyling user token with returned data - by updating it here saves recalling this method
        //over and over again
        // const login = Login.all()[0].fields().access_token // = data.access_token;
       // Login.insert(login);
       //  console.log(login);
         //store.dispatch('user/setToken', data.access_token);
        // const authUser1 = JSON.parse(window.localStorage.getItem('vuex'));
        // //This is simply checking set data matches what came back in http request
        // if (data.access_token === authUser1.user.token) {
        //   console.log('Great news the underlying user token has now been updated meaning the user has now been re-authenticated')
        // }

        axios.defaults.headers.common['X-Auth-Token'] = data.access_token;
    return axios(originalRequest);
      });
  }
  return Promise.reject(error);
});

const database = new VuexORM.Database()

database.register(Login)
database.register(Pais)
database.register(Acarreacao)
database.register(Provincia)
database.register(Acusado)
database.register(Agente)
database.register(Alocacao)
database.register(Apreensao)
database.register(Arguido)
database.register(AutoEntrada)
database.register(BemSubtraido)
database.register(Cargo)
database.register(ClasseJudicial)
database.register(CorpoDelito)
database.register(Crime)
database.register(Custodiado)
database.register(Declaracao)
database.register(Declarante)
database.register(Denuncia)
database.register(Denunciante)
database.register(Despacho)
database.register(Detencao)
database.register(Detido)
database.register(Bairro)
database.register(Cidade)
database.register(Distrito)
database.register(Localidade)
database.register(PostoAdministrativo)
database.register(FormaProcesso)
database.register(Funcao)
database.register(ImpressaoDigital)
database.register(Individuo)
database.register(Inspector)
database.register(Magistrado)
database.register(Marca)
database.register(MeioUtilizado)
database.register(Modelo)
database.register(ModoAtuacao)
database.register(MotivoDetencao)
database.register(ObjectoApreendido)
database.register(ObjectoDetido)
database.register(Ofendido)
database.register(Orgao)
database.register(DependenciaOrgao)
database.register(ParecerProcesso)
database.register(PecaProcesso)
database.register(PecaProcessoAcusado)
database.register(PecaProcessoArguido)
database.register(PecaProcessoCustodiado)
database.register(PecaProcessoDeclarante)
database.register(PecaProcessoDenunciante)
database.register(PecaProcessoDetido)
database.register(PecaProcessoOfendido)
database.register(PecaProcessoPossuidor)
database.register(PecaProcessoSuspeito)
database.register(PecaProcessoTestemunha)
database.register(PecaProcessoVitima)
database.register(Pergunta)
database.register(Possuidor)
database.register(ProcessoInstrucaoPreparatoria)
database.register(ProcessoInstrucaoPreparatoriaAcusado)
database.register(ProcessoInstrucaoPreparatoriaArguido)
database.register(ProcessoInstrucaoPreparatoriaCustodiado)
database.register(ProcessoInstrucaoPreparatoriaDeclarante)
database.register(ProcessoInstrucaoPreparatoriaDenunciante)
database.register(ProcessoInstrucaoPreparatoriaDetido)
database.register(ProcessoInstrucaoPreparatoriaOfendido)
database.register(ProcessoInstrucaoPreparatoriaPossuidor)
database.register(ProcessoInstrucaoPreparatoriaSuspeito)
database.register(ProcessoInstrucaoPreparatoriaTestemunha)
database.register(ProcessoInstrucaoPreparatoriaVitima)
database.register(ProcessoInvestigacao)
database.register(Profissao)
database.register(Quadro)
database.register(ResponsavelOrgao)
database.register(SituacaoPrisional)
database.register(Suspeito)
database.register(Testemunha)
database.register(TipoAuto)
database.register(TipoDedo)
database.register(TipoDocumentoIdentificacao)
database.register(TipoMeio)
database.register(TipoOrgao)
database.register(TipoParecer)
database.register(Vitima)

export default new Vuex.Store({
  plugins: [VuexORM.install(database)]
})

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */
//
// export default function (/* { ssrContext } */) {
//   const Store = new Vuex.Store({
//     modules: {
//       pais,
//      login
//     },
//
//     // enable strict mode (adds overhead!)
//     // for dev mode only
//     strict: process.env.DEBUGGING
//   })
//
//   return Store
// }
