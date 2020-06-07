<template>
  <div>
    <h1>你好，Hello，{{name}}</h1>

    <el-radio v-model="radio" label="1">备选项1</el-radio>
    <el-radio v-model="radio" label="2">备选项2</el-radio>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "fireflynay",
      radio: "2"
    };
  }
};
</script>

<style >
</style>
