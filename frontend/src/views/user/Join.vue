
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="entire" @keyup.enter="test">
    <h2>회원 가입</h2>
    <hr />
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="name" id="name" placeholder="이름을 입력하세요." type="text" />
        <label for="name">이름</label>
        <div class="error-text" v-if="error.name">{{error.name}}</div>
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
        <input
          v-model="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          :type="passwordType"
          placeholder="비밀번호를 입력하세요."
        />
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
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>
    <div class="input-with-label cell-label">
      <input v-model="phone" id="cellphone" placeholder="휴대폰 번호를 입력하세요." type="text" />
      <label for="cellphone">휴대폰</label>
      <div class="error-text" v-if="error.phone">{{error.phone}}</div>
      <v-btn @click="onSignup" class="cell-auth" color="error">인증</v-btn>
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>
    <div class="error-text" style="color:red;" v-if="error.term">{{error.term}}</div>

    <router-link to="/user/joinrule">
      <span @click="termPopup=true" class="seeRules">약관보기</span>
    </router-link>

    <!-- <router-link to="/user/joininfo"> -->
    <v-btn color="warning" class="next-btn" @click="checkFormAndSignUp">다음</v-btn>
    <!-- @click="$emit('signup', signupData)" type="submit" -->
    <!-- </router-link> -->
  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";



import * as EmailValidator from "email-validator";
import PV from "password-validator";
import UserApi from "../../api/UserApi";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";

export default {
  name: "Join",
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
      phone: "",
      isTerm: false,
      isLoading: false,
      error: {
        name: false,
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false,
        phone: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
      // signupData: {
      //   nickName: "",
      //   email: ""
      // }
    };
  },
  watch: {
    name: function() {
      if (this.name.length < 1) this.error.name = "이름 입력해주세요.";
      else this.error.name = false;
    },
    password: function(v) {
      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
      this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
    },
    nickName: function(v) {
      if (this.nickName.length < 1)
        this.error.nickName = "닉네임을 입력해주세요.";
      else this.error.nickName = false;
    },
    email: function(v) {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
    },
    passwordConfirm: function(v) {
      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.password
      ) {
        this.error.passwordConfirm = "비밀번호가 다릅니다.";
      } else this.error.passwordConfirm = false;
    },
    isTerm: function() {
      if (this.isTerm) this.error.term = false;
    },
    phone: function (v) {
      if (this.phone.length > 0 && isNaN(this.phone))
        this.error.phone = "올바른 휴대폰 번호를 입력해주세요.";
      else this.error.phone = false;
    },
    isTerm: function() {
      if (this.isTerm) this.error.term = false;
    }
  },
  methods: {
    checkFormAndSignUp() {
      // 전체적으로 폼들 체크하고,
      this.checkForm();

      // 약관 동의 체크하고, 
      if (!this.isTerm) this.error.term = "약관에 동의해주세요.";

      // 다 됐으면 중복검사
      if (this.isValidForm())
        this.onSignup();
      
    },
    onSignup() {
      axios
        .get(
          `${SERVER_URL}/account/signup/valid?nickname=${this.nickName}&email=${this.email}`
        )
        .then(response => {
          // data : success / isExistEmail / isExistNickname
          var data = response.data.data;

          // 이메일과 닉네임이 중복이 아니라면,
          if (data == "success") {
            let userInfo = {
              email: this.email,
              password: this.password,
              nickName: this.nickName,
              name: this.name,
            };

            // 현재 페이지에서 입력받은 정보를 params에 넣어서 다음 페이지로 이동
            router.push({name : "JoinInfo", params : userInfo});
          } else if (data == "isExistEmail") {
            this.error.email =
              "사용중인 이메일입니다. 다른 이메일을 입력해주세요.";
          } else if (data == "isExistNickname") {
            this.error.nickName =
              "사용중인 닉네임입니다. 다른 닉네임을 입력해주세요.";
          }
        })
        .catch(error => {
          console.log(error.response);
        });
    },
    checkForm() {
      if (this.name.length < 1) this.error.name = "이름을 입력해주세요.";
      else this.error.name = false;

      if (this.nickName.length < 1)
        this.error.nickName = "닉네임을 입력해주세요.";
      else this.error.nickName = false;

      if(this.email.length < 1)
        this.error.email = "이메일을 입력해주세요.";
      else if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if(this.password.length < 1)
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.password
      ) {
        this.error.passwordConfirm = "비밀번호가 다릅니다.";
      } else this.error.passwordConfirm = false;
    },

    // 입력 정보가 다 유효하면 true, 아니면 false
    isValidForm() {
      for (let key in this.error) {
        if (this.error[key]) return false;
      }
      return true;
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