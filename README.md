# svc-playerdb
Demo microservice application that loads player profiles from a csv file. 
This csv is loaded at startup(PlayerdbApplication.java) from location `src/main/resources/People.csv`


## Technology ##
Following libraries were used during the development of this microservice :

- **Spring Boot** - Server side framework
- **Docker** - Containerizing framework
- **H2** - in memory relational database 
- **Swagger** - API documentation
- **Lombok** - Annotation plugin/processor
- **Spring actuator** - Production readiness components
- **Micrometer** - Prometheus registry to expose metric 
- **Gradle** - Dependency management 

## RUN

go to svc-playerdb root folder and run the following command to build the project:

`./gradlew build`

**Run as docker:**

`docker-compose up --build`

To run with cpu/memory limits, use the following command. This works only in docker v3.0 and above:

`docker-compose --compatibility up --build`

Service will run on port 8080 by default which could be accessed at 
`http://localhost:8080/`

## REST API

The service should expose these REST endpoints:
* `GET /api/players` - returns the list of all players
* `GET /api/players/paginated` - returns the list of all players in paginated fashion
* `GET /api/players/{playerID}` - returns a single player by ID
* `PUT /api/players/{playerID}/weight` - increments a player's weight by 1
* `PUT /api/players/{playerID}/height` - increments a player's height by 1


**Response**
 
Custom response type: 
 
 ```
public class Response<T> {

    private Status status;
    private T payload;
    private Object errors;
    private Object metadata;
}
```

Example Json output from `GET /api/players/{playerID}`

```
{
    "status": "OK",
    "payload": {
        "playerID": "aaronto01",
        "birthYear": 1939,
        "birthMonth": 8,
        "birthDay": 5,
        "birthCountry": "USA",
        "birthState": "AL",
        "birthCity": "Mobile",
        "deathYear": 1984,
        "deathMonth": 8,
        "deathDay": 16,
        "deathCountry": "USA",
        "deathState": "GA",
        "deathCity": "Atlanta",
        "nameFirst": "Tommie",
        "nameLast": "Aaron",
        "nameGiven": "Tommie Lee",
        "weight": 190,
        "height": 75,
        "bats": "R",
        "pThrows": "R",
        "debut": "1962-04-10",
        "finalGame": "1971-09-26",
        "retroID": "aarot101",
        "bbrefID": "aaronto01",
        "pthrows": "R"
    }
}
```

Example error response

```
{
    "status": "NOT_FOUND",
    "errors": {
        "timestamp": "2020-11-30T23:36:14.028+00:00",
        "message": "Requested Player with id - aaronto01ss was not found.",
        "details": "Requested Player with id - aaronto01ss was not found."
    }
}
```

**Dependency injection**

This project uses `Autowired` annotation driven dependency injection


**Exceptions**

Exceptions and its types are defined in package `com.intuit.playerdb.exceptions`
We could throw an exception like:

`throw exception(PLAYER, ENTITY_NOT_FOUND, PlayerID);`

This results in clubbing the names of these two enums PLAYER(player) and ENTITY_NOT_FOUND(not.found) and coming up with a key player.not.found which is present in the application.properties files as follows :

player.not.found=Requested Player with id - {0} was not found.


### Production readiness components

**Actuator**

The spring-boot-actuator module provides all of Spring Boot’s production-ready features. The simplest way to enable the features is to add a dependency to the spring-boot-starter-actuator

Setup:

Add the following dependency in build.gradle file

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
```

List of all actuator endpoints will be available here http://localhost:8080/actuator/

Some of the features are turned off by default. We can enable them by adding following line in application.properties `management.endpoints.web.exposure.include=*`

Components can be accessed now via http. Following is the endpoint for healthcheck
 `http://localhost:8080/actuator/health`
 
 Metrics: `http://localhost:8080/actuator/metrics`
 

**Open api Swagger**

The springdoc suite of java libraries are all about automating the generation of machine and human readable specifications for JSON APIs written using the spring family of projects

Setup:

Add the following dependencies in build.gradle file

```
dependencies {
    implementation ''org.springdoc:springdoc-openapi-ui:1.5.0''
}
```

Swagger ui:  http://localhost:8080/swagger-ui.html#/


**Docker Setup**

Dockerfile and docker-compose.yml have been created manually


**Logging**

Spring boot offers logback for logging by default.
To change the logging level, add the following lines in application.properties

`logging.level.root=WARN`
`logging.level.com.intuit=TRACE`

Other than that I've also implemented request/response logging at Controller & Service level using Aspect and Logging Filter.
See `com.intuit.playerdb.logging` package for implementation. Example of log statements below:

```
svc-playerdb | 2020-11-30 23:32:29.122  INFO 1 --- [nio-8080-exec-5] c.i.p.logging.filter.LoggingFilter       : GET : /api/players/aaronto01
svc-playerdb | 2020-11-30 23:32:29.140 TRACE 1 --- [nio-8080-exec-5] c.intuit.playerdb.service.PlayerService  : [Enter : PlayerService.fetchPlayerById(..)] with args [aaronto01]
svc-playerdb | 2020-11-30 23:32:29.154 TRACE 1 --- [nio-8080-exec-5] c.intuit.playerdb.service.PlayerService  : [Exit : PlayerService.fetchPlayerById(..)] with return type [class com.intuit.playerdb.model.Player] and value [Player(playerID=aaronto01, birthYear=1939, birthMonth=8, birthDay=5, birthCountry=USA, birthState=AL, birthCity=Mobile, deathYear=1984, deathMonth=8, deathDay=16, deathCountry=USA, deathState=GA, deathCity=Atlanta, nameFirst=Tommie, nameLast=Aaron, nameGiven=Tommie Lee, weight=190, height=75, bats=R, pThrows=R, debut=1962-04-10, finalGame=1971-09-26, retroID=aarot101, bbrefID=aaronto01)]
svc-playerdb | 2020-11-30 23:32:29.188  INFO 1 --- [nio-8080-exec-5] c.i.p.logging.filter.LoggingFilter       : Completed : 200
```

**Tracing** 

Tracing is part of Actuator. We just need to modify our application.properties file to include the httpTrace endpoint:

`management.endpoints.web.exposure.include=httptrace`

Trace logs can be accessed at http://localhost:8080/actuator/httptrace



##Further enhancements(out of scope for this demo)
Although the project contains certain level of production readiness and production like structure, there are a couple more things that could be implemented to make it more efficient.
Below are some of the example:

- **Database** - This project uses H2 in-memory database. This should be changed eventually and H2 could be used while running locally.
- **Design patterns such as Factory, Template etc** - We could make use of factory and template design patterns to make the code more flexible and reusable and various levels such as service/repository.
- **Testing** - Support for testing(unit, integration, end-to-end, performance)
- **Logging** - Logging wrapper could be enhanced to be more intelligent about context and formatting
- **Security** - Support for security of endpoints
- **Custom middlewares** - We could add custom middlewares for processing in request/response 





