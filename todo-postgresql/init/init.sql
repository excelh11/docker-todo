-- Users table creation
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(1) NOT NULL,
    birth_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- Categories table creation (할 일 카테고리)
CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL,
    category_desc VARCHAR(255),
    color VARCHAR(7) NOT NULL DEFAULT '#4F6BED',
    icon VARCHAR(50) NOT NULL DEFAULT 'mdi-checkbox-marked-circle-outline',
    default_due_days INT NOT NULL DEFAULT 7,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- Todos table creation (사용자의 할 일)
CREATE TABLE todos (
    todo_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    category_id INT REFERENCES categories(category_id) ON DELETE SET NULL,
    title VARCHAR(100) NOT NULL,
    priority VARCHAR(10) NOT NULL DEFAULT 'MEDIUM' CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
    due_date DATE,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- Todo_Logs table creation (할 일 진행 기록)
CREATE TABLE todo_logs (
    todo_log_id SERIAL PRIMARY KEY,
    todo_id INT NOT NULL REFERENCES todos(todo_id) ON DELETE CASCADE,
    log_date DATE NOT NULL,
    note VARCHAR(255),
    done BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- Users 테이블에 데이터 삽입
-- $2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG = password123
-- $2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC = password456
-- $2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q = password789
INSERT INTO users (name, email, password, gender, birth_date) VALUES
('John', 'john123@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'M', '1988-05-01'),
('Jane', 'jane456@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'F', '1995-08-15'),
('Peter', 'peter789@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'M', '1981-12-25'),
('Susan', 'susan321@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'F', '1990-06-02'),
('David', 'david654@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'M', '1992-03-11'),
('Judy', 'judy987@qmail.com', '$2a$10$vYR4pPQqR/oZcUDZfXrahecEejQHY0kLkDB5s.FctPRMcEMh1PYhG', 'F', '1983-10-19'),
('Timothy', 'timothy012@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'M', '1996-11-30'),
('Lisa', 'lisa345@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'F', '1988-07-20'),
('Steve', 'steve678@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'M', '1977-01-05'),
('Emily', 'emily321@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'F', '1994-09-23'),
('Henry', 'henry654@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'M', '1989-06-14'),
('Grace', 'grace987@qmail.com', '$2a$10$Vqx3VUuB8gy9NvtKHQARWOOYB2wG4wV2WXy1sdQHIoY8TivSHZ3sC', 'F', '1982-04-28'),
('Mike', 'mike012@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'M', '1998-02-08'),
('Sophie', 'sophie345@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'F', '1991-12-12'),
('Daniel', 'daniel678@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'M', '1980-07-01'),
('Olivia', 'olivia321@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'F', '1992-05-28'),
('Jackson', 'jackson654@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'M', '1985-02-18'),
('Amelia', 'amelia987@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'F', '1995-01-10'),
('Tom', 'tom012@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'M', '1987-08-03'),
('Sarah', 'sarah345@qmail.com', '$2a$10$ke3IM6noeWfQtX6POjZHl.49gSolYbqfrSTIn8sOQubdwjP2IT94q', 'F', '1984-03-09');

-- Categories 테이블에 데이터 삽입
INSERT INTO categories (category_name, category_desc, color, icon, default_due_days) VALUES
('업무', '회의 준비, 보고서 작성처럼 회사·학교에서 해야 하는 일', '#4F6BED', 'mdi-briefcase-outline', 3),
('공부', '강의 수강, 과제, 자격증 준비 등 배우는 일', '#7C4DFF', 'mdi-book-open-variant', 7),
('운동', '헬스, 러닝, 스트레칭 등 몸을 움직이는 일', '#00A86B', 'mdi-run', 1),
('집안일', '청소, 빨래, 분리수거 같은 생활 관리', '#FF9800', 'mdi-broom', 2),
('장보기', '마트·온라인으로 사야 할 물건', '#E91E63', 'mdi-cart-outline', 2),
('건강', '병원 예약, 영양제, 수면 관리', '#F44336', 'mdi-heart-pulse', 7),
('취미', '독서, 그림, 악기 등 나를 위한 시간', '#009688', 'mdi-palette-outline', 14),
('약속', '친구·가족과의 만남과 기념일 챙기기', '#3F51B5', 'mdi-calendar-account', 5);

-- Todos 테이블에 데이터 삽입 (마감일은 DB가 처음 만들어지는 날 기준으로 계산)
INSERT INTO todos (user_id, category_id, title, priority, due_date, completed) VALUES
(1, 1, '주간 회의 자료 만들기', 'HIGH', CURRENT_DATE + 1, FALSE),
(1, 3, '저녁 30분 러닝', 'MEDIUM', CURRENT_DATE + 0, FALSE),
(1, 5, '우유, 계란 사기', 'LOW', CURRENT_DATE - 1, FALSE),
(2, 2, 'Spring Boot 강의 3장 듣기', 'HIGH', CURRENT_DATE + 2, FALSE),
(2, 4, '주말 대청소', 'MEDIUM', CURRENT_DATE + 3, FALSE),
(2, 7, '소설책 50쪽 읽기', 'LOW', CURRENT_DATE + 5, TRUE),
(3, 1, '분기 보고서 초안 작성', 'HIGH', CURRENT_DATE - 2, FALSE),
(3, 6, '치과 예약하기', 'MEDIUM', CURRENT_DATE + 4, FALSE),
(4, 2, '정보처리기사 기출 풀기', 'HIGH', CURRENT_DATE + 6, FALSE),
(4, 3, '스트레칭 15분', 'LOW', CURRENT_DATE + 0, TRUE),
(5, 8, '엄마 생신 선물 고르기', 'HIGH', CURRENT_DATE + 2, FALSE),
(5, 5, '세제 주문하기', 'LOW', CURRENT_DATE + 1, FALSE),
(6, 4, '분리수거 하기', 'MEDIUM', CURRENT_DATE - 1, FALSE),
(6, 6, '비타민 챙겨 먹기', 'LOW', CURRENT_DATE + 0, TRUE),
(7, 1, '고객사 메일 회신', 'HIGH', CURRENT_DATE + 0, FALSE),
(7, 7, '기타 코드 연습', 'LOW', CURRENT_DATE + 10, FALSE),
(8, 2, 'Vue 컴포넌트 과제 제출', 'HIGH', CURRENT_DATE + 1, FALSE),
(8, 3, '헬스장 하체 운동', 'MEDIUM', CURRENT_DATE + 2, FALSE),
(8, 4, '빨래 개기', 'LOW', CURRENT_DATE - 3, FALSE),
(9, 8, '동창 모임 장소 예약', 'MEDIUM', CURRENT_DATE + 7, FALSE),
(9, 1, '프로젝트 일정표 정리', 'HIGH', CURRENT_DATE + 3, TRUE),
(10, 7, '수채화 한 장 그리기', 'LOW', CURRENT_DATE + 12, FALSE),
(11, 6, '건강검진 결과 확인', 'MEDIUM', CURRENT_DATE + 2, FALSE),
(12, 5, '캠핑 준비물 사기', 'MEDIUM', CURRENT_DATE + 4, FALSE),
(13, 2, '영어 단어 30개 외우기', 'MEDIUM', CURRENT_DATE + 0, FALSE),
(14, 3, '요가 클래스 참여', 'LOW', CURRENT_DATE + 1, FALSE),
(15, 1, '팀 회고 정리하기', 'HIGH', CURRENT_DATE - 1, FALSE);

-- Todo_Logs 테이블에 데이터 삽입
INSERT INTO todo_logs (todo_id, log_date, note, done) VALUES
(1, CURRENT_DATE - 2, '목차와 핵심 지표 정리', FALSE),
(1, CURRENT_DATE - 1, '그래프 초안 완성, 내일 마무리 예정', FALSE),
(2, CURRENT_DATE - 1, '5km 중 3km까지 뛰었습니다', FALSE),
(4, CURRENT_DATE - 3, '1장, 2장 복습 완료', FALSE),
(4, CURRENT_DATE - 1, '3장 절반까지 수강', FALSE),
(6, CURRENT_DATE - 2, '40쪽 읽음, 재미있어지기 시작', FALSE),
(6, CURRENT_DATE - 0, '50쪽 다 읽었습니다', TRUE),
(7, CURRENT_DATE - 3, '자료 수집 완료', FALSE),
(7, CURRENT_DATE - 1, '초안 70% 작성, 마감이 지나서 서두르는 중', FALSE),
(9, CURRENT_DATE - 2, '2023년 1회차 풀이 (68점)', FALSE),
(9, CURRENT_DATE - 0, '틀린 문제 오답노트 작성', FALSE),
(10, CURRENT_DATE - 0, '아침 스트레칭 완료', TRUE),
(11, CURRENT_DATE - 1, '후보 3개로 압축', FALSE),
(13, CURRENT_DATE - 2, '종이·플라스틱 분리해 둠', FALSE),
(14, CURRENT_DATE - 0, '오늘 분량 먹음', TRUE),
(15, CURRENT_DATE - 0, '초안 작성 중', FALSE),
(17, CURRENT_DATE - 1, 'props/emit 부분 구현', FALSE),
(17, CURRENT_DATE - 0, '라우터 연결 남음', FALSE),
(18, CURRENT_DATE - 1, '스쿼트 5세트', FALSE),
(19, CURRENT_DATE - 4, '세탁은 끝, 개는 것만 남음', FALSE),
(21, CURRENT_DATE - 3, '마일스톤 정리', FALSE),
(21, CURRENT_DATE - 1, '팀원 검토 반영하고 공유 완료', TRUE),
(23, CURRENT_DATE - 1, '병원 앱에서 결과 조회 예약', FALSE),
(25, CURRENT_DATE - 0, '15개 외움', FALSE),
(27, CURRENT_DATE - 2, '회고 템플릿 준비', FALSE);
