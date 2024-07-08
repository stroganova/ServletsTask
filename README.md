Actor:

GET http://localhost:8080/actor?id=1 - получить актера по id
GET http://localhost:8080/allActors - получить список всех актеров
POST http://localhost:8080/actor - добавить актера 
{
    "firstname": «firstname",
    "lastname": "lastname",
    "phoneNumber": "+79345654654"
}
 
PUT  http://localhost:8080/actor  - изменить данные актера
{
    "id": ( существующий id),
    "firstname": «firrstname2,
    "lastname": "lastname2»,
    "phoneNumber": "+79343544654"
}

POST  http://localhost:8080/actorsOfPerformance?actorId=(id актера)&performanceId= (id спектакля) - добавить актера спектаклю
DELETE  http://localhost:8080/actorsOfPerformance?actorId=(id актера)&performanceId= (id спектакля) - удалить актера у спектакля

DELETE http://localhost:8080/actor?id= (существующий id) - удалить актера по id

Hall:

GET http://localhost:8080/hall?id=1  - получить зал по id
GET http://localhost:8080/allHalls - получить список залов
POST http://localhost:8080/hall - добавить зал 
{
    "name": "name",
    "address": "address",
    "phoneNumber": "65456547"
}

PUT -   изменить данные зала
{
    "id": (существующий id),
    "name": "name",
    "address": "address",
    "phoneNumber": "65456547"
}

DELETE http://localhost:8080/hall?id= (существующий  id)   - удалить зал по id

Performance:

GET http://localhost:8080/performance?id=1 - получить спектакль по id
GET http://localhost:8080/allPerformances - получить список спектаклей
POST  http://localhost:8080/performance - добавить спектакль
{
   "name": "name",
    "description": "description",
    "hallId": 1
}

PUT  http://localhost:8080/performance - изменить данные спектакля
{
    "id": (существующий id),
    "name": "name",
    "description": "description",
    "hallId": 1
}


DELETE http://localhost:8080/performance?id= (существующий id) -удалить по id
