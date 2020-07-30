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
          src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
          class="white--text align-end"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="100%"
        ></v-img>
      </v-carousel-item>
    </v-carousel>

    <v-list two-line>
      <v-list-item>
        <!-- <v-list-item-avatar>
          <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
        </v-list-item-avatar>-->
        <v-list-item-content>
          <v-list-item-title v-text="restaurant.name"></v-list-item-title>
          <v-list-item-subtitle v-text="restaurant.category"></v-list-item-subtitle>
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
              <v-list-item-title v-text="restaurant.location"></v-list-item-title>
              <v-list-item-subtitle v-text="restaurant.location"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-phone</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="restaurant.telphone"></v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-clock-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>10:00 - 22:00</v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-parking</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>주차가능</v-list-item-title>
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
              <!-- <v-list-item-title v-html="review.title"></v-list-item-title> -->
              <!-- <v-list-item-subtitle v-html="review.subtitle"></v-list-item-subtitle> -->
              <v-list-item-title>
                {{review.title}}
                <span class="text-caption grey--text text--lighten-1" style="float:right;" v-text="review.date"></span>
              </v-list-item-title>
              <v-list-item-subtitle>
                <span class="text--primary" v-for="(m, index) in meetups[review.meetupId].members" :key="index">
                  <span class="blue--text" v-if="index == 0">{{m}} </span>
                  <span v-else>{{m}} </span>
                </span>
                &mdash; {{review.contents}}
              </v-list-item-subtitle>
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
      // restaurantDetails: [
      //   {},
      //   {
      //     restId: 1,
      //     restAddr: "대전 유성구 온천북로13번길 35",
      //     restJibun: "대전 유성구 봉명동 612-3",
      //     restPhone: "042-485-3766",
      //     restRunning: "10:30 - 02:00",
      //     restParking: "주차가능",
      //   },
      //   {
      //     restId: 2,
      //     restAddr: "대전 유성구 대학로 56",
      //     restJibun: "대전 유성구 봉명동 615-1",
      //     restPhone: "042-822-6022",
      //     restRunning: "11:00 - 23:30",
      //     restParking: "주차가능",
      //   },
      //   {
      //     restId: 3,
      //     restAddr: "대전 유성구 대학로 60",
      //     restJibun: "대전 유성구 봉명동 612-3 매드블럭 6층",
      //     restPhone: "042-485-3766",
      //     restRunning: "11:00 - 22:00",
      //     restParking: "주차가능",
      //   },
      // ],
      reviews: [
        // { header: 'Today' },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          title : '육즙 터집니다',
          contents : '리얼 존맛탱',
          meetupId : 1,
          date : '2020-07-26',
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          title : '한국와서 정말 맛있는...',
          contents : "Wish I could come, but I'm out of town this weekend.",
          meetupId : 2,
          date : '2020-07-23',
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          title : '마시써영',
          contents : "맛있어너무너무너무",
          meetupId : 3,
          date : '2020-07-16',
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
          title : '와 맛있당',
          contents : "고기대박",
          meetupId : 4,
          date : '2020-07-13',
        },
        // { divider: true, inset: true },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
          title : '돼지잡내가쫌나네요',
          contents : "그냥저냥 먹고갑니다...",
          meetupId : 5,
          date : '2020-07-11',
        },
      ],
      meetups : [{},
        {
          id : 1,
          master : '김승범',
          members : ['김승범','조민기', '박세훈']
        },
        {
          id : 2,
          master : '하정우',
          members : ['하정우','김성균', '최민식']
        },
        {
          id : 3,
          master : '이정재',
          members : ['이정재','황정민']
        },
        {
          id : 4,
          master : '한지민',
          members : ['한지민','조민기', '박세훈']
        },
        {
          id : 5,
          master : '조민기',
          members : ['조민기', '쯔위', '박세훈', '김승범']
        }
      ]
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