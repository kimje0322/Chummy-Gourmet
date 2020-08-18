<template>
  <div>
    <!-- <img src="../../assets/images/home-img.png" alt=""> -->
    <!-- <v-parallax src="../../assets/images/home-img.png" width="500"></v-parallax> -->
      <v-col >
        <v-row
          style="margin-top:80%;"
          align="end"
          justify="center"
        >
        <router-link to="/user/join">
          <v-btn class="ma-3 pa-6" color="orange accent-2 white--text">회원가입</v-btn>
        </router-link>
        <router-link to="/login">
          <v-btn class="ma-3 pa-6" color="orange accent-2 white--text">로그인</v-btn>
        </router-link>
        </v-row>
      </v-col>
  </div>
</template>

<script>
// import "../../components/css/home.scss";
export default {};
</script>

<style>
#home {
  /* background-color : red; */
  /* background-position-y: bottom; */
  /* height: 100%; */
  /* background-image: url(../../assets/images/home-img.png); */
/* position: relative; */
/* background-repeat: no-repeat; */
  /* background-size: cover; */
}
</style>