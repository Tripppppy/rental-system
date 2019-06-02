<style lang="less">
    @import 'own-space.less';

    .fileInput {
        background: #D0EEFF;
        border-radius: 4px;
        padding: 4px 12px;
        overflow: hidden;
        color: #1E88C7;
        text-decoration: none;
        text-indent: 0;
        line-height: 23px;
    }

    .fileInput input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
    }

    .cropperAgain{
        height:300px;
        width: 500px ;
    }
</style>

<template>
    <div style="position: absolute;width: 600px;height: 100%;background-color: #FFF">
        <div class="header">
            <span class="text">个人中心</span>
        </div>
        <div>
            <Form
                    ref="userForm"
                    :model="userForm"
                    :label-width="100"
                    label-position="right"
                    :rules="inforValidate"
                    style="margin-left: 97px;margin-top: 20px">
                <FormItem>
                    <img class="own-img" :src="userInfo.imageUrl"/>
                </FormItem>
                <FormItem label="登录名：" prop="loginName">
                    <div style="display:inline-block;width:300px;">
                        <Input v-model="userForm.loginName"></Input>
                    </div>
                </FormItem>
                <FormItem label="用户姓名：" prop="name">
                    <div style="display:inline-block;width:300px;">
                        <Input v-model="userForm.name"></Input>
                    </div>
                </FormItem>
                <FormItem label="联系方式：">
                    <div style="display:inline-block;width:300px;">
                        <Input v-model="userForm.mobile"></Input>
                    </div>
                </FormItem>
                <FormItem label="电子邮箱：">
                    <div style="display:inline-block;width:300px;">
                        <Input v-model="userForm.email"></Input>
                    </div>
                </FormItem>
                <FormItem label="登录密码：">
                    <Button type="text" size="small" @click="showEditPassword">修改密码</Button>
                </FormItem>
                <div>
                    <Button type="text" style="width: 100px;"></Button>
                    <Button type="primary" style="width: 100px;" :loading="save_loading" @click="saveEdit">保存
                    </Button>
                </div>
            </Form>
        </div>
        <Spin fix v-if="ownSpaceLoading">
            <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
            <div>正在加载数据...</div>
        </Spin>

        <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="350">
            <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right"
                  :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input type="password" v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码"
                           style="width: 170px"></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input type="password" v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符"
                           style="width: 170px"></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input type="password" v-model="editPasswordForm.rePass" placeholder="请再次输入新密码"
                           style="width: 170px"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">修改</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import {cropperPicture} from '../../libs/util';
    import VueCropper from 'vue-cropper';
    import { mapActions } from 'vuex'
    export default {
        components: {VueCropper},
        name: 'ownspace_index',
        data () {
            const valideRePassword = (rule, value, callback) => {
                if (value !== this.editPasswordForm.newPass) {
                    callback(new Error('两次输入密码不一致'));
                } else {
                    callback();
                }
            };
            return {
                userForm: {
                    id: '',
                    loginName: '',
                    name: '',
                    imageUrl: '',
                    mobile: '',
                    email: ''
                },
                uid: '', // 登录用户的userId
                securityCode: '', // 验证码
                save_loading: false,
                identifyError: '', // 验证码错误
                editPasswordModal: false, // 修改密码模态框显示
                savePassLoading: false,
                oldPassError: '',
                identifyCodeRight: false, // 验证码是否正确
                hasGetIdentifyCode: false, // 是否点了获取验证码
                canGetIdentifyCode: false, // 是否可点获取验证码
                checkIdentifyCodeLoading: false,
                inforValidate: {
                    loginName: [
                        {required: true, message: '请输入登录名', trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ]
                },
                editPasswordForm: {
                    oldPass: '',
                    newPass: '',
                    rePass: ''
                },
                passwordValidate: {
                    oldPass: [
                        {required: true, message: '请输入原密码', trigger: 'blur'}
                    ],
                    newPass: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {min: 6, message: '请至少输入6个字符', trigger: 'blur'},
                        {max: 32, message: '最多输入32个字符', trigger: 'blur'}
                    ],
                    rePass: [
                        {required: true, message: '请再次输入新密码', trigger: 'blur'},
                        {validator: valideRePassword, trigger: 'blur'}
                    ]
                },
                inputCodeVisible: false, // 显示填写验证码box
                initPhone: '',
                gettingIdentifyCodeBtnContent: '获取验证码', // “获取验证码”按钮的文字
                ownSpaceLoading: false,
                pictureShow: false,
                pictureModalLoading: false,
                pictureButtonLoading: false,
                cut: {
                    size: 1,
                    Img: '',
                    outputType: 'jpeg || png || web',
                    autoCrop: true,
                    autoCropWidth: 120,
                    autoCropHeight: 120
                },
            };
        },
        computed: {
            userInfo() {
                return this.$store.state.user.userIdentity;
            }
        },
      watch: {
          'userInfo.imageUrl'(val) {
            this.userForm.imageUrl = val;
          }
      },
        methods: {
          ...mapActions([
            'handleLogOut'
          ]),
            showEditPassword() {
                this.editPasswordModal = true;
            },
          logout () {
            this.handleLogOut().then(() => {
              this.$router.push({
                name: 'login'
              })
            })
          },
            saveEdit() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        this.ownSpaceLoading = true;
                      this.userForm.roleIds = [];
                      this.userInfo.roles.map(item => {
                        this.userForm.roleIds.push(item.id);
                      });
                        this.$http.put('/user/updateAdmin', this.userForm).then((res) => {
                            if (res.code === 200) {
                                this.$Message.success('保存成功！');
                            } else {
                                this.$Message.error('保存个人信息失败，错误代码：' + res.code);
                            }
                          this.ownSpaceLoading = false;
                        });
                    }
                });
            },
            cancelEditPass() {
                this.editPasswordModal = false;
            },
            saveEditPass() {
                this.$refs['editPasswordForm'].validate((valid) => {
                    if (valid) {
                        this.savePassLoading = true;
                        let param = {
                            username: this.userInfo.loginName,
                            password: this.editPasswordForm.oldPass
                        };
                        this.$http.post('/user/login', param).then((res) => {
                            if (res.code === 200) {
                                this.userInfo.password = this.editPasswordForm.newPass;
                                this.$http.put('/user/password', this.userInfo).then((resp) => {
                                    if (resp.code === 200) {
                                        this.cancelEditPass();
                                        this.$Message.success('更新密码成功，请重新登录');
                                        // 执行登出操作
                                        this.logout();
                                    } else {
                                        this.$Message.error('修改密码失败，错误代码：' + res.code);
                                    }
                                });
                            } else {
                                this.$Message.error('原密码错误，请重试！');
                            }
                            this.savePassLoading = false;
                            this.$refs.editPasswordForm.resetFields();
                        });
                    }
                });
            },
            init () {
                this.userForm = this.userInfo;
            },
            handleChange (e) {
                this.pictureModalLoading = true;
                this.pictureShow = true;
                let reader = cropperPicture(e);
                reader.onload = (event) => {
                    let data = event.target.result;
                    reader.onload = null;
                    this.cut.Img = data;
                    this.pictureModalLoading = false;
                };
            },
        },
        mounted () {
            this.init();
        }
    };
</script>
