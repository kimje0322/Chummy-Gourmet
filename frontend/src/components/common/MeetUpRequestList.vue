<template>
  <v-app>
     <v-toolbar-title >
      <v-toolbar dense>
      <v-icon @click="$router.go(-1)">
        mdi-arrow-left
      </v-icon>
      <v-spacer></v-spacer>
      <p class="my-auto text-center">밋업 참가요청</p>
      <v-spacer></v-spacer>
    </v-toolbar>
    </v-toolbar-title>
    
    
    <div v-if="guestList.length > 0">
    <v-list subheader>
      <v-list-item
        v-for="(guest,i) in guestList"
        :key="i"
      >
        <v-list-item-avatar @click="showUser(guest)">
          <v-img
            :src="'https://i3b302.p.ssafy.io:8080/img/user?imgname='+guest.userImg">
          </v-img>
        </v-list-item-avatar>

        <v-list-item-content @click="showUser(guest)">
          <a
          class="pf-n-a"
          tabindex="0"
          style="color: black; font-weight: 600;" 
          >
            {{guest.userNickname}}
          </a>
        </v-list-item-content>

        <v-list-item-icon >
          <v-btn outlined color="orange" @click="showRequest(i)">
            요청보기
          </v-btn>
        </v-list-item-icon>
       
      </v-list-item>
    </v-list>
    </div>

    <div v-else>
      <h3>밋업 참가 요청이 없습니다.</h3>
    </div>
    <v-dialog
      v-model="dialog"
      >
      <v-card>
        <div class="d-flex">
          <v-avatar
          class="ma-3"
          size="85"
          tile
          >
            <v-img :src="meetupInfo.img"></v-img>
          </v-avatar>
          <div>
            <v-card-title
            class="headline"
            v-text="meetupInfo.title"
            ></v-card-title>
            <v-card-subtitle>{{this.meetupInfo.location}}<br>{{this.meetupDate}}</v-card-subtitle>
          </div>
        </div>
      </v-card>
      <v-list>
        <v-list-item-avatar>
          <v-img
            :src="'https://i3b302.p.ssafy.io:8080/img/user?imgname='+this.userImg">
          </v-img>
        </v-list-item-avatar>
        <span
          style="color: black; font-weight: 600;" 
          >
            {{this.userNickname}}
        </span>
        <span
          style="color: orange; font-weight: 600;" 
          >
            {{this.requestMessage}}
        </span><br>   
        <v-list-item-icon >
          <v-btn color="info" @click="acceptRequest()">
            수락
          </v-btn>
          <v-btn color="error" @click="rejectRequest()">
            거절
          </v-btn>
        </v-list-item-icon>
      </v-list>
     
    </v-dialog>
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
      requestList: [],
      guestList:[],
      dialog : false,
      meetupInfo:[],
      index : "",
      userImg : "",
      userNickname : "",
      requestMessage : "",
      meetupDate : "",
    };
  },
  methods :{
    acceptRequest(){
      // axios
      // .get(
      //   `${SERVER_URL}/meetup/acceptMeetuprequest?userId=${this.userId}&followingRequestId=`+followingRequestId
      // )
      // .then((response) => {
      //   if(response.data.data == "success"){
      //     alert("수락완료");
      //     this.created();
      //   }
      //   else
      //     alert("수락실패");
      // })
      // .catch((error) => {
      //   console.log(error.response);
      // });
    },
    rejectRequest(){

    },
    showRequest(idx){
      this.index = idx;
      this.dialog = true;
      this.userImg = this.guestList[idx].userImg
      this.userNickname = this.guestList[idx].userNickname
      this.requestMessage = this.requestList[idx].requestMessage
      axios
      .get(`${SERVER_URL}/meetup/searchByMeetupID/${this.requestList[idx].meetupId}`)
      .then((response) => {
        this.meetupInfo = response.data;
        this.meetupDate = this.meetupInfo.date.slice(0, 16)
        console.log(this.meetupInfo)
      })
    },
    showUser(user){
      let profileInfo = {
        userId: user.userId,
        userImg : user.userImg,
        followerFollowing: user.followerFollowing
      };
      this.$router.push('/user/profile?userId='+user.followingRequestId
        +'&followerFollowing='+user.followerFollowing
        +'&userImg='+user.followingRequestUserImg);
    },
    created(){
      this.userId = this.$cookie.get("userId");
      this.requestList =[]
    axios
      .get(
        `${SERVER_URL}/meetup/request?userId=${this.userId}`
      )
      .then((response) => {
        if(response.data.data == "success"){
          this.requestList = response.data.object
          for (let i = 0; i < this.requestList.length; i++) {
            axios
            .get(
              `${SERVER_URL}/userpage/getuser?userId=`+ this.requestList[i].guestId
            )
            .then((response) => {
              this.guestList.push(response.data)
            })
            .catch((error) => {
              console.log(error.response);
            });
          }
        }
        console.log(this.guestList)
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

  h3 {
    margin: 60px;
    text-align: center;
  }
</style>