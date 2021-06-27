<template>
  <div id="app">
    <a href="https://github.com/seungwoo321/vue-pivottable" target="_blank" style="position: fixed; top: 1rem; right: 1rem;">
      <svg id="i-github" viewBox="0 0 64 64" width="36" height="36">
        <path stroke-width="0" fill="black" d="M32 0 C14 0 0 14 0 32 0 53 19 62 22 62 24 62 24 61 24 60 L24 55 C17 57 14 53 13 50 13 50 13 49 11 47 10 46 6 44 10 44 13 44 15 48 15 48 18 52 22 51 24 50 24 48 26 46 26 46 18 45 12 42 12 31 12 27 13 24 15 22 15 22 13 18 15 13 15 13 20 13 24 17 27 15 37 15 40 17 44 13 49 13 49 13 51 20 49 22 49 22 51 24 52 27 52 31 52 42 45 45 38 46 39 47 40 49 40 52 L40 60 C40 61 40 62 42 62 45 62 64 53 64 32 64 14 50 0 32 0 Z"></path>
      </svg>
    </a>
      <div class="title">
        <h3>Tabela Dinâmica - Modelo 1, 2 e 8</h3>
        <!--small>Sample Dataset: Tips({{ filteredData.length }})</small-->
      </div>

      <div>
        <select v-model="locale">
          <option value="en" :selected="locale === 'en' ? 'selected' : undefined">en</option>
          <option value="pt" :selected="locale === 'pt' ? 'selected' : undefined">pt</option>
        </select>
      </div>
      <!-- {{ localeStrings[locale] }} -->
      <vue-pivottable-ui
        v-model="config"
        :data="data"
        :localeStrings="localeStrings[locale]"
        :rendererName="rendererName"
        :aggregatorName="aggregatorName"
        :tableColorScaleGenerator="colorScaleGenerator"
        :attributes="attributes"
        :valueFilter="valueFilter"
        :rows="rows"
        :cols="cols"
        :vals="vals"
        :disabledFromDragDrop="disabledFromDragDrop"
        :sortonlyFromDragDrop="sortonlyFromDragDrop"
        :hiddenFromDragDrop="hiddenFromDragDrop"
        :sorters="sorters"
        rowOrder="value_a_to_z"
      >
        <div v-if="loading" slot="output">
          loading...
        </div>
        <template v-if="!loading" slot="output" slot-scope="{ pivotData }">
          {{ pivotData }}
        </template>
        <template slot="pvtAttr" slot-scope="{ name }">
          {{ name }}
        </template>
      </vue-pivottable-ui>
      <!-- <vue-pivottable
        v-model="config"
        :data="data"
        :localeStrings="localeStrings[locale]"
        :rendererName="rendererName"
        :aggregatorName="aggregatorName"
        :tableColorScaleGenerator="colorScaleGenerator"
        :attributes="attributes"
        :valueFilter="valueFilter"
        :rows="rows"
        :cols="cols"
        :vals="vals"
        :disabledFromDragDrop="disabledFromDragDrop"
        :sortonlyFromDragDrop="sortonlyFromDragDrop"
        :hiddenFromDragDrop="hiddenFromDragDrop"
        :sorters="sorters"
        rowOrder="value_a_to_z"
      >
      </vue-pivottable> -->
      <!-- fix issue #14 -->
      <!--
      <div>
        {{ Object.keys(config).length }}
        {{ config }}
      </div>
      
    <footer>Released under the <a href="//github.com/seungwoo321/vue-pivottable/blob/master/LICENSE">MIT</a> license. <a href="//github.com/seungwoo321/vue-pivottable">View source.</a></footer>-->
  </div>
</template>

