<template>
<div id="app">
  <div       
        style="position: absolute;
            background-color: #FFD180;
            top: 40%;
            left: 50%;
            transform: translate(-50%, -40%);">
      <div class="text-center">
        <img src="../../assets/images/background1.png" width="100%" alt="back">
      </div>

      <div>
        <v-row
          style="margin-top:35%;"
          justify="center"
        >
        <v-btn-toggle style="background-color: #FFD180;">
            <router-link to="/user/join">
              <v-btn  class="ma-2" color="orange accent-2 white--text">회원가입</v-btn>
            </router-link>
            <router-link to="/login">
              <v-btn class="ma-2"  color="orange accent-2 white--text">로그인</v-btn>
            </router-link>
        </v-btn-toggle>
        </v-row>
      </div>
  </div>
  </div>
</template>

<script>
import "../../components/css/home.scss";
export default {};
</script>

<style scoped>
#app {
  background-color: #FFD180;
  position: relative;
  width: 100%;
  height: 100%;
}

.home-btns {
  margin: 85px 0 0 0;
  text-align: center;
}
.join-btn {
  margin-right: 10px;
}

</style>