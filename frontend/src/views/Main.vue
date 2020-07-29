<template>
  <v-app>
    <!-- 상단 -->
     <v-toolbar-title>
      <!-- <Top></Top> -->
      <Topsub></Topsub>
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
                <h2>{{ item }}</h2>
                {{ text }}
              </v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
    </div>
    
    <!-- 하단 -->
    <!-- <Footer></Footer> -->

  </v-app>
  
</template>

<script>
// import Top from "../components/common/Top";
import Topsub from "../components/common/Topsub";
import Message from "../components/common/Message";
import History from "../components/common/History";
// import Footer from "../components/common/Footer";
import "../assets/css/components.scss";

export default {
  name: "components",
  components: {
    // Top,
    Topsub,
    Message,
    History,
    // Footer,
  },
  data: () => {
    return {
      contents: "",
      currentItem: 'tab-Web',
      items: [
        'Profile', 'History', 'Message'
      ],
      text: '대한민국 국적의 토트넘 홋스퍼 FC 소속 축구 선수이자 대한민국 축구 국가대표팀의 주장이며 프리미어 리그와 UEFA 챔피언스 리그 아시아인 통산 최다 득점자로 2019년에는 아시아 선수 최초로 FIFA/FIFPro 월드 XI 후보 55인, 발롱도르 후보 30인에 선정되었다.',
      methods: {
      },
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