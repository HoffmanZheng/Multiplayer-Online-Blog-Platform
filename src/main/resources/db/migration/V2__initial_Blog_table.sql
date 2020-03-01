create table blog
(   id int primary key auto_increment,
    title varchar(30),
    description varchar(200),
    content text,
    user_id int,
    created_at datetime,
    updated_at datetime
)