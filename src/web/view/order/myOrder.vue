<!-- ClientCodeGenerator created on 2019/04/22-->
<style lang="less">
    @import '../../styles/common.less';
</style>
<template>
    <div class="smart-crud-container">

        <div class="smart-crud-top">
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
                    <DatePicker type="date" v-model='orderForm.orderReturnDate' placeholder='请输入还球日期'/></FormItem>
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

        <Modal
                v-model="orderBallModal"
                width="850"
                title="订单球类"
                :styles="{top: '20px'}">
            <Table :columns="orderBallColumns" :data="orderBallData"></Table>
            <div slot="footer">
                <Button @click="orderBallModal = false" style="margin-left: 8px">取消</Button>
            </div>
        </Modal>

        <Modal
                v-model="returnBallModal"
                width="280"
                title="请确认球已入库，并确认还球金额"
                :styles="{top: '20px'}">
            <p style="text-align: center">实际花费：{{getTotalCost}}</p>
            <div slot="footer">
                <Button @click="returnBallModal = false" style="margin-left: 8px">取消</Button>
                <Button type="primary" :loading="isSaving" @click="handleReturn()">确认还球</Button>
            </div>
        </Modal>

        <Modal
                v-model="rentalBallModal"
                width="280"
                title="请确认"
                :styles="{top: '20px'}">
            <p style="text-align: center">请确认球已全部借出！</p>
            <div slot="footer">
                <Button @click="rentalBallModal = false" style="margin-left: 8px">取消</Button>
                <Button type="primary" :loading="isSaving" @click="handleRental()">确认租球</Button>
            </div>
        </Modal>

        <CodeSelect :codeType="ballType" @emitedCodes="getBallTypeList">
        </CodeSelect>

        <CodeSelect :codeType="ballBrand" @emitedCodes="getBallBrandList">
        </CodeSelect>
    </div>
