<template>
  <div>
    <h1>게시물 상세보기</h1>
    <div v-if="loading">데이터를 불러오는 중입니다...</div> <!-- 로딩 메시지 -->
    <div v-else>
      <div v-if="post">
        <h2>{{ post.title }}</h2>
        <p><strong>작성자:</strong> {{ post.author }}</p>
        <p><strong>내용:test</strong></p>
        <p>{{ post.content }}</p> <!-- 게시물 내용 -->
        <p><strong>댓글 수:</strong> {{ post.comment_cnt }}</p>
      </div>
      <div v-else>
        <p>게시물을 찾을 수 없습니다.</p> <!-- 게시물이 없을 때 메시지 -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loading: true, // 로딩 상태
      post: null,    // 게시물 데이터
    };
  },
  methods: {
    async fetchPostData() {
      const postId = this.$route.params.id; // URL 파라미터에서 게시물 ID 가져오기
      try {
        const response = await axios.get(`http://localhost:8080/board/list/${postId}`);
        this.post = response.data.data; // 서버에서 받은 데이터 저장
      } catch (error) {
        console.error('게시물 데이터를 불러오는 중 오류 발생:', error);
      } finally {
        this.loading = false; // 데이터 요청 완료 후 로딩 상태 해제
      }
    },
  },
  mounted() {
    this.fetchPostData(); // 컴포넌트가 마운트될 때 데이터 요청
  }
};
</script>

<style>
/* 스타일 추가 가능 */
</style>
