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
            <v-btn height="48px" width="8%" style="float : right;" class="btn btn-primary" @click="onClick(list.type)">확인</v-btn>
            <br>
            {{ list.time | moment("from","now")}}
        </div>
      </div>

      <!-- <div>
        <input type = "text" v-model = "name" placeholder="추가할 채팅방 이름입력">
        <button @click="addRoom">추가</button>
      </div> -->
  </div>
</template>

<script>
import moment from 'moment';
export default {
  data(){
    return {
      alarm : []
    }
  },
  created(){
    this.chat();

  },

  methods:{
    chat(){
       window.db.collection('alarm').doc('chat').collection('messages').where('to', '==', this.$cookie.get('userId')).where('confirm','==',false).get()
                 .then(snapshot=>{
                  //없을경우
                      if(snapshot.empty){
                        console.log("없다");
                        return;
                        }

                    //있을경우
                      snapshot.forEach(doc=>{
                         var data ={
                           type : '채팅',
                           message : doc.data().message,
                           time : moment(doc.data().time).format('LLL'),
                         }
                         this.alarm.push(data);
                       })
                  })
                  .catch(err => {
                     console.log(err);
                  });
    },
    onClick(type){
      if(type=='채팅'){
        this.$router.push({ path: '/chatroom' });
      }
    }
  }
}
</script>

<style>

</style>