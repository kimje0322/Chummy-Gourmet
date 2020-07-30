<template>
  <div id="app">
    <v-app>
      <v-main>
        <v-card class="overflow-hidden mx-auto" height="667" max-width="375">
          <!-- <v-app-bar color="deep-blue accent-4" dense dark>
            <v-toolbar-title style="margin:auto;">돈독한 미식가</v-toolbar-title>
          </v-app-bar>-->
          <v-bottom-navigation
            v-if="!$route.meta.navbar"
            scroll-target="#scroll-area-2"
            hide-on-scroll
            scroll-threshold="500"
            absolute
            color="white"
            horizontal
          >
            <v-btn class="nav-btn" text color="deep-purple accent-4">
              <router-link to="/map">
                <v-icon>fas fa-search</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/party">
                <v-icon>fas fa-plus</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/feed/main">
                <v-icon>fas fa-list</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/user/info">
                <v-icon>fas fa-user</v-icon>
              </router-link>
            </v-btn>
          </v-bottom-navigation>
          <v-sheet
            v-if="$route.name === 'Login'"
            id="scroll-area-1"
            class="overflow-y-auto"
            max-height="667"
          >
            <v-container class="home-padding" style="height: 100%;">
              <router-view></router-view>
            </v-container>
          </v-sheet>
          <!-- home.vue 이외 -->
          <v-sheet v-if="$route.name !== 'Home'"  id="scroll-area-1" class="overflow-y-auto" max-height="610">
            <v-container class="nothome" style="height: 100%;">
              <router-view></router-view>
            </v-container>
          </v-sheet>
          <!-- home -->
          <v-sheet
            v-if="$route.name === 'Home'"
            id="scroll-area-1"
            class="overflow-y-auto"
            max-height="667"
          >
            <v-container class="home-padding" style="height: 100%;">
              <router-view></router-view>
            </v-container>
          </v-sheet>
          <!-- Login -->
        </v-card>
      </v-main>
    </v-app>
  </div>
</template>
<script>
const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";
import "./components/css/style.scss";
import axios from "axios";

export default {
  name: "app",
  methods: {
    
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
.home-padding {
  padding: 0px !important;
}
</style>