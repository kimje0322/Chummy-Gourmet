<template>
  <div class="user join">
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
                  src="https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-19/s150x150/64568083_346714766240529_8023659861445181440_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_ohc=Bxek_7qbsNkAX_0dv-_&oh=a14ba48d56d9821b9ab60764d1f14258&oe=5F515501"
                />
              </a>
            </div>
          </header>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data: () => ({}),

  mounted() {},
  methods: {},
};
</script>

<style>
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
  background-color: rgba(var(--b3f,250,250,250),1);
  border-radius: 50%;
  box-sizing: border-box;
  display: block;
  -webkit-box-flex: 0;
  flex: 0 0 auto;
  overflow: hidden;
  position: relative;
}

.a-img2 {
  cursor: pointer;
}
</style>
