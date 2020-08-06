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

          <v-list-item-group class="followlist" v-model="followerList">
            <v-list-item v-for="(user, i) in followerList" :key="i">
                <!-- <v-list-item-avatar>
                <v-img :src="item.avatar"></v-img>
                </v-list-item-avatar> -->

                <v-list-item-content>
                <v-list-item-title @click="gotoProfile(user)" v-text="user.followerNickname"></v-list-item-title>
                <!-- 이름 표기 -->
                <!-- <v-list-item-title v-text="user.followingName"></v-list-item-title> -->
                </v-list-item-content>

                <v-btn color="blue" @click="onFollow(user)" v-if="user.followerFollowing !== 'true'">
                    팔로잉
                </v-btn>
                <v-btn @click="unFollow(user)" v-else>
                    언팔로우
                </v-btn>

            </v-list-item>
          </v-list-item-group>
        </v-list>
        
        <!-- 팔로잉 -->
        <v-list nav dense v-else>
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
            <v-list-item-group class="followlist" v-model="followingList">
                <v-list-item v-for="(user, i) in followingList" :key="i">
                    <v-list-item-content>
                    <v-list-item-title @click="gotoProfile(user)" v-text="user.followingNickname"></v-list-item-title>
                    </v-list-item-content>
                    <v-btn @click="unFollow(user)">
                      언팔로우
                    </v-btn>
                </v-list-item>
            </v-list-item-group>
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
      if (this.$route.params.info == 'follow') {
      this.currentItem = 'tab-following'
    }

     this.userId = this.$cookie.get("userId");
     axios
      .get(
        `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.userId+`&searchName=`+this.searchFollowing
      )
      .then((response) => {
        this.followingList = response.data;
      })
      .catch((error) => {
        console.log(error.response);
      });
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId+`&searchName=`+this.searchFollower
      )
      .then((response) => {
        this.followerList = response.data;
        // console.log(this.followerList);
        // for (var i=0; i<this.followerList.length; i++) {
        //   if (this.followerList[i].followerFollowing)
        // }
      })
      .catch((error) => {
        console.log(error.response);
      });    
    },
    gotoProfile(user) {
      // console.log(user.followingId)
      if (user.followerId) {
        let profileInfo = {
        userId: user.followerId,
        followerFollowing: user.followerFollowing
        };
        // console.log('user.followerId,')
        this.$router.push({name :'Profile', params: profileInfo});
      } else { 
        let profileInfo = {
        userId: user.followingId,
        followerFollowing: "true",
        };
        this.$router.push({name :'Profile', params: profileInfo});
      }
      // axios
      // .get(
      //   `${SERVER_URL}/userpage/getuser?userId=`+user.userId
      // )
    },
    onSearchFollower () {
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId+`&searchName=`+this.searchFollower
      )
      .then((response) => {
        this.followerList = []
        this.followerList = response.data;
        // console.log(this.followerList);
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
        this.followingList = response.data;
        // console.log(this.followingList);
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
    onFollow(user) {
      axios
      .post(
        `${SERVER_URL}/userpage/insertfollowingRequest?followerId=`+user.followerId+`&userId=`+this.userId
      )
      .then((response) => {
        console.log('팔로우')
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
    unFollow(user) {
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
        if(response.data.data == "success"){
          this.whenCreated();
        }
      })
      .catch((error) => {
        console.log(error.response);
      });
      this.whenCreated();
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

</style>