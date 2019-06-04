<style lang="less">
    @import "orderGeneral.less";
    @import "orderBooking.less";
    @import '../../styles/common.less';

    .order-booking {
        .item-content {
            line-height: 18px;
            margin-top: 11px;
            font-size: 14px;
            padding-left: 20px;
            padding-right: 20px;

            span {
                float: right;
            }

            .item-price {
                color: #ed2010;
            }
        }

        .search-form {
            .ivu-form-item {
                display: inline-block;
            }
        }
    }
</style>

<template>

    <div class="orderBooking order-booking">
        <div class="paneinner_right">
            <div>
                <Form ref='searchForm' :model='searchForm' :label-width='80' class="pickForm search-form">
                    <FormItem label='类型：' style="width: 170px">
                        <CodeSelect :codeType="ballType" @emitedCodes="getBallTypeList">
                            <Select v-model="searchForm.type">
                                <Option value="null">请选择</Option>
                                <Option v-for="item in ballTypeList" :value="item.code" :key="item.id">{{ item.name }}
                                </Option>
                            </Select>
                        </CodeSelect>
                    </FormItem>
                    <FormItem label='品牌：' style="width: 170px">
                        <CodeSelect :codeType="ballBrand" @emitedCodes="getBallBrandList">
                            <Select v-model="searchForm.brand">
                                <Option value="null">请选择</Option>
                                <Option v-for="item in ballBrandList" :value="item.code" :key="item.id">{{ item.name
                                    }}
                                </Option>
                            </Select>
                        </CodeSelect>
                    </FormItem>
                    <FormItem>
                        <Button @click="reloadBallList">筛选</Button>
                    </FormItem>
                </Form>

                <div v-if="addShowList.length > 0" style="margin: 0 36px 36px 36px;position: relative;">
                    <Spin fix size="large" v-if="spinShow"></Spin>
                    <div style="display: inline-block"
                         v-for="(item, index) in addShowList" :key="index" class="itemWrapper">
                        <div class="itemArea">
                            <div class="itemPic">
                                <img :src="item.imageUrl" style="width: 100%;height: 100%">
                            </div>
                            <div class="itemTitle"><span>{{item.name}}</span></div>
                            <div class="item-content">
                                <p>球类：<span>{{getBallTypeName(item.type)}}</span></p>
                                <p>品牌：<span>{{getBallBrandName(item.brand)}}</span></p>
                                <p>日租价格：<span class="item-price">¥{{item.dayPrice1}}</span></p>
                                <p>日租超出价格：<span class="item-price">¥{{item.dayPrice2}}</span></p>
                                <p>月租价格：<span class="item-price">¥{{item.monthPrice}}</span></p>
                                <p>库存：<span>{{item.stock}}</span></p>
                            </div>
                            <div style="text-align: center;margin-top: 18px;font-size: 16px;">
                                <Button size="small" shape="circle" icon="ios-remove"
                                        @click="minusSum(index)"></Button>
                                {{item.count? item.count : '0'}}
                                <Button size="small" shape="circle" icon="ios-add"
                                        @click="plusSum(index)"></Button>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else style="text-align: center">暂时没有您需要的球哦!</div>

                <div style="text-align: center;margin-top: 10px">
                    <Page
                            v-model="pageInfo"
                            :current="pageInfo.pageNum"
                            :total="pageInfo.total"
                            :page-size="pageInfo.pageSize"
                            size="small"
                            @on-change="changePage"
                            show-total>
                    </Page>
                </div>
                <div slot="footer" class="submit-button">
                    <Button type="primary" :loading="isSaving" @click="handleSubmit()"
                            style="margin-left:0px;border-radius: 20px">
                        <span v-if="!isSaving">提交下单</span>
                        <span v-else>正在提交...</span></Button>
                </div>
            </div>
        </div>

        <Modal
                v-model="confirmModal"
                width="850"
                title="确认订单"
                :styles="{top: '20px'}">
            <span>还球时间：</span>
            <DatePicker v-model="returnDate" type="date" placeholder="请选择还球时间" style="width: 200px;margin-bottom: 20px" @on-change="getReturnDate"></DatePicker>
            <Input v-model="phoneNum" v-if="isVisitor" placeholder="请输入手机号" style="width: 150px;margin-left: 30px"></Input>
            <Table :loading="confirmModalLoading" :columns="columns" :data="data"></Table>
            <p style="text-align: right;margin-top: 20px">预计花费：<span style="color: #ed2010;">￥{{getTotalCost}}</span></p>
            <div slot="footer">
                <Button @click="handleConfirmReset()" style="margin-left: 8px">取消</Button>
                <Button type="primary" :loading="confirmButtonLoading" @click="totalProdList()">确认订单</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
  import CodeSelect from '@/view/shared/code/CodeSelect.vue';
  import constants from '../shared/constants'
  import DateUtil from "../../libs/DateUtil";

  export default {
    components: {
      CodeSelect
    },
    name: 'orderBooking',
    data() {
      return {
        searchForm: {
          type: 'null',
          brand: 'null'
        },
        ballType: constants.codeType.ball_type,
        ballBrand: constants.codeType.ball_brand,
        ballTypeList: [],
        ballBrandList: [],
        orderTypeForm: {
          selectType: '',
          orgTAmt: 1
        },
        orderDetailList: [],
        prodSkuList: [],
        userAddrList: [],
        cubList: [],
        addShowList: [],
        searchModel: undefined,
        spinShow: false,
        isLoading: false,
        SelectScopeId: undefined,
        isSaving: false,
        isDeleting: false,
        pageInfo: {
          pageSize: 8
        },
        checkList: [],
        catgList: [],
        sumList: [],
        totalList: [],
        confirmModal: false,
        confirmModalLoading: false,
        confirmButtonLoading: false,
        columns: [
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
        data: [],
        returnDate: '',
        phoneNum: undefined
      }
    },
    computed: {
      userIdentity () {
        return this.$store.state.user.userIdentity;
      },
      getTotalCost() {
        let cost = 0;
        if (this.returnDate) {
          let startTime = new Date().getTime();
          let endTime = this.returnDate.getTime();
          let dates = Math.floor((endTime - startTime) / (3600 * 24 * 1e3)) + 1;
          if (dates < 30) {
            this.data.map(item => {
              cost += item.dayPrice1 * dates;
            })
          } else {
            this.data.map(item => {
              cost += item.monthPrice * Math.floor(dates / 30);
              cost += item.dayPrice1 * (dates % 30)
            })
          }
        }
        return cost;
      },
      isVisitor() {
        let flag = false;
        this.userIdentity.roles.map(item => {
          if (item.code === 'ROLE_VISITOR') {
            flag = true;
          }
        });
        return flag;
      }
    },
    methods: {
      reloadBallList() {
        this.pageInfo.pageNum = 1;
        this.getBallList();
      },
      getBallList() {
        this.spinShow = true;
        const self = this;
        let page = this.pageInfo.pageNum || 1;
        this.$http.post('/ball/search?page=' + page + '&size=20', this.searchForm).then((res) => {
          if (res.code === 200) {
            const result = res.data;
            self.pageInfo.total = result.total;
            self.pageInfo.pageSize = result.pageSize;
            self.prodSkuList = result.list;
            self.addShowList = self.prodSkuList;
            if (self.sumList.length > 0) {
              for (let j = 0; j < self.sumList.length; j++) {
                for (let i = 0; i < self.addShowList.length; i++) {
                  if (self.sumList[j].id === self.addShowList[i].id) {
                    self.addShowList[i].count = self.sumList[j].count;
                  }
                }
              }
            } else {
              for (let i = 0; i < self.addShowList.length; i++) {
                self.addShowList[i].count = 0;
              }
            }
          } else {
            self.$Message.error('获取数据失败！' + res.data.code);
          }
          this.spinShow = false;
        }).catch(err => {
          this.spinShow = false;
        });
      },
      getBallTypeList(data) {
        this.ballTypeList = data.data;
      },
      getBallBrandList(data) {
        this.ballBrandList = data.data;
      },
      getBallTypeName(type) {
        let ballTypeName = '';
        this.ballTypeList.map(item => {
          if (item.code === type) {
            ballTypeName = item.name;
          }
        });
        return ballTypeName;
      },
      getBallBrandName(type) {
        let ballBrandName = '';
        this.ballBrandList.map(item => {
          if (item.code === type) {
            ballBrandName = item.name;
          }
        });
        return ballBrandName;
      },
      recordCount(index) {
        let params = {
          id: this.addShowList[index].id,
          data: this.addShowList[index],
          count: this.addShowList[index].count
        };
        if (this.sumList.length === 0) {
          this.sumList.push(params);
        } else {
          for (let i = 0; i < this.sumList.length; i++) {
            if (this.sumList[i].id === this.addShowList[index].id) {
              this.sumList.splice(i, 1);
              this.sumList.push(params);
              break;
            } else {
              if (i === this.sumList.length - 1) {
                this.sumList.push(params);
              }
            }
          }
        }
      },
      plusSum(index) {
        if ((this.addShowList[index].count + 1) <= this.addShowList[index].stock) {
          this.addShowList[index].count++;
          this.addShowList.push();
          this.recordCount(index);
        } else {
          this.$Message.warning("库存告急啦")
        }
      },
      minusSum(index) {
        if (this.addShowList[index].count !== 0) {
          this.addShowList[index].count--;
          this.addShowList.push();
          this.recordCount(index);
        }
      },
      changePage(currentPage) {
        this.pageInfo.pageNum = currentPage;
        this.getBallList()
      },

      changePageSize(pageSize) {
        this.pageInfo.pageSize = pageSize;
        this.getBallList()
      },
      handleSubmit() {
        if (this.sumList.length !== 0) {
          this.data = [];
          this.sumList.map(item => {
            item.data.count = item.count;
            if (item.count > 0) {
              this.data.push(item.data)
            }
          });
          this.returnDate = ''
          this.resetColumns();
          this.confirmButtonLoading = false;
          this.confirmModal = true;
        } else {
          this.$Message.error("您还没有选择任何商品，不可下单");
        }
      },
      totalProdList() {
        if (this.returnDate) {
          let url = '/order/confirm/'
            + this.userIdentity.id + "?returnDate="
            + DateUtil.formatDate(this.returnDate, 'yyyy-MM-dd hh:mm:ss')
            + "&predictCost=" + this.getTotalCost;
          if (this.isVisitor && !this.phoneNum) {
            this.$Message.warning("游客必须输入手机号")
            return;
          } else if (this.isVisitor && this.phoneNum) {
            url += "&phoneNum=" + this.phoneNum;
          }
          this.confirmButtonLoading = true;
          let data = [];
          this.sumList.map(item => {
            data.push({
              id: item.id,
              count: item.count
            })
          });
          this.$http.post(url, data).then((res) => {
            if (res.code === 200) {
              if (res.data && res.data.length) {
                this.resetColumns();
                this.columns.push({
                  title: '注意',
                  render: (h, params) => {
                    let flag = false;
                    res.data.map(item => {
                      if (item === params.row.id) {
                        flag = true;
                      }
                    });
                    if (flag) {
                      return h('span', {
                        style: {
                          color: '#d8001a'
                        }
                      }, '库存不足')
                    }
                  }
                })
              } else {
                this.$Message.success("下单成功")
                this.confirmModal = false;
                this.sumList = [];
                this.getBallList();
              }
            } else {
              this.$Message.error("下单失败，请重新下单")
            }
            this.confirmButtonLoading = false;
          }).catch(err => {
            this.confirmButtonLoading = false;
          });
        } else {
          this.$Message.warning("请选择还球时间")
        }
      },
      handleConfirmReset() {
        this.confirmModal = false;
      },
      resetColumns() {
        if (this.columns.length === 9) {
          this.columns.splice(this.columns.length - 1)
        }
      },
      getReturnDate(value) {
        let returnDate = new Date(value);
        let today = new Date().setHours(0, 0, 0, 0);
        if (today > returnDate) {
          this.$Message.error("还球时间选择有误，请重新选择");
          this.returnDate = "";
        }
      }
    },
    created() {
      this.getBallList();
    },
  }
</script>