# Spring Boot Controllers Lab

Java project for simple Spring Boot controller examples.

## Stack

- Java 21
- Spring Boot 3.5.5
- Maven Wrapper
- Spring Web
- Spring Validation

## Run

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts on `http://localhost:8080`.

## Endpoints

| Method | URL | Description |
| --- | --- | --- |
| `GET` | `/` | Application info and endpoint list |
| `GET` | `/api/hello?name=Fedor` | Query parameter example |
| `GET` | `/api/numbers/5` | Path variable and validation example |
| `GET` | `/api/students` | Returns all demo students |
| `GET` | `/api/students/1` | Returns one student by id |
| `POST` | `/api/students` | Creates a student from JSON body |

Example POST body:

```json
{
  "fullName": "Petr Ivanov",
  "groupName": "IVBO-03-25"
}
```

## Verification

```bash
./mvnw test
```
