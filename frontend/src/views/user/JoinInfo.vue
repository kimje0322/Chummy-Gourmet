<template>
  <div id="personal">
    <h2>개인정보 입력 </h2>
    <hr>
    <h3 class="userInfo">성별</h3>
      <span class="selectInfo">남성</span>  
      <span class="selectInfo per">여성</span> 

    <h3 class="userInfo">나이</h3>
      <span class="selectInfo">10대</span>  
      <span class="selectInfo">20대</span>
      <span class="selectInfo per">30대</span>
      <br><br>
      <span class="selectInfo">40대</span>
      <span class="selectInfo">50대</span>  
      <span class="selectInfo">60 +</span>

    <h3 class="userInfo">· 선호음식</h3>
      <span class="selectInfoFood">
        <!-- <img src="../../assets/images/food_han.png" alt="Food Image">   -->
        한식
      </span>  
      <span class="selectInfoFood per">
        <!-- <img src="../../assets/images/food_yang.png" alt="Food Image"> -->
        양식
      </span>
      <span class="selectInfoFood">
        <!-- <img src="../../assets/images/food_jung.png" alt="Food Image"> -->
        중식
      </span>
      <br><br>
      <span class="selectInfoFood">
        <!-- <img src="../../assets/images/food_il.png" alt="Food Image"> -->
        일식
      </span>
      <span class="selectInfoFood">
        <!-- <img src="../../assets/images/food_bun.png" alt="Food Image"> -->
        분식
      </span>  
      <span class="selectInfoFood">
        <!-- <img src="../../assets/images/food_bu.png" alt="Food Image"> -->
        뷔페
      </span>

    <h3 class="userInfo">성향</h3>
      <span class="selectInfo per">낙천적</span>
      <span class="selectInfo">부정적</span>  
      <span class="selectInfo">내향적</span>
      <br><br>
      <span class="selectInfo">외향적</span>
      <span class="selectInfo per">충동적</span>  
      <span class="selectInfo">사교적</span>
      <br><br>
      <span class="selectInfo">대담함</span>  
      <span class="selectInfo">성실함</span>
      <span class="selectInfo per">냉정함</span>
      <br><br>
      <span class="selectInfo per">온화함</span>
      <span class="selectInfo">신중함</span>  
      <span class="selectInfo">게으름</span>
    
    <h3 class="userInfo">관심사</h3>
      <span class="selectInfo per">게임</span>
      <span class="selectInfo">패션</span>  
      <span class="selectInfo">대학교</span>
      <br><br>
      <span class="selectInfo">재테크</span>
      <span class="selectInfo per">건강</span>  
      <span class="selectInfo">취업</span>
      <br><br>
      <span class="selectInfo">군대</span>  
      <span class="selectInfo">인테리어</span>
      <span class="selectInfo per">여행</span>
      <br><br>
      <span class="selectInfo">일러스트</span>  
      <span class="selectInfo">이직</span>
      <span class="selectInfo per">IT</span>

    <v-btn color="#616161" class="next-btn"><h4>제출</h4></v-btn> 
  </div>
</template>

<script>
export default {
  name: 'JoinInfo'
}
</script>

<style scoped>
  h2 {
    text-align: center;
  }
  #personal {
    margin: 0 0 0 22px;
    padding: 0;
  }

  .userInfo {
    margin: 18px 0 10px 0;
  }

  .selectInfo {
    padding: 8px 25px;
    border: solid gray;
    border-radius: 15px;
    border-width: 1.5px;
    margin: 5px 5px 30px 0;
  }
  .per {
    background-color: #E0E0E0;
  }

  .selectInfoFood {
    padding: 8px 18px;
    border: solid gray;
    border-radius: 15px;
    border-width: 1.5px;
    margin: 5px 5px 30px 0;
  }

  img {
  max-width: 8.5%;
  height: auto;
  border-radius: 100px; 
}

  .next-btn {
    margin: 20px 0;
    height: 200px;  
    width: 98%;
}

  h4 {
    color: white;
  }
</style>
