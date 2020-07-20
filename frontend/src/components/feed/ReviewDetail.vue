<template>
<div style="padding-top:5px">
 <div class="feed-item" style="padding-top:5px">
    <div class="top">
     <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
     <div class="user-info">
        <div class="user-name">
          <button>김승범</button>
        </div>
        <p class="date">9시간 전</p>
      </div>
      <div class="content">
        <p>이 집은 맛이 좋습니다.</p>
      </div>
    </div>
 </div>
 
 <div style="padding-left:10%;">
 <div class="feed-item" style="padding-top:5px">
    <div class="top">
     <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
     <div class="user-info">
        <div class="user-name">
          <button>김지은</button>
        </div>
      </div>
      <div class="content">
        <p>일주일에 5번 먹기 가능.</p>
      </div>
    </div>
 </div>
 <div class="feed-item" style="padding-top:5px">
    <div class="top">
     <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
     <div class="user-info">
        <div class="user-name">
          <button>박현영</button>
        </div>
      </div>
      <div class="content">
        <p>배고프다</p>
      </div>
    </div>
 </div>
  <div class="feed-item" style="padding-top:5px">
    <div class="top">
     <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
     <div class="user-info">
        <div class="user-name">
          <button>조민기</button>
        </div>
      </div>
      <div class="content">
        <p>배고프다</p>
      </div>
    </div>
 </div>
  <div class="feed-item" style="padding-top:5px">
    <div class="top">
     <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
     <div class="user-info">
        <div class="user-name">
          <button>박세훈</button>
        </div>
      </div>
      <div class="content">
        <p>배고프다</p>
      </div>
    </div>
 </div>
 </div>
     
      
</div>
  
</template>

<script>
import defaultImage from "../../assets/images/detailtest.png";
import defaultProfile from "../../assets/images/profile_default.png";
export default {
  data: () => {
    return { defaultImage, defaultProfile };
  },
  methods:{
      goReviewDetail(){
          this.$router.push('/reviewDetail');
      }
  }
};
</script>
