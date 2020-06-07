<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-form-item label="优惠卷类型" prop="couponType">
        <el-select v-model="dataForm.couponType" placeholder="请选择">
          <el-option label="全场赠券" :value="0"></el-option>
          <el-option label="会员赠券" :value="1"></el-option>
          <el-option label="购物赠券" :value="2"></el-option>
          <el-option label="注册赠券" :value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="优惠券图片" prop="couponImg">
        <single-upload v-model="dataForm.couponImg"></single-upload>
      </el-form-item>
      <el-form-item label="优惠卷名字" prop="couponName">
        <el-input v-model="dataForm.couponName" placeholder="优惠卷名字"></el-input>
      </el-form-item>
      <el-form-item label="数量" prop="num">
        <el-input-number :min="0" v-model="dataForm.num"></el-input-number>
      </el-form-item>
      <el-form-item label="金额" prop="amount">
        <el-input-number :min="0" v-model="dataForm.amount" :precision="2"></el-input-number>
      </el-form-item>
      <el-form-item label="每人限领张数" prop="perLimit">
        <el-input-number :min="0" v-model="dataForm.perLimit"></el-input-number>
      </el-form-item>
      <el-form-item label="使用门槛（最小积分）" prop="minPoint">
        <el-input-number :min="0" v-model="dataForm.minPoint"></el-input-number>
      </el-form-item>
      <el-form-item label="有效时间" prop="useTimeRange">
        <el-date-picker
          v-model="dataForm.useTimeRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="使用类型" prop="useType">
        <el-select v-model="dataForm.useType" placeholder="请选择">
          <el-option :value="0" label="全场通用"></el-option>
          <el-option :value="1" label="指定分类"></el-option>
          <el-option :value="2" label="指定商品"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="dataForm.note" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item label="发行数量" prop="publishCount">
        <el-input-number v-model="dataForm.publishCount" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="领取日期" prop="enableStartTime">
        <el-date-picker
          v-model="dataForm.timeRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="优惠码" prop="code">
        <el-input v-model="dataForm.code" placeholder="优惠码"></el-input>
      </el-form-item>
      <el-form-item label="领取所需等级" prop="memberLevel">
        <el-select v-model="dataForm.memberLevel" placeholder="请选择">
          <el-option :value="0" label="不限制"></el-option>
          <el-option
            v-for="item in memberLevels"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SingleUpload from "@/components/upload/singleUpload";
export default {
  components: { SingleUpload },
  data() {
    return {
      visible: false,
      memberLevels: [],
      dataForm: {
        id: 0,
        couponType: "",
        couponImg: "",
        couponName: "",
        num: "",
        amount: "",
        perLimit: "",
        minPoint: "",
        startTime: "",
        endTime: "",
        useType: "",
        note: "",
        publishCount: "",
        useCount: "",
        receiveCount: "",
        enableStartTime: "",
        enableEndTime: "",
        code: "",
        memberLevel: "",
        publish: 0,
        timeRange: [],
        useTimeRange:[]
      },
      dataRule: {
        couponType: [
          {
            required: true,
            message:
              "优惠卷类型不能为空",
            trigger: "blur"
          }
        ],
        couponImg: [
          { required: true, message: "优惠券图片不能为空", trigger: "blur" }
        ],
        couponName: [
          { required: true, message: "优惠卷名字不能为空", trigger: "blur" }
        ],
        num: [{ required: true, message: "数量不能为空", trigger: "blur" }],
        amount: [{ required: true, message: "金额不能为空", trigger: "blur" }],
        perLimit: [
          { required: true, message: "每人限领张数不能为空", trigger: "blur" }
        ],
        minPoint: [
          { required: true, message: "使用门槛不能为空", trigger: "blur" }
        ],
        useType: [
          {
            required: true,
            message: "使用类型不能为空",
            trigger: "blur"
          }
        ],
        note: [{ required: true, message: "备注不能为空", trigger: "blur" }],
        publishCount: [
          { required: true, message: "发行数量不能为空", trigger: "blur" }
        ],
        enableStartTime: [
          {
            required: true,
            message: "可以领取的开始日期不能为空",
            trigger: "blur"
          }
        ],
        enableEndTime: [
          {
            required: true,
            message: "可以领取的结束日期不能为空",
            trigger: "blur"
          }
        ],
        code: [{ required: true, message: "优惠码不能为空", trigger: "blur" }],
        memberLevel: [
          {
            required: true,
            message: "可以领取的会员等级不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getMemberLevels();
  },
  methods: {
    getMemberLevels() {
      //获取所有的会员等级
      this.$http({
        url: this.$http.adornUrl("/member/memberlevel/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500
        })
      }).then(({ data }) => {
        this.memberLevels = data.page.list;
      });
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/coupon/coupon/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.couponType = data.coupon.couponType;
              this.dataForm.couponImg = data.coupon.couponImg;
              this.dataForm.couponName = data.coupon.couponName;
              this.dataForm.num = data.coupon.num;
              this.dataForm.amount = data.coupon.amount;
              this.dataForm.perLimit = data.coupon.perLimit;
              this.dataForm.minPoint = data.coupon.minPoint;
              this.dataForm.startTime = data.coupon.startTime;
              this.dataForm.endTime = data.coupon.endTime;
              this.dataForm.useType = data.coupon.useType;
              this.dataForm.note = data.coupon.note;
              this.dataForm.publishCount = data.coupon.publishCount;
              this.dataForm.useCount = data.coupon.useCount;
              this.dataForm.receiveCount = data.coupon.receiveCount;
              this.dataForm.enableStartTime = data.coupon.enableStartTime;
              this.dataForm.enableEndTime = data.coupon.enableEndTime;
              this.dataForm.code = data.coupon.code;
              this.dataForm.memberLevel = data.coupon.memberLevel;
              this.dataForm.publish = data.coupon.publish;
              this.dataForm.timeRange = [
                this.dataForm.startTime,
                this.dataForm.endTime
              ];
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/coupon/coupon/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              couponType: this.dataForm.couponType,
              couponImg: this.dataForm.couponImg,
              couponName: this.dataForm.couponName,
              num: this.dataForm.num,
              amount: this.dataForm.amount,
              perLimit: this.dataForm.perLimit,
              minPoint: this.dataForm.minPoint,
              startTime: this.dataForm.useTimeRange[0],
              endTime: this.dataForm.useTimeRange[1],
              useType: this.dataForm.useType,
              note: this.dataForm.note,
              publishCount: this.dataForm.publishCount,
              useCount: this.dataForm.useCount,
              receiveCount: this.dataForm.receiveCount,
              enableStartTime: this.dataForm.timeRange[0],
              enableEndTime: this.dataForm.timeRange[1],
              code: this.dataForm.code,
              memberLevel: this.dataForm.memberLevel,
              publish: this.dataForm.publish
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>
