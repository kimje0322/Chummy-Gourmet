<template>
  <div>
    <h2>비밀번호 찾기</h2>
    <hr>
     <div class="input-with-label">
        <input
          id="nickname"
          placeholder="이름을 입력하세요."
          type="text"
        />
      <label for="email">이름</label>
    </div>
    <div class="input-with-label">
        <input
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
        />
      <label for="email">이메일</label>
    </div>
    <div class="input-with-label">
        <input
          id="phone"
          placeholder="휴대폰 번호을 입력하세요."
          type="text"
          
        />
      <label for="email">휴대폰</label>
    </div>
      <br><br>
      <!-- <p>※ 회원가입시 입력한 정보를 입력해주세요</p> -->
    <v-btn color="info confirm-btn">확인</v-btn> 
    <v-btn color="#FFB74D">인증</v-btn> 
  </div>
</template>

<script>
export default {
  name: 'FindPw'
}
</script>

<style>
  h2 {
    text-align: center;
  } 
  .confirm-btn {
    margin: 20px 0;
    height: 200px;  
    width: 100%;
 }
  p {
    margin: 0 0 0 5px
  }
</style>