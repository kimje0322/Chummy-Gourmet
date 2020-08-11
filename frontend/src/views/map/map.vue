
<template>
  <div class="map_wrap_1">
        
        <v-speed-dial
          style="top:470px; height:10px;"
          v-model="fab"
          absolute bottom right
          direction="top"
          transition="slide-y-reverse-transition"
          retain-focus-on-click="true"
        >
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

          <v-btn fab dark small color="pink" @click="moveCurPosition">
            <v-icon color="white">mdi-map-marker</v-icon>
          </v-btn>

          <v-btn fab dark small color="green">
            <router-link to="/map/createMeetup">
              <v-icon color="white">mdi-pencil</v-icon>
            </router-link>    
          </v-btn>

          <v-btn
            fab small dark
            color="indigo"
            @click="menu=true"
          >
            <v-icon color="white">mdi-tune-variant</v-icon>
          </v-btn>
          
        </v-speed-dial>

        <!-- 필터창  -->
          <div class="text-center">
            <v-menu
               v-model="menu"
              :close-on-click="false"
              :close-on-content-click="false"
              :nudge-width="375"
              :position-y="500"
              offset-overflow
              offset-y
            >

              <v-card>
                <v-list>

                  <v-list-item>
                    <v-select
                      ref="select1"
                      v-model="selectedFoods"
                      :items="foods"
                      label="선호음식을 선택해주세요"
                      multiple small-chips deletable-chips
                      prepend-icon="mdi-food"
                      auto
                    >

                      <template v-slot:append-item>
                        <v-divider class="mb-2"></v-divider>
                        <v-list-item>
                            <v-btn block @click="$refs.select1.blur()">
                              확인
                            </v-btn>
                        </v-list-item>
                      </template>


                    </v-select>
                  </v-list-item>

                  <v-list-item>
                    <v-select
                      ref="select2"
                      v-model="selectedProps"
                      :items="properties"
                      label="성향을 선택해주세요"
                      multiple small-chips deletable-chips
                      prepend-icon="mdi-heart"
                      auto
                    >
                    
                    <template v-slot:append-item>
                        <v-divider class="mb-2"></v-divider>
                        <v-list-item>
                            <v-btn block @click="$refs.select2.blur()">
                              확인
                            </v-btn>
                        </v-list-item>
                      </template>
                    
                    
                    </v-select>
                  </v-list-item>
                  
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

                      <v-date-picker v-model="dates" range scrollable show-current
                        :min="new Date().toISOString().substr(0, 10)"
                        max="2050-01-01"
                      >
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="modalCalendar = false">Cancel</v-btn>
                        <v-btn text color="primary" @click="$refs.dialog.save(dates)">OK</v-btn>
                      </v-date-picker>
                    </v-dialog>

                  </v-list-item>

                  <v-list-item>

                    <v-range-slider
                      v-model="personnel"
                      prepend-icon="mdi-account-group"
                      :tick-labels="['2','3','4','5','6','7','8']"
                      min="2" max="8"
                      ticks="always" tick-size="2"
                    >
                    </v-range-slider>

                  </v-list-item>

                </v-list>

                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn text @click="menu = false">닫기</v-btn>
                  <v-btn color="primary" text @click.stop="doFilter" @click="menu=false">적용</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </div>





    <div id="map1" style="position:unset;">map</div>
    <div id="menu_wrap" class="bg_white">
        <div class="option">
            <div>
              <!-- <v-container>
                <v-text-field @keyup.enter="search" v-model="keyword">
                  <template v-slot:label>
                    Enter the location..<v-icon style="vertical-align: middle">mdi-find</v-icon>
                  </template>
                </v-text-field>
              </v-container> -->
              <input @keyup.enter="search"  v-model="keyword" type="text" placeholder="입력창"> 
              <button @click="search"><v-icon size="20">mdi-magnify</v-icon></button> 
            </div>
        </div>
    </div>
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
        lat : '',
        lng : '',
      },
      keyword : '',


      modalFoods : false,
      foods: ['한식', '중식', '양식', '일식', '분식', '뷔페'],
      selectedFoods : [],

      modalProps : false,
      properties : [
        "낙천적", "부정적", "내향적", "외향적", "충동적", "사교적",
        "대담함", "성실함", "냉정함", "온화함", "신중함", "게으름"
      ],
      selectedProps : [],
      
      
      modalCalendar: false,
      dates: [new Date().toISOString().substr(0, 10),new Date().toISOString().substr(0, 10)],

      personnel : [2,8],

      menu: false,
      fab : false,
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
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(pos => {
        this.targetLocation.lat = pos.coords.latitude;
        this.targetLocation.lng = pos.coords.longitude;
      });
    }

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
    doFilter(){
      console.log(this.selectedFoods);
      console.log(this.selectedProps);
      console.log(this.dates);
      console.log(this.personnel);
    },
    moveCurPosition(){
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
          var coords = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
          this.map.setCenter(coords);
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
            .get(`${SERVER_URL}/meetup/search/${address.region_1depth_name} ${address.region_2depth_name}`)
            .then((response) => {
              // 밋업 리스트
              var meetups = response.data.object;

              var markers = [];
              var overlays = [];
              var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
              meetups.forEach(meetup => {
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
                                              <div><label>현재원 : </label> ${meetup.personnel}</div>
                                          </div>
                                      </div>
                                  </div> 
                              </div>
                            `
                        });
                        overlays.push(overlay);
                        kakao.maps.event.addListener(marker, "click", this.toggleInfoWindow(this.map, marker, overlay, overlays));
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

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                this.map.setCenter(coords);
            } 
        });    
    }
  }
}

</script>


<style>
#map1 {
  width: 100%;
  height: 610px;
  position: unset;
}

.map_wrap1 {
  overflow: hidden;
  width: 100%;
  height: 667px;
  padding-top: 0px;
}
.radius_border {
  border: 1px solid #919191;
  border-radius: 5px;
}
.custom_zoomcontrol {
  position: absolute;
  top: 520px;
  right: 10px;
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
  width: 10px;
  height: 35px;
  padding: 12px 0;
  border: none;
}
.custom_zoomcontrol span:first-child {
  border-bottom: 1px solid #bfbfbf;
}

#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:hidden;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:14px;border-radius: 10px;height:55px;}
.bg_white {background:#fff;}
#menu_wrap hr { display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}




._wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
._wrap * {padding: 0;margin: 0;}
._wrap ._info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
._wrap ._info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
._info ._title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
._info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
._info .close:hover {cursor: pointer;}
._info ._body {position: relative;overflow: hidden; background: white;}
._info ._desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
._desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
._desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
._info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
._info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
._info .link {color: #5085BB;}

</style>

