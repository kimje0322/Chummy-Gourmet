<template>
  <v-app>
    <!-- 상단 -->
     <v-toolbar-title>
      <Top></Top>
    </v-toolbar-title>
    <!-- 가운데 부분 -->
    <div>
      <!-- tab view -->
      <v-tabs dark v-model="currentItem" fixed-tabs slider-color="grey">
      <v-tab class="follow-list" v-for="item in items" :key="item" :href="'#tab-' + item">
        <v-icon class="followListBtn" v-if="item=='follower'">팔로워</v-icon>
        <v-icon class="followListBtn" v-if="item=='following'">팔로잉</v-icon>
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="currentItem">
      <v-tab-item v-for="item in items" :key="item" :value="'tab-' + item">
        <v-card flat>
          
        <!-- 팔로워 -->
        <v-list nav dense  v-if="item=='follower'">
        <!-- 팔로워 존재하면  -->
          <!-- 팔로워 검색바 -->
          <v-row>
            <v-col class="search-bar">
            <v-text-field 
              @keyup="onSearchFollower"
              v-model="searchFollower"
              label="검색"
              :append-icon="'mdi-account-search-outline'"
              hide-details
              solo
            ></v-text-field>             
            </v-col>
          </v-row>

            <v-list-item class="followlist" v-for="(user, i) in followerList" :key="i">
                <!-- 사진 표기 -->
                <v-list-item-avatar @click="gotoProfile(user)">
                  <v-img
                    :src="user.followerImg">
                  </v-img>
                </v-list-item-avatar>
                
                <v-list-item-content>
                <v-list-item-title @click="gotoProfile(user)" v-text="user.followerNickname"></v-list-item-title>
                <!-- 이름 표기 -->
                <!-- <v-list-item-title v-text="user.followingName"></v-list-item-title> -->
                </v-list-item-content>

                <v-btn color="primary"  @click="onFollow(user,i)" v-if="user.followerFollowing === 'false'">
                    팔로우
                </v-btn>
                
                <v-btn depressed  @click="deleteFollowRequest(user,i)" v-else-if="user.followerFollowing === 'doing'">
                    요청중
                </v-btn>

                <v-btn depressed @click="unFollow(user,i,'followerlist')" v-else>
                    팔로잉
                </v-btn>
            </v-list-item>
        <!-- 팔로워 존재하지 않으면 -->
        <div v-if="followerList.length == 0">
          <i class="fas fa-user-plus fa-6x"></i>
          <div class="text-css">
            <h1>팔로워</h1>
            <p>회원님을 팔로우하는 모든 사람이 표시됩니다.</p>
          </div>
        </div>
        </v-list>

        <!-- 팔로잉 -->
        <v-list nav dense v-else>
        <!-- 팔로잉 존재하면  -->
          <!-- 팔로잉 검색바 -->
          <v-row>
            <v-col class="search-bar">
              <v-text-field 
                @keyup="onSearchFollowing"
                v-model="searchFollowing"
                label="검색"
                :append-icon="'mdi-account-search-outline'"
                hide-details
                solo
              ></v-text-field>             
            </v-col>
          </v-row>

        <v-list-item class="followlist" v-for="(user, i) in followingList" :key="i">
            <!-- 사진 표기 -->
            <v-list-item-avatar @click="gotoProfile(user)">
              <v-img
                :src="user.followingImg">
              </v-img>
            </v-list-item-avatar>

            <v-list-item-content>
            <v-list-item-title @click="gotoProfile(user)" v-text="user.followingNickname"></v-list-item-title>
            </v-list-item-content>

            <v-btn depressed @click="unFollow(user,i,'followinglist')">
                팔로잉
            </v-btn>
          </v-list-item>

          <div v-if="followingList.length == 0">
            <i class="fas fa-user-plus fa-6x"></i>
            <div class="text-css">
              <h1>팔로잉</h1>
              <p>회원님이 팔로우하는 모든 사람이 표시됩니다.</p>
            </div>
          </div>
          </v-list>
        </v-card>
      </v-tab-item>
    </v-tabs-items>

    </div>
  </v-app>
</template>

<script>
import Top from "../common/Top";

