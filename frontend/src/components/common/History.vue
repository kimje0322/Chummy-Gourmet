<template>
  <v-layout class="entireClass">
      <v-row>
        
      <v-col v-for="(lst,i) in postlst" :key="i" class="d-flex child-flex" cols="4">
          <v-card flat tile class="d-flex">
              {{lst.postimgurl}}
              <v-img @click="detailInfo(lst,commentlst[i])"
                :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+lst.post_img_url"
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
        items: [],
        userId : "",
        postlst: [],
        commentlst :[]
      }
    },
    methods:{
      detailInfo(post,comment) {
        let item = {
        post: post,
        comment : comment,
        };
        this.$router.push({name :'PostDetail', params: item});
      },
    },
    created(){
      this.userId = this.proptoTopsub.userId
      axios
        .get(`${SERVER_URL}/userpage/getuserpost?userId=`+this.userId)
        .then((response) => {
            this.postlst = response.data.data
            this.commentlst = response.data.comment;
        })  
    }

  }
</script>

<style>
  .entireClass{
    padding: 20px;
  }
</style>