<template>
<!-- 부모 -->
  <v-layout>
    <v-toolbar dark>
      <v-list-item-avatar>
        <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
      </v-list-item-avatar>
      {{proptoTopsub.userName}}
      <v-spacer></v-spacer>
        <v-btn @click="getFollowings" depressed>{{proptoTopsub.followingCount}} <br>팔로잉</v-btn>
      <v-spacer></v-spacer>
        <v-btn depressed>{{proptoTopsub.followerCount}}<br>팔로워</v-btn>
      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>
</template>
<script>
import Following from "../../views/Following";
import router from "@/routes";
import axios from "axios";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

export default {
  props:{
    proptoTopsub: {
      type : Object
    },
  },
  data() {
    return {
    followingUser: {
      name: [],
      nickname: [],
      }
    }
  },
  methods: {
    getFollowings () {
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.proptoTopsub.userId
      )
      .then((response) => {
        // data = [{…}, {…}, {…}]
        if (response.data) {
          let data = response.data
          console.log(data[2].followingName)
          for (var i=0; i<data.length; i++) {
          // alert(data[i].followingName); 
            this.followingUser.name.push(data[i].followingName)
            this.followingUser.nickname.push(data[i].followingNickname)
          }
          console.log(this.followingUser)          
        }
        router.push({name : "Following", params: this.followingUser});
      })
      .catch((error) => {
        console.log(error.response);
      });
    }
  }
}
</script>