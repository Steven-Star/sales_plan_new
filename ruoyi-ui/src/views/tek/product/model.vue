<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="modelName" prop="modelName">
        <el-input
          v-model="queryParams.modelName"
          placeholder="请输入产品系列名称"
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
          v-hasPermi="['product:model:add']"
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
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:model:export']"
        >export</el-button>
      </el-col>
    </el-row>

    <!-- 列表框展示 -->
      <el-table v-loading="loading" :border="true" :data="newTekModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable header-align="center" align="center" label="modelId" prop="id" width="150" />
      <el-table-column sortable header-align="center" align="center" label="modelName" prop="modelName" :show-overflow-tooltip="true" width="180" />
       <el-table-column sortable header-align="center" align="center" label="categoryName"  prop="categoryName" width="180" />
      <el-table-column sortable header-align="center" align="center" label="modelSortId"  prop="modelSortId" width="180" />
      <el-table-column sortable header-align="center" align="center" label="createTime"  prop="createTime" width="180" />
      <el-table-column sortable header-align="center" align="center" label="updateTime"  prop="updateTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:model:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:model:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getModelList"
    />

    <!-- 添加或修改产品系列对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
         <el-form-item label="categoryName" prop="categoryId"> 
          <el-select v-model="form.categoryId" placeholder="请选择产品品类名称">
            <!-- <el-option value="默认" selected>默认</el-option> -->
           <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id">
       </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="modelName" prop="modelName">
          <el-input v-model="form.modelName" placeholder="请输入产品系列名称" />
        </el-form-item>
        <el-form-item label="modelSortId" prop="modelSortId">
          <el-input-number v-model="form.modelSortId" controls-position="right" :min="1" :max="50"></el-input-number>
        </el-form-item>
        <el-form-item label="备注">
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
import { listModel,addModel,getModel,updateModel,delModel,listCategory,exportModel} from "@/api/tek/model/model";

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
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "1",
          label: "全部数据权限"
        },
        {
          value: "2",
          label: "自定数据权限"
        },
        {
          value: "3",
          label: "本部门数据权限"
        },
        {
          value: "4",
          label: "本部门及以下数据权限"
        },
        {
          value: "5",
          label: "仅本人数据权限"
        }
      ],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        modelName: undefined
      },
      // 表单参数
      form: {
          modelSortId: undefined,
          categoryId: undefined,
          modelName: undefined,
          remark: undefined

      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
         categoryId: [
          { required: true, message: "产品品类名称不能为空", trigger: "change" }
        ],
        modelName: [
          { required: true, message: "产品系列名称不能为空", trigger: "change" }
        ],
        modelSortId: [
          { required: true, message: "产品系列排序不能为空", trigger: "change" }
        ]
      },
      newTekModelList: [],
      options: []
    };
  },
  created() {
    this.getCategoryListForModel();
    this.getModelList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询产品系列列表 */
      getModelList() {
      this.loading = true;
      listModel(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.newTekModelList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

      /** 查询所有产品品类给select框用 */
      getCategoryListForModel() {
      this.loading = true;
      listCategory().then(
        response => {
          this.options = response.data;
          this.loading = false;
        }
      );
    },



    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
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
      this.getModelList();
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
      // this.getMenuTreeselect();
      this.open = true;
      this.title = "添加产品系列";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品系列";
      });
    },
    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.reset();
      this.$nextTick(() => {
        this.getRoleDeptTreeselect(row.roleId);
      });
      getRole(row.roleId).then(response => {
        this.form = response.data;
        this.openDataScope = true;
        this.title = "分配数据权限";
      });
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
            addModel(this.form).then(response => {
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
    /** 提交按钮（数据权限） */
    submitDataScope: function() {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          if (response.code === 200) {
            this.msgSuccess("修改成功");
            this.openDataScope = false;
            this.getList();
          }
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const modelName = row.modelName;
      this.$confirm('是否确认删除产品系列名称为"' + modelName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delModel(ids);
        }).then(() => {
          this.getModelList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有Series数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportModel(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>

