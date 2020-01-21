### Test task

Voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

  **Stack:**
- Spring Boot
- Spring Data JPA

### Install:
```
$ git clone https://github.com/JBBDC/voting-system
```

### Run:
```
$ mvn spring-boot:run
```
or

```
$ mvn clean package
$ java -Dfile.encoding=UTF8 -jar target/task-0.0.1-SNAPSHOT.jar
```

### Credentials:

```
User login: user@yandex.ru
      Password: password
Authorization: --user user@yandex.ru:password

   Admin login: admin@gmail.com
      Password: password
Authorization: --user admin@gmail.com:password
```

##  **CURL examples**:

## 	USER:

- **GET restaurants with menu for today:** 

```
  curl 'http://localhost:8080/api/v1/restaurants' --user user@yandex.ru:password
```

- **Vote for restaurant with id 100004:** 

```
   curl 'http://localhost:8080/api/v1/vote/100004' -i -X POST --user user@yandex.ru:password -H "Content-Type: application/json"
```

- **GET vote for today:**

```
curl 'http://localhost:8080/api/v1/votes' --user user@yandex.ru:password
```
-----------------------
  
## ADMIN:

 ## Users:

- **GET all users:**

```
curl 'http://localhost:8080/api/v1/admin/users' --user admin@gmail.com:password
```

- **GET user by email:**

```
curl 'http://localhost:8080/api/v1/admin/users/by?email=user@yandex.ru' --user admin@gmail.com:password
```

- **GET user with id 100000:**

```
curl 'http://localhost:8080/api/v1/admin/users/100000' --user admin@gmail.com:password
```

- **Create new user:**

```
curl 'http://localhost:8080/api/v1/admin/users' -i -d '{"name" : "NewUser", "email" : "new@yandex.ru","password" : "123456","roles" : ["ROLE_USER"]}' --user admin@gmail.com:password -H "Content-Type: application/json"
```


- **DELETE user with id 100017:**

```
curl 'http://localhost:8080/api/v1/admin/users/100017' -i -X DELETE  --user admin@gmail.com:password -H "Content-Type: application/json"
```


 ##  Votes:

- **GET voting history:**

```
curl 'http://localhost:8080/api/v1/admin/votes' --user admin@gmail.com:password

curl 'http://localhost:8080/api/v1/admin/votes?startDate=2019-11-21&endDate=2020-12-31' --user admin@gmail.com:password
```



 ## Restaurants:

- **GET all restaurants:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants' --user admin@gmail.com:password
```

- **GET restaurant with id 100004:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100004' --user admin@gmail.com:password
```

- **GET restaurant by name:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/by?name=First' --user admin@gmail.com:password
```

- **Create new restaurant:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants' -i -d '{"name" : "NewOne"}' --user admin@gmail.com:password -H "Content-Type: application/json"
```

-	**Update restaurant with id 100003:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003' -i -X PUT -d '{"id":"100003","name" : "UpdatedName"}' --user admin@gmail.com:password -H "Content-Type: application/json"
```

- **DELETE restaurant with id 100004:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100004' -i -X DELETE --user admin@gmail.com:password -H "Content-Type: application/json"
```



 ## Dishes:

- **GET all dishes for restaurant with id 100003:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes' --user admin@gmail.com:password
```

- **GET  dishes for restaurant with id 100003 filtered by date:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes?startDate=2019-11-21&endDate=2020-12-31' --user admin@gmail.com:password
```

- **Create new dish for restaurant with id 100003:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes' -i -d '{"name" : "NewDish","price" : "10.10"}' --user admin@gmail.com:password -H "Content-Type: application/json"
```

- **Update dish 100008 for restaurant 100003:**

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes/100008' -i -X PUT -d '{"id":"100008","name" : "Updated","price" : "11.10"}' --user admin@gmail.com:password -H "Content-Type: application/json"
```

- **DELETE dish with id 100008:**

```
 curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes/100008' -i -X DELETE --user admin@gmail.com:password -H "Content-Type: application/json"
```
