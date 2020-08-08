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
        <v-btn color="primary"  @click="onFollow()" v-if="followerFollowing === 'false'">
            팔로우
        </v-btn>
        <v-btn depressed  @click="deleteFollowRequest()" v-else-if="followerFollowing === 'doing'">
            요청중
        </v-btn>
        <v-btn depressed @click="unFollow()" v-else>
            팔로잉
        </v-btn>
      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>


  </div>
</template>

<script>
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

import axios from "axios";
export default {
  data() {
    return {
      anotherId:"",
      userId:"",
      followerFollowing: "",
      followerCount: null,
      followingCount: null,
      userNickname: "",
      userImg:"",
    }
  },
  methods :{
    deleteFollowRequest(){
      this.followerFollowing = 'false'
        //언팔로우 요청
        axios
          .delete(
            `${SERVER_URL}/userpage/deletefollowingRequest?anotherId=`+this.anotherId+`&userId=`+this.userId
          )
          .then((response) => {
            console.log('팔로우취소완료')
          })
          .catch((error) => {
            console.log(error.response);
          });
    },
    onFollow() {
      this.followerFollowing = 'doing'
      axios
      .post(
        `${SERVER_URL}/userpage/insertfollowingRequest?followerId=`+this.anotherId+`&userId=`+this.userId
      )
      .then((response) => {
        console.log('팔로우성공')
      })
      .catch((error) => {
          console.log(error.response);
      });
    },
    unFollow() {
        this.followerFollowing = 'false'
      axios
        .delete(
          `${SERVER_URL}/userpage/deletefollowing?anotherId=`+this.anotherId+`&userId=`+this.userId
        )
        .then((response) => {
          console.log("언팔로우 성공")
        })
        .catch((error) => {
          console.log(error.response);
        });
    }
  },

  name: "Profile",
  
  created() {
    this.userId = this.$cookie.get("userId");

    this.anotherId = this.$route.params.userId
    this.userImg = this.$route.params.userImg
    this.followerFollowing = this.$route.params.followerFollowing
    if (this.followerFollowing === 'true') {
      this.followerFollowing = 'true'
    }else if(this.followerFollowing === 'doing'){
      this.followerFollowing = 'doing'  
    }else{
      this.followerFollowing = 'false'
    }

    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=`+this.userId
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