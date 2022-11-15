# SumUpAPI

##Info for the project:

Appache Maven - project management build tool;

TestNG - JAVA testing framework;

RestAssured - API designed for automating REST services/REST APIs

SumUpAPI project:
```
pom.xml
src
  |--main
        |--java
              |--Authorization
              |--Constants

  |--test
        |--java
              |--APITests
```

How to run tests:
```
mvn -Dtest=APITests test
```
this command will run and execute all methods anotated with @Test tag in the APITests class


SQL Task

SQL Queries:
###### FIRST QUERY
```
SELECT * 
  FROM customers 
  WHERE Country = 'United States';
```

###### SECOND QUERY
```
SELECT c.COUNTRY, ROUND(AVG(d.Age)) as Avg_Age
  FROM customers c
  JOIN customers_details d
  ON c.ID = d.Customer_id
  GROUP by c.COUNTRY;
```
  
###### THIRD QUERY
```
SELECT c.ID, c.FIRST_NAME, c.LAST_NAME, c.Created_at, d.Updated_at
  FROM customers c
  JOIN customers_details d
  ON c.ID = d.Customer_id
  WHERE to_char(c.Created_at) = to_char(d.Updated_at);
```