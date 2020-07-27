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
        </h3> -->
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <p class="text-center" style="color:white;">Login</p>
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

        <v-btn color="white" width="100%">로그인</v-btn>

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
          </div> -->
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

export default {
  components: {
    KakaoLogin,
    GoogleLogin
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
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
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
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin() {
      if (this.isSubmit) {
        let { email, password } = this;
        let data = {
          email,
          password
        };

        //요청 후에는 버튼 비활성화
        this.isSubmit = false;

        UserApi.requestLogin(
          data,
          res => {
            //통신을 통해 전달받은 값 콘솔에 출력
            //console.log(res);

            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;

            this.$router.push("/main");
          },
          error => {
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
          }
        );
      }
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this
    };
  }
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
  color:white;
}

.container {
  height: 50%;
}
::placeholder {
  color: white;
}
</style>