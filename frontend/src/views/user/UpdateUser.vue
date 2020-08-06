<template>
  <v-app>
    <v-toolbar-title >
      <v-toolbar dark>
        <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <p class="my-auto">Profile 수정</p>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-toolbar-title>
    
    <v-layout row>
      <v-flex xs4 order-md2 order-xs1>
      </v-flex>
      <v-flex xs4 order-md3 order-xs2>
        <v-avatar size="100" >
          <v-img
            v-if="viewImg" :src="viewImg">
          </v-img>
        </v-avatar>
        
      </v-flex>
      <v-flex xs4 order-md1 order-xs3>
      </v-flex>
    </v-layout>
    

    <v-layout row>
      <v-flex xs3 order-md2 order-xs1>
      </v-flex>
      <v-flex xs6 order-md3 order-xs2>
        <v-btn type="button" @click="onClickImageChange" >수정</v-btn>
        <input ref="imageInput" type="file" hidden @change="onChangeImage">
        <v-btn @click="onClickSubmit">확인</v-btn>
      </v-flex>
      <v-flex xs3 order-md1 order-xs3>
      </v-flex>
    </v-layout>
    
    <v-layout>
    <v-content>
      
      <v-text-field
        color="dark"
        v-model="userNickname"
        label="Nickname"
        outlined
        >
      </v-text-field >
      
      <v-text-field 
        color="dark"
        v-model="userPwd"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
        :type="show1 ? 'text' : 'password'"
        @click:append="show1 = !show1"
        label="Password" 
        outlined
        >
      </v-text-field>

      <v-text-field 
        color="dark"
        v-model="userComment" 
        label="Comment" 
        outlined>
      </v-text-field>

      <v-btn
        block
        @click="checkForm"
      >
        프로필 수정
      </v-btn>
    </v-content>
    </v-layout>
    <v-layout>
      <v-list>
        <v-list-item-title>프로필 정보</v-list-item-title>
        <v-list-item-content>
          <v-list-item-subtitle>성함</v-list-item-subtitle>
          <v-list-item-title>{{user.userName}}</v-list-item-title>
          <v-list-item-subtitle>이메일 주소</v-list-item-subtitle>
          <v-list-item-title>{{user.userEmail}}</v-list-item-title>
          <v-list-item-subtitle>전화번호</v-list-item-subtitle>
          <v-list-item-title>{{user.userPhone}}</v-list-item-title>
        </v-list-item-content>
      </v-list>
    </v-layout>
  </v-app>
</template>

<script>
import PV from "password-validator";
import axios from "axios";
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";


export default {
  data: () => {
    return {
      passwordSchema: new PV(),
      nickName: "",
      error: {
        nickName: true,
      },
      show1: false,
      user : {},
      userNickname : "",
      userPwd : "",
      userComment : "",
      userImg : "",
      userId : "",
      viewImg:""
    };
  },
  methods: {
    //이미지 변경버튼이 클릭됐을때
    //파일 입력 창을 띄운다
    onClickImageChange(){
      this.$refs.imageInput.click();
    },

    //이미지 보이는부분 변경
    onChangeImage(e){
      this.file = e.target.files[0];
      this.viewImg = URL.createObjectURL(this.file);
    },

    //이미지 입력 버튼을 클릭했을때
    //서버와 통신하여 이미지를 저장하고 url을 반환.
    onClickSubmit(){
      const file = new FormData();
      file.append('file',this.file);
      axios
      .post(`${SERVER_URL}/userpage/img`,file)

      .then((response) => {
        this.userImg = response.data;
        console.log(this.userImg);
        this.addImg();
      })

      .catch((error) => {
        console.log(error.response);
        alert("이미지 전송 실패");
      });
    },

    addImg(){
      this.userImgCpy = this.userImg;
      axios
      .get(`${SERVER_URL}/userpage/updateimg?user_img=${this.userImgCpy}&user_id=${this.userId}`
        )

      .then((response) => {
        alert("성공");
      })

      .catch((error) => {
        console.log(error.response);
        alert("데이터 전송 실패");
      });
    },

    checkpwd() {
      
    },
    checkForm() {
      this.error.nickName = false;
      this.updateUser();
    },
    updateUser (){
      if (this.userPwd.length > 0 && !this.passwordSchema.validate(this.userPwd))
        this.$alert("영문,숫자 포함 8 자리이상이어야 합니다.");
      else{
        axios
        .put(
          `${SERVER_URL}/userpage/updateuser?userId=${this.user.userId}&userNickname=${this.userNickname}&userPwd=${this.userPwd}&userComment=${this.userComment}`
        )
        .then((response) => {
          if(response.data.data == "isExistNickname"){
            this.$alert("사용중인 닉네임입니다. 다른 닉네임을 입력해주세요.");
              
          }
          else{
            // 정상 동작
            this.$alert("수정완료");
            this.$router.go(-1);
          }
        })
        .catch((error) => {
          console.log(error.response);
        });  
      }
    }
  },
  created(){
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
    axios
      .get(
        `${SERVER_URL}/userpage/getuser?userId=${this.$cookie.get("userId")}`
      )
      .then((response) => {
        console.log(response.data);
        this.user = response.data;
        this.userNickname = this.user.userNickname;
        this.userPwd = this.user.userPWd;
        this.userComment = this.user.userComment;
        this.userImg = this.user.userImg;
        this.userId = this.$cookie.get("userId");
        this.viewImg = SERVER_URL+"/img/user?imgname="+this.userImg;
        console.log("userImg : "+this.viewImg);
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
  };
</script>

<style scoped>
  .my-auto {
      font-family: 'Jua', sans-serif;
  }
  .fa-chevron-left {
    color: white; 
    margin-left: 7px;
  }
  
</style>