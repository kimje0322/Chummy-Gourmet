<template>
    <div>
    <span style="display: inline-block;">
                    <button @click="onClick" style="background: 0 0; border: 0; display: flex; padding: 8px;">
                    <div>
                        <i
                        style="display: block; position: relative; height: 24px; width: 24px;"
                        class="far fa-paper-plane"
                        ></i>
                    </div>
                    </button>
                </span>
    </div>
</template>

<script>
import axios from "axios";
import router from "@/routes";

const SERVER_URL = "https://i3b302.p.ssafy.io:8080";
// const SERVER_URL = "https://localhost:8080";

export default {
    props: ['postuserid'],
    data(){
        return{
            userid : this.$cookie.get('userId'),
            data :[],
            nick : []
            // room : {}
        }
    },
    methods:{
        onClick(){
            console.log("hello");
            console.log(this.postuserid);
            console.log(this.userid);

            //클릭한 아이디 둘로 배열을 만든다 (정렬해서)
            if(this.postuserid==this.userid){
                console.log("같다")
                return;
            }
            else if(parseInt(this.postuserid)>parseInt(this.userid)){
                console.log("pustuserid가 더 크다")
                this.data = [this.userid,this.postuserid];
            }
            else{
                console.log("userid가 더 크다.");
                this.data = [this.postuserid,this.userid];
            }
                console.log(this.data);

            //동일한 채팅방이 있는지 검사한다.
            window.db.collection('test').where('id', '==', this.data).get()
                 .then(snapshot=>{
                  //없을경우
                    var room = {};
                    // var ro = {};
                      if(snapshot.empty){
                            //만들기

                            //새로운 채팅방 생성 
                            const newRoomRef = window.db.collection('test').doc();
                            console.log(newRoomRef.id);
                            window.db.collection('test').doc(newRoomRef.id).collection('test').doc();

                             var res = window.db.collection('test').doc(newRoomRef.id).set({
                                id :this.data,
                                time : Date.now(),
                                name : "Room"
                            }).catch(err =>{
                                console.log(err);
                            })

                            alert("새로운 채팅방 생성");
                            return;
                        }

                    //있을경우
                    else{
                        snapshot.forEach(doc=>{
                            //데이터 집어넣기
                            room.id = this.data;
                            room.time = Date.now();
                            room.name = doc.data().name;
                            room.rid = doc.id;
                         })
                    }

                    //닉네임 집어넣기
                    axios
                            .post(
                                `${SERVER_URL}/chat/nickname`,this.data
                            )
                            .then((response) => {
                                this.nickName
                                this.nick.push(response.data);
                                room.nickName = response.data;
                           })
                            .catch((error) => {
                                console.log(error.response);
                            });


                         this.goChat(room);
                  })
                  .catch(err => {
                     console.log(err);
                  });
        },
        goChat(room){
            //데이터 넣고 채팅방으로 이동
            console.log("함수이동 ");
            console.log(room)
            this.$router.push({name: 'Chat', params: {room: room}});
        }
    }
}
</script>

<style>

</style>