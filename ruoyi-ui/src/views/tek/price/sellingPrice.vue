<template>
<div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="Country" prop="country">
        <el-input
          v-model="queryParams.country"
          placeholder="input Country"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Customer Name" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="input Customer Name"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="input SKU"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ChannelType" prop="channelType">
        <!-- <el-input
          v-model="queryParams.channelType"
          placeholder="input SKU"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.channelType" placeholder="input ChannelType">
                  <el-option label="online" value="online"></el-option>
                  <el-option label="offline" value="offline"></el-option>
                  <el-option label="both" value="both"></el-option>
                  </el-select>
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
            v-hasPermi="['customer:sellingPrice:add']"
          >add</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
              type="info"
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['customer:sellingPrice:import']"
            >import</el-button>
          </el-col>
          <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['customer:sellingPrice:export']"
        >export</el-button>
      </el-col>
     </el-row>  

        <!-- 列表框展示 -->
    <el-table v-loading="loading" :border="true" height="690" :data="newTekMsrpWkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable header-align="center" align="center" label="id" prop="id" width="80" />
      <el-table-column sortable header-align="center" align="center" label="Country" prop="country" :show-overflow-tooltip="true" width="150" />
      <el-table-column sortable header-align="center" align="center" label="Customer Name" prop="customerName" :show-overflow-tooltip="true" width="150" />
      <el-table-column sortable header-align="center" align="center" label="model#" prop="modelInfo" :show-overflow-tooltip="true" width="200" />
      <el-table-column sortable header-align="center" align="center" label="SKU" prop="sku" :show-overflow-tooltip="true" width="200" />
      <el-table-column sortable header-align="center" align="center" label="ChannelType" prop="channelType" :show-overflow-tooltip="true" width="150" />
      <el-table-column sortable header-align="center" align="center" label="Valid From" prop="validFromWK" width="150" />
      <el-table-column sortable header-align="center" align="center" label="Valid To" prop="validToWK" width="150" />
      <el-table-column sortable header-align="center" align="center" label="Selling Price" prop="sellingPrice" width="130" />
      <el-table-column sortable header-align="center" align="center" label="currency" prop="currencyType" width="100" />
      <el-table-column sortable header-align="center" align="center" label="Promotion Type" prop="promotionType"  width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['customer:sellingPrice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['customer:sellingPrice:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getMsrpWKList"
    />

    <!-- 添加或修改wkMsrp对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <!-- <el-row>
        <el-col :span="12"> -->
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
        <!-- </el-col>
        </el-row> -->

            <!-- <el-row>
            <el-col :span="12"> -->
            <el-form-item label="modelName" prop="modelId">
                      <el-select v-model="form.modelId" placeholder="请选择产品系列名称" size="mini" value-key="id" @change="getAllModelForChange(form.modelId)">
                      <el-option
                        v-for="item in modelOptions"
                        :key="item.id"
                        :label="item.modelName"
                        :value="item.id">
                  </el-option>
                      </el-select>
                  </el-form-item>
            <!-- </el-col>
            </el-row> -->

                  <!-- <el-row>
                  <el-col :span="12"> -->
                  <el-form-item label="disposeName" prop="disposeId">
                      <el-select v-model="form.disposeId" placeholder="请选择产品配置名称" size="mini" value-key="id" @change="getAllDisposeForChange(form.disposeId)">
                      <el-option
                        v-for="item in disposeOptions"
                        :key="item.id"
                        :label="item.disposeName"
                        :value="item.id">
                  </el-option>
                      </el-select>
                  </el-form-item>
                  <!-- </el-col>
                  </el-row> -->
                  <el-row>
                <el-col :span="12">
                        <el-form-item label="Country" prop="country">
                          <el-autocomplete
                            class="inline-input"
                            v-model="form.country"
                            clearable
                            :fetch-suggestions="querySearchCountry"
                            placeholder="input Country"
                            :trigger-on-focus="true" 
                            @select="handleSelectCountry"
                            @keyup.enter.native="search"
                          ></el-autocomplete>
                        </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                        <el-form-item label="CustomerName" prop="customerName">
                          <el-autocomplete
                            class="inline-input"
                            clearable
                            v-model="form.customerName"
                            :fetch-suggestions="querySearchCustomer"
                            placeholder="input CustomerName"
                            :trigger-on-focus="true" 
                            @select="handleSelectCustomer"
                            @keyup.enter.native="search"
                          ></el-autocomplete>
                        </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                      <el-form-item label="ChannelType" prop="channelType">
                     <el-select v-model="form.channelType" placeholder="请选择渠道类型" size="mini" value-key="id" @change="getChannelTypeForChange(form.channelType)">
                      <el-option value="offline">offline</el-option>
                      <el-option value="online">online</el-option>
                      <el-option value="both">both</el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                      <el-form-item label="SKU" prop="sku">
                      <el-autocomplete
                        class="inline-input"
                        clearable
                        v-model="form.sku"
                        :fetch-suggestions="querySearchSku"
                        placeholder="input SKU"
                        :trigger-on-focus="true" 
                        @select="handleSelectSKU"
                        @keyup.enter.native="search"
                      ></el-autocomplete>
                    </el-form-item>
                </el-col>
              </el-row>
                  <el-row>
                    <el-col :span="12">
                      <el-form-item label="Valid From D" prop="validFromWK">
                        <el-date-picker
                          v-model="form.validFromWK"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="select ValidFromWK">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                     <el-col :span="12">
                      <el-form-item label="To D" prop="validToWK">
                        <el-date-picker
                          v-model="form.validToWK"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="select ValidToWK">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                  </el-row>
        <el-row>
         <el-col :span="12">
        <el-form-item label="Currency" prop="currencyType">
          <el-autocomplete
                        class="inline-input"
                        v-model="form.currencyType"
                        clearable
                        :fetch-suggestions="querySearch"
                        placeholder="input Currency"
                        :trigger-on-focus="true" 
                        @select="handleSelect"
                        @keyup.enter.native="search"
                        disabled="disabled"
                      ></el-autocomplete>
        </el-form-item>
         </el-col>
         <el-col :span="12">
        <el-form-item label="Promotion Type" prop="promotionType">
        <el-select v-model="form.promotionType" placeholder="select PromotionType">
            <el-option
              v-for="item in newTekPromotionTypeList"
              :key="item.id"
              :label="item.promotionType"
              :value="item.promotionType">
            </el-option>
      </el-select>
        </el-form-item>
         </el-col>
        </el-row>
        
        <el-col :span="12">
        <el-form-item label="Selling Price" prop="sellingPrice">
          <el-input v-model="form.sellingPrice" maxlength="12" oninput="if(isNaN(value)) { value = '' } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}" placeholder="input Selling Price"></el-input>
        </el-form-item>
        </el-col>
         <el-col :span="24">
        <el-form-item label="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="input Remark"></el-input>
        </el-form-item>
         </el-col>
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

import { listCustomerForPermission,listCountryCodeForPermission,exportSellingPrice,listDispose,listModelForCategoryId,listCategory,querySKU,delMsrpWK,addMsrp,listProduct,listCustomer,addMsrpWk,listCountry,listCurrencyCode,listMsrpWK,getMsrpWK,updateMsrpWK,listCountryCode,listPromotionType,importTemplate,listProductModel } from "@/api/tek/msrp/msrp";
import { getToken } from "@/utils/auth";

export default {
  data() {
    return {
      queryParams: {
         pageNum: 1,
        pageSize: 10,
        country: undefined,
        customerName: undefined,
        sku: undefined,
        channelType: undefined
      },
      allWeeks: '',
      lastWeekDays: '',
      rows: 0,
      year: '',
      form: {
      },
      tblGoodsList: [],
      newTekCustomerList: [],
      newTekMsrpWkList: [],
        // 总条数
      total: 0,
        // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
         // 表单校验
      rules: {
        sku: [
          { required: true, message: "sku不能为空", trigger: "change" }
        ],
         country: [
          { required: true, message: "country不能为空", trigger: "change" }
        ],
          customerName: [
          { required: true, message: "customerName不能为空", trigger: "change" }
        ],
          channelType: [
          { required: true, message: "channelType不能为空", trigger: "change" }
        ],
          year: [
          { required: true, message: "year不能为空", trigger: "blur" }
        ],
          validFromWK: [
          { required: true, message: "有效期开始日期不能为空", trigger: "change" }
        ],
          validToWK: [
          { required: true, message: "有效期结束日期不能为空", trigger: "change" }
        ],
          promotionType: [
          { required: true, message: "promotionType不能为空", trigger: "change" }
        ],
          currencyType: [
          { required: true, message: "currency不能为空", trigger: "change" }
        ],
          sellingPrice: [
          { required: true, message: "sellingPrice不能为空", trigger: "change" }
        ]
      },
      selectedWeek: '',
      countryOptions: [],
      customerOptions: [],
      yearOfWeeksOptions: [
        {weekId:"1"}, {weekId:"2"}, {weekId:"3"}, {weekId:"4"}, {weekId:"5"}, {weekId:"6"}, {weekId:"7"}, {weekId:"8"}, {weekId:"9"}, {weekId:"10"}, {weekId:"11"}, {weekId:"12"}, {weekId:"13"}, {weekId:"14"}, {weekId:"15"}, {weekId:"16"}, {weekId:"17"}, {weekId:"18"}, {weekId:"19"}
         ,{weekId:"20"},{weekId:"21"},{weekId:"22"},{weekId:"23"},{weekId:"24"},{weekId:"25"},{weekId:"26"},{weekId:"27"},{weekId:"28"},{weekId:"29"},{weekId:"30"},{weekId:"31"},{weekId:"32"},{weekId:"33"},{weekId:"34"},{weekId:"35"},{weekId:"36"},{weekId:"37"},{weekId:"38"},{weekId:"39"},{weekId:"40"}
         ,{weekId:"41"},{weekId:"42"},{weekId:"43"},{weekId:"44"},{weekId:"45"},{weekId:"46"},{weekId:"47"},{weekId:"48"},{weekId:"49"},{weekId:"50"},{weekId:"51"},{weekId:"52"}
      ],
      newTekCurrencyCodeList: [],
      newTekPromotionTypeList: [],
      modelOptions: [],
      disposeOptions: [],
      categoryOptions: [],
      productSKUParams: {
        categoryId: undefined,
        modelId: undefined,
        disposeId: undefined,
        channelType: undefined,
        countryName: undefined
      },
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
        url: process.env.VUE_APP_BASE_API + "/tek/newTekMsrpWk/importData"
      },
    };
  },
     mounted () {
            listCurrencyCode().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.newTekCurrencyCodeList.push({
                    id: item.id,
                    value: item.currencyCode
                  })
                })
              }
            })
                  listProductModel().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
               listCountryCodeForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.countryOptions.push({
                    id: item.id,
                    value: item.chineseName
                  })
                })
              }
            })
                listCustomerForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.newTekCustomerList.push({
                    id: item.id,
                    value: item.customerName
                  })
                })
              }
            })
          },
  created() {
    this.getMsrpWKList();
    this.getModelListForDispose();
    this.getPromotionTypeList();
     this.getCategoryListForProduct();
    this.getModelListForProduct();
    this.getDisposeListForProduct();
  },
  methods: {

      /** 查询周零售价列表 */
      getMsrpWKList() {
      this.loading = true;
      listMsrpWK(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.newTekMsrpWkList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    getPromotionTypeList(){
      this.loading = true;
      listPromotionType().then(
        response => {
          if(response.code === 200){
            this.newTekPromotionTypeList = response.rows;
            this.loading = false;
          }
        }
      );
    },

    /** 通过选择country自动带出currency */ 
     getCurrencyCodeByCountry(data){
      listCurrencyCode(data).then(res => {
              if (res.code === 200) {
                this.$set(this.form,'currencyType',res.data[0].currencyCode)
              }
            })
  },
       /** 查询所有客户名称给select框用 */
      getModelListForDispose(data) {
      this.loading = true;
      listCustomer(data).then(
        response => {
          this.customerOptions = response.data;
          this.loading = false;
        }
      );
    },
          /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearch (queryString, cb) {
          const newTekCurrencyCodeList = this.newTekCurrencyCodeList
          const results = queryString
            ? newTekCurrencyCodeList.filter(this.createFilter(queryString))
            : newTekCurrencyCodeList
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
    createFilter (queryString) {
          return currencyType => {
            return (
           currencyType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
            )
          }
        },
        querySearchSku (queryString, cb) {
          const tblGoodsList = this.tblGoodsList
          const results = queryString
            ? tblGoodsList.filter(this.createFilterSku(queryString))
            : tblGoodsList
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
          createFilterSku (queryString) {
                return sku => {
                  return (
                sku.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
                  )
                }
              },
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
               querySearchCustomer (queryString, cb) {
                  const newTekCustomerList = this.newTekCustomerList
                  const results = queryString
                    ? newTekCustomerList.filter(this.createFilterCustomer(queryString))
                    : newTekCustomerList
                  // 调用 callback 返回建议列表的数据
                  cb(results)
              },
                createFilterCustomer (queryString) {
                return customerName => {
                  return (
                customerName.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
                  )
                }
              }, 
        /* 跳转并传值 */
        handleSelect (item) {
         
        },

        getCustomerFromCountry(data){
            listCustomer(data).then(res => {
              if (res.code === 200) {
                this.newTekCustomerList = [];
                res.data.forEach(item => {
                  this.newTekCustomerList.push({
                    id: item.id,
                    value: item.customerName
                  })
                })
              }
            })
          },
          
          handleSelectCountry (item) {
            // this.form.customerName = '';
            // if(this.form.customerName !== '' || this.form.customerName !== undefined){
            //   this.form.customerName = '';
            // }
          var country = item.value.split('_')[0];
          this.getCurrencyCodeByCountry(country);
          this.getCustomerFromCountry(country);

              this.productSKUParams.countryName = country;
              listProductModel(this.productSKUParams).then(res => {
              if (res.code === 200) {
                 this.tblGoodsList = [];
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
        },
        handleIconClick (ev) {
        },

        handleSelectSKU(item) {
          this.getCountryBySKU(item.value);
        },
          handleSelectCustomer(item) {
          if(this.form.country === undefined || this.form.country === ''){
            alert('请先选择Country');
            this.form.customerName = '';
          }
        },

      /** 通过选择SKU自动带出Country和Currency */ 
     getCountryBySKU(data){
                listCountryCode(data).then(res => {
                  if (res.code === 200) {
                    res.data.forEach(item => {
                      this.countryOptions = [];
                      this.countryOptions.push({
                        id: item.id,
                        value: item.chineseName
                      })
                    })
                  }
                })
      },

    getAllCategoryForChange(data){
   this.form.modelId = ''
   if(this.form.disposeId != undefined){
        this.form.disposeId = ''
    }
      this.getModelListForProduct(data);
      this.getDisposeListForProduct(data+"_1");
              this.productSKUParams.categoryId = data;
              listProductModel(this.productSKUParams).then(res => {
              if (res.code === 200) {
                this.tblGoodsList = [];
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
  },

  getAllModelForChange(data){
    // if(this.form.disposeId != undefined){
    //     this.form.disposeId = ''
    // }
      // this.getDisposeListForProduct(data);
      this.$forceUpdate();
              this.productSKUParams.modelId = data;
              listProductModel(this.productSKUParams).then(res => {
              if (res.code === 200) {
                 this.tblGoodsList = [];
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
  },

  
  getAllDisposeForChange(data){
  //  this.form.disposeId = data;
      // this.getDisposeListForProduct(data);

            this.productSKUParams.disposeId = data;
              listProductModel(this.productSKUParams).then(res => {
              if (res.code === 200) {
                 this.tblGoodsList = [];
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
  },
    getChannelTypeForChange(data){
            this.productSKUParams.channelType = data;
              listProductModel(this.productSKUParams).then(res => {
              if (res.code === 200) {
                 this.tblGoodsList = [];
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
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
      listModelForCategoryId(data).then(
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

     /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
         this.getMsrpWKList();
      // this.theWeek();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
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
          return delMsrpWK(ids);
        }).then(() => {
          this.getMsrpWKList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },

        /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMsrpWK(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getMsrpWKList();
              }
            });
          } else {
               addMsrpWk(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                 this.getMsrpWKList();
              }
            });
          }
        }
      });
    },

       // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

     /** 新增按钮操作 */
    handleAdd() {
       this.tblGoodsList = [];
       this.countryOptions = [];
       this.newTekCustomerList = [];
            listProductModel().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
            listCountryCodeForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.countryOptions.push({
                    id: item.id,
                    value: item.chineseName
                  })
                })
              }
            })
            listCustomerForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.newTekCustomerList.push({
                    id: item.id,
                    value: item.customerName
                  })
                })
              }
            })
      this.reset();
      this.open = true;
      this.title = "添加Selling Price信息";
    },

     /** 修改按钮操作 */
    handleUpdate(row) {
       this.tblGoodsList = [];
       this.countryOptions = [];
       this.newTekCustomerList = [];
            listProductModel().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.tblGoodsList.push({
                    id: item.id,
                    value: item.sku
                  })
                })
              }
            })
            listCountryCodeForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.countryOptions.push({
                    id: item.id,
                    value: item.chineseName
                  })
                })
              }
            })
            listCustomerForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.newTekCustomerList.push({
                    id: item.id,
                    value: item.customerName
                  })
                })
              }
            })
      this.reset();
      const id = row.id || this.ids
      getMsrpWK(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改Selling Price信息";
      });
    },

      // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
       // 表单重置
    reset() {
      this.form = {
        id: undefined,
        remark: undefined,
      };
      this.resetForm("form");
    },
       /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有Selling Price数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSellingPrice(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },

           /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "Selling Price导入";
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
    }

  }
}
</script>

<style>
  /* .el-row {
    margin-bottom: 30px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 72px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  } */


</style>