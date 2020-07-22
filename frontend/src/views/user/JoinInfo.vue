<template>
  <div id="joininfo">
    <h2 class="title">추가정보 입력 </h2>
    <hr>    
    <!-- 성별 -->
    <h3 class="userInfo">성별</h3>
      <ul class="select-gender">
        <li>
            <input type="radio" id="man" name="gender" />
            <label for="man" class="gender">남성</label>
        </li>
        <li>
            <input type="radio" id="woman" name="gender" />
            <label for="woman" class="gender">여성</label>
        </li>
      </ul><br><br>  
    <!-- 나이 -->
    <h3 class="userInfo">나이</h3>
      <ul class="select-gender">
        <li>
            <input type="radio" id="10" name="age" />
            <label for="10" class="age">10대</label>
        </li>
        <li>
            <input type="radio" id="20" name="age" />
            <label for="20" class="age">20대</label>
        </li>
        <li>
            <input type="radio" id="30" name="age" />
            <label for="30" class="age">30대</label>
        </li>
        <li>
            <input type="radio" id="40" name="age" />
            <label for="40" class="age">40대</label>
        </li>
        <li>
            <input type="radio" id="50" name="age" />
            <label for="50" class="age">50대</label>
        </li>
        <li>
            <input type="radio" id="60" name="age" />
            <label for="60" class="age">60 +</label>
        </li>
      </ul><br><br><br><br>
    <!-- 선호 음식 -->
    <h3 class="userInfo">선호음식</h3>
      <ul class="select-many">
        <li>
          <input type="checkbox" id="han" name="food" />
          <label for="han" class="age">
            <img src="../../assets/images/food_han.png" alt="Food Image">  
            한식
          </label>
        </li>
        <li>
          <input type="checkbox" id="yang" name="food" />
          <label for="yang" class="age">
            <img src="../../assets/images/food_yang.png" alt="Food Image">  
            양식
          </label>
        </li>
        <li>
          <input type="checkbox" id="jung" name="food" />
          <label for="jung" class="age">
            <img src="../../assets/images/food_jung.png" alt="Food Image">  
            중식
          </label>
        </li>
        <li>
          <input type="checkbox" id="il" name="food" />
          <label for="il" class="age">
            <img src="../../assets/images/food_il.png" alt="Food Image">  
            일식
          </label>
        </li>
        <li>
          <input type="checkbox" id="bun" name="food" />
          <label for="bun" class="age">
            <img src="../../assets/images/food_bun.png" alt="Food Image">  
            분식
          </label>
        </li>
        <li >
          <input type="checkbox" id="bu" name="food" />
          <label for="bu" class="age">
            <img src="../../assets/images/food_bu.png" alt="Food Image">  
            뷔페
          </label>
        </li>
      </ul><br><br><br><br>

    <!-- personality -->
    <h3 class="userInfo">성격</h3>
      <ul class="select-many">
        <li>
          <input type="checkbox" id="p1" name="personality" />
          <label for="p1" class="age">
            <!-- <h5 style="padding-left:15px; padding-right:15px"> -->
            낙천적
            <!-- </h5> -->
          </label>
        </li>
        <li>
          <input type="checkbox" id="p2" name="personality" />
          <label for="p2" class="age">
            부정적
          </label>
        </li>
        <li>
          <input type="checkbox" id="p3" name="personality" />
          <label for="p3" class="age">
            내향적
          </label>
        </li>
        <li>
          <input type="checkbox" id="p4" name="personality" />
          <label for="p4" class="age">
            외향적
          </label>
        </li>
        <li>
          <input type="checkbox" id="p5" name="personality" />
          <label for="p5" class="age">
            충동적
          </label>
        </li>
        <li>
          <input type="checkbox" id="p6" name="personality" />
          <label for="p6" class="age">
            사교적
          </label>
        </li>
        <li>
          <input type="checkbox" id="p7" name="personality" />
          <label for="p7" class="age">
            대담함
          </label>
        </li>
        <li>
          <input type="checkbox" id="p8" name="personality" />
          <label for="p8" class="age">
            성실함
          </label>
        </li>
        <li>
          <input type="checkbox" id="p9" name="personality" />
          <label for="p9" class="age">
            냉정함
          </label>
        </li>
        <li>
          <input type="checkbox" id="p10" name="personality" />
          <label for="p10" class="age">
            온화함
          </label>
        </li>
        <li>
          <input type="checkbox" id="p11" name="personality" />
          <label for="p11" class="age">
            신중함
          </label>
        </li>
        <li>
          <input type="checkbox" id="p12" name="personality" />
          <label for="p12" class="age">
            게으름
          </label>
        </li>        
      </ul>
      
      <!-- <span class="selectInfo">낙천적</span> -->
      <br><br><br><br><br><br><br><br>
     
    
    <!-- interest -->
    <h3 class="userInfo">관심사</h3>
      <ul class="select-many-interest">
        <li>
          <input type="checkbox" id="i1" name="interest" />
          <label for="i1" class="age">
            아이돌
          </label>
        </li>
        <li>
          <input type="checkbox" id="i2" name="interest" />
          <label for="i2" class="age">
            콘서트
          </label>
        </li>
        <li>
          <input type="checkbox" id="i3" name="interest" />
          <label for="i3" class="age">
            취업
          </label>
        </li>
        <li>
          <input type="checkbox" id="i4" name="interest" />
          <label for="i4" class="age">
            게임
          </label>
        </li>
        <li>
          <input type="checkbox" id="i5" name="interest" />
          <label for="i5" class="age">
            패션
          </label>
        </li>
        <li>
          <input type="checkbox" id="i6" name="interest" />
          <label for="i6" class="age">
            다이어트
          </label>
        </li>
        <li>
          <input type="checkbox" id="i7" name="interest" />
          <label for="i7" class="age">
            재테크
          </label>
        </li>
        <li>
          <input type="checkbox" id="i8" name="interest" />
          <label for="i8" class="age">
            사업
          </label>
        </li>
        <li>
          <input type="checkbox" id="i9" name="interest" />
          <label for="i9" class="age">
            경제
          </label>
        </li>
        <li>
          <input type="checkbox" id="i10" name="interest" />
          <label for="i10" class="age">
            건강 
          </label>
        </li>
        <li>
          <input type="checkbox" id="i11" name="interest" />
          <label for="i11" class="age">
            스포츠
          </label>
        </li>
        <li>
          <input type="checkbox" id="i12" name="interest" />
          <label for="i12" class="age">
            요리
          </label>
        </li>
        <li>
          <input type="checkbox" id="i13" name="interest" />
          <label for="i13" class="age">
            군대 
          </label>
        </li>
        <li>
          <input type="checkbox" id="i14" name="interest" />
          <label for="i14" class="age">
            인테리어
          </label>
        </li>
        <li>
          <input type="checkbox" id="i15" name="interest" />
          <label for="i15" class="age">
            여행
          </label>
        </li>
        <li>
          <input type="checkbox" id="i16" name="interest" />
          <label for="i16" class="age">
            일러스트
          </label>
        </li>
        <li>
          <input type="checkbox" id="i17" name="interest" />
          <label for="i17" class="age">
            동물
          </label>
        </li>
        <li>
          <input type="checkbox" id="i18" name="interest" />
          <label for="i18" class="age">
            IT
          </label>
        </li>
      </ul>
      <!-- <span class="selectInfo">게임</span> -->
    <router-link to="/user/finishjoin"><v-btn color="#B388FF" class="next-btn"><h4 class="submitbtn">제출</h4></v-btn></router-link> 
  </div>
