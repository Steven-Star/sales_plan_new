<template>
<div>
      <el-form :model="queryParams" ref="queryForm" :inline="true">
            <el-form-item label="Country" prop="country">
           <el-autocomplete
            class="inline-input"
            v-model="queryParams.country"
            clearable
            :fetch-suggestions="querySearchCountry"
            placeholder="input Country"
            :trigger-on-focus="true" 
            @select="handleSelect"
            @keyup.enter.native="search"
          ></el-autocomplete>
        </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

        <el-calendar id="calendar">
        <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
        <template
          slot="dateCell"
          slot-scope="{date, data}">
          <!-- <p :class="data.isSelected ? 'is-selected' : ''" style="margin: -5px;">
            {{ data.day.split('-').slice(1).join('-') }} 
            {{ data.isSelected ? '√' : ''}}
          </p> -->
          <div class="date-cell" :class="data.isSelected ? 'is-selected' : ''" style="margin: -5px;">
                        <div class="calendar-day">
                          {{
                          data.day
                          .split('-')
                          .slice(1)
                          .join('-')
                          }}
                        </div>
          </div>
                <div v-for="item in calendarData" :key="item.id">
                        <div v-if="(item.validFromWK).indexOf(data.day.split('-').slice(1)[0])!=-1">
                          <div v-if="(item.validFromWK).indexOf(data.day.split('-').slice(2).join('-'))!=-1">
                              <div v-if="(item.validFromWK) == data.day">
                                <el-tooltip class="item" effect="dark" :content="item.promotionType" placement="top-end">
                                    <div @click="aa(item.validFromWK,item.promotionType)" style="margin: 0px; margin-left: 70px;font-size: 8px;" class="is-selected">{{item.promotionType}}</div>
                                </el-tooltip>
                              </div>
                            </div>
                          <div v-else></div>
                        </div>
                    <div v-else></div>
                  </div>
        </template>
        </el-calendar>


    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-table :data="getPromotionTypeList">
        <el-table-column property="modelName" label="model#" width="150"></el-table-column>
        <el-table-column property="sku" label="SKU" width="300"></el-table-column>
        <el-table-column property="country" label="Country" width="200"></el-table-column>
        <el-table-column property="customerName" label="Customer" width="100"></el-table-column>
        <el-table-column property="promotionType" label="PromotionType" width="150"></el-table-column>
        <el-table-column property="sellingPrice" label="Price" width="120"></el-table-column>
        <el-table-column property="currencyType" label="Currency" width="100"></el-table-column>
      </el-table>
    </el-dialog>

</div>
</template>

<script>

import { listCountryCodeForPermission,listSpecialEvent,addSpecialEvent,updateSpecialEvent,delSpecialEvent,getAllSpecialEvent,listCountryCode,listPromotionTypeByDate } from "@/api/tek/specialEvent/specialEvent";


export default {
  data() {
    return {
    // 表单参数
      form: {},
    // 弹出层标题
      title: "",
    // 是否显示弹出层
      open: false,
        // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
       country: undefined
      },
         // 表单校验
      // rules: {
      //   modelName: [
      //     { required: true, message: "产品系列名称不能为空", trigger: "blur" }
      //   ]
      // },
      calendarData: [],
      value: '',
      countryOptions: [],
      getPromotionTypeList: []
    };
  },
       mounted () {
               listCountryCodeForPermission().then(res => {
              if (res.code === 200) {
                res.data.forEach(item => {
                  this.countryOptions.push({
                    id: item.id,
                    value: item.chineseName
                  })
                })
                  this.queryParams.country = this.countryOptions[0].value;
              }
            })
          },
  created() {
      this.getSpecialEventList();
  },

  methods: {
 /** 查询特殊事件列表 */
      getSpecialEventList() {
      this.loading = true;
      const query = this.queryParams.country;
      getAllSpecialEvent(query).then(
        response => {
          this.calendarData = response.data;
          this.loading = false;
        }
      );
    },

      /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

      /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getSpecialEventList();
    },

     // 表单重置
    reset() {
      this.form = {
        id: undefined,
        modelName: undefined,
        deptIds: [],
        remark: undefined
      };
      this.resetForm("form");
    },
       /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateModel(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getModelList();
              }
            });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            addSpecialEvent(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getModelList();
              }
            });
          }
        }
      });
    },
      // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
         /* 跳转并传值 */
        handleSelect (item) {
         
        },
         handleIconClick (ev) {
        },

aa(validFrom,promotionType){
  this.getPromotionTypeListMethod(validFrom,promotionType,this.queryParams.country);
      this.open = true;
      this.title = "查看PromotionType详情";
},

    /** 点击日期查询相关promotionType信息 */
      getPromotionTypeListMethod(validFrom,promotionType,country) {
      this.loading = true;
      listPromotionTypeByDate(validFrom,promotionType,country).then(
        response => {
          this.getPromotionTypeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

  }
  
}



</script>

<style>
  .is-selected {
    color: #1989FA;
  }
</style>
