INSERT INTO users(id,username,email,password,enable,avatar) VALUES (1, '四哥', 'ting6405@gmail.com','$2a$10$0bvs7K6EiFxhdHozEaEtW.bdTs7WSSbBhYPxzSTlRFjPek1qLep5q',true,'/static/images/avatar/man.png');
INSERT INTO users(id,username,email,password,enable,avatar) VALUES (2, '奥健', 'aojianshop@gmail.com','$2a$10$0bvs7K6EiFxhdHozEaEtW.bdTs7WSSbBhYPxzSTlRFjPek1qLep5q',true,'/static/images/avatar/man.png');
INSERT INTO users(id,username,email,password,enable,avatar) VALUES (3, '神易风', 'shenyifeng_1@gmail.com','$2a$10$0bvs7K6EiFxhdHozEaEtW.bdTs7WSSbBhYPxzSTlRFjPek1qLep5q',true,'/static/images/avatar/man.png');

insert into authorities(id,email,authority)values (1,'ting6405@gmail.com','ROLE_USER');
insert into authorities(id,email,authority)values (2,'aojianshop@gmail.com','ROLE_USER');
insert into authorities(id,email,authority)values (3,'henyifeng_1@gmail.com','ROLE_USER');