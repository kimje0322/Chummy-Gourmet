<template>
<div style="padding : 0">
<input type="text" placeholder="원하는 지역 검색" style="width : 90%; border : 1px solid">
        <button class="fas fa-search" style="width : 10%" />
        <br>
        <p>대전광역시 봉명동에 대한 결과</p>
        <feed-item />
        <feed-item2 />
        <feed-item3 />
        
</div>
</template>
<script>
import FeedItem from "../../components/feed/FeedItem.vue";
import FeedItem2 from "../../components/feed/FeedItem2.vue";
import FeedItem3 from "../../components/feed/FeedItem3.vue";

export default {
   data () {
      return {
        activeBtn: 1,
      }
    },
  components: { FeedItem,FeedItem2,FeedItem3 },
};
</script>