<template>
  <div style="padding : 0">
    <input
      v-model="keyword"
      @keyup.enter="search"
      type="text"
      placeholder="원하는 지역 검색"
      style="width : 100%; border : 1px solid"
    />

    <v-btn width="100%" @click="search">검색</v-btn>
    <p>{{keyword}}에 대한 결과</p>

<!--  pop over  -->
      <div>
          <v-menu
            v-model="menu"
            :close-on-content-click="false"
            :nudge-width="375"
            transition="slide-y-transition"
            offset-y
          >
          <template v-slot:activator="{on}">
            <v-btn
              slot="activator"
              v-on="on"
            >
              필터
            </v-btn>
          </template>

            <v-card>
              <v-card-actions>
                <v-btn-toggle>
                  <v-btn outlined @click="getNear">거리순</v-btn>
                  <v-btn outlined @click="getRating">평점순</v-btn>
                  <v-btn outlined @click="getManyReview">리뷰순</v-btn>
                  <v-btn outlined @click="getProperties">선호음식</v-btn>
                </v-btn-toggle>
                <!-- <v-spacer></v-spacer>
                <v-btn @click="menu = false">Cancel</v-btn>
                <v-btn color="primary" @click="menu = false">Save</v-btn> -->
              </v-card-actions>
            </v-card>
          </v-menu>
        </div>


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


const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      on : '',
      fav: true,
      menu: false,
      message: false,
      hints: true,
      
      targetLocation :{
        lat : '',
        lng : '',
      },


      keyword: "",
      restaurants : []
    };
  },
  mounted() {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(this.initMap);
    script.src =
      "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=90891b3c4fa765cd378361c6b16e4dd6&libraries=services";
    document.head.appendChild(script);


      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          // console.log(pos);
          this.targetLocation.lat = pos.coords.latitude;
          this.targetLocation.lng = pos.coords.longitude;
          // console.log(this.targetLocation.lat + "," + this.targetLocation.lng);
        });
      }
  },
  methods: {
    getNear(){
      console.log("거리순");
      this.restaurants.sort((a, b) => {
        return a.dist - b.dist;
      })
    },
    getRating(){
      console.log("좋아요순");
      this.restaurants.sort((a, b) => {
        return a.like - b.like;
      })
    },
    getManyReview(){
      console.log("리뷰순");
      this.restaurants.sort((a, b) => {
        return a.review - b.review;
      })
    },
    getProperties(){
      console.log("선호음식");
      axios
        // .get(`${SERVER_URL}/userpage/getuserInfo?userId=${this.$cookie.get('userId')}`)
        .get(`${SERVER_URL}/userpage/getuserInfo?userId=62`)
        .then((response) => {
          var userInfo = response.data;
          var userFavoriteID = userInfo.userFavorite;
          var userFavorite = [];
          userFavoriteID = userFavoriteID.substr(1, userInfo.userFavorite.length - 2);
          userFavoriteID = userFavoriteID.split(", ");
          (userFavoriteID).forEach(category => {
            if(category == 1){
              userFavorite.push("한식");
            } 
            else if(category == 2){
              userFavorite.push("양식");
            }
            else if(category == 3){
              userFavorite.push("중식");
            }
            else if(category == 4){
              userFavorite.push("일식");
            }
            else if(category == 5){
              userFavorite.push("분식");
            }
            else if(category == 6){
              userFavorite.push("뷔페");
            }
          });
          this.restaurants.sort((a, b) => {
            return (userFavorite.indexOf(a.category) - userFavorite.indexOf(b.category)) * -1;
          })
        })
    },

    search() {
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      axios
        .get(`${SERVER_URL}/curation?location=${this.keyword}`)
        .then((response) => {
          
          // 음식점리스트 받기
          var restaurants = response.data.list;

          // 음식점리스트 돌면서 좌표(position), 거리(dist) 구하기
          restaurants.forEach(restaurant => {

            // 주소 -> 좌표
            geocoder.addressSearch(restaurant.location, (result, status) => {
              if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
              }
              restaurant.position = coords;
              
              // 현위치에서 음식점까지의 거리(dist) 구하기
              if(restaurant.position){
                var polyline = new kakao.maps.Polyline({
                  path: [
                    new kakao.maps.LatLng(this.targetLocation.lat, this.targetLocation.lng),
                    restaurant.position
                  ],
                });
                restaurant.dist = polyline.getLength();
              }
            });
          })
          this.restaurants = restaurants;
        })
        .catch((error) => {
          console.log(error.response);
          alert("로그인 실패");
        });
    },
    moveDetail(restaurant) {
      router.push({name : "Detail", params : restaurant});
    }
  },
  components: {},
};
</script>