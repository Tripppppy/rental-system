import Main from '@/components/main'
// *** insert import here *** // // don't change this line

export default {
	path: '/order',
	name: '订单',
	component: Main,
  role: ['ROLE_ADMIN'],
  meta: {
    icon: 'ios-stats',
  },
	children: [
    {
      path: 'manager',
      name: 'order-manager',
      meta: {
        icon: 'ios-stats',
        title: '订单管理',
        hideInMenu: false
      },
      component: () => import(/* webpackChunkName: 'system' */ '@/view/order/order.vue')
    }
		// *** insert router here *** // // don't change this line
	]
}
