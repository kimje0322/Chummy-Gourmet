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
          <p class="my-auto">NewsFeed</p>
          <v-spacer></v-spacer>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-toolbar-title>

      <div style="flex-direction: column; padding-bottom: 5600px; padding-top: 0px">
        <!-- <span v-for="(n, i) in 10" :key="i">{{ n }} </span> -->
        <!-- <p>dkfjdlf=adfldfa;lkdfj;lkj</p> -->
          
        <article v-for="(lst, i) in postlst" :key="i">
          <!-- <p>{{ lst.postid }}</p> -->
          <div role="button" tabindex="-1">
            <div class="hc1 hc2" style="postion: relative; padding-right: 17px;">
              <div class="hc-d1" tabindex="-1">
                <canvas
                  height="84"
                  width="84"
                  style="position: absolute; top: -5px; left: -5px; width: 42px; height: 42px;"
                ></canvas>
                <a class="a-img1 a-img2" tabindex="0" style="width: 32px; height: 32px;">
                  <img
                    @click="gotoProfile(lst)"
                    style="height: 100%; width: 100%;"
                    :src="`https://i3b302.p.ssafy.io:8080/img/user?imgname=`+lst.user_img"
                  />
                </a>
              </div>
              <div class="pf">
                <div>
                  <div class="pf-n">
                    <a 
                      @click="gotoProfile(lst)"
                      class="pf-n-a"
                      tabindex="0"
                      style="color: black; font-weight: 600;"
                    >{{lst.usernickname}}</a>

                    <div v-if="lst.postuserid  == userid" style="float: right; ">
                      <button @click.stop="del(lst.postid)">
                        <div style="padding: 2px; width: 24px; height: 24px;">
                          <i class="far fa-trash-alt"></i>
                        </div>
                      </button>
                      <v-dialog dark v-model="dialog" max-width="300">
                        <v-list>
                          게시글을 삭제하시겠습니까?
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

                    <div
                      v-if="lst.postuserid  == userid"
                      style="float: right; margin-right: 0px; margin-left: 167px;"
                    >
                      <button @click="onRevise(lst)">
                        <div style="padding: 2px; width: 24px; height: 24px;">
                          <i class="far fa-edit"></i>
                        </div>
                      </button>
                    </div>

                    <!-- <div
                      v-if="lst.postuserid  == userid"
                      style="float: right; margin-left: 160px; "
                    >
                      <button @click="onDelete(lst)">
                        <div style="padding: 2px; width: 24px; height: 24px;">
                          <i class="far fa-trash-alt"></i>
                        </div>
                      </button>
                    </div>-->
                  </div>
                </div>
                <div></div>
              </div>
            </div>
            <!-- <div style="float: right; width: 40px; height: 60px;">
              <button>
                <div style="padding: 8px; width: 24px; height: 24px;">
                  <i class="fas fa-ellipsis-v"></i>
                </div>
              </button>
            </div>-->
          </div>
          <div class="fc">
            <div class="fc-frame" tabindex="0">
              <div class="fc-fr">
                <v-img min-height="300" max-height="300"
                  :src="`https://i3b302.p.ssafy.io:8080/img/post?imgname=`+lst.postimgurl"
                  class="fc-img"
                />
              </div>
            </div>
          </div>
          <div class="fb">
            <section class="func">
              <span v-if="likelist.includes(lst.postid*1)" class="heart" @click="unLike(lst, i)">
                <button class="heart-btn">
                  <div style="border: 0">
                    <span style="margin: 0; height: 24px; width: 24px;">
                      <!-- {{likelist}}
                      뿅-->
                      <v-icon
                        style="display: block; position: relative; height: 24px; width: 24px; color: red;"
                      >mdi-heart</v-icon>
                      <!-- <i
                        style="display: block; position: relative; height: 24px; width: 24px; color: red;"
                        class="fas fa-heart"
                      ></i>-->
                    </span>
                  </div>
                </button>
              </span>
              <span v-else class="heart" @click="onLike(lst, i)">
                <button class="heart-btn">
                  <div style="border: 0">
                    <span style="margin: 0; height: 24px; width: 24px;">
                      <!-- {{likelist}}
                      하트-->
                      <v-icon
                        style="display: block; position: relative; height: 24px; width: 24px;"
                      >mdi-heart-outline</v-icon>
                      <!-- <i
                        style="display: block; position: relative; height: 24px; width: 24px; "
                        class="far fa-heart"
                      ></i>-->
                    </span>
                  </div>
                </button>
              </span>
              <span style="display: inline-block;">
                <button
                  @click="onComment(lst.postid, lst.usernickname, lst.postcontent,lst.user_img,lst.postuserid)"
                  style="background: 0 0; border: 0; display: flex; padding: 8px 6px;"
                >
                  <div>
                    <!-- <router-link to="/newsfeed/comment"> -->
                    <!-- <div :v-model="pid" @click="onComment"> -->
                    <i
                      style="display: block; position: relative; height: 21px; width: 21px;"
                      class="far fa-comment"
                    ></i>
                    <!-- </div> -->
                    <!-- </router-link> -->
                  </div>
                </button>
              </span>
              <!-- here messageing -->
              <CreateChat :postuserid="lst.postuserid" />
              <!-- <span style="display: inline-block; margin-left: auto; margin-right: -10px;">
                <button>

                </button>

              </span>-->
            </section>

            <section style="height: 17.6px; margin-bottom: 8px;">
              <div style="flex: 1 1 auto;">
                <p style="font-weight: 600;">
                  좋아요
                  <span>{{ lst.postlike }}</span>
                  개
                </p>
              </div>
            </section>
            <div style="margin-bottom: 4px;">
              <div>
                <div>
                  <div>
                    <a
                      style="text-decoration: none; font-weight: 600; font-size: 14px; padding-left: 5px; padding-right: 5px;color: rgba(var(--i1d,38,38,38),1)"
                    >{{lst.usernickname}}</a>
                    <span>{{ lst.postcontent }} {{lst.postid}}</span>
                  </div>
                </div>
                <div>
                  <div v-if="commentlst[i][0] > 0" style="marign-bottom: 4px; padding-left: 5px;">
                    <a
                      style="font-size: 14px; font-weight: 400; color: #8e8e8e;"
                      @click="onComment(lst.postid, lst.usernickname, lst.postcontent, lst.user_img, lst.postuserid)"
                    >
                      댓글
                      <span>{{commentlst[i][0]}}</span>개 모두 보기
                    </a>
                  </div>
                </div>
                <div>{{ lst.postdate | moment("from", "now") }}</div>
              </div>
            </div>
            <!-- <p>{{ lst.postcontent }}</p> -->
          </div>
        </article>
      </div>

      <div v-if="postlst.length==0" class="nofeed" style="text-align: center;">
        <i class="far fa-images fa-5x"></i>
        <p style="font-size:1.1rem; margin-top:15px">다른 사람을 팔로우하면 <br>상대방의 피드를 확인할 수 있습니다.</p>
        <router-link to="/SearchUser"><v-btn color="warning" style="width: 60%">유저 보기</v-btn></router-link>
      </div>

    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import router from "@/routes";
