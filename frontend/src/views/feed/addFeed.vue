<template>
  <section class="user join">
    <v-app>
      <v-toolbar-title>
        <v-toolbar dark>
          <a @click="$router.go(-1)">
            <i class="fas fa-chevron-left back"></i>
          </a>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <p class="my-auto">Posting</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-toolbar-title>

      <div>
        <!-- <p>{{this.postuserid}}</p> -->
        <div style="padding: 0 16px;">
          <div>
            <div style="padding: 12px 0;">
              <div style="margin-right: 8px;">
                <span class="prf" style="margin-left: 0px; ">
                  <img
                    style="height: 32px; width: 32px; border-radius: 50%;"
                    :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+ userimg"
                  />
                </span>
              </div>
              <div style="flex-direction: column;">
                <span>{{username}}</span>
              </div>
            </div>
          </div>

          <v-textarea v-model="postcontent" placeholder="내용입력"></v-textarea>
        </div>
        <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages" /> -->
        <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
        <v-img v-if="postimgurl" :src="postimgurl"></v-img>
        <v-btn @click="addImg">입력</v-btn>
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      postcontent: "",
      postimgurl: "",
      postuserid: this.$cookie.get("userId"),
      username : "",
      userimg : ""
    };
  },
  created() {
    console.log(this.$cookie.get("userId"));
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((response) => {
        // console.log("alfkjsdsi");
        console.log(response);
        this.username = response.data.userNickname;
        this.userimg = response.data.userImg;
        console.log(this.username)
      })
      .catch((error) => {
        console.log(error.response)
      });
    console.log(this.username);
  },
  methods: {
    addImg() {
      //이미지 서버에 전송해서 저장하는부분
      const file = new FormData();
      file.append("file", this.file);
      axios
        .post(`${SERVER_URL}/post/img`, file)

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
    addPost() {
      var newpost = {
        postcontent: this.postcontent,
        postimgurl: this.postimgurl,
        postuserid: this.postuserid,
      };
      axios
        .post(`${SERVER_URL}/post`, newpost)

        .then((response) => {
          alert("성공");
          this.$router.push("/newsfeed");
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
      console.log(e.target.files);
      this.file = e.target.files[0];
      this.postimgurl = URL.createObjectURL(this.file);
    },
    insert() {},
  },
};
</script>

<style scoped>
.prf {
  width: 32px;
  height: 32px;
  background-color: rgba(var(--b3f, 250, 250, 250), 1);
  border-radius: 50%;
  box-sizing: border-box;
  /* display: block; */
  /* -webkit-box-flex: 0; */
  flex: 0 0 auto;
  overflow: hidden;
  position: relative;
  margin: 0 16px;
  padding: 0;
  border: 0;
}
</style>
