create table user
(   id int primary key auto_increment,
    username varchar(30) unique,
    encrypted_password varchar(200),
    avatar varchar(100),
    created_at datetime,
    updated_at datetime
)