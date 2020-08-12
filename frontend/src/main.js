import Vue from 'vue'
import App from './App.vue'
// import VueRouter from 'vue-router';
import router from './routes'
import store from './vuex/store'
import vuetify from '@/plugins/vuetify'

import VueCookie from 'vue-cookie'

import VueChatScroll from 'vue-chat-scroll';
Vue.use(VueChatScroll);

import VueSimpleAlert from "vue-simple-alert";
Vue.config.productionTip = false
window.Kakao.init("6192aa4fccde619ea3ac09491e57abd0");

import firebase from 'firebase'

var config = { apiKey: "AIzaSyClT8BESKIzQIZuPN0Xw8gks4w6HOTIiLM",
    authDomain: "taste-ac33e.firebaseapp.com",
    databaseURL: "https://taste-ac33e.firebaseio.com",
    projectId: "taste-ac33e",
    storageBucket: "taste-ac33e.appspot.com",
    messagingSenderId: "706725717763",
    appId: "1:706725717763:web:7631b8082177166451eb36",
    measurementId: "G-P3SKG9RZF8"};
firebase.initializeApp(config);
firebase.firestore().settings({timestampsInSnapshots:true});

var db = firebase.firestore();

window.db = db;

// Vue.use(VueRouter)

Vue.use(VueCookie)
Vue.use(VueSimpleAlert)
// const router = new VueRouter({
//     routes,
// });

new Vue({
    VueSimpleAlert,
    vuetify,
    router,
    store,
    render: h => h(App),
}).$mount('#app');