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
          v-model="email"
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
import * as EmailValidator from "email-validator";

export default {
  name: 'FindPw',
  watch: {
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
    }
  },
  data () {
    return {
      email: "",
      error: {
        email: false,
      },
    }
  }
};
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