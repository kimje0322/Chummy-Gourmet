<template>
  <div class="user join">
    <!-- <v-app> -->
    <v-bottom-navigation
      scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >
      <v-btn-toggle tile color="deep-purple accent-3" group>
        <v-btn @click="$router.go(-1)" value="center">취소</v-btn>
        <v-btn value="right" @click="meetUp">생성</v-btn>
      </v-btn-toggle>
    </v-bottom-navigation>

    <v-toolbar-title>
      <v-toolbar dark>
        <a @click="$router.go(-1)">
          <i class="fas fa-chevron-left back"></i>
        </a>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <p class="my-auto">Meet Up</p>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-toolbar-title>
    <br />

    <!-- 입력 폼 -->
    <div class="party wrapC">
      <!-- 파티 타이틀 -->
      <div class="input-group mb-3">
        <v-text-field v-model="meetup.title" solo placeholder="제목"></v-text-field>
      </div>

      <!-- 파티 내용 -->
      <div class="input-group mb-3">
        <v-textarea v-model="meetup.content" solo placeholder="일정 내용을 입력하세요."></v-textarea>
      </div>

      <!-- 파티 장소 -->
      <v-layout justify-center>
        <v-dialog v-model="dialog" scrollable max-width="300px">
          <template v-slot:activator="{on}">
            <v-text-field
              slot="activator"
              v-on="on"
              v-model="meetup.location"
              solo
              readonly
              placeholder="장소"
              :append-icon="isColor ? 'mdi-map-marker orange--text text--lighten-2' : 'mdi-map-marker'"
              @click:append="isColor = !isColor"
              @click:input="isClick = !isClick"
            ></v-text-field>
          </template>
          <!-- dialog -->
          <v-card>
            <v-card-title class="headline">
              <input
                v-model="keyword"
                @keyup.enter="search"
                type="text"
                placeholder="원하는 지역 검색"
                style="width : 100%; border : 1px solid"
              />
              <v-btn width="100%" @click="search">검색</v-btn>
            </v-card-title>

            <v-card-text style="height: 300px;">
              <!-- 음식점 리스트 들어감 -->

              <v-card class="mx-auto" max-width="500">
                <v-row dense>
                  <v-col v-for="restaurant in restaurants" :key="restaurant.restId" cols="12">
                    <v-hover v-slot:default="{ hover }">
                      <v-card
                        :elevation="hover ? 12 : 2"
                        :class="{ 'on-hover': hover }"
                        @click="select(restaurant)"
                      >
                        <v-card-actions>
                          <div>
                            <v-card-title v-text="restaurant.name"></v-card-title>
                            <v-card-subtitle v-text="restaurant.location"></v-card-subtitle>
                          </div>
                          <v-spacer></v-spacer>
                        </v-card-actions>
                      </v-card>
                    </v-hover>
                  </v-col>
                </v-row>
              </v-card>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
              <!-- <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn> -->
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-layout>

      <!-- 파티 날짜 -->
      <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :return-value.sync="date"
        transition="scale-transition"
        offset-y
      >
        <!-- 월/일 선택 -->
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            solo
            v-model="meetup.date"
            placeholder="날짜"
            :append-icon="isColor1 ? 'mdi-calendar-check orange--text text--lighten-2' : 'mdi-calendar-check'"
            :return-value.sync="date"
            @click:append="isColor1 = !isColor1"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="date"
          :min="new Date().toISOString().substr(0, 10)"
          max="2050-01-01"
          scrollable
          full-width
        >
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
          <v-menu
            v-model="menu2"
            :close-on-content-click="false"
            :return-value.sync="time"
            transition="scale-transition"
            offset-y
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn text color="primary" v-on="on" v-bind="attrs">OK</v-btn>
            </template>
            <v-time-picker v-model="time" ampm-in-title width="260px">
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu2 = false">Cancel</v-btn>
              <v-btn text color="primary" @click="test">OK</v-btn>
            </v-time-picker>
          </v-menu>
        </v-date-picker>
      </v-menu>

      <!-- 파티 시간 -->
      <vue-timepicker v-model="day.start_time" placeholder="Start Time"></vue-timepicker>
      <span>to</span>
      <vue-timepicker v-model="day.end_time" placeholder="End Time"></vue-timepicker>

      <!-- 파티 인원 -->
      <div id="dropdown-example">
        <v-overflow-btn
          v-model="meetup.personnel"
          solo
          class="my-2"
          :items="dropdown_font"
          placeholder="인원"
          style="margin:0px;"
          target="#dropdown-example"
        ></v-overflow-btn>
      </div>

      <!-- 성향 -->
      <div class="text-center">
        <v-dialog v-model="dialog2" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-text-field solo v-model="selected" v-on="on" v-bind="attrs" placeholder="성향"></v-text-field>
          </template>

          <v-card class="mx-auto" max-width="400">
            <v-toolbar flat dark>
              <v-btn icon>
                <v-icon @click="dialog2 = false">mdi-close</v-icon>
              </v-btn>
              <v-toolbar-title>밋업 성향 선택</v-toolbar-title>
            </v-toolbar>

            <v-card-text>
              <h2 class="subtitle mb-1">원하는 성향을 선택하세요</h2>
              <v-chip-group active-class="deep-purple--text text--accent-4" column multiple>
                <v-chip
                  outlined
                  v-for="(property,index) in properties"
                  :key="index"
                  @click="selected.indexOf(property) != -1 ? selected.splice(selected.indexOf(property), 1) : selected.push(property)"
                >{{property}}</v-chip>
              </v-chip-group>
            </v-card-text>
          </v-card>
        </v-dialog>
      </div>
    </div>
    <!-- </v-app> -->
  </div>
