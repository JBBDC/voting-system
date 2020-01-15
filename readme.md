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



### Credentials:

```
User login: user@yandex.ru
      Password: password
Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=

   Admin login: admin@gmail.com
      Password: password
Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk
```

###  **CURL examples**:

#### 	USER:

- **GET restaurants with menu for today:** 

```
  curl 'http://localhost:8080/api/v1/restaurants' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='
```

- **Vote for restaurant with id 100004:** 

 ```
   curl 'http://localhost:8080/api/v1/vote/100004' -i -X POST -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -H "Content-Type: application/json"
 ```

- **GET votes history for user:** 

```
  curl 'http://localhost:8080/api/v1/votes' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='
  curl 'http://localhost:8080/api/v1/votes?startDate=2019-10-21&endDate=2020-12-31' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='
```

  

- **GET vote for today or arbitrary date:**

 ```
curl 'http://localhost:8080/api/v1/vote' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='
curl 'http://localhost:8080/api/v1/vote?date=2019-11-20' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='
```
  
  

#### ADMIN:

- #### Users:

```
curl 'http://localhost:8080/api/v1/admin/users' -i -d '{"name" : "NewUser", "email" : "new@yandex.ru","password" : "123456","roles" : ["ROLE_USER"]}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/users' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/users/100000' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/users/100017' -i -X DELETE  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/users/by?email=user@yandex.ru' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
```

- #### Votes:

```
curl 'http://localhost:8080/api/v1/admin/votes' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
```

- #### Restaurants:

```
curl 'http://localhost:8080/api/v1/admin/restaurants' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/restaurants/100004' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/restaurants/by?name=First' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/restaurants' -i -d '{"name" : "NewOne"}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/restaurants/100003' -i -X PUT -d '{"name" : "UpdatedName"}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/restaurants/100004' -i -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
```

- #### Dishes:

```
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes?startDate=2019-11-21&endDate=2020-12-31' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes' -i -d '{"name" : "NewDish","price" : "10.10"}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes/100008' -i -X PUT -d '{"id":"100008","name" : "Updated","price" : "11.10"}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
curl 'http://localhost:8080/api/v1/admin/restaurants/100003/dishes/100008' -i -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"
```

  

  

