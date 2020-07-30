<template>
  <div style="padding : 0">
    <input
      v-model="location"
      type="text"
      placeholder="원하는 지역 검색"
      style="width : 100%; border : 1px solid"
    />

    <v-btn width="100%" @click="search">검색</v-btn>
    <p>{{location}}에 대한 결과</p>
    <v-card class="mx-auto" max-width="500">
        <v-row dense>
          <v-col v-for="restaurant in restaurants" :key="restaurant.restId" cols="12">
            <v-hover v-slot:default="{ hover }">
              <v-card  :elevation="hover ? 12 : 2"
              :class="{ 'on-hover': hover }"
              @click="test(restaurant)">
                <v-img
                  :src="restaurant.src"
                  class="white--text align-end"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px">
                </v-img>

                <v-card-actions>
                  <div>
                    <v-card-title v-text="restaurant.restName"></v-card-title>
                    <v-card-subtitle v-text="restaurant.restAddr"></v-card-subtitle>
                  </div>
                  <v-spacer></v-spacer>

                  <v-btn icon>
                    <v-icon>mdi-heart-outline</v-icon>
                  </v-btn>
                  <span v-text="restaurant.restLike"></span>

                  <v-btn icon>
                    <v-icon>mdi-bookmark-check-outline</v-icon>
                  </v-btn>
                  <span v-text="restaurant.restReview"></span>

                  <v-btn icon>
                    <v-icon>mdi-comment-edit-outline</v-icon>
                  </v-btn>
                  <span v-text="restaurant.restScrap"></span>
                </v-card-actions>
              </v-card>
            </v-hover>
          </v-col>
        </v-row>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";
import router from "@/routes";


// const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      location: "",
      restaurants: [
        {
          restId: 1,
          restName: "맛찬들",
          restAddr: "대전 유성구",
          restCategory: "육류,고기요리",
          restLike: 25,
          restScrap: 15,
          restReview: 13,
          src: "https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
        },
        {
          restId: 2,
          restName: "대게나라",
          restCategory: "게요리",
          restAddr: "대전 유성구",
          restLike: 64,
          restScrap: 52,
          restReview: 42,
          src: "https://t1.daumcdn.net/cfile/tistory/997F8E415A94097B0A",
        },
        {
          restId: 3,
          restName: "봉명가든",
          restCategory: "양식",
          restAddr: "대전 유성구",
          restLike: 37,
          restScrap: 41,
          restReview: 53,
          src: "https://t1.daumcdn.net/cfile/tistory/9977864D5B07565017",
        },
      ],
    };
  },
  methods: {
    search() {
      axios
        .get(`${SERVER_URL}/curation?location=${this.location}`)

        .then((response) => {
         alert("성공");
         console.log("성공");
         console.log(response.data[0]);
        })

        .catch((error) => {
          console.log(error.response);
          alert("로그인 실패");
        });
    },
    test(restaurant) {
      // console.log(restaurant);
      router.push({name : "Detail", params : restaurant});
      // this.$router.push("/detail");
    }
  },
  components: {},
};
</script>