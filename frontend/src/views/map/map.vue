
<template>
  <div class="map_wrap_1">
        <!-- SPEED DIAL -->
        <v-speed-dial
          v-model="fab"
          absolute
          direction="top"
          transition="slide-y-reverse-transition"
          retain-focus-on-click="true"
          style="bottom:200px; right:20px;"
        >
          <!-- BUTTON ACTIVATOR -->
          <template v-slot:activator>
            <v-btn
              v-model="fab"
              color="blue darken-2"
              dark small fab
            >
              <v-icon v-if="fab">mdi-close</v-icon>
              <v-icon v-else>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <!-- 현위치 버튼 -->
          <v-btn fab dark small color="pink" @click="moveCurPosition">
            <v-icon color="white">mdi-map-marker</v-icon>
          </v-btn>

          <!-- 밋업 생성 페이지 이동 버튼 -->
          <v-btn fab dark small color="green" @click="$router.push('/map/createMeetup')">
              <v-icon color="white" >mdi-pencil-plus</v-icon>
          </v-btn>

          <!-- 밋업 필터링 버튼 -->
          <v-btn fab small dark color="indigo" @click="menu=true">
            <v-icon color="white">mdi-tune-variant</v-icon>
          </v-btn>
        </v-speed-dial>

        <!-- 필터창  -->
          <div class="text-center">
            <v-menu
              transition="slide-y-transition"
               v-model="menu"
              :close-on-content-click="false"
              :min-width="375" :max-width="375"
              :position-y="235"
              :position-x="574"
              z-index="100"

            >
            <!-- POP OVER -->
              <v-card class="mx-auto">
                <v-list color="">
                  <v-list-item>
                    <v-list-content>
                      <v-list-item-title class="text-h6">밋업 필터</v-list-item-title>
                      <v-list-item-subtitle>취향에 맞게 밋업을 검색해보세요 :)</v-list-item-subtitle>
                    </v-list-content>
                  </v-list-item>
                </v-list>
                <v-divider class="my-0"></v-divider>
                <v-list>
                  <!-- 선호 음식 -->
                  <v-list-item>
                    <v-dialog v-model="modalFoods" :max-width="330">
                      <template v-slot:activator="{ on, attrs }">
                        <v-combobox
                          ref="select1"
                          v-model="selectedFoods"
                          label="선호음식을 선택해주세요"
                          multiple small-chips
                          prepend-icon="mdi-food"
                          v-on="on" v-bind="attrs"
                          auto clearable
                        >
                             <template v-slot:selection="data">
                                <v-chip
                                  color="primary" small
                                >
                                  <span class="mr-2">
                                    {{data.item}}
                                  </span>
                                  <v-icon small @click="data.parent.selectItem(data.item)">
                                    mdi-close
                                  </v-icon>
                                </v-chip>
                            </template>
                        </v-combobox>
                      </template>

                      <!-- FOOD DIALOG -->
                      <v-card>
                        <v-card-title class="subtitle">선호음식
                          <v-spacer></v-spacer>
                          <v-btn outlined rounded small
                            class="text-caption font-weight-bold"
                            :class="{'primary--text': isAllFoodsSelect}"
                            @click="doAllFoodsSelect"
                          >
                            <v-icon small>mdi-check</v-icon>                            
                            전체
                          </v-btn>
                          <!-- <v-btn icon>
                            <v-icon>mdi-close</v-icon>
                          </v-btn> -->
                        </v-card-title>
                        
                        <v-divider class="mt-1 mb-6"></v-divider>
                        <v-card-text>
                          <v-btn-toggle v-model="selectedFoods" multiple color="primary">
                            <v-row justify="center">
                              <v-btn tile outlined large v-for="food in foods" :key="food" :value="food"
                              active-class="primary--text">
                                {{food}}
                              </v-btn>
                            </v-row>
                          </v-btn-toggle>
                        </v-card-text>
                        <v-card-actions>
                          <v-btn block color="primary" @click="modalFoods = false">확인</v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </v-list-item>

                  <!-- 성향 -->
                  <v-list-item>
                    <v-dialog v-model="modalProps" :max-width="330">
                      <template v-slot:activator="{ on, attrs }">
                        <v-combobox
                          ref="select2"
                          v-model="selectedProps"
                          label="성향을 선택해주세요"
                          multiple small-chips
                          prepend-icon="mdi-heart"
                          v-on="on" v-bind="attrs"
                          auto clearable
                        >
                             <template v-slot:selection="data">
                                <v-chip class="mr-1 px-2"
                                  color="primary" small
                                >
                                  <span class="mr-1">
                                    {{data.item}}
                                  </span>
                                  <v-icon small @click="data.parent.selectItem(data.item)">
                                    mdi-close
                                  </v-icon>
                                </v-chip>
                            </template>
                        </v-combobox>
                      </template>

                      <!-- PERSONALITIES DIALOG -->
                      <v-card>
                        <v-card-title class="subtitle">성향
                          <v-spacer></v-spacer>
                          <v-btn outlined rounded small
                            class="text-caption font-weight-bold"
                            :class="{'primary--text': isAllPropsSelect}"
                            @click="doAllPropsSelect"
                          >
                            <v-icon small>mdi-check</v-icon>                            
                            전체
                          </v-btn>
                          <!-- <v-btn icon>
                            <v-icon>mdi-close</v-icon>
                          </v-btn> -->
                        </v-card-title>
                        
                        <v-divider class="mt-1 mb-6"></v-divider>
                        <v-card-text>
                          <v-btn-toggle v-model="selectedProps" multiple color="primary">
                            <v-row justify="center">
                              <v-btn tile outlined large v-for="props in personalities" :key="props" :value="props"
                              active-class="primary--text">
                                {{props}}
                              </v-btn>
                            </v-row>
                          </v-btn-toggle>
                        </v-card-text>
                        <v-card-actions>
                          <v-btn block color="primary" @click="modalProps = false">확인</v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </v-list-item>

                  <!-- 밋업 기간 -->
                  <v-list-item>
                    <v-dialog
                      ref="dialog" v-model="modalCalendar"
                      :return-value.sync="dates"
                      persistent width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          prepend-icon="mdi-calendar-month-outline"
                          v-model="dateRangeText"
                          label="기간을 선택해주세요"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>

                      <v-date-picker v-model="dates" range scrollable show-current no-title
                        :min="new Date().toISOString().substr(0, 10)"
                        max="2050-01-01"
                      >
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="modalCalendar = false">Cancel</v-btn>
                        <v-btn text color="primary" @click="setDate">OK</v-btn>
                      </v-date-picker>
                    </v-dialog>
                  </v-list-item>

                  <!-- 밋업 인원 -->
                  <p class="ml-12 mb-n2 text-caption">인원을 선택해주세요</p>
                  <v-list-item>
                      <v-range-slider
                        v-model="personnel"
                        track-color="grey"
                        prepend-icon="mdi-account-group"
                        :tick-labels="['2','3','4','5','6','7','8']"
                        min="2" max="8"
                        ticks="always" tick-size="2"
                      >
                      </v-range-slider>
                  </v-list-item>
                </v-list>

                <!-- POP OVER FOOTER-->
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn text @click="menu = false">닫기</v-btn>
                  <v-btn color="primary" text @click.stop="doFilter" @click="menu=false">적용</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </div>





    <div id="map1" style="position:absolute">map</div>
    <!-- <div id="menu_wrap" class="bg_white"> -->
        <!-- <div class="option"> -->
            <div>
              <v-text-field @keyup.enter="search" v-model="keyword"
              placeholder="지역을 입력하세요.."
              append-icon="mdi-magnify"
              background-color="white"
              solo rounded clearable
              style="position:absolute; left:10px; top:10px; z-index:20; opacity:.8;"
              > 
              </v-text-field>

              <!-- <div class="search-box">
                <input type="text" />
                <span></span>
              </div> -->
            </div>


            <!-- snackbar -->
             <v-snackbar
              v-model="snackbar"
              :color="snackbarColor"
              timeout="3000"
            >
              {{snackbarText}}

              <template v-slot:action="{ attrs }">
                <v-btn
                  dark
                  text
                  v-bind="attrs"
                  @click="snackbar = false"
                >
                  닫기
                </v-btn>
              </template>
            </v-snackbar>
        <!-- </div> -->
    <!-- </div> -->
    <div class="custom_zoomcontrol radius_border">
      <span @click="zoomIn">
        <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대" />
      </span>
      <span @click="zoomOut">
        <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소" />
      </span>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const CURLAT = 36.3587222, CURLNG = 127.3439205;
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  data: () => {
    return {
      map: "",
      targetLocation :{
        lat : CURLAT,
        lng : CURLNG,
      },
      keyword : '',
      meetups : '',
      markers : [],
      overlays : [],

      isAllFoodsSelect : false,
      isAllPropsSelect : false,
      modalFoods : false,
      foods: ['한식', '중식', '양식', '일식', '분식', '뷔페'],
      selectedFoods : [],

      modalProps : false,
      personalities : [
        "낙천적", "부정적", "내향적", "외향적", "충동적", "사교적",
        "대담함", "성실함", "냉정함", "온화함", "신중함", "게으름"
      ],
      selectedProps : [],
      
      
      modalCalendar: false,
      dates: [new Date().toISOString().substr(0, 10),new Date().toISOString().substr(0, 10)],

      personnel : [2,8],

      menu: false,
      fab : false,

      snackbar : false,
      snackbarText : '',
      snackbarColor : '',
    };
  },
  computed: {
    dateRangeText () {
      if(this.dates[0] > this.dates[1]){
        this.swap(this.dates);
      }
      return this.dates.join(' ~ ')
    }
  },
  mounted() {
    // 현재 위치 확인
    // if (navigator.geolocation) {
    //   navigator.geolocation.getCurrentPosition(pos => {
    //     this.targetLocation.lat = pos.coords.latitude;
    //     this.targetLocation.lng = pos.coords.longitude;
    //   });
    // }

    // 카카오 맵 로딩
    // if (window.kakao && window.kakao.maps) {
    //   console.log("이미 로딩됨");
    //   this.initMap();
    // } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=90891b3c4fa765cd378361c6b16e4dd6&libraries=services";
      document.head.appendChild(script);
    // }
  },
  methods: {
    setDate(){
      if(this.dates.length == 1){
        this.dates.push(this.dates[0]);
      }
      this.$refs.dialog.save(this.dates);
    },
    doAllFoodsSelect(){
      this.$nextTick( () => {
        if(this.selectedFoods.length===this.foods.length){
          this.isAllFoodsSelect = false;
          this.selectedFoods=[];
        }
        else{
          this.foods.forEach(food => {
            if(this.selectedFoods.indexOf(food) == -1){
              this.selectedFoods.push(food)
            }
          });
          this.isAllFoodsSelect = true;
        }
      })
    },
    doAllPropsSelect(){
      this.$nextTick( () => {
        if(this.selectedProps.length===this.personalities.length){
          this.isAllPropsSelect = false;
          this.selectedProps=[];
        }
        else{
          this.personalities.forEach(props => {
            if(this.selectedProps.indexOf(props) == -1){
              this.selectedProps.push(props)
            }
          });
          this.isAllPropsSelect = true;
        }
      })
    },
    doFilter(){
      this.meetups.forEach((meetup, index) => {
        var isPersonalites = true;
        var isDate = true;
        var isPersonnel = true;
        var isCategory = true;

        // 카테고리 필터링
        for(let i = 0; i< this.selectedFoods.length; i++){
          // 밋업이 필터성향을 하나라도 포함하고 있으면
          if(meetup.category == this.selectedFoods[i])
            break;
          
          // 밋업이 필터성향을 하나도 포함하고 있지 않으면
          else{
            if(i == this.selectedFoods.length - 1)
              isCategory = false;
          }
        }
        // 성향 필터링
        for(let i = 0; i<this.selectedProps.length; i++){
          // 밋업이 필터성향을 하나라도 포함하고 있으면
          if(meetup.personalities.indexOf(this.selectedProps[i]) > -1)
            break;
          
          // 밋업이 필터성향을 하나도 포함하고 있지 않으면
          else{
            if(i == this.selectedProps.length - 1)
              isPersonalites = false;
          }
        }

        // 인원 필터링
        if(meetup.curPersonnel < this.personnel[0] || meetup.curPersonnel > this.personnel[1])
          isPersonnel = false;
        
        // 날짜 필터링
        if(new Date(meetup.date) < new Date(this.dates[0]) || new Date(meetup.date) > new Date(this.dates[1]))
          isDate = false;
        
        if(isPersonalites && isDate && isPersonnel && isCategory)
          this.markers[index].setMap(this.map)
        else
          this.markers[index].setMap(null)

      });

      
    },
    moveCurPosition(){
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          var coords = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
          this.map.panTo(coords);
        });
     }
    },
    swap(dates) {
        let temp = this.dates[0];
        this.dates[0] = this.dates[1];
        this.dates[1] = temp;
    },
    initMap() {
      // map 초기설정
      var container = document.getElementById("map1");
      var options = {
        center: new kakao.maps.LatLng(this.targetLocation.lat, this.targetLocation.lng),
        level: 3
      };
      this.map = new kakao.maps.Map(container, options);

      // 좌표->주소
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.coord2Address(CURLNG, CURLAT, (result, status) => {
        if(status === kakao.maps.services.Status.OK){

          // 현재 주소
          var address = result[0].address;
          if(result[0].road_address){
            address = result[0].road_address;
          }

          // 해당 위치 주변의 밋업 리스트          
          axios
            .get(`${SERVER_URL}/meetup/search/location/${address.region_1depth_name} ${address.region_2depth_name}`)
            .then((response) => {
              // 밋업 리스트
              this.meetups = response.data.object;

              var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
              this.meetups.forEach(meetup => {
                // console.log(meetup)
                geocoder.addressSearch(meetup.address, (result, status) => {
                    if (status === kakao.maps.services.Status.OK) {
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                        // marker 생성
                        var markerImage = new kakao.maps.MarkerImage(imageSrc, new kakao.maps.Size(24, 35));
                        var marker = new kakao.maps.Marker({
                          title: meetup.title,
                          position: coords,
                          image : markerImage,
                          clickable: true,
                        });

                        // custom overlay infowindow 생성
                        var overlay = new kakao.maps.CustomOverlay({     
                          position: marker.getPosition(),
                          content: 
                            `
                              <div class="_wrap">
                                  <div class="_info">
                                      <div class="_title">
                                          <a href="#/map/detailMeetup?meetupId=${meetup.id}" class="">${meetup.title}</a>
                                      </div>
                                      <div class="_body">
                                          <div class="img">
                                              <img src="${meetup.img}" width="73" height="70"/>
                                          </div>
                                          <div class="_desc">
                                              <div class="ellipsis"><label>일시 : </label> ${meetup.date}</div>
                                              <div class="ellipsis"><label>위치 : </label> ${meetup.location}</div>
                                              <div><label>인원 : </label> ${meetup.curPersonnel} / ${meetup.maxPersonnel}</div>
                                          </div>
                                      </div>
                                  </div> 
                              </div>
                            `
                        });
                        this.overlays.push(overlay);
                        this.markers.push(marker);
                        kakao.maps.event.addListener(marker, "click", this.toggleInfoWindow(this.map, marker, overlay, this.overlays));
                        marker.setMap(this.map);
                    } 
                });    
                  
              });

            })
        }
      });
    },
    zoomIn() {
      this.map.setLevel(this.map.getLevel() - 1);
    },
    zoomOut() {
      this.map.setLevel(this.map.getLevel() + 1);
    },
    toggleInfoWindow(map, marker, overlay, overlays) {
      return function() {
        // 떠있는 인포윈도우 다 제거
        overlays.forEach(element => {
            if (element != overlay && element.getMap() != null) {
              element.setMap(null);
            }
        });

        // 클릭한 마커의 인포윈도우만 띄우기
        if (overlay.getMap() == null) {
          overlay.setMap(map);
        } else {
          overlay.setMap(null);
        }
      }
    },
    search(){
        if (!this.keyword.replace(/^\s+|\s+$/g, '')) {
          alert('키워드를 입력해주세요!');
          return false;
        }
        // 장소 검색 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();
        
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(this.keyword, (result, status) => {

            // 정상적으로 검색이 완료됐으면 
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                this.snackbarText = "[ " + this.keyword + " ] 으로 이동합니다 :)";
                this.snackbarColor = "info";
                this.snackbar = true;
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                this.map.setCenter(coords);
            } else{
              this.snackbarText = "없는 지역이예요.. 다시 확인해주세요 :(";
              this.snackbarColor = 'error';
              this.snackbar = true;
              
            }
        });    
    }
  }
}


