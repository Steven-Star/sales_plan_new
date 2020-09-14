<template>
<div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="year" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年份">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

    <div v-if="lastWeekDays == undefined" > 
    <el-row :gutter="10"  v-for="item of (this.allWeeks % 6)" :key="item">
      <el-col :span="4" v-for="item of 6" :key="item"><div class="grid-content bg-purple">WK{{item}}</div></el-col>
    </el-row>
    </div> 


    <div v-if="lastWeekDays != undefined" >
    <el-row :gutter="10"  v-for="(item,index1) of (rows - 1)" :key="item">
      <el-col :span="4" v-for="(item,index) of 6" :key="item"><div @click="insertMsrp((index+1) + ((index1 * 6)))" class="grid-content bg-purple">WK{{(index+1) + ((index1 * 6))}}</div></el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="lastWeekDays" v-for="item of lastWeekDays" :key="item"><div @click="insertMsrp(48 + item)" class="grid-content bg-purple">WK{{48 + item}}</div></el-col>
    </el-row>
    </div>

    <!-- 添加或修改wkMsrp对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="24">
              <el-form-item label="sku" prop="sku">
          <el-select v-model="form.sku" placeholder="请选择产品sku">
            <!-- <el-option value="默认" selected>默认</el-option> -->
           <el-option
            v-for="item in tblGoodsList"
            :key="item.id"
            :label="item.sku"
            :value="item.sku">
       </el-option>
          </el-select>
        </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
         <el-form-item label="customerName" prop="customerId">
          <el-select v-model="form.customerId" placeholder="请选择客户名称">
            <el-option value="默认" selected>默认</el-option>
           <el-option
            v-for="item in newTekCustomerList"
            :key="item.id"
            :label="item.customerName"
            :value="item.id">
       </el-option>
          </el-select>
        </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
        <el-form-item label="WK MSRP" prop="wk1Msrp">
          <el-input v-model="form.wk1Msrp" maxlength="12" oninput="if(isNaN(value)) { value = '' } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}" placeholder="请输入产品周建议零售价，只能为数字"></el-input>
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

  </div>
  
</template>

<script>

import { addMsrp,listProduct,listCustomer,addMsrpWk } from "@/api/tek/msrp/msrp";


export default {
  data() {
    return {
      queryParams: {
        year: undefined
      },
      allWeeks: '',
      lastWeekDays: '',
      rows: 0,
      year: '',
      form: {
        year: ''
      },
      tblGoodsList: [],
      newTekCustomerList: [],
        // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
         // 表单校验
      rules: {
        productId: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
         customerId: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
          wk1Msrp: [
          { required: true, message: "产品周建议零售价不能为空", trigger: "blur" }
        ]
      },
      selectedWeek: ''

    };
  },
  
  created() {
    var weekss = this.theWeek();
    this.allWeeks = weekss;
    this.rows = Math.round(weekss/6);
    this.lastWeekDays =  weekss % 6;
    this.getProductList();
    this.getCustomerList();
  },

  methods: {

     /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      // this.queryParams.year = '2202';
      this.theWeek();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

      /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.selectedWeek = this.selectedWeek;
          this.form.year = this.queryParams.year;
            addMsrpWk(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
               this.theWeek();
              }
            });
          
        }
      });
    },

    insertMsrp(data){
      this.selectedWeek = data;

       this.reset();
      this.open = true;
      this.title = "添加WK MSRP信息";
    },

    theWeek() {
      var years;
      var now = new Date();
      if(this.queryParams.year != undefined){
          years = this.queryParams.year;
      }else{
          years = now.getYear()
          }
          var totalDays = 0;
         years = now.getYear()
          if (years < 1000)
              years += 1900
          var days = new Array(12);
          days[0] = 31;
          days[2] = 31;
         days[3] = 30;
         days[4] = 31;
         days[5] = 30;
         days[6] = 31;
         days[7] = 31;
         days[8] = 30;
         days[9] = 31;
         days[10] = 30;
         days[11] = 31;
         //判断是否为闰年，针对2月的天数进行计算
         if (Math.round(now.getYear() / 4) == now.getYear() / 4) {
             days[1] = 29
         } else {
             days[1] = 28
        }
         if (now.getMonth() == 0) {
             totalDays = totalDays + now.getDate();
         } else {
             var curMonth = now.getMonth();
             for (var count = 1; count <= curMonth; count++) {
                totalDays = totalDays + days[count - 1];
            }
             totalDays = totalDays + now.getDate();
         }

        //算出本年总共多少周
        totalDays = days[0] + days[1] + days[2] + days[3] +days[4]+days[5]+days[6]+days[7]+days[8]+days[9]+days[10]+days[11];
        var week = Math.round(totalDays / 7);
        return week;
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
        productId: undefined,
        customerId: undefined,
        wk1Msrp: undefined,
        deptIds: [],
        remark: undefined,
        // selectedWeek: undefined
      };
      this.resetForm("form");
    },

            /** 查询所有产品给select框用 */
      getProductList() {
      this.loading = true;
      listProduct().then(
        response => {
          this.tblGoodsList = response.data;
          this.loading = false;
        }
      );
    },
    
        /** 查询所有客户给select框用 */
      getCustomerList() {
      this.loading = true;
      listCustomer().then(
        response => {
          this.newTekCustomerList = response.data;
          this.loading = false;
        }
      );
    }

  }
}
</script>

<style>
  .el-row {
    margin-bottom: 30px;
    /* &:last-child {
      margin-bottom: 0;
    } */
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
  }
</style>