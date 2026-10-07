#도커 이미지 빌드 및 푸시
docker build -t {TYPE_YOUR_REPOSITORY}/todo-backend:1.0.0 . --no-cache
docker push {TYPE_YOUR_REPOSITORY}/todo-backend:1.0.0

# 컨테이너 생성 (윈도우 cmd or Powershell 사용)
docker run -d -p 8080:8080 -e DB_URL=todo-postgres --network todo-network --name todo-backend {TYPE_YOUR_REPOSITORY}/todo-backend:1.0.0

# 로그 확인
docker logs -f todo-backend
