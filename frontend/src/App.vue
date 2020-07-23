<template>
  <div id="app">
    <v-app>
      <v-main>
        <v-card class="overflow-hidden mx-auto" height="667" max-width="375">
          <!-- <v-app-bar color="deep-blue accent-4" dense dark>
            <v-toolbar-title style="margin:auto;">돈독한 미식가</v-toolbar-title>
          </v-app-bar> -->
          <v-bottom-navigation
            v-if="!$route.meta.navbar"
            scroll-target="#scroll-area-2"
            hide-on-scroll
            scroll-threshold="500"
            absolute
            color="white"
            horizontal
          >
            <v-btn text color="deep-purple accent-4">
              <router-link to="/map">
                <v-icon>fas fa-search</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/party">
                <v-icon>fas fa-plus</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/feed/main">
              <v-icon>fas fa-list</v-icon>
              </router-link>
            </v-btn>

            <v-btn text color="deep-purple accent-4">
              <router-link to="/user/info">
                <v-icon>fas fa-user</v-icon>
              </router-link>
            </v-btn>
          </v-bottom-navigation>

          <v-sheet id="scroll-area-1" class="overflow-y-auto" max-height="600">
            <v-container style="height: 100%;">
              <router-view></router-view>
            </v-container>
          </v-sheet>
        </v-card>
      </v-main>
    </v-app>
  </div>
</template>
<script>
import "./components/css/style.scss";
export default {
  name: "app"
};
</script>
