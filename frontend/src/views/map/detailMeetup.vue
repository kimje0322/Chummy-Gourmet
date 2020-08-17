<template>

  <div class="user join">
    <v-bottom-navigation
      scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >
      <v-btn-toggle tile color="deep-purple accent-3" >
          <v-btn  @click="$router.go(-1)">취소</v-btn>
          <v-btn  v-if="isGuest" @click="cancleMeetup">{{text}}</v-btn>
          <v-btn  v-else-if="meetup.curPersonnel==meetup.maxPersonnel" disabled>{{text}}</v-btn>
          <v-btn  v-else-if="isRequest" @click="cancleRequestMeetup">{{text}}</v-btn>
          <v-btn  v-else @click="requestDialog = true">{{text}}</v-btn>
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

    <GoChatForMeetUp :meetup ="meetup" />
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


      <!-- 밋업 참석 신청 DIALOG -->
      <v-dialog v-model="requestDialog" max-width="290">
          <v-card>
            <v-card-title class="text-title">밋업 요청 메세지
              <v-spacer></v-spacer>
              <v-btn icon>
                <v-icon>mdi-close</v-icon>
              </v-btn>

            </v-card-title>
            <v-divider class="mt-1 mb-6"></v-divider>
            <v-card-text>
              <v-textarea v-model="requestMessage" color="orange lighten-1">
                <template v-slot:label>
                  <div class="small">
                    밋업 요청 메세지를 작성해주세요 :)
                  </div>
                </template>
              </v-textarea>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn text @click="requestDialog = false">
                닫기
              </v-btn>

              <v-btn color="orange lighten-1" text
                @click="requestMeetup"
                @click.stop="requestDialog = false"
              >
                신청하기
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
  </div>
</template>

<script>
import axios from "axios";
import GoChatForMeetUp from '../../components/common/GoChatForMeetUp';

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  components:{
    GoChatForMeetUp
  },
  data: () => {
    return {
      meetup : '',
      requestDialog : false,
      requestMessage : '',
      text : '참석 신청',
      isGuest : false,
      isRequest : false,
    }
  },
  created() {
    var meetupId = this.$route.query.meetupId;
    
    /* 밋업 정보 불러오기 */
    this.init();

    /* 해당 밋업에 참석 신청중인지 확인 */
    axios
    .get(`${SERVER_URL}/meetup/request/${meetupId}`)
    .then((response) => {
      if(response.data == 'success'){
        let requests = response.data.object; 
        requests.forEach(request => {
          // if(request.guestId == "70"){
          if(request.guestId == this.$cookie.get("userId")){
            this.isRequest = request.id;
            this.text = "신청 취소";
          }
        });
      }
    })

    /* 해당 밋업의 멤버 리스트 조회해서 참석중인지 확인 */
    axios
    .get(`${SERVER_URL}/meetup/members/${meetupId}`)
    .then((response) => {
      let guests = response.data.object;
      guests.forEach(guest => {
        // if(guest.userId == "70"){
        if(guest.userId == this.$cookie.get("userId")){
          this.isGuest = true;
          this.text = "참석 취소";
        }
      });
    })  


  },
  methods: {
    init(){
      var meetupId = this.$route.query.meetupId;
      axios
      .get(`${SERVER_URL}/meetup/search/${meetupId}`)
      .then((response) => {
          let meetup = response.data;
          meetup.personalities = this.stringToArray(meetup.personalities);
          this.meetup = meetup;
      })
    },
    cancleMeetup(){
      var meetupId = this.$route.query.meetupId;
      axios
      .delete(`${SERVER_URL}/meetup?meetupId=${meetupId}&userId=${this.$cookie.get("userId")}`)
      .then((response) => {
        this.isGuest = false;
        this.isRequest = false;
        this.text = "참석 신청";
        this.init();
        alert("밋업 참석이 취소되었습니다.");
      })  
    },
    cancleRequestMeetup(){
      console.log(this.isRequest)
      axios
      .delete(`${SERVER_URL}/meetup/request/${this.isRequest}`)
      .then((response) => {
        this.isRequest = false;
        this.text = "참석 신청";
        alert("밋업 참석 신청이 취소되었습니다.");
      })  
    },
    requestMeetup(){
      let request = {
        meetupId : this.meetup.id,
        hostId : this.meetup.master,
        guestId : this.$cookie.get("userId"),
        // guestId : '70',
        requestMessage : this.requestMessage,
      };
      console.log(request);
      axios
      .post(`${SERVER_URL}/meetup/request`, request)
      .then((response) => {
        alert("신청이 완료되었습니다.")
        this.$router.push("/map");
      })  
    },
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