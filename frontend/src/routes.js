import Vue from 'vue'
import VueRouter from 'vue-router';

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinRule1 from './views/user/JoinRule1.vue'
import JoinInfo from './views/user/JoinInfo.vue'
import FinishJoin from './views/user/FinishJoin.vue'
import Curator from './views/curator/curator.vue'
import Detail from './views/curator/detail.vue'
import ReviewDetail from './views/curator/reviewDetail.vue'
import Components from './views/Components.vue'
import Party from './views/feed/Party.vue'
import FindPw from './views/user/FindPw.vue'
import FoundPw from './views/user/FoundPw.vue'
import UserInfo from './views/Main.vue'
import Pref from './views/user/Preference.vue'
import Map from './views/map/map.vue'
import CreateMeetup from './views/map/createMeetup.vue'
import DetailMeetup from './views/map/detailMeetup.vue'
import Home from './views/user/Home.vue'
import FollowList from './components/common/Followlist.vue'
import NewsFeed from './views/feed/newsfeed.vue'
import FollowRequestList from './components/common/FollowRequestList.vue'
import Feedcomment from './views/feed/comment.vue'
import UpdateUser from './views/user/UpdateUser.vue'
import Profile from './views/user/Profile.vue'
import PostDetail from './views/user/PostDetail.vue'
import SearchUser from './components/common/SearchUser.vue'
//추가
import PostUpdate from './views/user/PostUpdate.vue'

import UpdateUserInfo from './views/user/UpdateUserInfo.vue'

//채팅
import ChatRoom from './views/chat/chatRoom.vue'
import Chat from './views/chat/chat.vue'

//피드 게시글 업로드
import AddFeed from './views/feed/addFeed.vue'

// 리뷰 작성
import AddReview from './views/review/addReview.vue'

//밋업 참가
import MeetUpRequestList from './components/common/MeetUpRequestList.vue'

//알람페이지
import AlarmPage from './views/user/AlarmPage.vue'

Vue.use(VueRouter);
export default new VueRouter({
    routes: [
        //알람페이지
        {
            path: '/user/alarm',
            name: 'AlaramPage',
            component: AlarmPage,
            // meta: {
            //     navbar: true
            // }
        },
        //밋업 참가
        {
            path: '/meetup/add',
            name: 'FollowRequestList',
            component: MeetUpRequestList,
            // meta: {
            //     navbar: true
            // }
        },
        //리뷰 작성
        {
            path: '/review/add',
            name: 'AddReview',
            component: AddReview,
            // meta: {
            //     navbar: true
            // }
        },
        //피드 게시글 업로드
        {
            path: '/feed/add',
            name: 'AddFeed',
            component: AddFeed,
            // meta: {
            //     navbar: true
            // }
        },
        //채팅방
        {
            path: '/chat',
            name: 'Chat',
            component: Chat,
            props: true
        },
        //채팅목록
        {
            path: '/chatroom',
            name: 'ChatRoom',
            component: ChatRoom,
            props: true
        },
        {

            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                navbar: true
            }
        },
        //승범 추가
        {
            path: '/PostUpdate',
            name: 'PostUpdate',
            component: PostUpdate,
        },
        {
            path: '/SearchUser',
            name: 'SearchUser',
            component: SearchUser,
        },
        {
            path: '/user/PostDetail',
            name: 'PostDetail',
            component: PostDetail,
        },
        {
            path: '/user/FollowRequestList',
            name: 'FollowRequestList',
            component: FollowRequestList,
        },
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
            path: '/user/updateUserInfo',
            name: 'UpdateUserInfo',
            component: UpdateUserInfo,
        },
        // 여기까지
        {
            path: '/user/join',
            name: 'Join',
            component: Join,
            meta: {
                navbar: true
            }
        },
        {
            path: '/user/info',
            name: 'UserInfo',
            component: UserInfo,
        },
        {
            path: '/user/joinrule',
            name: 'Joinrule1',
            component: JoinRule1,
            meta: {
                navbar: true
            }
        },
        {
            path: '/user/joininfo',
            name: 'JoinInfo',
            component: JoinInfo,
            meta: {
                navbar: true
            }
        },
        {
            path: '/user/finishjoin',
            name: 'FinshJoin',
            component: FinishJoin
        },
        {
            path: '/user/findPw',
            name: 'FindPw',
            component: FindPw,
            meta: {
                navbar: true
            }
        },
        {
            path: '/user/foundPw',
            name: 'FoundPw',
            component: FoundPw
        },
        // {
        //     path: '/user/pref',
        //     name: 'Pref',
        //     component: Pref
        // },
        {
            path: '/map',
            name: 'Map',
            component: Map
        },
        {
            path: '/map/createMeetup',
            name: 'CreateMeetup',
            component: CreateMeetup,
            meta: {
                navbar: true
            }
        },
        {
            path: '/map/detailMeetup',
            name: 'DetailMeetup',
            component: DetailMeetup
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
        {
            path: '/newsfeed',
            name: 'NewsFeed',
            component: NewsFeed,
        },
        {
            path: '/newsfeed/comment',
            name: 'Comment',
            component: Feedcomment,
        }

    ]
});