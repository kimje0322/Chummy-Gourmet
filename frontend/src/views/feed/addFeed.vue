<template>
  <section class="user join">
        <!-- 상단 툴바 -->
        <v-toolbar class="mb-1" dense elevation="1">
          <v-icon @click="$router.go(-1)">
            mdi-arrow-left
          </v-icon>
          <v-spacer></v-spacer>
          <p class="my-auto">피드 작성</p>
          <v-spacer></v-spacer>
        </v-toolbar>
      <!-- <v-toolbar-title>
        <v-toolbar dark>
          <a @click="back">
            <i class="fas fa-chevron-left back"></i>
          </a>
          <v-dialog dark v-model="dialog" max-widht="250">
            <v-list>
              작업을 취소하시겠습니까?
              <v-list-item v-for="(item, index) in items" :key="index" @click="doit(item)">
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-dialog>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <p class="my-auto">Posting</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <div v-if="userpage1" @click="reviseAll()">
            <i class="fas fa-check"></i>
          </div>
          <div v-else-if="revise" @click="reviseImg()">
            <i class="fas fa-check"></i>
          </div>
          <div v-else @click="addImg">
            <i class="fas fa-check"></i>
          </div>
        </v-toolbar>
      </v-toolbar-title> -->

      <div>
        <!-- <p>{{this.postuserid}}</p> -->
        <div style="padding: 18px 25px;">
          <div>
            <div style="padding: 12px 0;">
              <div style="margin-right: 8px;">
                <span class="prf" style="margin-left: 0px; ">
                  <img
                    style="height: 32px; width: 32px; border-radius: 50%;"
                    :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+ userimg"
                  />
                </span>
                <span>{{username}}</span>
                <div style="float: right;">
                  <div @click="onClickImageUpload">
                    <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages" /> -->
                    <i class="fa fa-images" style="margin-top: 6px; width: 20px; height: 20px;"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <img
            id="imageview"
            style="width: 100%"
            v-if="userpage1&&userpage2"
            :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+postimg"
          />
          <img
            id="imageview"
            style="width: 100%"
            v-else-if="revise && revise3"
            :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+postimg"
          />
          <!-- <img id="imageview" style="width: 100%" v-if="userpage1" :src="postimg" /> -->
          <img id="imageview" style="width: 100%" v-if="revise1" :src="postimg" />
          <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages" /> -->
          <!-- <v-img v-if="postimgurl" :src="postimgurl"></v-img> -->
          <v-img v-else :src="postimgurl"></v-img>
          <v-textarea v-if="revise || userpage1" style="margin-top: 0;" v-model="content"></v-textarea>

          <v-textarea
            v-if="!revise&&!userpage1"
            v-model="postcontent"
            placeholder="내용입력"
            style="margin-top: 0;"
          ></v-textarea>
        </div>

        <!-- <div style="padding: 12px; 0;">
          <div style="margin: 0 12px; border: solid 1px var(--divider); border-radius: 8px;">
            <div style="6px;" aria-label="게시물에 이미지 추가">adfadfffgdsgsdㅗㅓㅗㅓㅛㅛㅅ</div>
          </div>
        </div>-->
        <input ref="imageInput" type="file" hidden @change="onChangeImages" />
        <!-- <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn> -->
        <!-- <v-btn @click="addImg">입력</v-btn> -->
      </div>
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
      username: "",
      userimg: "",
      revise: false,
      revise1: false,
      revise3: true,
      uploadimg: false,
      items: [{ title: "예" }, { title: "아니오" }],
      dialog: false,
      userpage1: false,
      userpage2: true,
    };
  },
  created() {
    if (this.$route.params.userpage) {
      this.userpage1 = true;
    } else {
      this.userpage1 = false;
    }

    if (this.$route.params.postid >= 0) {
      if (this.userpage1) {
        // this.userpage = this.$router.params.userpage
        this.postimg = this.$route.params.postimage;
        this.content = this.$route.params.postcontent;
      } else {
        this.revise = true;
        this.postimg = this.$route.params.postimage;
        this.content = this.$route.params.postcontent;
      }
    }
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((response) => {
        this.username = response.data.userNickname;
        this.userimg = response.data.userImg;
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  methods: {
    doit(item) {
      if (item.title == "예") {
        router.go(-1);
        this.dialog = false;
      } else {
        this.dialog = false;
      }
    },
    back() {
      if (this.revise) {
        this.dialog = true;
      } else {
        router.go(-1);
      }
    },
    addImg() {
      //이미지 서버에 전송해서 저장하는부분
      const file = new FormData();
      file.append("file", this.file);
      axios
        .post(`${SERVER_URL}/post/img`, file)

        .then((response) => {
          this.postimgurl = response.data;
          this.addPost();
        })

        .catch((error) => {
          console.log(error.response);
          alert("이미지 전송 실패");
        });
    },
    reviseAll() {
      if (!this.uploadimg) {
        var repost = {
          postcontent: this.content,
          postid: this.$route.params.postid,
          postimgurl: this.postimg,
        };

        axios
          .put(`${SERVER_URL}/post`, repost)
          .then((response) => {
            router.push({ name: "UserInfo" });
          })
          .catch((error) => {
            console.log(error.response);
            alert("이미지 전송 실패");
          });
      } else {
        const file = new FormData();
        file.append("file", this.file);
        axios
          .post(`${SERVER_URL}/post/img`, file)

          .then((response) => {
            this.postimgurl = response.data;

            var repost = {
              postcontent: this.content,
              postid: this.$route.params.postid,
              postimgurl: this.postimgurl,
            };
            axios
              .put(`${SERVER_URL}/post`, repost)

              .then((response) => {
                router.push({ name: "UserInfo" });
              })

              .catch((error) => {
                console.log(error.response);
                alert("이미지 전송 실패");
              });
          })

          .catch((error) => {
            console.log(error.response);
            alert("이미지 전송 실패");
          });
      }
    },

    reviseImg() {
      if (!this.uploadimg) {
        var repost = {
          postcontent: this.content,
          postid: this.$route.params.postid,
          postimgurl: this.postimg,
        };
        axios
          .put(`${SERVER_URL}/post`, repost)
          .then((response) => {
            router.push({ name: "NewsFeed" });
          })
          .catch((error) => {
            console.log(error.response);
            alert("이미지 전송 실패");
          });
      } else {
        const file = new FormData();
        file.append("file", this.file);
        console.log(file);
        axios
          .post(`${SERVER_URL}/post/img`, file)

          .then((response) => {
            this.postimgurl = response.data;
            var repost = {
              postcontent: this.content,
              postid: this.$route.params.postid,
              postimgurl: this.postimgurl,
            };
            axios
              .put(`${SERVER_URL}/post`, repost)

              .then((response) => {
                router.push({ name: "NewsFeed" });
              })

              .catch((error) => {
                console.log(error.response);
                alert("이미지 전송 실패");
              });
          })

          .catch((error) => {
            console.log(error.response);
            alert("이미지 전송 실패");
          });
      }
    },
    //게시물을 DB에 저장하는 부분
    addPost() {
      if (this.revise1) {
        var repost = {
          postcontent: this.postcontent,
          postimgurl: this.postimg,
          postuserid: this.postuserid,
        };
        console.log(repost);
        axios
          .post(`${SERVER_URL}/post`, repost)

          .then((response) => {
            alert("성공");
            this.$router.push("/newsfeed");
          })

          .catch((error) => {
            console.log(error.response);
            alert("데이터 전송 실패");
          });
      } else {
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
      }
    },

    onClickImageUpload() {
      this.$refs.imageInput.click();
    },

    onChangeImages(e) {
      if (this.$route.params.postid >= 0) {
        // if(this.userpage1){
        //   this.file = e.target.files[0];
        //   this.postimg = URL.createObjectURL(this.file);
        //   // this.revise1 = false;
        //   // this.revise3 = true

        // }else {
        this.uploadimg = true;

        this.file = e.target.files[0];
        this.postimg = URL.createObjectURL(this.file);
        if (this.userpage1) {
          this.revise1 = true;
          this.revise3 = false;
          this.userpage2 = false;
        } else {
          this.revise1 = true;
          this.revise3 = false;
        }
        // }
      } else {
        this.file = e.target.files[0];
        this.postimgurl = URL.createObjectURL(this.file);
      }
      console.log(e.target.files);
      console.log(this.postimg);
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
