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
        <div class="cssBox">
          <div class="input-group mb-3">
            <v-text-field hide-details v-model="meetup.title" solo placeholder="제목">
            </v-text-field>
          </div>
          <div class="error-text" v-if="error.title">{{ error.title }} </div>
        </div>
        <!-- 파티 내용 -->
        <div class="cssBox">
        <div class="input-group mb-3">
          <v-textarea hide-details v-model="meetup.content" solo placeholder="내용을 입력하세요."></v-textarea>
        </div>
        <div class="error-text" v-if="error.content">{{ error.content }} </div>
        </div>
        
        <!-- 파티 장소 -->
          <div class="cssBox">
          <v-layout justify-center>
            <v-dialog v-model="dialog" scrollable max-width="300px">
              <template v-slot:activator="{on}">
                <v-text-field
                  hide-details
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
                              <v-card  :elevation="hover ? 12 : 2"
                              :class="{ 'on-hover': hover }"
                              @click="select(restaurant)">
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
         <div class="error-text mt-2" v-if="error.location">{{ error.location }} </div>
         </div>

        <!-- 파티 날짜 -->
        <div class="cssBox">
        <v-menu
          v-model = "menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          >

          <!-- 월/일 선택 -->
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              hide-details
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
                  <v-btn text color="primary"  v-on="on" v-bind="attrs">OK</v-btn>
                </template>
                <v-time-picker
                  v-model="time"
                  ampm-in-title
                  width="260px"
                >
                <v-spacer></v-spacer>
                <v-btn text color="primary" @click="menu2 = false">Cancel</v-btn>
                <v-btn text color="primary" @click="setDate">OK</v-btn>
              </v-time-picker>

            </v-menu>
          </v-date-picker>
        </v-menu>
        <div class="error-text mt-2" v-if="error.date">{{ error.date }} </div>
        </div>
      


        <!-- 파티 인원 -->
        <div id="dropdown-example" class="cssBox">
          <v-overflow-btn
            hide-details
            v-model="meetup.maxPersonnel"
            solo
            class="my-2"
            :items="dropdown_font"
            placeholder="인원"
            style="margin:0px;"
            target="#dropdown-example"
          ></v-overflow-btn>
        <div class="error-text" v-if="error.personnel">{{ error.personnel }} </div>
        </div>

        <!-- 성향 -->
        <div>
        <div class="text-center">
          <v-dialog
            v-model="dialog2"
            width="500"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-combobox 
                hide-details
                v-model="meetup.personalities" 
                solo multiple chips deletable-chips
                v-on="on" v-bind="attrs"
                label="성향"
              >
              </v-combobox>
            </template>

            <v-card
              class="mx-auto"
              max-width="400"
            >
              <v-toolbar
                flat dark
              >
              <v-btn icon>
                  <v-icon @click="dialog2 = false">mdi-close</v-icon>
                </v-btn>
                <v-toolbar-title>밋업 성향 선택</v-toolbar-title>
              </v-toolbar>

              <v-card-text>
                <h2 class="subtitle mb-1">원하는 성향을 선택하세요</h2>
                <v-chip-group
                  v-model="meetup.personalities"
                  active-class="deep-purple--text text--accent-4"
                  column
                  multiple
                >
                  <v-chip outlined v-for="personality in personalities" :value="personality" :key="personality">
                  {{personality}}                        
                  </v-chip>
                </v-chip-group>
              </v-card-text>
            </v-card>
          </v-dialog>
        </div>
          <div class="error-text mt-2" v-if="error.personality">{{ error.personality }} </div>
        </div>
        
      </div>
    <!-- </v-app> -->
  </div>
</template>

<script>
import axios from "axios";

// const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
const SERVER_URL = "https://localhost:8080";

