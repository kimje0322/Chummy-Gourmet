<template>
  <section class="user join">
    <v-app>
      <v-toolbar-title>
        <v-toolbar class="mb-1" dense elevation="1">
          <v-icon @click="$router.go(-1)">
            mdi-arrow-left
          </v-icon>
          <v-spacer></v-spacer>
          <p class="my-auto">게시물</p>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-toolbar-title>
      <!-- <p>{{ lst.postid }}</p> -->
      <div role="button" tabindex="-1">
        <div class="hc1 hc2" style="postion: relative; padding-right: 17px;">
          <div class="hc-d1" tabindex="-1">
            <a class="a-img1 a-img2" href="#" tabindex="0" style="width: 32px; height: 32px;">
              <img
                style="height: 100%; width: 100%;"
                :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+this.postlst.user_img"
              />
            </a>
          </div>
          <div class="pf">
            <div>
              <div class="pf-n">
                <a
                  class="pf-n-a"
                  href="#"
                  tabindex="0"
                  style="color: black; font-weight: 600;"
                >{{this.postlst.user_nickname}}</a>
                <div style="float: right; margin-left:190px; ">
                  <button @click="dialog = true" v-show="show">
                    <div style="padding: 2px; width: 24px; height: 24px;">
                      <i class="fas fa-ellipsis-v"></i>
                    </div>
                  </button>
                  <v-dialog dark v-model="dialog" max-width="190">
                    <v-list>
                      <v-list-item v-for="(item, index) in items" :key="index" @click="doit(item)">
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-dialog>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <img
        style="height: 350px;"
        :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+this.postlst.post_img_url"
        class="fc-img"
      />
      <div class="fb">
        <section class="func">
          <span class="heart">
            <button class="heart-btn">
              <div style="border: 0" class="heart-div">
                <span style="margin: 0; height: 24px; width: 24px;">
                  <i
                    style="display: block; position: relative; height: 24px; width: 24px;"
                    class="far fa-heart"
                  ></i>
                </span>
              </div>
            </button>
          </span>
          <span style="display: inline-block;">
            <button
              @click="onComment()"
              style="background: 0 0; border: 0; display: flex; padding: 8px;"
            >
              <div>
                <i
                  style="display: block; position: relative; height: 24px; width: 24px;"
                  class="far fa-comment"
                ></i>
                <!-- </div> -->
                <!-- </router-link> -->
              </div>
            </button>
          </span>
          <span style="display: inline-block;">
            <button style="background: 0 0; border: 0; display: flex; padding: 8px;">
              <div>
                <i
                  style="display: block; position: relative; height: 24px; width: 24px;"
                  class="far fa-paper-plane"
                ></i>
              </div>
            </button>
          </span>
        </section>
        <section style="height: 17.6px; margin-bottom: 8px;">
          <div style="flex: 1 1 auto;">
            <p style="font-weight: 600;">
              좋아요
              <span>{{ this.postlst.post_like }}</span>
              개
            </p>
          </div>
        </section>

        <div style="margin-bottom: 4px;">
          <div>
            <div>
              <div>
                <a
                  style="text-decoration: none; font-weight: 600; font-size: 14px; padding-left: 5px; color: rgba(var(--i1d,38,38,38),1)"
                  href="#"
                >{{this.postlst.user_nickname}}</a>&nbsp;
                <span>{{ this.postlst.post_content }}</span>
              </div>
            </div>
            <div>
              <div style="marign-bottom: 4px; padding-left: 5px;">
                <a style="font-size: 14px; font-weight: 400; color: #8e8e8e;" @click="onComment()">
                  댓글
                  <span>{{this.commentlst}}</span>개 모두 보기
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  data() {
    return {
      postlst: [],
      commentlst: [],
      items: [{ title: "수정" }, { title: "삭제" }],
      dialog: false,
      userId: "",
      show: false,
    };
  },
  created() {
    this.postlst = {
      post_content: this.$route.query.post_content,
      post_id: this.$route.query.post_id,
      post_img_url: this.$route.query.post_img_url,
      post_like: this.$route.query.post_like,
      post_userid: this.$route.query.post_userid,
      user_img: this.$route.query.user_img,
      user_nickname: this.$route.query.user_nickname,
    };
    this.userId = this.$route.query.userId;
    // this.postlst = this.$route.query.post
    this.commentlst = this.$route.query.comment;
    if (this.userId == this.$cookie.get("userId")) {
      this.show = true;
    }
  },
  methods: {
    doit(item) {
      if (item.title == "삭제") {
        axios
          .delete(`${SERVER_URL}/post?postid=` + this.postlst.post_id)
          .then((response) => {
            this.dialog = false;
            router.go(-1);
          });
      } else {
        // this.dialog = false
        // router.push({ name: "PostUpdate", query: {postlst: this.postlst}});
        let repost = {
          postid: this.postlst.post_id,
          postnickname: this.postlst.user_nickname,
          postcontent: this.postlst.post_content,
          postimage: this.postlst.post_img_url,
          userpage: true,
        };
        router.push({ name: "AddFeed", params: repost });
      }
    },
    onComment() {
      let postinfo = {
        postid: this.postlst.post_id,
        postnickname: this.postlst.user_nickname,
        postcontent: this.postlst.post_content,
        postuserimg: this.postlst.user_img,
        postuserid : this.userId
      };
      router.push({ name: "Comment", query: postinfo });
    },
  },
};
</script>

