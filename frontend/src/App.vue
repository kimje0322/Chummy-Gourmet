<template>
  <div class="pa-0 ma-0 mx-auto" id="app" style="max-width:960px; max-height:959px;">
    <v-app>
      <!-- <v-main class="ma-0 pa-0"> -->
          <v-bottom-navigation
            class="navbar-fixed-bottom"
            v-if="!$route.meta.navbar"
            scroll-target="#scroll-area-2"
            hide-on-scroll
            absolute
            color="white"
            horizontal
            grow
          >
            <v-btn class="nav-btn" @click="gotomap" text color="orange accent-4">
                <v-icon class="icon-css">mdi-map-marker-radius</v-icon>
            </v-btn>

            <v-btn @click="gotonewsfeed" text color="orange accent-4">
                <v-icon class="icon-css">mdi-history</v-icon>
            </v-btn>

            <v-btn @click="gotofeedadd" text color="orange accent-4">
                <v-icon class="icon-css">fas fa-plus</v-icon>
            </v-btn>

            <v-btn @click="gotocurator" text color="orange accent-4">
                <v-icon class="icon-css">mdi-magnify</v-icon>
            </v-btn>

            <v-btn @click="gotouserinfo" text color="orange accent-4">
                <v-icon class="icon-css">fas fa-user</v-icon>
            </v-btn>
          </v-bottom-navigation>

          <v-container class="pa-0 mx-auto" style="background-color:bisque;">
            <router-view></router-view>
          </v-container>

      <!-- </v-main> -->
    </v-app>
  </div>
</template>
<script>
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";
import "./components/css/style.scss";
import axios from "axios";
import router from "@/routes";

export default {
  name: "app",
  methods: {
    gotomap() {
      router.push({ path: '/map' }).catch(()=>{});
    },
    gotonewsfeed() {
      router.push({ path: '/newsfeed' }).catch(()=>{});
      
    },
    gotofeedadd() {
      router.push({ path: '/feed/add' }).catch(()=>{});
      
    },
    gotocurator() {
      router.push({ path: '/curator' }).catch(()=>{});
    },
    gotouserinfo() {
      router.push({ path: '/user/info' }).catch(()=>{});

    },
      // onClickImageUpload() {
      //   this.$refs.imageInput.click();
  // },
    
  },
  created() {
    //로그인 유지가 아닐경우
  
    if (
      this.$cookie.get("loginSave") == "false" ||
      this.$cookie.get("loginSave") == null
    ) {
      //저장되어있는 쿠키를 제거한다.
      this.$cookie.delete("accesstoken");
      this.$cookie.delete("userId");
      alert("로그인을 해주세요");
      this.$router.push("/");
    }
    //로그인 유지일 경우
    else {
      //새 토큰을 받아온다.
      axios
        .get(
          `${SERVER_URL}/account/loginSave?email=${this.$cookie.get(
            "useremail"
          )}`
        )

        .then((response) => {
          console.log("로그인페이지");
          console.log(response.data);

          this.$cookie.set("accesstoken", response.data, 1);
          this.$cookie.set("userId", response.data.object.userId, 1);

          this.$router.push("/map");
        })

        .catch((error) => {
          console.log(error.response);
          alert("다시 로그인 해주세요");
          this.$router.push("/");
        });
    }
  }

};
</script>

<style>

</style>