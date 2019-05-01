# NTNU Cyborg Backend

This is the backend repository for NTNU Cyborg's promotation page.
The project is based on [Spring](https://spring.io/ "A JVM-based server framework")

## Installing

To run this project you need to have Java installed on your system.
To build it you need [Maven](https://maven.apache.org/ "Among many other things, a practical build tool for Java"), and [JDK](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html "Java Devlopment Kit") as well.

To install Maven and JDK on a Debian/Ubuntu system, type:
```
sudo apt-get install default-jdk
sudo apt install maven
```

When Maven and JDK is installed, clone the project and cd into it.  

To build the project and get a .jar-file, type:
```
mvn clean package
```

To run the project, type:
```
java -jar target/gs-rest-service-0.1.0.jar
```

The server will then run on port 8080.

## The Frontend
[NTNU Cyborg Frontent](https://github.com/Astroxslurg/ntnu-cyborg_node "A Node/Next-based frontend repo")
