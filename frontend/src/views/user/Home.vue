<template>
  <v-app id="app">
    <div class="user" id="home">
    <!-- <img src="../../assets/images/home-img.png" alt=""> -->
    <!-- <v-parallax src="../../assets/images/home-img.png" width="500"></v-parallax> -->
    
      <div class="home-btns">
        <router-link to="/user/join"><v-btn color="warning" class="join-btn">회원가입</v-btn></router-link>
        <router-link to="/login"><v-btn color="warning">로그인</v-btn></router-link>
      </div>
    </div>
  </v-app>
</template>

<script>
import "../../components/css/home.scss";
export default {

}
</script>

<style>

  .home-btns {
    margin: 500px 0 0 0;
    text-align: center;
  }
  
  .join-btn {
    margin-right: 10px;
    /* border-width: 2px;
    border-color: black; */
  }
</style>