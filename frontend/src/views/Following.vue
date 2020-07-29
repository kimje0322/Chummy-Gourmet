<template>
  <div>
    <v-card>
    <v-tabs
      background-color="orange"
      center-active
      dark
    >
      <v-tab class="following">팔로잉</v-tab>
      <v-tab>팔로우</v-tab>
    </v-tabs>
  </v-card>
    <div class="following-page">
      
    <!-- <h3>팔로잉 페이지</h3> -->
      <ul>
    <!-- 마지막 변수명 나오는 거 해결하기 -->
        <li v-for="nickname in followingUser.nicknames" :key="nickname">
        <v-icon class="usericon">mdi-account-circle</v-icon>
        <span>{{ nickname }}</span>
        <v-btn small class="following-btn">팔로잉</v-btn>
      </li>
    
      <!-- key값 해결하기 -->
      <!-- <li v-for="name in followingUser.names">{{ name }}</li> -->
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Following',
  data() {
    return {
    followingUser: {
      names: [],
      nicknames: [],
      }
    }
  },
  created() {
    // var followingUser = {
    this.followingUser.names = this.$route.params.name,
    this.followingUser.nicknames = this.$route.params.nickname,
    // }
    console.log(this.$route.params)
    console.log(this.followingUser.names)
  }
}
</script>

<style>
  .following {
    padding: 0 50px 0 50px;
  }
  .container {
  /* height: 50%; */
  padding: 0px !important; 
}
  .following-page {
    padding: 12px
  }
  .usericon {
    padding-right: 5px;
  }
  .following-btn {
   margin-left: 25px;
  }
</style>