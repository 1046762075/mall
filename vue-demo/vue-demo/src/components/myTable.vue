<template>
  <div>
    <el-table :data="tableData">
      <el-table-column prop="date" label="日期" width="140"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: "",
  data() {
    const item = {
        date: '2020-05-20',
        name: 'fire',
        address: '长沙市雨花区金沙江路 245 路'
      };
      return {
        tableData: Array(20).fill(item)
      }
  },
  components: {},
  computed: {},
  beforeMount() {},
  mounted() {},
  methods: {},
  watch: {}
};
</script>
<style scoped>
</style>
