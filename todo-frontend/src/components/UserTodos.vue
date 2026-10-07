<template>
  <div class="user-todos">
    <h3>내 할 일 리스트</h3>
    <v-select
      :items="userTodoList"
      item-title="label"
      item-value="todoId"
      label="할 일 선택"
      v-model="todoId"
      density="compact"
      no-data-text="진행 중인 할 일이 없습니다."
    ></v-select>
  </div>
</template>

<script>
import { ref, watch, onMounted } from 'vue';
import api from '@/api/api';

export default {
  name: 'UserTodos',
  props: {
    userId: {
      type: Number,
      required: true,
    },
  },
  emits: ['updated-todo-id'],
  setup(props, { emit }) {
    const userTodoList = ref([]);
    const todoId = ref(null);

    const fetchUserTodos = async () => {
      try {
        const response = await api.get(`/api/v1/todos/user/${props.userId}`);
        // 완료하지 않은 할 일만 기록 대상으로 보여 준다
        userTodoList.value = response.data
          .filter((todo) => !todo.completed)
          .map((todo) => ({
            ...todo,
            label: todo.category ? `${todo.title} (${todo.category.categoryName})` : todo.title,
          }));
      } catch (error) {
        console.error(error);
      }
    };

    onMounted(fetchUserTodos);

    watch(todoId, (newTodoId) => {
      emit('updated-todo-id', newTodoId);
    });

    return { userTodoList, todoId };
  },
};
</script>

<style scoped>
.user-todos {
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 1rem;
}

h3 {
  margin-top: 0;
  margin-bottom: 1rem;
}
</style>
