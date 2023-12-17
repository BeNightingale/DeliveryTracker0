USE delivery_tracking;
CREATE TABLE deliveries
(
    delivery_id                 INT NOT NULL AUTO_INCREMENT,
    delivery_number             VARCHAR(30) NOT NULL,
    delivery_status             VARCHAR(350) NOT NULL,
    status_description          VARCHAR(3000), ### krótki polski w InPoSt
    deliverer                   VARCHAR(100) NOT NULL,
    delivery_description        VARCHAR(2000),
    finished                    BOOLEAN NOT NULL,
    PRIMARY KEY (delivery_id)
);

CREATE TABLE history
(
    id                          INT NOT NULL AUTO_INCREMENT,
    delivery_id                 INT NOT NULL,
    delivery_number             VARCHAR(30) NOT NULL,
    delivery_status             VARCHAR(350) NOT NULL,
    status_description          VARCHAR(3000),
    status_change_datetime      DATETIME,
    PRIMARY KEY (id)
);

INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, finished)
VALUES ('1', '12345678', 'DELIVERED', 'INPOST', true);
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, finished)
VALUES ('2', '660166696359300112430272', 'ON_THE_ROAD', 'INPOST', false);
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, delivery_description, finished)
VALUES ('3', '605552596359300024984977', 'CONFIRMED', 'INPOST', 'jacket', false);
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, status_description, finished)
VALUES ('4', '660166976359300115325076', 'NOT_FOUND', 'INPOST', 'pen', false);

INSERT INTO history(id, delivery_id, delivery_number, delivery_status, status_description, status_change_datetime)
VALUES ('1', '1', '12345678', 'DELIVERED', null, '2023-10-12 12:15:01');