<style scoped>
.heart-div {
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  position: relative;
}

.heart-btn {
  -webkit-box-align: center;
  align-items: center;
  background: 0 0;
  border: 0;
  cursor: pointer;
  display: flex;
  -webkit-box-pack: center;
  justify-content: center;
  padding: 8px;
}

.heart {
  /* display: inline-block; */
  margin: 0 0 0 -4px;
  padding: 0;
  border: 0;
  /* vertical-align: baseline; */
  -webkit-box-direction: normal;
}

.func {
  margin-top: 4px;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  flex-direction: row;
  -webkit-box-align: stretch;
  align-items: stretch;
  border: 0 solid black;
  display: flex;
  box-sizing: border-box;
  display: flex;
  flex-shrink: 0;
  margin: 0;
  padding: 0;
  position: relative;
}

.fb {
  padding: 0 16px;
  -webkit-box-align: stretch;
  align-items: stretch;
  border: 0 solid black;
  box-sizing: border-box;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  flex-shrink: 0;
  margin: 0;
  position: relative;
}

.fc {
  -webkit-box-align: stretch;
  align-items: stretch;
  display: flex;
  border: 0 solid black;
  box-sizing: border-box;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  flex-shrink: 0;
  margin: 0;
  padding: 0;
  position: relative;
}

.hc1 {
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  flex-direction: row;
  height: 60px;
  padding: 16px;
}

.hc2 {
  border-bottom: 1px solid rgba(var(--ce3, 239, 239, 239), 1);
}

.hc-d1 {
  -webkit-box-align: center;
  align-items: center;
  align-self: center;
  /* display: block; */
  float: left;
  -webkit-box-flex: 0;
  flex: none;
  -webkit-box-pack: center;
  justify-content: center;
}

.a-img1 {
  background-color: rgba(var(--b3f, 250, 250, 250), 1);
  border-radius: 50%;
  box-sizing: border-box;
  display: block;
  /* float: left; */
  /* padding: 12px; */
  -webkit-box-flex: 0;
  flex: 0 0 auto;
  overflow: hidden;
  position: relative;
}

.a-img2 {
  cursor: pointer;
}

.pf {
  -webkit-box-aligh: start;
  align-items: flex-start;
  display: flex;
  /* float: left; */
  -webkit-box-flex: 1;
  flex-grow: 1;
  flex-shrink: 1;
  margin-left: 12px;
  /* padding: 12px; */
  overflow: hidden;
}

.pf-n {
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  flex-direction: row;
  -webkit-box-flex: 1;
  flex-grow: 1;
  flex-shrink: 1;
  max-width: 100%;
  overflow: hidden;
  padding: 2px;
  top: 1px;
}

.pf-n-a {
  padding-left: 12px;
  padding-top: 4px;
  appearance: none;
  background: 0 0;
  text-align: center;
  text-transform: inherit;
  text-overflow: ellipsis;
  width: auto;
}

.fc-img {
  object-fit: cover;
  height: 100%;
  left: 0;
  position: absolute;
  top: 0;
  user-select: none;
  width: 100%;
  margin: 0;
  padding: 0;
  border: 0;
  font: inherit;
  vertical-align: baseline;
  position: relative;
}

.fc-frame {
  touch-action: manipulation;
  -webkit-box-align: stretch;
  align-content: stretch;
  border: 0 solid black;
  box-sizing: border-box;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  flex-shrink: 0;
  margin: 0;
  padding: 0;
  position: relative;
}

.fc-fr {
  /* padding-bottom: 125%; */
  display: block;
  /* overflow: hidden; */
  /* align-items: stretch; */
  border: 0 solid black;
  /* box-sizing: border-box; */
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  flex-shrink: 0;
  margin: 0;
  padding: 0;
  position: relative;
}
</style>