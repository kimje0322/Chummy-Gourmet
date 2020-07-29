<template>
  <v-layout>
    <v-toolbar dark>
      <v-list-item-avatar>
        <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
      </v-list-item-avatar>
      {{proptoTopsub.userName}}
      <v-spacer></v-spacer>
        <v-btn depressed>{{proptoTopsub.followingCount}} <br>팔로잉</v-btn>
      <v-spacer></v-spacer>
        <v-btn depressed>{{proptoTopsub.followerCount}}<br>팔로워</v-btn>
      <v-spacer></v-spacer>
    </v-toolbar>
  </v-layout>
</template>
<script>
export default {
  props:{
    proptoTopsub: {
      type : Object
    },
  }
}
</script>