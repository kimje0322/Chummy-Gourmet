<template>
   <div>
       <v-toolbar dark>
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
      <p class="my-auto">알람</p>
      <v-spacer></v-spacer>
    </v-toolbar>
      <div v-for="(list, i) in alarm" :key="i"> 
        <div>
            <br><br>
            <input style="width : 80%; border : 1px solid" readonly :value="list.message"/>
            <v-btn height="48px" width="8%" style="float : right;" class="btn btn-primary" @click="onClick(list)">확인</v-btn>
            <br>
            {{ list.time | moment("from","now")}}
        </div>
      </div>

      <div v-if="alarm.length == 0" class="aligncss"> 
        <i class="far fa-file-image fa-5x"></i>
        <h3 class="mt-5">새로운 알림이 없습니다.</h3>
      </div>

  </div>
</template>

<script>
import moment from 'moment';

import axios from "axios";
const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
  data(){
    return {
      alarm : []
    }
  },
  created(){
    this.view('chat');
    this.view('like');
    this.view('comment');
    this.view('follow');
    this.view('meetup');
  },

  methods:{
    //type별로 알림목록을 가져온다.
    view(type){
      window.db.collection('alarm').doc(type).collection('messages').where('to', '==', this.$cookie.get('userId')).where('confirm','==',false).get()
                 .then(snapshot=>{
                  //없을경우
                      if(snapshot.empty){
                        // console.log("없다");
                        return;
                        }

                    //있을경우
                      snapshot.forEach(doc=>{
                        var data = doc.data();
                         data.rid = doc.id;
                         data.type = type;
                         console.log('data',data)
                         this.alarm.push(data);
                       })
                  })
                  .catch(err => {
                     console.log(err);
                  });
    },

    //클릭했을 때 페이지 변경해주는 함수
    onClick(list){
      if(list.type=='chat'){
        this.$router.push({ path: '/chatroom' });
      }
      else if(list.type=='like' || list.type =='comment'){
        if(list.type == 'like'){
          window.db.collection('alarm').doc('like').collection('messages').doc(list.rid).set({
            confirm : true
          },{merge : true})
        }
        else{
          window.db.collection('alarm').doc('comment').collection('messages').doc(list.rid).set({
            confirm : true
          },{merge : true})
        }

          axios
            .get(`${SERVER_URL}/userpage/getpost?postId=`+list.postid)
            .then((response) => {
                var post = response.data.data
                var comment = response.data.comment;
                console.log(post.post_content);
                console.log(comment);
               this.$router.push({name :'PostDetail', query: {post_content: post.post_content
                ,post_id : post.post_id, post_img_url : post.post_img_url,
                post_like : post.post_like, post_userid : post.post_userid, user_img:post.user_img,
                user_nickname : post.user_nickname, comment:comment , userId : this.userId}});
            })  
      }
      else if(list.type =='follow'){

         //팔로우 요청 알림을 모두 읽음으로 변경.
                      window.db.collection('alarm').doc('follow').collection('messages').where('to','==',this.$cookie.get('userId')).get()
                      .then(snapshot =>{
                          if(snapshot.empty) {
                              console.log('팔로우 요청이 없다.')
                          }

                          snapshot.forEach(doc =>{
                             window.db.collection('alarm').doc('follow').collection('messages').doc(doc.id).set({
                                    confirm : true
                                },{merge:true}).catch(err => {
                                    console.log(err);
                                });
                          })
                      })

        this.$router.push({ path: '/user/FollowRequestList' });
      }
      else if(list.type == 'meetup'){
         //팔로우 요청 알림을 모두 읽음으로 변경.
                      window.db.collection('alarm').doc('meetup').collection('messages').where('to','==',this.$cookie.get('userId')).get()
                      .then(snapshot =>{
                          if(snapshot.empty) {
                              console.log('팔로우 요청이 없다.')
                          }
                          snapshot.forEach(doc =>{
                             window.db.collection('alarm').doc('meetup').collection('messages').doc(doc.id).set({
                                    confirm : true
                                },{merge:true}).catch(err => {
                                    console.log(err);
                                });
                          })
                      })
            this.$router.push({ path: '/meetup/add' });
      }
    }
  }
}
</script>

<style>

</style>