export default {
  data: () => {
    return {
      keyword : '',
      date: '',
      time: null,
      menu: false,
      menu2 : false,
      isColor: false,
      isColor1: false,
      isClick: false,
      dialog: false,
      dialog2: false,

      restaurants : [],
      selectedRest : '',

      meetup : {
        title : '',
        content : '',
        location : '',
        address : '',
        date : '',
        maxPersonnel : '',
        master : 70,
        img : '',
        personalities : [],
      },

      error: {
        title: false,
        content: false,
        location: false,
        date: false,
        personnel: false,
        master : false,
        personality: false,
      },

      dropdown_font: ["1", "2", "3", "4", "5", "6", "7", "8"],

      personalities : [
        "낙천적", "부정적", "내향적", "외향적", "충동적", "사교적",
        "대담함", "성실함", "냉정함", "온화함", "신중함", "게으름"
      ],

      targetLocation : {
        lat : '',
        lng : ''
      },
      geocoder : '',
    }
  },
  watch: {
    'meetup.title' : function() {
      if (this.meetup.title.length < 1) this.error.title = "제목을 입력해주세요.";
      else this.error.title = false;
      return;
    },
    'meetup.content' : function() {
      if ( this.meetup.content.length < 1 ) this.error.content = "내용을 입력해주세요.";
      else this.error.content = false;
      return;
    },
    'meetup.location' : function() {
      if (!this.meetup.location)
        this.error.location = "음식점을 입력해주세요.";
      else this.error.location = false;
      return;
    },
    'meetup.date' : function() {
      if (this.meetup.date.length < 1) this.error.date = "날짜를 입력해주세요.";
      else this.error.date = false;
      return;
    },
    'meetup.personnel' : function() {
      if (this.meetup.personnel.length < 1) this.error.personnel = "인원을 입력해주세요.";
      else this.error.personnel = false;
      return;
    },
  },

  mounted() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
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
    setDate() {
      this.menu = false;
      this.menu2 = false;
      this.meetup.date = this.date + " " + this.time;
    },
    initMap() {
        // 좌표->주소
        this.geocoder = new kakao.maps.services.Geocoder();
    },
    meetUp() {
      this.createMeetUpChat();
      if (this.meetup.title.length === 0) {
        this.error.title = "제목을 입력해주세요.";
        return false;
      }
      else this.error.title = false;

      if (this.meetup.content.length === 0) {
        // alert("내용을 작성해주세요.");
        this.error.content = "내용을 입력해주세요.";
        return false;
      }
      else this.error.content = false;

      if (!this.meetup.location) {
        this.error.location = "장소를 선택해주세요.";
        return false;
      }
      else this.error.location = false;
      
      if (!this.meetup.date) {
        this.error.date = "날짜를 선택해주세요.";
        return false;
      }
      else this.error.date = false;

      if (!this.meetup.maxPersonnel) {
        this.error.personnel = "인원을 선택해주세요.";
        return false;
      }
      else this.error.personnel = false;


      if (!this.meetup.personalities) {
        this.error.personalities = "성향을 선택해주세요.";
        return false;
      }
      else this.error.personnel = false;


      // 밋업 등록 폼 유효성 검사
      if (this.isValidForm()){

        // 밋업 성향 배열을 String으로 변환
        var personalities = this.meetup.personalities.toString();
        personalities = `[${personalities}]`;

        // 새로운 밋업 객체 생성      
        var newMeetup = {
          title :this.meetup.title,
          content :this.meetup.content,
          location :this.meetup.location,
          address :this.meetup.address,
          date :this.meetup.date,
          maxPersonnel :this.meetup.maxPersonnel,
          master :this.meetup.master,
          img :this.meetup.img,
          category :this.meetup.category,
          personalities : personalities,
          restId : this.selectedRest.id,
        }

        // REST ID 추가 프로세스
        // DB에 존재하지 않는 음식점이라면 DB에 음식점 등록 후 REST ID 반환
        if(this.selectedRest.id == null){

          // img src 배열을 String으로 변환 "[src1,src2,...,src5]"
          var imgSrcs = this.selectedRest.img.toString();
          imgSrcs = `[${imgSrcs}]`;

          // 새로운 음식점 객체 생성
          var newRest = {
            category : this.selectedRest.category,
            img : imgSrcs,
            like : this.selectedRest.like,
            location : this.selectedRest.location,
            name : this.selectedRest.name,
            review : this.selectedRest.review,
            scrap : this.selectedRest.scrap,
            telphone : this.selectedRest.telphone,
            url : this.selectedRest.url,
            dist  : this.selectedRest.dist
          };

          // DB에 음식점 정보 등록 후 restId 반환
          axios
          .post(`${SERVER_URL}/rest`, newRest)
          .then((response) => {
              this.selectedRest.id = response.data;
              newMeetup.restId = this.selectedRest.id;

              // 밋업 객체 DB 등록
              axios
                .post(`${SERVER_URL}/meetup`, newMeetup)
                .then((response) => {
                  this.createMeetUpChat()
                  alert("밋업 등록이 완료됐습니다.");
                  this.$router.push("/map")
                })
                .catch((error) => {
                  console.log('error보기')
                })
          });
        }

        // DB에 존재하는 음식점이라면
        else{
          // 밋업 객체 DB 등록
          axios
            .post(`${SERVER_URL}/meetup`, newMeetup)
            .then((response) => {
              this.createMeetUpChat()
              alert("밋업 등록이 완료됐습니다.");
              this.$router.push("/map")
            })
            .catch((error) => {
              console.log('error보기')
            })
        }
      }
    },

    //밋업에대한 채팅방 등록
    createMeetUpChat(){

      const newRoomRef = window.db.collection('test').doc();
                            console.log(newRoomRef.id);
                            window.db.collection('test').doc(newRoomRef.id).collection('test').doc();

                             var res = window.db.collection('test').doc(newRoomRef.id).set({
                                id :[this.meetup.master],
                                time : Date.now(),
                                name : this.meetup.title
                            }).catch(err =>{
                                console.log(err);
                            })

                            alert("새로운 채팅방 생성");
                            return;




  // 유저 밋업에 추가할 때 사용할 코드 삭제하지 말 것
    
    //  const newRoomRef = window.db.collection('test').where('name','==','테스트').get()
    //  .then(snapshot =>{
    //    if(snapshot.empty){
    //      alert("없다");
    //    }
    //    snapshot.forEach(doc=>{
    //      alert(doc.data().id)
    //      var id = doc.data().id;
    //      id.push('56');
    //      id.push('2');
    //      alert(id.sort())
    //      window.db.collection('test').doc(doc.id).set({
    //        id : id
    //      },{merge:true});
    //    })
    //  })
                          
    },
    
    checkForm() {
      if (this.meetup.title.length < 1) this.error.title = "제목을 입력해주세요.";
      else { this.error.title = false; return; }
      if ( this.meetup.content.length < 1 ) this.error.content = "내용을 입력해주세요.";
      else {this.error.content = false;
      return;}
      if (this.meetup.location.length < 1)
        this.error.location = "음식점을 입력해주세요.";
      else {this.error.location = false;
      return;}
      if (this.meetup.date.length < 1) this.error.date = "날짜를 입력해주세요.";
      else {this.error.date = false;
      return;}
      if (this.meetup.maxPersonnel.length < 1) this.error.personnel = "인원을 입력해주세요.";
      else {this.error.personnel = false;
      return;}
      if (this.meetup.personalities.length < 1) this.error.personality = "성향을 입력해주세요.";
      else {this.error.personality = false;
      return;}
    },
     // 입력 정보가 다 유효하면 true, 아니면 false
    isValidForm() {
      for (let key in this.error) {
        if (this.error[key]) return false;
      }
      return true;
    },
    search(){
      axios
        .get(`${SERVER_URL}/curation?location=${this.keyword}`)
        .then((response) => {
          
          // 음식점리스트 받기
          var restaurants = response.data.list;

          // 음식점리스트 돌면서 좌표(position), 거리(dist) 구하기
          restaurants.forEach(restaurant => {

            // String 형태의 img src 5개를 파싱해서 배열로 만듬
            let imgSrcs = restaurant.img;
            imgSrcs = imgSrcs.replace('[', '');
            imgSrcs = imgSrcs.replace(']', '');
            imgSrcs = imgSrcs.replace(/(\s*)/g, ''); // 모든공백제거
            imgSrcs = imgSrcs.split(",");
            restaurant.img = imgSrcs;

            // 주소 -> 좌표
            this.geocoder.addressSearch(restaurant.location, (result, status) => {
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
        })
    },
    select(restaurant){
      this.selectedRest = restaurant;
      this.meetup.location = restaurant.name;
      this.meetup.address = restaurant.location;
      this.meetup.img = restaurant.img[0];
      this.meetup.category = restaurant.category;
      this.dialog = false;
    },
  },

};
</script>

<style>
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
.error-text {
  margin: 0 0 8px 8px;
}
.cssBox {
  margin: 0 0 18px 0;
}

</style>