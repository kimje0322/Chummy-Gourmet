<template>
  <div>
      <input ref="imageInput" type="file" hidden @change="onChangeImages">
    <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
    <v-img
        v-if="imageUrl" :src="imageUrl"
    ></v-img>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";


// const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
const SERVER_URL = "http://localhost:8080";


export default {
data() {
        return {
            imageUrl: null,
            file : null
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
             axios
              .post(`${SERVER_URL}/post/test`,file)

              .then((response) => {
                this.restaurants = response.data.list;
                console.log(this.restaurants);
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