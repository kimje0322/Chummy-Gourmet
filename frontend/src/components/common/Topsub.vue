<template>
  <v-layout>
    <v-toolbar dark>
      <v-list-item-avatar>
        <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
      </v-list-item-avatar>

        {{proptoTopsub.userNickname}}

      <!-- <v-btn><br>매너온도</v-btn> -->
      <v-spacer></v-spacer>
        <v-btn @click="getFollowers" depressed>{{proptoTopsub.followerCount}}<br>팔로워</v-btn>
      <v-spacer></v-spacer>
        <v-btn @click="getFollowings" depressed>{{proptoTopsub.followingCount}} <br>팔로잉</v-btn>
      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>
</template>
<script>
// import Following from "../../views/Following";
import router from "@/routes";
import axios from "axios";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

export default {
  props:{
    proptoTopsub: {
      type : Object
    },
  },
  // data() {
  //   return {
  //   followingUser: {
  //     name: [],
  //     nickname: [],
  //     }
  //   }
  // },
  methods: {
    getFollowers () {
      this.$router.push('/followlist')
    },
    getFollowings () {
      let userInfo = {
              info: "follow",
            };
      this.$router.push({name :'FollowList', params: userInfo});
    },
  }

}
</script>

<style>

</style>