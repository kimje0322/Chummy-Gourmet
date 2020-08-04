<template>
  <v-layout>
    <v-toolbar dark>
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
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">{{user.userName}}</v-list-item-title><br>
            <v-list-item-subtitle>{{user.userEmail}}</v-list-item-subtitle>
          </v-list-item-content>
          <!-- <v-list-item-action>
            <v-icon>mdi-menu-down</v-icon>
          </v-list-item-action> -->
        </v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list nav dense>
        <v-list-item-group>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Meet Up(x)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-star</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>favorite(x)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-account-plus</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>팔로우요청(x)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-folder</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>스크랩(x)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="updateUser">
            <v-list-item-icon>
              <v-icon>mdi-account-box</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>회원정보 설정(o)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="logout">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>로그아웃(o)</v-list-item-title>
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

export default {
  data () {
    return {
      user:{},
      drawer: null,
    }
  },
  methods:{
    updateUser(){
      this.$router.push('/user/updateUser');
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