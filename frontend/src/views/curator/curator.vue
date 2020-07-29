<template>
<div style="padding : 0">
  <input v-model="location" type="text" placeholder="원하는 지역 검색" style="width : 100%; border : 1px solid">

  <v-btn width="100%" @click="search">검색</v-btn>
  <p>{{location}}에 대한 결과</p>
       
        
</div>
</template>
<script>
import axios from "axios";

// const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
const SERVER_URL = "http://localhost:8080";

export default {
   data () {
      return {
        location : ""
      }
    },
    methods :{
      search(){
         axios
        .get(
          `${SERVER_URL}/curation?location=${this.location}`
        )

        .then((response) => {
         alert("성공");
        })

        .catch((error) => {
          console.log(error.response);
          alert("로그인 실패");
        });
      }
    },
  components: { }
};
</script>