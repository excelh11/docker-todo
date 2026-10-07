// 브라우저(한국 시간 등) 기준의 'YYYY-MM-DD' 문자열을 만든다.
// toISOString()은 UTC 기준이라 아침 시간대에 날짜가 하루 밀릴 수 있어서 직접 만든다.
export function toLocalDateString(date = new Date()) {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, '0');
  const d = String(date.getDate()).padStart(2, '0');
  return `${y}-${m}-${d}`;
}

export function addDays(days, from = new Date()) {
  const date = new Date(from);
  date.setDate(date.getDate() + Number(days || 0));
  return toLocalDateString(date);
}

// 마감일까지 남은 날짜를 사람이 읽기 쉬운 문구로 바꾼다.
export function dueLabel(dueDate) {
  if (!dueDate) return '마감일 없음';
  const today = new Date(toLocalDateString());
  const due = new Date(dueDate);
  const diff = Math.round((due - today) / (1000 * 60 * 60 * 24));
  if (diff === 0) return '오늘 마감';
  if (diff > 0) return `D-${diff}`;
  return `${-diff}일 지남`;
}
