<template>
  <section class="user join">
    <v-app>
      <v-toolbar-title>
        <v-toolbar dark>
          <!-- <a @click="$router.go(-1)">
            <i class="fas fa-chevron-left back"></i>
          </a> -->
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <p class="my-auto">게시물</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>

          <a @click="$router.go(-1)">
            <i class="fas fa-chevron-left back"></i>
          </a>
        </v-toolbar>
      </v-toolbar-title>
          <!-- <p>{{ lst.postid }}</p> -->
          <div role="button" tabindex="-1">
              <a @click="$router.go(-1)">
                <i class="fas fa-chevron-left back"></i>
            </a>
            <div class="hc1 hc2" style="postion: relative; padding-right: 17px;">
              <div class="hc-d1" tabindex="-1">
                <canvas
                  height="84"
                  width="84"
                  style="position: absolute; top: -5px; left: -5px; width: 42px; height: 42px;"
                ></canvas>
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
                      <button @click.stop="dialog = true" v-show="show">
                        <div style="padding: 2px; width: 24px; height: 24px;">
                          <i class="fas fa-ellipsis-v"></i>
                        </div>
                      </button>
                      <v-dialog
                        dark
                        v-model="dialog"
                        max-width="190"
                        >
                            <v-list> 
                            <v-list-item
                            v-for="(item, index) in items"
                            :key="index"
                            @click="doit(item)"
                            >
                            <v-list-item-title>{{ item.title }}</v-list-item-title>
                            </v-list-item>
                        </v-list>
                        </v-dialog>
                    </div>
                  </div>
                </div>
                <div></div>
              </div>
            </div>
          </div>
          <div class="fc">
            <div class="fc-frame" tabindex="0">
              <div class="fc-fr">
                <img
                  :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+this.postlst.post_img_url"
                  class="fc-img"
                />
              </div>
            </div>
          </div>
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
                  @click="onComment(this.postlst.post_id, this.postlst.user_nickname, this.postlst.post_content,this.postlst.user_img)"
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
                    <a
                      style="font-size: 14px; font-weight: 400; color: #8e8e8e;"
                      @click="onComment()"
                    >
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
      commentlst :[],
      items: [
        { title: '수정' },
        { title: '삭제' },
      ],
       dialog: false,
       data : {},
       show:false,
    };
  },
  created() {
      this.data = this.$route.params.users
      this.postlst = this.$route.params.post
      this.commentlst = this.$route.params.comment
      if(this.data.userId == this.$cookie.get("userId")){
          this.show = true;
      }
  },
  methods: {
    doit(item){
        if(item.title == '삭제'){
            axios
            .delete(`${SERVER_URL}/post?postid=`+this.postlst.post_id)
            .then((response) => {
                this.dialog = false
                router.go(-1)
            })        
        }
        else{
            alert("수정할꺼임")
            this.dialog = false
        }
        
    },
    onComment() {

      let postinfo = {
        postid: this.postlst.post_id,
        postnickname: this.postlst.user_nickname,
        postcontent: this.postlst.post_content,
        postuserimg: this.postlst.user_img,
        post: this.postlst,
        comment : this.commentlst
      };
      // console.log("dfsdafgfgfdfadf");
      router.push({ name: "Comment", params: postinfo });
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