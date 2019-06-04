import Main from '@/components/main'
// *** insert import here *** // // don't change this line

export default {
	path: '/myOrder',
	name: '我的订单',
	component: Main,
  role: ['ROLE_COMMON_USER'],
  meta: {
    icon: 'ios-stats',
  },
	children: [
    {
      path: 'manager',
      name: ' myOrder-manager',
      meta: {
        icon: 'ios-stats',
        title: '我的订单',
        hideInMenu: false
      },
      component: () => import(/* webpackChunkName: 'system' */ '@/view/order/myOrder.vue')
    }
		// *** insert router here *** // // don't change this line
	]
}
