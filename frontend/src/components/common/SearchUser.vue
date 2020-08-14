<template>
  <v-app>
    <v-toolbar-title >
      <v-toolbar dark>
        <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <p class="my-auto">유저검색</p>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-toolbar-title>
    
    <!-- 팔로워 -->
        <v-list nav dense >
        
          <!-- 팔로워 검색바 -->
          <v-row>
            <v-col class="search-bar">
            <v-text-field 
              @keyup="onSearchUser"
              v-model="searchUser"
              label="검색"
              :append-icon="'mdi-account-search-outline'"
              hide-details
              solo
            ></v-text-field>             
            </v-col>
          </v-row>
            <v-skeleton-loader
                ref="skeleton"
                type="list-item-avatar"
                class="mx-auto"
                v-show="show"
            ></v-skeleton-loader>
            <v-list-item v-show="!show" class="followlist" v-for="(user, i) in items" :key="i">
                <!-- 사진 표기 -->
                <v-list-item-avatar @click="gotoProfile(user)">
                  <v-img
                    :src="user.UserImg">
                  </v-img>
                </v-list-item-avatar>
                
                <v-list-item-content>
                <a
                class="pf-n-a"
                tabindex="0"
                style="color: black; font-weight: 600;" 
                @click="gotoProfile(user)"
                >
                  {{user.UserNickname}}
                </a> 
                <!-- <v-list-item-title @click="gotoProfile(user)" v-text="user.UserNickname"></v-list-item-title> -->
                </v-list-item-content>

                <v-btn color="primary"  @click="onFollow(user,i)" v-if="user.followerFollowing === 'false'">
                    팔로우
                </v-btn>
                
                <v-btn depressed  @click="deleteFollowRequest(user,i)" v-else-if="user.followerFollowing === 'doing'">
                    요청중
                </v-btn>

                <v-btn depressed @click="unFollow(user,i,'followerlist')" v-else>
                    팔로잉
                </v-btn>

            </v-list-item>
        </v-list>

  </v-app>
</template>

<script>

import axios from "axios";
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";
export default {
  name: "components",
 
  data: () => {
    return {
      userId :"",
      items: [],
      viewImg:"",
      userImg:"",
      searchUser:"",
      show:true,
    };
  },
  methods :{
    gotoProfile(user) {
        let profileInfo = {
            userId: user.UserId,
            userImg : user.UserImg,
            followerFollowing: user.followerFollowing
        };
        // this.$router.push('/map/detailMeetup?meetupId='+this.list.meetupId);
        this.$router.push('/user/profile?userId='+user.UserId
        +'&followerFollowing='+user.followerFollowing
        +'&userImg='+user.UserImg);
        // this.$router.push({name :'Profile', params: profileInfo});
    },
    onSearchUser() {
      axios
      .get(
        `${SERVER_URL}/userpage/getalluser?userId=`+this.userId+`&searchName=`+this.searchUser
      )
      .then((response) => {
        this.items = []
        for (let i = 0; i <  response.data.length; i++) {
          let userImg = response.data[i].UserImg;
          let viewImg = "https://i3b302.p.ssafy.io:8080/img/user?imgname=" + userImg;
          this.items.push({
              UserId : response.data[i].UserId,
              UserNickname : response.data[i].UserNickname,
              UserImg: viewImg,
              followerFollowing :  response.data[i].followerFollowing
          })
        }
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
    deleteFollowRequest(user,idx){
      this.$set(this.items[idx], 'followerFollowing', 'false');
        //언팔로우 요청
        axios
          .delete(
            `${SERVER_URL}/userpage/deletefollowingRequest?anotherId=`+user.UserId+`&userId=`+this.userId
          )
          .then((response) => {
          })
          .catch((error) => {
            console.log(error.response);
          });
    },
    onFollow(user,idx) {
      this.$set(this.items[idx], 'followerFollowing', 'doing');
      axios
      .post(
        `${SERVER_URL}/userpage/insertfollowingRequest?followerId=`+user.UserId+`&userId=`+this.userId
      )
      .then((response) => {
      })
      .catch((error) => {
          console.log(error.response);
      });
    },
    unFollow(user,idx,str) {
    this.$set(this.items[idx], 'followerFollowing', 'false');
      //언팔로우 요청
      axios
        .delete(
          `${SERVER_URL}/userpage/deletefollowing?anotherId=`+user.UserId+`&userId=`+this.userId
        )
        .then((response) => {
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
    created(){
      this.userId = this.$cookie.get("userId");
      this.items =[]
    axios
      .get(
        `${SERVER_URL}/userpage/getalluser?userId=`+this.userId+`&searchName=`+this.searchUser
      )
      .then((response) => {
        if(response.data != ""){
          for (let i = 0; i < response.data.length; i++) {
            let userImg = response.data[i].UserImg;
            let viewImg = "https://i3b302.p.ssafy.io:8080"+"/img/user?imgname=" + userImg;
            this.items.push({
              UserId : response.data[i].UserId,
              UserNickname : response.data[i].UserNickname,
              UserImg: viewImg,
              followerFollowing : response.data[i].followerFollowing
            })
          }
          
        }
        this.show = false;
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
},
   created(){
     this.created();
   }
};
</script>

<style scoped>
  .follow-lsit {
    width: 100px
  }
</style>