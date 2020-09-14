<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
       <el-form-item label="sku" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="请输入sku"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
       <el-form-item label="Country" prop="countryName">
        <el-input
          v-model="queryParams.countryName"
          placeholder="请输入Country"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['product:sku:add']"
        >add</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >edit</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col> -->
            <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['product:sku:import']"
            >import</el-button>
          </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:sku:export']"
        >export</el-button>
      </el-col>
    </el-row>

    <!-- 列表框展示 -->
    <el-table v-loading="loading" :border="true" :data="newTekProductModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable header-align="center" align="center" label="SKU" prop="sku" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="Country" prop="countryName" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="ChannelType" prop="channelType" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="CategoryName" prop="categoryName" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="ModelName" prop="modelName" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="DisposeName" prop="disposeName" :show-overflow-tooltip="true" width="180" />
      <el-table-column sortable header-align="center" align="center" label="remark" prop="remark" :show-overflow-tooltip="true" width="300" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:sku:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:sku:remove']"
          >删除</el-button>
           <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleChooseModel(scope.row)"
          >选择</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getproductModelList"
    />

    <!-- 查询产品系列列表对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="sku" prop="sku">
           <el-autocomplete
            class="inline-input"
            clearable
            v-model="form.sku"
            :fetch-suggestions="querySearch"
            placeholder="请输入产品名称"
            :trigger-on-focus="true" 
            @select="handleSelectSKU"
            @keyup.enter.native="search"
          ></el-autocomplete>
        </el-form-item>

            <el-form-item label="country" prop="countryName">
              <el-autocomplete
                class="inline-input"
                clearable
                v-model="form.countryName"
                :fetch-suggestions="querySearchCountry"
                placeholder="请输入国家"
                :trigger-on-focus="true" 
                @select="handleSelect"
                @keyup.enter.native="search"
              ></el-autocomplete>
            </el-form-item>

            <el-form-item label="ChannelType" prop="channelType">
                  <el-select v-model="form.channelType" placeholder="请选择渠道类型" size="mini">
                  <el-option label="online" value="online"></el-option>
                  <el-option label="offline" value="offline"></el-option>
                  <el-option label="both" value="both"></el-option>
                  </el-select>
              </el-form-item>

        <el-form-item label="categoryName" prop="categoryId">
                  <el-select v-model="form.categoryId" placeholder="请选择产品品类名称" size="mini" value-key="id" @change="getAllCategoryForChange(form.categoryId)">
                  <el-option
                    v-for="item in categoryOptions"
                    :key="item.id"
                    :label="item.categoryName"
                    :value="item.id">
              </el-option>
                  </el-select>
              </el-form-item>

            <!-- @change="getAllModelForChange(form.modelId)" -->
            <el-form-item label="modelName" prop="modelId">
                      <el-select v-model="form.modelId" placeholder="请选择产品系列名称" size="mini" value-key="id">
                      <el-option
                        v-for="item in modelOptions"
                        :key="item.id"
                        :label="item.modelName"
                        :value="item.id">
                  </el-option>
                      </el-select>
                  </el-form-item>

                  <el-form-item label="disposeName" prop="disposeId">
                      <el-select v-model="form.disposeId" placeholder="请选择产品配置名称" size="mini" value-key="id">
                      <el-option
                        v-for="item in disposeOptions"
                        :key="item.id"
                        :label="item.disposeName"
                        :value="item.id">
                  </el-option>
                      </el-select>
                  </el-form-item>

                  <el-form-item label="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
                  </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitModelForm">确 定</el-button>
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
import { exportSKU,importTemplate,listProductModel,listModel,addProductModel,listGoods,listCategory,listDispose,getProductModel,updateProductModel,listCountryCode,delProductModel    } from "@/api/tek/productModel/productModel";
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
      // 角色表格数据
      roleList: [],
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
        modelName: undefined,
        sku: undefined,
        countryName: undefined
      },
      // 表单参数
      form: {
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
          sku: [
            { required: true, message: "SKU不能为空", trigger: 'change'}
          ],
          countryName: [
          { required: true, message: "Country不能为空", trigger: 'change'}
          ],
          channelType: [
          { required: true, message: "ChannelType不能为空", trigger: 'change'}
          ],
           categoryId: [
          { required: true, message: "产品品类名称不能为空", trigger: 'change'}
          ],
           modelId: [
          { required: true, message: "产品系列名称不能为空", trigger: 'blur'}
          ],
          disposeId: [
          { required: true, message: "产品配置名称不能为空", trigger: 'change'}
          ]
      },
      modelName: '',
      goodsOptions: [],
      modelOptions: [],
      disposeOptions: [],
      categoryOptions: [],
      newTekProductModelList: [],
      goodsList: [],
      sku: '',
      disposeName: '',
      countryOptions: [],
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
        url: process.env.VUE_APP_BASE_API + "/tek/newTekProductModel/importData"
      },
    };
  },
  mounted () {
            listGoods().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.goodsList.push({
                    id: item.id,
                    value: item.goodsSku
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
    this.getproductModelList();
    this.getCategoryListForProduct();
    this.getModelListForProduct();
    this.getDisposeListForProduct();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询产品关联系列列表 */
      getproductModelList() {
      this.loading = true;
      listProductModel(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.newTekProductModelList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

     /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearch (queryString, cb) {
          const goodsList = this.goodsList
          const results = queryString
            ? goodsList.filter(this.createFilter(queryString))
            : goodsList
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
                return countryName => {
                  return (
                countryName.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
                  )
                }
              },
     /* 跳转并传值 */
        handleSelect (item) {
        },

        handleSelectSKU (item) {
          console.log(item)


        },
        handleIconClick (ev) {
        },

          /** 查询所有产品品类给select框用 */
      getCategoryListForProduct() {
      this.loading = true;
      listCategory().then(
        response => {
          this.categoryOptions = response.data;
          this.loading = false;
        }
      );
    },

           /** 查询所有产品系列给select框用 */
      getModelListForProduct(data) {
      this.loading = true;
      listModel(data).then(
        response => {
          this.modelOptions = response.data;
          this.loading = false;
        }
      );
    },
           /** 查询所有产品配置给select框用 */
      getDisposeListForProduct(data) {
      this.loading = true;
      listDispose(data).then(
        response => {
          this.disposeOptions = response.data;
          this.loading = false;
        }
      );
    },

        /** 选择产品系列-按钮操作 */
    handleChooseModel(row) {
      this.reset();
      const productId = row.productId || this.ids
      this.form.id = productId;
        this.open = true;
        this.title = "选择产品系列";
    },


  getAllCategoryForChange(data){
   this.form.modelId = ''
   if(this.form.disposeId != undefined){
        this.form.disposeId = ''
    }
      this.getModelListForProduct(data);
       this.getDisposeListForProduct(data+"_1");
  },

  // getAllModelForChange(data){
  //   if(this.form.disposeId != undefined){
  //       this.form.disposeId = ''
  //   }
  //     this.getDisposeListForProduct(data);
  // },
  
  // getAllDisposeForChange(data){
  //  this.form.disposeId = data;
  //     this.getDisposeListForProduct(data);
  // },


        /** 选择产品系列-提交按钮 */
    // submitModelForm: function() {
    //   this.form.id = 0;
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.id != undefined) {
    //         addProductModel(this.form).then(response => {
    //           if (response.code === 200) {
    //             this.msgSuccess("添加产品关联成功");
    //             this.open = false;
    //             this.getproductModelList();
    //           }
    //         });
    //       }
    //     }
    //   });
    // },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        modelName: undefined,
        deptIds: [],
        remark: undefined,
        sku: undefined,
        categoryId: undefined,
        modelId: undefined,
        disposeId: undefined,
        categoryName: undefined,
        disposeName: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getproductModelList();
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
      this.title = "添加产品品类系列配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductModel(id).then(response => {
        // this.getModelListForProduct(response.data.modelId);
        // this.getDisposeListForProduct(response.data.disposeId);
        this.form = response.data;
        this.open = true;
        this.title = "修改产品关联";
      });
    },
    /** 提交按钮 */
    submitModelForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateProductModel(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getproductModelList();
              }
            });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            addProductModel(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getproductModelList();
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
      this.$confirm('是否确认删除SKU名称为"' + sku + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProductModel(ids);
        }).then(() => {
          this.getproductModelList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
       /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "SKU导入";
      this.upload.open = true;
    },
       /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
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
      this.getproductModelList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有SKU数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSKU(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>

