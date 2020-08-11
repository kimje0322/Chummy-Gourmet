<template>
  <v-app class="mypage">
    <!-- 상단 -->
    <v-toolbar-title>
      <Top></Top>
      <Topsub :proptoTopsub="users"></Topsub>
    </v-toolbar-title>
    <!-- 가운데 부분 -->
    <div>
      <!-- tab view -->
      <v-tabs dark v-model="currentItem" fixed-tabs slider-color="grey">
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
              <!-- meetupData -->
              <!-- <h2>Meet Up</h2><br>
            <div v-if="meetupData" class="meetupData">
              <h3 v-for="(meetup, i) in meetupData" :key="i">{{ meetup.meetupTitle }}</h3>-->
              <!-- <span class="meetup-title">{{ meetupData.title }}</span>
              <span class="ml-2"><i class="fas fa-crown"></i><span class="mr-2"></span>{{ meetupData.master }}</span>-->
              <!-- <v-row>
            </v-row>
          </div>

          <div v-else class="meetupData">
            <h3 class="text-meetup">예정된 meetup이 없습니다.</h3>
              </div>-->

              <v-row justify="space-around">
                <v-date-picker
                  full-width
                  v-model="date1"
                  :show-current="showCurrent"
                  :multiple="multiple"
                  :events = "dateFunctionEvents"
                ></v-date-picker>
              </v-row>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </v-app>
</template>


<script>
import Top from "../components/common/Top";
import Topsub from "../components/common/Topsub";
import Message from "../components/common/Message";
import History from "../components/common/History";
import "../assets/css/components.scss";
import axios from "axios";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";

export default {
  name: "components",
  components: {
    Top,
    Topsub,
    Message,
    History,
  },
  
  data: () => {
    return {
      // 밋업 날짜만 분리한 데이터
      dateData: {
        muyear: [],
        mumonth: [],
        muday: [],
      },
      date1: new Date().toISOString().substr(0, 10),
      enableEvents: true,
      showCurrent: true,
      // 밋업 전체 데이터
      mData: {}, 
      // 밋업 날짜만 담긴 list
      meetupDate: [],
      month: false,
      multiple: false,
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
            console.log("클릭");
          },
        },
        {
          title: "취소",
          onClick: () => {
            console.log("클릭");
          },
        },
      ],
      options: [
        {
          value: "option1",
          title: "옵션1",
        },
        {
          value: "option2",
          title: "옵션2",
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
        `${SERVER_URL}/userpage/getuserMeetup?userId=${this.userId}`
      )
      .then((response) => {
        this.mData = response.data
        for (var i=0; i<this.mData.length; i++) {
          this.meetupDate.push(this.mData[i].meetupPersonnel.slice(0, 11));
        }
        for (var j=0; j<this.meetupDate.length; j++) {
          this.dateData.muyear.push(this.meetupDate[j].slice(0, 4));
          this.dateData.mumonth.push(this.meetupDate[j].slice(5, 7));
          this.dateData.muday.push(this.meetupDate[j].slice(8, 10));
        }
      })
      .catch((error) => {
        console.log(error.response);
        this.meetupData = false;
      });
  },
  methods: {
    // 이번달 달력에서 
    dateFunctionEvents(date) {
      const [year, month, day] = date.split("-");
      for (let i = 0; i < this.dateData.muyear.length; i++) {
         if (this.dateData.muyear[i] == year
            &&this.dateData.mumonth[i] == month
            &&this.dateData.muday[i] == day){
              return true
          }
      }
      return false;
    },
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
</style>