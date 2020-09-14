<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
       <el-form-item label="sku" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="input SKU"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="valid From" prop="validFrom">
       <el-date-picker
                      v-model="queryParams.validFrom"
                      type="date"
                      clearable
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="select ValidFrom">
                    </el-date-picker>
      </el-form-item>
       <el-form-item label="valid To" prop="validTo">
       <el-date-picker
                      v-model="queryParams.validTo"
                      type="date"
                      clearable
                      value-format="yyyy-MM-dd"
                      placeholder="select ValidTo">
                    </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['product:msrp:add']"
        >add</el-button>
      </el-col>
       <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['product:msrp:import']"
            >import</el-button>
          </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:msrp:export']"
        >export</el-button>
      </el-col>
    </el-row>

    <!-- 列表框展示 -->
    <el-table v-loading="loading" :border="true" :data="newTekMsrpList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable header-align="center" align="center" label="id" prop="id" width="100" />
      <el-table-column sortable header-align="center" align="center" label="model#" prop="modelInfo" :show-overflow-tooltip="true" width="220" />
      <el-table-column sortable header-align="center" align="center" label="sku" prop="sku" :show-overflow-tooltip="true" width="300" />
      <el-table-column sortable header-align="center" align="center" label="country" prop="country" :show-overflow-tooltip="true" width="300" />
      <el-table-column sortable header-align="center" align="center" label="valid From" prop="validFrom" width="150" />
      <el-table-column sortable header-align="center" align="center" label="valid To" prop="validTo" width="150" />
      <el-table-column sortable header-align="center" align="center" label="msrp" prop="msrp" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:msrp:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:msrp:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getMsrpList"
    />

    <!-- 添加或修改产品建议零售价对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="sku" prop="sku">
                      <el-autocomplete
                        class="inline-input"
                        clearable
                        v-model="form.sku"
                        :fetch-suggestions="querySearch"
                        placeholder="请输入产品名称"
                        :trigger-on-focus="true" 
                        @select="handleSelect"
                        @keyup.enter.native="search"
                      ></el-autocomplete>
                    </el-form-item>
          </el-col>
        </el-row>
          <el-row>
            <el-col :span="24">
                <el-form-item label="country" prop="country">
                <el-autocomplete
                  class="inline-input"
                  clearable
                  v-model="form.country"
                  :fetch-suggestions="querySearchCountry"
                  placeholder="请输入国家"
                  :trigger-on-focus="true" 
                  @select="handleSelectCountry"
                  @keyup.enter.native="search"
                ></el-autocomplete>
              </el-form-item>
            </el-col>
          </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="Valid From" prop="validFrom">
                    <el-date-picker
                      v-model="form.validFrom"
                      type="date"
                      value-format="yyyy-MM-dd"
                      placeholder="选择有效期开始日期">
                    </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="Valid To" prop="validTo">
                   <el-date-picker
                      v-model="form.validTo"
                      type="date"
                      value-format="yyyy-MM-dd"
                      placeholder="选择有效期结束日期">
                    </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
   

        <el-row>
          <el-col :span="16">
        <el-form-item label="msrp" prop="msrp">
          <el-input v-model="form.msrp" maxlength="12" oninput="if(isNaN(value)) { value = '' } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}" placeholder="input MSRP">
            <span slot="suffix">{{currency}}</span>
          </el-input>
        </el-form-item>
          </el-col>
        </el-row>


        <el-form-item label="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

      <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <!-- <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的SKU数据 -->
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCountryBySKU,listProductModel,exportMSRP,importMSRPTemplate,listMsrp,listProduct,listModel,listCustomer,addMsrp,updateMsrp,getMsrp,listGoods,delMsrp,listCountryCode,getCurrencyByCountry   } from "@/api/tek/msrp/msrp";
import { getToken } from "@/utils/auth";

