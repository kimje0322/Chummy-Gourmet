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
    // console.log('문제가있다.');
     window.db.collection('alarm').doc('chat').collection('messages').where('confirm','==',false).onSnapshot(snapshot=>{
                  snapshot.docChanges().forEach(change =>{
                    if (change.type == 'added'){
                    //   console.log(change.doc.data());
                      var doc = change.doc;

                      console.log(doc.data());
                      this.count++;

                    }
                  })
                })
  },
  methods : {
    goAlarmPage(){
      this.$router.push({ path: '/user/alarm' });
    }
  }

}
</script>

<style>

</style>