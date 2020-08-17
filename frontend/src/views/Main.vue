<template>
  <v-app class="mypage">
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
            <History :proptoTopsub="users" v-else-if="item=='History'"></History>
            <!-- profile -->
            <v-card-text v-else>
            
            <!-- 밋업 있을 때 -->
              <v-row dense>
                <v-col
                  v-for="(meetup, i) in meetups"
                  :key="i"
                  cols="12"
                >
                  <v-card @click="showMenu(meetup)">
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

            <!-- 밋업 없을 때 --> 
            <div v-if="!isNaN(meetups)" style="margin-top:100px;text-align: center;"> 
              <i class="fab fa-meetup fa-6x"></i>
              <h3 class="mt-5">등록된 Meetup이 없습니다.</h3>
            </div>

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
        <v-list-item @click="cancelMeetup">
          <v-list-item-title>참여취소</v-list-item-title>
        </v-list-item>
    </v-list>
    </v-dialog>
  </v-app>
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
      meetups: {}, 
      users: {},
      userId: "",
      contents: "",
      currentItem: "",
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
        this.meetups = response.data.object;
        console.log(response);
      })
      .catch((error) => {
        console.log(error.response);
        this.meetupData = false;
      });
  },
  methods: {
    showMenu(item){
      this.dialog = true;
      this.meetup = item;
    },
    cancelMeetup(){
      alert("취소하기 백엔드완성되면 이어붙일 예정");
      // axios.delete(`${SERVER_URL}/userpage/restsacrp?userid=${this.userId}&restid=`)
      // .then((response) => {
      //     this.dialog = false
      // })
      // .catch((error) => {
      //     console.log(error.response);
      // });
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
.container nothome {
  padding: 0px !important;
}

.meetup-title {
  font-size: 22px;
}

.text-meetup {
  text-align: center;
}
.mt-5 {
  font-family: 'Jua', sans-serif;
}
</style>