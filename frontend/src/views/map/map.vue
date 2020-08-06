
<template>
  <div class="map_wrap_1">
    <div id="map1" style="position:unset;">map</div>
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
const CURLAT = 36.3587222, CURLNG = 127.3439205;

export default {
  data: () => {
    return {
      map: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=90891b3c4fa765cd378361c6b16e4dd6";
      document.head.appendChild(script);
    }
  },
  beforeUpdate() {},
  methods: {
    initMap() {
      var container = document.getElementById("map1");
          var options = {
            center: new kakao.maps.LatLng(CURLAT, CURLNG),
            level: 3
          };
          this.map = new kakao.maps.Map(container, options);

          var positions = [
            {
              title: "봉명가든",
              latlng: new kakao.maps.LatLng(CURLAT, CURLNG)
            },
            {
              title: "봉명가든",
              latlng: new kakao.maps.LatLng(CURLAT, CURLNG)
            },
            {
              title: "맛찬들",
              latlng: new kakao.maps.LatLng(CURLAT + 0.0005, CURLNG + 0.0005)
            },
            {
              title: "대게나라",
              latlng: new kakao.maps.LatLng(CURLAT - 0.0005, CURLNG - 0.0005)
            }
          ];

          var meetUps = [
            {
              title: "삼겹살 먹으실 분",
              date: "2020-07-16 18:00",
              store: "맛찬들",
              address: "",
              members: "4/4",
              img:
                "https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
            },
            {
              title: "고기땡기노",
              date: "2020-07-16 17:00",
              store: "맛찬들",
              members: "2/4",
              img:
                "https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
            },
            {
              title: "커피한잔해요",
              date: "2020-07-16 19:00",
              store: "봉명가든",
              members: "3/4",
              img: "https://t1.daumcdn.net/cfile/tistory/9977864D5B07565017"
            },
            {
              title: "대게맛있는대게",
              date: "2020-07-16 20:00",
              store: "대게나라",
              members: "4/7",
              img: "https://t1.daumcdn.net/cfile/tistory/997F8E415A94097B0A"
            }
          ];

          var markers = [];
          var infoWindows = [];
          for (var i = 0; i < positions.length; i++) {
            var marker = new kakao.maps.Marker({
              title: meetUps[i].title,
              position: positions[i].latlng,
              clickable: true
            });
            var overlay = new kakao.maps.CustomOverlay({     
              position: marker.getPosition(),
              content: 
                  `
                     <div class="_wrap">
                        <div class="_info">
                            <div class="_title">
                                <a href="#/map/party" class="">${meetUps[i].title}</a>
                            </div>
                            <div class="_body">
                                <div class="img">
                                    <img src=${meetUps[i].img} width="73" height="70"/>
                                </div>
                                <div class="_desc">
                                    <div class="ellipsis"><label>일시 : </label> ${meetUps[i].date}</div>
                                    <div class="ellipsis"><label>위치 : </label> ${meetUps[i].store}</div>
                                    <div><label>현재원 : </label> ${meetUps[i].members}</div>
                                </div>
                            </div>
                        </div> 
                    </div>
                  `
            });
            kakao.maps.event.addListener(marker, "click", this.toggleInfoWindow(this.map, marker, overlay));
            marker.setMap(this.map);
          }
      // if (navigator.geolocation) {
      //   navigator.geolocation.getCurrentPosition(pos => {
      //     var curLat = pos.coords.latitude;
      //     var curLng = pos.coords.longitude;
      //     // console.log(curLat + "," + curLng);
          
      //   });
      // }
    },
    zoomIn() {
      this.map.setLevel(this.map.getLevel() - 1);
    },
    zoomOut() {
      this.map.setLevel(this.map.getLevel() + 1);
    },
    toggleInfoWindow(map, marker, overlay) {
      return function() {
        if (overlay.getMap() == null) {
          overlay.setMap(map);
        } else {
          overlay.setMap(null);
        }
      }
    },
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
  top: 475px;
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


