<template>
<v-app>
  <div class="feed newsfeed">
    <div class="wrapB">
      <!-- 상단 탭 시작 -->
      <div class="row" style="height : 40px">
        <div class="col" style="text-align : center;">
          <div class="like likeScrap">
          <svg
            class="svg-inline--fa fa-heart fa-w-16 icon full"
            aria-hidden="true"
            data-prefix="fas"
            data-icon="heart"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 248 312"
            data-fa-i2svg
          >
            <path
              fill="currentColor"
              d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z"
            />
          </svg>
      
        </div>
          </div>
        <div class="col" style="text-align : center;">
          <div class="comment">
          <svg
            class="svg-inline--fa fa-comment-alt fa-w-16 icon"
            aria-hidden="true"
            data-prefix="far"
            data-icon="comment-alt"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 248 312"
            data-fa-i2svg
          >
            <path
              fill="currentColor"
              d="M448 0H64C28.7 0 0 28.7 0 64v288c0 35.3 28.7 64 64 64h96v84c0 7.1 5.8 12 12 12 2.4 0 4.9-.7 7.1-2.4L304 416h144c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64zm16 352c0 8.8-7.2 16-16 16H288l-12.8 9.6L208 428v-60H64c-8.8 0-16-7.2-16-16V64c0-8.8 7.2-16 16-16h384c8.8 0 16 7.2 16 16v288z"
            />
          </svg>
          <!-- <i class="far fa-comment-alt icon"></i> -->
        
        </div>
        <!---->
        </div>
        <div class="col" style="text-align : center">
          <div class="share">
          <svg
            class="svg-inline--fa fa-share-alt fa-w-14 icon"
            aria-hidden="true"
            data-prefix="fas"
            data-icon="share-alt"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 248 312"
            data-fa-i2svg
          >
            <path
              fill="currentColor"
              d="M352 320c-22.608 0-43.387 7.819-59.79 20.895l-102.486-64.054a96.551 96.551 0 0 0 0-41.683l102.486-64.054C308.613 184.181 329.392 192 352 192c53.019 0 96-42.981 96-96S405.019 0 352 0s-96 42.981-96 96c0 7.158.79 14.13 2.276 20.841L155.79 180.895C139.387 167.819 118.608 160 96 160c-53.019 0-96 42.981-96 96s42.981 96 96 96c22.608 0 43.387-7.819 59.79-20.895l102.486 64.054A96.301 96.301 0 0 0 256 416c0 53.019 42.981 96 96 96s96-42.981 96-96-42.981-96-96-96z"
            />
          </svg>
        </div>
        </div>
      </div>
  <!-- 상단 탭 종료 -->
      <input type="text" style="width : 100%;" placeholder="검색">      
      <InputComponent />
      <br><br>
      <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem />
       <v-bottom-navigation
       scroll-target="#scroll-area-2"
      hide-on-scroll
      scroll-threshold="500"
      absolute
      color="white"
      horizontal
    >

      <v-btn text color="deep-purple accent-4">
        <span>Recents</span>
        <v-icon>mdi-history</v-icon>
      </v-btn>

      <v-btn text color="deep-purple accent-4">
        <span>Favorites</span>
        <v-icon>mdi-heart</v-icon>
      </v-btn>

      <v-btn text color="deep-purple accent-4">
        <span>Nearby</span>
        <v-icon>mdi-map-marker</v-icon>
      </v-btn>
    </v-bottom-navigation>
    </div>
  </div>
</v-app>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import TextareaComponent from "../../components/common/Textarea";
import InputComponent from "../../components/common/Input";

export default {
  props: ["keyword"],

  components: { FeedItem,InputComponent }
};
</script>

