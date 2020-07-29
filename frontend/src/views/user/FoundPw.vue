<template>
  <div><br><br><br>
    <h2>회원님의 임시 비밀번호가 전송되었습니다.</h2>
    
    
    <router-link to="/login">
      <h3>
      <v-icon>mdi-login</v-icon>
      로그인하러 가기
      </h3></router-link>
  </div>
</template>

<script>
export default {
  name: "FoundPw",
}
</script>

<style scoped>
  h3 {
    color: black;
    margin-top: 100px;
    text-align: center;
  }
</style>