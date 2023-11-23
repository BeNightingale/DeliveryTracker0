USE delivery_tracking;
CREATE TABLE deliveries
(
    delivery_id             INT NOT NULL AUTO_INCREMENT,
    delivery_number         VARCHAR(30) NOT NULL,
    delivery_status         VARCHAR(350) NOT NULL,
    deliverer               VARCHAR(100) NOT NULL,
    delivery_description    VARCHAR(2000),
    PRIMARY KEY (delivery_id)
);

INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer)
VALUES ('1', '12345678', 'DELIVERED', 'InPost');
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer)
VALUES ('2', '660166696359300112430272', 'ON_THE_ROAD', 'InPost');
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, delivery_description)
VALUES ('3', '605552596359300024984977', 'CONFIRMED', 'InPost', 'jacket');
INSERT INTO deliveries(delivery_id, delivery_number, delivery_status, deliverer, delivery_description)
VALUES ('4', '660166976359300115325076', 'NOT_FOUND', 'InPost', 'pen');