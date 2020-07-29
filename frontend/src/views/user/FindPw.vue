<template>
  <div>
    <h2>비밀번호 찾기</h2>
    <hr />

    <!-- 이름 -->
    <div class="input-with-label">
      <input v-model="name" id="name" placeholder="이름을 입력하세요." type="text" />
      <label for="name">이름</label>
      <div class="error-text" v-if="error.name">{{error.name}}</div>
    </div>

    <!-- 이메일 -->
    <div class="input-with-label">
      <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
      <label for="email">이메일</label>
      <div class="error-text" v-if="error.email">{{error.email}}</div>
    </div>

    <!-- 휴대폰 -->
    <!-- <div class="input-with-label cell-label">
      <input v-model="phone" id="phone" placeholder="휴대폰 번호을 입력하세요." type="text" />
      <label for="email">휴대폰</label>
      <div class="error-text" v-if="error.phone">{{error.phone}}</div>
    </div> -->
    <br />
    <br />
    <div>※ 회원가입시 입력한 정보를 입력해주세요</div>

    <!-- 버튼 -->
    <v-btn @click="checkFormAndFindpw" color="warning find-btn">확인</v-btn>
    <!-- <v-btn class="cell-auth" color="error">인증</v-btn> -->
  </div>
</template>

<script>
import axios from "axios";
import * as EmailValidator from "email-validator";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

export default {
  name: "FindPw",
  watch: {
    name: function () {
      if (this.name.length < 1) this.error.name = "이름 입력해주세요.";
      else this.error.name = false;
    },
    email: function (v) {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
    },
    // phone: function (v) {
    //   if (this.phone.length > 0 && isNaN(this.phone))
    //     this.error.phone = "올바른 휴대폰 번호를 입력해주세요.";
    //   else this.error.phone = false;
    // },
  },
  methods: {
    checkFormAndFindpw() {
      // 전체 폼 체크(형식)
      this.checkForm();
      // 
      if (this.isValidForm()){
        this.onFindPw();
        // this.$router.push("/user/foundpw")
      }
    },
    checkForm() {
      if (this.name.length < 1) this.error.name = "이름 입력해주세요.";
      else this.error.name = false;
    
      if(this.email.length < 1)
        this.error.email = "이메일을 입력해주세요.";
      else if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
      

      // if(this.phone.length < 1)
      //   this.error.phone = "휴대폰 번호를 입력해주세요.";
      // else if (this.phone.length > 0 && isNaN(this.phone))
      //   this.error.phone = "올바른 휴대폰 번호를 입력해주세요.";
      // else this.error.phone = false;
    },
    isValidForm() {
      for (let key in this.error) {
        if (this.error[key]) return false;
      }
      return true;
    },
    onFindPw() {
      let userData = {
        userEmail : this.email,
        userName : this.name
      };
      console.log(userData);
      axios.post(`${SERVER_URL}/account/senduserpwd`, userData)
        .then(res => {
          // isNotExistName / isNotExistEmail / success  
          console.log(res)
          var data = res.data.data;
          
          if(data == 'isNotExistName'){
            console.log('isNotExistName');
            this.error.name = '가입되지 않는 이름입니다.'
          }
          if (data == 'isNotExistEmail'){
            console.log('isNotExistEmail');
            this.error.email = '가입되지 않는 이메일입니다.'
          }
          else if (data == 'success'){
            console.log('success');
            this.$router.push("/user/foundpw")
          }

        })
        .catch(error => {
          console.log(error.res)
        })
    },
  },
  data() {
    return {
      name: "",
      email: "",
      phone: "",
      error: {
        name: false,
        email: false,
        // phone: false,
      },
    }
  },
};
</script>

<style>
h2 {
  text-align: center;
}
.find-btn {
  margin: 20px 0;
  height: 200px;
  width: 100%;
}
p {
  margin: 0 0 0 5px;
}
.cell-label {
  position: relative;
}
.cell-auth {
  position: absolute;
  left: 277px;
  bottom: 168px;
}
</style>