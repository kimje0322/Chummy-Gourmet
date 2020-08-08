<template>
  <div>
    <!-- Profile -->
    <v-toolbar dark>
      <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
      <p class="my-auto">Profile</p>
      <v-spacer></v-spacer>
    </v-toolbar>
    <!-- 매너온도/팔로워/팔로잉 -->
    <v-layout>
    <v-toolbar dark>
      <v-list-item-avatar>
        <v-img :src="this.userImg"></v-img>
      </v-list-item-avatar>
      {{ userNickname }}
      <!-- <v-btn><br>매너온도</v-btn> -->
      <v-spacer></v-spacer>
        <v-btn depressed>매너평가</v-btn>
      <v-spacer></v-spacer>
        <v-btn v-if="followerFollowing" class="followListBtn" depressed>언팔로우</v-btn>
        <v-btn v-else class="followListBtn" depressed>팔로우</v-btn>

      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>


  </div>
</template>

<script>
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
import axios from "axios";
export default {
  data() {
    return {
      followerFollowing: false,
      followerCount: null,
      followingCount: null,
      userNickname: "",
      userImg:"",
    }
  },

  name: "Profile",
  
  created() {
    let userID = this.$route.params.userId
    this.userImg = this.$route.params.userImg
    if (this.$route.params.followerFollowing == 'true') {
      this.followerFollowing = true
    } 

    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=`+userID
      )
      .then((response) => {
        this.followerCount =  response.data.followerCount
        this.followingCount = response.data.followingCount
        this.userNickname = response.data.userNickname
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  
}

</script>

<style>

</style>