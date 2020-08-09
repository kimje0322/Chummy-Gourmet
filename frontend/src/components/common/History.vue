<template>
  <v-layout class="entireClass">
      <v-row>
      <v-col v-for="item in items" :key="item.title" class="d-flex child-flex" cols="4">
          <v-card flat tile class="d-flex">
              <v-img :src="item.img" aspect-ratio="1" class="grey lighten-2">
              <template v-slot:placeholder>
              <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
              >
                  <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
              </v-row>
              </template>
              </v-img>
          </v-card>
      </v-col>
      </v-row>
  </v-layout>
</template>


<script>

import axios from "axios";
import router from "@/routes";


const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
    props:['userId'],

    data () {
      return {
        items:[]
      }
    },

    created(){
      console.log("생성");
      console.log(this.userId);

       axios
        .get(`${SERVER_URL}/userpage/getRest?userid=${this.userId}`)
        .then((response) => {
          // console.log(response);
          this.items = response.data;
          console.log(this.items);
        })
        .catch((error) => {
          console.log(error.response);
        });
    }

  }
</script>

<style>
  .entireClass{
    padding: 20px;
  }
</style>