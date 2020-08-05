<template>
  <v-layout>
    <!-- color="orange" -->
    <v-toolbar dark >
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
      <v-spacer></v-spacer>
      <p class="my-auto">MY PAGE</p>
      <v-spacer></v-spacer>
      
      <v-btn @click.stop="drawer = !drawer" depressed>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>
      </v-btn>
    </v-toolbar>
    
    <!-- 햄버거? 눌렀을 때 -->
    <v-navigation-drawer dark v-model="drawer" app right>
      <v-system-bar></v-system-bar>
      <v-list >
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">{{ user.userNickname }}</v-list-item-title><br>
            <v-list-item-subtitle>{{ user.userEmail }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
          <v-btn @click="updateUser" block>
            프로필 수정
          </v-btn>
      </v-list>

      <v-divider></v-divider>

      <v-list nav dense>
        <v-list-item-group>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Meet Up</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-star</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>favorite</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="followRequestList">
            <v-list-item-icon>
              <v-badge v-model="show" color="indigo">
                <span slot="badge">{{user.followingRequestCount}}</span>
                <v-icon>mdi-account-plus</v-icon>
              </v-badge>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>팔로우요청</v-list-item-title>
            </v-list-item-content>
            
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-folder</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>스크랩</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="updateUserInfo">
            <v-list-item-icon>
              <v-icon>mdi-account-box</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>추가정보 설정</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="logout">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>로그아웃</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>

    </v-navigation-drawer>
  </v-layout>
</template>

<script>
import axios from "axios";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";
export default {
  data () {
    return {
      user:{},
      drawer: null,
      show: false,
      badgeData: { value: '!' },
      followCount :"",
    }
  },
  computed: {
  },
  methods:{
    followRequestList(){
      this.$router.push('/user/FollowRequestList');
    },
    updateUser(){
      this.$router.push('/user/updateUser');
    },
    updateUserInfo(){
      this.$router.push('/user/updateUserInfo');
    },
    logout (){
      this.$confirm("로그아웃 하시겠습니까?").then(() => {
      //do something...
      this.$cookie.delete('accesstoken');
      this.$cookie.delete('userId');
      this.$router.push('/');
    });
    }
  },
  created() {
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((res) => {
        this.user = res.data;
        if(this.user.followingRequestCount != 0){
          this.show = true;
        }
      })
  }
};
</script>
<style>
  .my-auto {
      font-family: 'Jua', sans-serif;
  }
  .fa-chevron-left {
    color: white; 
    margin-left: 7px;
  }
</style>