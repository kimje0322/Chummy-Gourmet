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
    <v-navigation-drawer v-model="drawer" temporary absolute right>
      <v-system-bar></v-system-bar>
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title class="title">돈독이</v-list-item-title><br>
            <v-list-item-subtitle>dd@instagram.com</v-list-item-subtitle>
          </v-list-item-content>

          <v-list-item-action>
            <v-icon>mdi-menu-down</v-icon>
          </v-list-item-action>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list nav dense>
        <v-list-item-group v-model="items" color="primary">
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title v-text="item.text"></v-list-item-title>
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
      drawer: null,
      items: [
      { text: 'Meet Up', icon: 'mdi-account-multiple' },
      // { text: 'favorite', icon: 'mdi-star' },
      { text: '팔로우 요청', icon: 'mdi-account-plus' },
      { text: '스크랩', icon: 'mdi-folder' },
      { text: '회원정보 설정', icon: 'mdi-account-box' },
      { text: '로그아웃', icon: 'mdi-logout' },
    ],
    }
  },
  created() {
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((res) => {
        console.log(res.data);
      })
  }
};
</script>
<style scoped>
  .my-auto {
      font-family: 'Jua', sans-serif;
  }
  .fa-chevron-left {
    color: white; 
    margin-left: 7px;
  }
</style>