DROP DATABASE IF EXISTS skodatabasen;
CREATE DATABASE IF NOT EXISTS skodatabasen;

USE skodatabasen;
DELIMITER ;

-- här bryts 3NF eftersom 'ort' är funktionellt beroende av 'postnr'
-- att bryta ut ortnamnet till en egen tabell tycker jag är överflödigt och introducerar onödig komplexitet
-- om alla i Sverige bodde på samma ort hade man slösat (1+17)B * 10^7 = 180MB

CREATE TABLE IF NOT EXISTS adress(
id INT AUTO_INCREMENT PRIMARY KEY, 
postnr VARCHAR(5) NOT NULL,
ort VARCHAR(17) NOT NULL, 
gatunamn VARCHAR(30) NOT NULL, 
husnummer VARCHAR(5) NOT NULL
);

INSERT INTO adress(postnr, ort, gatunamn, husnummer)
VALUES
('13738','VÄSTERHANINGE','Villavägen','358'),
('12346','ENSKEDE','Hejgatan','88'),
('56566','FARSTA','Tjohojvägen','67'),
('87654','ÄLVSJÖ','Blävägen','21');

CREATE TABLE IF NOT EXISTS kund(
id INT AUTO_INCREMENT PRIMARY KEY,
namn VARCHAR(32) NOT NULL,
password VARCHAR(32) NOT NULL,
email VARCHAR(32) NOT NULL,
telefon VARCHAR(16) NOT NULL,
adressId INT NOT NULL,
FOREIGN KEY (adressId) REFERENCES adress(id)
);

INSERT INTO kund(namn, password, email, telefon, adressId)
VALUES
('Reinard Räv', '1234', 'firefox@hotmail.com','0701234656', 4),
('Lille Skutt', '123', 'morotfan47@gmail.com','07012341111',2),
('Bamse Björn', '123', 'dunderhonung@gmail.com','0701234656',3),
('Skal Man', '123', 'sleepyturtle@snailmail.com','0704321656', 1),
('Brumme Lisa', '123', 'krokodilhatarn@gmail.com','0709898656',3),
('Krösus Sork', '123', 'gal1rik@yahoo.com','0701243333',1);

CREATE TABLE IF NOT EXISTS sko(
id INT AUTO_INCREMENT PRIMARY KEY,
pris INT NOT NULL,
brand VARCHAR(16) NOT NULL,
color VARCHAR(16) NOT NULL
);

INSERT INTO sko(pris, brand, color)
VALUES
(400,'Crocs','Blå'),
(600,'Crocs','Vit'),
(800,'Nike','Svart'),
(1200,'Nike','Grå'),
(1000,'Adidas','Vit'),
(500,'Adidas','Grön'),
(700,'Ecco','Gul'),
(900,'Ecco','Orange'),
(10001,'LV','Guld');

CREATE TABLE IF NOT EXISTS sko_details(
id INT AUTO_INCREMENT PRIMARY KEY,
antal INT DEFAULT 0,
storlek INT NOT NULL,
skoId INT,
FOREIGN KEY (skoId) REFERENCES sko(id)
);

INSERT INTO sko_details(antal, storlek, skoId)
VALUES
(0, 40, 1),
(100, 47, 1),
(100, 47, 2),
(100, 47, 3),
(100, 47, 4),
(100, 47, 5),
(100, 47, 6),
(100, 47, 7),
(100, 47, 8),
(100, 36, 8),
(100, 36, 7),
(100, 36, 6),
(100, 36, 5),
(100, 36, 4),
(100, 36, 3),
(100, 36, 2),
(100, 36, 1),
(100, 1, 9);

CREATE TABLE IF NOT EXISTS kategori(
id INT AUTO_INCREMENT PRIMARY KEY,
namn VARCHAR(16)
);

INSERT INTO kategori(namn)
VALUES
('Sneaker'),
('Sport'),
('Friluftsliv'),
('Sandaler'),
('Klackar');


CREATE TABLE IF NOT EXISTS kat_tillhörighet(
katId INT NOT NULL,
skoId INT NOT NULL,
PRIMARY KEY (katId, skoId),
FOREIGN KEY (katId) REFERENCES kategori(id) ON DELETE CASCADE,
FOREIGN KEY (skoId) REFERENCES sko(id) ON DELETE CASCADE
);

INSERT INTO kat_tillhörighet()
VALUES
(4,1),
(4,2),
(3,1),
(5,2),
(1,2),
(1,3),
(1,5),
(3,6),
(4,6),
(1,6),
(1,7),
(1,8),
(2,9);

