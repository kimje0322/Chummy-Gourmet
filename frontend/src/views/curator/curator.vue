<template>
  <div style="padding : 0">
    <input
      v-model="location"
      @keyup.enter="search"
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
              @click="moveDetail(restaurant)">
                <v-img
                  src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
                  class="white--text align-end"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                  height="200px">
                </v-img>

                <v-card-actions>
                  <div>
                    <v-card-title v-text="restaurant.name"></v-card-title>
                    <v-card-subtitle v-text="restaurant.location"></v-card-subtitle>
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
      restaurants : []
    };
  },
  mounted() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          console.log(pos);
          var curLat = pos.coords.latitude;
          var curLng = pos.coords.longitude;
          console.log(curLat + "," + curLng);
          
        });
      }
  },
  methods: {
    search() {
      axios
        .get(`${SERVER_URL}/curation?location=${this.location}`)

        .then((response) => {
          this.restaurants = response.data;
          // console.log(this.restaurants);
        })

        .catch((error) => {
          console.log(error.response);
          alert("로그인 실패");
        });
    },
    moveDetail(restaurant) {
      // console.log(restaurant);
      router.push({name : "Detail", params : restaurant});
    }
  },
  components: {},
};
</script>