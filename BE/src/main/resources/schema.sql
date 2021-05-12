-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema baseball
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema baseball
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `baseball` DEFAULT CHARACTER SET utf8;
USE `baseball` ;

-- -----------------------------------------------------
-- Table `baseball`.`TEAM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`TEAM`;

CREATE TABLE IF NOT EXISTS `baseball`.`TEAM`
(
    `TEAM_ID`   INT          NOT NULL AUTO_INCREMENT,
    `TEAM_NAME` VARCHAR(200) NOT NULL DEFAULT '',
    PRIMARY KEY (`TEAM_ID`)
)DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `baseball`.`GAME`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`GAME`;

CREATE TABLE IF NOT EXISTS `baseball`.`GAME`
(
    `GAME_ID`         INT NOT NULL AUTO_INCREMENT,
    `HOME_TEAM_ID`    INT NOT NULL,
    `HOME_TEAM_SCORE` INT NOT NULL DEFAULT 0,
    `AWAY_TEAM_ID`    INT NOT NULL,
    `AWAY_TEAM_SCORE` INT NOT NULL DEFAULT 0,
    `NOW_INNING_ID`   INT NOT NULL,
    PRIMARY KEY (`GAME_ID`),
    INDEX             `fk_GAME_TEAM1_idx` (`HOME_TEAM_ID` ASC),
    INDEX             `fk_GAME_TEAM2_idx` (`AWAY_TEAM_ID` ASC),
    CONSTRAINT `fk_GAME_TEAM1`
        FOREIGN KEY (`HOME_TEAM_ID`)
            REFERENCES `baseball`.`TEAM` (`TEAM_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_GAME_TEAM2`
        FOREIGN KEY (`AWAY_TEAM_ID`)
            REFERENCES `baseball`.`TEAM` (`TEAM_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `baseball`.`PLAYER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`PLAYER`;

CREATE TABLE IF NOT EXISTS `baseball`.`PLAYER`
(
    `PLAYER_ID`   INT          NOT NULL AUTO_INCREMENT,
    `TEAM_ID`     INT          NOT NULL,
    `PLAYER_NAME` VARCHAR(200) NOT NULL DEFAULT '',
    `IS_PITCHER`   BOOLEAN     DEFAULT FALSE,
    PRIMARY KEY (`PLAYER_ID`),
    INDEX         `fk_PLAYER_TEAM_idx` (`TEAM_ID` ASC),
    CONSTRAINT `fk_PLAYER_TEAM`
        FOREIGN KEY (`TEAM_ID`)
            REFERENCES `baseball`.`TEAM` (`TEAM_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    TEAM_KEY INT
)DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `baseball`.`INNING`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`INNING`;

CREATE TABLE IF NOT EXISTS `baseball`.`INNING`
(
    `INNING_ID`       INT          NOT NULL,
    `GAME_ID`         INT          NOT NULL,
    `TEAM_ID`         INT          NOT NULL,
    `NOW_BATTER_ID`   INT          NOT NULL,
    `NOW_PITCHER_ID`  INT          NOT NULL,
    `INNING_NUMBER`   INT          NOT NULL DEFAULT 1,
    `SCORE`           INT          NULL     DEFAULT 0,
    `NOW_BALL_COUNT`  VARCHAR(100) NULL     DEFAULT '',
    `NOW_OUT_COUNT`   INT          NULL     DEFAULT 0,
    `NOW_BASE_STATUS` VARCHAR(45)  NULL     DEFAULT '',
    PRIMARY KEY (`INNING_ID`),
    INDEX             `fk_INNING_Game1_idx` (`GAME_ID` ASC),
    INDEX             `fk_INNING_PLAYER1_idx` (`NOW_BATTER_ID` ASC),
    INDEX             `fk_INNING_PLAYER2_idx` (`NOW_PITCHER_ID` ASC),
    CONSTRAINT `fk_INNING_Game1`
        FOREIGN KEY (`GAME_ID`)
            REFERENCES `baseball`.`GAME` (`GAME_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_INNING_PLAYER1`
        FOREIGN KEY (`NOW_BATTER_ID`)
            REFERENCES `baseball`.`PLAYER` (`PLAYER_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_INNING_PLAYER2`
        FOREIGN KEY (`NOW_PITCHER_ID`)
            REFERENCES `baseball`.`PLAYER` (`PLAYER_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    GAME_KEY INT
)DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `baseball`.`BATTING_STAT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`BATTING_STAT`;

CREATE TABLE IF NOT EXISTS `baseball`.`BATTING_STAT`
(
    `BATTING_STAT_ID`   INT            NOT NULL AUTO_INCREMENT,
    `GAME_ID`           INT            NOT NULL,
    `TEAM_ID`           INT            NOT NULL,
    `PLAYER_ID`         INT            NOT NULL,
    `AT_BAT`            INT            NULL DEFAULT 0,
    `HITS`              INT            NULL DEFAULT 0,
    `OUT`               INT            NULL DEFAULT 0,
    `AVERAGE_HIT_RATIO` DECIMAL(10, 3) NULL DEFAULT 0,
    PRIMARY KEY (`BATTING_STAT_ID`),
    INDEX               `fk_BATTING_STAT_GAME1_idx` (`GAME_ID` ASC),
    INDEX               `fk_BATTING_STAT_PLAYER1_idx` (`PLAYER_ID` ASC),
    CONSTRAINT `fk_BATTING_STAT_GAME1`
        FOREIGN KEY (`GAME_ID`)
            REFERENCES `baseball`.`GAME` (`GAME_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_BATTING_STAT_PLAYER1`
        FOREIGN KEY (`PLAYER_ID`)
            REFERENCES `baseball`.`PLAYER` (`PLAYER_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `baseball`.`MATCH`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baseball`.`MATCH`;

CREATE TABLE IF NOT EXISTS `baseball`.`MATCH`
(
    `GAME_ID`    INT NOT NULL,
    `TEAM_ID`    INT NOT NULL,
    `TEAM_SCORE` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`GAME_ID`, `TEAM_ID`),
    INDEX        `fk_GAME_has_TEAM_TEAM1_idx` (`TEAM_ID` ASC),
    INDEX        `fk_GAME_has_TEAM_GAME1_idx` (`GAME_ID` ASC),
    CONSTRAINT `fk_GAME_has_TEAM_GAME1`
        FOREIGN KEY (`GAME_ID`)
            REFERENCES `baseball`.`GAME` (`GAME_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_GAME_has_TEAM_TEAM1`
        FOREIGN KEY (`TEAM_ID`)
            REFERENCES `baseball`.`TEAM` (`TEAM_ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)DEFAULT CHARSET=utf8;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
