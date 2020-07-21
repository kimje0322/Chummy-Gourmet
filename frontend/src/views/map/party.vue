<template>
  <v-layout row>
    <v-flex xs12 sm6 offset-sm3>
      <v-card>
        <v-img
          src="https://img.siksinhot.com/place/1485274468095571.jpg?w=307&h=300&c=Y"
          height="250px"
        />

        <v-card-title>
          <div>
            <span class="headline">고기땡기노 </span>
            <span class="subtitle-1">맛찬들</span>
          </div>
        </v-card-title>
        <v-card-text>
          <div class="subtitle-1">
            <v-icon>mdi-map-marker</v-icon>  대전 유성구 온천북로13번길 35
          </div>
          <br />
          <div class="subtitle-1">
            <v-icon>mdi-calendar-clock</v-icon>  2020-07-18 17:00
          </div>
          <br />
          <div class="subtitle-1">
            <v-icon>mdi-account-multiple</v-icon>  2명 / 4명
          </div>
          <br />
        </v-card-text>
          <div align="center">
            <v-btn color="primary">
              참석
            </v-btn>
            <v-btn href="/#/map" color="error">
              닫기
            </v-btn>
          </div>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn> -->
        </v-card-actions>

        <!-- <v-slide-y-transition>
          <v-card-text
            v-show="show"
          >I'm a thing. But, like most politicians, he promised more than he could deliver. You won't have time for sleeping, soldier, not with all the bed making you'll be doing. Then we'll go with that data file! Hey, you add a one and two zeros to that or we walk! You're going to do his laundry? I've got to find a way to escape.</v-card-text>
        </v-slide-y-transition> -->
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  data: () => ({
    show: false,
    items: [
      { header: "Today" },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
        title: "Brunch this weekend?",
        subtitle:
          "<span class='text--primary'>Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?"
      },
      { divider: true, inset: true },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
        title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
        subtitle:
          "<span class='text--primary'>to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend."
      },
      { divider: true, inset: true },
      {
        avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
        title: "Oui oui",
        subtitle:
          "<span class='text--primary'>Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?"
      }
    ]
  })
};
</script>