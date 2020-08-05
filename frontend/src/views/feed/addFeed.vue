<template>
  <div>
    <p>{{this.postuserid}}</p>
     <input ref="imageInput" type="file" hidden @change="onChangeImages">
    <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
    <v-img
        v-if="postimgurl" :src="postimgurl"
    ></v-img>
    <v-textarea v-model="postcontent" placeholder="내용입력"></v-textarea>
    <v-btn @click="addImg">입력</v-btn>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";


const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";


export default {
data() {
        return {
            postcontent:"",
            postimgurl:"",
            postuserid: this.$cookie.get("userId")
        }
    },
    created(){
      console.log(this.$cookie.get("userId"));
    },
    methods: {
       
        addImg(){
          //이미지 서버에 전송해서 저장하는부분
             const file = new FormData();
             file.append('file',this.file);
             axios
              .post(`${SERVER_URL}/post/test`,
                 file
                )

              .then((response) => {
                this.postimgurl = response.data;
                console.log(this.postimgurl);
                this.addPost();
              })

              .catch((error) => {
                console.log(error.response);
                alert("이미지 전송 실패");
              });
        },

          //게시물을 DB에 저장하는 부분
         addPost(){
           var newpost ={
                    postcontent:this.postcontent,
                   postimgurl:this.postimgurl,
                   postuserid:this.postuserid
           }
              axios
              .post(`${SERVER_URL}/post`,
                 newpost
                )

              .then((response) => {
                alert("성공");
                this.$router.push("/newsfeed")
              })

              .catch((error) => {
                console.log(error.response);
                alert("데이터 전송 실패");
              });
        },


        onClickImageUpload() {
            this.$refs.imageInput.click();
        },

        onChangeImages(e) {
            console.log(e.target.files)
            this.file = e.target.files[0];
            this.postimgurl = URL.createObjectURL(this.file);
        },
        insert(){
        }
    },

}
</script>

<style>

</style>