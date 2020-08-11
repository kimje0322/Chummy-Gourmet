
<template>
  <div class="map_wrap_1">
          <v-btn
            fab
            small
            color="blue accent-2"
            bottom
            right
            absolute
            style="top:470px;"
          >
            <router-link to="/map/createMeetup">
              <v-icon color="white">mdi-plus</v-icon>
            </router-link>
          </v-btn>
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
        <!-- <hr>
        <ul id="placesList"></ul>
        <div id="pagination"></div> -->
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
    };
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
    //   console.log("카카오맵 로딩");
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=90891b3c4fa765cd378361c6b16e4dd6&libraries=services";
      document.head.appendChild(script);
    // }
  },
  methods: {
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
                                              <img src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y" width="73" height="70"/>
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
                            // `
                            //   <div class="overlay__info">
                            //     <a class="name" href="https://place.map.kakao.com/17600274" target="_blank"><span class="">월정리 해수욕장</span></a>
                            //     <div class="_desc">
                            //         <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_thumb.png" alt="">
                            //         <div class="">${meetup.date}</div>
                            //         <div class="">${meetup.location}</div>
                            //     </div>
                            //   </div>
                            
                            // `
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


/* .overlay__info {border-radius: 6px; margin: -175px 0 12px 0; float:left;position: relative; border: 1px solid #ccc; border-bottom: 2px solid #ddd;background-color:#fff;}
.overlay__info:nth-of-type(n) {border:0; box-shadow: 0px 1px 2px #888;}
.overlay__info .name {display: block; background: #d95050; background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center; text-decoration: none; color: #fff; padding:12px 36px 12px 14px; font-size: 14px; border-radius: 6px 6px 0 0}
.overlay__info a span {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/place_icon.png) no-repeat; padding-left: 27px;}
.overlay__info ._desc {padding:14px;position: relative; min-width: 230px;}
.overlay__info img {vertical-align: top;}
.overlay__info ._address {font-size: 12px; color: #333; position: absolute; left: 80px; right: 14px; top: 24px; white-space: normal}
.overlay__info:after {content:'';position: absolute; margin-left: -11px; left: 50%; bottom: -12px; width: 22px; height: 12px; background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png) no-repeat 0 bottom;} */


    
</style>


