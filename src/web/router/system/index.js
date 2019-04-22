import Main from '@/components/main'
import roleRouter from './roleRouter'
import codeRouter from './codeRouter'
import codegroupRouter from './codegroupRouter'
import userRouter from './userRouter'
// *** insert import here *** // // don't change this line

export default {
	path: '/system',
	name: '系统管理',
  meta: {
    icon: 'ios-stats',
  },
	component: Main,
	children: [
		roleRouter,
		codeRouter,
		codegroupRouter,
		userRouter,
		// *** insert router here *** // // don't change this line
	]
}
