<style lang="less">
    @import '../../styles/common.less';

</style>
<template>
    <div>
        <div style="margin-top: 10px">
            <div>
                <Row>
                    <Col span="24">
                    <div style="margin: 10px;margin-top: 15px">
                        <span style="color:#464c5b;font-size: 16px;font-weight:bold">编码组基本信息</span>
                    </div>
                    <div style="height: 1px;background-color: #e9eaec;margin-bottom: 10px"></div>
                    <Form ref='codeGroupForm' :model='codeGroupForm' :rules='codeGroupFormRule' :label-width='80'
                          style="width: 450px">
                        <FormItem label='Code' prop='code'>
                            <Input v-model='codeGroupForm.code' :maxlength=30
                                   placeholder='请输入编码'/>
                        </FormItem>
                        <FormItem label='名称' prop='name'>
                            <Input v-model='codeGroupForm.name' :maxlength=50
                                   placeholder='请输入名称'/>
                        </FormItem>
                        <FormItem label='描述' prop='description'>
                            <Input v-model='codeGroupForm.description' type='textarea'
                                   :maxlength=500 :autosize='{minRows: 2,maxRows: 5}'
                                   placeholder='请输入...'/></FormItem>
                        <FormItem>
                            <Button type="success" :loading="isSaving" @click="saveCodeGroup">保存</Button>&nbsp;&nbsp;
                            <Button @click="handleCodeGrupFormCancel">取消</Button>
                        </FormItem>
                    </Form>
                    <Spin fix v-if="codeGroupFormLoading">
                        <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
                        <div>加载中...</div>
                    </Spin>
                    </Col>
                </Row>
            </div>
            <div style="height: 1px;background-color: #e9eaec"></div>
            <br>
            <div>
                <Row>
                    <Col span="24">
                    <div style="margin: 10px;margin-top: 5px">
                        <span style="color:#464c5b;font-size: 16px;font-weight:bold">编码组列表</span>
                        <div style="display: inline-block;text-align:right;width: 91%">
                            <Button type="primary" icon="plus-round" :disabled="addCodeButtonDisabled"
                                    @click="addCode">新建编码
                            </Button>
                        </div>
                    </div>
                    <div style="height: 1px;background-color: #e9eaec;margin-bottom: 10px"></div>
                    <Table :loading="loading" :columns="columns" :data="data"></Table>
                    <div style="margin: 10px;overflow: hidden">
                        <div style="float: right;">
                            <Page v-model="pageInfo"
                                  :current="pageInfo.pageNum"
                                  :total="pageInfo.total"
                                  :page-size="pageInfo.pageSize"
                                  size="small"
                                  :page-size-opts="[5,10,15]"
                                  @on-change="changePage"
                                  @on-page-size-change="changePageSize"
                                  show-elevator
                                  show-sizer
                                  show-total></Page>
                        </div>
                    </div>
                    </Col>
                </Row>
            </div>
        </div>

        <Modal
                v-model="editModal"
                width="330"
                :styles="{top: '20px'}"
                :title="editModalTitle"
                :mask-closable="false">

            <Form ref='codeForm' :model='codeForm' :rules='codeFormRule' :label-width="80" style="width: 450px">
                <FormItem label='编码' prop='code'>
                    <Input v-model='codeForm.code' :maxlength=30 placeholder='请输入编码' style="width: 170px"/>
                </FormItem>
                <FormItem label='名称' prop='name'>
                    <Input v-model='codeForm.name' :maxlength=50 placeholder='请输入名称' style="width: 170px"/>
                </FormItem>
                <FormItem label='序号' prop='seqNum'>
                    <InputNumber v-model='codeForm.seqNum' :max='99999999999' :min="1"/>
                </FormItem>
                <FormItem label='描述' prop='description'>
                    <Input v-model='codeForm.description'
                           type='textarea'
                           :maxlength=500 :autosize='{minRows: 2,maxRows: 5}'
                           placeholder='请输入...' style="width: 170px"/>
                </FormItem>
            </Form>
            <Spin fix v-if="editLoading">
                <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
                <div>加载中...</div>
            </Spin>
            <div slot="footer">
                <Button @click="cancel()" style="margin-left: 8px">取消</Button>
                <Button type="success" :loading="isSaving" @click="saveCode()">保存</Button>
            </div>

        </Modal>

        <!--删除表格数据的弹框-->
        <Modal v-model="deleteModal" width="360">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="information-circled"></Icon>
                <span>删除编码</span>
            </p>
            <div style="text-align:center">
                <p>删除该编码，将无法恢复！</p>
                <p>是否删除?</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" long :loading="isDeleting" @click="deleteTableInfo">删除</Button>
            </div>
        </Modal>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                codeGroupForm: {code: undefined, name: undefined, description: undefined,},
                codeGroupFormRule: {
                    code: [{required: true, message: '编码不能为空.', trigger: 'blur'}, {
                        type: 'string',
                        max: 30,
                        message: '编码最多30字符',
                        trigger: 'blur'
                    },],
                    name: [{required: true, message: '名称不能为空.', trigger: 'blur'}, {
                        type: 'string',
                        max: 50,
                        message: '名称最多50字符',
                        trigger: 'blur'
                    },],
                    description: [{type: 'string', max: 500, message: '描述最多500字符', trigger: 'blur'},],
                },
                codeForm: {
                    code: undefined,
                    name: undefined,
                    seqNum: undefined,
                    description: undefined,
                },
                codeFormRule: {
                    code: [{required: true, message: '编码不能为空.', trigger: 'blur'}, {
                        type: 'string',
                        max: 30,
                        message: '编码最多30字符',
                        trigger: 'blur'
                    },],
                    name: [{required: true, message: '名称不能为空.', trigger: 'blur'}, {
                        type: 'string',
                        max: 50,
                        message: '名称最多50字符',
                        trigger: 'blur'
                    },],
                    seqNum: [{
                        type: 'number',
                        required: true,
                        message: '序号不能为空.',
                        trigger: 'blur'
                    },],
                    description: [{
                        type: 'string',
                        max: 500, message: '描述最多500字符',
                        trigger: 'blur'
                    },],
                },
                loading: false,
                isSaving: false,
                isDeleting: false,
                selectLoading: false,
                editLoading: false,
                pageInfo: {},
                editModal: false,
                addCodeButtonDisabled: true,
                deleteModal: false,
                deleteIndex: '',
                columns: [
                    {type: 'index', title: '序号', width: 60, align: 'center'},
                    {
                        title: '编码',
                        key: 'code'
                    },
                    {
                        title: '名称',
                        key: 'name'
                    },
                    {
                        title: '序号',
                        key: 'seqNum',
                        sortType: 'asc'
                    },
                    {
                        title: '描述',
                        key: 'description',
                        render: (h, params) => {
                          let result = params.row.description ? params.row.description : '无';
                            return h('span', result)
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        key: 'handle',
                      render: (h, params) => {
                        return h('div', [
                          h('Button', {
                            props: {
                              type: 'success',
                              size: 'small'
                            },
                            class: 'ivu-btn-edit',
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                this.edit(params.index)
                              }
                            }
                          }, '编辑'),
                          h('Button', {
                            props: {
                              type: 'error',
                              size: 'small'
                            },
                            class: 'ivu-btn-delete',
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                this.remove(params.index)
                              }
                            }
                          }, '删除'),
                        ]);
                      }
                    }],
                data: [],
                codeGroupFormLoading: false,
                editModalTitle: ''
            }
        },
        methods: {
            init() {
                this.codeGroupFormLoading = true;
                let self = this;
                this.$http.get('/codeGroup/' + this.codeGroupForm.id, {}).then(function (res) {
                    self.codeGroupForm = res.data;
                    self.addCodeButtonDisabled = false;
                    self.codeGroupFormLoading = false;
                    self.getcodeList();
                });
            },

            getcodeList() {
                this.loading = true;
                let self = this;
                let params = {
                    page: this.pageInfo.pageNum || 1,
                    size: this.pageInfo.pageSize || 10,
                    codeGroupCode: this.codeGroupForm.code
                };
                this.$http.get('/code/list', params).then(function (res) {
                    self.loading = false;
                    let result = res && res.data;
                    self.data = result && result.list;
                    self.pageInfo.total = result && result.total;
                });
            },

            changePage(currentPage) {
                this.pageInfo.pageNum = currentPage;
                this.getcodeList()
            },

            changePageSize(pageSize) {
                this.pageInfo.pageSize = pageSize;
                this.getcodeList()
            },

            addCode() {
                this.isSaving = false;
                this.codeForm = {code: undefined, name: undefined, seqNum: null, description: undefined};
                this.$refs.codeForm.resetFields();
                this.editModal = true;
                this.editModalTitle = '添加编码';
            },

            edit(index) {
                this.editLoading = true;
                this.isSaving = false;
                let self = this;
                this.editModalTitle = '编辑编码';
                this.$refs.codeForm.resetFields();
                this.editModal = true;
                this.$http.get('/code/' + self.data[index].id, {}).then(function (res) {
                    self.codeForm = res.data;
                    self.editLoading = false;
                });
            },

            saveCodeGroup() {
                this.isSaving = true;
                let self = this;
                this.$refs.codeGroupForm.validate((valid) => {
                    if (valid) {
                        if (this.codeGroupForm.id) {
                            this.$http.put('/codeGroup', self.codeGroupForm).then(function (res) {
                                self.isSaving = false;
                                self.editModal = false;
                                if (res.code === 200) {
                                    self.$Message.success('修改成功！');
                                } else {
                                    self.$Message.error('修改失败！服务器内部错误');
                                }
                            });
                        } else {
                            this.$http.post('/codeGroup', self.codeGroupForm).then(function (res) {
                                self.isSaving = false;
                                self.editModal = false;
                                if (res.code === 200) {
                                    self.addCodeButtonDisabled = false;
                                    self.$Message.success('添加成功！');
                                    self.$http.get('/codeGroup/' + res.data.id, {}).then(function (res) {
                                        self.codeGroupForm = res.data;
                                    });
                                } else {
                                    self.$Message.error('添加失败！服务器内部错误');
                                }
                            });
                        }
                    } else {
                        self.isSaving = false;
                    }
                })
            },
            handleCodeGrupFormCancel() {
                this.$router.push({
                    name: 'code_group',
                });
            },
            saveCode() {
                this.isSaving = true;
                let self = this;
                this.$refs.codeForm.validate((valid) => {
                    if (valid) {
                        if (this.codeForm.id) {
                            this.$http.put('/code', self.codeForm).then(function (res) {
                                self.isSaving = false;
                                self.editModal = false;
                                self.getcodeList();
                            });
                        } else {
                            self.codeForm.codeGroupCode = self.codeGroupForm.code;
                            this.$http.post('/code', self.codeForm).then(function (res) {
                                self.isSaving = false;
                                self.editModal = false;
                                self.getcodeList();
                            });
                        }

                    } else {
                        self.isSaving = false;
                        this.$Message.error('表单验证失败！');
                    }
                })
            },
            cancel() {
                this.editModal = false;
            },
            remove(index) {
                this.deleteModal = true;
                this.deleteIndex = index;
                this.isDeleting = false;
            },
            deleteTableInfo() {
                this.isDeleting = true;
                var self = this;
                this.$http.delete('/code/' + self.data[self.deleteIndex].id, {}).then(function (res) {
                    self.isDeleting = false;
                    self.deleteModal = false;
                    self.getcodeList();
                    self.$Message.success('删除成功');
                });
            }
        },
        created() {
            this.codeGroupForm.id = this.$route.params.codeGroupId
            if (typeof(this.codeGroupForm.id) != 'undefined') {
                this.init();
            } else {
                this.$refs.codeGroupForm.resetFields();
                this.data = [];
                this.pageInfo = {};
                this.addCodeButtonDisabled = true;
            }
        }
    }
</script>