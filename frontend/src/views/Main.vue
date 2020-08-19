<template>
  <div>
    <!-- 상단 -->
      <Alarm></Alarm>
    <v-toolbar-title>
      <Top></Top>
      <Topsub :proptoTopsub="users"></Topsub>
    </v-toolbar-title>
    <!-- 가운데 부분 -->
    <div>
      <!-- tab view -->
      <v-tabs v-model="currentItem" fixed-tabs slider-color="orange">
        <v-tab v-for="item in items" :key="item" :href="'#tab-' + item">
          <v-icon v-if="item=='Profile'">mdi-account-box</v-icon>
          <v-icon v-if="item=='History'">fas fa-list</v-icon>
          <v-icon v-if="item=='Message'">mdi-folder</v-icon>
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="currentItem">
        <v-tab-item v-for="item in items" :key="item" :value="'tab-' + item">
          <v-card flat>
            <!-- dm -->
            <Message v-if="item=='Message'" v-bind:userId="userId"></Message>
            <!-- history -->
            <History v-else-if="item=='History'" :proptoTopsub="users" ></History>
            <!-- profile -->
            <v-card-text v-else>
            
            <!-- 밋업 있을 때 -->
            <v-container v-show="listLen">
               <v-hover v-if="meetups.length > 0">진행중 밋업</v-hover>
              <v-row dense>
                <v-col
                  v-for="(meetup, i) in meetups"
                  :key="i"
                  cols="12"
                >
                  <v-card @click="showMenu(meetup, true)">
                    <div class="d-flex">
                      <v-avatar
                      class="ma-3"
                      size="85"
                      tile
                      >
                        <v-img :src="meetup.img"></v-img>
                      </v-avatar>
                      <div>
                        <v-card-title
                        class="headline"
                        v-text="meetup.title"
                        ></v-card-title>
                        <v-card-subtitle v-html="meetup.location+'<br>'+meetup.date.slice(0, 16)" ></v-card-subtitle>
                      </div>
                    </div>
                  </v-card>
                </v-col>
              </v-row>
              <v-hover v-if="closeMeetups.length > 0">종료 밋업</v-hover>
              <v-row dense>
                <v-col
                  v-for="(meetup, i) in closeMeetups"
                  :key="i"
                  cols="12"
                >
                  <v-card @click="showMenu(meetup,false)">
                    <div class="d-flex">
                      <v-avatar
                      class="ma-3"
                      size="85"
                      tile
                      >
                        <v-img :src="meetup.img"></v-img>
                      </v-avatar>
                      <div>
                        <v-card-title
                        class="headline"
                        v-text="meetup.title"
                        ></v-card-title>
                        <v-card-subtitle v-html="meetup.location+'<br>'+meetup.date.slice(0, 16)" ></v-card-subtitle>
                      </div>
                    </div>
                  </v-card>
                </v-col>
              </v-row>
              <v-hover v-if="closeMeetups.length > 0">종료 밋업</v-hover>
              <v-row dense>
                <v-col
                  v-for="(meetup, i) in closeMeetups"
                  :key="i"
                  cols="12"
                >
                  <v-card @click="showMenu(meetup,false)">
                    <div class="d-flex">
                      <v-avatar
                      class="ma-3"
                      size="85"
                      tile
                      >
                        <v-img :src="meetup.img"></v-img>
                      </v-avatar>
                      <div>
                        <v-card-title
                        class="headline"
                        v-text="meetup.title"
                        ></v-card-title>
                        <v-card-subtitle v-html="meetup.location+'<br>'+meetup.date.slice(0, 16)" ></v-card-subtitle>
                      </div>
                    </div>
                    </v-card>
                  </v-col>
                </v-row>
               
            </v-container>
             <!-- 밋업 없을 때 --> 
            <v-container v-show="!listLen" style="text-align: center; margin-top:30%;"> 
              <i class="fab fa-meetup fa-6x"></i>
              <h3 class="mt-5">등록된 Meetup이 없습니다.</h3>
            </v-container>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </div>
    <!-- dialog -->
    <v-dialog
      v-model="dialog"
      max-width="190"
      >
        <v-list> 
        <v-list-item @click="moveMeetupDetail">
          <v-list-item-title>밋업상세보기</v-list-item-title>
        </v-list-item>
        <v-list-item @click="moveCreateReview">
          <v-list-item-title>리뷰작성</v-list-item-title>
        </v-list-item>
        <v-list-item v-show="close" @click="cancelMeetup">
          <v-list-item-title>참여취소</v-list-item-title>
        </v-list-item>
    </v-list>
    </v-dialog>
  </div>
</template>


<script>
import Top from "../components/common/Top";
import Topsub from "../components/common/Topsub";
import Message from "../components/common/Message";
import History from "../components/common/History";

import Alarm from "../components/common/Alarm";

import "../assets/css/components.scss";
import axios from "axios";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  name: "components",
  components: {
    Top,
    Topsub,
    Message,
    History,
    Alarm
  },
  
  data: () => {
    return {
      meetup: "",
      dialog:false,
      // 밋업 전체 데이터
      meetups: [], 
      closeMeetups:[],
      users: {},
      userId: "",
      close:false,
      contents: "",
      currentItem: "",
      listLen : false,
      items: ["Profile", "History", "Message"],
      buttons: [
        {
          title: "확인",
          highlight: true,
          onClick: () => {
          },
        },
        {
          title: "취소",
          onClick: () => {
          },
        },
      ],
    };
  },
  created() {
    this.created();
  },
  methods: {
    isNaN(){
      alert(this.closeMeetups.length)
      alert(this.meetups.length)
      if(this.closeMeetups.length == 0 && this.meetups.length == 0){
        return true;
      }
      else
        return false;
    },
    created(){
      this.meetups = [],
      this.closeMeetups = []
      this.userId = this.$cookie.get("userId");
    axios
      .get(`${SERVER_URL}/userpage/getuser?userId=`+ this.userId)
      .then((response) => {
        this.users = response.data;
      })
      .catch((error) => {
        console.log(error.response);
      });
    //  meetup 정보 받아오기
    axios
      .get(
        `${SERVER_URL}/meetup/list/${this.userId}`
      )
      .then((response) => {
        let now = new Date();
        if(response.data.object.length > 0 ){
          this.listLen = true;
        }
        
        for (let i = 0; i <response.data.object.length; i++) {
          let time = new Date(response.data.object[i].date)
          if(time - now > 0){
            this.meetups.push(response.data.object[i])
            console.log(this.meetups)
          }
          else{
            this.closeMeetups.push(response.data.object[i])
          }
        }
      })
      .catch((error) => {
        console.log(error.response);
        this.meetupData = false;
      });
    },
    showMenu(item,flag){
      this.dialog = true;
      this.meetup = item;
      this.close = flag;
    },
    cancelMeetup(){
      this.$confirm("참여취소 하시겠습니까?").then(() => {
      //do something...
        axios
        .delete(
          `${SERVER_URL}/meetup?meetupId=${this.meetup.id}&userId=${this.userId}`
        )
        .then((response) => {
            this.dialog = false
            this.created();
        })
        .catch((error) => {
          console.log(error.response);
        });
      });
    },
    moveCreateReview(){
      this.$router.push({ name : 'AddReview', params : this.meetup });
    },
    moveMeetupDetail(){
      this.$router.push('/map/detailMeetup?meetupId='+this.meetup.id);
    }
  },
};
</script>


<style>

.meetup-title {
  font-size: 22px;
}

.text-meetup {
  text-align: center;
}
</style>