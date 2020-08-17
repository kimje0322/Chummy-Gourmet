<template>
<div style = "padding :0 ">

     <v-toolbar dark>
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
      <p class="my-auto">채팅</p>
      <v-spacer></v-spacer>
    </v-toolbar>

   <div class="container chat">
        <h4 class="text-primary text-center">{{ this.room.name }}</h4>
        <div class="card">
            <div class="card-body">
                <p class="text-secondary nomessages" v-if="messages.length == 0">
                    [No messages yet!]
                </p>
                <!-- {{messeages}}아니 -->
                <div class="messages" v-chat-scroll="{always: false, smooth: true}">
                    <div v-for="(message,i) in messages" :key="i">
                        <div v-if="message.userid == userid" style="text-align : right">
                             <span class="text-info">[{{ message.nickname }}]: </span>
                            <span>{{message.message}}</span>
                            <span class="text-secondary time">{{message.time}}</span>
                        </div>
                        <div v-else style = "text-align : left">
                            <span class="text-info">[{{ message.nickname }}]: </span>
                            <span>{{message.message}}</span>
                            <span class="text-secondary time">{{message.time}}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-action">
                <CreateMessage :id="userid" :rid="this.room.rid" :to="this.room.to" :myNickName="this.myNickName" />
            </div>
        </div>
    </div>
</div>
</template>

<script>
import CreateMessage from '../../components/common/CreateMessage';
import moment from 'moment';

import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
    props: ['room'],
    components: {
        CreateMessage
    },
    data(){
        return {
            // room :{},
            messages : [],
            userid : this.$cookie.get('userId'),
            // to : []
            myNickName : ''

        }
    },
    created(){
              var confirmChat = confirm("대화하시겠습니까?");
                if(confirmChat == false){
                    this.$router.go(-1);
                }
                //  console.log(this.room)
              this.view(this.room);

                    //접속한 방의 알림을 모두 읽음으로 변경.
                      window.db.collection('alerm').doc('chat').collection('messages').where('roomid','==',this.room.rid).where('to','==',this.userid).onSnapshot(
                      snapshot =>{
                          if(snapshot.empty) {
                              console.log('없다')
                          }

                          snapshot.forEach(doc =>{
                              console.log(doc.id, '=>',doc.data())
                             window.db.collection('alerm').doc('chat').collection('messages').doc(doc.id).set({
                                    confirm : true
                                },{merge:true}).catch(err => {
                                    console.log(err);
                                });
                          })
                      })

              //내 닉네임 구하는 부분
               axios
                            .post(
                                `${SERVER_URL}/chat/nickname`,[this.userid]
                            ).then((response) =>{
                                // console.log('내 닉네임');
                                // console.log(response);
                                this.myNickName = response.data[0];
                                // console.log(this.myNickName);
                            }).chatch((err) =>{

                            })

    
            },
    methods:{
        view(room){
             window.db.collection('test').doc(this.room.rid).collection('messages').orderBy('time').onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                    //   console.log(change.doc.data());
                      var doc = change.doc;

                      //닉네임 구하기
                      var id = doc.data().from;
                      var num = this.room.id.indexOf(id*1);   
                      if(num == -1){
                          num = this.room.id.indexOf(id);
                      }    
             
                    //   console.log("--------------------")
                    //   console.log(id)
                    //   console.log(typeof(id));
                    //   console.log(num)              
                    //   console.log(this.room.id)
                    //   console.log(this.room.nickName[num])
                    //   console.log(this.room.nickName)

                      var ms ={
                        message : doc.data().message,
                        nickname : this.room.nickName[num],
                        time : moment(doc.data().time).format('LLL'),
                        userid : doc.data().from,
                        // to : to
                      }
                    //   console.log('ms');
                    //   console.log(ms);

                      this.messages.push(ms);

                    }
                  })
                })
        }
    }

}
</script>

<style>
.card-body{
    height : 440px;
}

.chat h2{
    font-size: 2.6em;
    margin-bottom: 0px;
}
.chat h5{
    margin-top: 0px;
    margin-bottom: 40px;
}
.chat span{
    font-size: 1.2em;
}
.chat .time{
    display: block;
    font-size: 0.7em;
}
.messages{
    max-height: 400px;

    /* height : 400px; */
    overflow: auto;
}
</style>