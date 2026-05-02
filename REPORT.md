# Отчет по лабораторным работам

## Лабораторная работа 1

### Задание

Создать Java-проект на Spring Boot версии 3.5.5 или выше, опубликовать его на GitHub и добавить простые контроллеры для проверки работы HTTP-запросов.

### Создание проекта

Проект создан на Spring Boot `3.5.5`, Java `21`, сборка выполняется через Maven Wrapper.

Скриншот 1: структура проекта после создания.

![Структура проекта](screenshots/01-project-structure.png)

### Реализованные контроллеры

В первой лабораторной работе добавлены контроллеры:

- `HomeController` - стартовый endpoint `/` со списком доступных endpoint-ов.
- `HelloController` - примеры `@RequestParam`, `@PathVariable` и простой валидации.
- `StudentController` - примеры `GET` и `POST` запросов с JSON body.

### Проверка GET-запросов

Пример запроса:

```http
GET http://localhost:8080/api/hello?name=Fedor
```

Скриншот 2: результат GET-запроса `/api/hello`.

![GET hello](screenshots/02-get-hello.png)

Пример запроса:

```http
GET http://localhost:8080/api/students
```

Скриншот 3: результат GET-запроса `/api/students`.

![GET students](screenshots/03-get-students.png)

### Проверка POST-запроса

Пример запроса:

```http
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "fullName": "Petr Ivanov",
  "groupName": "IVBO-03-25"
}
```

Скриншот 4: результат POST-запроса `/api/students`.

![POST student](screenshots/04-post-student.png)

### Публикация на GitHub

Ссылка на репозиторий:

https://github.com/NS-mtuci/spring-boot-controllers-lab

Скриншот 5: опубликованный репозиторий на GitHub.

![GitHub repository](screenshots/05-github-repository.png)

## Лабораторная работа 2

### Задание

Выбрать тему из предложенных вариантов, реализовать для каждой сущности методы контроллера для операций создания, получения, удаления и изменения, а также продумать будущие функции сервиса.

Выбранная тема: авиаперевозки.

### Сущности предметной области

В сервисе авиаперевозок выделены сущности:

- `Aircraft` - самолет, который используется для выполнения рейсов.
- `Flight` - рейс между городами с датой и статусом.
- `Booking` - бронирование места пассажиром на рейс.

### CRUD для самолетов

Endpoint-ы:

- `POST /api/aircraft` - создание самолета.
- `GET /api/aircraft` - получение списка самолетов.
- `GET /api/aircraft/{id}` - получение самолета по идентификатору.
- `PUT /api/aircraft/{id}` - изменение самолета.
- `DELETE /api/aircraft/{id}` - удаление самолета.

Пример тела запроса:

```json
{
  "model": "Airbus A321",
  "tailNumber": "RA-73222",
  "seats": 220
}
```

Скриншот 6: создание самолета.

![POST aircraft](screenshots/06-post-aircraft.png)

Скриншот 7: получение списка самолетов.

![GET aircraft](screenshots/07-get-aircraft.png)

### CRUD для рейсов

Endpoint-ы:

- `POST /api/flights` - создание рейса.
- `GET /api/flights` - получение списка рейсов.
- `GET /api/flights/{id}` - получение рейса по идентификатору.
- `PUT /api/flights/{id}` - изменение рейса.
- `DELETE /api/flights/{id}` - удаление рейса.

Пример тела запроса:

```json
{
  "flightNumber": "SU300",
  "departureCity": "Moscow",
  "arrivalCity": "Sochi",
  "departureTime": "2026-06-10T12:30:00",
  "aircraftId": 1,
  "status": "SCHEDULED"
}
```

Скриншот 8: создание рейса.

![POST flight](screenshots/08-post-flight.png)

Скриншот 9: изменение рейса.

![PUT flight](screenshots/09-put-flight.png)

### CRUD для бронирований

Endpoint-ы:

- `POST /api/bookings` - создание бронирования.
- `GET /api/bookings` - получение списка бронирований.
- `GET /api/bookings/{id}` - получение бронирования по идентификатору.
- `PUT /api/bookings/{id}` - изменение бронирования.
- `DELETE /api/bookings/{id}` - удаление бронирования.

Пример тела запроса:

