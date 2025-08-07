# Makefile para automatizar tareas con Docker

.PHONY: build up down restart logs frontend-build frontend-up frontend-restart

build:
	docker-compose build

up:
	docker-compose up -d

down:
	docker-compose down

restart:
	docker-compose down
	docker-compose up -d

logs:
	docker-compose logs -f

reset:
	cd backend && ./mvnw clean package && cd ..
	docker-compose down
	docker-compose build --no-cache
	docker-compose up -d