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

frontend-build:
	docker-compose build frontend

frontend-up:
	docker-compose up -d frontend

frontend-restart:
	docker-compose stop frontend
	docker-compose build frontend
	docker-compose up -d frontend

frontend-cache:
	docker-compose build --no-cache frontend
	docker-compose up -d --force-recreate frontend