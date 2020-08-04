<template>
  <div>
      <input ref="imageInput" type="file" hidden @change="onChangeImages">
    <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
    <v-img
        v-if="imageUrl" :src="imageUrl"
    ></v-img>
    <v-img src="http://i3b302.p.ssafy.io:8080/post/img?imgname=5pMrYo1oaADwgpxFVeVcqg==.png">나와라잇</v-img>
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
            imageUrl: null,
            file : null,
            img:"test"
        }
    },
    methods: {
        onClickImageUpload() {
            this.$refs.imageInput.click();
        },
        onChangeImages(e) {
            console.log(e.target.files)
            this.file = e.target.files[0];
            this.imageUrl = URL.createObjectURL(this.file);

            const file = new FormData();
            file.append('file',this.file);

            //이미지 서버에 전송해서 저장하는부분
             axios
              .post(`${SERVER_URL}/post/test`,
                 file
                )

              .then((response) => {
                console.log(response);
              })

              .catch((error) => {
                console.log(error.response);
                alert("실패");
              });
        },
        insert(){
        }
    },

}
</script>

<style>

</style>