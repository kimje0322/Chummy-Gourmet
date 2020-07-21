<template>
  <v-layout>
    <v-toolbar dark>
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <v-spacer></v-spacer>
      <v-spacer></v-spacer>
      돈독한 미식가
      <v-spacer></v-spacer>
      <v-btn @click.stop="drawer = !drawer" depressed>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>
      </v-btn>
    </v-toolbar>
    
    <v-navigation-drawer v-model="drawer" temporary absolute right>
      <v-system-bar></v-system-bar>
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_doKnSS8nyn0SYPV-J4cQgaE7uHtbsKlB9A&usqp=CAU"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title class="title">Sony</v-list-item-title>
            <v-list-item-subtitle>hm_son7@instagram.com</v-list-item-subtitle>
          </v-list-item-content>

          <v-list-item-action>
            <v-icon>mdi-menu-down</v-icon>
          </v-list-item-action>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list nav dense>
        <v-list-item-group v-model="items" color="primary">
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title v-text="item.text"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>

    </v-navigation-drawer>
  </v-layout>
</template>

<script>
  export default {
    data () {
      return {
        drawer: null,
        items: [
        { text: 'My list', icon: 'mdi-folder' },
        { text: 'Meet Up', icon: 'mdi-account-multiple' },
        { text: 'favorite', icon: 'mdi-star' },
        { text: 'Recent', icon: 'mdi-history' },
      ],
      }
    }
  }
</script>