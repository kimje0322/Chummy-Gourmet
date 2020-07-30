<template>
  <v-card elevation="24" max-width="444" class="mx-auto">
    <v-system-bar lights-out></v-system-bar>

    <v-carousel
      :continuous="false"
      :cycle="cycle"
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

      <!-- <v-list-item>
        <v-list-item-avatar>
          <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title v-text="!show ? '밋업상세조회' : '직이노' ">파티정보보기</v-list-item-title>
          <v-list-item-subtitle v-text="temp"></v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
          <v-switch v-model="cycle" label="Cycle Slides" inset></v-switch>
        </v-list-item-action>
      </v-list-item> -->

      <v-expansion-panels accordion multiple hover>
        <v-expansion-panel>
          <v-expansion-panel-header>밋업상세조회</v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-list>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-map-marker-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>맛찬들</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.location" v-text="restaurant.location"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle v-if="restaurant.location" v-text="restaurant.location"></v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-calendar-month-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>2020년 7월 30일 20:00</v-list-item-title>
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-crown-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>조민기</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-account-group-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>김승범 박세훈</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-information-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-expansion-panel-content>
        </v-expansion-panel>
      
        <v-expansion-panel>
          <v-expansion-panel-header>리뷰상세조회</v-expansion-panel-header>
          <v-expansion-panel-content>
            <v-list>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-map-marker-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>맛찬들</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.location" v-text="restaurant.location"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle v-if="restaurant.location" v-text="restaurant.location"></v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-calendar-month-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>2020년 7월 30일 20:00</v-list-item-title>
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-crown-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>조민기</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-account-group-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>김승범 박세훈</v-list-item-title>
                  <!-- <v-list-item-title v-if="restaurant.telphone" v-text="restaurant.telphone"></v-list-item-title> -->
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="indigo">mdi-information-outline</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <v-list-item-title>안녕안녕안녕안녕안녕안녕</v-list-item-title>
                  <!-- <v-list-item-subtitle>Mobile</v-list-item-subtitle> -->
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-expansion-panel-content>
          
          
          
          <v-expansion-panel-content>
            <v-list>
              <template v-for="(review, index) in temp">
                <v-divider :key="index"></v-divider>
                <v-subheader v-if="review.header" :key="review.header" v-text="review.header"></v-subheader>
                <v-divider v-else-if="review.divider" :key="index" :inset="review.inset"></v-divider>
                <v-list-item v-else :key="review.title" @click="test(review)">
                  <v-list-item-avatar>
                    <v-img src="https://cdn.vuetifyjs.com/images/lists/1.jpg"></v-img>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <!-- <v-list-item-title v-html="review.title"></v-list-item-title> -->
                    <!-- <v-list-item-subtitle v-html="review.subtitle"></v-list-item-subtitle> -->
                    <v-list-item-title>
                      {{temp}}
                      <span
                        class="text-caption grey--text text--lighten-1"
                        style="float:right;"
                        v-text="review.date"
                      ></span>
                    </v-list-item-title>
                    <v-list-item-subtitle>
                      <span class="text--primary" v-for="(member, index) in temp" :key="index">
                        <span class="blue--text" v-if="index == 0">{{member}}</span>
                        <span v-else>{{member}}</span>
                      </span>
                      <!-- &mdash; {{review.content}} -->
                    </v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-list>

          </v-expansion-panel-content>

        </v-expansion-panel>
      </v-expansion-panels>

      

      
  </v-card>
</template>

<script>
import ReviewDetail from "../../components/feed/ReviewDetail.vue";

export default {
  data() {
    return {
      show: false,
      temp: "temp",
    };
  },
  created() {
    console.log(this.$route.params);
  },
};
</script>