<template>
  <v-app>
    <v-toolbar-title >
      <v-toolbar dark>
        <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <p class="my-auto">팔로잉 요청</p>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-toolbar-title>
    
    <v-list subheader>
      <v-list-item
        v-for="item in items"
        :key="item.followingRequestId"
      >
        <v-list-item-avatar @click="showUser(item)">
          <v-img
            :src="item.followingRequestUserImg">
          </v-img>

        </v-list-item-avatar>

        <v-list-item-content @click="showUser(item)">
          <v-list-item-title>{{item.followingRequestNickname}}</v-list-item-title>
        </v-list-item-content>

        <v-list-item-icon @click="acceptFollowing(item.followingRequestId)">
          <v-btn color="info">
            수락
          </v-btn>
        </v-list-item-icon>

        <v-list-item-icon @click="deleteFollowing(item.followingRequestId)">
          <v-btn color="error">
            거절
          </v-btn>
        </v-list-item-icon>

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
    };
  },
  methods :{
    showUser(user){
      let profileInfo = {
        userId: user.followingRequestId,
        userImg : user.followingRequestUserImg,
        followerFollowing: user.followerFollowing
      };
      this.$router.push('/user/profile?userId='+user.followingRequestId
        +'&followerFollowing='+user.followerFollowing
        +'&userImg='+user.followingRequestUserImg);
    },
    acceptFollowing(followingRequestId){
      axios
      .get(
        `${SERVER_URL}/userpage/acceptfollowingrequest?userId=${this.userId}&followingRequestId=`+followingRequestId
      )
      .then((response) => {
        if(response.data.data == "success"){
          alert("수락완료");
          this.created();
        }
        else
          alert("수락실패");
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
    deleteFollowing(followingRequestId){
      axios
      .delete(
        `${SERVER_URL}/userpage/deletefollowingrequest?userId=${this.userId}&followingRequestId=`+followingRequestId
      )
      .then((response) => {
        if(response.data.data == "success"){
          alert("삭제완료");
          this.created();
        }
        else
          alert("삭제실패");
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
        `${SERVER_URL}/userpage/getfollowingrequest?userId=${this.userId}`
      )
      .then((response) => {
        if(response.data != ""){
          for (let i = 0; i < response.data.length; i++) {
            let userImg = response.data[i].followingRequestUserImg;
            let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
            this.items.push({
              followingRequestId : response.data[i].followingRequestId,
              followingRequestNickname : response.data[i].followingRequestNickname,
              followingRequestUserImg: viewImg,
              followerFollowing : response.data[i].followerFollowing
            })
          }
        }
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