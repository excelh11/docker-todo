export const PRIORITIES = [
  { value: 'HIGH', title: '높음', color: '#E53935' },
  { value: 'MEDIUM', title: '보통', color: '#FB8C00' },
  { value: 'LOW', title: '낮음', color: '#43A047' },
];

export function priorityInfo(value) {
  return PRIORITIES.find((p) => p.value === value) || PRIORITIES[1];
}

export const CATEGORY_COLORS = [
  '#4F6BED', '#7C4DFF', '#00A86B', '#FF9800',
  '#E91E63', '#F44336', '#009688', '#3F51B5',
];

export const CATEGORY_ICONS = [
  { value: 'mdi-briefcase-outline', title: '업무' },
  { value: 'mdi-book-open-variant', title: '공부' },
  { value: 'mdi-run', title: '운동' },
  { value: 'mdi-broom', title: '집안일' },
  { value: 'mdi-cart-outline', title: '장보기' },
  { value: 'mdi-heart-pulse', title: '건강' },
  { value: 'mdi-palette-outline', title: '취미' },
  { value: 'mdi-calendar-account', title: '약속' },
  { value: 'mdi-checkbox-marked-circle-outline', title: '기본' },
];