</template>

<script>
export default {
  name: 'JoinInfo',
  data () {
    return {
      row1: null,
      row2: null,
    }
  }
}

</script>

<style scoped>
  .title{
    text-align: center;
  }

  .userInfo {
    margin: 18px 0 10px 25px;
  }

  .selectInfo {
    padding: 8px 25px;
    border: solid gray;
    border-radius: 15px;
    border-width: 1.5px;
    margin: 5px 5px 30px 0;
  }

  img {
  max-width: 37%;
  margin-right: 5px;
  height: auto;
  border-radius: 100px; 
}

  .next-btn {
    margin: 20px 20px;
    height: 200px; 
    width: 80%;
    text-align: center;
}

  .submitbtn{
    color: white;
  }

  .gender, .age {
    text-align: center;
    justify-content: center;
  }

  .select-gender, .select-many {
     list-style-type:none;
     margin:2px 0 0 0;
     padding:0x;
  }

   .select-many-interest {
     list-style-type:none;
     margin:2px 0 0 0;
     padding:0x;
  }

  .select-gender li, .select-many li{
      float:left;
      margin:0 5px 0 0;
      width:85px;
      height:42px;
      position:relative;
  }

  .select-many-interest li{
      float:left;
      margin:0 5px 0 0;
      width:85px;
      height:42px;
      position:relative;
  }


  .select-gender label, .select-gender input {
      display:block;
      position:absolute;
      top:0;
      left:0;
      right:0;
      bottom:0;
  }


  .select-gender input[type="radio"],
  .select-many input[type="checkbox"] {
      opacity:0.011;
      z-index:100;
  }

  .select-gender input[type="radio"]:checked + label

   {
      background:#D1C4E9;
  }

  .select-many input[type="checkbox"]:checked + label
   {
      background:#D1C4E9;
  }

  .select-many-interest input[type="checkbox"]:checked + label
   {
      background:#D1C4E9;
  }

  .select-gender label {
      padding:5px;
      border:1.5px solid gray; 
      border-radius: 15px;
      cursor:pointer;
      z-index:90;
  }

  .select-many label {
      width: 100%;
      padding: 5px 12px 5px 12px;
      border:1.5px solid gray; 
      border-radius: 15px;
      cursor:pointer;
      z-index:90;
  }

  .select-many-interest label {
      width: 100%;
      padding: 5px 12px 5px 12px;
      border:1.5px solid gray; 
      border-radius: 15px;
      cursor:pointer;
      z-index:90;
  }

  .select-gender label:hover {
      background:#DDD;
  }


</style>
