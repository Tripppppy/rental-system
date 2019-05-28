<template>
    <div>
        <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
            <FormItem prop="loginName">
                <Input v-model="form.loginName" placeholder="请输入用户名">
        <span slot="prepend">
          <Icon :size="16" type="ios-person"></Icon>
        </span>
                </Input>
            </FormItem>
            <FormItem prop="password">
                <Input type="password" v-model="form.password" placeholder="请输入密码">
        <span slot="prepend">
          <Icon :size="14" type="md-lock"></Icon>
        </span>
                </Input>
            </FormItem>
            <div style="margin-top: -8px;margin-bottom: 6px;text-align: center;font-size: 10px">
                <span>还没有账号？<a @click="startRegister">点击注册</a></span>
                <p>或</p>
                <p>直接 <a @click="handleVisitorLogin">游客登录</a></p>
            </div>
            <FormItem>
                <Button @click="handleSubmit" type="primary" long>登录</Button>
            </FormItem>
        </Form>
        <Modal
                v-model="registerModal"
                width="330"
                :styles="{top: '20px'}"
                title="用户注册">
            <Form ref='userForm' :model='userForm' :rules='userFormRule' :label-width='90' style="width: 450px">
                <FormItem label='登录名' prop='loginName'>
                    <Input v-model='userForm.loginName' :maxlength=30 placeholder='请输入登录名' style="width: 170px"/>
                </FormItem>
                <FormItem label='昵称' prop='name'>
                    <Input v-model='userForm.name' :maxlength=50 placeholder='请输入昵称' style="width: 170px"/>
                </FormItem>
                <FormItem label='密码' prop='password'>
                    <Input type="password" v-model='userForm.password' :maxlength=50 placeholder='请输入密码,至少6个字符'
                           style="width: 170px"/>
                </FormItem>
                <FormItem label='确认密码' prop='verifyPassword'>
                    <Input type="password" v-model='userForm.verifyPassword' :maxlength=50 placeholder='请输入相同密码,至少6个字符'
                           style="width: 170px"/>
                </FormItem>
                <FormItem label='联系方式' prop='mobile'>
                    <Input v-model='userForm.mobile' :maxlength=50 placeholder='请输入联系方式' style="width: 170px"/>
                </FormItem>
                <FormItem label='电子邮箱' prop='email'>
                    <Input v-model='userForm.email' :maxlength=50 placeholder='请输入电子邮箱' style="width: 170px"/>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button @click="registerModal = false" style="margin-left: 8px">取消</Button>
                <Button type="success" @click="handleRegister()" :loading="registerLoading">{{registerButtonText}}
                </Button>
            </div>
        </Modal>
    </div>
</template>
<script>
  export default {
    name: 'LoginForm',
    props: {
      loginNameRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '账号不能为空', trigger: 'blur'}
          ]
        }
      },
      passwordRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '密码不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    data() {
      return {
        form: {
          loginName: ' ',
          password: ''
        },
        registerModal: false,
        registerButtonText: '注册',
        userForm: {
          loginName: undefined,
          name: undefined,
          password: undefined,
          verifyPassword: undefined,
          mobile: undefined,
          email: undefined,
        },
        registerLoading: false,
        userFormRule: {
          loginName: [
            {required: true, message: '登录名不能为空.', trigger: 'blur'},
            {type: 'string', max: 30, message: '登录名最多30字符', trigger: 'blur'},
          ],
          name:
            [
              {required: true, message: '名称不能为空.', trigger: 'blur'},
              {type: 'string', max: 50, message: '名称最多50字符', trigger: 'blur'},
            ],
          password:
            [
              {required: true, message: '密码不能为空.', trigger: 'blur'},
              {min: 6, message: '请至少输入6个字符', trigger: 'blur'},
              {max: 32, message: '最多输入32个字符', trigger: 'blur'}
            ],
          mobile: [
            {type: 'string', max: 30, message: '联系方式必须为数字', trigger: 'blur'}
          ],
          email: [
            {type: 'email', message: '电子邮箱的格式不正确', trigger: 'blur'}
          ],
          verifyPassword: [
            {required: true, message: '确认密码不能为空', trigger: 'blur'},
          ]
        },
      }
    },
    computed: {
      rules() {
        return {
          loginName: this.loginNameRules,
          password: this.passwordRules
        }
      }
    },
    methods: {
      handleSubmit() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.$emit('on-success-valid', {
              loginName: this.form.loginName,
              password: this.form.password
            })
          }
        })
      },
      handleVisitorLogin() {
        this.$emit('on-success-valid', {
          loginName: '游客',
          password: '游客'
        })
      },
      handleRegister() {
        this.registerLoading = false;
        let self = this;
        this.$refs.userForm.validate((valid) => {
          if (valid) {
            if (this.userForm.password === this.userForm.verifyPassword) {
              this.registerButtonText = '正在注册';
              this.$http.post('/user', self.userForm).then(function (res) {
                if (res.code === 200) {
                  self.registerButtonText = '注册成功';
                  self.$Message.success('注册成功！请快点登录吧~~');
                  self.form.loginName = self.userForm.loginName;
                  self.form.password = self.userForm.password;
                  self.registerModal = false;
                } else if (res.code === 502) {
                  self.$Message.error(res.message);
                } else {
                  self.$Message.error('注册失败，请重新注册');
                }
                self.registerLoading = false;
                self.registerButtonText = '注册';
              });
            } else {
              this.$Message.error("两次输入的密码不同，请重新输入")
            }
          }
        });
      },
      startRegister() {
        this.userForm = {
          loginName: undefined,
          name: undefined,
          password: undefined,
          verifyPassword: undefined,
          mobile: undefined,
          email: undefined,
        },
          this.registerModal = true;
      }
    }
  }
</script>