<script>
// import { VuePivottable, VuePivottableUi } from 'vue-pivottable'
import { VuePivottableUi, PivotUtilities, Renderer } from 'vue-pivottable'
// import { VuePivottable, PivotUtilities, Renderer } from '../../../src'
import 'vue-pivottable/dist/vue-pivottable.css'
import { scaleLinear } from 'd3-scale'
import { QSpinnerBall } from 'quasar'
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import Provincia from 'src/store/models/provincia/provincia'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Pais from 'src/store/models/pais/pais'
import TipoParecer from 'src/store/models/tipoParecer/tipoParecer'
import ProcessoInvestigacao from 'src/store/models/processoInvestigacao/processoInvestigacao'
import Despacho from 'src/store/models/despacho/despacho'
import Pergunta from 'src/store/models/pergunta/pergunta'
import PecaProcessoArguido from 'src/store/models/pecaProcesso/pecaProcessoArguido'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'
import SituacaoPrisional from 'src/store/models/situacaoPrisional/situacaoPrisional'


export default {
  components: {
    VuePivottableUi
    // VuePivottable
  },
  name: 'app',
  data () {
    return {
      // fix issue #27
      valueFilter: {
        Meal: {
          Dinner: true
        }
      },
      orgao: {
        codigo: '',
        sigla: '',
        designacao: ''
      },
      config: {},
      filteredData: [],
      data: [],
      attributes: ['Numero Processo', 
                   'Forma Processo', 
                   'Movimento', 
                   'Orgão',
                   'Destino',
                   'Tipo Parecer',
                   'Familia Delitiva',
                   'Crimes',
                   'Autor Processo',
                   'Situação Prisional',
                   'Periodo - Mensal',
                   'Periodo - Trimestral',
                   'Periodo - Semestral',
                   'Periodo - Anual'
                   ],
      rows: ['Forma Processo'],
      cols: ['Movimento'],
      vals: ['Numero Processo'],
      disabledFromDragDrop: [], // ['Forma Processo'],
      hiddenFromDragDrop: ['Numero Processo'],
      sortonlyFromDragDrop: [], // ['Party Size'],
      pivotColumns: ['Movimento'],
      loading: false,
      aggregatorName: 'Count',
      rendererName: 'Table',
      localeStrings: {
        en: {
          renderError: 'An error occurred rendering the PivotTable results.',
          computeError: 'An error occurred computing the PivotTable results.',
          uiRenderError: 'An error occurred rendering the PivotTable UI.',
          selectAll: 'Select All',
          selectNone: 'Select None',
          tooMany: 'too many values to show',
          filterResults: 'Filter values',
          totals: 'Totals',
          only: 'only',
          rendererNames: {
            Table: 'Table',
            'Table Heatmap': 'Table Heatmap',
            'Table Col Heatmap': 'Table Col Heatmap',
            'Table Row Heatmap': 'Table Row Heatmap',
            'Export Table TSV': 'Export Table TSV',
            'Grouped Column Chart': 'Grouped Column Chart',
            'Stacked Column Chart': 'Stacked Column Chart',
            'Grouped Bar Chart': 'Grouped Bar Chart',
            'Stacked Bar Chart': 'Stacked Bar Chart',
            'Line Chart': 'Line Chart',
            'Dot Chart': 'Dot Chart',
            'Area Chart': 'Area Chart',
            'Scatter Chart': 'Scatter Chart',
            'Multiple Pie Chart': 'Multiple Pie Chart'
          },
          aggregatorMap: {
            Count: 'Count',
            'Count Unique Values': 'Count Unique Values',
            'List Unique Values': 'List Unique Values',
            Sum: 'Sum',
            'Integer Sum': 'Integer Sum',
            Average: 'Average',
            Median: 'Median',
            'Sample Variance': 'Sample Variance',
            'Sample Standard Deviation': 'Sample Standard Deviation',
            Minimum: 'Minimum',
            Maximum: 'Maximum',
            First: 'First',
            Last: 'Last',
            'Sum over Sum': 'Sum over Sum',
            'Sum as Fraction of Total': 'Sum as Fraction of Total',
            'Sum as Fraction of Rows': 'Sum as Fraction of Rows',
            'Sum as Fraction of Columns': 'Sum as Fraction of Columns',
            'Count as Fraction of Total': 'Count as Fraction of Total',
            'Count as Fraction of Rows': 'Count as Fraction of Rows',
            'Count as Fraction of Columns': 'Count as Fraction of Columns'
          }
        },
        pt: {
          renderError: 'Ocorreu um erro ao renderizar os resultados da tabela dinâmica.',
          computeError: 'Ocorreu um erro ao calcular o resultado da tabela dinâmica.',
          uiRenderError: 'Ocorreu um erro ao renderizar a IU da tabela dinâmica.',
          selectAll: 'Selecionar tudo',
          selectNone: 'Tirar a  seleção',
          tooMany: 'Exibir todos valores.',
          filterResults: 'Filtragem de valor',
          totals: 'Totais',
          only: 'Exclusivo',
          rendererMap: {
            Table: 'Tabela',
            'Table Heatmap': 'Tabela mapa de calor',
            'Table Col Heatmap': 'Tabela mapa de calor por coluna',
            'Table Row Heatmap': 'Tabela mapa de calor por linha',
            'Export Table TSV': 'Exportar para tabela TSV',
            'Grouped Column Chart': 'Gráfico agrupado por coluna',
            'Stacked Column Chart': 'Gráfico empilhado por coluna',
            'Grouped Bar Chart': 'Gráfico de barras agrupadas',
            'Stacked Bar Chart': 'Gráfico de barras empilhadas',
            'Line Chart': 'Gráfico de linha',
            'Dot Chart': 'Gráfico de pontos',
            'Area Chart': 'Gráfico de área',
            'Scatter Chart': 'Gráfico de dispersão',
            'Multiple Pie Chart': 'Vários gráficos de pizza'
          },
          aggregatorMap: {
            Count: 'Contagem',
            'Count Unique Values': 'Contagem de valores únicos',
            'List Unique Values': 'Lista de valores únicos',
            Sum: 'Soma',
            'Integer Sum': 'Soma Inteira',
            Average: 'Média',
            Median: 'Mediana',
            'Sample Variance': 'Variância da amostra',
            'Sample Standard Deviation': 'Desvio padrão da amostra',
            Minimum: 'Mínimo',
            Maximum: 'Máximo',
            First: 'Primeiro',
            Last: 'Último',
            'Sum over Sum': 'Soma total',
            'Sum as Fraction of Total': 'Soma de Fracções do valor total',
            'Sum as Fraction of Rows': 'Soma de Fracções por linha',
            'Sum as Fraction of Columns': 'Soma de Fracções por colunas',
            'Count as Fraction of Total': 'Total de Fracções',
            'Count as Fraction of Rows': 'Total de Fracções por linha',
            'Count as Fraction of Columns': 'Total de Fracções por colunas'
          }
        }
      },
      locale: 'pt'
    }
  },
  created () {
     let offset = 0
    this.getAllProcesso(offset)
    this.getAllFormaProcesso(offset)
    this.getAllJurisdicao(offset)
    this.getAllCrime(offset)
    this.getAllMagistrado(offset)
    this.getAllOrgao(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
    this.getAllDespachos(offset)
    this.getAllTipoParecer(offset)
    this.getAllPergunta(offset)
    this.getAllArguidoProcesso(offset)
    this.getAllSituacaoPrisional(offset)
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })
    setTimeout(() => {
      this.$q.loading.hide()
        this.data = this.allData
    }, 1000)
  },
  mounted () {
    let offset = 0
    this.getAllProcesso(offset)
    this.getAllFormaProcesso(offset)
    this.getAllJurisdicao(offset)
    this.getAllCrime(offset)
    this.getAllMagistrado(offset)
    this.getAllOrgao(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
    this.getAllDespachos(offset)
    this.getAllTipoParecer(offset)
    this.getAllPergunta(offset)
    this.getAllArguidoProcesso(offset)
    this.getAllSituacaoPrisional(offset)
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })
    setTimeout(() => {
      this.$q.loading.hide()
        this.data = this.allData
    }, 1000)
  },
  computed: {

    allProcessos () {

    this.orgao = Orgao.query().with('provincia.*')
                                .with('tipoOrgao')
                                .with('distrito.*')
                                .find(Number(localStorage.getItem('orgaoId')))

    let listOrgaoId = Orgao.query().where('orgao_id',Number(localStorage.getItem('orgaoId'))).get()


    if(this.orgao.nivel === 0){
       const findos = ProcessoInvestigacao.query()
                                                .has('despachos',1)
                                                .withAll()
                                                // .with('formaProcesso')
                                                .with('despachos.tipoParecer')
                                                // .with('classeJudicial')
                                                // .with('accoesCrimes.classeJudicial')
                                                // .with('despachos')
                                                // .with('numeroAuto')
                                                // .with('numeroAuto.orgao.tipoOrgao')
                                                // .with('numeroAuto.orgao.provincia.pais')
                                                .all().map(v => ({...v, movimento:'Findos'}))
   
    const transitados = ProcessoInvestigacao.query()
                                                .hasNot('despachos')
                                                .withAll()
                                                // .with('magistrado')
                                                // .with('formaProcesso')
                                                .with('despachos.tipoParecer')
                                                // .with('classeJudicial')
                                                // .with('accoesCrimes.classeJudicial')
                                                // .with('despachos')
                                                // .with('numeroAuto')
                                                // .with('numeroAuto.orgao.tipoOrgao')
                                                // .with('numeroAuto.orgao.provincia.pais')
                                                .all().map(v => ({...v, movimento:'Transitados'}))

    const processo = ProcessoInvestigacao.query()
                                                .withAll()
                                                  // .with('magistrado')
                                                  // .with('formaProcesso')
                                                  // .with('classeJudicial')
                                                  // .with('numeroAuto')
                                                  // .with('accoesCrimes.classeJudicial')
                                                  // .with('numeroAuto.orgao.tipoOrgao')
                                                  // .with('numeroAuto.orgao.provincia.pais')
                                                  .all().map(v => ({...v, movimento:'Entradas'}))

    return processo.concat(transitados).concat(findos)

    }else{
       const findos = ProcessoInvestigacao.query()
                                                .has('despachos',1)
                                                .withAll()
                                                // .with('magistrado')
                                                // .with('formaProcesso')
                                                .with('despachos.tipoParecer')
                                                // .with('classeJudicial')
                                                // .with('accoesCrimes.classeJudicial')
                                                // .with('despachos')
                                                // .with('numeroAuto')
                                                .whereHas('numeroAuto', (query) => {
                                                                        query.where('orgao_id', Number(localStorage.getItem('orgaoId')))
                                                })
                                                .orWhere('numeroAuto.orgao', (query) => {
                                                                        query.whereIdIn(listOrgaoId.map( (orgao) => {return orgao.id} ))
                                                })
                                                // .with('numeroAuto.orgao.provincia.pais')
                                                .all().map(v => ({...v, movimento:'Findos'}))
   
        const transitados = ProcessoInvestigacao.query()
                                                .hasNot('despachos')
                                                .withAll()
                                                // .with('magistrado')
                                                // .with('formaProcesso')
                                                .with('despachos.tipoParecer')
                                                // .with('classeJudicial')
                                                // .with('accoesCrimes.classeJudicial')
                                                // .with('despachos')
                                                // .with('numeroAuto')
                                                // .with('numeroAuto.orgao.tipoOrgao')
                                                // .with('numeroAuto.orgao.provincia.pais')
                                                .whereHas('numeroAuto', (query) => {
                                                                        query.where('orgao_id', Number(localStorage.getItem('orgaoId')))
                                                })
                                                .orWhere('numeroAuto.orgao', (query) => {
                                                                        query.whereIdIn(listOrgaoId.map( (orgao) => {return orgao.id} ))
                                                })
                                                .all().map(v => ({...v, movimento:'Transitados'}))

        const processo = ProcessoInvestigacao.query()
                                                .withAll()
                                                  // .with('magistrado')
                                                  // .with('formaProcesso')
                                                  // .with('classeJudicial')
                                                  // .with('numeroAuto')
                                                  // .with('accoesCrimes.classeJudicial')
                                                  // .with('numeroAuto.orgao.tipoOrgao')
                                                  // .with('numeroAuto.orgao.provincia.pais')
                                                 .whereHas('numeroAuto', (query) => {
                                                                        query.where('orgao_id', Number(localStorage.getItem('orgaoId')))
                                                })
                                                .orWhere('numeroAuto.orgao', (query) => {
                                                                        query.whereIdIn(listOrgaoId.map( (orgao) => {return orgao.id} ))
                                                })
                                                  .all().map(v => ({...v, movimento:'Entradas'}))
      
      return processo.concat(transitados).concat(findos)

    }

    },
    allData () {
      const data = []
      for (let processo of this.allProcessos){

      let autoPergunta = Pergunta.query().where('processo_id',processo.id).get()
      let pecaProcesso = null
      let arguidos = null
      let situacaoPrisional = null
     
      if(autoPergunta !== null)
          pecaProcesso = PecaProcesso.query().with('arguidos.situacaoPrisional').whereIdIn(autoPergunta.map( (auto) => {return auto.id})).get()

      if( pecaProcesso !== null)
          arguidos = pecaProcesso.map( (peca) => {return peca.arguidos})

       if(arguidos.length > 0){
          situacaoPrisional = arguidos[0].map( (arguido) => {return arguido.situacaoPrisional})
          for(let sp of situacaoPrisional)
            data.push(
          {
            'Numero Processo': processo.numeroProcesso, 
            'Forma Processo': processo.formaProcesso.designacao, 
            'Movimento': processo.movimento, 
            'Autor Processo': (processo.autor !== null) ? processo.autor : 'Sem Informação',
            'Tipo Parecer': (processo.despachos.length > 0) ? TipoParecer.query().find(processo.despachos[0].tipoParecer_id).designacao : ' ',
            'Destino': (processo.despachos.length > 0) ? Orgao.query().find(processo.despachos[0].destino_id).designacao : ' ',
            'Orgão': (processo.numeroAuto !== null) ? Orgao.query().find(processo.numeroAuto.orgao_id).designacao : ' ', 
            'Familia Delitiva': (processo.numeroAuto !== null) ? ClasseJudicial.query().find(processo.numeroAuto.classeJudicial_id).designacao : ' ',
            'Crimes': (processo.numeroAuto !== null) ? Crime.query().find(processo.numeroAuto.crime_id).designacao : ' ',
            'Periodo - Mensal': new Date(processo.dataEntrada).toLocaleString('pt-pt', { month: 'short' }),
            'Periodo - Trimestral': this.getCurrentQuartly(new Date(processo.dataEntrada)),
            'Periodo - Semestral': this.getCurrentSemestral(new Date(processo.dataEntrada)),
            'Periodo - Anual': new Date(processo.dataEntrada).getUTCFullYear(),
            'Situação Prisional': (sp !==null) ? sp.designacao : ' '
          }
        )
       }else{
             data.push(
          {
            'Numero Processo': processo.numeroProcesso, 
            'Forma Processo': processo.formaProcesso.designacao, 
            'Movimento': processo.movimento, 
            'Autor Processo': (processo.autor !== null) ? processo.autor : 'Sem Informação',
            'Tipo Parecer': (processo.despachos.length > 0) ? TipoParecer.query().find(processo.despachos[0].tipoParecer_id).designacao : ' ',
            'Destino': (processo.despachos.length > 0) ? Orgao.query().find(processo.despachos[0].destino_id).designacao : ' ',
            'Orgão': (processo.numeroAuto !== null) ? Orgao.query().find(processo.numeroAuto.orgao_id).designacao : ' ', 
            'Familia Delitiva': (processo.numeroAuto !== null) ? ClasseJudicial.query().find(processo.numeroAuto.classeJudicial_id).designacao : ' ',
            'Crimes': (processo.numeroAuto !== null) ? Crime.query().find(processo.numeroAuto.crime_id).designacao : ' ',
            'Periodo - Mensal': new Date(processo.dataEntrada).toLocaleString('pt-pt', { month: 'short' }),
            'Periodo - Trimestral': this.getCurrentQuartly(new Date(processo.dataEntrada)),
            'Periodo - Semestral': this.getCurrentSemestral(new Date(processo.dataEntrada)),
            'Periodo - Anual': new Date(processo.dataEntrada).getUTCFullYear(),
            'Situação Prisional': 'Sem Informação'
          }
        )
       }
       


    
      }
        return data
    },
    sorters () {
      return {
        // 'situacao Prisional': PivotUtilities.sortAs(['Arguido Preso', 'Liberdade provisória', 'Normal'])
        'Movimento': PivotUtilities.sortAs( ['Pendentes', 'Entradas', 'Total', 'Findos','Transitados'])
      }
    },
    unusedAttrs () {
      return this.config.unusedAttrs
    },
    aggregators () {
      const usFmt = PivotUtilities.numberFormat()
      // const usFmtInt = PivotUtilities.numberFormat({ digitsAfterDecimal: 0 })
      // const usFmtPct = PivotUtilities.numberFormat({
      //   digitsAfterDecimal: 1,
      //   scaler: 100,
      //   suffix: '%'
      // })
      return ((tpl) => ({
         Count: tpl.count(usFmtInt),
        // 'Count Unique Values': tpl.countUnique(usFmtInt),
        // 'List Unique Values': tpl.listUnique(', '),
        Sum: tpl.sum(usFmt)
        // 'Integer Sum': tpl.sum(usFmtInt),
        // 'Average': tpl.average(usFmt),
        // 'Median': tpl.median(usFmt),
        // 'Sample Variance': tpl.var(1, usFmt),
        // 'Sample Standard Deviation': tpl.stdev(1, usFmt),
        // 'Minimum': tpl.min(usFmt),
        // 'Maximum': tpl.max(usFmt),
        // 'First': tpl.first(usFmt),
        // 'Last': tpl.last(usFmt),
        // 'Sum over Sum': tpl.sumOverSum(usFmt),
        // 'Sum as Fraction of Total': tpl.fractionOf(tpl.sum(), 'total', usFmtPct),
        // 'Sum as Fraction of Rows': tpl.fractionOf(tpl.sum(), 'row', usFmtPct),
        // 'Sum as Fraction of Columns': tpl.fractionOf(tpl.sum(), 'col', usFmtPct),
        // 'Count as Fraction of Total': tpl.fractionOf(tpl.count(), 'total', usFmtPct),
        // 'Count as Fraction of Rows': tpl.fractionOf(tpl.count(), 'row', usFmtPct),
        // 'Count as Fraction of Columns': tpl.fractionOf(tpl.count(), 'col', usFmtPct)
      })
      )(PivotUtilities.aggregatorTemplates)
    },
    renderers () {
      const TableRenderer = Renderer.TableRenderer
      // const PlotlyRenderer = Renderer.PlotlyRenderer
      return (() => ({
        'Table': TableRenderer.Table,
        'Table Heatmap': TableRenderer['Table Heatmap'],
        'Table Col Heatmap': TableRenderer['Table Col Heatmap'],
        'Table Row Heatmap': TableRenderer['Table Row Heatmap'],
        'Export Table TSV': TableRenderer['Export Table TSV']
        // 'Grouped Column Chart': PlotlyRenderer['Grouped Column Chart'],
        // 'Stacked Column Chart': PlotlyRenderer['Stacked Column Chart'],
        // 'Grouped Bar Chart': PlotlyRenderer['Grouped Bar Chart'],
        // 'Stacked Bar Chart': PlotlyRenderer['Stacked Bar Chart'],
        // 'Line Chart': PlotlyRenderer['Line Chart'],
        // 'Dot Chart': PlotlyRenderer['Dot Chart'],
        // 'Area Chart': PlotlyRenderer['Area Chart'],
        // 'Scatter Chart': PlotlyRenderer['Scatter Chart'],
        // 'Multiple Pie Chart': PlotlyRenderer['Multiple Pie Chart']
      })
      )()
    }
  },
  methods: {
    colorScaleGenerator (values) {
      const scale = scaleLinear()
        .domain([0, Math.max.apply(null, values)])
        .range(['#fff', '#77f'])
      return x => {
        return { 'background-color': scale(x) }
      }
    },
    getCurrentQuartly(date){
       var month = date.getMonth() + 1;
          return (Math.ceil(month / 3))+'º Trimestre';
    },
    getCurrentSemestral(date){
       var month = date.getMonth() + 1;
          return (Math.ceil(month / 6))+'º Semestre';
    },
    getAllProcesso(offset) {
         if(offset >= 0){
          ProcessoInvestigacao.api().get("/processoInvestigacao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllFormaProcesso(offset) {
      if(offset >= 0){
          FormaProcesso.api().get("/formaProcesso?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllFormaProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllJurisdicao(offset) {
      if(offset >= 0){
          ClasseJudicial.api().get("/classeJudicial?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllJurisdicao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllCrime(offset) {
      if(offset >= 0){
          Crime.api().get("/crime?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllCrime(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllMagistrado(offset) {
      if(offset >= 0){
          Magistrado.api().get("/magistrado?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllMagistrado(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllOrgao(offset) {
      if(offset >= 0){
          Orgao.api().get("/orgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllOrgao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
     getAllProvincia(offset) {
       if(offset >= 0){
          Provincia.api().get("/provincia?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProvincia(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllTipoOrgao(offset) {
      if(offset >= 0){
          TipoOrgao.api().get("/tipoOrgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoOrgao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
     getAllPais(offset) {
       if(offset >= 0){
          Pais.api().get("/pais?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllPais(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllTipoParecer(offset) {
      if(offset >= 0){
          TipoParecer.api().get("/tipoParecer?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoParecer(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllDespachos(offset) {
      if(offset >= 0){
          Despacho.api().get("/despacho?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllDespachos(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllPergunta(offset) {
       if(offset >= 0){
          Pergunta.api().get("/pergunta?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllPergunta(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
     getAllArguidoProcesso(offset) {
              if(offset >= 0){
          PecaProcessoArguido.api().get("/pecaProcessoArguido?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllArguidoProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllSituacaoPrisional(offset) {
          if(offset >= 0){
          SituacaoPrisional.api().get("/situacaoPrisional?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllSituacaoPrisional(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    }
  },
  watch: {
    config: {
      handler (value, oldValue) {
        const PivotData = PivotUtilities.PivotData
        this.data = this.allData
        this.filteredData = new PivotData(value).getFilteredData()
      },
      deep: true,
      immediate: false
    }
  }
}
</script>

<style>
.main {
  max-width: 980px;
  margin: 8vh auto 20px;
}
.title {
  text-align: center;
  margin-bottom: 20px;
}
h1 {
  margin-bottom: 0px;
}
.table-responsive {
  display: block;
  width: 100%;
  overflow-x: auto;
}
pre {
  text-align: left;
  background-color: #f8f8f8;
  padding: 1.2em 1.4em;
  line-height: 1.5em;
  margin: 60px 0 0;
  overflow: auto;
}
code {
  padding: 0;
  margin: 0;
}
footer {
  text-align: center;
  margin-top: 40px;
  line-height: 2;
}
</style>