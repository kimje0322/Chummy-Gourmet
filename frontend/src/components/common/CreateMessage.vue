<template>
    <div style="margin-bottom: 30px;">
        <form @submit.prevent="createMessage">
            <div class="form-group">
                <input style = "width : 80%; display : inline" type="text" name="message" class="form-control" placeholder="Enter message ..." v-model="newMessage">
               <v-btn style = "width : 20%" class="btn btn-primary" type="submit" name="action">Submit</v-btn>
               <!-- <button style = "float : right" class="btn btn-primary" type="submit" name="action">Submit</button> -->
                <p class="text-danger" v-if="errorText">{{ errorText }}</p>
            </div>
            
        </form>
    </div>
</template>

<script>
// import fb from '@/firebase/init';


export default {
    name: 'CreateMessage',
    props: ['id','rid','to','myNickName'],
    data() {
        return {
            newMessage: null,
            errorText: null
        }
    },
    created(){
    },
    methods: {
        createMessage () {
            if (this.newMessage) {
                window.db.collection('test').doc(this.rid).collection('messages').add({
                    from : this.id,
                    message: this.newMessage,
                    time: Date.now()
                }).catch(err => {
                    console.log(err);
                });
                this.newMessage = null;
                this.errorText = null;


                for(var i = 0;i<this.to.length;i++){
                    // console.log(this.myNickName);
                    //새로운 알람을 보낸다.
                    window.db.collection('alarm').doc('chat').collection('messages').add({
                        to : this.to[i],
                        from : this.id,
                        message: this.myNickName+"님이 채팅메시지를 보냈습니다.",
                        time: Date.now(),
                        roomid : this.rid,
                        confirm : false
                    }).catch(err => {
                        console.log(err);
                    });
                }
            } else {
                this.errorText = "A message must be entered first!";
            }
        }
    }    
}
</script>
