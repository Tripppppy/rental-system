<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit"></login-form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapMutations, mapActions } from 'vuex'
export default {
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([

    ]),
    ...mapMutations([
      'setToken',
      'setUserIdentity'
    ]),
    handleSubmit (loginFormData) {
      loginFormData.loginName = loginFormData.loginName.trim()
      if (loginFormData.loginName === '游客') {
        this.setToken(JSON.stringify(loginFormData));
        loginFormData.imageUrl = 'http://img.kimen.xyz/psb.png';
        loginFormData.id = 0;
        loginFormData.roles = [{id: 30, code: 'ROLE_VISITOR'}]
        this.setUserIdentity(loginFormData);
        this.$router.push({
          name: this.$config.homeName
        })
      } else {
        this.$http.post('/user/login', loginFormData).then((res) => {
          if (res.code === 200) {
            this.setToken(JSON.stringify(loginFormData));
            this.setUserIdentity(res.data);
            this.$router.push({
              name: this.$config.homeName
            })
          } else {
            this.$Message.error(res.message);
          }
        })
      }
    },
  }
}
</script>

<style>

</style>
