create table order_status
(
    id     int identity
        constraint PK_orderstatus
            primary key,
    status nvarchar(100)
        unique
)
go

create table payment_method
(
    id   int identity
        constraint PK_paymentmethod
            primary key,
    name nvarchar(100)
        unique
)
go

create table product_category
(
    id            int identity
        constraint PK_category
            primary key,
    category_name nvarchar(200)
        unique
)
go

create table product
(
    id            int identity
        constraint PK_product
            primary key,
    category_id   int
        constraint FK_product_category
            references product_category,
    name          nvarchar(500),
    description   nvarchar(4000),
    product_image nvarchar(1000)
)
go

create table product_item
(
    id            int identity
        constraint PK_productitem
            primary key,
    product_id    int
        constraint FK_proditem_product
            references product,
    sku           nvarchar(20),
    qty_in_stock  int,
    product_image nvarchar(1000),
    price         float
)
go

create table promotion
(
    id            int identity
        constraint PK_promo
            primary key,
    name          nvarchar(200),
    description   nvarchar(2000),
    discount_rate int,
    start_date    datetime,
    end_date      datetime
)
go

create table shipping_method
(
    id    int identity
        constraint PK_shipmethod
            primary key,
    name  nvarchar(100)
        unique,
    price float
)
go

create table site_user
(
    id            int identity
        constraint PK_user
            primary key,
    name          nvarchar(100),
    email_address nvarchar(350)
        unique,
    phone_number  nvarchar(20)
        unique,
    password      nvarchar(500),
    role          int
)
go

create table address
(
    id           int identity
        constraint PK_address
            primary key,
    unit_number  nvarchar(20),
    address_line nvarchar(500),
    city         nvarchar(200),
    district     nvarchar(200),
    user_id      int
        constraint FK_user_address
            references site_user
)
go

create table shop_order
(
    id               int identity
        constraint PK_shoporder
            primary key,
    user_id          int
        constraint FK_shoporder_user
            references site_user,
    order_date       datetime,
    shipping_address int
        constraint FK_shoporder_shipaddress
            references address,
    shipping_method  int
        constraint FK_shoporder_shipmethod
            references shipping_method,
    order_total      float,
    order_status     int
        constraint FK_shoporder_status
            references order_status,
    promotion_id     int
        constraint FK_shoporder_pro
            references promotion,
    payment_method   int
        constraint FK_shoporder_paymentmethod
            references payment_method
)
go

create table order_line
(
    id              int identity
        constraint PK_orderline
            primary key,
    product_item_id int
        constraint FK_orderline_proditem
            references product_item,
    order_id        int
        constraint FK_orderline_order
            references shop_order,
    qty             int,
    price           float
)
go

create table shopping_cart
(
    id      int identity
        constraint PK_shopcart
            primary key,
    user_id int
        constraint FK_shopcart_user
            references site_user
)
go

create table shopping_cart_item
(
    id              int identity
        constraint PK_shopcartitem
            primary key,
    cart_id         int
        constraint FK_shopcartitem_shopcart
            references shopping_cart,
    product_item_id int
        constraint FK_shopcartitem_proditem
            references product_item,
    qty             int
)
go

create table variation
(
    id   int identity
        constraint PK_variation
            primary key,
    name nvarchar(500)
)
go

create table variation_option
(
    id           int identity
        constraint PK_varoption
            primary key,
    variation_id int
        constraint FK_varoption_variation
            references variation,
    value        nvarchar(200)
)
go

create table product_configuration
(
    product_item_id     int
        constraint FK_prodconf_proditem
            references product_item,
    variation_option_id int
        constraint FK_prodconf_varoption
            references variation_option
)
go


