
-- drop tables at the start of a session
DROP TABLE IF EXISTS information;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS post_likes;
DROP TABLE IF EXISTS feed_tags;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS feed;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS translations;

create table if not exists categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_title VARCHAR(100) NOT NULL,
    category_description VARCHAR(255) NOT NULL,
    user_id INT NOT NULL
);


-- create schema for information


create table if not exists information
(
    info_id             int auto_increment primary key,
    category_id         int,
    info_title          varchar(255),
    created_by           varchar(255),
    created_date         date,
    updated_date        date,
    tag_id              int,
    short_info          text,
    info_article        text,
    foreign key category_foreign_key (category_id) references categories(category_id)
) engine = InnoDB;


-- schema for news

create table if not exists news
(
    news_id             int auto_increment primary key,
    news_title          varchar(255),
    news_summary        text,
    news_source         varchar(255),
    news_link           varchar(255),
    news_image_url      varchar(255),
    user_id             int,
    news_upload_date    date
) engine = InnoDB;


-- schema for roles
CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- schema for users
create table if not exists users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    dob DATE,
    role_id INT NOT NULL default 2,
    organization varchar(255),
    enabled boolean default true,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- schema for user roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
    );



create table if not exists feed (
    post_id int primary key auto_increment,
    post_image_url varchar(255),
    post_title varchar(255),
    post_description TEXT,
    post_time date,
    user_id int,
    foreign key (user_id) references users(id)
    ) engine = InnoDB;



create table if not exists tags (
    tag_id   int primary key auto_increment,
    tag_name varchar(100)
    ) engine = InnoDB;

CREATE TABLE IF NOT EXISTS feed_tags (
    post_id INT,
    tag_id INT,
    FOREIGN KEY (post_id) REFERENCES feed(post_id),
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id),
    PRIMARY KEY (post_id, tag_id)
    ) ENGINE = InnoDB;


create table if not exists post_likes (
    like_id int primary key auto_increment,
    post_id int,
    user_id int,
    created_at timestamp default current_timestamp,
    foreign key (post_id) references feed(post_id),
    foreign key (user_id) references users(id),
    constraint unique_post_user unique (post_id, user_id)
    ) engine = InnoDB;

CREATE TABLE IF NOT EXISTS comment
(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    comment_content TEXT NOT NULL,
    created_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id         INT  NOT NULL,
    post_id      INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES feed(post_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB;

create table if not exists translations (
    id int auto_increment primary key,
    translation_key varchar(255) not null,
    language varchar(10) not null,
    value text not null
) engine = InnoDB;

ALTER TABLE translations MODIFY value TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- create schema for event

create table if not exists event(
                                    event_id int primary key auto_increment,
                                    event_title varchar(255),
                                    event_description varchar(255),
                                    location varchar(255),
                                    event_date date,
                                    event_time time,
                                    user_id int,
                                    event_poster_url varchar(255),
                                    whyJoin text,
                                    benefits text,
                                    foreign key event_user_id_fk (user_id) references users(id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS user_profile (
    user_id INT PRIMARY KEY NOT NULL,
    profile_picture TEXT,
    bio TEXT,
    phone_number VARCHAR(20),
    show_phone_number BOOLEAN DEFAULT FALSE,
    show_dob BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) engine = InnoDB;

CREATE TRIGGER UMustHaveProfile AFTER INSERT ON users FOR EACH ROW INSERT INTO user_profile (user_id) VALUES (NEW.id);