```json
{
  "passengerName": "Petr Ivanov",
  "flightId": 1,
  "seatNumber": "14B",
  "status": "CONFIRMED"
}
```

Скриншот 10: создание бронирования.

![POST booking](screenshots/10-post-booking.png)

Скриншот 11: удаление бронирования.

![DELETE booking](screenshots/11-delete-booking.png)

### Будущие функции сервиса

В дальнейшем сервис авиаперевозок может предоставлять функции:

- поиск рейсов по маршруту, дате, цене и доступным местам;
- регистрация пассажиров и хранение их данных;
- бронирование, оплата и отмена билетов;
- онлайн-регистрация на рейс и выбор места;
- управление самолетами, расписанием рейсов и статусами рейсов;
- формирование посадочных талонов и отчетов по бронированиям;
- уведомление пассажиров о задержках и отменах рейсов.

## Лабораторная работа 3

### Задание

Добавить реляционную базу данных, создать таблицы для сущностей предметной области, настроить связи и ограничения, заполнить тестовые данные, перевести CRUD на работу с БД и добавить 5 бизнес-операций.

### База данных

В проект добавлена поддержка PostgreSQL через Spring Data JPA.

Чувствительные параметры подключения вынесены в переменные окружения:

```properties
DB_URL=jdbc:postgresql://localhost:5432/airline_lab
DB_USERNAME=postgres
DB_PASSWORD=change_me
DDL_AUTO=update
```

Скриншот 12: запущенная база данных PostgreSQL.

![PostgreSQL](screenshots/12-postgresql.png)

### Таблицы и связи

Созданы таблицы:

- `aircraft` - самолеты;
- `flights` - рейсы;
- `bookings` - бронирования.

Связи:

- `flights.aircraft_id` связан с `aircraft.id`;
- `bookings.flight_id` связан с `flights.id`.

Ограничения:

- `aircraft.tail_number` уникален;
- `flights.flight_number` уникален;
- пара `bookings.flight_id` и `bookings.seat_number` уникальна.

DDL-схема вынесена в файл `docs/database-schema.sql`.

Скриншот 13: таблицы в базе данных.

![Database tables](screenshots/13-database-tables.png)

### Тестовые данные

При первом запуске приложения `DataSeeder` добавляет несколько самолетов, рейсов и бронирований, чтобы сразу можно было выполнять запросы.

Скриншот 14: тестовые данные в таблицах.

![Seed data](screenshots/14-seed-data.png)

### Перевод CRUD на БД

Контроллеры лабораторной работы 2 теперь работают не с in-memory списками, а через service-слой и Spring Data JPA repositories:

- `AircraftController` использует `AircraftService` и `AircraftRepository`;
- `FlightController` использует `FlightService` и `FlightRepository`;
- `BookingController` использует `BookingService` и `BookingRepository`.

Скриншот 15: GET-запрос к `/api/flights` возвращает данные из БД.

![GET flights from DB](screenshots/15-get-flights-db.png)

### Бизнес-операции

Добавлены операции, которые не являются обычным CRUD:

- поиск рейсов по маршруту и дате: `GET /api/airline/operations/flights/search`;
- бронирование места: `POST /api/airline/operations/bookings`;
- отмена бронирования: `POST /api/airline/operations/bookings/{id}/cancel`;
- регистрация пассажира на рейс: `POST /api/airline/operations/bookings/{id}/check-in`;
- просмотр всех бронирований рейса: `GET /api/airline/operations/flights/{id}/bookings`;
- изменение статуса рейса: `POST /api/airline/operations/flights/{id}/status`;
- назначение самолета на рейс: `POST /api/airline/operations/flights/{id}/aircraft`.

Операции изменения данных выполняются в транзакциях в service-слое.

Скриншот 16: бронирование места через бизнес-операцию.

![Book seat](screenshots/16-book-seat.png)

Скриншот 17: регистрация пассажира на рейс.

![Check in](screenshots/17-check-in.png)

### Коллекция запросов

Коллекция запросов для проверки находится в файле `requests/lab3-airline.http`.

В коллекции есть:

- CRUD-запросы для самолетов;
- CRUD-запросы для рейсов;
- CRUD-запросы для бронирований;
- запросы для всех бизнес-операций.
