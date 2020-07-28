import Vue from 'vue'
import App from './App.vue'
// import VueRouter from 'vue-router';
import router from './routes'
import store from './vuex/store'
import vuetify from '@/plugins/vuetify'

Vue.config.productionTip = false

// Vue.use(VueRouter)

// const router = new VueRouter({
//     routes,
// });

new Vue({
    vuetify,
    router,
    store,
    render: h => h(App),
}).$mount('#app');


