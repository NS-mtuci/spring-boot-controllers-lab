# Spring Boot Controllers Lab

Лабораторная работа по созданию простого Java-проекта на фреймворке **Spring Boot** с реализацией базовых контроллеров.

## Описание проекта

Проект создан для изучения работы контроллеров в Spring Boot.  
В приложении реализованы простые HTTP-эндпоинты, которые позволяют проверить работу:

- `@RestController`;
- `@GetMapping`;
- `@PostMapping`;
- `@RequestParam`;
- `@PathVariable`;
- `@RequestBody`;
- валидации входных параметров;
- обработки простых JSON-запросов.

## Используемые технологии

- Java 21
- Spring Boot 3.5.5
- Spring Web
- Spring Validation
- Maven

## Структура проекта

```text
src
└── main
    └── java
        └── ru
            └── mtuci
                └── lab
                    └── controllers
                        ├── SpringBootControllersLabApplication.java
                        └── controller
                            ├── HomeController.java
                            ├── HelloController.java
                            ├── StudentController.java
                            └── StudentNotFoundException.java
