CREATE TABLE `user`
(
  `identifier`         CHAR(36)    NOT NULL,
  `email`              VARCHAR(24) NOT NULL,
  `password`           CHAR(60)    NOT NULL,
  `first_name`         VARCHAR(24) NOT NULL,
  `last_name`          VARCHAR(24) NOT NULL,
  `birth_date`         DATE        NOT NULL,
  `created_date`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `last_modified_date` DATETIME             DEFAULT NULL,
  `enabled`            BOOLEAN     NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`identifier`)
);

CREATE TABLE `refresh_token`
(
  `identifier` CHAR(36) NOT NULL COMMENT 'universally unique identifier',
  `token`      CHAR(36) NOT NULL COMMENT 'json web token',
  `expiration` DATETIME NOT NULL,
  `user`       CHAR(36) NOT NULL,
  PRIMARY KEY (`identifier`),
  CONSTRAINT `refresh_token_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`identifier`)
);

CREATE TABLE `location_country`
(
  `identifier` CHAR(36)    NOT NULL COMMENT 'universally unique identifier',
  `key`        VARCHAR(24) NOT NULL COMMENT 'resource key',
  PRIMARY KEY (`identifier`)
);

CREATE TABLE `location_region`
(
  `identifier`    CHAR(36)    NOT NULL COMMENT 'universally unique identifier',
  `key`           VARCHAR(24) NOT NULL COMMENT 'resource key',
  `domestic_name` VARCHAR(24) NOT NULL COMMENT 'domestic common name',
  `country`       CHAR(36)    NOT NULL,
  PRIMARY KEY (`identifier`),
  CONSTRAINT `location_region_ibfk_1` FOREIGN KEY (`country`) REFERENCES `location_country` (`identifier`)
);

CREATE TABLE `location_city`
(
  `identifier`    CHAR(36)                                         NOT NULL COMMENT 'universally unique identifier',
  `key`           VARCHAR(24)                                      NOT NULL COMMENT 'resource key',
  `domestic_name` VARCHAR(24)                                      NOT NULL COMMENT 'domestic common name',
  `region`        CHAR(36)                                         NOT NULL,
  `population`    INT                                              NOT NULL DEFAULT 0,
  `capital`       ENUM ('PRIMARY','ADMINISTRATIVE','MINOR','NONE') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`identifier`),
  CONSTRAINT `location_city_ibfk_1` FOREIGN KEY (`region`) REFERENCES `location_region` (`identifier`)
);

CREATE TABLE `crime_classification`
(
  `identifier` CHAR(36)    NOT NULL COMMENT 'universally unique identifier',
  `key`        VARCHAR(24) NOT NULL COMMENT 'resource key',
  PRIMARY KEY (`identifier`)
);

CREATE TABLE `crime_perpetrator`
(
  `identifier` CHAR(36) NOT NULL COMMENT 'universally unique identifier',
  `first_name` VARCHAR(24)       DEFAULT NULL,
  `last_name`  VARCHAR(24)       DEFAULT NULL,
  `age`        INT      NOT NULL DEFAULT 0,
  `caught`     BOOLEAN  NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`identifier`)
);

CREATE TABLE `crime`
(
  `identifier`     CHAR(36) NOT NULL COMMENT 'universally unique identifier	',
  `date`           DATE     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `city`           CHAR(36) NOT NULL,
  `classification` CHAR(36) NOT NULL,
  `perpetrator`    CHAR(36)          DEFAULT NULL,
  `description`    TEXT     NOT NULL,
  `confirmed`      BOOLEAN  NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`identifier`),
  CONSTRAINT `crime_ibfk_1` FOREIGN KEY (`city`) REFERENCES `location_city` (`identifier`),
  CONSTRAINT `crime_ibfk_2` FOREIGN KEY (`classification`) REFERENCES `crime_classification` (`identifier`),
  CONSTRAINT `crime_ibfk_3` FOREIGN KEY (`perpetrator`) REFERENCES `crime_perpetrator` (`identifier`)
);
