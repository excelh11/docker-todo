<template>
  <v-dialog v-model="isDialogOpen" max-width="600px">
    <v-card v-if="categoryDetail">
      <div class="category-banner" :style="{ backgroundColor: categoryDetail.color }">
        <v-icon :icon="categoryDetail.icon" size="72" color="white"></v-icon>
      </div>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <h2 class="category-name">{{ categoryDetail.categoryName }}</h2>
              <p class="category-desc">{{ categoryDetail.categoryDesc }}</p>
            </v-col>
            <v-col cols="12">
              <p class="category-info-label">기본 마감 기한:</p>
              <p>할 일을 추가하면 {{ categoryDetail.defaultDueDays }}일 뒤로 마감일이 자동 설정됩니다.</p>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-row class="button-group" align="center" justify="center">
        <v-col :cols="deleteButton ? 4 : 5">
          <v-btn block color="#999999" @click="close">닫기</v-btn>
        </v-col>
        <v-col v-if="deleteButton" cols="4">
          <v-btn block color="error" @click="removeCategory">삭제</v-btn>
        </v-col>
        <v-col :cols="deleteButton ? 4 : 5">
          <v-btn block color="primary" @click="openTodoDialog">할 일 추가</v-btn>
        </v-col>
      </v-row>
    </v-card>

    <v-dialog v-model="todoDialogOpen" max-width="420px">
      <v-card>
        <v-card-title class="text-center">
          <h3>할 일 추가</h3>
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="newTodo.title" label="할 일" autofocus />
          <v-select
            v-model="newTodo.priority"
            :items="priorities"
            item-title="title"
            item-value="value"
            label="중요도"
          />
          <v-text-field v-model="newTodo.dueDate" label="마감일" type="date" />
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn variant="flat" color="#999999" @click="todoDialogOpen = false">취소</v-btn>
          <v-btn variant="flat" color="primary" :disabled="!newTodo.title.trim()" @click="submitTodo">확인</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-dialog>
</template>

<script>
import { ref, reactive, watch, computed } from 'vue';
import api from '@/api/api.js';
import { useStore } from 'vuex';
import { addDays } from '@/utils/date';
import { PRIORITIES } from '@/utils/todo';

export default {
  name: 'CategoryDetailModal',
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
    categoryId: {
      type: Number,
      default: null,
    },
    deleteButton: {
      type: Boolean,
      default: false,
    }
  },
  emits: ['update:isOpen', 'removed-category', 'added-todo'],
  setup(props, { emit }) {
    const isDialogOpen = ref(false);
    const categoryDetail = ref(null);
    const todoDialogOpen = ref(false);
    const newTodo = reactive({ title: '', priority: 'MEDIUM', dueDate: '' });

    const store = useStore();
    const user = computed(() => store.state.user);

    const close = () => {
      emit('update:isOpen', false);
    };

    const fetchCategoryDetail = async () => {
      if (props.categoryId == null) return;
      try {
        const response = await api.get(`/api/v1/categories/${props.categoryId}`);
        categoryDetail.value = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const removeCategory = async () => {
      if (!confirm('이 카테고리를 삭제할까요? 이 카테고리의 할 일은 남고 카테고리만 비워집니다.')) return;
      try {
        await api.delete(`/api/v1/categories/${props.categoryId}`);
        emit('removed-category');
        close();
      } catch (error) {
        console.error(error);
      }
    };

    const openTodoDialog = () => {
      newTodo.title = '';
      newTodo.priority = 'MEDIUM';
      newTodo.dueDate = addDays(categoryDetail.value ? categoryDetail.value.defaultDueDays : 0);
      todoDialogOpen.value = true;
    };

    const submitTodo = async () => {
      try {
        await api.post(`/api/v1/todos`, {
          user: {
            userId: user.value.userId
          },
          category: {
            categoryId: props.categoryId
          },
          title: newTodo.title.trim(),
          priority: newTodo.priority,
          dueDate: newTodo.dueDate || null,
        });
        store.dispatch("showPopup", {
          message: `'${newTodo.title.trim()}' 할 일을 추가했습니다.`,
          status: "success",
          showingSecond: 1500,
        });
        emit('added-todo');
        todoDialogOpen.value = false;
        close();
      } catch (error) {
        console.error(error);
        store.dispatch("showPopup", {
          message: "할 일을 추가하지 못했습니다.",
          status: "error",
          showingSecond: 1500,
        });
      }
    };

    watch(() => props.isOpen, (newVal) => {
      isDialogOpen.value = newVal;
      if (newVal) {
        categoryDetail.value = null;
        fetchCategoryDetail();
        todoDialogOpen.value = false;
      }
    });

    // 바깥을 눌러 닫았을 때 부모에게도 알려 준다
    watch(isDialogOpen, (newVal) => {
      if (!newVal && props.isOpen) {
        close();
      }
    });

    return {
      isDialogOpen,
      categoryDetail,
      close,
      removeCategory,
      todoDialogOpen,
      newTodo,
      priorities: PRIORITIES,
      openTodoDialog,
      submitTodo,
    };
  },
};
</script>
<style scoped>
.v-btn {
  color: white;
}

.button-group {
  margin: 1rem;
}

.category-banner {
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.category-name {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.category-desc {
  font-size: 1rem;
  margin-bottom: 1rem;
  color: #555;
}

.category-info-label {
  font-weight: bold;
}
</style>
