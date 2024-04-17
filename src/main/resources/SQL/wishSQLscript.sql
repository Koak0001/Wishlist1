DROP DATABASE IF EXISTS wishdb;

CREATE DATABASE wishdb;
use wishdb;
CREATE TABLE IF NOT EXISTS Item (
  idItem INT NOT NULL AUTO_INCREMENT,
  ItemName VARCHAR(255) NOT NULL,
  ItemDescription VARCHAR(255) NULL,
  ItemPrice INT NULL,
  PRIMARY KEY (idItem));


CREATE TABLE IF NOT EXISTS ItemList (
  idItemList INT NOT NULL AUTO_INCREMENT,
  ListName VARCHAR(255) NOT NULL,
  PRIMARY KEY (idItemList));
  
  CREATE TABLE IF NOT EXISTS ListJunction (
  idItemList INT NOT NULL,
  idItem INT NOT NULL,
  PRIMARY KEY (idItem, idItemList),
    FOREIGN KEY (idItemList)
    REFERENCES ItemList (idItemList)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (idItem)
    REFERENCES Item (idItem)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
INSERT INTO ItemList(ListName) VALUES
('Liste 1'),
('Liste 2'),
('Liste 3');
    
INSERT INTO Item(ItemName) VALUES
('Item 1'),
('Item 2'),
('Item 3');

INSERT INTO ListJunction(idItemList, idItem) VALUES
(1, 1),
(1, 2),
(1, 3);