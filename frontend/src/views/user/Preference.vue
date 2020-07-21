
<template>
  <div class="user join wrapC components-page">
    <div class="header">
      <router-link to="/user/join" class="header-back-btn">◀</router-link>
    </div>
    <h2>개인정보 입력</h2>
    <div class="form-wrap">
        <div class="wrapB">
          <p>성별</p>
          <SmallButton text="남성" />
          <SmallButton text="여성" />
        </div>
        <div class="wrapB">
          <p>나이</p>
          <SmallButton text="10대" />
          <SmallButton text="20대" />
          <SmallButton text="30대" />
          <SmallButton text="40대" />
          <SmallButton text="50대" />
          <SmallButton text="60+" />
        </div>
        <div class="wrapB">
          <p>선호 음식</p>
          <SmallButton text="한식" />
          <SmallButton text="양식" />
          <SmallButton text="중식" />
          <SmallButton text="일식" />
          <SmallButton text="etc1" />
          <SmallButton text="etc2" />
        </div>
     
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>

    <span @click="termPopup=true">약관보기</span>
    <router-link to="/user/pref" class="btn-bottom">다음</router-link>
  </div>
</template>

<script>
import SmallButton from "@/components/common/ButtonSmall";

export default {
  name : "components",
  components : {
      SmallButton,
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      isTerm: false,
      isLoading: false,
      error: {
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

};
</script>


