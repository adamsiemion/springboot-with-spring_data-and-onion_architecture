Sample Spring boot project using

* Spring Data JPA (with H2 in-memory database)
* Onion Architecture (http://jeffreypalermo.com/blog/the-onion-architecture-part-1/)

# Build

    mvn clean install

# Run

    java -jar springboot-onionarch-rest/target/springboot-onionarch-rest-0.0.1-SNAPSHOT.jar

# Usage

    curl http://localhost:8080/users
    
returns an empty array

    curl -H "Content-Type: application/json" -X POST -d '{"name":"John"}' http://localhost:8080/users
    curl http://localhost:8080/users
    
returns an array with one entry with the "John"

    curl -X PUT http://localhost:8080/users/upp
    curl http://localhost:8080/users
    
returns an array with one entry with the "JOHN"
    
    
