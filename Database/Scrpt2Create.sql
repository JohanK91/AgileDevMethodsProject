-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb2` DEFAULT CHARACTER SET utf8 ;
USE `mydb2` ;

-- -----------------------------------------------------
-- Table `mydb2`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Address` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(45) NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`User` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` INT NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  `Address_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_User_Address1_idx` (`Address_ID` ASC) VISIBLE,
  CONSTRAINT `fk_User_Address1`
    FOREIGN KEY (`Address_ID`)
    REFERENCES `mydb2`.`Address` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`Charity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Charity` (
  `User_ID` INT NOT NULL,
  `webpage` VARCHAR(45) NULL,
  `description` VARCHAR(450) NULL,
  PRIMARY KEY (`User_ID`),
  INDEX `fk_Charity_User1_idx` (`User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Charity_User1`
    FOREIGN KEY (`User_ID`)
    REFERENCES `mydb2`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`Driver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Driver` (
  `User_ID` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`User_ID`),
  INDEX `fk_Driver_User1_idx` (`User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Driver_User1`
    FOREIGN KEY (`User_ID`)
    REFERENCES `mydb2`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`Task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Task` (
  `ID` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  `description` VARCHAR(300) NOT NULL,
  `start_date` VARCHAR(45) NOT NULL,
  `end_date` VARCHAR(45) NULL,
  `ItemList_idItemList` INT NOT NULL,
  `Donor_ID` INT NOT NULL,
  `pickupAddress_ID` INT NOT NULL,
  `ItemList_ID` INT NOT NULL,
  `Driver_User_ID` INT NULL,
  `Charity_User_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Task_User1_idx` (`Donor_ID` ASC) VISIBLE,
  INDEX `fk_Task_Address1_idx` (`pickupAddress_ID` ASC) VISIBLE,
  INDEX `fk_Task_Driver1_idx` (`Driver_User_ID` ASC) VISIBLE,
  INDEX `fk_Task_Charity1_idx` (`Charity_User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Task_User1`
    FOREIGN KEY (`Donor_ID`)
    REFERENCES `mydb2`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Address1`
    FOREIGN KEY (`pickupAddress_ID`)
    REFERENCES `mydb2`.`Address` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Driver1`
    FOREIGN KEY (`Driver_User_ID`)
    REFERENCES `mydb2`.`Driver` (`User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Charity1`
    FOREIGN KEY (`Charity_User_ID`)
    REFERENCES `mydb2`.`Charity` (`User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`itemType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`itemType` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Description` VARCHAR(45) NULL,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`Task_has_itemType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Task_has_itemType` (
  `Task_ID` INT NOT NULL,
  `itemType_ID` INT NOT NULL,
  PRIMARY KEY (`Task_ID`, `itemType_ID`),
  INDEX `fk_Task_has_itemType_itemType1_idx` (`itemType_ID` ASC) VISIBLE,
  INDEX `fk_Task_has_itemType_Task1_idx` (`Task_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Task_has_itemType_Task1`
    FOREIGN KEY (`Task_ID`)
    REFERENCES `mydb2`.`Task` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_has_itemType_itemType1`
    FOREIGN KEY (`itemType_ID`)
    REFERENCES `mydb2`.`itemType` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb2`.`Charity_has_itemType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb2`.`Charity_has_itemType` (
  `Charity_User_ID` INT NOT NULL,
  `itemType_ID` INT NOT NULL,
  PRIMARY KEY (`Charity_User_ID`, `itemType_ID`),
  INDEX `fk_Charity_has_itemType_itemType1_idx` (`itemType_ID` ASC) VISIBLE,
  INDEX `fk_Charity_has_itemType_Charity1_idx` (`Charity_User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Charity_has_itemType_Charity1`
    FOREIGN KEY (`Charity_User_ID`)
    REFERENCES `mydb2`.`Charity` (`User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Charity_has_itemType_itemType1`
    FOREIGN KEY (`itemType_ID`)
    REFERENCES `mydb2`.`itemType` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


select * from user;
select * from itemtype;
select * from address;

insert into address (address, city, postcode, x, y)
values ("qaz", "wsx", 123, 11, 22);

insert into address (address, city, postcode, x, y)
values ("blabla", "gdsgfdg", 123, 11, 22);

insert into address (address, city, postcode, x, y)
values ("Hello", "There", 123, 11, 22);

Insert into user (userName, name, type, phone, pass, Address_ID)
values ("test0", "TestDoner", 0, 123, "root", 1);
  
Insert into user (userName, name, type, phone, pass, Address_ID)
values ("test1", "TestDriver", 1, 123, "root", 2);

Insert into user (userName, name, type, phone, pass, Address_ID)
values ("test2", "TestCharity", 2, 123, "root", 3);


-- clothes 
INSERT into itemtype (Description, Name) 
values ("Things you can wear in the summer", "Summer clothes");

INSERT into itemtype (Description, Name) 
values ("Things you can wear in the winter", "Winter clothes");

INSERT into itemtype (Description, Name) 
values ("Things you can wear on your head", "Hats");

INSERT into itemtype (Description, Name) 
values ("Things you can wear on your feet", "Shoes");


-- furniture
INSERT into itemtype (Description, Name) 
values ("Chairs and tables", "Dining furniture");

INSERT into itemtype (Description, Name) 
values ("Things you can sit or sleep in", "Beds and couches");

INSERT into itemtype (Description, Name) 
values ("Things you can put clothes or books in", "Bookcases and closets");

INSERT into itemtype (Description, Name) 
values ("General furniture", "Other furniture items");


-- electrical items
INSERT into itemtype (Description, Name) 
values ("Things that run on electricity", "Electrical items");

INSERT into itemtype (Description, Name) 
values ("Computer kinds of items", "Computer items");

-- battery powered items
INSERT into itemtype (Description, Name) 
values ("Things that run on batteries", "Battery powered items");


-- tools
INSERT into itemtype (Description, Name) 
values ("Crafting and work tools", "Tools");

-- toys
INSERT into itemtype (Description, Name) 
values ("Hard toys for children", "Hard toys");

INSERT into itemtype (Description, Name) 
values ("Soft toys for children", "Soft toys");

INSERT into itemtype (Description, Name) 
values ("Battery powered toys for children", "Battery toys");

-- fabrics
INSERT into itemtype (Description, Name) 
values ("Bed fabric items", "Bed covers");

INSERT into itemtype (Description, Name) 
values ("Kitchen and table fabrics", "Tablecloths");

INSERT into itemtype (Description, Name) 
values ("Fabrics on floor and windows", "Carpets and curtains");

select * from task;
select * from user;


-- hello
