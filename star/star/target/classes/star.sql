/*键值表*/
create table sys_key(
	id int primary key auto_increment,
	`name` varchar(20) not null,
	num int not null
);
/*用户表*/
create table `user`