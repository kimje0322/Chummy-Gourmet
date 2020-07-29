<template>
  <v-app>
    <!-- 상단 -->
     <v-toolbar-title>
      <Top></Top>
      <Topsub :proptoTopsub="users"></Topsub>
    </v-toolbar-title>
    <!-- 가운데 부분 -->
    <div>
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
                <h2>Meet Up</h2>
                <!-- <h2>{{ item }}</h2> -->
                {{ text }}
                <!-- 캘린더 보여줄 부분 -->           
                <v-row>
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

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

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
      console.log(response.data);
      this.users = response.data;
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  data: () => {
    return {
      users: {},
      userId: "",
      today: '2020-07-31',
      tracked: {
        '2020-07-29': [23, 45, 10],
        '2020-07-08': [10],
        '2020-07-17': [0, 78, 5],
        '2020-07-20': [0, 0, 50],
        '2020-07-05': [0, 10, 23],
        '2020-07-04': [2, 90],
        '2020-07-03': [10, 32],
        '2020-07-02': [80, 10, 10],
        '2020-07-11': [20, 25, 10],
      },
      colors: ['#FFF176', '#FDD835', '#F9A825'],
      category: ['Development', 'Meetings', 'Slacking'],
      // calender 코드 끝
      contents: "",
      currentItem: 'tab-Web',
      items: [
        'Profile', 'History', 'Message'
      ],
      text: '돈독이님의 Meet Up 일정입니다.',

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

<style scoped>
</style>