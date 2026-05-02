# Spring Boot Controllers Lab

Лабораторный проект на Spring Boot с примерами REST-контроллеров.

## Используемые технологии

- Java 21
- Spring Boot 3.5.5
- Spring Web
- Spring Validation
- Maven Wrapper

## Тема лабораторной работы 2

Тема: авиаперевозки.

Сущности предметной области:

- Aircraft - самолет.
- Flight - рейс.
- Booking - бронирование.

## Запуск

```bash
./mvnw spring-boot:run
```

На Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение запускается на `http://localhost:8080`.

## Endpoint-ы лабораторной работы 1

| Method | URL | Description |
| --- | --- | --- |
| `GET` | `/` | Application info and endpoint list |
| `GET` | `/api/hello?name=Fedor` | Query parameter example |
| `GET` | `/api/numbers/5` | Path variable and validation example |
| `GET` | `/api/students` | Returns all demo students |
| `GET` | `/api/students/1` | Returns one student by id |
| `POST` | `/api/students` | Creates a student from JSON body |

## CRUD endpoint-ы лабораторной работы 2

### Aircraft

| Method | URL | Operation |
| --- | --- | --- |
| `POST` | `/api/aircraft` | Create aircraft |
| `GET` | `/api/aircraft` | Get all aircraft |
| `GET` | `/api/aircraft/{id}` | Get aircraft by id |
| `PUT` | `/api/aircraft/{id}` | Update aircraft |
| `DELETE` | `/api/aircraft/{id}` | Delete aircraft |

Example aircraft body:

```json
{
  "model": "Airbus A321",
  "tailNumber": "RA-73222",
  "seats": 220
}
```

### Flights

| Method | URL | Operation |
| --- | --- | --- |
| `POST` | `/api/flights` | Create flight |
| `GET` | `/api/flights` | Get all flights |
| `GET` | `/api/flights/{id}` | Get flight by id |
| `PUT` | `/api/flights/{id}` | Update flight |
| `DELETE` | `/api/flights/{id}` | Delete flight |

Example flight body:

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

### Bookings

| Method | URL | Operation |
| --- | --- | --- |
| `POST` | `/api/bookings` | Create booking |
| `GET` | `/api/bookings` | Get all bookings |
| `GET` | `/api/bookings/{id}` | Get booking by id |
| `PUT` | `/api/bookings/{id}` | Update booking |
| `DELETE` | `/api/bookings/{id}` | Delete booking |

Example booking body:

```json
{
  "passengerName": "Petr Ivanov",
  "flightId": 1,
  "seatNumber": "14B",
  "status": "CONFIRMED"
}
```

## Будущие функции сервиса

- Поиск рейсов по маршруту, дате, цене и доступным местам.
- Регистрация пассажиров и хранение их данных.
- Бронирование, оплата и отмена билетов.
- Онлайн-регистрация на рейс и выбор места.
- Управление самолетами, расписанием рейсов и статусами рейсов.
- Формирование посадочных талонов и отчетов по бронированиям.
- Уведомление пассажиров о задержках и отменах рейсов.

## Проверка

```bash
./mvnw test
```
