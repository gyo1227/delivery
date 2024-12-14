truncate table user;
insert into user (email, password, nickname, provider, created_at, updated_at) values ('existUser@email.com', 'encodedPassword', 'existUser', 'LOCAL' ,now(), now())