</script>


<style>
#map1 {
  width: 100%;
  height: 969px;
}

.map_wrap_1 {
  overflow: hidden;
  width: 100%;
  height: 969px;

}
.radius_border {
  border: 1px solid #919191;
  border-radius: 5px;
}
/* .v-application .blue.darken-2 {
  background-color: #ff9800 !important;
} */
.custom_zoomcontrol {
  border: none;
  margin-right: 7px;
  position: absolute;
  bottom: 100px;
  right : 20px;
  width: 36px;
  height: 80px;
  overflow: hidden;
  z-index: 15;
  background-color: #f5f5f5;
}
.custom_zoomcontrol span {
  display: block;
  width: 34px;
  height: 40px;
  text-align: center;
  cursor: pointer;
}
.custom_zoomcontrol span img {
  height: 35px;
  padding: 12px 0;
  border: none;
}
.custom_zoomcontrol span:first-child {
  border-bottom: 1px solid #bfbfbf;
}

.bg_white {background:#fff;}
#menu_wrap hr { display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option button {margin-left:5px;}




._wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
._wrap * {padding: 0;margin: 0;}
._wrap ._info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
._wrap ._info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
._info .close:hover {cursor: pointer;}
._info ._body {position: relative;overflow: hidden; background: white;}
._info ._desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
._desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
._desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
._info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
._info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
._info .link {color: #5085BB;}





</style>

