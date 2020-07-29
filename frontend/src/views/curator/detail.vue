<template>
  <v-card elevation="24" max-width="444" class="mx-auto">
    <v-system-bar lights-out></v-system-bar>

    <v-carousel
      :continuous="false"
      :cycle="cycle"
      show-arrows
      hide-delimiter-background
      height="300"
    >
      <v-carousel-item v-for="i of 5" :key="i">
        <v-img
          :src="restaurant.src"
          class="white--text align-end"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="100%"
        ></v-img>
        <!-- <v-sheet :color="colors[i]" height="100%" tile>
          <v-row class="fill-height" align="center" justify="center">
            <div class="display-3">{{ slide }} Slide</div>
          </v-row>
        </v-sheet>-->
      </v-carousel-item>
    </v-carousel>

    <v-list two-line>
      <v-list-item>
        <!-- <v-list-item-avatar>
          <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
        </v-list-item-avatar>-->
        <v-list-item-content>
          <v-list-item-title v-text="restaurant.restName"></v-list-item-title>
          <v-list-item-subtitle v-text="restaurant.restCategory"></v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
          <!-- <v-switch v-model="cycle" label="Cycle Slides" inset></v-switch> -->
        </v-list-item-action>
      </v-list-item>

      <v-expand-transition>
        <div v-show="show">
          <v-divider></v-divider>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-map-marker</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="restaurantDetails[restaurant.restId].restAddr"></v-list-item-title>
              <v-list-item-subtitle v-text="restaurantDetails[restaurant.restId].restJibun"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-phone</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="restaurantDetails[restaurant.restId].restPhone"></v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-clock-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="restaurantDetails[restaurant.restId].restRunning"></v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-parking</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="restaurantDetails[restaurant.restId].restParking"></v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>
        </div>
      </v-expand-transition>

      <v-list three-line>
        <template v-for="(review, index) in reviews">
          <v-divider :key="index"></v-divider>
          <v-subheader v-if="review.header" :key="review.header" v-text="review.header"></v-subheader>
          <v-divider v-else-if="review.divider" :key="index" :inset="review.inset"></v-divider>
          <v-list-item v-else :key="review.title" @click="test(review)">

            <v-list-item-avatar>
              <v-img :src="review.avatar"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title v-html="review.title"></v-list-item-title>
              <v-list-item-subtitle v-html="review.subtitle"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-list>
  </v-card>
</template>

<script>
// import FeedDetail from "../../components/feed/FeedDetail.vue";
// this.$route.params.email,
export default {
  data() {
    return {
      cycle: false,
      show: false,
      restaurant: this.$route.params,
      restaurantDetails: [
        {},
        {
          restId: 1,
          restAddr: "대전 유성구 온천북로13번길 35",
          restJibun: "대전 유성구 봉명동 612-3",
          restPhone: "042-485-3766",
          restRunning: "10:30 - 02:00",
          restParking: "주차가능",
        },
        {
          restId: 2,
          restAddr: "대전 유성구 대학로 56",
          restJibun: "대전 유성구 봉명동 615-1",
          restPhone: "042-822-6022",
          restRunning: "11:00 - 23:30",
          restParking: "주차가능",
        },
        {
          restId: 3,
          restAddr: "대전 유성구 대학로 60",
          restJibun: "대전 유성구 봉명동 612-3 매드블럭 6층",
          restPhone: "042-485-3766",
          restRunning: "11:00 - 22:00",
          restParking: "주차가능",
        },
      ],
      reviews: [
        // { header: 'Today' },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          title: '육즙 터집니다<span style="float:right;" class="text-caption grey--text text--lighten-1"> 4일전</span>',
          subtitle: "<span class='text--primary'><span class='blue--text'>김승범</span>, 조민기, 박세훈</span> &mdash; 리얼 존맛탱",
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          title: '한국와서 정말 맛있는 고기를... <span style="float:right;" class="text-caption grey--text text--lighten-1"> 6일전</span>',
          subtitle: "<span class='text--primary'><span class='blue--text'>Alex</span>, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.",
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          title: '마시써영 <span style="float:right;" class="text-caption grey--text text--lighten-1">7일전</span>',
          subtitle: "<span class='text--primary'><span class='blue--text'>Sandra Adams</span></span> &mdash; 담에 같이 먹으러 갈 분 팔로우 신청 해주세여~",
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
          title: '와 맛있당 <span style="float:right;" class="text-caption grey--text text--lighten-1">9일전</span>',
          subtitle: "<span class='blue--text'>Trevor Hansen</span> &mdash; Have any ideas about what we should get Heidi for her birthday?",
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
          title: '돼지잡내가쫌나네요 <span style="float:right;" class="text-caption grey--text text--lighten-1">13일전</span>',
          subtitle: "<span class='text--primary'><span class='blue--text'>Britta Holt</span>, 김승범</span> &mdash; 그냥저냥 먹고갑니다...",
        },
      ],
    };
  },
  created() {
    console.log(this.$route.params);
  },
  methods : {
    test(review) {
      console.log(review);
    }
  }
  // components : {FeedDetail}
};
</script>