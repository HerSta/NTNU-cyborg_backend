SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
CREATE DATABASE IF NOT EXISTS `kyborg-db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `kyborg-db`;

CREATE TABLE `Data` (
                        `ID` int(16) NOT NULL,
                        `ID2` int(16) NOT NULL,
                        `base` varchar(10) NOT NULL,
                        `timeStamp` int(64) NOT NULL,
                        `value` int(64) NOT NULL,
                        `experimentID` int(16) NOT NULL,
                        `autoID` int(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Experiment` (
                              `experimentID` int(32) NOT NULL,
                              `performedBy` int(32) NOT NULL,
                              `hasInputStream` tinyint(1) NOT NULL,
                              `comment` text NOT NULL,
                              `startTime` datetime DEFAULT NULL,
                              `endTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Users` (
                         `ID` int(32) NOT NULL,
                         `Name` varchar(128) NOT NULL,
                         `Password` varchar(128) NOT NULL,
                         `Email` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cellData` (
                            `cellID` varchar(64) NOT NULL,
                            `timeStamp` varchar(64) NOT NULL,
                            `value` varchar(64) DEFAULT NULL,
                            `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `Data`
    ADD PRIMARY KEY (`autoID`);

ALTER TABLE `Experiment`
    ADD PRIMARY KEY (`experimentID`);

ALTER TABLE `Users`
    ADD UNIQUE KEY `IDA` (`ID`);

ALTER TABLE `cellData`
    ADD PRIMARY KEY (`ID`);


ALTER TABLE `Data`
    MODIFY `autoID` int(64) NOT NULL AUTO_INCREMENT;
ALTER TABLE `Users`
    MODIFY `ID` int(32) NOT NULL AUTO_INCREMENT;
ALTER TABLE `cellData`
    MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;