CREATE TABLE IF NOT EXISTS beställning(
id INT AUTO_INCREMENT PRIMARY KEY,
kundId INT,
FOREIGN KEY (kundId) REFERENCES kund(id) ON DELETE SET NULL, 
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS beställning_details(
id INT AUTO_INCREMENT PRIMARY KEY,
orderId INT NOT NULL,
sko_detailsId INT NOT NULL,
antal INT NOT NULL CHECK (antal > 0),
FOREIGN KEY (orderId) REFERENCES beställning(id),
FOREIGN KEY (sko_detailsId) REFERENCES sko_details(id) 
);

CREATE TABLE IF NOT EXISTS out_of_stock(
id INT AUTO_INCREMENT PRIMARY KEY,
sko_detailsId INT NOT NULL,
datum TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (sko_detailsId) REFERENCES sko_details(id) ON DELETE CASCADE
);

INSERT INTO out_of_stock(sko_detailsId)
VALUES(1);

-- -> Filter: ((s.color = 'Svart') and (s.brand = 'Nike'))  (cost=1.15 rows=1) (actual time=0.0161..0.018 rows=1 loops=1)
-- innan:   -> Table scan on s  (cost=1.15 rows=9) (actual time=0.0116..0.0137 rows=9 loops=1)
-- efter:   -> Index lookup on s using idx_sko_color (color='Svart')  (cost=0.272 rows=1) (actual time=0.0262..0.0268 rows=1 loops=1)
CREATE INDEX idx_sko_color ON sko(color);
CREATE INDEX idx_sko_brand ON sko(brand);
CREATE INDEX idx_sko_details_storlek ON sko_details(storlek);
CREATE INDEX idx_kategori_namn ON kategori(namn);

-- CREATE INDEX idx_beställning_created ON beställning(created);
-- ^skulle nog vara bra om det bara var DATE och inte TIMESTAMP

DELIMITER //

CREATE TRIGGER ins_out_of_stock AFTER UPDATE ON sko_details
	FOR EACH ROW
	BEGIN
		IF NEW.antal < 1 THEN
        INSERT INTO out_of_stock(sko_detailsId)
        VALUES(NEW.id);
        END IF;
	END//
    
CREATE PROCEDURE insert_best(
	IN IN_kund_id INT
)
BEGIN
	UPDATE beställning
    SET active = false
	WHERE kundId = IN_kund_id;
    
    INSERT INTO beställning(kundId) VALUES(IN_kund_id);
END//

CREATE PROCEDURE AddToCart(
    IN IN_sko_details_id INT,
    IN IN_antal INT,
    IN IN_kund_id INT,
    IN IN_best_id INT
)
BEGIN
    -- variabler
    DECLARE SP_i_lager INT;
    DECLARE SP_kund_id INT;
    DECLARE SP_count INT;
	    
	DECLARE errmsg TEXT;
    -- error handler
    DECLARE EXIT HANDLER FOR 45000
		BEGIN
			ROLLBACK;
			RESIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = errmsg;
        END;
	DECLARE EXIT HANDLER FOR 1645
		BEGIN
			ROLLBACK;
            RESIGNAL SET MESSAGE_TEXT = 'DU FÅR INTE KÖPA MINDRE ÄN EN (1!) SKO!';
        END;
    -- validera indata
    START TRANSACTION;
	SET errmsg = 'Kaffe i servern';

    IF IN_kund_id IS NULL THEN
        ROLLBACK;
		SET errmsg = 'Måste specifiera en kund';
		RESIGNAL SQLSTATE '45000';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM kund WHERE id = IN_kund_id) THEN
        ROLLBACK;
		SET errmsg = 'Kunden finns inte i databasen';
		RESIGNAL SQLSTATE '45000';
    END IF;

    IF IN_sko_details_id IS NULL THEN
		ROLLBACK;
        SET errmsg = 'Måste välja en sko';
		RESIGNAL SQLSTATE '45000';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM sko_details WHERE id = IN_sko_details_id) THEN
		ROLLBACK;
        SET errmsg = 'Skon finns inte i databasen';
		RESIGNAL SQLSTATE '45000';
    END IF;

    IF IN_antal < 1 THEN
		ROLLBACK;
        SET errmsg = 'Måste köpa minst en sko';
		RESIGNAL SQLSTATE '45000';
    END IF;
    -- se till att nog med skor finns i lager
    SELECT antal
    INTO SP_i_lager
    FROM sko_details
    WHERE id = IN_sko_details_id;

    IF SP_i_lager < IN_antal THEN
		ROLLBACK;
		SET errmsg = 'Det finns inte nog med skor i lagret';
        RESIGNAL SQLSTATE '45000';
        -- throw new Exception(Det finns inte nog med skor i lager);
    END IF;
    -- om ingen beställning är angiven, skapa en
    IF IN_best_id IS NULL THEN
        CALL insert_best(IN_kund_id);
        SET IN_best_id = last_insert_id();
    ELSE
        -- annars kolla att angivna beställningen finns, är aktiv och tillhör kunden
        SELECT kundId
        INTO SP_kund_id
        FROM beställning
        WHERE id = IN_best_id AND active IS TRUE;

        IF SP_kund_id != IN_kund_id THEN
			ROLLBACK;
            SET errmsg = 'Inaktiv eller fel beställning för kunden';
			RESIGNAL SQLSTATE '45000';
        END IF;
    END IF;
	
    -- se om skon redan finns i beställningen
    SELECT COUNT(bd.id)
    INTO SP_count
    FROM beställning_details bd
    WHERE bd.orderId = IN_best_id AND bd.sko_detailsId = IN_sko_details_id;

    IF SP_count > 0 THEN
        -- kanske kolla om nya antalet + gamla antalet inte överskrider lagersaldo ?
        UPDATE beställning_details bd
        SET antal = antal + IN_antal
        WHERE bd.orderId = IN_best_id AND bd.sko_detailsId = IN_sko_details_id;
    ELSE
        INSERT INTO beställning_details(orderId, sko_detailsId, antal)
        VALUES(IN_best_id, IN_sko_details_id, IN_antal);
    END IF;
	-- uppdatera lagersaldo
	UPDATE sko_details sd 
		SET antal = antal - IN_antal
		WHERE sd.id = IN_sko_details_id;
    COMMIT;
END//

DELIMITER ;