import Vue from "vue";
import vueMoment from "vue-moment";
import CreateChat from "../../components/common/CreateChat";


Vue.use(vueMoment);

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  components: {
    CreateChat,
  },
  data() {
    return {
      dialog: false,
      show: false,
      postlst: [],
      commentlst: [],
      like: false,
      timestamp: "",
      likelist: [],
      likeornot: "",
      likestate: true,
      items: [{ title: "삭제" }, { title: "취소" }],
      delid : "",
      mynickname : ''
    };
  },

  mounted() {},
  created() {
    //유저의 닉네임 가져오기
     axios
      .post(`${SERVER_URL}/chat/nickname`,[this.$cookie.get('userId')])
      .then((response) => {
       this.mynickname = response.data[0];
      })
      .catch((error) => {
        console.log(error.response);
      });


    this.timestamp = new Date();

    this.userid = this.$cookie.get("userId")
    axios
      .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
      .then((response) => {
        console.log(response);
        var posts = response.data.data;
        var comments = response.data.comment;
        // alert(this.postlst.length);
        // console.log(posts);
        posts.sort((a, b) => {
          return -1 * (a.postid - b.postid);
        });
        comments.sort((a, b) => {
          return -1 * (a[1] - b[1]);
        });
        this.postlst = posts;

        this.commentlst = response.data.comment;
        console.log("mentlst : " + response.data.comment);
      })
      .catch((error) => {
        console.log(error.response);
      });
    axios
      .get(`${SERVER_URL}/post/like/${this.$cookie.get("userId")}`)
      .then((response) => {
        console.log(response);
        this.likelist = response.data;
        console.log(this.likelist);
      });
  },
  methods: {
    del(deleteid){
      this.dialog =  true;
      this.delid = deleteid;

    },
    doit(item, deleteid) {
      if (item.title == "삭제") {
        console.log("삭제해버려?");
        // console.log(deleteid);
        axios
          .delete(`${SERVER_URL}/post?postid=${this.delid}`)
          .then((response) => {
            this.dialog = false;

            axios
              .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
              .then((response) => {
                console.log(response);
                var posts = response.data.data;
                var comments = response.data.comment;
                // alert(this.postlst.length);
                // console.log(posts);
                posts.sort((a, b) => {
                  return -1 * (a.postid - b.postid);
                });
                comments.sort((a, b) => {
                  return -1 * (a[1] - b[1]);
                });
                this.postlst = posts;
                
                this.commentlst = response.data.comment;
                console.log("mentlst : " + response.data.comment);
              })
              .catch((error) => {
                console.log(error.response);
              });
          })
          .catch((error) => {});
      } else {
        this.dialog = false;
      }
    },
    gotoProfile(user) {
      // 나를 누르면 마이페이지로 이동
      if(user.postuserid == this.$cookie.get("userId")){
        this.$router.push('/user/info');
      }
      // 타 유저 프로필 페이지로 이동
      else{
        let userImg = `https://i3b302.p.ssafy.io:8080/img/user?imgname=`+user.user_img;
         this.$router.push('/user/profile?userId='+user.postuserid
        +'&followerFollowing='+true
        +'&userImg='+userImg);
      }
    },
    onDelete(lst) {
      console.log(lst);
      axios
        .delete(`${SERVER_URL}/post?postid=${lst.postid}`)
        .then((response) => {
          axios
            .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
            .then((response) => {
              console.log(response);
              var posts = response.data.data;
              var comments = response.data.comment;
              // alert(this.postlst.length);
              // console.log(posts);
              posts.sort((a, b) => {
                return -1 * (a.postid - b.postid);
              });
              comments.sort((a, b) => {
                return -1 * (a[1] - b[1]);
              });
              this.postlst = posts;
              this.commentlst = response.data.comment;
              console.log("mentlst : " + response.data.comment);
            })
            .catch((error) => {
              console.log(error.response);
            });
        })
        .catch((error) => {});
    },
    onComment(pid, pname, pcontent, puserimg,puserid) {
      let postinfo = {
        postid: pid,
        postnickname: pname,
        postcontent: pcontent,
        postuserimg: puserimg,
        postuserid : puserid
      };
      // console.log("dfsdafgfgfdfadf");
      // query로 넘기고 받는 라우터에서 query로 받아야 뒤로가기했을때 데이터 존재
      router.push({ name: "Comment", query: postinfo });
    },
    onLike(postlike, idx) {
      this.likestate = !this.likestate;
      console.log(this.like);
      console.log(this.postlst[idx].postid);
      if (this.postlst[idx].postid in this.likelist) {
        console.log("이거나오면안됨");
      } else {
        axios
          .put(
            `${SERVER_URL}/post/like?postid=${
              this.postlst[idx].postid
            }&userid=${this.$cookie.get("userId")}`
          )
          .then((response) => {
            //게시한 유저가 자신이 아닐때만
            //좋아요 알림 보냄
            if(this.postlst[idx].postuserid != this.$cookie.get('userId')){
              console.log(this.mynickname);
               window.db.collection('alarm').doc('like').collection('messages').add({
                        to : this.postlst[idx].postuserid,
                        from : this.$cookie.get('userId'),
                        message: this.mynickname+"님이 회원님의 게시글에 좋아요를 눌렀습니다..",
                        time: Date.now(),
                        postid : this.postlst[idx].postid,
                        confirm : false
                    }).catch(err => {
                        console.log(err);
                    });
            }


            // console.log("유저가 좋아요 성공");
            this.like = !this.like;
            this.timestamp = new Date();
            // console.log(this.$cookie.get("userId"));
            axios
              .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
              .then((response) => {
                // console.log(response);
                var posts = response.data.data;
                var comments = response.data.comment;
                // alert(this.postlst.length);
                // console.log(posts);
                posts.sort((a, b) => {
                  return -1 * (a.postid - b.postid);
                });
                comments.sort((a, b) => {
                  return -1 * (a[1] - b[1]);
                });
                this.postlst = posts;
                this.commentlst = response.data.comment;
                // console.log("mentlst : " + response.data.comment);
              })
              .catch((error) => {
                console.log(error.response);
              });
            axios
              .get(`${SERVER_URL}/post/like/${this.$cookie.get("userId")}`)
              .then((response) => {
                // console.log(response);
                this.likelist = response.data;
                // console.log("바뀐거보자");
                // console.log(this.likelist);
              });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    unLike(list, idx) {
      this.likestate = !this.likestate;
      axios
        .delete(
          `${SERVER_URL}/post/like?postid=${
            this.postlst[idx].postid
          }&userid=${this.$cookie.get("userId")}`
        )
        .then((response) => {
          console.log("성공함");
          console.log(response);
          this.timestamp = new Date();
          console.log(this.$cookie.get("userId"));
          axios
            .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
            .then((response) => {
              console.log(response);
              var posts = response.data.data;
              var comments = response.data.comment;
              // alert(this.postlst.length);
              // console.log(posts);
              posts.sort((a, b) => {
                return -1 * (a.postid - b.postid);
              });
              comments.sort((a, b) => {
                return -1 * (a[1] - b[1]);
              });
              this.postlst = posts;
              this.commentlst = response.data.comment;
              console.log("mentlst : " + response.data.comment);
            })
            .catch((error) => {
              console.log(error.response);
            });
          axios
            .get(`${SERVER_URL}/post/like/${this.$cookie.get("userId")}`)
            .then((response) => {
              console.log(response);
              this.likelist = response.data;
              console.log("바뀐거보자");
              console.log(this.likelist);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onRevise(lst) {
      let repost = {
        postid: lst.postid,
        postnickname: lst.postnickname,
        postcontent: lst.postcontent,
        postimage: lst.postimgurl,
        userpage: false,
      };
      router.push({ name: "AddFeed", params: repost });
    },
  },
};
</script>

<style scoped>
/* div {
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
  padding: 0;
  position: relative;
} */

/* .heart-div {
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  position: relative;
} */

.heart-btn {
  /* -webkit-box-align: center; */
  /* align-items: center; */
  /* background: 0 0; */
  /* border: 0; */
  /* cursor: pointer; */
  /* display: flex; */
  /* -webkit-box-pack: center; */
  /* justify-content: center; */
  padding: 6px 4px 8px 6px;
}

.heart {
  /* display: inline-block; */
  margin: 0 0 0 -4px;
  padding: 0;
  border: 0;
  /* vertical-align: baseline; */
  /* -webkit-box-direction: normal; */
}

.func {
  margin-top: 4px;
  /* -webkit-box-orient: horizontal; */
  /* -webkit-box-direction: normal; */
  /* flex-direction: row; */
  /* -webkit-box-align: stretch; */
  /* align-items: stretch; */
  border: 0 solid black;
  /* display: flex; */
  /* box-sizing: border-box; */
  display: flex;
  /* flex-shrink: 0; */
  margin: 0;
  padding: 0;
  /* position: relative; */
}

.fb {
  padding: 0 16px;
  -webkit-box-align: stretch;
  align-items: stretch;
  border: 0 solid black;
  box-sizing: border-box;
  /* display: flex; */
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  /* flex-shrink: 0; */
  margin: 0;
  position: relative;
}

.fc {
  /* -webkit-box-align: stretch; */
  /* align-items: stretch; */
  display: flex;
  border: 0 solid black;
  /* box-sizing: border-box; */
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  flex-direction: column;
  /* flex-shrink: 0; */
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
  /* -webkit-box-align: center; */
  /* align-items: center; */
  /* -webkit-box-orient: horizontal; */
  -webkit-box-direction: normal;
  /* flex-direction: row; */
  /* -webkit-box-flex: 1; */
  /* flex-grow: 1; */
  /* flex-shrink: 1; */
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

/* div {
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
  padding: 0;
  position: relative;
} */

.nofeed {
  margin: 120px 0 0 0;
  text-align: center;
}
</style>