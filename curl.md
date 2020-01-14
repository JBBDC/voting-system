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

- **Vote for restaurant with id 100004:** 

   curl 'http://localhost:8080/api/v1/vote/100004' -i -X POST -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -H "Content-Type: application/json"

- **GET restaurants with menu for today:** 

  curl 'http://localhost:8080/api/v1/restaurants' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

  

- **GET votes history for user:** 

  curl 'http://localhost:8080/api/v1/votes' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

  curl 'http://localhost:8080/api/v1/votes?startDate=2019-10-21&endDate=2020-12-31' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

  

- **GET vote for today or arbitrary date:**

  curl 'http://localhost:8080/api/v1/vote' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

  curl 'http://localhost:8080/api/v1/vote?date=2019-11-20' -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='

#### ADMIN:

Users:

Create new User

curl 'http://localhost:8080/api/v1/admin/users' -i -d '{"name" : "NewUser", "email" : "new@mail.ru","password" : "123456","roles" : ["ROLE_USER"]}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"

GET all users

curl 'http://localhost:8080/api/v1/admin/users' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'

GET user

curl 'http://localhost:8080/api/v1/admin/users/100000' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk'

UPDATE user

curl 'http://localhost:8080/api/v1/admin/users/100017' -i -X PUT -d '{"id":"100017","name" : "Updated", "email" : "new@mail.ru","password" : "123456","roles" : ["ROLE_USER"]}' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -H "Content-Type: application/json"

Votes:

Restaurants:

Dishes:



  

  

