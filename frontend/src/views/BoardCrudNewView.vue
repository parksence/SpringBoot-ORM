<template>
  <div class="board-crud-new">
    <h1>게시글 생성</h1>
    <form @submit.prevent="createPost">
      <div>
        <label for="title">제목:</label>
        <input type="text" v-model="title" required />
      </div>
      <div>
        <label for="author">작성자:</label>
        <input type="text" v-model="author" required />
      </div>
      <div>
        <label for="content">내용:</label>
        <textarea v-model="content" required></textarea>
      </div>
      <div>
        <label for="password">패스워드:</label>
        <input type="password" v-model="password" required />
      </div>
      <button class="button-ui" :disabled="!isFormValid">생성</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      author: '',
      content: '',
      password: '',
      errorMessage: '',
    };
  },
  computed: {
    isFormValid() {
      return this.title && this.author && this.content && this.password;
    },
  },
  methods: {
    async createPost() {
      try {
        await axios.post(process.env.VUE_APP_API_URL + '/notice/board/add', {
          title: this.title,
          author: this.author,
          content: this.content,
          password: this.password,
        });
        // 게시글 생성 성공 시 게시판 목록 페이지로 이동
        this.$router.push('/board-crud');
      } catch (error) {
        this.errorMessage = '게시글 생성 중 오류가 발생했습니다.'; // 오류 처리
      }
    },
  },
};
</script>

<style scoped>
.board-crud-new {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input, textarea {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-ui {
  background-color: #4CAF50; /* 초록색 */
  color: white;
  border: none;
  padding: 10px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
}

.button-ui:disabled {
  background-color: #ccc; /* 회색 */
  cursor: not-allowed;
}

.error {
  color: red;
  font-weight: bold;
}

</style>
