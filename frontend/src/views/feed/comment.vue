<template>
  <section class="user join">
    <!-- <v-bottom-navigation
      v-if="$route.name === 'NewsFeed'"
      scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >
    </v-bottom-navigation>-->
    <v-app>
      <v-toolbar-title>
        <v-toolbar dark>
          <a @click="$router.go(-1)">
            <i class="fas fa-chevron-left back"></i>
          </a>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <p class="my-auto">Comment</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-toolbar-title>
      <div style="position: relative;">
        <section class="comment">
          <div class="top">
            <span class="prf">
              <img
                style="height: 100%; width: 100%; -webkit-user-drag: none;"
                :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+this.myimg"
              />
            </span>
            <form style="height: 42px;" class="text-box">
              <textarea
                aria-label="here"
                autocomplete="off"
                autocorrect="off"
                data-focus-visible-added
                style="height: 18px;"
                class="text"
                placeholder="write here!"
                v-model="commentText"
              ></textarea>
              <button @click="onCreate()" class="upload">게시</button>
            </form>
          </div>
          <div v-if="recomment" class="top">
            <span class="prf">
              <img
                style="height: 100%; width: 100%; -webkit-user-drag: none;"
                :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+this.myimg"
              />
            </span>
            <form style="height: 42px; " class="text-box">
              <!-- v-model="recommentText" -->
              <textarea style="height: 18px;" class="text" v-model="commentcontent"></textarea>
              <button @click="onRewrite()" class="upload">수정</button>
            </form>
          </div>
        </section>
        <div class="lst">
          <ul style="height: 494px;">
            <div class="lst-div" tabindex="0">
              <li
                style="width: 335px; margin-left: 0px; padding-left: 16px; border-bottom: 1px solid rgba(var(--ce3, 239, 239, 239), 1);"
              >
                <div class="lst-box">
                  <div class="lst-cover">
                    <div
                      class="lst-prf"
                      style="float: left; height: 32px; width: 32px; margin-right: 18px;"
                    >
                      <span class="prf" style="margin-left: 0px;">
                        <img
                          @click="gotoProfile({userid:postuserid, userimg: postuserimg})"
                          style="height: 100%; width: 100%; -webkit-user-drag: none;"
                          :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+postuserimg"
                        />
                      </span>
                    </div>
                    <div
                      style="float: left; flex-direction: column; position: relative;"
                    >
                      <!-- <div style="display: inline-flex;"> -->
                      <h2
                        style="font-size: 14px; font-weight: 600; align-items: center; display: inline-flex; margin-right: 4px;"
                      >
                        <div style="display: flex; flex-direction: column;">
                          <a class="prf-link" @click="gotoProfile({userid:postuserid, userimg : postuserimg})">{{postname}}</a>
                        </div>
                      </h2>
                      <!-- </div> -->
                      <span>
                        {{ postcontent }}
                        <br />
                      </span>
                    </div>
                  </div>
                </div>
              </li>
            </div>
            <ul style="margin-bottom: 16px; padding-top: 0 !important;">
              <div>
                <li
                  style="display: list-item; width: 335px; padding-bottom: 0;"
                  v-for="(lst, i) in commentlst"
                  :key="i"
                >
                  <div style="position: relative;">
                    <div style="width: 307px;">
                      <div
                        class="lst-prf"
                        style="float: left; height: 32px; width: 32px; margin-right: 18px;"
                      >
                        <span class="prf" style="margin-left: 0px;">
                          <img
                            @click="gotoProfileByComment(lst)"
                            style="height: 100%; width: 100%; -webkit-user-drag: none;"
                            :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+lst.userimg"
                          />
                        </span>
                      </div>
                      <div style="float: left; width: 250px;">
                        <!-- <h3 style="display: inline-flex;"></h3> -->
                        <h2
                          style="font-size: 14px; font-weight: 600; align-items: center; display: inline-flex; margin-right: 4px;"
                        >
                          <div style="display: flex; flex-direction: column;">
                            <a class="prf-link" @click="gotoProfileByComment(lst)">{{ lst.usernickname }}</a>
                          </div>
                        </h2>
                        <!-- <h3
                          style="font-size: 14px; font-weight: 600; align-items: center; display: inline-flex; margin: 0; display: flex;"
                        >
                          <div style="display: flex; flex-direction: column;">
                            <a class="prf-link" href="#">dlwlrma</a>
                          </div>
                        </h3>-->
                        <span style="width: 270px;">
                          <!-- {{lst}} -->
                          {{lst.postcomment}}
                          <!-- {{ }} -->
                        </span>
                        <div style="color: #8e8e8e; margin-top: 12px; margin-bottom: 4px;">
                          <a style="margin-right: 12px; color: #8e8e8e;">
                            {{ lst.commentdate | moment("from", "now") }}
                          </a>
                          <!-- v-if="lst.commentid === userId" -->
                          <button v-if="lst.commentuserid  == userid" @click="rewrite(lst)">수정하기</button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- v-if="commentid === user" -->
                  <span  v-if="lst.commentuserid  == userid" style="margin-top: 9px;">
                    <div style="float: right;">
                      <button @click="onDelete(lst)">
                        <div>
                          <span>
                            <i
                              style="display: block; position: relative; height: 14px; width: 14px;"
                              class="far fa-trash-alt"
                            ></i>
                          </span>
                        </div>
                      </button>
                    </div>
                  </span>
                </li>
              </div>
            </ul>
          </ul>
        </div>
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import $ from "jquery";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      commentText: "",
      commentlst: [],
      myimg: "",
      postid: "",
      postnickname: "",
      postuserimg: "",
      postcontent: "",
      postname: "",
      recomment: false,
      cid : "",
      postuserid:"",
      userid : "",
    };
  },
  watch: {
    //   commentText: function (v) {
    //   this.Text();
    //   }
  },
  mounted() {},
  created() {
    // console.log(this.$route.params);
    // console.log("aaaaaaaaaaaaaaaa");
    // console.log(this.$cookie.get("userId"));
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((response) => {
        // console.log("alfkjsdsi");
        console.log(response);
        this.userid = this.$cookie.get("userId");
        this.username = response.data.userNickname;
        this.myimg = response.data.userImg;
      })
      .catch((error) => {
        console.log(error.response);
      });

    axios
      .get(`${SERVER_URL}/post/comment?commentid=${this.$route.query.postid}`)
      .then((response) => {
        this.commentlst = response.data.data;
        this.postname = this.$route.query.postnickname;
        this.postcontent = this.$route.query.postcontent;
        this.postuserimg = this.$route.query.postuserimg;
        this.postuserid = this.$route.query.postuserid;
        // alert(this.postuserid)
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  methods: {
    gotoProfile(user) {
      // 나를 누르면 마이페이지로 이동
      if(user.userid == this.$cookie.get("userId")){
        this.$router.push('/user/info');
      }
      // 타 유저 프로필 페이지로 이동
      else{
        let userImg = `https://i3b302.p.ssafy.io:8080/img/user?imgname=`+user.userimg;
         this.$router.push('/user/profile?userId='+user.userid
        +'&followerFollowing='+true
        +'&userImg='+userImg);
      }
    },
    gotoProfileByComment(user) {
      console.log(user)
      // 나를 누르면 마이페이지로 이동
      if(user.commentuserid == this.$cookie.get("userId")){
        this.$router.push('/user/info');
      }
      // 타 유저 프로필 페이지로 이동
      else{
        // 댓글을 단 사용자들은 내가 팔로잉 중인 사용자가 무조건 적인게 아닐 수있다.그래서 검사
        axios
        .get(
          `${SERVER_URL}/userpage/getfollowerfollowing?userId=`+this.userId+`&followeruserId=`+user.commentuserid
        )
        .then((response) => {
          console.log(response);
          this.username = response.data.userNickname;
          this.myimg = response.data.userImg;
        })
        .catch((error) => {
          console.log(error.response);
        });
        let userImg = `https://i3b302.p.ssafy.io:8080/img/user?imgname=`+user.userimg;
         this.$router.push('/user/profile?userId='+user.commentuserid
        +'&followerFollowing='+true
        +'&userImg='+userImg);
      }
    },
    onDelete(lst) {
      console.log(lst);
      axios
        .delete(`${SERVER_URL}/post/comment?commentid=${lst.commentid}`)
        .then((response) => {
          axios
            .get(
              `${SERVER_URL}/post/comment?commentid=${this.$route.params.postid}`
            )
            .then((response) => {
              console.log(response);
              this.commentlst = response.data.data;
              this.postname = this.$route.params.postnickname;
              this.postcontent = this.$route.params.postcontent;
              this.postuserimg = this.$route.params.postuserimg;
            })
            .catch((error) => {
              console.log(error.response);
            });
        })
        .catch((error) => {});
    },
    onCreate() {
      var commentxt = {
        commentuserid: this.$cookie.get("userId"),
        postcomment: this.commentText,
        postid: this.$route.params.postid,
      };
      this.commentText = "";

      console.log(commentxt);
      axios
        .post(`${SERVER_URL}/post/comment`, commentxt)
        .then((respose) => {
          axios
            .get(
              `${SERVER_URL}/post/comment?commentid=${this.$route.params.postid}`
            )
            .then((response) => {
              console.log(response);
              this.commentlst = response.data.data;
              this.postname = this.$route.params.postnickname;
              this.postcontent = this.$route.params.postcontent;
              this.postuserimg = this.$route.params.postuserimg;
            })
            .catch((error) => {
              console.log(error.response);
            });
        })
        .catch((error) => {});
    },
    rewrite(comment) {
      this.recomment = true
        // var commmenttxt = {
        //   commentuserid : this.$cookie.get("userId"),
        //   commentcontent: comment.postcomment,
        //   postid: this.$route.params.postid,
        // 148 /
        // 5;
      this.cid = comment.commentid
      
      this.commentcontent = comment.postcomment;
      console.log("여기여기");
      console.log(this.commentcontent);
      // axios
      //   .post(`${SERVER_URL}/post/comment`, commmenttxt)
      //   .then((response) => {
      //     alert("수정 완료");
      //   })
    },
    onRewrite() {
      var commentxt = {
        // commentuserid: this.$cookie.get("userId"),
        postcomment: this.commentcontent,
        commentid: this.cid

        // postid: this.$route.params.postid,
      };
      
      axios
        .put(`${SERVER_URL}/post/comment`, commentxt)
        .then((response) => {
          this.recomment = false
          console.log(commentxt)
          alert("성공!");
          axios
            .get(
              `${SERVER_URL}/post/comment?commentid=${this.$route.params.postid}`
            )
            .then((response) => {
              console.log(response);
              this.commentlst = response.data.data;
              this.postname = this.$route.params.postnickname;
              this.postcontent = this.$route.params.postcontent;
              this.postuserimg = this.$route.params.postuserimg;
            })
            .catch((error) => {
              console.log(error.response);
            });
        })
        .catch((error) => {});
    },
    // Text() {
    //     $('.upload').css('color', 'blue')
    // }
  },
};
</script>

<style scoped>
.upload[disabled] {
  opacity: 0.3;
}
.prf-link {
  color: black !important;
  border: 0;
  display: inline;
  position: relative;
  text-decoration: none;
}
.lst-prf {
  margin: 0 18px 0 0;
  display: flex;
  flex-direction: column;
  position: relative;
  padding: 0;
}
.lst-cover {
  display: unset;
  flex-direction: column;
  position: relative;
}
.lst-box {
  flex-direction: row;
  display: flex;
  flex-direction: column;
  position: relative;
}
li {
  padding-bottom: 16px;
  padding-right: 28px;
  /* margin-left: -12px; */
  margin-right: 0;
  padding: 12px 16px;
  width: auto;
  overflow: hidden;
  position: relative;
  margin-top: -5px;
  /* border-bottom: 1px solid rgba(var(--ce3, 239, 239, 239), 1); */
  margin-bottom: 16px;
  display: list-item;
  /* list-style: none; */
}
.lst-div {
  touch-action: manipulation;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  position: relative;
  margin: 0;
  padding: 0;
}
ul {
  box-sizing: content-box;
  height: cal(100% - 32px);
  overflow-y: scroll;
  /* padding: 16px 12px; */
  padding: 12px 28px 16px 0 !important;
  position: absolute;
  /* width: calc(100% - 8px); */
  list-style: none;
  margin: 0;
  display: block;
}
.lst {
  flex: 1 1 auto;
  margin-top: -2px;
  overflow-anchor: none;
  display: flex;
  flex-direction: column;
  position: relative;
  margin: 0;
  padding: 0;
  border: 0;
  vertical-align: baseline;
  /* -webkit-box-direction: normal; */
}
.text {
  text-rendering: auto;
  font-size: 14px;
  /* height: 18px; */
  line-height: 18px;
  background: 0 0;
  border: 0;
  color: rgba(var(--i1d, 38, 38, 38), 1);
  display: flex;
  /* -webkit-box-flex: 1; */
  flex-grow: 1;
  max-height: 80px;
  outline: 0;
  padding: 0;
  resize: none;
  width: 0;
  cursor: text;
  white-space: pre-wrap;
  flex-direction: column;
}

.text-box {
  /* -webkit-box-align: center; */
  /* -webkit-box-orient: horizontal; */
  /* -webkit-box-direction: normal; */
  flex-direction: row;
  display: flex;
  /* -webkit-box-flex: 1; */
  flex-grow: 1;
  flex-shrink: 1;
  position: relative;
  align-items: center;
  /* background-color: white; */
  background-color: rgba(var(--d87, 255, 255, 255), 1);
  /* border-bottom-color: rgb(219, 219, 219); */
  border: 1px solid rgba(var(--b6a, 219, 219, 219), 1);
  border-radius: 30px;
  margin-right: 16px;
  /* margin-top: 8px; */
  /* margin-bottom: 8px; */
  padding: 12px 16px;
  border: 0;
  outline: 0;
  /* padding: 0; */
  resize: none;
  width: 0;
  line-height: 18px;
}
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

.comment {
  color: rgba(var(--f52, 142, 142, 142), 1);
  border: 0;
  padding: 0;
  top: -2px;
  width: 100%;
  border-top: 1px solid rgba(var(--ce3, 239, 239, 239), 1);
  flex-shrink: 0;
  font-size: 14px;
  line-height: 18px;
  min-height: 56px;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-align: stretch;
  /* -webkit-box-direction: normal; */
  /* -webkit-box-pack: center; */
  justify-content: center;
  position: relative;
  flex-direction: column;
  align-items: stretch;
  box-sizing: border-box;
}
.top {
  /* background-repeat: no-repeat; */
  height: 60px;
  /* -webkit-box-align: center; */
  /* -webkit-box-orient: horizontal; */
  /* -webkit-box-direction: normal; */
  /* -webkit-box-pack: start; */
  flex-flow: row wrap;
  align-items: center;
  justify-content: flex-start;
  vertical-align: baseline;
  /* background-color: #efefef; */
  background-color: rgba(var(--bb2, 239, 239, 239), 1);
  /* border-bottom: 1px solid #dbdbdb; */
  border-bottom: 1px solid rgba(var(--b6a, 219, 219, 219), 1);
  border-top: 1px solid #dbdbdb;
  border: 0 solid black;
  box-sizing: border-box;
  display: flex;
  flex-shrink: 0;
  margin: 0;
  position: relative;
  padding: 8px 0;
}
</style>