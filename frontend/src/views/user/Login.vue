<template>
  <v-app id="app">
    <!-- <div class="img-info"> -->
    <!-- <img src="../../assets/images/logo_ex.png" alt="log" width="100"> -->
    <!-- </div> -->
    <!-- <router-view></router-view> -->

    <div class="user" id="login">
      <!-- <img src="../../assets/images/background.jpg" width="500"> -->
      <div class="wrapC">
        <!-- <h3>
          <router-link to="Home" class="btn-back">  </router-link>
        </h3>-->
        <!-- <br>
        <br>
        <br>
        <br>
        <br>
        <br>-->
        <br />
        <br />
        <h2 class="text-white">로그인</h2>
        <hr />
        <!-- <p class="text-center" style="color:white;">Login</p> -->
        <div class="input-with-label">
          <input
            v-model="email"
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
            @keyup.enter="Login"
            id="email"
            placeholder="이메일을 입력하세요."
            type="text"
            class="border-white"
          />
          <label for="email">이메일</label>
          <div class="error-text" v-if="error.email">{{error.email}}</div>
        </div>

        <div class="input-with-label">
          <input
            v-model="password"
            type="password"
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
            id="password"
            @keyup.enter="Login"
            placeholder="비밀번호를 입력하세요."
            class="border-white"
          />
          <label for="password">비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>

        <v-btn width="100%" @click="onLogin">로그인</v-btn>

        <div>
          <router-link to="/user/findpw">
            <p color="white">비밀번호 찾기</p>
          </router-link>
        </div>

        <div class="sns-login">
          <div class="text">
            <p>SNS 간편 로그인</p>
            <div class="bar"></div>
          </div>
          <kakaoLogin :component="component" />
          <GoogleLogin :component="component" />
        </div>
        <div class="add-option">
          <!-- <div class="text">
        <p>혹시</p>
        <div class="bar"></div>
          </div>-->
          <br />
          <br />
          <br />
          <br />
          <br />
          <router-link to="/user/findpw"><p>비밀번호 찾기</p></router-link>
          <!-- <div class="row">
            <p class="d-flex mx-auto">
              <router-link to="/user/join">
                <img src="../../assets/images/join_logo.png" alt="join_log" width="15" />
                회원가입 |
              </router-link>
              <router-link to="/user/findpw">
                <img src="../../assets/images/find_logo.png" alt="find_logo" width="15" />
                계정찾기
              </router-link>
            </p>
          </div>-->
          <!-- <div class="wrap">
        <p>아직 회원이 아니신가요?</p>
        <router-link to="/user/join" class="btn-join"><strong>가입하기!</strong></router-link>
          </div>-->
        </div>
      </div>
    </div>
  </v-app>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/user/snsLogin/Google.vue";
import UserApi from "../../api/UserApi";
import axios from "axios";

const SERVER_URL = "http://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  components: {
    KakaoLogin,
    GoogleLogin,
  },
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
  watch: {
    password: function (v) {
      this.checkForm();
    },
    email: function (v) {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
  
// 쿠키를 사용한 로그인 부분
// 로그인이 성공했을 때 쿠키에 토큰와 userId를 저장한다.
    onLogin() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email)) {
        // console.log('이메일 확인')
        alert("이메일을 확인해주세요");
        return;
      }

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        alert("비밀번호를 확인해주세요");
        return;
      }

      axios
        .get(
          `${SERVER_URL}/account/login?email=${this.email}&password=${this.password}`
        )

        .then((response) => {
          console.log("로그인페이지");
          console.log(response.data);

          this.$cookie.set("accesstoken", response.data, 1);
          this.$cookie.set("userId", response.data.object.userId, 1);

          this.$router.push("/map");
        })

        .catch((error) => {
          console.log(error.response);
          alert("로그인 실패");
        });
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false,
      },
      isSubmit: false,
      component: this,
    };
  },
};
</script>


<style scoped>
.login-entire {
  padding: 0;
}

.img-info {
  /* position: absolute; */
  margin: 15px auto;
}

.bibun {
  text-align: center;
}

label {
  color: black;
}

.container {
  height: 50%;
  /* padding: 0px !important;  */
}
::placeholder {
  color: black;
}
</style>