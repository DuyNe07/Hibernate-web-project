USE duckstore
USE testDuckstore

CREATE TABLE site_user (
	id INT IDENTITY(1,1),
	name NVARCHAR(100),
	email_address NVARCHAR(350) UNIQUE,
	phone_number NVARCHAR(20) UNIQUE,
	password NVARCHAR(500),
	role int,
	CONSTRAINT PK_user PRIMARY KEY (id),
);

CREATE TABLE address (
	id int IDENTITY(1,1),
	unit_number NVARCHAR(20),
	address_line NVARCHAR(500),
	city NVARCHAR(200),
	district NVARCHAR(200),
	user_id int,
	CONSTRAINT PK_address PRIMARY KEY (id),
	CONSTRAINT FK_user_address FOREIGN KEY(user_id) REFERENCES site_user(id)
);

CREATE TABLE product_category (
	id INT IDENTITY(1,1),
	category_name NVARCHAR(200) UNIQUE,
	CONSTRAINT PK_category PRIMARY KEY (id),
);


CREATE TABLE promotion (
	id INT IDENTITY(1,1),
	name NVARCHAR(200),
	description NVARCHAR(2000),
	discount_rate INT,
	start_date DATETIME,
	end_date DATETIME,
	CONSTRAINT PK_promo PRIMARY KEY (id)
);


CREATE TABLE product (
	id INT IDENTITY(1,1),
	category_id INT,
	name NVARCHAR(500),
	description NVARCHAR(4000),
	product_image NVARCHAR(1000),
	CONSTRAINT PK_product PRIMARY KEY (id),
	CONSTRAINT FK_product_category FOREIGN KEY (category_id) REFERENCES product_category(id)
);

CREATE TABLE product_item (
	id INT IDENTITY(1,1),
	product_id INT,
	sku NVARCHAR(20),
	qty_in_stock INT,
	product_image NVARCHAR(1000),
	price INT,
	CONSTRAINT PK_productitem PRIMARY KEY (id),
	CONSTRAINT FK_proditem_product FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE variation (
	id INT IDENTITY(1,1),
	name NVARCHAR(500),
	CONSTRAINT PK_variation PRIMARY KEY (id),
);

CREATE TABLE variation_option (
	id INT IDENTITY(1,1),
	variation_id INT,
	value NVARCHAR(200),
	CONSTRAINT PK_varoption PRIMARY KEY (id),
	CONSTRAINT FK_varoption_variation FOREIGN KEY (variation_id) REFERENCES variation (id)
);

CREATE TABLE product_configuration (
	product_item_id INT,
	variation_option_id INT,
	CONSTRAINT FK_prodconf_proditem FOREIGN KEY (product_item_id) REFERENCES product_item (id),
	CONSTRAINT FK_prodconf_varoption FOREIGN KEY (variation_option_id) REFERENCES variation_option (id)
);

CREATE TABLE shopping_cart (
	id INT IDENTITY(1,1),
	user_id INT,
	CONSTRAINT PK_shopcart PRIMARY KEY (id),
	CONSTRAINT FK_shopcart_user FOREIGN KEY (user_id) REFERENCES site_user (id)
);

CREATE TABLE shopping_cart_item (
	id INT IDENTITY(1,1),
	cart_id INT,
	product_item_id INT,
	qty INT,
	CONSTRAINT PK_shopcartitem PRIMARY KEY (id),
	CONSTRAINT FK_shopcartitem_shopcart FOREIGN KEY (cart_id) REFERENCES shopping_cart (id),
	CONSTRAINT FK_shopcartitem_proditem FOREIGN KEY (product_item_id) REFERENCES product_item (id)
);

CREATE TABLE shipping_method (
	id INT IDENTITY(1,1),
	name NVARCHAR(100) UNIQUE,
	price INT,
	CONSTRAINT PK_shipmethod PRIMARY KEY (id)
);

CREATE TABLE order_status (
	id INT IDENTITY(1,1),
	status NVARCHAR(100) UNIQUE,
	CONSTRAINT PK_orderstatus PRIMARY KEY (id)
);

CREATE TABLE shop_order (
	id INT IDENTITY(1,1),
	user_id INT,
	order_date DATETIME,
	shipping_address INT,
	shipping_method INT,
	order_total INT,
	order_status INT,
	promotion_id INT,
	CONSTRAINT PK_shoporder PRIMARY KEY (id),
	CONSTRAINT FK_shoporder_user FOREIGN KEY (user_id) REFERENCES site_user (id),
	CONSTRAINT FK_shoporder_shipaddress FOREIGN KEY (shipping_address) REFERENCES address (id),
	CONSTRAINT FK_shoporder_shipmethod FOREIGN KEY (shipping_method) REFERENCES shipping_method (id),
	CONSTRAINT FK_shoporder_status FOREIGN KEY (order_status) REFERENCES order_status (id),
	CONSTRAINT FK_shoporder_pro FOREIGN KEY (promotion_id) REFERENCES promotion (id)
);

CREATE TABLE order_line (
	id INT IDENTITY(1,1),
	product_item_id INT,
	order_id INT,
	qty INT,
	price INT,
	CONSTRAINT PK_orderline PRIMARY KEY (id),
	CONSTRAINT FK_orderline_proditem FOREIGN KEY (product_item_id) REFERENCES product_item (id),
	CONSTRAINT FK_orderline_order FOREIGN KEY (order_id) REFERENCES shop_order (id)
);

GO 

INSERT INTO variation(name)
VALUES ('size'),
	('color');

GO

INSERT INTO variation_option(variation_id, value)
VALUES (1, 'S'),
	(1, 'L'),
	(1, 'M'),
	(1, 'XL'),
	(1, 'XXL'),
	(2, 'Blue'),
	(2, 'Red'),
	(2, 'White'),
	(2, 'Black'),
	(2, 'Brown');