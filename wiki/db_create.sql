SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `bd2-baza` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `bd2-baza` ;

-- -----------------------------------------------------
-- Table `bd2-baza`.`kategoria`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`kategoria` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Nr_polki` INT(11) NOT NULL ,
  PRIMARY KEY (`Id`) ,
  INDEX `Nr_polki` (`Nr_polki` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`czesci`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`czesci` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Ilosc_wymagana` INT(11) NOT NULL ,
  `Ilosc_aktualna` INT(11) NOT NULL ,
  `kategoria_Id` INT(11) NOT NULL ,
  PRIMARY KEY (`Id`) ,
  INDEX `fk_czesci_kategoria_idx` (`kategoria_Id` ASC) ,
  CONSTRAINT `fk_czesci_kategoria`
    FOREIGN KEY (`kategoria_Id` )
    REFERENCES `bd2-baza`.`kategoria` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`dostawca`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`dostawca` (
  `Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Adres` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Regon` VARCHAR(9) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  UNIQUE INDEX `Regon` (`Regon` ASC) ,
  PRIMARY KEY (`Regon`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`klient`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`klient` (
  `Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Adres` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Regon` VARCHAR(9) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  UNIQUE INDEX `Regon` (`Regon` ASC) ,
  PRIMARY KEY (`Regon`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`typ_umow`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`typ_umow` (
  `Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Max_hajs` INT(11) NOT NULL ,
  PRIMARY KEY (`Nazwa`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`pracownik`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`pracownik` (
  `Imie` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Nazwisko` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Adres` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Pesel` VARCHAR(11) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Pensja` INT(11) NOT NULL ,
  `typ_umow_Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  PRIMARY KEY (`Pesel`) ,
  INDEX `fk_pracownik_typ_umow1_idx` (`typ_umow_Nazwa` ASC) ,
  CONSTRAINT `fk_pracownik_typ_umow1`
    FOREIGN KEY (`typ_umow_Nazwa` )
    REFERENCES `bd2-baza`.`typ_umow` (`Nazwa` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`lokalizacja`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`lokalizacja` (
  `Nr_lokalizacji` INT(11) NOT NULL ,
  `Nazwa` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `Adres` varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  UNIQUE INDEX `Nr_filii` (`Nr_lokalizacji` ASC) ,
  PRIMARY KEY (`Nr_lokalizacji`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`zamowienia_wyj`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`zamowienia_wyj` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Data` DATE NOT NULL ,
  `faktury_Nr_faktury` INT(11) NOT NULL ,
  `klient_Regon` VARCHAR(9) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `pracownik_Pesel` VARCHAR(11) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `filia_Nr_filii` INT(11) NOT NULL ,
  PRIMARY KEY (`Id`) ,
  INDEX `fk_zamowienia_wyj_klient1_idx` (`klient_Regon` ASC) ,
  INDEX `fk_zamowienia_wyj_pracownik1_idx` (`pracownik_Pesel` ASC) ,
  INDEX `fk_zamowienia_wyj_filia1_idx` (`filia_Nr_filii` ASC) ,
  CONSTRAINT `fk_zamowienia_wyj_klient1`
    FOREIGN KEY (`klient_Regon` )
    REFERENCES `bd2-baza`.`klient` (`Regon` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zamowienia_wyj_pracownik1`
    FOREIGN KEY (`pracownik_Pesel` )
    REFERENCES `bd2-baza`.`pracownik` (`Pesel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zamowienia_wyj_filia1`
    FOREIGN KEY (`filia_Nr_filii` )
    REFERENCES `bd2-baza`.`lokalizacja` (`Nr_lokalizacji` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`faktury_przychodowe`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`faktury_przychodowe` (
  `Nr_faktury` INT(11) NOT NULL ,
  `Korekta` INT(11) NULL DEFAULT NULL ,
  `zamowienia_wyj_Id` INT(11) NOT NULL ,
  PRIMARY KEY (`Nr_faktury`) ,
  INDEX `fk_korekta_rozch_idx` (`Korekta` ASC) ,
  INDEX `fk_faktury_przychodowe_zamowienia_wyj1_idx` (`zamowienia_wyj_Id` ASC) ,
  CONSTRAINT `fk_korekta_przych`
    FOREIGN KEY (`Korekta` )
    REFERENCES `bd2-baza`.`faktury_przychodowe` (`Nr_faktury` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_faktury_przychodowe_zamowienia_wyj1`
    FOREIGN KEY (`zamowienia_wyj_Id` )
    REFERENCES `bd2-baza`.`zamowienia_wyj` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`zamowienia_wej`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`zamowienia_wej` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT ,
  `Data` DATE NOT NULL ,
  `dostawca_Regon` VARCHAR(9) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `pracownik_Pesel` VARCHAR(11) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  `filia_Nr_filii` INT(11) NOT NULL ,
  `nr_faktury_rozch` INT(11) NOT NULL ,
  PRIMARY KEY (`Id`) ,
  INDEX `fk_zamowienia_wej_dostawca1_idx` (`dostawca_Regon` ASC) ,
  INDEX `fk_zamowienia_wej_pracownik1_idx` (`pracownik_Pesel` ASC) ,
  INDEX `fk_zamowienia_wej_filia1_idx` (`filia_Nr_filii` ASC) ,
  CONSTRAINT `fk_zamowienia_wej_dostawca1`
    FOREIGN KEY (`dostawca_Regon` )
    REFERENCES `bd2-baza`.`dostawca` (`Regon` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zamowienia_wej_pracownik1`
    FOREIGN KEY (`pracownik_Pesel` )
    REFERENCES `bd2-baza`.`pracownik` (`Pesel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zamowienia_wej_filia1`
    FOREIGN KEY (`filia_Nr_filii` )
    REFERENCES `bd2-baza`.`lokalizacja` (`Nr_lokalizacji` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`pozycje_zam_wej`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`pozycje_zam_wej` (
  `Ilosc` INT(11) NOT NULL ,
  `czesci_Id` INT(11) NOT NULL ,
  `zamowienia_wej_Id` INT(11) NOT NULL ,
  INDEX `fk_pozycje_zam_wej_czesci1_idx` (`czesci_Id` ASC) ,
  INDEX `fk_pozycje_zam_wej_zamowienia_wej1_idx` (`zamowienia_wej_Id` ASC) ,
  CONSTRAINT `fk_pozycje_zam_wej_czesci1`
    FOREIGN KEY (`czesci_Id` )
    REFERENCES `bd2-baza`.`czesci` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pozycje_zam_wej_zamowienia_wej1`
    FOREIGN KEY (`zamowienia_wej_Id` )
    REFERENCES `bd2-baza`.`zamowienia_wej` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`pozycje_zam_wyj`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`pozycje_zam_wyj` (
  `Ilosc` INT(11) NOT NULL ,
  `czesci_Id` INT(11) NOT NULL ,
  `zamowienia_wyj_Id` INT(11) NOT NULL ,
  INDEX `fk_pozycje_zam_wyj_czesci1_idx` (`czesci_Id` ASC) ,
  INDEX `fk_pozycje_zam_wyj_zamowienia_wyj1_idx` (`zamowienia_wyj_Id` ASC) ,
  CONSTRAINT `fk_pozycje_zam_wyj_czesci1`
    FOREIGN KEY (`czesci_Id` )
    REFERENCES `bd2-baza`.`czesci` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pozycje_zam_wyj_zamowienia_wyj1`
    FOREIGN KEY (`zamowienia_wyj_Id` )
    REFERENCES `bd2-baza`.`zamowienia_wyj` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;


-- -----------------------------------------------------
-- Table `bd2-baza`.`Inwentaryzacja`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`Inwentaryzacja` (
  `Id` INT NOT NULL ,
  `Data` DATETIME NULL ,
  `pracownik_Pesel` VARCHAR(11) CHARACTER SET 'utf8' COLLATE 'utf8_polish_ci' NOT NULL ,
  PRIMARY KEY (`Id`) ,
  INDEX `fk_Inwentaryzacja_pracownik1_idx` (`pracownik_Pesel` ASC) ,
  CONSTRAINT `fk_Inwentaryzacja_pracownik1`
    FOREIGN KEY (`pracownik_Pesel` )
    REFERENCES `bd2-baza`.`pracownik` (`Pesel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd2-baza`.`faktury_rozchodowe`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bd2-baza`.`faktury_rozchodowe` (
  `Nr_faktury` INT(11) NOT NULL ,
  `Korekta` INT(11) NULL DEFAULT NULL ,
  `zamowienia_wej_Id` INT(11) NOT NULL ,
  PRIMARY KEY (`Nr_faktury`) ,
  INDEX `fk_autoref_faktury_rozch_idx` (`Korekta` ASC) ,
  INDEX `fk_faktury_rozchodowe_zamowienia_wej1_idx` (`zamowienia_wej_Id` ASC) ,
  CONSTRAINT `fk_autoref_faktury_rozch`
    FOREIGN KEY (`Korekta` )
    REFERENCES `bd2-baza`.`faktury_rozchodowe` (`Nr_faktury` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_faktury_rozchodowe_zamowienia_wej1`
    FOREIGN KEY (`zamowienia_wej_Id` )
    REFERENCES `bd2-baza`.`zamowienia_wej` (`Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_polish_ci;

USE `bd2-baza` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
