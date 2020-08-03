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
    <!-- 가운데 부분 -->
    <!-- 팔로워 -->
    
    <v-list subheader>
      <v-list-item
        v-for="item in list"
        :key="item.userId"
      >
        <v-list-item-avatar @click="showUser">
          <v-img :src="items[0].avatar"></v-img>
        </v-list-item-avatar>

         
        <v-list-item-content @click="showUser">
          <v-list-item-title>{{item.followingRequestNickname}}</v-list-item-title>
        </v-list-item-content>

        
        <v-list-item-icon @click="acceptFollowing(item.followingRequestId)">
          <v-btn color="blue">
            수락
          </v-btn>
          <!-- <v-icon>mdi-account-plus</v-icon> -->
        </v-list-item-icon>

        <v-list-item-icon @click="deleteFollowing(item.followingRequestId)">
          <v-btn color="red">
            거절
          </v-btn>
        </v-list-item-icon>

      </v-list-item>
    </v-list>

  </v-app>
</template>

<script>

import axios from "axios";
const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";
export default {
  name: "components",
 
  data: () => {
    return {
      userId :"",
      items: [
        { active: true, title: 'Jason Oner', avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg' },
      ],
      list:[],
    };
  },
  methods :{
    showUser(){
      alert("유저 보여줄려고 클릭함")

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
    axios
      .get(
        `${SERVER_URL}/userpage/getfollowingrequest?userId=${this.userId}`
      )
      .then((response) => {
        if(response.data != ""){
          this.list = response.data;
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