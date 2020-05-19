insert into authority (name) value ('ROLE_AGENT');
insert into agent (email, password) value ('agent@gmail.com', '$2a$10$z8pgIaOYaOE7MgeY7g/ceuqt377Zp8U/ZWQK2N5i1/8Sn4PYM1IIS');
insert into agent_authority (agent_id, authority_id) value (1,1);

insert into car_brand (name) value ('Tesla');
insert into car_brand (name) value ('BMW');
insert into car_brand (name) value('Audi');
insert into car_brand (name) value('Mercedes');

insert into car_model (name, car_brand_id) value ('Model S', 1);
insert into car_model (name, car_brand_id) value ('x5', 2);

insert into car_model (name, car_brand_id) value('A8', 3);
insert into car_model (name, car_brand_id) value('SLR McLaren', 4);

insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app) value (1, 3.5, 5000, 1,'Suv','Gas','Semi_automatic', true);
insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app) value (0, 3.8, 2000, 2, 'Station_vagon','Gas','Manuel', false);
insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app) value (2, 4, 10000, 3, 'Sport_car','Gas','Semi_automatic', true);

insert into photo (path, car_id) value ('@/assets/bmw_m2.jpg',1);
insert into photo (path, car_id) value ('@/assets/bmw_m2_2.jpg',2);
insert into photo (path, car_id) value ('@/assets/audi_a8.jpeg',3);

insert into price_list (alias, discount20Days, discount30Days, price_per_day, price_per_km) value ('Price list 1', 0, 15, 25, 3);

insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location ) value ('2020-05-05','2020-06-05','0',false,1,1,'Novi Sad');
insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location ) value ('2020-06-01','2020-06-30','0',true,2,1,'Novi Sad');
insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location ) value ('2020-04-01','2020-04-30','0',false,3,1, 'Beograd');

