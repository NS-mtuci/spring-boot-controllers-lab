# Отчет по лабораторной работе

## Задание

Создать Java-проект на Spring Boot версии 3.5.5 или выше, опубликовать его на GitHub и добавить простые контроллеры для проверки работы HTTP-запросов.

## Создание проекта

Проект создан на Spring Boot `3.5.5`, Java `21`, сборка выполняется через Maven Wrapper.

Скриншот 1: структура проекта после создания.

![Структура проекта](screenshots/01-project-structure.png)

## Реализованные контроллеры

В проект добавлены контроллеры:

- `HomeController` - стартовая страница `/` со списком доступных endpoint-ов.
- `HelloController` - примеры `@RequestParam`, `@PathVariable` и простой валидации.
- `StudentController` - примеры `GET` и `POST` запросов с JSON body.

## Проверка GET-запросов

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

## Проверка POST-запроса

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

## Публикация на GitHub

Ссылка на репозиторий:

`TODO: вставить ссылку на созданный GitHub-репозиторий`

Скриншот 5: опубликованный репозиторий на GitHub.

![GitHub repository](screenshots/05-github-repository.png)
