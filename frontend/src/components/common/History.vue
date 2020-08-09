<template>
  <v-layout class="entireClass">
      <v-row>
        
      <v-col v-for="item in items" :key="item.post_content" class="d-flex child-flex" cols="4">
          <v-card flat tile class="d-flex">
              <v-img @click="detailInfo(item)"
                :src="item.post_img_url" 
                aspect-ratio="1" 
                class="grey lighten-2"
              >
              <template v-slot:placeholder>
              <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
              >
                  <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
              </v-row>
              </template>
              </v-img>
          </v-card>
      </v-col>
      </v-row>
  </v-layout>
</template>


<script>
import axios from "axios";
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

  export default {
    props:{
      proptoTopsub: {
        type : Object
      },
    },
    data () {
      return {
        items: [
            {post_content:'온천칼국수',post_img_url:'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcStF6ZkDCp6EzlV4RGCCbC2NBy1NOKJkIweEg&usqp=CAU'},
            {post_content:'맛찬들',post_img_url:'https://mobile-api.tabling.co.kr/api/restaurantimage/9554'},
            {post_content:'원조집',post_img_url:'https://t1.daumcdn.net/cfile/tistory/990A94475C5030480C'},
            {post_content:'해양산',post_img_url:'https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FOzj2K%2FbtqASzmBb2C%2FryfmpfOQQ0S8R3EQbLCBKk%2Fimg.jpg'},
        ],
        userId : "",
      }
    },
    methods:{
      detailInfo(item) {
        alert(item.post_content)
      },
    },
    created(){
      this.userId = this.proptoTopsub.userId
      axios
        .get(
          `${SERVER_URL}/post/getuserpost?userId=${this.userId}`
        )
        .then((response) => {
          console.log(response.data)
          for (let i = 0; i < response.data.data.length; i++) {
            this.items.push({
              post_id : response.data.data[i].post_content,
              post_content :  response.data.data[i].post_content,
              post_date : response.data.data[i].post_date,
              post_like : response.data.data[i].post_like,
              post_update_date : response.data.data[i].post_update_date,
              post_img_url : SERVER_URL+"/img/user?imgname=" +response.data.data[i].post_img_url
            })
          }
          
        })  
    }

  }
</script>

<style>
  .entireClass{
    padding: 20px;
  }
</style>