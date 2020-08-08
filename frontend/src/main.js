import Vue from 'vue'
import App from './App.vue'
// import VueRouter from 'vue-router';
import router from './routes'
import store from './vuex/store'
import vuetify from '@/plugins/vuetify'

import VueCookie from 'vue-cookie'

import VueSimpleAlert from "vue-simple-alert";
Vue.config.productionTip = false
window.Kakao.init("6192aa4fccde619ea3ac09491e57abd0");
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