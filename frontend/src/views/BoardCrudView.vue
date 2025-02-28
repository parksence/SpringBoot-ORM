<template>
  <div>
    <h1>게시판 CRUD 보기</h1>
    <button class="button-ui" @click="newBoard">추가</button>
    <button class="delete-button-ui" @click="deleteSelected">삭제</button>
    <div v-if="loading">데이터를 불러오는 중입니다...</div> <!-- 로딩 메시지 -->
    <div v-else>
      <grid
          ref="grid"
          :data="gridProps.data"
          :columns="gridProps.columns"
          :rowHeaders="['checkbox']"
          :pageOptions="gridProps.pageOptions"
          :pagination="true"
          @click="onClick"
          @pageChange="onPageChange"
          @check="handleCheck"
          @uncheck="handleUncheck"
          @checkAll="handleCheckAll"
          @uncheckAll="handleUncheckAll"
      />
    </div>
  </div>
</template>

<script>
import { Grid } from '@toast-ui/vue-grid';
import 'tui-grid/dist/tui-grid.css';
import 'tui-pagination/dist/tui-pagination.css';
import axios from 'axios';

export default {
  components: {
    grid: Grid,
  },
  data() {
    return {
      loading: true, // 로딩 상태 추가
      currentPage: 1,
      selectedRows: [], // 선택된 행의 ID를 저장할 배열
      gridProps: {
        data: [], // 데이터는 빈 배열로 초기화
        columns: [
          { header: 'No', name: 'id', sortable: true, compare: (a, b) => a - b },
          { header: '제목', name: 'title', formatter: ({ value }) => `<strong>${value}</strong>` },
          { header: '작성자', name: 'author' },
        ],
        pageOptions: {
          useClient: true,
          perPage: 10,
          visiblePages: 5,
          centerAlign: true,
        }
      }
    };
  },
  methods: {
    async fetchBoardData() {
      try {
        const response = await axios.get(process.env.VUE_APP_API_URL + 'notice/board/list');
        const responseData = response.data.data; // 서버에서 받은 데이터 구조에 맞게 수정 필요
        const postArray = Object.values(responseData); // 객체를 배열로 변환

        // ID 기준 내림차순 정렬
        postArray.sort((a, b) => b.id - a.id);

        this.gridProps.data = postArray; // 변환된 데이터를 gridProps.data에 저장
        this.loading = false; // 데이터를 불러온 후 로딩 상태 해제
      } catch (error) {
        console.error('Error fetching data:', error);
        this.loading = false; // 에러 발생 시에도 로딩 상태 해제
      }
    },
    onClick(ev) {
      if (ev.columnName === 'title' && ev.rowKey + 1) {
        const post = this.gridProps.data[ev.rowKey];
        const postId = post.id;

        this.$router.push(`/board-crud/detail/${postId}`);
      }
    },
    onPageChange(page) {
      this.currentPage = page;
    },
    newBoard() {
      // 신규 게시글 작성 페이지로 이동
      this.$router.push('/board-crud/new');
    },
    async deleteSelected() { // 삭제 버튼 클릭
      const pageRows = this.$refs.grid.invoke('getCheckedRows');
      const values = pageRows.map(row => row.id);

      if (values.length === 0) {
        alert('삭제할 게시글을 선택해 주세요.');
        return;
      } else {
        try {
          const response = await axios.post(process.env.VUE_APP_API_URL + 'notice/board/delete', {
            ids: values // 삭제할 ID 배열 전송
          });

          // 서버 응답이 'ok' 일 때 화면 갱신
          if (response.status === 200 && response.data === "게시물이 삭제되었습니다.") {
            console.log('삭제 성공:', response.data);

            // 화면 새로고침
            location.reload();
          } else {
            console.log('삭제 실패 또는 서버 오류:', response.data);
          }

        } catch (error) {
          console.error('Error deleting data:', error);
        }
      }
    },
    handleCheck() {
      console.log('handleCheck')
      // 선택된 행 ID를 업데이트
      const pageRows = this.$refs.grid.invoke('getCheckedRows');
      const ids = pageRows.map(row => row.id);
      this.selectedRows.push(ids);
    },
    handleUncheck(ev) {
      console.log('handleUncheck')
      // 선택된 행 ID를 업데이트
      this.selectedRows = this.selectedRows.filter(id => id !== ev.rowKey+1);
    },
    handleCheckAll() {
      console.log('handleCheckAll')
      // 현재 페이지의 모든 행 선택
      const pageRows = this.$refs.grid.invoke('getCheckedRows');
      const ids = pageRows.map(row => row.id);
      this.selectedRows.push(...ids);
    },
    handleUncheckAll() {
      console.log('handleUncheckAll')
      // 현재 페이지의 모든 행 선택 해제
      this.selectedRows = [];
    },
  },
  mounted() {
    // 게시판 리스트 조회
    this.fetchBoardData();
  }
};
</script>

<style>
.button-ui {
  position: fixed;            /* 버튼을 고정 위치에 놓음 */
  top: 125px;                 /* 화면 우측에서 20px 떨어진 위치 */
  right: 20px;                /* 화면 하단에서 20px 떨어진 위치 */
  padding: 10px 20px;         /* 버튼 내부 여백 (위아래 10px, 좌우 20px) */
  background-color: #007bff;  /* 파란색 배경 */
  color: white;               /* 버튼 텍스트 색상 */
  border: none;               /* 테두리 제거 */
  border-radius: 5px;         /* 둥근 테두리 */
  font-size: 16px;            /* 폰트 크기 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 살짝 그림자 추가 */
  cursor: pointer;            /* 마우스 포인터를 버튼 모양으로 */
  transition: background-color 0.3s ease, transform 0.3s ease; /* 배경색, 크기 전환 효과 */
}

.button-ui:hover {
  background-color: #0056b3;  /* 호버 시 조금 더 짙은 파란색으로 변경 */
  transform: translateY(-3px); /* 마우스 호버 시 버튼이 살짝 위로 올라가는 효과 */
}

.button-ui:active {
  transform: translateY(1px);  /* 클릭 시 살짝 눌린 효과 */
}

.delete-button-ui {
  position: fixed;
  top: 130px; /* 위치 조정 */
  right: 90px; /* 오른쪽으로 간격 */
  padding: 10px 20px;
  background-color: #dc3545; /* 빨간색 배경 */
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.delete-button-ui:hover {
  background-color: #c82333; /* 호버 시 조금 더 짙은 빨간색 */
  transform: translateY(-3px);
}

.delete-button-ui:active {
  transform: translateY(1px);
}

</style>
