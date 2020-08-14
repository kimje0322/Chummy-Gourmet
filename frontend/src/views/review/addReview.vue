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
          <p class="my-auto">Review</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <!-- <div v-if="revise" @click="reviseImg">
            <i class="fas fa-check"></i>
          </div> -->
          <!-- <div v-else @click="addImg">
            <i class="fas fa-check"></i>
          </div> -->
          <!-- <a @click="addImg"></a> -->
        </v-toolbar>
      </v-toolbar-title>

      <div>
        <!-- <p>{{this.postuserid}}</p> -->
        
        <!-- <div style="padding: 18px 25px;">
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
                    <input ref="imageInput" type="file" hidden @change="onChangeImages" />
                    <i class="fa fa-images" style="margin-top: 6px; width: 20px; height: 20px;"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <img
            id="imageview"
            style="width: 100%"
            v-if="revise && revise3"
            :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+postimg"
          />
          <img id="imageview" style="width: 100%" v-if="revise1" :src="postimg" />
          <input ref="imageInput" type="file" hidden @change="onChangeImages" />
          <v-img v-if="postimgurl" :src="postimgurl"></v-img>
          <v-img v-else :src="postimgurl"></v-img>
          <v-textarea v-if="revise" style="margin-top: 0;" v-model="content"></v-textarea>
        </div> -->
        <v-form
          ref="form" v-model="form"
          class="pa-8 pt-8"
        >
          <v-text-field
            v-model="review.title"
            filled clearable
            color="deep-purple"
            label="Title"
          ></v-text-field>
          <v-textarea
            v-model="review.content"
            filled clearable
            color="deep-purple"
            label="Contents"
            rows="7"
          ></v-textarea>
        </v-form>
        <v-divider></v-divider>
        <v-card-actions class="pa-8 pt-8">
          <v-btn @click="$refs.form.reset()" text>
            Clear
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            :disabled="!form"
            :loading="isLoading"
            class="white--text"
            color="deep-purple accent-4"
            depressed
            @click="addReview"
          >Submit</v-btn>
        </v-card-actions>

        <!-- <div style="padding: 12px; 0;">
          <div style="margin: 0 12px; border: solid 1px var(--divider); border-radius: 8px;">
            <div style="6px;" aria-label="게시물에 이미지 추가">adfadfffgdsgsdㅗㅓㅗㅓㅛㅛㅅ</div>
          </div>
        </div>-->
        <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages" /> -->
        <!-- <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn> -->
        <!-- <v-btn @click="addImg">입력</v-btn> -->
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import router from "@/routes";

// const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
const SERVER_URL = "https://localhost:8080";

export default {
  data() {
    return {
      review : {
        writer : this.$cookie.get("userId"),
        title : '',
        content : '',

        category : '',
        restId : '',
        meetupId : '',
      },



      revise: false,
      revise1: false,
      revise3: true,

      form: false,
      isLoading: false,
      rules: {
        email: v => !!(v || '').match(/@/) || 'Please enter a valid email',
        length: len => v => (v || '').length >= len || `Invalid character length, required ${len}`,
        password: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
          'Password must contain an upper case letter, a numeric character, and a special character',
        required: v => !!v || 'This field is required',
      },
    };
  },
  created() {
    console.log(this.$cookie.get("userId"));
    // console.log(this.$route.params);

    

    // if (this.$route.params.postid >= 0) {
    //   console.log("여기");
    //   console.log(this.$route.params);
    //   this.revise = true;
    //   console.log(this.revise);
    //   this.postimg = this.$route.params.postimage;
    //   this.content = this.$route.params.postcontent;
    //   console.log(this.content);
    //   console.log(this.postimg);
    // }
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((response) => {
        console.log(response);
        this.username = response.data.userNickname;
        this.userimg = response.data.userImg;
        console.log(this.username);
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  methods: {
    addReview(){
      var meetup = this.$route.params;
      this.review.category = meetup.category;
      this.review.meetupId = meetup.id;
      this.review.restId = meetup.restId;
      axios
        .put(`${SERVER_URL}/review`, this.review)
        .then((response) => {
          alert("리뷰가 등록되었습니다.");
          this.$router.go(-1);
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
    // addImg() {
    //   //이미지 서버에 전송해서 저장하는부분
    //   const file = new FormData();
    //   file.append("file", this.file);
    //   console.log(file);
    //   axios
    //     .post(`${SERVER_URL}/post/img`, file)

    //     .then((response) => {
    //       this.postimgurl = response.data;
    //       console.log("여기");
    //       console.log(this.postimgurl);
    //       this.addPost();
    //     })

    //     .catch((error) => {
    //       console.log(error.response);
    //       alert("이미지 전송 실패");
    //     });
    // },

    // reviseImg() {
    //   const file = new FormData();
    //   file.append("file", this.file);
    //   console.log(file);
    //   axios
    //     .post(`${SERVER_URL}/post/img`, file)

    //     .then((response) => {
    //       this.postimgurl = response.data;
    //       console.log("여기");
    //       console.log(this.postimgurl);
    //       var repost = {
    //         postcontent: this.content,
    //         postid: this.$route.params.postid,
    //         postimgurl: this.postimgurl
    //       };
    //       console.log(this.postimgurl);
    //       console.log("여기여기");
    //       console.log(repost);
    //       axios
    //         .put(`${SERVER_URL}/post`, repost)

    //         .then((response) => {
    //           // this.postimgurl = response.data;
    //           // console.log(this.postimgurl);
    //           console.log("여기여기여기역이겨이겨")
    //           router.push({ name: "NewsFeed" });
    //         })

    //         .catch((error) => {
    //           console.log(error.response);
    //           alert("이미지 전송 실패");
    //         });
    //     })

    //     .catch((error) => {
    //       console.log(error.response);
    //       alert("이미지 전송 실패");
    //     });
    // },
    //게시물을 DB에 저장하는 부분
    // addPost() {
      // if (this.revise1) {
      //   var repost = {
      //     postcontent: this.postcontent,
      //     postimgurl: this.postimg,
      //     postuserid: this.postuserid,
      //   };
      //   console.log(repost);
      //   axios
      //     .post(`${SERVER_URL}/post`, repost)

      //     .then((response) => {
      //       alert("성공");
      //       this.$router.push("/newsfeed");
      //     })

      //     .catch((error) => {
      //       console.log(error.response);
      //       alert("데이터 전송 실패");
      //     });
      // } else {
      //   var newpost = {
      //     postcontent: this.postcontent,
      //     postimgurl: this.postimgurl,
      //     postuserid: this.postuserid,
      //   };
      //   axios
      //     .post(`${SERVER_URL}/post`, newpost)

      //     .then((response) => {
      //       alert("성공");
      //       this.$router.push("/newsfeed");
      //     })

      //     .catch((error) => {
      //       console.log(error.response);
      //       alert("데이터 전송 실패");
      //     });
      // }
    // },

    // onClickImageUpload() {
    //   this.$refs.imageInput.click();
    // },

    // onChangeImages(e) {
    //   if (this.$route.params.postid >= 0) {
    //     this.file = e.target.files[0];
    //     this.postimg = URL.createObjectURL(this.file);
    //     this.revise1 = true;
    //     this.revise3 = false;
    //   } else {
    //     this.file = e.target.files[0];
    //     this.postimgurl = URL.createObjectURL(this.file);
    //   }
    //   console.log(e.target.files);
    //   console.log(this.postimg);
    // },

    // insert() {},
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
