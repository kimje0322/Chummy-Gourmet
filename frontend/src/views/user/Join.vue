<template>
  <div>
    <v-toolbar dense>
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <v-icon @click="$router.go(-1)">
        mdi-arrow-left
      </v-icon>
      <v-spacer></v-spacer>
      <p class="my-auto text-center">회원가입</p>
      <v-spacer></v-spacer>
    </v-toolbar>

    <div class="entire">
      <v-form ref="form">
        <v-col class="pb-0">
              <v-text-field
                :rules="[() => !!name || '이름을 입력해주세요']"
                outlined hide-details="auto" 
                v-model="name" 
                label="이름"
                clearable
              ></v-text-field>
        </v-col>
        <v-col class="pb-0">
              <v-text-field
                :rules="[() => !!nickName || '닉네임을 입력해주세요']"
                :error-messages="error.nickName"
                :hint="hint.nickName"
                outlined hide-details="auto" 
                v-model="nickName"
                label="닉네임"
                clearable
                class="pa-0"
                @input="hint.nickName=''"
              >
                <template v-slot:append>
                  <v-btn ref="button1" @click="checkNicknameAndEmail" class="ml-2 px-0" color="blue-grey lighten-1" style="top:-9px;" height="40" width="30" tile>
                    <label class="my-0 white--text text-caption">중복확인</label>
                  </v-btn>
                </template>
              
              
              </v-text-field>
        </v-col>
        <v-col class="pb-0">
              <v-text-field
                :rules="[() => !!email || '이메일을 입력해주세요', rules.email]"
                :error-messages="error.email"
                :hint="hint.email"
                outlined hide-details="auto" 
                v-model="email"
                label="이메일"
                clearable
                @input="hint.email=''"
              >
                <template v-slot:append>
                  <v-btn @click="checkNicknameAndEmail" class="ml-2 px-0" color="blue-grey lighten-1" style="top:-9px;" height="40" width="30" tile>
                    <label class="my-0 white--text text-caption">중복확인</label>
                  </v-btn>
                </template>
              </v-text-field>
        </v-col>
        <v-col class="pb-0">
              <v-text-field
                :rules="[() => !!password || '패스워드를 입력해주세요', rules.password]"
                outlined hide-details="auto" 
                type="password"
                v-model="password"
                label="비밀번호"
                clearable
              ></v-text-field>
        </v-col>
        <v-col class="pb-0">
              <v-text-field
                :rules="[() => password == passwordConfirm || '패스워드가 다릅니다.']"
                outlined hide-details="auto" 
                type="password"
                v-model="passwordConfirm"
                label="비밀번호 확인"
                clearable
              ></v-text-field>
        </v-col>
        <v-col class="pb-0">
               <v-text-field
               :rules="[() => !!phone || '연락처를 입력해주세요']"
                outlined hide-details="auto" 
                v-model="phone"
                label="연락처"
                clearable
              ></v-text-field>
        </v-col>
        <v-col class="pb-4">
            <v-checkbox
              v-model="isTerm"
              :rules="[() => !!isTerm || '약관에 동의해주세요.']"
            >
            <template v-slot:label >
              <label class="text-body-2">
                [필수] 
                <a href="#" @click.stop.prevent="dialog = true">이용약관</a>,
                <a href="#" @click.stop.prevent="dialog = true">개인정보처리방침</a>,
                <a href="#" @click.stop.prevent="dialog = true">위치기반서비스약관</a>
                에 동의합니다.
              </label>
            </template>
            </v-checkbox>
        </v-col>
      </v-form>

    <v-dialog v-model="dialog" max-width="375"> 
      <v-card>
        <v-card-title class="h6 font-weight-bold mb-0">XX약관
            <v-spacer></v-spacer>
            <v-icon @click="dialog=false">mdi-close</v-icon>
        </v-card-title>
        <v-divider class="mt-0"></v-divider>
        <v-card-text v-for="n in 5" :key="n">
          약관이 들어갈 자리입니다.
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text @click="dialog=false"
          >닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-btn color="orange lighten-1" class="next-btn my-0 text-white" @click="checkFormAndSignUp">다음</v-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";



import * as EmailValidator from "email-validator";
import PV from "password-validator";
import UserApi from "../../api/UserApi";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";

export default {
  name: "Join",
  created() {
    this.component = this;
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      name: "",
      phone: "",
      isTerm: false,
      dialog : false,
      hint : {
        email : '',
        nickName: '',
      },
      error : {
        email : '',
        nickName: '',
      },

      // 유효성 검사를 위한 rule
      rules: {
        nickName : () => {
          if(this.error.nickName)
            return true;
          else 
            return false;
        },
        password : value =>{
          const pattern = /^[A-Za-z0-9+]{8,}$/; 
          var checkNum = value.search(/[0-9]/g);
          var checkEng = value.search(/[a-z]/ig);
          if(checkNum < 0 || checkEng < 0 || !pattern.test(value))
            return "영문,숫자 포함 8 자리이상이어야 합니다.";
          else
            return true;
        },
        email: value => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          return pattern.test(value) || '이메일 형식이 아닙니다.'
        },
      },
    };
  },
  methods: {
    checkNicknameAndEmail(){
       axios
        .get(`${SERVER_URL}/account/signup/valid?nickname=${this.nickName}&email=${this.email}`)
        .then(response => {
          // data : success / isExistEmail / isExistNickname
          var data = response.data.data;
          if(data == "success"){
            this.error.email = '';
            this.error.nickName = '';
            this.hint.nickName = "사용가능한 닉네임입니다."
            this.hint.email = "사용가능한 이메일입니다."
          }
          else if (data == "isExistEmail") {
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
    checkFormAndSignUp() {
      // 모든 폼들의 유효성 검사가 됐다면
      if(this.$refs.form.validate()){
        let userInfo = {
          email: this.email,
          password: this.password,
          nickName: this.nickName,
          name: this.name,
        };

        // 현재 페이지에서 입력받은 정보를 params에 넣어서 다음 페이지로 이동
        router.push({name : "JoinInfo", params : userInfo});
      }
    },
  }
};
</script>


<style scoped>
/* .entire {
  padding: 18px
} */
.my-auto {
  font-size: 20px;
}

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

/* #password{
  background-color: transparent !important;
  border: 1px solid black !important;
}
[type=checkbox]:checked + span:before, 
[type=radio]:checked + span:before {
  background: orange;
  border: 1px solid orange;
} */

</style>