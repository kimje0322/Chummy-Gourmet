<template>
  <v-layout>
    <v-toolbar dark>
      <v-list-item-avatar>
        <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
      </v-list-item-avatar>
      {{proptoTopsub.userName}}
      <v-spacer></v-spacer>
        <v-btn @click="getFollowings" depressed>{{proptoTopsub.followingCount}} <br>팔로잉</v-btn>
      <v-spacer></v-spacer>
        <v-btn @click="getFollowers" depressed>{{proptoTopsub.followerCount}}<br>팔로워</v-btn>
    </v-toolbar>
  </v-layout>
</template>
<script>
// import Following from "../../views/Following";
import axios from "axios";

const SERVER_URL = "http://localhost:8080";

export default {
  props:{
    proptoTopsub: {
      type : Object
    },
  },
  methods: {
    logout(){
      this.$cookie.delete('accesstoken');
      this.$cookie.delete('userId');
      this.$router.push('/')
    },
    getFollowings () {
      this.$router.push({ name: "List", prams:{flag:"FollowingList"}})
    },
    getFollowers () {
      this.$router.push({ name: "List", prams:{flag:"FollowerList"}})
    }
  }
}
</script>