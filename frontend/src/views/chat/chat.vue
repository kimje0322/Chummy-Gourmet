<template>
<div style = "padding :0 ">
   <div class="container chat">
        <h2 class="text-primary text-center">Chat</h2>
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
                window.db.collection('test').doc(this.room.rid).collection('messages').orderBy('time').onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                      console.log(change.doc.data());
                      var doc = change.doc;

                      //닉네임 구하기
                      var id = doc.data().from;
                      var num = this.room.id.indexOf(id);
                      // console.log(num);
                      // console.log(this.room.nickName[num]);
                      

                      var ms ={
                        message : doc.data().message,
                        nickname : this.room.nickName[num],
                        time : doc.data().time,
                        userid : this.userid
                      }
                      console.log(ms);

                      this.messages.push(ms);

                    }
                  })
                })
            },
    methods:{
        
    }

}
</script>

<style>
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
    max-height: 300px;
    overflow: auto;
}
</style>