<template>
  <v-app id="app">
    <div class="user" id="home">
    <img class="img-css" src="../../assets/images/background1.png" width="55%" alt="back">
    <!-- <v-parallax src="../../assets/images/home-img.png" width="500"></v-parallax> -->
    
      <div class="home-btns">
        <router-link to="/user/join"><v-btn color="orange accent-2 white--text" class="join-btn">회원가입</v-btn></router-link>
        <router-link to="/login"><v-btn color="orange accent-2 white--text">로그인</v-btn></router-link>
      </div>
    </div>
  </v-app>
</template>

<script>
// import "../../components/css/home.scss";
export default {};
</script>

<style scoped>
.home-btns {
  margin: 85px 0 0 0;
  text-align: center;
}
.join-btn {
  margin-right: 10px;
  /* border-width: 2px;
    border-color: black; */
}

.img-css {
  display: block;
  margin-top: 100px;
  margin-left: auto;
  margin-right: auto;
}

#app{
  background-color: #FFD180
}
</style>