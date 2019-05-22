import Main from '@/components/main'
// *** insert import here *** // // don't change this line

export default {
	path: '/ball',
	name: '球类',
  role: ['ROLE_ADMIN'],
  meta: {
    icon: 'ios-stats',
	},
	component: Main,
	children: [
    {
      path: 'manager',
      name: 'ball-manager',
      meta: {
        icon: 'ios-stats',
        title: '球类管理',
        hideInMenu: false
      },
      component: () => import(/* webpackChunkName: 'system' */ '@/view/ball/ball.vue')
    },
		// *** insert router here *** // // don't change this line
	]
}
