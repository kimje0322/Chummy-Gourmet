<template>
  <div class="user join">
    <!-- <v-app> -->
    <v-bottom-navigation
      scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >
      <v-btn-toggle tile color="deep-purple accent-3" group>
          <v-btn @click="$router.go(-1)" value="center">취소</v-btn>
          <v-btn value="right">참석</v-btn>
      </v-btn-toggle>
    </v-bottom-navigation>

      <v-toolbar-title>
        <v-toolbar dark>
          <a @click="$router.go(-1)">
            <i class="fas fa-chevron-left back"></i>
          </a>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <p class="my-auto">Meet Up</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-toolbar-title>
      <br />


      <!-- 입력 폼 -->
      <div class="party wrapC">

        <!-- 파티 타이틀 -->
        <div class="input-group mb-3">
          <v-text-field v-model="meetup.title" solo readonly></v-text-field>
        </div>
        
        <!-- 파티 내용 -->
        <div class="input-group mb-3">
          <v-textarea v-model="meetup.content" solo readonly></v-textarea>
        </div>

        <!-- 파티 장소 -->
          <v-layout justify-center>
            <v-text-field
                v-model="meetup.location"
                solo
                readonly
                :append-icon="'mdi-map-marker orange--text text--lighten-2'"
            ></v-text-field>
          </v-layout>

        <!-- 파티 날짜 -->
        <v-text-field
            solo
            v-model="meetup.date"
            :append-icon="'mdi-calendar-check orange--text text--lighten-2'"
            readonly
        ></v-text-field>

        <!-- 파티 인원 -->
        <v-text-field solo readonly :value="meetup.curPersonnel + ' / ' + meetup.maxPersonnel ">
        </v-text-field>

        <!-- 파티 성향 -->
        <v-combobox 
          v-model="meetup.personalities" 
          solo multiple chips readonly
        >
        </v-combobox>

      </div>
    <!-- </v-app> -->
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  data: () => {
    return {
      meetup : ''
    }
  },
  mounted() {
    var meetupId = this.$route.query.meetupId;
    axios
    .get(`${SERVER_URL}/meetup/searchByMeetupID/${meetupId}`)
    .then((response) => {
        let meetup = response.data;
        meetup.personalities = this.stringToArray(meetup.personalities);
        this.meetup = meetup;
        
        console.log(this.meetup)
    })  
  },
  methods: {
    stringToArray(strings){
        strings = strings.replace('[', '');
        strings = strings.replace(']', '');
        strings = strings.replace(/(\s*)/g, ''); // 모든공백제거
        strings = strings.split(",");
        return strings;
    }
    
  },

};
</script>

<style>
.back {
  color: white !important;
}
.nothome {
  padding: 0px !important;
}
.party {
  padding: 0px 12px;
}
.section .article {
  position: relative;
  color: black;
}
div {
  display: block;
}
.tit {
  position: absolute;
  top: 8px;
  left: 0;
  font-weight: bold;
}
.fa-chevron-left {
  color: white; 
  margin-left: 7px;
}
/* .btn-person{
  margin: 0px;
} */
</style>