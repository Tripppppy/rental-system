import Main from '@/components/main'
import rentalRouter from "./rentalRouter"
// *** insert import here *** // // don't change this line

export default {
	path: '/booking',
	name: '下单',
	component: Main,
  role: ['ROLE_COMMON_USER', 'ROLE_VISITOR'],
  meta: {
    icon: 'ios-stats',
  },
	children: [
    rentalRouter
	]
}
