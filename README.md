# Restaurant Voting System

[Technical requirements](https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md)

REST API for deciding where to have lunch. No frontend.

- Users vote for a restaurant for today (one vote per user per day).
- A vote can be changed until 11:00; after that it is final.
- Admins manage restaurants and daily menus (dish name + price in kopecks).

## Stack

JDK 21, Spring Boot 3.3, Spring Data JPA, Spring Security, H2, Caffeine, SpringDoc OpenAPI 2.x

## Run

```
mvn spring-boot:run
```

## API documentation

[Swagger UI](http://localhost:8080/) — Authorize with Basic Auth.

```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```

## curl

Restaurants with today's menus:

```
curl -u user@yandex.ru:password http://localhost:8080/api/restaurants
```

Vote:

```
curl -u user@yandex.ru:password -H "Content-Type: application/json" -d "{\"restaurantId\":1}" -X POST http://localhost:8080/api/votes
```

Change vote (until 11:00):

```
curl -u user@yandex.ru:password -H "Content-Type: application/json" -d "{\"restaurantId\":2}" -X PUT http://localhost:8080/api/votes/today
```

Today's vote:

```
curl -u user@yandex.ru:password http://localhost:8080/api/votes/today
```

Vote history:

```
curl -u user@yandex.ru:password http://localhost:8080/api/votes
```

Create restaurant (admin):

```
curl -u admin@gmail.com:admin -H "Content-Type: application/json" -d "{\"name\":\"New Place\"}" http://localhost:8080/api/admin/restaurants
```

Add a dish for today (admin):

```
curl -u admin@gmail.com:admin -H "Content-Type: application/json" -d "{\"name\":\"Tiramisu\",\"price\":350}" http://localhost:8080/api/admin/restaurants/1/dishes
```
