<template>
<div style="padding : 0">
    <!-- 가게이름 나중에 밑으로 스크롤 나오게 -->
  <input v-model="name" type="text" placeholder="가게 이름" style="width : 100%; border : 1px solid">
  <!-- 종류 고를 수 있게 -->
  <input v-model="category" type="text" placeholder="종류" style="width : 100%; border : 1px solid">
  <!-- 작성자 명 나중에 팀원들도 적을 수 있게 해야함// 작성자는 쿠키에 가지고있는 아이디로 한다.-->
  <input v-model="writer" type="text" placeholder="작성자" style="width : 100%; border : 1px solid">
  <!-- 내용 나중에 팀원들도 적을 수 있게 해야함 -->
  <input v-model="content" type="text" placeholder="내용" style="width : 100%; border : 1px solid">


  <v-btn width="100%" @click="addReview">작성</v-btn>
       
        
</div>
</template>
<script>
import axios from "axios";

// const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
const SERVER_URL = "http://localhost:8080";

export default {
   data () {
      return {
        name : "",
        category : "",
        writer : "",
        content : ""
      }
    },
    methods :{
      addReview(){
         axios
        .get(
          `${SERVER_URL}/review/add?name=${this.name}&category=${this.category}&writer=${this.writer}&content=${this.content}&`
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