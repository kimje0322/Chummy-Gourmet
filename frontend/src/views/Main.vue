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
          <v-icon v-if="item=='History'">mdi-heart</v-icon>
          <v-icon v-if="item=='Message'">far fa-paper-plane</v-icon>
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="currentItem">
        <v-tab-item v-for="item in items" :key="item" :value="'tab-' + item">
          <v-card flat>
            <!-- dm -->
            <Message v-if="item=='Message'"></Message>
            <!-- history -->
            <History v-else-if="item=='History'"></History>
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
                  v-model="picker"
                  :show-current="showCurrent"
                  :type="month ? 'month' : 'date'"
                  :multiple="multiple"
                  :events="enableEvents ? functionEvents : null"
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
  created() {
    // alert(this.$cookie.get("userId"));
    this.userId = this.$cookie.get("userId");
    axios
      .get(`${SERVER_URL}/userpage/getuser?userId=` + this.userId)
      .then((response) => {
        // console.log(response.data);
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
        // console.log(response.data);
        console.log("밋업 data 성공");
        this.mData = response.data
        console.log(this.mData)
        for (var i=0; i<this.mData.length; i++) {
          this.meetupDate.push(this.mData[i].meetupPersonnel.slice(0, 11));
        }
        for (var j=0; j<this.meetupDate.length; j++) {
          this.dateData.muyear.push(this.meetupDate[j].slice(0, 4));
          this.dateData.mumonth.push(this.meetupDate[j].slice(5, 7));
          this.dateData.muday.push(this.meetupDate[j].slice(8, 10));
        }
        console.log(this.dateData)
      })
      .catch((error) => {
        console.log(error.response);
        this.meetupData = false;
        console.log("밋업 실패");
      });
  },
  data: () => {
    return {
      // 밋업 날짜만 분리한 데이터
      dateData: {
        muyear: [],
        mumonth: [],
        muday: [],
      },
      picker: new Date().toISOString().substr(0, 10),
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
  computed: {
    functionEvents() {
      return this.month ? this.monthFunctionEvents : this.dateFunctionEvents;
    },
  },
  methods: {
    dateFunctionEvents(date) {
      
      const [, , day] = date.split("-");
      console.log(this.dateData.muday);
      if (this.dateData.muday.map(parseInt).includes(parseInt(day, 10))) return true;
      // if ([2, 17, 28].includes(parseInt(day, 10))) return true;
      // if ([1, 19, 22].includes(parseInt(day, 10))) return ['red', '#00f']
      return false;
    },
    monthFunctionEvents(date) {
      const month = parseInt(date.split("-")[1], 10);
      if ([1, 3, 7].includes(month)) return true;
      if ([2, 5, 12].includes(month))
        return ["error", "purple", "rgba(0, 128, 0, 0.5)"];
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