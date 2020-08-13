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
                <CreateMessage :id="userid" :rid="this.room.rid" />
            </div>
        </div>
    </div>
</div>
</template>

<script>
import CreateMessage from '../../components/common/CreateMessage';
import moment from 'moment';

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

        }
    },
    created(){
              var confirmChat = confirm("대화하시겠습니까?");
                if(confirmChat == false){
                    this.$router.go(-1);
                }
                //  console.log(this.room)
              this.view(this.room);
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
                    //   console.log("--------------------")
                    //   console.log(id)
                    //   console.log(num)              
                    //   console.log(this.room.id)
                    //   console.log(this.room.nickName[num])
                    //   console.log(this.room.nickName)

                      var ms ={
                        message : doc.data().message,
                        nickname : this.room.nickName[num],
                        time : moment(doc.data().time).format('LLL'),
                        userid : doc.data().from
                      }
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