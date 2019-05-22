export default {
	path: 'rental',
	name: 'booking-rental',
	meta: {
		icon: 'ios-stats',
		title: '租球',
    hideInMenu:false
	},
	component: () => import(/* webpackChunkName: 'system' */ '@/view/booking/orderBooking.vue')
}
