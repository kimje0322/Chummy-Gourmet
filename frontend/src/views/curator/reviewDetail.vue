<template>
  <v-card elevation="24" max-width="444" class="mx-auto">
    <v-system-bar lights-out></v-system-bar>

    <v-carousel :continuous="false" show-arrows hide-delimiter-background height="300">
      <!-- <v-carousel-item v-for="index of 5" :key="index"> -->
        <v-img
          src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
          class="white--text align-end"
          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
          height="100%"
        ></v-img>
      <!-- </v-carousel-item> -->
    </v-carousel>
    <v-expansion-panels accordion multiple hover>
      <v-expansion-panel>
        <v-expansion-panel-header @click="show =! show">
          <span v-if="!show">밋업상세조회</span>
          <span v-else>{{meetup.title}}</span>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-list>
            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-map-marker-outline</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="meetup.location"></v-list-item-title>
                <!-- <v-list-item-title v-if="restaurant.location" v-text="restaurant.location"></v-list-item-title> -->
                <!-- <v-list-item-subtitle v-if="restaurant.location" v-text="restaurant.location"></v-list-item-subtitle> -->
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-calendar-month-outline</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="meetup.date"></v-list-item-title>
                <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-crown-outline</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="meetup.master"></v-list-item-title>
                <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-account-group-outline</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-for="(member) in members" :key="member">@{{member}}</v-list-item-title>
                <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
              </v-list-item-content>
            </v-list-item>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-information-outline</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                {{meetup.content}}
                <!-- <v-list-item-title v-text="meetup.content"></v-list-item-title> -->
                <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-expansion-panel-content>
      </v-expansion-panel>

      <v-expansion-panel>
        <v-expansion-panel-header>리뷰상세조회</v-expansion-panel-header>

        <v-expansion-panel-content>
          <v-spacer></v-spacer>
          <v-card class="mx-auto" max-width="400">
            <v-card-title>
              <span class="title font-weight-bold">{{review.title}}</span>
            </v-card-title>

            <v-card-text class="headline font-weight-bold" v-text="review.content"></v-card-text>

            <v-card-actions>
              <v-list-item class="grow">
                <v-list-item-avatar color="grey darken-3">
                  <v-img
                    class="elevation-6"
                    src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                  ></v-img>
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title v-text="meetup.master"></v-list-item-title>
                  <v-list-item-subtitle>master</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-card-actions>
          </v-card>

          <v-list>
            <template v-for="(comment, i) of comments">
              <v-divider :key="comment.id"></v-divider>
              <v-list-item :key="comment.writer">
                <v-list-item-avatar>
                  <v-img :src="imgs[i]" />
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-title>
                    {{comment.content}}
                  </v-list-item-title>
                  <v-list-item-subtitle>
                    {{comment.date}}
                  </v-list-item-subtitle>
                </v-list-item-content>
                
              </v-list-item>
            </template>
          </v-list>

          <v-divider></v-divider>
          <v-text-field
            v-model="comment"
            placeholder="댓글을 입력하세요.." solo
            rounded dense clearable append-outer-icon="mdi-arrow-up-thick"
            @keyup.enter="writeComment"
            @click:append-outer="writeComment"
          ></v-text-field>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  data() {
    return {
      show: false,
      review: "",
      meetup: "",
      members: [],
      comments: [],
      comment : '',
      imgs: [
        "https://cdn.vuetifyjs.com/images/lists/1.jpg",
        "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        "https://cdn.vuetifyjs.com/images/lists/3.jpg",
        "https://cdn.vuetifyjs.com/images/lists/4.jpg",
        "https://cdn.vuetifyjs.com/images/lists/5.jpg",
      ],
    };
  },
  created() {
    this.review = this.$route.params.review;
    this.getComments();
  },
  methods : {
    getComments(){
    axios
      .get(`${SERVER_URL}/review/comment/${this.review.id}`)
      .then((response) => {
        console.log(response.data);
        this.comments = response.data.object;
      })
      .catch((error) => {
        console.log(error.response);
      });
    },
    writeComment() {
      var newComment = {
        writer : 70,
        content : this.comment,
        reviewId : this.review.id,
      };
      axios
        .put(`${SERVER_URL}/review/comment`, newComment)
        .then((response) => {
          console.log(response.data);
          this.getComments();
          this.comment = "";
        })
        .catch((error) => {
          console.log(error.response);
        });

    },

  },
};
</script>