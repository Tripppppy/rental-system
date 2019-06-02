import Main from '@/components/main'
import roleRouter from './roleRouter'
import userRouter from './userRouter'
import ownSpace from './ownSpace'
// *** insert import here *** // // don't change this line

export default {
	path: '/system',
	name: '系统管理',
  role: ['ROLE_ADMIN'],
  meta: {
    icon: 'ios-stats',
  },
	component: Main,
	children: [
		roleRouter,
		userRouter,
    ownSpace,
    {
      path: 'codeGroup',
			name: 'code_group',
      meta: {
        icon: 'ios-stats',
        title: '编码组',
        hideInMenu: false
      },
      component: () => import(/* webpackChunkName: 'system' */ '@/view/system/codeGroup.vue')
    },
    {
      path: 'codeGroup/add',
			name: 'code_group_add',
      meta: {
        title: '添加编码组',
        hideInMenu: true
      },
      notMenu: true,
      component: () => import(/* webpackChunkName: "config" */ '@/view/system/edit-codeGroup.vue')
    },
    {
      path: 'codeGroup/edit/:codeGroupId',
      meta: {
        title: '编辑编码组',
        hideInMenu: true
      },
			name: 'code_group_edit',
      notMenu: true,
      component: () => import(/* webpackChunkName: "config" */ '@/view/system/edit-codeGroup.vue')
    },
		// *** insert router here *** // // don't change this line
	]
}
