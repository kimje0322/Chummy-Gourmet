<template>
<div style="padding : 0">
 <review-detail />
</div>
</template>
<script>
import ReviewDetail from "../../components/feed/ReviewDetail.vue";

export default {
   data () {
      return {
        activeBtn: 1,
      }
    },
    components : {ReviewDetail}
};
</script>