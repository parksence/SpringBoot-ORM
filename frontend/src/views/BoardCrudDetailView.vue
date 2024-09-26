<template>
  <div class="board-detail-view">
    <h1>게시물 상세보기</h1>
    <div v-if="loading">데이터를 불러오는 중입니다...</div> <!-- 로딩 메시지 -->
    <div v-else>
      <div v-if="post">
        <div v-if="isEditing">
          <div>
            <label for="title">제목:</label>
            <input type="text" v-model="post.title" />
          </div>
          <div>
            <label for="author">작성자:</label>
            <input type="text" v-model="post.author" />
          </div>
          <div>
            <label for="content">내용:</label>
            <textarea v-model="post.content"></textarea>
          </div>
          <div class="button-group">
            <button class="button-ui-1" @click="updatePost">수정 완료</button>
            <button class="button-ui-2 cancel-btn" @click="cancelEdit">취소</button>
          </div>
        </div>
        <div v-else>
          <h2>제목 : {{ post.title }}</h2>
          <p><strong>작성자:</strong> {{ post.author }}</p>
          <p><strong>내용:</strong></p>
          <p>{{ post.content }}</p> <!-- 게시물 내용 -->
          <button class="button-ui" @click="openPasswordModal">수정</button>
        </div>
      </div>
      <div v-else>
        <p>게시물을 찾을 수 없습니다.</p> <!-- 게시물이 없을 때 메시지 -->
      </div>
    </div>

    <!-- 패스워드 확인 모달 -->
    <div v-if="showPasswordModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closePasswordModal">&times;</span>
        <h3>패스워드 확인</h3>
        <p>수정을 위해 패스워드를 입력해주세요 :)</p>
        <input type="password" v-model="passwordInput" placeholder="패스워드 입력" @keyup.enter="verifyPassword" />
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
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
      isEditing: false, // 수정 모드 상태
      showPasswordModal: false, // 패스워드 모달 상태
      passwordInput: '', // 입력된 패스워드
      errorMessage: '', // 오류 메시지
    };
  },
  methods: {
    async fetchPostData() {
      const postId = this.$route.params.id; // URL 파라미터에서 게시물 ID 가져오기
      try {
        const response = await axios.get(process.env.VUE_APP_API_URL + `/notice/board/list/${postId}`);
        this.post = response.data.data[postId]; // 서버에서 받은 데이터 저장
      } catch (error) {
        console.error('게시물 데이터를 불러오는 중 오류 발생:', error);
      } finally {
        this.loading = false; // 데이터 요청 완료 후 로딩 상태 해제
      }
    },
    openPasswordModal() {
      this.showPasswordModal = true; // 패스워드 모달 열기
    },
    closePasswordModal() {
      this.showPasswordModal = false; // 패스워드 모달 닫기
      this.passwordInput = ''; // 입력된 패스워드 초기화
      this.errorMessage = ''; // 오류 메시지 초기화
    },
    async verifyPassword() {
      const postId = this.$route.params.id;
      try {
        // 서버에 패스워드 확인 요청
        const response = await axios.post(process.env.VUE_APP_API_URL + `/notice/board/verify-password`, {
          id: postId,
          password: this.passwordInput,
        });

        if (response.data.code) {
          this.isEditing = true; // 패스워드가 일치하면 수정 모드 활성화
          this.closePasswordModal(); // 모달 닫기
        } else {
          this.errorMessage = '패스워드가 일치하지 않습니다.'; // 패스워드 오류 메시지
        }
      } catch (error) {
        console.error('패스워드 확인 중 오류 발생:', error);
        this.errorMessage = '패스워드 확인 중 오류가 발생했습니다.';
      }
    },
    async updatePost() {
      try {
        await axios.put(process.env.VUE_APP_API_URL + `/notice/board/update`, this.post);
        alert('게시물이 성공적으로 수정되었습니다.');
        this.isEditing = false; // 수정 모드 비활성화
      } catch (error) {
        console.error('게시물 수정 중 오류 발생:', error);
        alert('게시물 수정 중 오류가 발생했습니다.');
      }
    },
    cancelEdit() {
      this.isEditing = false; // 수정 모드 취소
      this.fetchPostData(); // 원본 데이터로 복구
    },
  },
  mounted() {
    this.fetchPostData(); // 컴포넌트가 마운트될 때 데이터 요청
  }
};
</script>

<style>
.board-detail-view {
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

.button-ui:disabled {
  background-color: #ccc; /* 회색 */
  cursor: not-allowed;
}

.modal {
  display: block;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.5); /* 배경 반투명 */
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 300px;
  border-radius: 10px;
  text-align: center;
}

input[type="password"] {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover, .close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.error {
  color: red;
  font-weight: bold;
}

.button-group {
  display: flex;
  margin-top: 10px; /* 버튼 위에 간격 추가 */
  justify-content: space-between;
}

.button-ui {
  background-color: #4CAF50; /* 초록색 */
  color: white;
  border: none;
  padding: 10px 15px;
  text-align: center;
  display: flex; /* Flexbox 사용 */
  align-items: center; /* 버튼 내용 수직 중앙 정렬 */
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
  margin: 5px; /* 버튼 간의 여백 추가 */
  flex-grow: 1; /* 버튼이 동일한 크기로 확장되도록 설정 */
}

.cancel-btn {
  background-color: #FF5722; /* 오렌지색 */
}
</style>
