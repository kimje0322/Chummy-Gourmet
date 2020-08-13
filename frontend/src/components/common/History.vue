<template>
  <v-layout v-if="postlst.length > 0" class="entireClass">
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
    
  <!-- 피드 없을 때 -->
  <v-layout v-else class="entireClass">
    <div class="aligncss"> 
      <i class="far fa-file-image fa-5x"></i>
      <h3 class="mt-5">등록된 게시물이 없습니다.</h3>
    </div>
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
        this.$router.push({name :'PostDetail', query: {post_content: post.post_content
        ,post_id : post.post_id, post_img_url : post.post_img_url,
        post_like : post.post_like, post_userid : post.post_userid, user_img:post.user_img,
        user_nickname : post.user_nickname, comment:comment , userId : this.userId}});
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

<style scoped>
  .entireClass{
    padding: 20px;
  }
  .aligncss {
    margin: 70px 0 0 0;
    color: rgba(0,0,0,.6);
    width: 100%;
    text-align: center;
  }
</style>