</template>

<script>
import axios from "axios";
import VueTimepicker from 'vue2-timepicker'

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  components: {
    VueTimepicker
  },
  data: () => {
    return {
      day: [
        { start_time: { HH: "", mm: "" }, end_time: { HH: "", mm: "" } },
      ],
      keyword: "",
      date: "",
      time: null,
      menu: false,
      menu2: false,
      isColor: false,
      isColor1: false,
      isClick: false,
      dialog: false,
      dialog2: false,

      restaurants: [],

      meetup: {
        title: "",
        content: "",
        location: "",
        address: "",
        date: "",
        personnel: "",
        master: 70,
        img: "",
      },

      error: {
        title: false,
        content: false,
        location: false,
        date: false,
        personnel: false,
        master: false,
      },

      dropdown_font: ["1", "2", "3", "4", "5", "6", "7", "8"],

      properties: [
        "낙천적",
        "부정적",
        "내향적",
        "외향적",
        "충동적",
        "사교적",
        "대담함",
        "성실함",
        "냉정함",
        "온화함",
        "신중함",
        "게으름",
      ],

      selected: [],

      targetLocation: {
        lat: "",
        lng: "",
      },
      geocoder: "",
    };
  },
  watch: {
    title: function () {
      if (this.meetup.title.length < 1)
        this.error.title = "제목을 입력해주세요.";
      else this.error.title = false;
      return;
    },
    content: function () {
      if (this.meetup.content.length > 0)
        this.error.content = "내용을 입력해주세요.";
      else this.error.content = false;
      return;
    },
    location: function () {
      if (this.meetup.location.length < 1)
        this.error.location = "음식점을 입력해주세요.";
      else this.error.location = false;
      return;
    },
    date: function () {
      if (this.meetup.date.length < 1) this.error.date = "날짜를 입력해주세요.";
      else this.error.date = false;
      return;
    },
    personnel: function () {
      if (this.meetup.personnel.length < 1)
        this.error.personnel = "인원을 입력해주세요.";
      else this.error.personnel = false;
      return;
    },
  },

  mounted() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((pos) => {
        this.targetLocation.lat = pos.coords.latitude;
        this.targetLocation.lng = pos.coords.longitude;
      });
    }

    if (window.kakao && window.kakao.maps) {
      console.log("이미 로딩됨");
      this.initMap();
    } else {
      console.log("카카오맵 로딩");
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=90891b3c4fa765cd378361c6b16e4dd6&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    test() {
      this.menu = false;
      this.menu2 = false;
      this.meetup.date = this.date + " " + this.time;
    },
    initMap() {
      // 좌표->주소
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    meetUp() {
      if (this.meetup.title.length === 0) {
        alert("제목을 작성해주세요.");
        return;
      }

      if (this.meetup.content.length === 0) {
        alert("내용을 작성해주세요.");
        return;
      }

      if (this.meetup.date.length === 0) {
        alert("날짜를 선택해주세요.");
        return;
      }

      if (this.meetup.personnel.length === 0) {
        alert("인원을 선택해주세요.");
        return;
      }
      console.log(this.meetup);
      axios.post(`${SERVER_URL}/meetup`, this.meetup).then((response) => {
        // console.log(response);
        alert("밋업 등록이 완료됐습니다.");
        this.$router.push("/map");
      });
    },
    checkForm() {
      if (this.meetup.title.length < 1)
        this.error.title = "제목을 입력해주세요.";
      else {
        this.error.title = false;
        return;
      }
      if (this.meetup.content.length > 0)
        this.error.content = "내용을 입력해주세요.";
      else {
        this.error.content = false;
        return;
      }
      if (this.meetup.location.length < 1)
        this.error.location = "음식점을 입력해주세요.";
      else {
        this.error.location = false;
        return;
      }
      if (this.meetup.date.length < 1) this.error.date = "날짜를 입력해주세요.";
      else {
        this.error.date = false;
        return;
      }
      if (this.meetup.personnel.length < 1)
        this.error.personnel = "인원을 입력해주세요.";
      else {
        this.error.personnel = false;
        return;
      }
    },
    search() {
      axios
        .get(`${SERVER_URL}/curation?location=${this.keyword}`)
        .then((response) => {
          // 음식점리스트 받기
          var restaurants = response.data.list;

          // 음식점리스트 돌면서 좌표(position), 거리(dist) 구하기
          restaurants.forEach((restaurant) => {
            // String 형태의 img src 5개를 파싱해서 배열로 만듬
            let imgSrcs = restaurant.img;
            imgSrcs = imgSrcs.replace("[", "");
            imgSrcs = imgSrcs.replace("]", "");
            imgSrcs = imgSrcs.replace(/(\s*)/g, ""); // 모든공백제거
            imgSrcs = imgSrcs.split(",");
            restaurant.img = imgSrcs;

            // 주소 -> 좌표
            this.geocoder.addressSearch(
              restaurant.location,
              (result, status) => {
                if (status === kakao.maps.services.Status.OK) {
                  var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                }
                restaurant.position = coords;

                // 현위치에서 음식점까지의 거리(dist) 구하기
                if (restaurant.position) {
                  var polyline = new kakao.maps.Polyline({
                    path: [
                      new kakao.maps.LatLng(
                        this.targetLocation.lat,
                        this.targetLocation.lng
                      ),
                      restaurant.position,
                    ],
                  });
                  restaurant.dist = polyline.getLength();
                }
              }
            );
          });
          this.restaurants = restaurants;
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
    select(restaurant) {
      console.log(restaurant);
      this.meetup.location = restaurant.name;
      this.meetup.address = restaurant.location;
      this.meetup.img = restaurant.img[0];
      this.dialog = false;
    },
  },
};
</script>


<style scoped>
.back {
  color: white !important;
}
.nothome {
  padding: 0px !important;
}
.party {
  padding: 0px 12px;
}
.section .article {
  position: relative;
  color: black;
}
div {
  display: block;
}
.tit {
  position: absolute;
  top: 8px;
  left: 0;
  font-weight: bold;
}
.fa-chevron-left {
  color: white;
  margin-left: 7px;
}
/* .btn-person{
  margin: 0px;
} */
</style>