import Vue from 'vue'
import VueRouter from 'vue-router';

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinRule from './views/user/JoinRule.vue'
import JoinInfo from './views/user/JoinInfo.vue'
import FinishJoin from './views/user/FinishJoin.vue'
import Curator from './views/curator/curator.vue'
import Detail from './views/curator/detail.vue'
import ReviewDetail from './views/curator/reviewDetail.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import Party from './views/feed/Party.vue'
import FindPw from './views/user/FindPw.vue'
import FoundPw from './views/user/FoundPw.vue'
import UserInfo from './views/Main.vue'
import Pref from './views/user/Preference.vue'
import Map from './views/map/map.vue'
import MapParty from './views/map/party.vue'
import Home from './views/user/Home.vue'
import FollowList from './components/common/Followlist.vue'


//추가
import UpdateUser from './views/user/UpdateUser.vue'
import Profile from './views/user/Profile.vue'


Vue.use(VueRouter);
export default new VueRouter({
    routes: [{
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                navbar: true
            }
        },
        //추가
        {
            path: '/user/profile',
            name: 'Profile',
            component: Profile,
        },
        {
            path: '/user/updateUser',
            name: 'UpdateUser',
            component: UpdateUser,
        },
        {
            path: '/user/join',
            name: 'Join',
            component: Join
        },
        {
            path: '/user/info',
            name: 'UserInfo',
            component: UserInfo
        },
        {
            path: '/user/joinrule',
            name: 'Joinrule',
            component: JoinRule
        },
        {
            path: '/user/joininfo',
            name: 'JoinInfo',
            component: JoinInfo
        },
        {
            path: '/user/findPw',
            name: 'FindPw',
            component: FindPw
        },
        {
            path: '/user/foundPw',
            name: 'FoundPw',
            component: FoundPw
        },
        {
            path: '/user/finishjoin',
            name: 'FinshJoin',
            component: FinishJoin
        },
        {
            path: '/user/findPw',
            name: 'FindPw',
            component: FindPw
        },
        {
            path: '/user/foundPw',
            name: 'FoundPw',
            component: FoundPw
        },
        {
            path: '/user/pref',
            name: 'Pref',
            component: Pref
        },
        {
            path: '/map',
            name: 'Map',
            component: Map
        },
        {
            path: '/map/party',
            name: 'MapParty',
            component: MapParty
        },
        {
            path: '/feed/main',
            name: 'FeedMain',
            component: FeedMain
        },
        {
            path: '/components',
            name: 'Components',
            component: Components
        },
        {
            path: '/curator',
            name: 'Curator',
            component: Curator
        },
        {
            path: '/detail',
            name: 'Detail',
            component: Detail
        },
        {
            path: '/reviewDetail',
            name: 'ReviewDetail',
            component: ReviewDetail
        },
        {
            path: '/party',
            name: 'Party',
            component: Party,
            meta: {
                navbar: true
            }
        },
        {
            path: '/',
            name: 'Home',
            component: Home,
            meta: {
                navbar: true
            }
        },
        {
            path: '/followlist',
            name: 'FollowList',
            component: FollowList,
            meta: { transitionName: 'slide' },
        },

    ]
});