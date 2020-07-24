
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="entire">
    <h2>회원 가입</h2>
    <hr>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="name" id="name" placeholder="이름을 입력하세요." type="text" />
        <label for="name">이름</label>
      </div>
      
      <div class="input-with-label">
        <input v-model="nickName" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
        <label for="nickname">닉네임</label>
       <div class="error-text" v-if="error.nickName">{{error.nickName}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
        <label for="email">이메일</label>
      <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="password" v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}" id="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
      </div>
    </div>
    <div class="input-with-label cell-label">
      <input id="cellphone" placeholder="휴대폰 번호를 입력하세요." type="text" />
      <v-btn class="cell-auth" color="error">인증</v-btn>
      <label for="cellphone">
        휴대폰
      </label>
      
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>

    <router-link to="/user/joinrule"><span @click="termPopup=true" class="seeRules">약관보기</span></router-link>
  
    <router-link to="/user/joininfo" @click.native="onSignup"><v-btn color="warning" class="next-btn">다음</v-btn></router-link>
   
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3b302.p.ssafy.io:8080'

import * as EmailValidator from "email-validator";
import PV from "password-validator";
import UserApi from "../../api/UserApi"

export default {
  name: 'Join',
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      passwordConfirm: "",
      nickName: "",
      name: "",
      isTerm: false,
      isLoading: false,
      error: {
        name: false,
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    
    nickName: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
    passwordConfirm: function(v) {
      this.checkForm();
    },
  },
  methods: {
    onSignup () {
      const name = this.name;
      const password = this.password;
      const email = this.email;
      const nickName = this.nickName;
      axios.post(`${SERVER_URL}/account/signup/`, {name, password, email, nickName})
        .then(response => {
          console.log(response)
        })
    },
    checkForm() {
      if (this.nickName.length == 3 )
        this.error.nickName = "사용중인 닉네임입니다. 다른 닉네임을 입력해주세요.";
      else this.error.nickName = false;

      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;  

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      if (this.passwordConfirm != this.password)
        this.error.passwordConfirm = "비밀번호가 다릅니다.";
      else this.error.passwordConfirm = false;
  }
    
}
};

</script>


<style scoped>
  /* h1 {
    margin: 0 0 10px 0;
  } */
  h2 {
    text-align: center;
  }
 .seeRules {
   float: right;
 }
 .next-btn {
    margin: 20px 0 0 0;
    height: 200px;  
    width: 100%;
 }
 .cell-label {
    position: relative;
 }
 
 .cell-auth {
   position: absolute;
   right: 11px;
   bottom: 8px;
 }
  
  .input-with-label {
    margin: 0 0 15px 0;
  }

</style>