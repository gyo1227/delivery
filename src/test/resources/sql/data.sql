truncate table user;
insert into user (email, password, name, phone, created_at, updated_at) values ('existUser@email.com', 'encodedPassword', 'existUser', '01012341234' ,now(), now())