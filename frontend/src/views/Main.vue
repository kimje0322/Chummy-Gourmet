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
            <h2>Meet Up</h2><br>
            <!-- {{ meetupData}} -->      
            <div v-if="meetupData" class="meetupData">
              <span class="meetup-title">{{ meetupData.title }}</span>
              <span class="ml-2"><i class="fas fa-crown"></i><span class="mr-2"></span>{{ meetupData.master }}</span>
              <p class="mt-2">{{ meetupData.content }}</p>
              <v-row>
              <!-- 밋업정보 vuetify 이런 식으로 할까 생각중. -->
              <v-col cols="12" sm="6" offset-sm="3">
                <v-card>
                  <v-list two-line>
                    <template v-for="(item, index) in meetupItems.slice(0, 6)">
                      <v-subheader v-if="item.header" :key="item.header">{{ item.header }}</v-subheader>
                      <v-divider v-else-if="item.divider" :key="index" :inset="item.inset"></v-divider>
                      <v-list-item v-else :key="item.title" > 
                        <!-- @click="" -->
                        <v-list-item-avatar>
                          <img :src="item.avatar">
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title v-html="item.title"></v-list-item-title>
                          <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>
                    </template>
                  </v-list>
                </v-card>
              </v-col>
            </v-row>
          </div>

          <div v-else class="meetupData">
            <h3 class="text-meetup">예정된 meetup이 없습니다.</h3>
          </div>
            <!-- <h2>{{ item }}</h2> -->
            <!-- {{ text }} -->
            <!-- 캘린더 보여줄 부분 -->           
            <!-- <v-row>
              <v-col>
                <v-sheet height="500">
                  <v-calendar
                    :now="today"
                    :value="today"
                    color="primary"
                  >
                    <template v-slot:day="{ present, past, date }">
                      <v-row
                        class="fill-height"
                      >
                        <template v-if="past && tracked[date]">
                          <v-sheet
                            v-for="(percent, i) in tracked[date]"
                            :key="i"
                            :title="category[i]"
                            :color="colors[i]"
                            :width="`${percent}%`"
                            height="100%"
                            tile
                          ></v-sheet>
                        </template>
                      </v-row>
                    </template>
                  </v-calendar>
                </v-sheet>
              </v-col>
            </v-row> -->
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
  created(){
    // alert(this.$cookie.get("userId"));
    this.userId = this.$cookie.get("userId");
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=`+this.userId
      )
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
        `${SERVER_URL}/meetup/search?id=${this.userId}`
      )
      .then((response) => {
        // console.log(response.data);
        this.meetupData = response.data;
        console.log('밋업정보')
        let meetupDate = this.meetupData.date
        console.log(this.meetupData)
      })
      .catch((error) => {
        console.log(error.response);
        this.meetupData = false
        console.log(this.meetupData)
      });
  },
  data: () => {
    return {
      meetupData: {},
      users: {},
      userId: "",
      meetupItems: [
        { header: 'Meetup 모아보기' },
        { avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg', title: 'Brunch this weekend?', subtitle: "<span class='font-weight-bold'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?" },
        { divider: true, inset: true },
        { avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg', title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>', subtitle: "<span class='font-weight-bold'>to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend." },
        { divider: true, inset: true },
        { avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg', title: 'Oui oui', subtitle: "<span class='font-weight-bold'>Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?" },
      ],
 
      // today: '2020-07-31',
      // tracked: {
      //   '2020-07-29': [23, 45, 10],
      //   '2020-07-08': [10],
      //   '2020-07-17': [0, 78, 5],
      //   '2020-07-20': [0, 0, 50],
      //   '2020-07-05': [0, 10, 23],
      //   '2020-07-04': [2, 90],
      //   '2020-07-03': [10, 32],
      //   '2020-07-02': [80, 10, 10],
      //   '2020-07-11': [20, 25, 10],
      // },
      // colors: ['#FFF176', '#FDD835', '#F9A825'],
      category: ['Development', 'Meetings', 'Slacking'],
      // calender 코드 끝


      contents: "",
      currentItem: '',
      items: [
        'Profile', 'History', 'Message'
      ],

      buttons: [
        {
          title: "확인",
          highlight: true,
          onClick: () => {
            console.log("클릭");
          }
        },
        {
          title: "취소",
          onClick: () => {
            console.log("클릭");
          }
        }
      ],
      options: [
        {
          value: "option1",
          title: "옵션1"
        },
        {
          value: "option2",
          title: "옵션2"
        }
      ]
    };
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