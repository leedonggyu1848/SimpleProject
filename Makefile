.PHONY: all up down build clean fclean

COMPOSE_FILE = docker-compose.yml
PROJECT_NAME = simple-app

all: up

up:
	@echo "Starting Docker Compose..."
	docker compose up -d --build
	@echo "Docker Compose started."

down:
	@echo "Stopping Docker Compose..."
	docker compose down || true
	@echo "Docker Compose stopped."

clean: down
	@echo "Cleaning up Docker images, containers, volume."
	docker compose -f $(COMPOSE_FILE) rm -fsv
	docker images -f "label=com.docker.compose.project=$(PROJECT_NAME)" -q | xargs -r docker rmi -f || true
	docker volume ls -q -f "label=com.docker.compose.project=$(PROJECT_NAME)" | xargs -r docker volume rm || true
	docker system prune -a --volumes --force

fclean: clean
	rm -rf ./db/data
	rm -rf ./db/mongo-data
	rm -rf ./kafka/kafka-data
