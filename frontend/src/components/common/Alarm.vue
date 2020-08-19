<template>
     <v-btn @click="goAlarmPage" text color="orange accent-4">
       <v-badge v-model="show" color="indigo">
                <span slot="badge">{{count}}</span>
                <v-icon>mdi-bell</v-icon>
        </v-badge>
     </v-btn>
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
   this.alarm('chat');
   this.alarm('like');
   this.alarm('comment');
   this.alarm('follow');
   this.alarm('meetup');
  },
  methods : {
    goAlarmPage(){
      this.$router.push({ path: '/user/alarm' });
    },
    alarm(data){
      window.db.collection('alarm').doc(data).collection('messages').where('confirm','==',false).where('to','==',this.$cookie.get('userId')).onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                      // console.log(change.doc.data());
                      var doc = change.doc;

                      // console.log(doc.data());
                      this.count++;

                    }
                  })
                })

    },
  }

}
</script>

<style>

</style>