import axios from "axios";
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";
export default {
  name: "components",
  components: {
     Top,
  },
 
  data: () => {
    return {
        anotherId:"",
        nicknameForProfile: "",
        searchFollower: "",
        searchFollowing:"",
        userId: "",
        currentItem: 'tab-Web',
        items: [
            'follower', 'following'
        ],
        text: '',
        followingList:[],
        followerList:[],
        methods: {
          checkfollow(flag){
              if(flag=="true")
                  return true;
              else
                  return false;
          }
        },
    };
  },
  created(){
    this.whenCreated(); 
  },
  methods: {
    whenCreated() {
      this.followingList = []
      this.followerList = []
      if (this.$route.params.info == 'follow') {
      this.currentItem = 'tab-following'
      }

     this.userId = this.$cookie.get("userId");
     axios
      .get(
        `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.userId+`&searchName=`+this.searchFollowing
      )
      .then((response) => {
        for (let i = 0; i <  response.data.length; i++) {
          let userImg = response.data[i].followingImg;
          let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
          this.followingList.push({
              followingId : response.data[i].followingId,
              followingNickname : response.data[i].followingNickname,
              followingImg: viewImg,
              followerFollowing :  "true"
          })
        }
      })
      .catch((error) => {
        console.log(error.response);
      });
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId+`&searchName=`+this.searchFollower
      )
      .then((response) => {
        for (let i = 0; i <  response.data.length; i++) {
          let userImg = response.data[i].followerImg;
          let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
          this.followerList.push({
              followerId : response.data[i].followerId,
              followerNickname : response.data[i].followerNickname,
              followerImg: viewImg,
              followerFollowing :  response.data[i].followerFollowing
          })
        }
      })
      .catch((error) => {
        console.log(error.response);
      });    
    },
    gotoProfile(user) {
      if (user.followerId) {
        let profileInfo = {
          userId: user.followerId,
          userImg : user.followerImg,
          followerFollowing: user.followerFollowing
        };
        this.$router.push('/user/profile?userId='+user.followerId
        +'&followerFollowing='+user.followerFollowing
        +'&userImg='+user.followerImg);
      } else { 
        let profileInfo = {
        userId: user.followingId,
        userImg : user.followingImg,
        followerFollowing: user.followerFollowing
        };
        this.$router.push('/user/profile?userId='+user.followingId
        +'&followerFollowing='+user.followerFollowing
        +'&userImg='+user.followingImg);
      }
    },
    onSearchFollower () {
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId+`&searchName=`+this.searchFollower
      )
      .then((response) => {
        this.followerList = []
        for (let i = 0; i <  response.data.length; i++) {
          let userImg = response.data[i].followerImg;
          let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
          this.followerList.push({
              followerId : response.data[i].followerId,
              followerNickname : response.data[i].followerNickname,
              followerImg: viewImg,
              followerFollowing :  response.data[i].followerFollowing
          })
        }
      })
      .catch((error) => {
        console.log(error.response);
      });
    },

    onSearchFollowing () {
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.userId+`&searchName=`+this.searchFollowing
      )
      .then((response) => {
        this.followingList = []
        for (let i = 0; i <  response.data.length; i++) {
            let userImg = response.data[i].followingImg;
            let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
            this.followingList.push({
                followingId : response.data[i].followingId,
                followingNickname : response.data[i].followingNickname,
                followingImg: viewImg,
                followerFollowing :  "true"
            })
          }
      })
      .catch((error) => {
        console.log(error.response);
      });
    },

    deleteFollowRequest(user,idx){
      this.$set(this.followerList[idx], 'followerFollowing', 'false');
        //언팔로우 요청
        if (user.followerId) {
          this.anotherId = user.followerId
        } else {
          this.anotherId = user.followingId
        }
        axios
          .delete(
            `${SERVER_URL}/userpage/deletefollowingRequest?anotherId=`+this.anotherId+`&userId=`+this.userId
          )
          .then((response) => {
            console.log('팔로우취소완료')
          })
          .catch((error) => {
            console.log(error.response);
          });
    },
    onFollow(user,idx) {
      this.$set(this.followerList[idx], 'followerFollowing', 'doing');
      axios
      .post(
        `${SERVER_URL}/userpage/insertfollowingRequest?followerId=`+user.followerId+`&userId=`+this.userId
      )
      .then((response) => {
        console.log('팔로우성공')
      })
      .catch((error) => {
          console.log(error.response);
      });
    },
    unFollow(user,idx,str) {
      if(str == 'followerlist'){
        this.$set(this.followerList[idx], 'followerFollowing', 'false');
      }
      else{
        this.$delete(this.followingList, idx);
      }
      //언팔로우 요청
      if (user.followerId) {
        this.anotherId = user.followerId
      } else {
        this.anotherId = user.followingId
      }
      axios
        .delete(
          `${SERVER_URL}/userpage/deletefollowing?anotherId=`+this.anotherId+`&userId=`+this.userId
        )
        .then((response) => {
          if(str == 'followerlist'){
            this.followingList = []
            axios
              .get(
                `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.userId+`&searchName=`+this.searchFollowing
              )
              .then((response) => {
                for (let i = 0; i <  response.data.length; i++) {
                  let userImg = response.data[i].followingImg;
                  let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
                  this.followingList.push({
                      followingId : response.data[i].followingId,
                      followingNickname : response.data[i].followingNickname,
                      followingImg: viewImg,
                      followerFollowing :  "true"
                  })
                }
              })
              .catch((error) => {
                console.log(error.response);
              });
          }
          else{
            this.followerList = []
            axios
            .get(
              `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId+`&searchName=`+this.searchFollower
            )
            .then((response) => {
              for (let i = 0; i <  response.data.length; i++) {
                let userImg = response.data[i].followerImg;
                let viewImg = SERVER_URL+"/img/user?imgname=" + userImg;
                this.followerList.push({
                    followerId : response.data[i].followerId,
                    followerNickname : response.data[i].followerNickname,
                    followerImg: viewImg,
                    followerFollowing :  response.data[i].followerFollowing
                })
              }
            })
            .catch((error) => {
              console.log(error.response);
            });  
        }
        })
        .catch((error) => {
          console.log(error.response);
        });
    }
    
  }
};
</script>

<style scoped>
  .follow-lsit {
    width: 100px
  }

  .search-bar {
    padding: 0 12px 0 12px;
    margin: 10px 18px 10px 18px;
  }


  .followListBtn {
    font-style: normal;
    font-size: 15px !important;
    font-family: 'Jua', sans-serif;  
  }

  .followlist{
    margin: 18px;
  }
  .v-list-item__content {
    width: 12px;
  }
  .text-css {
    color: rgba(0,0,0,.6);
    text-align: center;
  }
  .fa-user-plus {
    color: rgba(0,0,0,.6);
    margin: 70px 0 20px 140px;
    align-items: center;
  }
</style>