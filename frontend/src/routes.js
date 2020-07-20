

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Curator from './views/curator/curator.vue'
import Detail from './views/curator/detail.vue'
import ReviewDetail from './views/curator/reviewDetail.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import Party from './views/feed/Party.vue'


export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/curator',
        name : 'Curator',
        component : Curator
    },
    {
        path : '/detail',
        name : 'Detail',
        component : Detail
    },
    {
        path : '/reviewDetail',
        name : 'ReviewDetail',
        component : ReviewDetail
    },
    {
        path : '/party',
        name : 'Party',
        component : Party
    }
]
