import Localbase from 'localbase'
let db = new Localbase('gestao_processual_crime')


export default ({ app }) => {
    // Set i18n instance on app
    app.db = db
  }
  
export { db }

