<template>
  <div class="home">
    <div class="welcome-word">
      <p>
        {{userInfo.name || '游客'}}, 您好
      </p>
      <p>欢迎使用租球系统</p>
    </div>
  </div>
</template>

<script>
  import InforCard from '_c/info-card'
  import CountTo from '_c/count-to'
  import { ChartPie, ChartBar } from '_c/charts'
  import Example from './example.vue'
  export default {
    name: 'home',
    components: {
      InforCard,
      CountTo,
      ChartPie,
      ChartBar,
      Example
    },
    data () {
      return {
      }
    },
    computed: {
      userInfo() {
        return this.$store.state.user.userIdentity;
      }
    },
    mounted () {
      //
    }
  }
</script>

<style lang="less">
  .home {
    width: 100%;
    height: 100%;
    background-image: url('../../../assets/images/home.jpg');
    background-size: cover;
    background-position: center;
    text-align: center;
    padding-top: 50px;

    .welcome-word {
      margin: 0 auto;
      font-weight: bold;
      font-size: 30px;
      color: #fff;
    }
  }
  .count-style{
    font-size: 50px;
  }
</style>
