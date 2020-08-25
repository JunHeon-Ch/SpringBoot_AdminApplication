insert into person(`id`, `name`, `age`, `blood_type`, `year`, `month`, `day`) values (1, 'a', 10, 'A', 1991, 1, 21);
insert into person(`id`, `name`, `age`, `blood_type`, `year`, `month`, `day`) values (2, 'b', 12, 'B', 1992, 3, 31);
insert into person(`id`, `name`, `age`, `blood_type`, `year`, `month`, `day`) values (3, 'c', 14, 'O', 1994, 2, 12);
insert into person(`id`, `name`, `age`, `blood_type`, `year`, `month`, `day`) values (4, 'd', 15, 'AB', 1996, 1, 21);
insert into person(`id`, `name`, `age`, `blood_type`, `year`, `month`, `day`) values (5, 'e', 17, 'A', 1997, 5, 29);

insert into block(`id`, `name`) values (1, 'a');
insert into block(`id`, `name`) values (2, 'c');

update person set block_id = 1 where id = 1;
update person set block_id = 1 where id = 3;