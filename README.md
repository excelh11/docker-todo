# TODO

카테고리별로 할 일을 관리하고 진행 기록을 남기는 할 일(Todo) 관리 웹 서비스입니다. 프론트엔드 · 백엔드 · DB 3계층으로 구성되어 있으며 Docker Compose로 한 번에 실행할 수 있습니다.

```
todo-main/
├── todo-frontend/     Vue 3 + Vuetify 3 (nginx로 서비스, /api 는 백엔드로 프록시)
├── todo-backend/      Spring Boot 3.3 + Gradle 8.14 (Wrapper 포함) + Java 17
├── todo-postgresql/   PostgreSQL 13 + 초기 스키마/샘플 데이터(init/init.sql)
├── docker-compose.yml
└── .github/workflows/ 각 이미지 빌드 & 푸시
```

## 실행

```bash
docker compose up -d --build
```

브라우저에서 http://localhost 접속 → 샘플 계정으로 로그인

| 이메일 | 비밀번호 |
|---|---|
| john123@qmail.com | password123 |

> `init.sql` 은 DB 볼륨(`mydata`)이 처음 만들어질 때만 실행됩니다. 샘플 데이터를 처음 상태로 되돌리려면 `docker compose down -v` 로 볼륨을 지운 뒤 다시 실행하세요.

백엔드만 로컬에서 실행할 때 (PostgreSQL이 localhost:5432 에 떠 있어야 함):

```bash
cd todo-backend
./gradlew bootRun
```

## 데이터 구조

| 테이블 | 설명 |
|---|---|
| users | 사용자 |
| categories | 할 일 카테고리: 이름, 설명, 색상, 아이콘, 기본 마감 기한(일) |
| todos | 사용자의 할 일: 카테고리, 제목, 중요도(HIGH/MEDIUM/LOW), 마감일, 완료 여부 |
| todo_logs | 할 일 진행 기록: 날짜, 메모, 이 기록으로 완료했는지(done) |

할 일 응답의 `overdue` 값은 마감일이 지났는데 아직 완료하지 않은 경우 `true` 입니다.

## API

| 메서드 | 경로 | 설명 |
|---|---|---|
| POST | /api/v1/users/login | 로그인 |
| GET/POST | /api/v1/users | 사용자 목록 / 가입 |
| GET/PUT/DELETE | /api/v1/users/{userId} | 사용자 조회 / 수정 / 삭제 |
| GET/POST | /api/v1/categories | 카테고리 목록 / 추가 |
| GET/PUT/DELETE | /api/v1/categories/{categoryId} | 카테고리 조회 / 수정 / 삭제 |
| GET | /api/v1/todos/user/{userId} | 사용자의 할 일 목록 (미완료 → 마감일 순) |
| POST | /api/v1/todos | 할 일 추가 (마감일을 비우면 카테고리 기본 기한으로 자동 설정) |
| GET/PUT/DELETE | /api/v1/todos/{todoId} | 할 일 조회 / 수정 / 삭제 |
| PATCH | /api/v1/todos/{todoId}/toggle | 완료 ↔ 미완료 전환 |
| GET/POST | /api/v1/todo-logs | 전체 기록 / 기록 추가 (done=true 면 할 일도 완료 처리) |
| GET | /api/v1/todo-logs/user/{userId} | 사용자의 기록 (최신 순) |
| GET | /api/v1/todo-logs/recent/user/{userId} | 최근 기록 4건 |
| GET | /api/v1/todo-logs/todos/{todoId} | 특정 할 일의 기록 |
| GET/PUT/DELETE | /api/v1/todo-logs/{todoLogId} | 기록 조회 / 수정 / 삭제 |

요청 예시는 `todo-backend/src/main/resources/postman/test.json`(Postman)과 `todo-backend/src/test/api.http` 에 있습니다.

## 화면

| 메뉴 | 내용 |
|---|---|
| Home | 진행률, 내 할 일 목록(체크로 완료 처리, 마감 지난 일 강조, 진행 중/완료/전체 필터), 최근 기록 |
| Categories | 카테고리 카드 목록 · 추가(색상/아이콘 선택) · 상세에서 삭제 및 **할 일 추가** |
| Logs | 할 일별 진행 기록 남기기 · 삭제 (완료로 기록하면 할 일도 완료) |
| Setting | 사용자 정보 확인 · 수정 |
