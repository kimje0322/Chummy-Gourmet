<template>
  <div><br><br><br>
    <h2>회원님의 임시 비밀번호가 전송되었습니다.</h2>
    
    <div class="go-to-login">
    <router-link to="/login">
      <v-btn color="warning">  
      <v-icon>mdi-login</v-icon>
      로그인하러 가기
      </v-btn>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "FoundPw",
}
</script>

<style scoped>
  .go-to-login {
    margin-top: 80px;
    text-align: center;
  }
</style>