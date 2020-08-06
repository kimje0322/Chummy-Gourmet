
<template>
  <div class="map_wrap">
    <div id="map">map</div>
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
function toggleInfoWindow(map, marker, overlay) {
  return function() {
    if (overlay.getMap() == null) {
      overlay.setMap(map);
    } else {
      overlay.setMap(null);
    }
  };
}

  


export default {
  data: () => {
    return {
      map: "",
      fav: true,
      menu: true,
      message: true,
      hints: true
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
      var container = document.getElementById("map");
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
              //       ` 
              //         <div class="v-menu__content theme--light menuable__content__active" style="min-width: 359px; top: 8766px; left: 4px; transform-origin: left top; z-index: 8;">
              //           <div class="v-card theme--light">
              //             <div class="v-list theme--light">
              //               <div>
              //                 <div class="v-list__tile v-list__tile--avatar theme--light">
              //                   <div class="v-list__tile__avatar"><div class="v-avatar" style="height: 40px; width: 40px;">
              //                     <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John">
              //                   </div>
              //                 </div>
              //                 <div class="v-list__tile__content">
              //                   <div class="v-list__tile__title">John Leider</div>
              //                   <div class="v-list__tile__sub-title">Founder of Vuetify.js</div>
              //                 </div>
              //                 <div class="v-list__tile__action">
              //                   <button type="button" class="v-btn v-btn--icon theme--light red--text">
              //                     <div class="v-btn__content">
              //                       <i aria-hidden="true" class="v-icon material-icons theme--light">favorite</i>
              //                     </div>
              //                   </button>
              //                 </div>
              //               </div>
              //             </div>
              //           </div>
              //           <hr class="v-divider theme--light">
              //           <div class="v-list theme--light">
              //             <div>
              //               <div class="v-list__tile theme--light">
              //                 <div class="v-list__tile__action">
              //                   <div class="v-input v-input--selection-controls v-input--switch theme--light">
              //                     <div class="v-input__control">
              //                       <div class="v-input__slot">
              //                         <div class="v-input--selection-controls__input">
              //                           <input aria-checked="false" role="checkbox" type="checkbox" value="">
              //                             <div class="v-input--selection-controls__ripple">
              //                             </div>
              //                           <div class="v-input--switch__track theme--light">
              //                         </div>
              //                         <div class="v-input--switch__thumb theme--light">
              //                         <!-- -->
              //                       </div>
              //                     </div>
              //                   </div>
              //                   <div class="v-messages theme--light">
              //                     <div class="v-messages__wrapper">
              //                     </div>
              //                   </div>
              //                 </div>
              //               </div>
              //             </div>
              //             <div class="v-list__tile__title">Enable messages</div>
              //           </div>
              //         </div>
              //         <div>
              //           <div class="v-list__tile theme--light">
              //             <div class="v-list__tile__action">
              //               <div class="v-input v-input--selection-controls v-input--switch v-input--is-label-active v-input--is-dirty theme--light">
              //                 <div class="v-input__control">
              //                   <div class="v-input__slot">
              //                     <div class="v-input--selection-controls__input">
              //                       <input aria-checked="true" role="checkbox" type="checkbox" value="">
              //                       <div class="v-input--selection-controls__ripple purple--text">
              //                       </div>
              //                       <div class="v-input--switch__track theme--light purple--text">
              //                     </div>
              //                     <div class="v-input--switch__thumb theme--light purple--text">
              //                     <!-- -->
              //                     </div>
              //                   </div>
              //                 </div>
              //                 <div class="v-messages theme--light">
              //                   <div class="v-messages__wrapper">
              //                   </div>
              //                 </div>
              //               </div>
              //             </div>
              //           </div>
              //           <div class="v-list__tile__title">Enable hints</div>
              //         </div>
              //       </div>
              //     </div>
              //     <div class="v-card__actions">
              //       <div class="spacer"></div>
              //       <button type="button" class="v-btn v-btn--flat theme--light">
              //         <div class="v-btn__content">Cancel</div>
              //       </button>
              //       <button type="button" class="v-btn v-btn--flat theme--light primary--text">
              //         <div class="v-btn__content">Save</div>
              //       </button>
              //     </div>
              //   </div>
              // </div>
              //       `
                  `
                      <div class="container">
                        <div class="email" onclick="this.classList.add('expand')">
                          <div class="from">
                            <div class="from-contents">
                              <div class="avatar me"></div>
                              <div class="name">Mikael Ainalem</div>
                            </div>
                          </div>
                          <div class="to">
                            <div class="to-contents">
                              <div class="top">
                                <div class="avatar-large me"></div>
                                <div class="name-large">Mikael Ainalem</div>
                                <div class="x-touch" onclick="document.querySelector('.email').classList.remove('expand');event.stopPropagation();">
                                  <div class="x">
                                    <div class="line1"></div>
                                    <div class="line2"></div>
                                  </div>
                                </div>
                              </div>
                              <div class="bottom">
                                <div class="row">
                                  <svg class="twitter" viewBox="0 0 300 244.18703">
                                    <g transform="translate(-539.17946,-568.85777)" id="layer1">
                                      <path id="path3611" style="fill:#1da1f2;fill-opacity:1;fill-rule:nonzero;stroke:none" d="m 633.89823,812.04479 c 112.46038,0 173.95627,-93.16765 173.95627,-173.95625 0,-2.64628 -0.0539,-5.28062 -0.1726,-7.90305 11.93799,-8.63016 22.31446,-19.39999 30.49762,-31.65984 -10.95459,4.86937 -22.74358,8.14741 -35.11071,9.62551 12.62341,-7.56929 22.31446,-19.54304 26.88583,-33.81739 -11.81284,7.00307 -24.89517,12.09297 -38.82383,14.84055 -11.15723,-11.88436 -27.04079,-19.31655 -44.62892,-19.31655 -33.76374,0 -61.14426,27.38052 -61.14426,61.13233 0,4.79784 0.5364,9.46458 1.58538,13.94057 -50.81546,-2.55686 -95.87353,-26.88582 -126.02546,-63.87991 -5.25082,9.03545 -8.27852,19.53111 -8.27852,30.73006 0,21.21186 10.79366,39.93837 27.20766,50.89296 -10.03077,-0.30992 -19.45363,-3.06348 -27.69044,-7.64676 -0.009,0.25652 -0.009,0.50661 -0.009,0.78077 0,29.60957 21.07478,54.3319 49.0513,59.93435 -5.13757,1.40062 -10.54335,2.15158 -16.12196,2.15158 -3.93364,0 -7.76596,-0.38716 -11.49099,-1.1026 7.78383,24.2932 30.35457,41.97073 57.11525,42.46543 -20.92578,16.40207 -47.28712,26.17062 -75.93712,26.17062 -4.92898,0 -9.79834,-0.28036 -14.58427,-0.84634 27.05868,17.34379 59.18936,27.46396 93.72193,27.46396"/>
                                    </g>
                                  </svg>
                                  <div class="link"><a href="https://twitter.com/mikaelainalem">@mikaelainalem</a></div>
                                </div>
                                <div class="row">
                                  <svg class="medium" viewBox="0 0 24 24">
                                    <style type="text/css" id="style2">
                                      .st0{fill:#3DD87F;}
                                      .st1{fill:#FFFFFF;}
                                    </style>
                                    <g id="Page-1" transform="translate(0,1)">
                                      <g id="Monogram">
                                        <rect id="Rectangle-path" x="0" y="-1" class="st0" width="24" height="24" style="fill:#000000;fill-opacity:1;stroke-width:1.090909"/>
                                        <path id="Shape" class="st1" d="m 6.3,7.6 c 0,-0.2 -0.1,-0.4 -0.2,-0.5 l -1.3,-1.7 v -0.2 h 4.3 l 3.3,7.3 2.9,-7.3 h 4.1 v 0.2 l -1.2,1.1 c -0.1,0.1 -0.2,0.2 -0.1,0.3 v 8.3 c 0,0.1 0,0.3 0.1,0.3 l 1.2,1.1 v 0.2 h -5.8 v -0.2 l 1.2,-1.2 c 0.1,-0.1 0.1,-0.2 0.1,-0.3 v -6.6 l -3.3,8.4 h -0.4 l -4,-8.4 v 5.6 c 0,0.2 0,0.5 0.2,0.6 l 1.6,1.9 v 0.2 h -4.4 v -0.2 l 1.5,-1.8 c 0.2,-0.2 0.2,-0.4 0.2,-0.6 z" inkscape:connector-curvature="0" style="fill:#ffffff"/>
                                      </g>
                                    </g>
                                  </svg>
                                  <div class="link"><a href="https://medium.com/@mikael_ainalem">@mikael_ainalem</a></div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>

                  `
            });
            kakao.maps.event.addListener(marker, "click", toggleInfoWindow(this.map, marker, overlay));
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

  }
};
</script>

