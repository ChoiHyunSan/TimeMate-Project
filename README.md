# TimeMate 프로젝트 기획서

## 1. 프로젝트 개요
- 프로젝트명: TimeMate (타임메이트)
- 개발 기간: 7일
- 담당자: FE/BE 개발자 1명
- 목적: 효율적인 개인 일정 관리 및 업무 생산성 향상

## 2. 핵심 기능 요구사항

### 2.1 일정 관리 (우선순위: High)
- 일정 등록/수정/삭제 기능
  - 제목, 설명, 시작일시, 종료일시, 장소, 카테고리 입력
  - 반복 일정 설정 (매일, 매주, 매월)
  - 알림 설정 (시작 시간 기준 5분/15분/30분/1시간 전)
- 드래그 앤 드롭으로 일정 시간 수정
- 일정 상세 정보 팝업

### 2.2 캘린더 뷰 (우선순위: High)
- 월간/주간/일간 뷰 제공
- 카테고리별 색상 구분
- 카테고리 필터링 기능
- 미니 캘린더로 날짜 빠른 이동

### 2.3 알림 기능 (우선순위: Medium)
- 브라우저 알림으로 일정 시작 전 알림
- 알림 히스토리 관리
- 알림 ON/OFF 설정

## 3. 데이터베이스 설계

### 3.1 Schedule 테이블
```sql
CREATE TABLE schedules (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME NOT NULL,
    location VARCHAR(200),
    category_id BIGINT,
    repeat_type VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 3.2 Category 테이블
```sql
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(7) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 3.3 Notification 테이블
```sql
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    notification_time DATETIME NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## 4. API 설계

### 4.1 일정 관련 API
- GET /api/schedules
  - Query Parameters: startDate, endDate, categoryId
  - Response: 기간 내 일정 목록
- POST /api/schedules
  - Request Body: 일정 정보
  - Response: 생성된 일정 정보
- PUT /api/schedules/{id}
  - Request Body: 수정할 일정 정보
  - Response: 수정된 일정 정보
- DELETE /api/schedules/{id}

### 4.2 카테고리 관련 API
- GET /api/categories
- POST /api/categories
- PUT /api/categories/{id}
- DELETE /api/categories/{id}

### 4.3 알림 관련 API
- GET /api/notifications
- PUT /api/notifications/{id}/read
- PUT /api/notifications/settings

## 5. 화면 설계

### 5.1 메인 화면 (Calendar.jsx)
- FullCalendar 컴포넌트 활용
- 좌측: 미니 캘린더, 카테고리 필터
- 중앙: 메인 캘린더
- 우측: 선택된 날짜 일정 목록

### 5.2 일정 등록/수정 모달 (ScheduleModal.jsx)
- 일정 입력 폼
- 카테고리 선택 드롭다운
- 날짜/시간 선택 (react-datepicker 활용)
- 반복 설정 옵션
- 알림 설정 옵션

### 5.3 알림 컴포넌트 (Notification.jsx)
- 알림 팝업 표시
- 알림 목록 표시
- 알림 설정 관리

## 6. 기술 스택 상세

### 6.1 Frontend
- React 18
- React Router 6
- FullCalendar
- React Query
- Tailwind CSS
- React DatePicker
- React Icons

### 6.2 Backend
- Spring Boot 3.0
- Spring Data JPA
- Spring Security
- Lombok
- MapStruct

### 6.3 Database
- MySQL 8.0

## 7. 개발 일정

### Day 1-2
- 프로젝트 환경 설정
- DB 설계 및 구현
- 기본 CRUD API 구현

### Day 3-4
- 캘린더 뷰 구현
- 일정 등록/수정 기능 구현
- 드래그 앤 드롭 기능 구현

### Day 5-6
- 알림 기능 구현
- 카테고리 관리 기능 구현
- 반복 일정 기능 구현

### Day 7
- 전체 기능 테스트
- 버그 수정
- 코드 리팩토링

## 8. 참고사항
- 모바일 대응은 2차 개발에서 진행
- 다중 사용자 지원은 추후 확장
- 데이터 백업 기능은 추후 구현 예정
