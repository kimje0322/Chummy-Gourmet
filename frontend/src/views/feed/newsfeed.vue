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
        <article>
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
                    <a class="pf-n-a" href="#" tabindex="0">dlwlrma</a>
                  </div>
                </div>
                <div></div>
              </div>
            </header>
          </div>
          <div class="fc">
            <div class="fc-frame" tabindex="0">
              <div style="padding-bottom: 125%; display: block; overflow: hidden;">
                <img
                  src="https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-15/e35/p1080x1080/117040004_3363435173707035_6496624469306050608_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=yr4oYNu9CN8AX-G4MoN&oh=f0e109fc179bc3767505d41bc029774e&oe=5F525B05"
                  class="fc-img"
                />
              </div>
            </div>
          </div>
        </article>
      </div>
    </v-app>
  </section>
</template>

<script>
import axios from "axios";

// const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
const SERVER_URL = "http://localhost:8080";

export default {
  data: () => ({}),

  mounted() {},
  methods: {},
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

.fc {
  -webkit-box-align: start;
  align-items: flex-start;
  display: flex;
  -webkit-box-flex: 1;
  flex-grow: 1;
  flex-shrink: 1;
  margin-left: 12px;
  overflow: hidden;
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
  display: block;
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
