import {
  login,
  logout,
  getUserInfo,
  getContentByMsgId,
  hasRead,
  removeReaded,
  restoreTrash,
} from '@/api/user'
import {setToken, getToken, getUserIdentity, setUserIdentity} from '@/libs/util'

export default {
  state: {
    token: getToken(),
    access: '',
    userIdentity: getUserIdentity(),
  },
  mutations: {
    setUserIdentity(state, userIdentity) {
      state.userIdentity = userIdentity
      setUserIdentity(userIdentity)
    },
    setAccess(state, access) {
      state.access = access
    },
    setToken(state, token) {
      state.token = token
      setToken(token)
    },
  },
  getters: {
  },
  actions: {
    // 登录
    handleLogin({commit}, {userName, password}) {
      userName = userName.trim()
      return new Promise((resolve, reject) => {
        login({
          userName,
          password
        }).then(res => {
          if (res.code === 200) {
            const data = res.data
            commit('setToken', data.token)
            resolve(res)
          } else {
            reject(res)
          }
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 退出登录
    handleLogOut({state, commit}) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('setToken', '')
          commit('setAccess', [])
          resolve()
        }).catch(err => {
          reject(err)
        })
        // 如果你的退出登录无需请求接口，则可以直接使用下面三行代码而无需使用logout调用接口
        // commit('setToken', '')
        // commit('setAccess', [])
        // resolve()
      })
    },
  }
}