export default {
  name: "Role",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msrp: undefined,
        validFrom: undefined,
        validTo: undefined
      },
      // 表单参数
      form: {
        currency : undefined
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        sku: [
          { required: true, message: "SKU不能为空", trigger: "change" }
        ],
         country: [
          { required: true, message: "Country不能为空", trigger: "change" }
        ],
          msrp: [
          { required: true, message: "MSRP不能为空", trigger: "blur" }
        ],
          validFrom: [
          { required: true, message: "validFrom不能为空", trigger: "blur" }
        ],
          validTo: [
          { required: true, message: "validTo不能为空", trigger: "blur" }
        ]
      },
      modelName: '',
      newTekMsrpList: [],
      tekSkuToMatnrList: [],
      newTekCustomerList: [],
      sku: '',
      validFrom: '',
      validTo: '',
      countryOptions: [],
      currency: '',
      // 用户导入参数
      upload: {
        // 是否显示弹出层（SKU导入）
        open: false,
        // 弹出层标题（SKU导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/tek/newTekMsrp/importData"
      },
    };
  },
    mounted () {
            // listGoods().then(res => {
            //   if (res.code === 200) {
            //     res.data.forEach(item => {
            //       this.tekSkuToMatnrList.push({
            //         id: item.id,
            //         value: item.goodsSku
            //       })
            //     })
            //   }
            // })

            listProductModel().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.tekSkuToMatnrList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })

            listCountryCode().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.countryOptions.push({
                    id: item.id,
                    value: item.chineseName
                  })
                })
              }
            })
          },
  created() {
    this.getMsrpList();
    // this.getProductList();
    // this.getCustomerList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询零售价列表 */
      getMsrpList() {
      this.loading = true;
      listMsrp(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.newTekMsrpList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

         /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearch (queryString, cb) {
          const tekSkuToMatnrList = this.tekSkuToMatnrList
          const results = queryString
            ? tekSkuToMatnrList.filter(this.createFilter(queryString))
            : tekSkuToMatnrList
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
    createFilter (queryString) {
          return sku => {
            return (
           sku.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
            )
          }
        },

                /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearchCountry (queryString, cb) {
          const countryOptions = this.countryOptions
          const results = queryString
            ? countryOptions.filter(this.createFilterCountry(queryString))
            : countryOptions
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
          createFilterCountry (queryString) {
                return country => {
                  return (
                    country.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
                  )
                }
              },
     /* 跳转并传值 */
        handleSelect (item) {
          console.log(this.form.country)
          if(this.form.country != undefined){
            this.form.country = '';
          }
          getCountryBySKU(item.value).then(res => {
            if (res.code === 200) {
              this.countryOptions = [];
                   res.data.forEach(productItem => {
                  this.countryOptions.push({
                    id: productItem.id,
                    value: productItem.countryName
                  })
                })
            }
          })
        },
        
           /* 跳转并传值 */
        handleSelectCountry (item) {
            getCurrencyByCountry(item.value).then(res => {
              if (res.code === 200) {
                this.currency = res.data.currencyCode
                this.$nextTick(() => {
                  this.currency = res.data.currencyCode
                })
              }
            })

        },
        handleIconClick (ev) {
        },

        /** 选择产品系列-按钮操作 */
    handleChooseModel(row) {
      this.reset();
      const productId = row.productId || this.ids
      // this.form.id = productId;
      // this.form = response.data;
        this.open = true;
        this.title = "选择产品系列";
    },


    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.form = {
        id: undefined,
        modelName: undefined,
        deptIds: [],
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getMsrpList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品建议零售价";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMsrp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品建议零售价";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMsrp(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getMsrpList();
              }
            });
          } else {
            addMsrp(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getMsrpList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const sku = row.sku;
      this.$confirm('是否确认删除sku为"' + sku + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMsrp(ids);
        }).then(() => {
          this.getMsrpList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有MSRP数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportMSRP(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
       /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "MSRP导入";
      this.upload.open = true;
    },
       /** 下载模板操作 */
    importTemplate() {
      importMSRPTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getMsrpList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
