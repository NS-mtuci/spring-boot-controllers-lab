# Spring Boot Controllers Lab

Тема проекта: сервис авиаперевозок.

## Стек

- Java 21
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Maven Wrapper

## Основные сущности

- `Aircraft` - самолет с моделью, уникальным бортовым номером и количеством мест.
- `Flight` - рейс с уникальным номером, маршрутом, временем вылета, статусом и связанным самолетом.
- `Booking` - бронирование пассажира на рейс с номером места и статусом.

Связи:

- один самолет может выполнять много рейсов;
- один рейс может иметь много бронирований;
- место в рамках одного рейса уникально.

## Конфигурация БД

Чувствительные данные не хранятся в `application.properties`; приложение читает их из env.

Пример переменных есть в `.env.example`:

```properties
DB_URL=jdbc:postgresql://localhost:5432/airline_lab
DB_USERNAME=postgres
DB_PASSWORD=change_me
DDL_AUTO=update
```

Если установлен Docker, PostgreSQL можно поднять так:

```powershell
copy .env.example .env
docker compose up -d
```

Для обычной локальной установки PostgreSQL нужно создать БД `airline_lab` и задать переменные окружения `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

## Запуск

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение запускается на `http://localhost:8080`.

При первом запуске `DataSeeder` добавляет тестовые записи: несколько самолетов, рейсов и бронирований.

## CRUD операции

Для каждой сущности реализованы создание, получение, изменение и удаление:

- `POST /api/aircraft`, `GET /api/aircraft`, `GET /api/aircraft/{id}`, `PUT /api/aircraft/{id}`, `DELETE /api/aircraft/{id}`.
- `POST /api/flights`, `GET /api/flights`, `GET /api/flights/{id}`, `PUT /api/flights/{id}`, `DELETE /api/flights/{id}`.
- `POST /api/bookings`, `GET /api/bookings`, `GET /api/bookings/{id}`, `PUT /api/bookings/{id}`, `DELETE /api/bookings/{id}`.

## Бизнес-операции

- `GET /api/airline/operations/flights/search` - поиск рейсов по маршруту и дате.
- `POST /api/airline/operations/bookings` - бронирование места на рейс.
- `POST /api/airline/operations/bookings/{id}/cancel` - отмена бронирования.
- `POST /api/airline/operations/bookings/{id}/check-in` - регистрация пассажира на рейс.
- `GET /api/airline/operations/flights/{id}/bookings` - получение бронирований конкретного рейса.
- `POST /api/airline/operations/flights/{id}/status` - изменение статуса рейса.
- `POST /api/airline/operations/flights/{id}/aircraft` - назначение самолета на рейс.

Операции, которые меняют связанные таблицы, выполняются в транзакциях на уровне service-слоя.

## Коллекция запросов

Файл с запросами для проверки:

```text
requests/lab3-airline.http
```

В нем есть CRUD-запросы по каждой сущности и сценарии бизнес-операций.

## Схема таблиц

DDL-описание таблиц и ограничений находится в:

```text
docs/database-schema.sql
```

В приложении таблицы создает Hibernate по JPA-сущностям.

## Проверка

```powershell
.\mvnw.cmd test
```
