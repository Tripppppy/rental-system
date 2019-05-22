<!-- ClientCodeGenerator created on 2019/04/23-->
<style lang="less">
    @import '../../styles/common.less';

</style>

<template>
    <div class="smart-crud-container">

        <div class="smart-crud-top">
            <Row>
                <Col span="24">
                    <Button class="" type="primary" @click="add">添加</Button>
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
                title="编辑球类">
            <Form ref='ballForm' :model='ballForm' :rules='ballFormRule' :label-width='120'>
                <FormItem label='类型' prop='type' style="width: 270px">
                    <CodeSelect :codeType="ballType" @emitedCodes="getBallTypeList">
                        <Select v-model="ballForm.type">
                            <Option v-for="item in ballTypeList" :value="item.code" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </CodeSelect>
                </FormItem>
                <FormItem label='品牌' prop='brand' style="width: 270px">
                    <CodeSelect :codeType="ballBrand" @emitedCodes="getBallBrandList">
                        <Select v-model="ballForm.brand">
                            <Option v-for="item in ballBrandList" :value="item.code" :key="item.id">{{ item.name }}</Option>
                        </Select>
                    </CodeSelect>
                </FormItem>
                <FormItem label='库存' prop='stock' style="width: 270px">
                    <Input v-model='ballForm.stock' number placeholder='请输入库存'/>
                </FormItem>
                <FormItem label='日租价格' prop='dayPrice1' style="width: 270px">
                    <Input v-model='ballForm.dayPrice1' number placeholder='请输入日租价格'/>
                </FormItem>
                <FormItem label='日租超出价格' prop='dayPrice2' style="width: 270px">
                    <Input v-model='ballForm.dayPrice2' number placeholder='请输入日租超出价格'/></FormItem>
                <FormItem label='月租价格' prop='monthPrice' style="width: 270px">
                    <Input v-model='ballForm.monthPrice' number placeholder='请输入月租价格'/></FormItem>
                <FormItem label="图片:" prop='imageUrl' style="margin-top: 25px">
                    <div>
                        <div class="margin-top-10 margin-top-10Again" v-show="fileChoose">
                            <img :src="ballForm.imageUrl" class="imgShow"/>
                        </div>
                        <div>
                            <div class="fileInput">
                                <input type="file" accept="image/png, image/jpeg, image/gif, image/jpg"
                                       @change="handleChange" id="fileinput"/>
                                <span>选择图片</span>
                            </div>
                        </div>
                    </div>
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
                <span>删除ball</span>
            </p>
            <div style="text-align:center">
                <p>删除该ball，将无法恢复！</p>
                <p>是否删除?</p>
            </div>
            <div slot="footer">
                <Button type="error" size="large" long :loading="isDeleting" @click="deleteItem">删除</Button>
            </div>
        </Modal>

        <Modal v-model="showCropedImage">
            <div class="cropperAgain">
                <vueCropper
                        ref="cropper"
                        :img="cut.Img"
                        :outputSize="cut.size"
                        :outputType="cut.outputType"
                        :autoCrop="cut.autoCrop"
                        :autoCropWidth="cut.autoCropWidth"
                        :autoCropHeight="cut.autoCropHeight"
                        :fixed="cut.fixed"
                        :canScale="cut.canScale"
                        :fixedNumber="cut.fixedNumber">
                </vueCropper>
            </div>
            <div slot="footer">
                <Button @click="cancelReset()" style="margin-left: 8px">取消</Button>
                <Button type="primary" icon="crop" @click="handleCrop" class="pictureButton">裁剪</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
  import { VueCropper }  from 'vue-cropper'
    import constants from '../shared/constants'
    import CodeSelect from '../shared/code/CodeSelect'
    import { cropperPicture } from '../../libs/util';
  export default {
    name: 'ball',
    components: {
      CodeSelect,
      VueCropper
    },
    data() {
      return {
        searchModel: undefined,
        ballForm: {
          type: undefined,
          brand: undefined,
          stock: undefined,
          dayPrice1: undefined,
          dayPrice2: undefined,
          monthPrice: undefined,
          imageUrl: undefined
        },
        ballFormRule: {
          type: { required: true, type: 'string', message: '类型不能为空', trigger: 'change' },
          brand: { required: true, type: 'string', message: '品牌不能为空', trigger: 'change' },
          stock: [{required: true, type: 'number', message: '库存不能为空且必须为数字.', trigger: 'blur'},],
          dayPrice1: [{required: true,type: 'number', message: '日租价格不能为空且必须为数字.', trigger: 'blur'},],
          dayPrice2: [{required: true,type: 'number', message: '日租超出不能为空且必须为数字.', trigger: 'blur'},],
          monthPrice: [{required: true,type: 'number', message: '月租不能为空且必须为数字.', trigger: 'blur'},],
          imageUrl: [
            { required: true, message: 'ImageUrl不能为空.', trigger: 'blur' },
            { type: 'string', max: 256, message: 'ImageUrl最多256字符', trigger: 'blur' },
          ],
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
            title: '类型',
            key: 'typeName',
          },
          {
            title: '品牌',
            key: 'brandName',
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
            title: '库存',
            key: 'stock'
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
        ballTypeList: [],
        ballBrandList: [],
        ballType: constants.codeType.ball_type,
        ballBrand: constants.codeType.ball_brand,
        fileChoose: false,
        showCropedImage: false,
        cut: {
          size: 1,
          Img: '',
          outputType: 'jpeg || png || web',
          autoCrop: true,
          autoCropWidth: 200,
          autoCropHeight: 200,
          canScale: true,
          fixed: true,
          fixedNumber: [1, 1]
        },
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
        this.$http.get('/ball', params).then((res) => {
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
        this.ballForm = {
          type: undefined,
          brand: undefined,
          stock: undefined,
          dayPrice1: undefined,
          dayPrice2: undefined,
          monthPrice: undefined,
          imageUrl: undefined
        };
        this.$refs.ballForm.resetFields();
        this.editModal = true;
      },
      cancelReset() {
        this.showCropedImage = false;
        console.log('cancelReset');
      },

      edit(index) {
        this.isSaving = false;
        const self = this;
        this.$refs.ballForm.resetFields();
        this.editModal = true;
        this.$http.get('/ball/' + self.data[index].id, {}).then((res) => {
          if (res.code === 200) {
            self.ballForm = res.data;
            if (self.ballForm.imageUrl) {
              self.fileChoose = true;
            }
          } else {
            self.$Message.error('获取ball失败！' + res.code);
          }

        });
      },
      clear() {
        if (document.getElementById('fileinput').value !== '') {
          document.getElementById('fileinput').value = '';
        }
      },

      handleSubmit() {
        this.isSaving = true;
        let self = this;
        this.$refs.ballForm.validate((valid) => {
          if (valid) {
            if (this.ballForm.id) {
              this.$http.put('/ball', self.ballForm).then((res) => {
                if (res.code === 200) {
                  self.editModal = false;
                  self.reloadList();
                  self.$Message.success('更新成功！');
                } else {
                  self.$Message.error('更新失败！' + res.code);
                }
                self.isSaving = false;
              })
            } else {
              this.$http.post('/ball', self.ballForm).then((res) => {
                if (res.code === 200) {
                  self.editModal = false;
                  self.reloadList();
                  self.$Message.success('添加成功！');
                } else {
                  self.$Message.error('添加失败！' + res.code);
                }
                self.isSaving = false;
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
        this.$http.delete('/ball/' + self.data[self.deleteIndex].id, {}).then((res) => {
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
      getBallTypeList(data) {
        this.ballTypeList = data.data;
      },
      getBallBrandList(data) {
        this.ballBrandList = data.data;
      },
      handleChange(e) {
        this.showCropedImage = true;
        let reader = cropperPicture(e);
        reader.onload = (event) => {
          let data = event.target.result;
          reader.onload = null;
          this.cut.Img = data;
          this.clear();
        };
      },
      handleCrop() {
        this.showCropedImage = false;
        let self = this;
        // 获取截图的blob数据
        this.$refs.cropper.getCropBlob((data) => {
          var fd = new FormData();
          fd.append('picturefile', data, 'cropped.png');
          const config = {
            headers: { 'Content-Type': 'multipart/form-data' }
          };
          this.$http.post('/common/file/upload', fd, config).then(resp => {
            if (resp.code === 200) {
              self.ballForm.imageUrl = resp.data.location;
              self.fileChoose = true;
            }
          }).catch(err => {
            console.log(err)
          });
        })
      },
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
