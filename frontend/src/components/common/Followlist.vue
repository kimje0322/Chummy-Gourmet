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
         <!-- 검색바 -->
          <v-row>
            <v-col class="search-bar" cols="12" sm="12">
              <v-text-field 
                @keyup.up="onSearch"
                v-model="searchName"
                label="검색"
                solo
              ></v-text-field>
              <!-- axios요청 보낼때 searchName -->
              <!-- <p>{{ searchName }}</p> -->
            </v-col>
          </v-row>
          <!-- 팔로워 -->
          <v-list nav dense  v-if="item=='follower'">
           

                <v-list-item-group v-model="followerList">
                    <v-list-item v-for="(user, i) in followerList" :key="i">
                        <!-- <v-list-item-avatar>
                        <v-img :src="item.avatar"></v-img>
                        </v-list-item-avatar> -->

                        <v-list-item-content>
                        <v-list-item-title v-text="user.followerName"></v-list-item-title>
                        <!-- 이름 표기 -->
                        <!-- <v-list-item-title v-text="user.followingNinkname"></v-list-item-title> -->
                        </v-list-item-content>

                        <v-btn v-if="user.followerFollowing !== 'true'">
                            팔로잉
                        </v-btn>
                        <v-btn disabled v-else >
                            팔로잉
                        </v-btn>

                    </v-list-item>
                </v-list-item-group>
            </v-list>
          
            <!-- 팔로잉 -->
            <v-list nav dense v-else>
                <v-list-item-group v-model="followingList">
                    <v-list-item v-for="(user, i) in followingList" :key="i">
                        <!-- <v-list-item-avatar>
                        <v-img :src="item.avatar"></v-img>
                        </v-list-item-avatar> -->

                        <v-list-item-content>
                        
                        <v-list-item-title v-text="user.followingName"></v-list-item-title>
                        </v-list-item-content>

                        <v-btn disabled>
                            팔로잉
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
// import Footer from "../components/common/Footer";

import axios from "axios";
const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

export default {
  name: "components",
  components: {
     Top,
    // Footer,
  },
 
  data: () => {
    return {
        searchName: "",
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
     this.userId = this.$cookie.get("userId");
     axios
      .get(
        `${SERVER_URL}/userpage/getfollowinglist?userId=`+this.userId
      )
      .then((response) => {
        this.followingList = response.data;
        console.log(this.followingList);
        console.log('팔로잉 리스트')
      })
      .catch((error) => {
        console.log(error.response);
      });
      axios
      .get(
        `${SERVER_URL}/userpage/getfollowerlist?userId=`+this.userId
      )
      .then((response) => {
        this.followerList = response.data;
        console.log(this.followerList);
        console.log('팔로워 리스트')
        // for (var i=0; i<this.followerList.length; i++) {
        //   if (this.followerList[i].followerFollowing)

        // }
      })
      .catch((error) => {
        console.log(error.response);
      });     
  },
  methods: {
    onSearch () {
      // console.log(value)
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
    margin: 10px 18px 0 18px;
  }

  .followListBtn {
    font-style: normal;
    font-size: 15px !important;
    font-family: 'Jua', sans-serif;
    
  }
</style>