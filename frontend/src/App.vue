<template>
  <div id="app">
    <v-app>
      <v-main>
        <v-card class="overflow-hidden mx-auto" height="667">
          <!-- <v-app-bar color="deep-blue accent-4" dense dark>
            <v-toolbar-title style="margin:auto;">돈독한 미식가</v-toolbar-title>
          </v-app-bar>-->
          <!-- class="navbar-static-bottom" -->
          
          <v-bottom-navigation
            class="navbar-fixed-bottom"
            v-if="!$route.meta.navbar"
            scroll-target="#scroll-area-2"
            hide-on-scroll
            scroll-threshold="500"

            absolute
            color="white"
            horizontal
          >
            <v-btn class="nav-btn" @click="gotomap" text color="orange accent-4">
                <!-- <v-icon>fas fa-search</v-icon> -->
                <v-icon>mdi-map-marker-radius</v-icon>
            </v-btn>

            <v-btn @click="gotonewsfeed" text color="orange accent-4">
                <v-icon>mdi-history</v-icon>
            </v-btn>

            <v-btn @click="gotofeedadd" text color="orange accent-4">
              <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages"> -->
              <!-- <v-btn type="button" @click="onClickImageUpload"> -->
              <!-- </v-btn> -->  
                <v-icon>fas fa-plus</v-icon>
            </v-btn>

            <v-btn @click="gotocurator" text color="orange accent-4">
                <!-- <v-icon>fas fa-list</v-icon> -->
                <v-icon>mdi-magnify</v-icon>
            </v-btn>

            <v-btn @click="gotouserinfo" text color="orange accent-4">
                <v-icon>fas fa-user</v-icon>
            </v-btn>
          </v-bottom-navigation>

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
          
          <!-- home, login 이외 -->
          <v-sheet v-if="$route.name !== 'Home'"  id="scroll-area-1" class="overflow-y-auto" max-height="100%">
            <v-container class="nothome" style="height: 100%;">
              <router-view></router-view>
            </v-container>
          </v-sheet>
          
        </v-card>
      </v-main>
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
 #app {
  min-height: 100%; 
  position: relative;
} 
.navbar-fixed-bottom {
  padding-left: 12px;
  position: absolute;
  bottom: 0;
} 
.home-padding {
  padding: 0px !important;
}

/* .nav-bar {
  overflow: hidden;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;  
  height: 75px;
  padding: 0.5rem;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
} */

/* .nav-css{
  height: 100%;
} */

</style>