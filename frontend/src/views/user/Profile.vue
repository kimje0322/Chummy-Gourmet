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
        <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
      </v-list-item-avatar>
      <v-btn><br>매너온도</v-btn>
      <v-spacer></v-spacer>
        <v-btn depressed><br>팔로워</v-btn>
      <v-spacer></v-spacer>
        <v-btn class="followListBtn" depressed><br>팔로잉</v-btn>
      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>
  </div>
</template>

<script>
const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
import axios from "axios";
export default {
  name: "Profile",
  created() {
    // userID = this.$route.params
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=`+this.$route.params
      )
      .then((response) => {
        console.log(this.userID);
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  data() {
    return {
    }
  }
}

</script>

<style>

</style>