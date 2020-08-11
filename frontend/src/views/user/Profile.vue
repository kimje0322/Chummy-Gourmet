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
  <v-layout class="entireClass">
      <v-row>
        
      <v-col v-for="(lst,i) in postlst" :key="i" class="d-flex child-flex" cols="4">
          <v-card flat tile class="d-flex">
              {{lst.postimgurl}}
              <v-img @click="detailInfo(lst,commentlst[i])"
                :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+lst.post_img_url"
                aspect-ratio="1" 
                class="grey lighten-2"
              >
              <template v-slot:placeholder>
              <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
              >
                  <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
              </v-row>
              </template>
              </v-img>
          </v-card>
      </v-col>
      </v-row>
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
      postlst: [],
      commentlst :[],
    }
  },
  methods :{
    detailInfo(post,comment) {
      let item = {
      post: post,
      comment : comment,
      };
      this.$router.push({name :'PostDetail', params: item});
    },
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
    this.anotherId = this.$route.query.userId
    this.userImg = this.$route.query.userImg
    this.followerFollowing = this.$route.query.followerFollowing
    if (this.followerFollowing === 'true') {
      this.followerFollowing = 'true'
    }else if(this.followerFollowing === 'doing'){
      this.followerFollowing = 'doing'  
    }else{
      this.followerFollowing = 'false'
    }

    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=`+this.anotherId
      )
      .then((response) => {
        this.followerCount =  response.data.followerCount
        this.followingCount = response.data.followingCount
        this.userNickname = response.data.userNickname
      })
      .catch((error) => {
        console.log(error.response);
      });

      axios
        .get(`${SERVER_URL}/userpage/getuserpost?userId=`+this.anotherId)
        .then((response) => {
            this.postlst = response.data.data
            this.commentlst = response.data.comment;
            // console.log(this.postlst)
        })  
  },
  
}

</script>

<style>

</style>