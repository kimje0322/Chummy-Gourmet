<template>
  <div>
    <v-btn @click="goAlarmPage">
         <p >{{this.count}}</p>
    </v-btn>
  </div>
</template>

<script>
import router from "@/routes";

export default {
  

  data(){
    return {
      count : 0
    }
  },
  created(){
   this.chat();
   this.like();
  },
  methods : {
    goAlarmPage(){
      this.$router.push({ path: '/user/alarm' });
    },
    chat(){
       window.db.collection('alarm').doc('chat').collection('messages').where('confirm','==',false).where('to','==',this.$cookie.get('userId')).onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                      console.log(change.doc.data());
                      var doc = change.doc;

                      console.log(doc.data());
                      this.count++;

                    }
                  })
                })
    },
    like(){
      window.db.collection('alarm').doc('like').collection('messages').where('confirm','==',false).where('to','==',this.$cookie.get('userId')).onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                    //   console.log(change.doc.data());
                      var doc = change.doc;

                      console.log(doc.data());
                      this.count++;

                    }
                  })
                })
    }

  }

}
</script>

<style>

</style>