export default {
	path: 'role',
	name: 'system-role',
	meta: {
		icon: 'ios-stats',
		title: '角色列表',
    hideInMenu:false
	},
	component: () => import(/* webpackChunkName: 'system' */ '@/view/system/role.vue')
}
