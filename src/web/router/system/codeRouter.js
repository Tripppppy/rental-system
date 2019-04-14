export default {
	path: 'code',
	name: 'system-code',
	meta: {
		icon: 'ios-stats',
		title: 'code列表',
    hideInMenu:false
	},
	component: () => import(/* webpackChunkName: 'system' */ '@/view/system/code.vue')
}
