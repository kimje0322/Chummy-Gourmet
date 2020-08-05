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
        <p>dkfjdlf=adfldfa;lkdfj;lkj</p>

        <article v-for="(lst, i) in postlst" :key="i">
          <p>{{ lst.postid }}</p>
          <div role="button" tabindex="-1">
            <header class="hc1 hc2" style="postion: relative;">
              <div class="hc-d1" tabindex="-1">
                <canvas
                  height="84"
                  width="84"
                  style="position: absolute; top: -5px; left: -5px; width: 42px; height: 42px;"
                ></canvas>
                <a class="a-img1 a-img2" href="#" tabindex="0" style="width: 32px; height: 32px;">
                  <img
                    style="height: 100%; width: 100%;"
                    src="https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-19/s150x150/64568083_346714766240529_8023659861445181440_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_ohc=Bxek_7qbsNkAX_0dv-_&oh=a14ba48d56d9821b9ab60764d1f14258&oe=5F515501"
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
                      style="color: black;"
                    >{{lst.postid}}dlwlrma</a>
                  </div>
                </div>
                <div></div>
              </div>
            </header>
          </div>
          <div class="fc">
            <div class="fc-frame" tabindex="0">
              <div class="fc-fr">
                <img
                  :src="`http://i3b302.p.ssafy.io:8080/img/post?imgname=`+lst.postimgurl"
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
                  @click="onComment(lst.postid)"
                  style="background: 0 0; border: 0; display: flex; padding: 8px;"
                >
                  <div>
                    <!-- <router-link to="/newsfeed/comment"> -->
                    <!-- <div :v-model="pid" @click="onComment"> -->
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
              <!-- <span style="display: inline-block; margin-left: auto; margin-right: -10px;">
                <button>

                </button>

              </span>-->
            </section>
            <section style="margin-bottom: 8px;">
              <div style="flex: 1 1 auto;">
                <p style="font-weight: 600;">
                  좋아요
                  <span>{{ lst.postlike }} </span>
                  개
                </p>
              </div>

            </section>
            <p>{{ lst.postcontent }}</p>
          </div>
        </article>
        <!-- <article>
          <div role="button" tabindex="-1">
            <header class="hc1 hc2" style="postion: relative;">
              <div class="hc-d1" tabindex="-1">
                <canvas
                  height="84"
                  width="84"
                  style="position: absolute; top: -5px; left: -5px; width: 42px; height: 42px;"
                ></canvas>
                <a class="a-img1 a-img2" href="#" tabindex="0" style="width: 32px; height: 32px;">
                  <img
                    style="height: 100%; width: 100%;"
                    src="https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-19/s150x150/64568083_346714766240529_8023659861445181440_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_ohc=Bxek_7qbsNkAX_0dv-_&oh=a14ba48d56d9821b9ab60764d1f14258&oe=5F515501"
                  />
                </a>
              </div>
              <div class="pf">
                <div>
                  <div class="pf-n">
                    <a class="pf-n-a" href="#" tabindex="0" style="color: black;">dlwlrma</a>
                  </div>
                </div>
                <div></div>
              </div>
            </header>
          </div>
          <div class="fc">
            <div class="fc-frame" tabindex="0">
              <div class="fc-fr">
                <img
                  src="https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-15/e35/p1080x1080/117040004_3363435173707035_6496624469306050608_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=yr4oYNu9CN8AX-G4MoN&oh=f0e109fc179bc3767505d41bc029774e&oe=5F525B05"
                  class="fc-img"
                />
              </div>
            </div>
          </div>
          <div class="fb">
            <section class="func">
              <span class="heart">
                <button class="heart-btn">
                  <div class="heart-div">
                    <span style="margin: 0;   height: 24px; width: 24px;">
                      <i style="display: block; position: relative; height: 24px; width: 24px;" class="far fa-heart"></i>
                    </span>
                  </div>
                </button>
              </span>
              <span style="display: inline-block;">
                <button style="background: 0 0; border: 0; display: flex; padding: 8px;">
                  <div>
                    <i style="display: block; position: relative; height: 24px; width: 24px;" class="far fa-comment"></i>
                  </div>
                </button>
              </span>
              <span style="display: inline-block;">
                <button style="background: 0 0; border: 0; display: flex; padding: 8px;">
                  <div>
                    <i style="display: block; position: relative; height: 24px; width: 24px;" class="far fa-paper-plane"></i>
                  </div>
                </button>
              </span>
            </section>
          </div>
        </article>-->
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      postlst: [],
    };
  },

  mounted() {},
  created() {
    console.log(this.$cookie.get("userId"));
    axios
      .get(`${SERVER_URL}/post?userid=${this.$cookie.get("userId")}`)
      .then((response) => {
        console.log(response);
        this.postlst = response.data.data;
        alert(this.postlst.length);
        console.log(this.postlst);
      })
      .catch((error) => {
        console.log(error.response);
      });
  },
  methods: {
    onComment(pid) {
      let postinfo = {
        postid: pid,
      };
      console.log("dfsdafgfgfdfadf");
      console.log(pid);
      router.push({ name: "Comment", params: postinfo });
    },
  },
};
</script>

<style>
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
</style>