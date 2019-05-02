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
If run in production one must either open this port in the firewall settings,
or forward the port using a reverse proxy.
The later being the recommended if security is crucial.

## The Frontend
[NTNU Cyborg Frontent](https://github.com/Astroxslurg/ntnu-cyborg_node "A Node/Next-based frontend repo")

## Database

The database is based on the MySQL database management system, but can be implemented in any SQL capable system.
First get the database management system you want to use. For this I suggest using the MySQL servers provided by NTNU.
[NTNU MySQL](https://innsida.ntnu.no/wiki/-/wiki/English/Using+MySQL+at+NTNU)

Then include the following snippet in the pom.xml document in the root folder of the system. Replace any other SQL-connectors beforehand to be safe. Should be placed next to the other dependencies, within the <dependencies> tag.
```
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.16</version>
</dependency>
```

Note that the database is not optimized, but works fine for this project as a whole.
Function to transfer data from a .csv file to the database included in DBHandler class in the project, named insertCSVIntoDatabase(). 

This requires an existing user, and an existing experiment.
Example code for initial user:
```
INSERT INTO kimera_k3ah.Users(Email, Name, Password) VALUES("test@example.com", "norsk norskersen", "passord1234")
```

Example code for initial user:
```
INSERT into kimera_k3ah.Experiment(performedBy, experimentID, startTime, endTime, comment, hasInputStream) VALUES(0, 0, '2012-06-18 10:34:09', '2019-05-01 10:40:00', "testingscomment", false)
```

Following is a set of queries that can be used to replicate the database structure. 

```
CREATE DATABASE IF NOT EXISTS kimera_k3ah DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE kimera_k3ah;

CREATE TABLE `Data` (
  `ID` int(16) NOT NULL,
  `ID2` int(16) NOT NULL,
  `base` varchar(10) NOT NULL,
  `timeStamp` int(64) NOT NULL,
  `value` int(64) NOT NULL,
  `experimentID` int(16) NOT NULL,
  `autoID` int(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE Experiment (
  `experimentID` int(32) NOT NULL,
  `performedBy` int(32) NOT NULL,
  `hasInputStream` tinyint(1) NOT NULL,
  `comment` text NOT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE Users (
  `ID` int(32) NOT NULL,
  `Name` varchar(128) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Email` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE cellData (
  `cellID` varchar(64) NOT NULL,
  `timeStamp` varchar(64) NOT NULL,
  `value` varchar(64) DEFAULT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Data`
  ADD PRIMARY KEY (`autoID`);

ALTER TABLE Experiment
  ADD PRIMARY KEY (`experimentID`);

ALTER TABLE Users
  ADD UNIQUE KEY `IDA` (`ID`);

ALTER TABLE cellData
  ADD PRIMARY KEY (`ID`);


ALTER TABLE `Data`
  MODIFY `autoID` int(64) NOT NULL AUTO_INCREMENT;
ALTER TABLE Users
  MODIFY `ID` int(32) NOT NULL AUTO_INCREMENT;
ALTER TABLE cellData
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
```
