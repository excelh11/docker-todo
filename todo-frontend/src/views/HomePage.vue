<template>
  <div class="home">
    <div class="section-header">
      <h3>나의 할 일</h3>
      <span class="summary">완료 {{ doneCount }} / 전체 {{ todos.length }}</span>
    </div>

    <v-progress-linear
      class="progress"
      :model-value="progress"
      color="primary"
      height="10"
      rounded
    ></v-progress-linear>

    <v-btn-toggle v-model="filter" mandatory density="comfortable" color="primary" class="filter">
      <v-btn value="active">진행 중</v-btn>
      <v-btn value="done">완료</v-btn>
      <v-btn value="all">전체</v-btn>
    </v-btn-toggle>

    <div class="todo-list">
      <p v-if="filteredTodos.length === 0" class="empty">
        할 일이 없습니다. Categories 메뉴에서 카테고리를 골라 할 일을 추가해 보세요.
      </p>
      <div
        v-for="todo in filteredTodos"
        :key="todo.todoId"
        class="todo-item"
        :class="{ 'is-done': todo.completed, 'is-overdue': todo.overdue }"
        :style="{ borderLeftColor: todo.category ? todo.category.color : '#BDBDBD' }"
      >
        <v-checkbox-btn
          :model-value="todo.completed"
          color="primary"
          @update:model-value="toggleTodo(todo)"
        ></v-checkbox-btn>

        <div class="todo-details">
          <p class="todo-title">{{ todo.title }}</p>
          <div class="todo-meta">
            <v-chip
              v-if="todo.category"
              size="small"
              :color="todo.category.color"
              variant="tonal"
              @click="openCategoryDetailModal(todo.category.categoryId)"
            >
              <v-icon start :icon="todo.category.icon"></v-icon>
              {{ todo.category.categoryName }}
            </v-chip>
            <v-chip v-else size="small" variant="tonal">카테고리 없음</v-chip>
            <v-chip size="small" :color="priorityInfo(todo.priority).color" variant="outlined">
              {{ priorityInfo(todo.priority).title }}
            </v-chip>
            <span class="due" :class="{ overdue: todo.overdue }">
              <v-icon v-if="todo.overdue" size="small" color="error">mdi-alert-circle-outline</v-icon>
              {{ todo.completed ? '완료' : dueLabel(todo.dueDate) }}
            </span>
          </div>
        </div>

        <v-icon class="delete-icon" color="#B0B0B0" @click="deleteTodo(todo.todoId)">mdi-delete-outline</v-icon>
      </div>
    </div>

    <h3>최근 기록</h3>
    <div class="log-container">
      <p v-if="logs.length === 0" class="empty">아직 기록이 없습니다.</p>
      <v-card v-for="log in logs" :key="log.todoLogId" class="log-card" variant="outlined">
        <div class="log-header">
          <v-icon v-if="log.done" color="primary"> mdi-check-decagram </v-icon>
        </div>
        <div class="log-body">
          <v-card-text class="log-title">
            <h5 class="log-todo-title">{{ log.todo.title }}</h5>
            {{ log.note }}
          </v-card-text>
        </div>
        <div class="log-footer">
          <v-card-text>{{ log.logDate }}</v-card-text>
        </div>
      </v-card>
    </div>

    <CategoryDetailModal
      :deleteButton="false"
      :isOpen="showCategoryDetailModal"
      @update:isOpen="showCategoryDetailModal = $event"
      :categoryId="selectedCategoryId"
      @added-todo="fetchMyTodos"
    />
  </div>
</template>
<script>
import { reactive, toRefs, computed } from 'vue';
import api from '@/api/api';
import { useStore } from 'vuex';
import CategoryDetailModal from '@/components/modals/CategoryDetailModal.vue';
import { dueLabel } from '@/utils/date';
import { priorityInfo } from '@/utils/todo';

export default {
  name: 'HomePage',
  components: {
    CategoryDetailModal
  },
  setup() {
    const store = useStore();

    const state = reactive({
      logs: [],
      todos: [],
      filter: 'active',
      showCategoryDetailModal: false,
      selectedCategoryId: null,
    });

    const user = computed(() => store.state.user);

    const filteredTodos = computed(() => {
      if (state.filter === 'active') return state.todos.filter((t) => !t.completed);
      if (state.filter === 'done') return state.todos.filter((t) => t.completed);
      return state.todos;
    });

    const doneCount = computed(() => state.todos.filter((t) => t.completed).length);

    const progress = computed(() =>
      state.todos.length === 0 ? 0 : Math.round((doneCount.value / state.todos.length) * 100)
    );

    const fetchRecentLogs = async () => {
      const userId = user.value.userId;
      try {
        const response = await api.get(`/api/v1/todo-logs/recent/user/${userId}`);
        state.logs = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const fetchMyTodos = async () => {
      const userId = user.value.userId;
      try {
        const response = await api.get(`/api/v1/todos/user/${userId}`);
        state.todos = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const toggleTodo = async (todo) => {
      try {
        await api.patch(`/api/v1/todos/${todo.todoId}/toggle`);
        await fetchMyTodos();
      } catch (error) {
        console.error(error);
      }
    };

    const deleteTodo = async (todoId) => {
      if (!confirm('이 할 일을 삭제할까요? 진행 기록도 함께 삭제됩니다.')) return;
      try {
        await api.delete(`/api/v1/todos/${todoId}`);
        await Promise.all([fetchMyTodos(), fetchRecentLogs()]);
      } catch (error) {
        console.error(error);
      }
    };

    const openCategoryDetailModal = (categoryId) => {
      state.selectedCategoryId = categoryId;
      state.showCategoryDetailModal = true;
    };

    fetchRecentLogs();
    fetchMyTodos();

    return {
      ...toRefs(state),
      user,
      filteredTodos,
      doneCount,
      progress,
      toggleTodo,
      deleteTodo,
      fetchMyTodos,
      openCategoryDetailModal,
      dueLabel,
      priorityInfo,
    };
  },
}
</script>
<style scoped>
.home {
  width: 100%;
  max-width: 900px;
  padding: 0 16px;
}

.section-header {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  margin-top: 15px;
}

.summary {
  color: #666;
  font-size: 0.95rem;
}

.progress {
  margin: 10px 0 20px;
}

.filter {
  margin-bottom: 10px;
}

.todo-list {
  margin: 10px 0 30px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.todo-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  border: 1px solid #e3e6f0;
  border-left: 6px solid #BDBDBD;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.06);
  background: #fff;
  text-align: left;
}

.todo-item.is-overdue {
  background: #FFF5F5;
}

.todo-item.is-done .todo-title {
  text-decoration: line-through;
  color: #9e9e9e;
}

.todo-details {
  flex: 1;
}

.todo-title {
  font-size: 1.05rem;
  font-weight: 600;
  margin: 0 0 6px;
}

.todo-meta {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 6px;
}

.due {
  font-size: 0.85rem;
  color: #666;
}

.due.overdue {
  color: #E53935;
  font-weight: 700;
}

.delete-icon {
  cursor: pointer;
}

.empty {
  color: #888;
  padding: 20px;
}

.log-container {
  margin: 30px 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.log-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  margin-left: 10px;
  margin-right: 10px;
  width: 280px;
  border-radius: 10px;
  border-color: #d9d9d9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.log-header {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  min-height: 24px;
}

.log-todo-title {
  color: var(--todo-primary);
  font-weight: bold;
}
</style>