<style scoped>


#map {
  width: 100%;
  height: 610px;
}

.map_wrap {
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







body {
  align-items: center;
  background: #F1EEF1;
  display: flex;
  font-family: sans-serif;
  justify-content: center;
  height: 100vh;
  margin: 0;
}
.me {
  background-image: url(https://cdn.vuetifyjs.com/images/john.jpg);
  background-size: cover;
}
.container {
  align-items: center;
  /*       background: #F1EEF1;
  border: 1px solid #D2D1D4;
  */      display: flex;
  height: 360px;
  justify-content: center;
  width: 360px;
}
.email {
  background: #DEDBDF;
  border-radius: 16px;
  height: 32px;
  overflow: hidden;
  position: relative;
  width: 162px;
  -webkit-tap-highlight-color: transparent;
  transition: width 300ms cubic-bezier(0.4, 0.0, 0.2, 1),
    height 300ms cubic-bezier(0.4, 0.0, 0.2, 1),
    box-shadow 300ms cubic-bezier(0.4, 0.0, 0.2, 1),
    border-radius 300ms cubic-bezier(0.4, 0.0, 0.2, 1);
}
.email:not(.expand) {
  cursor: pointer;
}
.email:not(.expand):hover {
  background: #C2C0C2;
}
.from {
  position: absolute;
  transition: opacity 200ms 100ms cubic-bezier(0.0, 0.0, 0.2, 1);
}
.from-contents {
  display: flex;
  flex-direction: row;
  transform-origin: 0 0;
  transition: transform 300ms cubic-bezier(0.4, 0.0, 0.2, 1);
}
.to {
  opacity: 0;
  position: absolute;
  transition: opacity 100ms cubic-bezier(0.4, 0.0, 1, 1);
}
.to-contents {
  transform: scale(.55);
  transform-origin: 0 0;
  transition: transform 300ms cubic-bezier(0.4, 0.0, 0.2, 1);
}
.avatar {
  border-radius: 12px;
  height: 24px;
  left: 6px;
  position: relative;
  top: 4px;
  width: 24px;
}
.name {
  font-size: 14px;
  line-height: 32px;
  margin-left: 10px;
}
.top {
  background: #6422EB;
  display: flex;
  flex-direction: row;
  height: 70px;
  transition: height 300ms cubic-bezier(0.4, 0.0, 0.2, 1);
  width: 300px;
}
.avatar-large {
  border-radius: 21px;
  height: 42px;
  margin-left: 12px;
  position: relative;
  top: 14px;
  width: 42px;
}
.name-large {
  color: #efd8ef;
  font-size: 16px;
  line-height: 70px;
  margin-left: 20px;
}
.x-touch {
  align-items: center;
  align-self: center;
  cursor: pointer;
  display: flex;
  height: 50px;
  justify-content: center;
  margin-left: auto;
  width: 50px;
}
.x {
  background: #BA87F9;
  border-radius: 10px;
  height: 20px;
  position: relative;
  width: 20px;
}
.x-touch:hover .x {
  background: #CB9AFB;
}
.line1 {
  background: #6422EB;
  height: 12px;
  position: absolute;
  transform: translateX(9px) translateY(4px) rotate(45deg);
  width: 2px;
}
.line2 {
  background: #6422EB;
  height: 12px;
  position: absolute;
  transform: translateX(9px) translateY(4px) rotate(-45deg);
  width: 2px;
}
.bottom {
  background: #FFF;
  color:  #444247;
  font-size: 14px;
  height: 200px;
  padding-top: 5px;
  width: 300px;
}
.row {
  align-items: center;
  display: flex;
  flex-direction: row;
  height: 60px;
}
.twitter {
  margin-left: 16px;
  height: 30px;
  position: relative;
  top: 0px;
  width: 30px;
}
.medium {
  height: 30px;
  margin-left: 16px;
  position: relative;
  width: 30px;
}
.link {
  margin-left: 16px;
}
.link a {
  color:  #444247;
  text-decoration: none;
}
.link a:hover {
  color:  #777579;
}
.email.expand {
  border-radius: 6px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.10), 0 6px 6px rgba(0,0,0,0.16);
  height: 200px;
  width: 300px;
}
.expand .from {
  opacity: 0;
  transition: opacity 100ms cubic-bezier(0.4, 0.0, 1, 1);
}
.expand .from-contents {
  transform: scale(1.91);
}
.expand .to {
  opacity: 1;
  transition: opacity 200ms 100ms cubic-bezier(0.0, 0.0, 0.2, 1);
}
.expand .to-contents {
  transform: scale(1);
}


    
</style>


