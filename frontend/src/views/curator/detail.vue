<template>
  <v-card elevation="24" max-width="444" class="mx-auto">
    <v-system-bar lights-out></v-system-bar>

    <v-carousel
      :continuous="false"
      show-arrows
      hide-delimiter-background
      height="300"
    >
      <v-carousel-item v-for="i of 5" :key="i">
        <v-img
          src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
          class="white--text align-end"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="100%"
        ></v-img>
      </v-carousel-item>
    </v-carousel>

    <v-list>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title v-text="restaurant.name"></v-list-item-title>
          <v-list-item-subtitle v-text="restaurant.category"></v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
        </v-list-item-action>
      </v-list-item>

      <v-expand-transition>
        <div v-show="show">
          <v-divider></v-divider>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-map-marker</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-if="restaurant.location" v-text="restaurant.location"></v-list-item-title>
              <v-list-item-subtitle  v-if="restaurant.location" v-text="restaurant.location"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-phone</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title  v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-clock-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>10:00 - 22:00</v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon color="indigo">mdi-parking</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>주차가능</v-list-item-title>
              <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
            </v-list-item-content>
          </v-list-item>
        </div>
      </v-expand-transition>

      <v-list three-line>
        <template v-for="(review, index) in reviews">
          <v-divider :key="index"></v-divider>
          <v-subheader v-if="review.header" :key="review.header" v-text="review.header"></v-subheader>
          <v-divider v-else-if="review.divider" :key="index" :inset="review.inset"></v-divider>
          <v-list-item v-else :key="review.title" @click="moveReviewDetail(review)">

            <v-list-item-avatar>
              <v-img src="https://cdn.vuetifyjs.com/images/lists/1.jpg"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>
                {{review.title}}
              </v-list-item-title>
              <v-list-item-subtitle>
                <span class="text--primary" v-for="(member, index) in members[index]" :key="index">
                  <span class="blue--text" v-if="index == 0">@{{member}} </span>
                  <span v-else>@{{member}} </span>
                </span>
                <span class="text-caption grey--text text--lighten-1" style="float:right;" v-text="review.date"></span>
                <!-- &mdash; {{review.content}} -->
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-list>
  </v-card>
</template>

<script>
import axios from "axios";
import router from "@/routes";


const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "http://localhost:8080";

export default {
  data() {
    return {
      cycle: false,
      show: false,
      restaurant: this.$route.params,
      reviews: [],
      members : [],
    };
  },
  created() {
    axios
        .get(`${SERVER_URL}/review/search?id=${this.restaurant.id}`)
        .then((response) => {
          console.log(response.data);
          this.reviews = response.data.review;
          this.members = response.data.member;
          
        })
        .catch((error) => {
          console.log(error.response);
        });
  },
  methods : {
    moveReviewDetail(review, member) {
      router.push({name : "ReviewDetail", params : { review : review, members : member }});
    }
  }
};
</script>