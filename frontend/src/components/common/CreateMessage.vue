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
    props: ['id','rid'],
    data() {
        return {
            newMessage: null,
            errorText: null
        }
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
            } else {
                this.errorText = "A message must be entered first!";
            }
        }
    }    
}
</script>
