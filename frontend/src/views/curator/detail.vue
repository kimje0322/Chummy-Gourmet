<template>
<div style="padding : 0">
 <feed-detail />
</div>
</template>
<script>
import FeedDetail from "../../components/feed/FeedDetail.vue";

export default {
   data () {
      return {
        activeBtn: 1,
      }
    },
    components : {FeedDetail}
};
</script>