// Crime Pages
import CrimeList from 'pages/crime/index'
import CrimeDetail from 'pages/crime/details'

const CrimeListRoute =
  {
    path: '/crime',
    name: 'crime',
    component: CrimeList
  }

const CrimeShowRoute = {
  path: '/:id',
  name: 'crimeDetails',
  component: CrimeDetail,
  props: true
}

export default [
  CrimeListRoute, CrimeShowRoute
]
