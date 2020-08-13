<template>
  <div>
       <v-toolbar dark>
      <!-- 중앙정렬 하기 위해 2개씀 -->
      <a @click="$router.go(-1)"><i class="fas fa-chevron-left"></i></a><v-spacer></v-spacer>
      <p class="my-auto">채팅방</p>
      <v-spacer></v-spacer>
    </v-toolbar>

      <div v-for="(room, i) in chatroom" :key="i"> 
        <div>
            <br><br>
            <input style="width : 80%; border : 1px solid" readonly :value="room.nickName"/>
            <v-btn height="48px" width="8%" style="float : right;" class="btn btn-primary" @click="chatGo(room)">입장</v-btn>
        </div>
      </div>

      <!-- <div>
        <input type = "text" v-model = "name" placeholder="추가할 채팅방 이름입력">
        <button @click="addRoom">추가</button>
      </div> -->
  </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {

    data(){
        return{
            chatroom : [],
            name : '',
            responseNickName : []
        }
    },
    created(){
        window.db.collection('test').where('id', 'array-contains', this.$cookie.get('userId')).get()
                 .then(snapshot=>{
                  //없을경우
                      if(snapshot.empty){
                        console.log("없다");
                        return;
                        }

                    //있을경우
                      snapshot.forEach(doc=>{

                          //axios로 id배열 보내서 닉네임 배열로 바꿔온다.
                          axios
                            .post(
                                `${SERVER_URL}/chat/nickname`,doc.data().id
                            )
                            .then((response) => {
                                this.responseNickName = response.data;
                                console.log(this.responseNickName);

                                var chatData = {
                                name : doc.data().name,
                                id : doc.data().id,
                                rid : doc.id,
                                nickName : this.responseNickName
                                }
                            console.log(chatData);

                            this.chatroom.push(chatData);
                        //  console.log(doc.id, '=>',doc.data());
                           })
                            .catch((error) => {
                                console.log(error.response);
                            });
                          
                                console.log(this.responseNickName);

                         
                       })
                  })
                  .catch(err => {
                     console.log(err);
                  });
    },
    methods:{
        chatGo(room){
            console.log(room);
            this.$router.push({name: 'Chat', params: {room: room}});
        },
        addRoom(){
             if (this.name) {
                window.db.collection('test').where('id', 'array-contains', this.name).orderby('time').get()
                .then(snapshot=>{
                    //없을경우
                    if(snapshot.empty){
                        console.log("없다");
                        return;
                    }

                    //있을경우
                    snapshot.forEach(doc=>{
                        console.log(doc.id, '=>',doc.data());
                    })
                })
                .catch(err => {
                    console.log(err);
                });
                this.newMessage = null;
                this.errorText = null;
            } else {
                this.errorText = "A message must be entered first!";
            }
        }
    }
}
</script>

<style>

</style>