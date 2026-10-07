<template>
  <div class="log-container">
    <v-btn v-if="!dialog" class="add-log-btn" @click="dialog = !dialog">
      기록 추가
    </v-btn>
    <transition name="slide-fade">
      <v-card v-if="dialog" class="log-card">
        <div class="add-log-body">
          <v-card-text>
            <UserTodos :userId="user.userId" @updated-todo-id="updateTodoId" />
            <v-text-field v-model="newLog.note" label="오늘 한 일 / 메모" required></v-text-field>
            <v-text-field v-model="newLog.logDate" label="날짜" type="date"></v-text-field>
            <v-switch
              v-model="newLog.done"
              hide-details
              label="이 기록으로 할 일을 완료 처리"
              color="primary"
            ></v-switch>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#999999" @click="cancel">Cancel</v-btn>
            <v-btn color="primary" :disabled="!newLog.todo.todoId" @click="addLog">Add</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </transition>
    <p v-if="logs.length === 0" class="empty">아직 기록이 없습니다. '기록 추가'로 오늘 한 일을 남겨 보세요.</p>
    <v-card v-for="log in logs" :key="log.todoLogId" class="log-card" variant="outlined">
      <div class="log-header">
        <v-chip v-if="log.done" size="small" color="primary" variant="tonal">
          <v-icon start>mdi-check-decagram</v-icon> 완료함
        </v-chip>
        <v-spacer></v-spacer>
        <v-icon color="#DC143C" @click="deleteLog(log.todoLogId)"> mdi-delete </v-icon>
      </div>
      <div class="log-body">
        <v-card-text class="log-title">
          <h5 class="log-todo-title">
            {{ log.todo.title }}
            <v-chip
              v-if="log.todo.category"
              size="x-small"
              :color="log.todo.category.color"
              variant="tonal"
              @click="openCategoryDetailModal(log.todo.category.categoryId)"
            >
              {{ log.todo.category.categoryName }}
            </v-chip>
          </h5>
          {{ log.note }}
        </v-card-text>
      </div>
      <div class="log-footer">
        <v-card-text>{{ log.logDate }}</v-card-text>
      </div>
    </v-card>
    <CategoryDetailModal :isOpen="showCategoryDetailModal" @update:isOpen="showCategoryDetailModal = $event" :categoryId="selectedCategoryId" />
  </div>
</template>
<script>
import { reactive, toRefs, computed } from 'vue';
import api from '@/api/api';
import CategoryDetailModal from '@/components/modals/CategoryDetailModal.vue';
import UserTodos from '@/components/UserTodos.vue';
import { useStore } from 'vuex';
import { toLocalDateString } from '@/utils/date';

const emptyLog = () => ({
  todo: {
    todoId: null
  },
  note: '',
  logDate: toLocalDateString(),
  done: false,
});

export default {
  name: 'TodologList',
  components: {
    CategoryDetailModal,
    UserTodos
  },
  setup() {
    const store = useStore();

    const state = reactive({
      logs: [],
      newLog: emptyLog(),
      dialog: false,
      showCategoryDetailModal: false,
      selectedCategoryId: null,
    });

    const user = computed(() => store.state.user);

    const fetchLogs = async () => {
      const userId = user.value.userId;
      try {
        const response = await api.get(`/api/v1/todo-logs/user/${userId}`);
        state.logs = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const addLog = async () => {
      try {
        await api.post('/api/v1/todo-logs', state.newLog);
        if (state.newLog.done) {
          store.dispatch("showPopup", {
            message: "기록을 남기고 할 일을 완료 처리했습니다.",
            status: "success",
            showingSecond: 1500,
          });
        }
        state.dialog = false;
        state.newLog = emptyLog();
        await fetchLogs();
      } catch (error) {
        console.error(error);
        store.dispatch("showPopup", {
          message: "기록을 추가하지 못했습니다.",
          status: "error",
          showingSecond: 1500,
        });
      }
    };

    const cancel = () => {
      state.dialog = false;
      state.newLog = emptyLog();
    };

    const deleteLog = async (todoLogId) => {
      try {
        await api.delete(`/api/v1/todo-logs/${todoLogId}`);
        await fetchLogs();
      } catch (error) {
        console.error(error);
      }
    };

    const openCategoryDetailModal = (categoryId) => {
      state.selectedCategoryId = categoryId;
      state.showCategoryDetailModal = true;
    };

    const updateTodoId = (newTodoId) => {
      state.newLog.todo.todoId = newTodoId;
    };

    fetchLogs();

    return {
      ...toRefs(state),
      user,
      addLog,
      cancel,
      deleteLog,
      openCategoryDetailModal,
      updateTodoId
    };
  },
}
</script>
<style scoped>
.log-container {
  margin: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.log-title {
  font-size: 20px;
  font-weight: bold;
  padding: 20px;
  border-bottom: 1px solid #e9ebee;
}

.log-todo-title {
  color: var(--todo-primary);
  font-weight: bold;
}

.log-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  width: 800px;
  max-width: 90%;
  border-radius: 10px;
  border-color: #d9d9d9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.log-header {
  display: flex;
  align-items: center;
  width: 100%;
}

.add-log-body {
  justify-content: center;
  width: 100%;
}

.log-body {
  justify-content: center;
  width: 100%;
}

.log-footer {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  text-align: right;
}

.empty {
  color: #888;
  padding: 20px;
}

.add-log-btn {
  width: 800px;
  max-width: 90%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  bottom: 20px;
  position: relative;
  z-index: 1;
  display: block;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  background-color: var(--todo-primary);
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.slide-fade-enter-active {
  transition: all 0.7s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.1s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-20px);
  opacity: 0;
}
</style>
