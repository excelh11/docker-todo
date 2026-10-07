<template>
  <div class="container">
    <div class="category-list">
      <div
        v-for="category in categories"
        :key="category.categoryId"
        class="category-item"
        @click="openCategoryDetailModal(category.categoryId)"
      >
        <div class="category-banner" :style="{ backgroundColor: category.color }">
          <v-icon :icon="category.icon" size="64" color="white"></v-icon>
        </div>
        <div class="category-details">
          <h2 class="category-name">{{ category.categoryName }}</h2>
          <p class="category-due">기본 마감 {{ category.defaultDueDays }}일</p>
          <p class="category-desc">{{ category.categoryDesc }}</p>
        </div>
      </div>
    </div>
    <button class="add-category-button" @click="openCategoryAddModal">카테고리 추가</button>

    <CategoryAddModal :isOpen="showCategoryAddModal" @update:isOpen="showCategoryAddModal = $event" @added-category="fetchCategories" />
    <CategoryDetailModal
      :deleteButton="true"
      :isOpen="showCategoryDetailModal"
      @update:isOpen="showCategoryDetailModal = $event"
      :categoryId="selectedCategoryId"
      @removed-category="fetchCategories"
    />
  </div>
</template>

<script>
import api from '@/api/api';
import CategoryAddModal from '@/components/modals/CategoryAddModal.vue';
import CategoryDetailModal from '@/components/modals/CategoryDetailModal.vue';

export default {
  name: 'CategoryList',
  components: {
    CategoryAddModal,
    CategoryDetailModal,
  },
  data() {
    return {
      categories: [],
      showCategoryAddModal: false,
      showCategoryDetailModal: false,
      selectedCategoryId: null,
    };
  },
  mounted() {
    this.fetchCategories();
  },
  methods: {
    fetchCategories() {
      api.get('/api/v1/categories')
        .then(response => {
          this.categories = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    openCategoryAddModal() {
      this.showCategoryAddModal = true;
    },
    openCategoryDetailModal(categoryId) {
      this.selectedCategoryId = categoryId;
      this.showCategoryDetailModal = true;
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 40px auto;
  width: 100%;
  padding: 0 16px;
}

.category-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 2rem;
}

.category-item {
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.06);
  transition: transform 0.15s, box-shadow 0.15s;
}

.category-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 14px rgba(0, 0, 0, 0.1);
}

.category-banner {
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.category-details {
  padding: 1rem;
}

.category-name {
  font-size: 1.5rem;
  margin-bottom: 0.3rem;
}

.category-due {
  font-size: 0.95rem;
  color: var(--todo-primary);
  font-weight: 600;
}

.category-desc {
  font-size: 0.95rem;
  color: #777;
}

.add-category-button {
  display: block;
  margin-top: 2rem;
  margin-bottom: 2rem;
  padding: 0.7rem;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  background-color: var(--todo-primary);
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;

  width: 60%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.add-category-button:hover {
  background-color: var(--todo-primary-dark);
}
</style>