</template>
<script>
  import constants from '../shared/constants'
  import CodeSelect from '../shared/code/CodeSelect'
  import DateUtil from "../../libs/DateUtil";
  export default {
    name: 'order',
    components: {
      CodeSelect
    },
    data() {
      return {
        phoneNum: '',
        searchModel: undefined,
        orderForm: {
          orderReturnDate: undefined,
          cost: undefined,
          status: undefined,
        },
        orderFormRule: {
          orderReturnDate: [{required: true, type:'date', message: '还球日期不能为空.', trigger: 'change'},],
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
              return h('a', {
                on: {
                  click: () => {
                    this.orderBallData = [];
                    this.orderBallData = this.data[params.index].balls;
                    this.orderBallModal = true;
                  }
                }
              },'查看')
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
            title: '下单日期',
            key: 'orderDate',
            render: (h, params) => {
              return h('span', DateUtil.formatDate(new Date(params.row.orderDate), 'yyyy-MM-dd'))
            }
          },
          {
            title: '预约还球日期',
            key: 'orderReturnDate',
            render: (h, params) => {
              return h('span', DateUtil.formatDate(new Date(params.row.orderReturnDate), 'yyyy-MM-dd'))
            }
          },
          {
            title: '租球日期',
            key: 'orderRentDate',
            render: (h, params) => {
              if (params.row.orderRentDate) {
                return h('span', DateUtil.formatDate(new Date(params.row.orderRentDate), 'yyyy-MM-dd'))
              } else {
                return h('span','无')
              }
            }
          },
          {
            title: '实际还球日期',
            key: 'orderRealReturnDate',
            render: (h, params) => {
              if (params.row.orderRealReturnDate) {
                return h('span', DateUtil.formatDate(new Date(params.row.orderRealReturnDate), 'yyyy-MM-dd'))
              } else {
                return h('span','无')
              }
            }
          },
          {
            title: '预计花费',
            key: 'predictCost'
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
          }],
        data: [],
        orderStatus: constants.codeType.order_status,
        orderStatusList: [],
        orderBallModal: false,
        returnBallModal: false,
        rentalBallModal: false,
        orderBallColumns: [
          {
            type: 'index',
            title: '序号',
            width: 60,
            align: 'center'
          },
          {
            title: '类型',
            key: 'typeName',
            render: (h, params) => {
              let typeName = '';
              this.ballTypeList.map(item => {
                if (item.code === params.row.type) {
                  typeName = item.name;
                }
              });
              return h('span', typeName)
            }
          },
          {
            title: '品牌',
            key: 'brandName',
            render: (h, params) => {
              let brandName = '';
              this.ballBrandList.map(item => {
                if (item.code === params.row.brand) {
                  brandName = item.name;
                }
              });
              return h('span', brandName)
            }
          },
          {
            title: '图片', key: 'imageUrl',
            render: (h, params) => {
              return h('img', {
                domProps: {
                  align: 'center',
                  src: params.row.imageUrl,
                },
                style: {
                  width: '45px',
                }
              })
            }
          },
          {
            title: '日租价格',
            key: 'dayPrice1'
          },
          {
            title: '日租超出价格',
            key: 'dayPrice2'
          },
          {
            title: '月租价格',
            key: 'monthPrice'
          },
          {
            title: '已选数量',
            key: 'count'
          }],
        orderBallData: [],
        returnIndex: undefined,
        rentalIndex: undefined,
        startDate: undefined,
        endDate: undefined,
        ballType: constants.codeType.ball_type,
        ballBrand: constants.codeType.ball_brand,
        ballTypeList: [],
        ballBrandList: [],
      }
    },
    computed: {
      getTotalCost() {
        let cost = 0;
        if (this.startDate) {
          let startTime = this.startDate.getTime();
          let returnTime = this.endDate.getTime();
          let endTime = new Date().getTime();
          let days = Math.floor((endTime - returnTime) / (3600 * 24 * 1e3)) + 1;
          let dates = Math.floor((endTime - startTime) / (3600 * 24 * 1e3)) + 1;
          if (days > 0) {
            dates -= days;
            this.orderBallData.map(item => {
              cost += item.dayPrice2 * days;
            })
          }
          if (dates < 30) {
            this.orderBallData.map(item => {
              cost += item.dayPrice1 * dates;
            })
          } else {
            this.orderBallData.map(item => {
              cost += item.monthPrice * Math.floor(dates / 30);
              cost += item.dayPrice1 * (dates % 30)
            })
          }
        }
        return cost;
      },
      userIdentity () {
        return this.$store.state.user.userIdentity;
      },
    },
    methods: {
      getList() {
        this.loading = true;
        const self = this;
        const params = {
          page: this.pageInfo.pageNum || 1,
          size: this.pageInfo.pageSize || 10,
        };
        this.$http.get('/order/my/' + this.userIdentity.id, params).then((res) => {
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
      },
      getBallTypeList(data) {
        this.ballTypeList = data.data;
      },
      getBallBrandList(data) {
        this.ballBrandList = data.data;
      },
      handleReturn() {
        this.isSaving = true;
        let params = {
          id: this.data[this.returnIndex].id,
          cost: this.getTotalCost
        };
        this.$http.get('/order/return', params).then((res) => {
          if (res.code === 200) {
            this.$Message.success("还球成功");
            this.returnBallModal = false;
            this.reloadList();
          } else {
            this.$Message.error("还球失败")
          }
          this.isSaving = false;
        });
      },
      handleRental() {
        this.isSaving = true;
        this.$http.get('/order/rental/' + this.data[this.rentalIndex].id).then((res) => {
          if (res.code === 200) {
            this.$Message.success("租球成功");
            this.rentalBallModal = false;
            this.reloadList();
          } else {
            this.$Message.error("租球失败")
          }
          this.isSaving = false;
        });
      },
      reloadList() {
        this.pageInfo.pageNum = 1;
        this.getList();
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
