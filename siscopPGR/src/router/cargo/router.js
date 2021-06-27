import CargoList from 'pages/cargo/index'
import CargoDetail from 'pages/cargo/details'

const CargoListRoute =
  {
    path: '/cargo',
    name: 'cargo',
    component: CargoList
  }

const CargoShowRoute = {
  path: '/cargo/:id',
  component: CargoDetail,
  props: true
}

export default [
  CargoListRoute, CargoShowRoute
]
