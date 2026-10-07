<template>
  <v-dialog v-model="isDialogOpen" persistent max-width="600px">
    <v-card>
      <v-card-title class="text-center">
        <h3 style="margin-top: 20px;">카테고리 추가</h3>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form" @submit.prevent="addCategory">
            <v-row>
              <v-col cols="8">
                <v-text-field
                  v-model="categoryName"
                  label="카테고리명 (예: 업무, 공부)"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model.number="defaultDueDays"
                  label="기본 마감(일)"
                  type="number"
                  min="0"
                  :rules="[rules.nonNegative]"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-textarea
                  v-model="categoryDesc"
                  label="설명"
                  rows="2"
                ></v-textarea>
              </v-col>
              <v-col cols="12" class="text-left">
                <p class="field-label">색상</p>
                <div class="swatches">
                  <button
                    v-for="c in colors"
                    :key="c"
                    type="button"
                    class="swatch"
                    :class="{ selected: c === color }"
                    :style="{ backgroundColor: c }"
                    @click="color = c"
                  ></button>
                </div>
              </v-col>
              <v-col cols="12">
                <v-select
                  v-model="icon"
                  :items="icons"
                  item-title="title"
                  item-value="value"
                  label="아이콘"
                >
                  <template v-slot:selection="{ item }">
                    <v-icon :icon="item.value" class="mr-2"></v-icon>{{ item.title }}
                  </template>
                  <template v-slot:item="{ props, item }">
                    <v-list-item v-bind="props" :prepend-icon="item.value"></v-list-item>
                  </template>
                </v-select>
              </v-col>
              <v-col cols="12">
                <div class="preview" :style="{ backgroundColor: color }">
                  <v-icon :icon="icon" size="40" color="white"></v-icon>
                  <span>{{ categoryName || '미리보기' }}</span>
                </div>
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>

      <v-row align="center" justify="center">
        <v-col cols="5">
          <v-btn block size="large" color="#999999" @click="close">취소</v-btn>
        </v-col>
        <v-col cols="5">
          <v-btn block size="large" color="primary" @click="addCategory">추가</v-btn>
        </v-col>
      </v-row>
      <v-card-actions>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import api from '@/api/api.js';
import { CATEGORY_COLORS, CATEGORY_ICONS } from '@/utils/todo';

const initialForm = () => ({
  categoryName: "",
  categoryDesc: "",
  color: CATEGORY_COLORS[0],
  icon: CATEGORY_ICONS[0].value,
  defaultDueDays: 3,
});

export default {
  name: 'CategoryAddModal',
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
  },
  emits: ['update:isOpen', 'added-category'],
  data() {
    return {
      isDialogOpen: false,
      colors: CATEGORY_COLORS,
      icons: CATEGORY_ICONS,
      rules: {
        required: (v) => !!(v && String(v).trim()) || '필수 입력입니다.',
        nonNegative: (v) => (v !== '' && Number(v) >= 0) || '0 이상의 숫자를 입력하세요.',
      },
      ...initialForm(),
    };
  },
  watch: {
    isOpen(newVal) {
      this.isDialogOpen = newVal;
    }
  },
  methods: {
    close() {
      Object.assign(this.$data, initialForm());
      this.$emit("update:isOpen", false);
    },
    async addCategory() {
      const { valid } = await this.$refs.form.validate();
      if (!valid) return;
      try {
        await api.post("/api/v1/categories", {
          categoryName: this.categoryName,
          categoryDesc: this.categoryDesc,
          color: this.color,
          icon: this.icon,
          defaultDueDays: Number(this.defaultDueDays),
        });
        this.$emit("added-category");
        this.close();
      } catch (error) {
        console.error(error);
        this.$store.dispatch("showPopup", {
          message: "카테고리를 추가하지 못했습니다.",
          status: "error",
          showingSecond: 1500,
        });
      }
    },
  },
};
</script>

<style scoped>
.v-btn {
  color: white;
}

.field-label {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 6px;
}

.swatches {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.swatch {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 3px solid transparent;
  cursor: pointer;
}

.swatch.selected {
  border-color: #333;
}

.preview {
  display: flex;
  align-items: center;
  gap: 12px;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  border-radius: 8px;
  padding: 16px;
}
</style>
