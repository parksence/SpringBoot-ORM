<template>
  <div>
    <h1>게시판 보기</h1>
    <div v-if="loading">데이터를 불러오는 중입니다...</div> <!-- 로딩 메시지 -->
    <div v-else>
      <grid
          :data="gridProps.data"
          :columns="gridProps.columns"
          :pageOptions="gridProps.pageOptions"
          :pagination="true"
          @click="onClick"
          @pageChange="onPageChange"
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
    grid: Grid
  },
  data() {
    return {
      loading: true, // 로딩 상태 추가
      currentPage: 1,
      gridProps: {
        data: [], // 데이터는 빈 배열로 초기화
        columns: [
          { header: 'No', name: 'id', sortable: true, compare: (a, b) => a - b },
          { header: '제목', name: 'title', formatter: ({ value }) => `<strong>${value}</strong>` },
          { header: '작성자', name: 'author' },
          { header: '댓글수', name: 'comment_cnt', sortable: true, compare: (a, b) => a - b },
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
        const response = await axios.get('http://localhost:8080/board/list');
        const responseData = response.data.data; // 서버에서 받은 데이터 구조에 맞게 수정 필요
        const postArray = Object.values(responseData); // 객체를 배열로 변환

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

        window.open(`/board/detail/${postId}`, '_blank');
      }
    },
    onPageChange(page) {
      this.currentPage = page;
    }
  },
  mounted() {
    this.fetchBoardData();
  }
};
</script>

<style>
/* 스타일 추가 가능 */
</style>
