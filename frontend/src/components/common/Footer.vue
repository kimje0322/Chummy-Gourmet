<template>
     <v-bottom-navigation
      scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >
      <v-btn text color="deep-purple accent-4">
        <v-icon>fas fa-search</v-icon>
      </v-btn>
      <v-btn text color="deep-purple accent-4">
        <v-icon>fas fa-plus</v-icon>
      </v-btn>

      <v-btn text color="deep-purple accent-4">
        <v-icon>fas fa-list</v-icon>
      </v-btn>

      <v-btn text color="deep-purple accent-4">
        <v-icon>fas fa-user</v-icon>
      </v-btn>
    </v-bottom-navigation>
</template>