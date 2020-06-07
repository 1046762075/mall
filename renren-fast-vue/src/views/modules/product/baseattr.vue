<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <category @tree-node-click="treenodeclick"></category>
    </el-col>
    <el-col :span="18">
      <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
          <el-form-item>
            <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataList()">查询</el-button>
            <el-button type="success" @click="getAllDataList()">查询全部</el-button>
            <el-button
              v-if="isAuth('product:attr:save')"
              type="primary"
              @click="addOrUpdateHandle()"
            >新增</el-button>
            <el-button
              v-if="isAuth('product:attr:delete')"
              type="danger"
              @click="deleteHandle()"
              :disabled="dataListSelections.length <= 0"
            >批量删除</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandle"
          style="width: 100%;"
        >
          <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
          <el-table-column prop="attrId" header-align="center" align="center" label="id"></el-table-column>
          <el-table-column prop="attrName" header-align="center" align="center" label="属性名"></el-table-column>
          <el-table-column
            v-if="attrtype == 1"
            prop="searchType"
            header-align="center"
            align="center"
            label="可检索"
          >
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.searchType==1"></i>
              <i class="el-icon-error" v-else></i>
            </template>
          </el-table-column>
          <el-table-column prop="valueType" header-align="center" align="center" label="值类型">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.valueType==0">单选</el-tag>
              <el-tag v-else>多选</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="icon" header-align="center" align="center" label="图标">
            <template slot-scope="scope">
              <!-- 自定义表格+自定义图片 -->
              <img :src="scope.row.logo" style="width: 60px; height: 60px" />
            </template>
          </el-table-column>
          <el-table-column prop="valueSelect" header-align="center" align="center" label="可选值">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">
                  <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">
                    {{i}}
                    <br />
                  </span>
                </div>
                <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="enable" header-align="center" align="center" label="启用">
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.enable==1"></i>
              <i class="el-icon-error" v-else></i>
            </template>
          </el-table-column>
          <el-table-column prop="catelogName" header-align="center" align="center" label="所属分类"></el-table-column>
          <el-table-column
            v-if="attrtype == 1"
            prop="groupName"
            header-align="center"
            align="center"
            label="所属分组"
          ></el-table-column>
          <el-table-column
            v-if="attrtype == 1"
            prop="showDesc"
            header-align="center"
            align="center"
            label="快速展示"
          >
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.showDesc==1"></i>
              <i class="el-icon-error" v-else></i>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.attrId)">修改</el-button>
              <el-button type="text" size="small" @click="deleteHandle(scope.row.attrId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update
          :type="attrtype"
          v-if="addOrUpdateVisible"
          ref="addOrUpdate"
          @refreshDataList="getDataList"
        ></add-or-update>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import Category from "../common/category";
import AddOrUpdate from "./attr-add-or-update";
export default {
  //import引入的组件需要注入到对象中才能使用
  components: { Category, AddOrUpdate },
  props: {
    attrtype: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      catId: 0,
      type: 1,
      dataForm: {
        key: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    };
  },
  activated() {
    this.getDataList();
  },
  methods: {
    //感知树节点被点击
    treenodeclick(data, node, component) {
      if (node.level == 3) {
        this.catId = data.catId;
        this.getDataList(); //重新查询
      }
    },
    getAllDataList() {
      this.catId = 0;
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let type = this.attrtype == 0 ? "sale" : "base";
      this.$http({
        url: this.$http.adornUrl(`/product/attr/${type}/list/${this.catId}`),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key
        })
      })
        .then(({ data }) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list;
            this.totalPage = data.page.totalCount;
          } else {
            this.dataList = [];
            this.totalPage = 0;
          }
          this.dataListLoading = false;
        })
        .catch(() => {});
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map(item => {
            return item.attrId;
          });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/attr/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.getDataList();
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        })
        .catch(() => {});
    }
  }
};
</script>
<style scoped>
</style>