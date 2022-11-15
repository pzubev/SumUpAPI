# SumUpAPI

##Info for the project:

Appache Maven - project management build tool;

TestNG - JAVA testing framework;

RestAssured - API designed for automating REST services/REST APIs

SumUpAPI project structure:
```
pom.xml
src
  |--main
        |--java
              |--Authorization
              |--ConfigProperties
        |--resources
              |--config.properties

  |--test
        |--java
              |--APITests
```

 * [Authorization.java](https://github.com/pzubev/SumUpAPI/blob/main/src/main/java/Authorization.java)
 simple POJO class, where are stored fields part used for composing the JSON object for the POST request to https://api.sumup.com/oauth
 
 * [ConfigProperties.java](https://github.com/pzubev/SumUpAPI/blob/main/src/main/java/ConfigProperties.java)
 this class serves for reading the config.properties file
 
 * [APITests.java](https://github.com/pzubev/SumUpAPI/blob/main/src/test/java/APITests.java)
 this is the Test class holding all the test cases created for:
 https://developer.sumup.com/docs/api/list-bank-accounts/ and
 https://developer.sumup.com/docs/api/list-transactions/


How to run the test cases:

After cloning the repository:
```
git clone https://github.com/pzubev/SumUpAPI.git
```
all dependencies from the pom.xml file should be resolved successfully

 
Navigate to the SumUpAPI folder and perform:

```
mvn -Dtest=APITests test
```
this command will run and execute all methods annotated with @Test tag in the APITests.class



##SQL Task

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