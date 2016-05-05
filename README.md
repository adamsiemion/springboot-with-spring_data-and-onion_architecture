This tutorial will show how to create a REST application using the Onion architecture and Spring boot (Spring Core, Spring MVC and Spring Data). 
For simplicity it will use an in-memory database (Fongo) and expose only one REST endpoint.

The traditional three-layered architecture consists of:

+ presentation layer
+ application layer (also called business logic, logic or middle layer)
+ data layer

The onion architecture is a variant of multi-layered architecture, which consists of:

+ application core which consits of:
 * domain model
 * domain services
 * application services
+ infrastructure

The onion architecture does not have the drawbacks of the traditional three-layered architecture:

+ the domain layer does not have dependencies to any infrastructure code (expect for the javax.inject library)
+ infrastructure code dependencies are kept in separate modules thanks to which they do not pollute the dependencies of the other modules

[More on Onion Architecture](http://jeffreypalermo.com/blog/the-onion-architecture-part-1)

[Complete tutorial sources](https://github.com/adko-pl/springboot-with-spring_data-and-onion_architecture)

# Project setup

## Create a new multimodule maven project

Create directory `onionarch` with `pom.xml` with:

+ `pom` packaging
+ a dependency to javax.inject:javax.inject:1
+ a dependency to junit:junit:4.12
+ Java 1.8 properties 

The complete `pom.xml` content:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.github.adamsiemion.onionarch</groupId>
    <artifactId>onionarch</artifactId>
    <packaging>pom</packaging>
    <version>1.0.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>javax.inject</groupId>
            <artifactId>javax.inject</artifactId>
            <version>1</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

Multimodule maven project allows better dependencies management because each maven module can contain only the dependencies needed by the code in this specific module. 
Whenever the domain module requires access to infrastructure code, e.g. to send an email or download a file from FTP instead of adding a dependency 
to the selected infrastructure library in the domain layer one should: 

+ create an interface in the domain layer simplifying the API of the infrastructure library (Facade design pattern) 
+ create a new maven module with an implementation of the interface and dependencies to the chosen libraries

The Onion Architecture relies on the Dependency Inversion principle, so a way to specify that a class will be injected by the Dependency Injection framework is needed.
One option is to use the annotations provided by the DI framework (e.g. Spring), however this will couple the domain 
to a specific infrastructure library. In order to prevent this coupling we use the annotations from the standard dependency injection API (JSR-330) `javax.inject`.

# Domain layer

## Create maven module `onionarch-domain`

From the root directory run:

```bash
mvn archetype:generate -DgroupId=com.github.adamsiemion.onionarch -DartifactId=onionarch-domain \
-DinteractiveMode=false -Dversion=1.0.0-SNAPSHOT
```

We start development from the domain layer, following the principles of Domain Driven Design.
A specific version (`1.0.0-SNAPSHOT`) was provided just to follow the most popular versioning convention - [semantic versioning](http://semver.org). 

## Delete the generated Java files

```bash
rm -rf onionarch-domain/src/main/java/com onionarch-domain/src/test/java/com
```

## Create an empty User model class

Create class `User` in `onionarch-domain\src\main\java`

```java
public class User {
}
```

# Presentation layer (providing REST API)

## Create maven module `onionarch-rest`

From the root directory run:

```bash
mvn archetype:generate -DgroupId=com.github.adamsiemion.onionarch -DartifactId=onionarch-rest \
-DinteractiveMode=false -Dversion=1.0.0-SNAPSHOT
```

## Delete the generated Java files

```bash
rm -rf onionarch-rest/src/main/java/com onionarch-rest/src/test/java/com
```

## Add Spring Boot Starter Web dependency

Add below content to `onionarch-rest\pom.xml`

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>1.3.3.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

## Add a dependency to the domain module

```xml
<dependency>
    <groupId>com.github.adamsiemion.onionarch</groupId>
    <artifactId>onionarch-domain</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Add a plugin to build an executable jar

Edit pom.xml from the rest module directory and add:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Create a `@SpringBootApplication` class

Create class `Application` in `onionarch-rest\src\main\java` with the following content:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
   public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
   }
}
```

## Create UserRest class with the `GET /users` method

Create class `UserRest` in `onionarch-rest\src\main\java` with the following content:

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRest {
   @RequestMapping(method = RequestMethod.GET)
   public List<User> list() {
       return new ArrayList<>();
   }
}
```

If you [build and run the application](#build-and-run-the-application) now 
and send a HTTP GET to http://localhost:8080/users (`curl http://localhost:8080/users`) 
the application will respond with an empty array.

# Domain and presentation layer development

## Add attributes to the User model class

+ String id
+ String name

The complete `User` source code:

```java
import java.util.Objects;

public class User {
    private String id;
    private String name;

    User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "'}";
    }
}
```

[Lombok](https://projectlombok.org) can reduce the number of boilerplate code (such as getters, `toString()`, `equals()`, `hashCode()`).
It is possible to [make the above class immutable what brings a lot of advantages](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html), 
by defining an all args constructor and using [Jackon’s parameter names module](https://github.com/FasterXML/jackson-module-parameter-names).

## Create UserRepository interface in the domain

Create interface `UserRepository` in `onionarch-domain\src\main\java` with the following content:

```java
public interface UserRepository {
    Iterable<User> list();

    User get(Long id);

    void save(User user);

    void delete(Long id);
}
```

## Inject UserRepository into UserRest

Add the following content to `onionarch-rest\src\main\java\UserRest.java`:

```java
private final UserRepository userRepository;

@Inject
public UserRest(final UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

## Add CRUD methods to UserRest

Add the following content to `onionarch-rest\src\main\java\UserRest.java` (overwrite the existing `list` method):

```java
@RequestMapping(method = RequestMethod.GET)
public Iterable<User> list() {
    return userRepository.list();
}

@RequestMapping(method = RequestMethod.POST)
public void create(@RequestBody User user) {
    userRepository.save(user);
}

@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
public void delete(@PathVariable("id") final Long id) {
    userRepository.delete(id);
}

@RequestMapping(value = "{id}", method = RequestMethod.GET)
public User get(@PathVariable("id") final Long id) {
    return userRepository.get(id);
}
```

## Create a fake UserRepository implementation

Create class `UserRespositoryFake` in `onionarch-domain\src\main\java` with the following content:

```java
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
public class UserRepositoryFake implements UserRepository {
   @Override
   public List<User> list() {
       return Arrays.asList(new User(1L, "John Smith"), new User(2L, "John Doe"));
   }

   @Override
   public User get(Long id) {
       return new User();
   }

   @Override
   public void save(User user) { }

   @Override
   public void delete(Long aLong) { }
}
```

This class is a [fake](http://www.martinfowler.com/bliki/TestDouble.html) implementation, created to test the current solution, which will not be used in production. 

If you [build and run the application](#build-and-run-the-application) now 
and do a GET to http://localhost:8080/users (`curl http://localhost:8080/users`) the application will respond with:
`[{"id":1,"name":"John Smith"},{"id":2,"name":"John Doe"}]`

# Data layer

## Delete UserRespositoryFake

## Create maven module `onionarch-data`

From the root directory run:

```bash
mvn archetype:generate -DgroupId=com.github.adamsiemion.onionarch -DartifactId=onionarch-data \
-DinteractiveMode=false -Dversion=1.0.0-SNAPSHOT
```

## Delete the generated Java files

```bash
rm -rf onionarch-data/src/main/java/com onionarch-data/src/test/java/com
```

## Add dependencies for Spring Boot and Spring Data

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>1.3.3.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
</dependencies>
```

## Add a dependency to the domain module

```xml
<dependency>
    <groupId>com.github.adamsiemion.onionarch</groupId>
    <artifactId>onionarch-domain</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Add a dependency to fongo in the storage module

```xml
<dependency>
    <groupId>com.github.fakemongo</groupId>
    <artifactId>fongo</artifactId>
    <version>1.6.7</version>
</dependency>
```

## Add a dependency to the storage module in the presentation module

```xml
<dependency>
    <groupId>com.github.adamsiemion.onionarch</groupId>
    <artifactId>onionarch-storage</artifactId>
    <version>${project.version}</version>
    <type>runtime</type>
</dependency>
```

This is required because contrary to the traditional layered architecture the domain layer does not have a dependency to the data layer, 
so the infrastructure has to directly provide a dependency to another infrastructure module. 

## Create UserDaoSpringData interface extending Spring Data's MongoRepository

Create class `UserDaoMongo` in `onionarch-storage\src\main\java` with the following content:

```java
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDaoMongo extends MongoRepository<User, String> {
}
```

## Create a Mongo configuration class

Create class `MongoConfig` in `onionarch-storage\src\main\java` with the following content:

```java
import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
   @Override
   protected String getDatabaseName() {
       return "users";
   }

   @Override
   public Mongo mongo() {
       return new Fongo("mongo-test").getMongo();
   }
}
```

## Create a UserRepository implementation, which will delegate all the calls to UserDaoSpringData

Create class `UserRepositorySpringData` in `onionarch-storage\src\main\java` with the following content:

```java
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class UserRepositorySpringData implements UserRepository {

   private final UserDaoMongo dao;

   @Inject
   public UserRepositorySpringData(final UserDaoMongo dao) {
       this.dao = dao;
   }

   @Override
   public Iterable<User> list() {
       return dao.findAll();
   }

   @Override
   public User get(String id) {
       return dao.findOne(id);
   }

   @Override
   public void save(User user) {
       dao.save(user);
   }

   @Override
   public void delete(String id) {
       dao.delete(id);
   }
}
```

The above class is an example of the delegate design pattern. 

## Build and run the application

To build the application go to the root directory and run: `mvn install`

To run the application go to the rest module directory and run: `java -jar target/onionarch-rest-1.0.0-SNAPSHOT.jar`

## Test the application

### Get a list of users

`curl http://localhost:8080/users`

### Add a user

`curl -H 'Content-Type: application/json' -X POST -d '{"name":"John Smith"}' http://localhost:8080/users`

### Get a user details

`curl http://localhost:8080/users/<user_id>`

### Delete a user

`curl -X DELETE http://localhost:8080/users/<user_id>`
