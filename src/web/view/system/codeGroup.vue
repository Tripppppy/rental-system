<!-- ClientCodeGenerator created on 2017/11/13-->
<style lang="less">
    @import '../../styles/common.less';

</style>

<template>
    <div class="thng-crud-container">

        <div class="thng-crud-top">
            <Row>
                <Col span="24">
                <Button class="pull-left" type="primary" @click="add" icon="plus-round">添加编码组</Button>
                </Col>
            </Row>
        </div>
        <br>
        <div class="thng-crud-bottom">
            <Row>
                <Col span="24">
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

        <Modal v-model="deleteModal" width="360">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="information-circled"></Icon>
                <span>删除编码组</span>
            </p>
            <div style="text-align:center">
                <p>删除该编码组，将无法恢复！</p>
                <p>是否删除?</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" long :loading="isDeleting" @click="deletecodeGroup">删除</Button>
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
                loading: false,
                isSaving: false,
                isDeleting: false,
                deleteModal: false,
                deleteIndex: '',
                pageInfo: {},
                columns: [{type: 'index', title: '序号', width: 60, align: 'center'}, {
                    title: 'Code',
                    key: 'code'
                },
                    {title: '名称', key: 'name'},
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
                data: []
            }
        },
        methods: {
            getcodeGroupList() {
                this.loading = true;
                let self = this;
                let params = {
                    page: this.pageInfo.pageNum || 1,
                    size: this.pageInfo.pageSize || 10
                };
                this.$http.get('/codeGroup', {params: params}).then(function (res) {
                    self.loading = false;
                    self.data = res.data.list
                    self.pageInfo.total = res.data.total;
                });
            },
            reloadcodeGroupList() {
                this.pageInfo.page = 1;
                this.getcodeGroupList();
            },

            changePage(currentPage) {
                this.pageInfo.pageNum = currentPage;
                this.getcodeGroupList()
            },

            changePageSize(pageSize) {
                this.pageInfo.pageSize = pageSize;
                this.getcodeGroupList()
            },

            add() {
                this.$router.push({
                    name: 'code_group_add',
                });
            },

            edit(index) {
                this.$router.push({
                    params: {
                        codeGroupId: this.data[index].id
                    },
                    name: 'code_group_edit',
                });
            },

            handleReset() {
                this.editModal = false;
                console.log('handleReset');
            },
            remove(index) {
                this.deleteModal = true;
                this.deleteIndex = index;
                this.isDeleting = false;
            },
            deletecodeGroup() {
                this.isDeleting = true;
                var self = this;
                this.$http.delete('/codeGroup/' + self.data[self.deleteIndex].id, {}).then(function (res) {
                    self.isDeleting = false;
                    self.deleteModal = false;
                    self.reloadcodeGroupList();
                    self.$Message.success('删除成功');
                });

            }

        },
        created() {
            this.getcodeGroupList();
        }
    }
</script>