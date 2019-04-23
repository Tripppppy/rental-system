<!-- ClientCodeGenerator created on 2019/04/22-->
<style lang="less">
    @import '../../styles/common.less';

</style>

<template>
    <div class="smart-crud-container">

        <div class="smart-crud-top">
            <Row>
                <Col span="24">
                </Col>
            </Row>
        </div>
        <br>
        <div class="smart-crud-bottom">
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

        <Modal
                v-model="editModal"
                width="400"
                :loading="isSaving"
                @on-ok="handleSubmit"
                @on-cancel="handleReset"
                ok-text="保存"
                cancel-text="取消"
                title="编辑order">
            <Form ref='orderForm' :model='orderForm' :rules='orderFormRule' :label-width='120'>
                <FormItem label='还球日期' prop='orderReturnDate' style="width: 270px">
                    <Input v-model='orderForm.orderReturnDate' placeholder='请输入还球日期'/></FormItem>
                <FormItem label='实际花费' prop='cost' style="width: 270px">
                    <Input v-model='orderForm.cost' number placeholder='请输入cost'/>
                </FormItem>
                <FormItem label='订单状态' prop='status' style="width: 270px">
                    <CodeSelect :codeType="orderStatus" @emitedCodes="getOrderStatusList">
                        <Select v-model="orderForm.status">
                            <Option v-for="item in orderStatusList" :value="item.code" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </CodeSelect>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button @click="handleReset()" style="margin-left: 8px">取消</Button>
                <Button type="primary" :loading="isSaving" @click="handleSubmit()">保存</Button>
            </div>
        </Modal>

        <Modal v-model="deleteModal" width="360">
            <p slot="header" style="color:#f60;text-align:center">
                <Icon type="information-circled"></Icon>
                <span>删除order</span>
            </p>
            <div style="text-align:center">
                <p>删除该order，将无法恢复！</p>
                <p>是否删除?</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" long :loading="isDeleting" @click="deleteItem">删除</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
  import constants from '../shared/constants'
  import CodeSelect from '../shared/code/CodeSelect'
  export default {
    name: 'order',
    components: {
      CodeSelect
    },
    data() {
      return {
        searchModel: undefined,
        orderForm: {
          orderReturnDate: undefined,
          cost: undefined,
          status: undefined,
        },
        orderFormRule: {
          orderReturnDate: [{required: true, message: '还球日期不能为空.', trigger: 'blur'},],
          cost: [{required: true, type: 'number',message: '实际花费不能为空且必须为数字.', trigger: 'blur'},],
          status: [{required: true, message: '状态不能为空.', trigger: 'blur'},],
        },
        loading: false,
        keepalive: false,
        isSaving: false,
        isDeleting: false,
        pageInfo: {},
        editModal: false,
        deleteModal: false,
        deleteIndex: '',
        columns: [
          {
            type: 'index',
            title: '序号',
            width: 60,
            align: 'center'
          },
          {
            title: '球类',
            key: 'ball_id',
            render: (h, params) => {
              return h('span', params.row.ball.brandName + ' ' + params.row.ball.typeName)
            }
          },
          {
            title: '租球人',
            key: 'user_id',
            render: (h, params) => {
              return h('span', params.row.user.name)
            }
          },
          {
            title: '租球日期',
            key: 'orderRentDate'
          },
          {
            title: '还球日期',
            key: 'orderReturnDate'
          },
          {
            title: '实际花费',
            key: 'cost'
          },
          {
            title: '订单状态',
            key: 'status',
            render: (h, params) => {
              let result = '';
              this.orderStatusList.map(item => {
                if (item.code === params.row.status) {
                  result = item.name;
                }
              });
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
        orderStatus: constants.codeType.order_status,
        orderStatusList: []
      }
    },
    methods: {
      getList() {
        this.loading = true;
        const self = this;
        const params = {
          page: this.pageInfo.pageNum || 1,
          size: this.pageInfo.pageSize || 10
        };
        this.$http.get('/order', params).then((res) => {
          self.loading = false;
          if (res.code === 200) {
            const result = res.data;
            self.data = result && result.list;
            self.pageInfo.total = result && result.total;
          } else {
            self.$Message.error('获取数据失败！' + res.code);
          }

        })
      },

      reloadList() {
        this.pageInfo.pageNum = 1;
        this.getList();
      },

      changePage(currentPage) {
        this.pageInfo.pageNum = currentPage;
        this.getList();
      },

      changePageSize(pageSize) {
        this.pageInfo.pageSize = pageSize;
        this.getList();
      },

      add() {
        this.isSaving = false;
        this.orderForm = {
          ball_id: undefined,
          user_id: undefined,
          order_rent_date: undefined,
          order_return_date: undefined,
          cost: undefined,
          status: undefined,
        };
        this.editModal = true;
      },

      edit(index) {
        this.isSaving = false;
        const self = this;
        this.$refs.orderForm.resetFields();
        this.editModal = true;
        this.$http.get('/order/' + self.data[index].id, {}).then((res) => {
          if (res.code === 200) {
            self.orderForm = res.data;
          } else {
            self.$Message.error('获取order失败！' + res.code);
          }

        });
      },

      handleSubmit() {
        this.isSaving = true;
        let self = this;
        this.$refs.orderForm.validate((valid) => {
          if (valid) {
            if (this.orderForm.id) {
              this.$http.put('/order', self.orderForm).then((res) => {
                if (res.code === 200) {
                  self.isSaving = false;
                  self.editModal = false;
                  self.reloadList();
                  self.$Message.success('更新成功！');
                } else {
                  self.$Message.error('更新失败！' + res.code);
                }
              })
            } else {
              this.$http.post('/order', self.orderForm).then((res) => {
                if (res.code === 200) {
                  self.isSaving = false;
                  self.editModal = false;
                  self.reloadList();
                  self.$Message.success('添加成功！');
                } else {
                  self.$Message.error('添加失败！' + res.code);
                }
              })
            }
          } else {
            self.isSaving = false;
            self.$Message.error('表单验证失败！');
          }
        })
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
      deleteItem() {
        this.isDeleting = true;
        const self = this;
        this.$http.delete('/order/' + self.data[self.deleteIndex].id, {}).then((res) => {
          if (res.code === 200) {
            self.isDeleting = false;
            self.deleteModal = false;
            self.reloadList();
            self.$Message.success('删除成功！');
          } else {
            self.$Message.error('删除失败！' + res.code);
          }
        });
      },
      getOrderStatusList(data) {
        this.orderStatusList = data.data;
      }
    },

    created() {
      this.getList();
    },

    activated() {
      if (this.keepalive) {
        this.keepalive = false;
        this.getList();
      }
    },

    deactivated() {
      this.keepalive = true;
    }

  }

</script>
