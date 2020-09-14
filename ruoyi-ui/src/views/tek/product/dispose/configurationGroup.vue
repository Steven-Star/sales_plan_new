<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="GroupName" prop="disposeGroupName">
        <el-input
          v-model="queryParams.disposeGroupName"
          placeholder="请输入产品配置组名称"
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
          v-hasPermi="['configuration:group:add']"
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
          v-hasPermi="['configuration:group:export']"
        >export</el-button>
      </el-col>
    </el-row>

    <!-- 列表框展示 -->
      <el-table v-loading="loading" :border="true" height="600" :data="newTekDisposeGroupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable header-align="center" align="center" label="GroupId" prop="id" width="150" />
      <el-table-column sortable header-align="center" align="center" label="ConfigurationGroupName" prop="disposeGroupName" :show-overflow-tooltip="true" width="300" sortable="true"/>
      <el-table-column sortable header-align="center" label="createTime" align="center" prop="createTime" width="250" />
      <el-table-columnvsortable header-align="center" label="updateTime" align="center" prop="updateTime" width="250" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['configuration:group:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['configuration:group:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getDisposeGroupList"
    />

    <!-- 添加或修改产品配置对话框 -->
    <el-dialog v-dialogDrag :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="160px">

        <el-form-item label="disposeGroupName" prop="disposeGroupName">
          <el-input v-model="form.disposeGroupName" placeholder="请输入产品配置组名称" />
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
import { listModel,listCategory,listDisposeGroup,addDisposeGroup,updateDisposeGroup,getDisposeGroup,delDisposeGroup,exportDispose} from "@/api/tek/dispose/disposeGroup";

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
        disposeGroupName: undefined
      },
      // 表单参数
      form: {
        categoryId: undefined,
        modelId: undefined,
        disposeSortId: undefined,
        disposeName: undefined,
        remark: undefined
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        disposeGroupName: [
          { required: true, message: "产品配置组名称不能为空", trigger: "blur" }
        ]
      },
      newTekDisposeGroupList: [],
      categoryOptions: [],
      modelOptions: [],
      changeFlag: false
    };
  },
  created() {
    this.getCategoryListForDispose();
      this.getModelListForDispose();
    this.getDisposeGroupList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询产品配置列表 */
      getDisposeGroupList() {
      this.loading = true;
      listDisposeGroup(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.newTekDisposeGroupList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
       /** 查询所有产品品类给select框用 */
      getCategoryListForDispose() {
      this.loading = true;
      listCategory().then(
        response => {
          this.categoryOptions = response.data;
          this.loading = false;
        }
      );
    },

           /** 查询所有产品系列给select框用 */
      getModelListForDispose(data) {
      this.loading = true;
      listModel(data).then(
        response => {
          this.modelOptions = response.data;
          this.loading = false;
        }
      );
    },
  getAllCategoryForChange(data){
      this.getModelListForDispose(data);
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
        modelName: undefined,
        deptIds: [],
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getDisposeList();
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
      this.title = "添加产品配置组";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDisposeGroup(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品配置组";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDisposeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getDisposeGroupList();
              }
            });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            addDisposeGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getDisposeGroupList();
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
      const disposeGroupName = row.disposeGroupName;
      this.$confirm('是否确认删除产品配置组名称为"' + disposeGroupName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDisposeGroup(ids);
        }).then(() => {
          this.getDisposeGroupList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有Configuration数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDispose(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
<style>
  /* .el-table__header tr,
  .el-table__header th {
    padding: 0;
    height: 40px;
} */
/* .el-table__body tr,
  .el-table__body td {
    padding: 0;
    height: 40px;
} */

</style>